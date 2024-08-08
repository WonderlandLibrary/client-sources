package in.momin5.cookieclient.client.config;

import in.momin5.cookieclient.CookieClient;

import java.io.IOException;

public class ConfigStopper extends Thread{
    @Override
    public void run() {
        saveConfig();
    }

    public static void saveConfig() {
        try {
            CookieClient.clickGUISave.clickGuiSave();
            CookieClient.clickGUISave.saveClickGUIPositions();
            // chinese code right here - momin5
            if(CookieClient.configSave != null) {
                CookieClient.configSave.save();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
