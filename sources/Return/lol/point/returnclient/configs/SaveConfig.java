package lol.point.returnclient.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lol.point.Return;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.ColorSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;

import java.io.IOException;

public class SaveConfig {

    public SaveConfig() {}

    public void saveToFile(String name, String directory) throws IOException {
        JsonObject configData = new JsonObject();

        configData.addProperty("Version", Return.INSTANCE.version);
        for (Category category : Category.values()) {
            JsonObject categoryObject = new JsonObject();

            for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                JsonObject moduleObject = new JsonObject();
                moduleObject.addProperty("Enabled", module.enabled);

                JsonArray settingsArray = new JsonArray();
                for (Setting setting : module.getSettings()) {
                    JsonObject settingObject = new JsonObject();
                    settingObject.addProperty("Name", setting.name);

                    switch (setting) {
                        case StringSetting stringSetting -> settingObject.addProperty("Value", stringSetting.value);
                        case NumberSetting numberSetting ->
                                settingObject.addProperty("Value", numberSetting.value.floatValue());
                        case BooleanSetting booleanSetting -> settingObject.addProperty("Value", booleanSetting.value);
                        case ColorSetting colorSetting -> {
                            settingObject.addProperty("Red", colorSetting.color.getRed());
                            settingObject.addProperty("Green", colorSetting.color.getGreen());
                            settingObject.addProperty("Blue", colorSetting.color.getBlue());
                            settingObject.addProperty("Alpha", colorSetting.color.getAlpha());
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + setting);
                    }

                    settingsArray.add(settingObject);
                }

                moduleObject.add("Settings", settingsArray);
                categoryObject.add(module.name, moduleObject);
            }

            configData.add(category.name, categoryObject);
        }

        Return.INSTANCE.configManager.saveConfig(name, directory, configData);

        Return.LOGGER.info("Saved config {} to {} successfully.", name, directory);
    }

    public void saveDefault() throws IOException {
        JsonObject configData = new JsonObject();

        configData.addProperty("Version", Return.INSTANCE.version);
        configData.addProperty("Prefix", Return.INSTANCE.commandPrefix);
        for (Category category : Category.values()) {
            JsonObject categoryObject = new JsonObject();

            for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                JsonObject moduleObject = new JsonObject();
                moduleObject.addProperty("Enabled", module.enabled);

                JsonArray settingsArray = new JsonArray();
                for (Setting setting : module.getSettings()) {
                    JsonObject settingObject = new JsonObject();
                    settingObject.addProperty("Name", setting.name);

                    switch (setting) {
                        case StringSetting stringSetting -> settingObject.addProperty("Value", stringSetting.value);
                        case NumberSetting numberSetting ->
                                settingObject.addProperty("Value", numberSetting.value.floatValue());
                        case BooleanSetting booleanSetting -> settingObject.addProperty("Value", booleanSetting.value);
                        case ColorSetting colorSetting -> {
                            settingObject.addProperty("Red", colorSetting.color.getRed());
                            settingObject.addProperty("Green", colorSetting.color.getGreen());
                            settingObject.addProperty("Blue", colorSetting.color.getBlue());
                            settingObject.addProperty("Alpha", colorSetting.color.getAlpha());
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + setting);
                    }

                    settingsArray.add(settingObject);
                }

                moduleObject.add("Settings", settingsArray);
                categoryObject.add(module.name, moduleObject);
            }

            configData.add(category.name, categoryObject);
        }

        Return.INSTANCE.configManager.saveDefault(configData);
        Return.LOGGER.info("Saved the default config successfully.");
    }

    public void saveKeybinds() throws IOException {
        JsonObject configData = new JsonObject();

        for (Category category : Category.values()) {
            JsonObject categoryObject = new JsonObject();

            for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                JsonObject moduleObject = new JsonObject();
                moduleObject.addProperty("Keybind", module.key);
                categoryObject.add(module.name, moduleObject);
            }

            configData.add(category.name, categoryObject);
        }

        Return.INSTANCE.configManager.saveKeybinds(configData);
        Return.LOGGER.info("Saved the keybinds successfully.");
    }
}