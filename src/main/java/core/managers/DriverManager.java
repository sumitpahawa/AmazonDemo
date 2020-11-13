package core.managers;

import com.google.common.collect.ImmutableMap;
import core.managers.baseutils.ADB;
import core.managers.logmanager.MyLogger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private HashMap<String, URL> hosts;
    private DriverService service;
    private String deviceID;
    private AndroidDriver<AndroidElement> driver;
    private ADB adb;

    public AndroidDriver<AndroidElement> getDriver() {
        return driver;
    }

    private DesiredCapabilities getAPNativeDesiredCapabilities(HashMap<Object, Object> sessionReqDetails) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, sessionReqDetails.get("DEVICE_NAME"));
        caps.setCapability(MobileCapabilityType.UDID, sessionReqDetails.get("DEVICE_ID"));
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, sessionReqDetails.get("APP_PACKAGE"));
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, sessionReqDetails.get("APP_ACTIVITY"));
        caps.setCapability(MobileCapabilityType.NO_RESET, sessionReqDetails.get("NO_RESET"));
        caps.setCapability("chromedriverExecutable", "/usr/bin/chromedriver");
        //caps.setCapability("chromedriverExecutable","");
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("newCommandTimeout", 60 * 10);
//      caps.setCapability("unicodeKeyboard", true);
        caps.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        return caps;
    }

    private DesiredCapabilities getAPWebDesiredCapabilities(HashMap<Object, Object> sessionReqDetails) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, sessionReqDetails.get("DEVICE_ID"));
        caps.setCapability(MobileCapabilityType.UDID, sessionReqDetails.get("DEVICE_ID"));
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, sessionReqDetails.get("BROWSER_NAME"));
        caps.setCapability("chromedriverExecutable", "C:\\Users\\techm\\Downloads\\chromedriver_win32_version80\\chromedriver.exe");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");

        //added by
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-data-dir=/Internal storage/Android/data/com.android.chrome");
        //chromeOptions.addArguments("--incognito");
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("w3c", false);
        chromeOptions.addArguments("--headless");
        caps.merge(chromeOptions);
        //caps.setCapability("chromedriverExecutable","C:\\Users\\techm\\IdeaProjects\\ATT_QE\\ATT_QE\\src\\test\\resources\\chromedriver\\chromedriver.exe");
        caps.setCapability("chromeOptions", chromeOptions);
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("newCommandTimeout", 60 * 10);
        caps.setCapability("unicodeKeyboard", true);
        return caps;
    }

    public AndroidDriver<AndroidElement> launchApp(HashMap<Object, Object> sessionRequest) {
        MyLogger.log.info("Trying to create new Driver for device: " + sessionRequest.get("DEVICE_ID"));
        if (sessionRequest.get("BROWSER_NAME") == null) {
            try {
                driver = new AndroidDriver<AndroidElement>(new URL(sessionRequest.get("URL") + ""), getAPNativeDesiredCapabilities(sessionRequest));
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                driver = new AndroidDriver<AndroidElement>(new URL(sessionRequest.get("URL") + ""), getAPWebDesiredCapabilities(sessionRequest));
            } catch (MalformedURLException e) {

            }
        }
        adb = new ADB((String) sessionRequest.get("DEVICE_ID"));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return driver;
    }


    public void killDriver() {
        if (driver != null) {
            MyLogger.log.info("Killing Android Driver");
            driver.quit();
        } else MyLogger.log.info("Android Driver is not initialized, nothing to kill");
    }
}