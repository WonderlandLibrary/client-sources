package me.r.touchgrass.file.files;


import com.google.gson.*;
import me.r.touchgrass.touchgrass;
import me.r.touchgrass.module.Module;
import me.r.touchgrass.settings.Setting;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by r on 04/01/2022
 */
public class SettingsConfig {

    private final File settingsFile;


    public SettingsConfig() {
        this.settingsFile = new File(touchgrass.getClient().directory + File.separator + "settings.json");
    }

    public void saveConfig() {
        try {
            final JsonObject jsonMod = new JsonObject();
            touchgrass.getClient().moduleManager.getModules().forEach(module -> {
                final JsonObject jsonSetting = new JsonObject();
                final Iterator<Setting> iterator = touchgrass.getClient().settingsManager.getSettings().iterator();
                while (iterator.hasNext()) {
                    final Setting setting = iterator.next();
                    if(setting.isModeButton() && setting.getParentMod() == module) {
                        jsonSetting.addProperty(setting.getName(), setting.isEnabled());
                    }
                    if(setting.isModeMode() && setting.getParentMod() == module) {
                        jsonSetting.addProperty(setting.getName(), setting.getMode());
                    }
                    if(setting.isModeSlider() && setting.getParentMod() == module) {
                        jsonSetting.addProperty(setting.getName(), setting.getValue());
                    }
                    if(setting.isModeText() && setting.getParentMod() == module) {
                        jsonSetting.addProperty(setting.getName(), setting.getText());
                    }
                }
                jsonMod.add(module.getName(), jsonSetting);
            });

            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.settingsFile));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            printWriter.println(gson.toJson(jsonMod));
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new BufferedReader(new FileReader(this.settingsFile)));
            if(jsonElement instanceof JsonNull) {
                return;
            }
            JsonObject jsonObject = (JsonObject) jsonElement;
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                Module module = touchgrass.getClient().moduleManager.getModulebyName(entry.getKey());
                if(module == null ) {
                    continue;
                }
                JsonObject jsonModule = (JsonObject) entry.getValue();
                for(Setting setting : touchgrass.getClient().settingsManager.getSettings()) {
                    JsonElement element = jsonModule.get(setting.getName());
                    if(element != null) {
                        if (setting.getParentMod().getName().equalsIgnoreCase(module.getName()) && setting.isModeButton()) {
                            setting.setState(element.getAsBoolean());
                        }
                        if (setting.getParentMod().getName().equalsIgnoreCase(module.getName()) && setting.isModeMode()) {
                            setting.setMode(element.getAsString());
                        }
                        if (setting.getParentMod().getName().equalsIgnoreCase(module.getName()) && setting.isModeText()) {
                            setting.setText(element.getAsString());
                        }
                        if (setting.getParentMod().getName().equalsIgnoreCase(module.getName()) && setting.isModeSlider()) {
                            setting.setValue(element.getAsDouble());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
