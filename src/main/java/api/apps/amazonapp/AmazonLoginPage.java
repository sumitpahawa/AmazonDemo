package api.apps.amazonapp;

import core.managers.logmanager.MyLogger;
import core.uiactions.AndroidUiActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AmazonLoginPage extends AndroidUiActions {

    public AmazonLoginPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
        MyLogger.log.info("AmazonLoginPage Objects instance is created");
    }

    @FindBy(id = "ap_email_login")
    AndroidElement loginField;
    @FindBy(id = "ap_password")
    AndroidElement passField;
    @FindBy(id = "signInSubmit")
    AndroidElement signInBtn;
    @FindBy(id = "continue")
    AndroidElement continueBtn;
    @FindBy(id = "login_accordion_header")
    WebElement loginRadioBtn;

    public AmazonLoginPage login(String username, String Password) {
        try {
            ScreenOrientation CurrentScreen = driver.getOrientation();
            if(CurrentScreen.equals(ScreenOrientation.LANDSCAPE))
                driver.rotate(ScreenOrientation.PORTRAIT);
            System.out.printf(Password);
            typeText(loginField, username);
            continueBtn.click();
            typeText(passField, Password);
            Thread.sleep(5000);
            tap(signInBtn);
            System.out.printf(driver.getContext() + getPageSource());
        } catch (Exception e) {
            MyLogger.log.info("Unable to login" + e.getMessage());
            System.out.println("Element not found");
        }
        return this;
    }


}
