package dev.darkmoon.client.manager.config;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonObject;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.command.CommandManager;
import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.Module;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class Config implements ConfigUpdater {
    @Getter
    private final String name;
    @Getter
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.configDirectory, name + ".dm");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();

        for (Module module : DarkMoon.getInstance().getModuleManager().getModules()) {
            modulesObject.add(module.name, module.save());
        }

        jsonObject.addProperty("Prefix", CommandManager.getPrefix());
        jsonObject.addProperty("StyleTheme", DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getName());
        jsonObject.add("Modules", modulesObject);

        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Prefix")) {
            CommandManager.setPrefix(object.get("Prefix").getAsString());
        }
        if (object.has("StyleTheme")) {
            DarkMoon.getInstance().getThemeManager().setCurrentStyleTheme(Themes.findByName(object.get("StyleTheme").getAsString()));
        }
        if (object.has("Prefix")) {
            CommandManager.setPrefix(object.get("Prefix").getAsString());
        }
        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");
            for (Module module : DarkMoon.getInstance().getModuleManager().getModules()) {
                EventManager.unregister(module);
                module.load(modulesObject.getAsJsonObject(module.name));
            }
        }
    }
}
