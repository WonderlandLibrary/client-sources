package in.momin5.cookieclient.client.config;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.client.gui.clickgui.ClickGuiConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClickGUISave {
    public ClickGUISave() {
        try {
            clickGuiSave();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String fileName = CookieClient.MOD_NAME + "/";
    String mainName = "ClickGUI/";

    public void clickGuiSave() throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            Files.createDirectories(Paths.get(fileName));
        }
        if (!Files.exists(Paths.get(fileName + mainName))) {
            Files.createDirectories(Paths.get(fileName + mainName));
        }
    }

    public void registerFiles(String location, String name) throws IOException {
        if (!Files.exists(Paths.get(fileName + location + name + ".json"))) {
            Files.createFile(Paths.get(fileName + location + name + ".json"));
        }
        else {
            File file = new File(fileName + location + name + ".json");

            file.delete();

            Files.createFile(Paths.get(fileName + location +name + ".json"));
        }
    }

    public void saveClickGUIPositions() throws IOException {
        registerFiles(mainName, "ClickGUI");
        CookieClient.clickGUI.gui.saveConfig(new ClickGuiConfig(fileName+mainName));
    }
}
