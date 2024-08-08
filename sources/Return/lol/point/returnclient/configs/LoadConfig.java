package lol.point.returnclient.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lol.point.Return;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.ColorSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.ChatUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.IOException;

public class LoadConfig {

    public LoadConfig() {}

    public String loadFromFile(String name, String directory) throws IOException {
        JsonObject configData = Return.INSTANCE.configManager.loadConfig(name, directory);
        if (configData != null) {

            parseConfigData(configData);
            return "Loaded the config successfully.";
        } else {
            return "Failed to load the config.";
        }
    }

    public String loadDefault() throws IOException {
        JsonObject configData = Return.INSTANCE.configManager.loadDefault();
        if (configData != null) {
            parseConfigData(configData);
            return "Loaded the default config successfully.";
        } else {
            return "Failed to load the default config.";
        }
    }

    public String loadKeybinds() throws IOException {
        JsonObject configData = Return.INSTANCE.configManager.loadKeybinds();
        if (configData != null) {
            parseKeybinds(configData);
            return "Loaded keybinds successfully.";
        } else {
            return "Failed to load keybinds.";
        }
    }

    private void parseConfigData(JsonObject configData) {
        if (configData.has("Prefix")) {
            JsonElement prefixElement = configData.get("Prefix");

            if (prefixElement.isJsonPrimitive() && prefixElement.getAsJsonPrimitive().isString()) {
                Return.INSTANCE.commandPrefix = prefixElement.getAsString();
            } else {
                Return.INSTANCE.commandPrefix = ".";
            }
        }

        if (configData.has("Version")) {
            JsonElement prefixElement = configData.get("Version");

            if (prefixElement.isJsonPrimitive() && prefixElement.getAsJsonPrimitive().isString()) {
                if (Minecraft.getMinecraft().thePlayer != null && !Return.INSTANCE.version.equals(prefixElement.getAsString())) {
                    Return.LOGGER.warn("Warning! Config was made on a different version of the client, some features can break.");
                }
                Return.LOGGER.info("Loaded config version: {}", prefixElement.getAsString());
            } else {
                Return.LOGGER.error("Loaded config version is not valid.");
            }
        }
        for (Category category : Category.values()) {
            if (configData.has(category.name)) {
                JsonObject categoryObject = configData.getAsJsonObject(category.name);

                for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                    if (categoryObject.has(module.name)) {
                        JsonObject moduleObject = categoryObject.getAsJsonObject(module.name);

                        if (moduleObject.has("Enabled")) {
                            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
                        }

                        if (moduleObject.has("Settings")) {
                            JsonArray settingsArray = moduleObject.getAsJsonArray("Settings");
                            parseSettings(module, settingsArray);
                        }
                    }
                }
            }
        }
    }

    private void parseSettings(Module module, JsonArray settingsArray) {
        for (JsonElement settingElement : settingsArray) {
            if (settingElement.isJsonObject()) {
                JsonObject settingObject = settingElement.getAsJsonObject();

                String settingName = settingObject.get("Name").getAsString();
                Setting setting = module.getSettingByName(settingName);

                if (setting != null) {
                    switch (setting) {
                        case StringSetting stringSetting when settingObject.has("Value") ->
                                stringSetting.value = settingObject.get("Value").getAsString();
                        case NumberSetting numberSetting when settingObject.has("Value") ->
                                numberSetting.value = settingObject.get("Value").getAsFloat();
                        case BooleanSetting booleanSetting when settingObject.has("Value") ->
                                booleanSetting.value = settingObject.get("Value").getAsBoolean();
                        case ColorSetting colorSetting when settingObject.has("Red") && settingObject.has("Green") && settingObject.has("Blue") && settingObject.has("Alpha") ->
                                colorSetting.color = new Color(settingObject.get("Red").getAsInt(),
                                        settingObject.get("Green").getAsInt(),
                                        settingObject.get("Blue").getAsInt(),
                                        settingObject.get("Alpha").getAsInt());
                        default -> {
                        }
                    }
                }
            }
        }
    }

    private void parseKeybinds(JsonObject configData) {
        for (Category category : Category.values()) {
            if (configData.has(category.name)) {
                JsonObject categoryObject = configData.getAsJsonObject(category.name);

                for (Module module : Return.INSTANCE.moduleManager.getModulesByCategory(category)) {
                    if (categoryObject.has(module.name)) {
                        JsonObject moduleObject = categoryObject.getAsJsonObject(module.name);

                        if (moduleObject.has("Keybind")) {
                            module.key = moduleObject.get("Keybind").getAsInt();
                        }
                    }
                }
            }
        }
    }

}