package in.momin5.cookieclient.client.config;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.client.gui.clickgui.ClickGuiConfig;

import java.io.IOException;

public class ClickGUILoad {
    public ClickGUILoad() {
        try {
            clickGuiLoad();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    String fileName = CookieClient.MOD_NAME + "/";
    String mainName = "ClickGUI/";

    public void clickGuiLoad() throws IOException {
        loadClickGUIPositions();
    }

    public void loadClickGUIPositions() throws IOException {
        CookieClient.clickGUI.gui.loadConfig(new ClickGuiConfig(fileName+mainName));
    }
}
