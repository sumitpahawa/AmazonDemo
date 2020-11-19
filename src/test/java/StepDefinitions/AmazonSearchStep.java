package StepDefinitions;

import api.apps.Apps;
import core.constants.Constants;
import core.managers.baseutils.ADB;
import core.managers.filemanager.JSONReader;
import core.managers.logmanager.MyLogger;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

public class AmazonSearchStep {
    Apps DUT;
    JSONReader jsonReader;
    ADB adb;
    JSONReader data;
    Boolean needLogin= false;
    @Given("User is on home page")
    public void user_is_on_home_page() throws InterruptedException {
        jsonReader = new JSONReader("deviceDetails.json");
        data = new JSONReader("data.json");
        adb = new ADB(jsonReader.getJSONValue("DEVICE_MO", "udid"));
        DUT = new Apps(Constants.DEVICE_MO, "AmazonApp");
        MyLogger.log.info("Successfully created the driver ith capabilities: " + DUT.driver);
        Thread.sleep(20000);
    }


    @When("click on signIn button")
    public void clickOnSignInButton() {
        if(DUT.amazonApp.amazonLoginPage.isLoggedIn()) {
            DUT.amazonApp.amazonHomePage.clickOnSignInButton();
            needLogin=true;

        }else{
            MyLogger.log.info("User already Logged In");
        }
    }

    @And("Enter the valid credential and click on login button.")
    public void enterTheValidCredential() {
        if(needLogin) {
            DUT.amazonApp.amazonLoginPage.login(data.getJSONValue("username"), data.getJSONValue("password"));
        }else{
            MyLogger.log.info("Already Logged In");

        }
    }

    @Then("User successfully logged in to the application")
    public void userSuccessfullyLoggedInToTheApplication() {
        Assert.assertFalse(DUT.amazonApp.amazonLoginPage.isLoggedIn());
    }
    @When("User enter the {string} in search box")
    public void userEnterTheInSearchBox(String arg0) {
        DUT.amazonApp.amazonHomePage.tapOnSearchBox();
        DUT.amazonApp.amazonHomePage.enterSearchText(arg0);
    }

    @And("Select any of the product from search list and add it to cart and complete the purchase process")
    public void selectAnyOfTheProductFromSearchListAndAddItToCartAndCompleteThePurchaseProcess() {
        DUT.amazonApp.amazonHomePage.scrollToText(data.getJSONValue("productName"));
        DUT.amazonApp.amazonHomePage.tapUiAutomatorText(data.getJSONValue("productName"));
        DUT.amazonApp.amazonHomePage.addToCart();
        DUT.amazonApp.amazonHomePage.proceedToCheckout();

    }
    @AfterStep("@Search")
    public void afterEveryStep(Scenario scenario) {
        if(scenario.isFailed())
        scenario.attach(((TakesScreenshot) DUT.amazonApp.driver).getScreenshotAs(OutputType.BYTES), "image/png", scenario.getName());

    }
    @After(value = "@Search", order = 1)
    public void closeDriver() {
        DUT.amazonApp.amazonHomePage.goBack();
        DUT.amazonApp.driver.quit();
    }




}
