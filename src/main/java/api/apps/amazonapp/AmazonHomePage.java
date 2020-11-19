package api.apps.amazonapp;

import core.managers.logmanager.MyLogger;
import core.uiactions.AndroidUiActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AmazonHomePage extends AndroidUiActions {

    public AmazonHomePage(AndroidDriver<AndroidElement> driver) {
        super(driver);
        MyLogger.log.info("AmazonHomePage Objects instance is created");
    }

    @FindBy(id = "in.amazon.mShop.android.shopping:id/rs_search_src_text")
    AndroidElement seacrhTextBox;

    @FindBy(id = "android.widget.TextView")
    List<AndroidElement> allTextViews;
    @FindBy(xpath = "//*[@resource-id='a-autoid-0-announce']")
    AndroidElement deliverToThisAddressBtn;

    public AmazonHomePage tapOnSearchBox() {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            tapUiAutomatorText("Search");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public AmazonHomePage enterSearchText(String search) {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            seacrhTextBox.sendKeys(search);
            pressEnterKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void selectItemForPurchase(String text) {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            Thread.sleep(1000);
            scrollToText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AmazonHomePage addToCart() {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            scrollToText("ADD TO CART");
            tapUiAutomatorText("ADD TO CART");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public AmazonHomePage proceedToCheckout() {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            Thread.sleep(4000);
            tapUiAutomatorContent("Proceed to checkout");
            Thread.sleep(4000);
            System.out.printf(driver.getContext() + driver.getPageSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }



public void clickOnContinueBtn() {
        try {
        tapUiAutomatorContent("Continue");
        MyLogger.log.info("Successfully tap on Continue button");
        } catch (Exception e) {
        MyLogger.log.info("Unable to tap on Continue button");
        e.printStackTrace();
        }
        }
public AmazonHomePage clickOnSignInButton(){
        try{
        tapUiAutomatorContent("Sign in");
        Thread.sleep(3000);
        MyLogger.log.info("Successfully tap on SignIn button");
        }catch (Exception e){
        MyLogger.log.info("Unable to tap on SignIn button");
        }
        return this;
        }
        }
