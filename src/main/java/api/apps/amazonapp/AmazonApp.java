package api.apps.amazonapp;

import core.managers.DeviceManager;
import core.managers.DriverManager;
import core.managers.filemanager.JSONReader;
import core.managers.logmanager.MyLogger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.HashMap;

public class AmazonApp {

    public AndroidDriver<AndroidElement> driver;
    public DeviceManager deviceManager;
    public JSONReader jsonReader;
    public AmazonHomePage amazonHomePage;
    public AmazonLoginPage amazonLoginPage;

    public AmazonApp(String device, boolean noReset) {
        init(device, noReset);
    }

    public AmazonApp(String device) {
        init(device, true);
    }


    public void init(String device, boolean noReset) {
        jsonReader = new JSONReader("deviceDetails.json");
        deviceManager = new DeviceManager(jsonReader.getJSONValue(device, "udid"));
        driver = new DriverManager().launchApp(getSessionReqMap(device, "amazonapp", noReset));
        MyLogger.log.info("Session created successfully with  " + driver.getCapabilities());
        amazonHomePage = new AmazonHomePage(driver);
        MyLogger.log.info("Successfully created the instance of Amazon HomePage");
        amazonLoginPage = new AmazonLoginPage(driver);
        MyLogger.log.info("Successfully created the instance of Amazon Home Page");

    }

    private HashMap<Object, Object> getSessionReqMap(String device, String childObject, boolean noReset) {
        HashMap<Object, Object> sessionReqMap = new HashMap<Object, Object>();
        sessionReqMap.put("DEVICE_ID", jsonReader.getJSONValue(device, "udid"));
        sessionReqMap.put("DEVICE_NAME", jsonReader.getJSONValue(device, "udid"));
        sessionReqMap.put("APP_PACKAGE", jsonReader.getJSONValue(device, childObject, "appPackage"));
        sessionReqMap.put("APP_ACTIVITY", jsonReader.getJSONValue(device, childObject, "appActivity"));
        sessionReqMap.put("NO_RESET", noReset);
        sessionReqMap.put("URL", jsonReader.getJSONValue(device, "appium_url"));
        return sessionReqMap;
    }
}
