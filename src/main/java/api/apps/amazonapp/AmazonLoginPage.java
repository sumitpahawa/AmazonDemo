package api.apps.amazonapp;

import core.managers.logmanager.MyLogger;
import core.uiactions.AndroidUiActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import jdk.jfr.internal.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AmazonLoginPage extends AndroidUiActions {

    public AmazonLoginPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
        MyLogger.log.info("AmazonLoginPage Objects instance is created");
    }

    @FindBy(id = "in.amazon.mShop.android.shopping:id/sign_in_button\"")
    AndroidElement signInButton;
    @FindBy(xpath = "//*[@resource-id='ap_email_login']")
    AndroidElement email;
    @FindBy(xpath = "//*[@resource-id='continue']")
    AndroidElement continueBtn;
    @FindBy(xpath = "//*[@resource-id='ap_password']")
    AndroidElement password;
    @FindBy(xpath = "//*[@resource-id='signInSubmit']")
    AndroidElement signInBtn;
    @FindBy(xpath = "//*[@resource-id='gwm-SignIn-button']")
    AndroidElement signIN;
    @FindBy(xpath = "//*[@resource-id='in.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon']")
    AndroidElement hamburgerMenu;
    @FindBy(xpath = "//*[@resource-id='in.amazon.mShop.android.shopping:id/anp_drawer_item']")
    AndroidElement homeMenu;


    @FindBy(id = "login_accordion_header")
    WebElement loginRadioBtn;

    public AmazonLoginPage login(String username, String Password) {

        try {
/*
                System.out.printf(driver.getContext());
                System.out.println(driver.getPageSource());*/
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if (CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            typeText(email, username);
            continueBtn.click();
            typeText(password, Password);
            System.out.println(driver.getPageSource());
            Thread.sleep(2000);
            tap(signInBtn);
            Thread.sleep(5000);
            System.out.printf(driver.getContext() + getPageSource());

        } catch (Exception e) {
            MyLogger.log.info("Unable to login" + e.getMessage());
            System.out.println("Element not found");
        }
        return this;
    }

    public AmazonLoginPage clickOnBurgerMenuIcon() {
        try {
            tap(hamburgerMenu);
        } catch (Exception e) {
            MyLogger.log.info("Unable to tap on Hamburger menu");
            e.printStackTrace();
        }
        return this;
    }

    public AmazonLoginPage goToMenuItem(String name) {
        try {
            scrollToText(name);
            tapUiAutomatorText(name);
            Thread.sleep(2000);
        } catch (Exception e) {
            MyLogger.log.info("Unable to tap on " + name +"menu");
            e.printStackTrace();
        }
        return this;
    }
    public AmazonLoginPage signOut() {
        try {
            Thread.sleep(2000);
            tapUiAutomatorText("SIGN OUT");
            Thread.sleep(5000);
        } catch (Exception e) {
            MyLogger.log.info("Unable to tap on Sign out button");
            e.printStackTrace();
        }
        return this;
    }
    public boolean isLoggedIn() {

        if (isElementPresent(signIN))
            return true;
        else
            return false;

    }


}
