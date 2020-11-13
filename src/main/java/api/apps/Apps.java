package api.apps;

import api.apps.amazonapp.AmazonApp;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class Apps {
    public AndroidDriver<AndroidElement> driver;
    public AmazonApp amazonApp;

    public Apps(String device, String app, boolean noReset) {
        if (app.equals("AmazonApp")) {
            amazonApp = new AmazonApp(device, noReset);
        }
    }

    public Apps(String device, String app) {
       if(app.equals("AmazonApp")){
            amazonApp = new AmazonApp(device);
        }

    }
}

