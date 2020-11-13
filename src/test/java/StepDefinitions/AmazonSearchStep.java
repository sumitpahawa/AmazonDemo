package StepDefinitions;

import api.apps.Apps;
import core.constants.Constants;
import core.managers.baseutils.ADB;
import core.managers.filemanager.JSONReader;
import core.managers.logmanager.MyLogger;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AmazonSearchStep {
    Apps DUT;
    JSONReader jsonReader;
    ADB  adb;
    @Given("User is on home page")
    public void user_is_on_home_page() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        jsonReader = new JSONReader("deviceDetails.json");
        adb = new ADB(jsonReader.getJSONValue("DEVICE_MO","udid"));
        DUT = new Apps(Constants.DEVICE_MO,"AmazonApp");
        MyLogger.log.info("Sucessfully created the driver ith capabilities: " +DUT.driver);
        Thread.sleep(20000);
    }

    @When("Search the product with {string}")
    public void search_the_product_with(String string) {

        DUT.amazonApp.amazonHomePage.tapOnSearchBox();
        DUT.amazonApp.amazonHomePage.enterSearchText(string);

    }

    @When("Select any of the random product from search list and add it to cart and complete the purchase process")
    public void select_any_of_the_random_product_from_search_list_and_addit_to_cart_and_complete_the_purchase_process() {
        // Write code here that turns the phrase above into concrete actions
        DUT.amazonApp.amazonHomePage.scrollToText("Sony Bravia 164 cm (65 inches) 4K Ultra HD Certified Android LED TV 65X8000H (Black) (2020 Model)");
        DUT.amazonApp.amazonHomePage.tapUiAutomatorText("Sony Bravia 164 cm (65 inches) 4K Ultra HD Certified Android LED TV 65X8000H (Black) (2020 Model)");

        //DUT.amazonApp.amazonHomePage.verifyDetailPageDisplayed();
        DUT.amazonApp.amazonHomePage.addToCart();

        DUT.amazonApp.amazonHomePage.proceedToCheckout();
       /* DUT.amazonApp.amazonHomePage.tapUiAutomatorResourceId("in.amazon.mShop.android.shopping:id/chrome_action_bar_cart_count");
        DUT.amazonApp.amazonHomePage.tapUiAutomatorText("Proceed to Buy");*/
        //DUT.amazonApp.amazonHomePage.Checkout();
        DUT.amazonApp.amazonLoginPage.login("9953007953","shilpi10");

    }
    @After(value = "@Search",order = 1)
    public void closeDriver(){
        DUT.amazonApp.driver.quit();
    }



}
