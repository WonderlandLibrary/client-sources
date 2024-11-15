package dev.lvstrng.argon.managers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.modules.setting.settings.KeybindSetting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ConfigManager {

    private final Gson gson;
    private final Path configDir;
    private final Path configFile;
    private JsonObject configData;

    public ConfigManager() {
        this.gson = new Gson();
        this.configDir = resolveConfigDirectory();
        this.configFile = configDir.resolve("settings.json");
    }

    private Path resolveConfigDirectory() {
        String baseDir = System.getProperty("os.name").toLowerCase().contains("win") ?
                System.getProperty("java.io.tmpdir") :
                System.getProperty("user.home");

        return Paths.get(baseDir, "argonConfig");
    }

    public void loadConfig() throws IOException {
        if (!Files.exists(configFile)) {
            return;
        }

        configData = gson.fromJson(Files.readString(configFile), JsonObject.class);

        for (Module module : Argon.INSTANCE.getModuleManager().getModules()) {
            JsonElement moduleData = configData.get(String.valueOf(Argon.INSTANCE.getModuleManager().getModules().indexOf(module)));

            if (moduleData != null && moduleData.isJsonObject()) {
                JsonObject moduleObject = moduleData.getAsJsonObject();
                loadModuleState(module, moduleObject);
            }
        }
    }

    private void loadModuleState(Module module, JsonObject moduleObject) {
        JsonElement enabledElement = moduleObject.get("enabled");
        if (enabledElement != null && enabledElement.isJsonPrimitive()) {
            module.setEnabled(enabledElement.getAsBoolean());
        }

        for (Setting setting : module.getSettings()) {
            loadSetting(module, setting, moduleObject);
        }
    }

    private void loadSetting(Module module, Setting setting, JsonObject moduleObject) {
        JsonElement settingElement = moduleObject.get(String.valueOf(module.getSettings().indexOf(setting)));

        if (settingElement != null && settingElement.isJsonPrimitive()) {
            if (setting instanceof BooleanSetting booleanSetting) {
                booleanSetting.setValue(settingElement.getAsBoolean());
            } else if (setting instanceof EnumSetting enumSetting) {
                enumSetting.setMode(settingElement.getAsInt());
            } else if (setting instanceof IntSetting intSetting) {
                intSetting.setValue(settingElement.getAsDouble());
            } else if (setting instanceof KeybindSetting keybindSetting) {
                keybindSetting.setKey(settingElement.getAsInt());
                module.setKeybind(settingElement.getAsInt());
            }
        }
    }

    public void saveConfig() throws IOException {
        Files.createDirectories(configDir);
        configData = new JsonObject();

        for (Module module : Argon.INSTANCE.getModuleManager().getModules()) {
            JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("enabled", module.isEnabled());

            saveModuleSettings(module, moduleObject);
            configData.add(String.valueOf(Argon.INSTANCE.getModuleManager().getModules().indexOf(module)), moduleObject);
        }

        Files.writeString(configFile, gson.toJson(configData));
    }

    private void saveModuleSettings(Module module, JsonObject moduleObject) {
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
                moduleObject.addProperty(String.valueOf(module.getSettings().indexOf(setting)), booleanSetting.getValue());
            } else if (setting instanceof EnumSetting enumSetting) {
                moduleObject.addProperty(String.valueOf(module.getSettings().indexOf(setting)), enumSetting.getMode());
            } else if (setting instanceof IntSetting intSetting) {
                moduleObject.addProperty(String.valueOf(module.getSettings().indexOf(setting)), intSetting.getValue());
            } else if (setting instanceof KeybindSetting keybindSetting) {
                moduleObject.addProperty(String.valueOf(module.getSettings().indexOf(setting)), keybindSetting.getKey());
            }
        }
    }
}
