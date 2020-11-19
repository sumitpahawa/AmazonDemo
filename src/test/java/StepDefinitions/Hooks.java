package StepDefinitions;


import core.constants.Constants;
import core.managers.DeviceManager;
import core.managers.baseutils.ADB;
import core.managers.filemanager.JSONReader;
import core.managers.logmanager.MyLogger;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.repeat;

public class Hooks {
    public ADB adbDUT;

    public DeviceManager deviceManagerDUT;
    public JSONReader jsonReader;
    private long startTime;
    private static AppiumDriverLocalService server;
    private static AppiumServiceBuilder serviceBuilder;

    @Before()
    public void beforeScenario(Scenario scenario) {
        MyLogger.log.info("#######################" + repeat("#", scenario.getName().length()) + "#######################");
        MyLogger.log.info("###########  START OF SCENARIO : " + scenario.getName() + getStartTime() + "##########");
        MyLogger.log.info("#######################" + repeat("#", scenario.getName().length()) + "#######################");
        try {
            startAppiumServer();

        } catch (Exception e) {
            System.out.println("Server is running");
        }

        jsonReader = new JSONReader("deviceDetails.json");
        adbDUT = new ADB(jsonReader.getJSONValue(Constants.DEVICE_MO, "udid"));
        deviceManagerDUT = new DeviceManager(jsonReader.getJSONValue(Constants.DEVICE_MO, "udid"));

    }


    @After(order = 0)
    public void after(Scenario scenario) {
        MyLogger.log.info("### END OF SCENARIO : " + scenario.getName() + " ,Time taken:" + getStartTime() + " mm:ss, Status: " + capitalize(scenario.getStatus().toString()));
        MyLogger.log.info("##########################" + repeat("#", scenario.getName().length() + scenario.getStatus().toString().length()) + "###########################");
        try {
            Runtime.getRuntime().exec("kill -9 $(lsof -t -i:4723)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public static void startAppiumServer() {
        try {
            serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withIPAddress("127.0.0.1");
            serviceBuilder.usingPort(4723);
            serviceBuilder.usingDriverExecutable(new File("/opt/node-v12.18.4-linux-x64/bin/node"));
            serviceBuilder.withAppiumJS(new File("/usr/local/bin/appium"));
            server = AppiumDriverLocalService.buildService(serviceBuilder);
            server.start();
        } catch (Exception e){

        }
    }

    public static void stopAppiumServer() {
        server.stop();
    }

    public boolean checkIfServerIsRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket = null;
        try {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }
}
