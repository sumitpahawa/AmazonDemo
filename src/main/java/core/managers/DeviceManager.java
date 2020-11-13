package core.managers;

import core.managers.baseutils.ADB;

public class DeviceManager {
    public ADB adb;

    public DeviceManager(String deviceUDID){
        adb = new ADB(deviceUDID);
    }
}
