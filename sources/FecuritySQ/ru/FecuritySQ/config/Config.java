package ru.FecuritySQ.config;

import com.google.gson.JsonObject;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.module.Module;

import java.io.File;

public final class Config implements ConfigUpdater {

    private final String name;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.configDirectory, name + ".json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();
        for (Module module : FecuritySQ.get().getModuleList()) {
            modulesObject.add(module.getName(), module.save());
        }
        jsonObject.add("Modules", modulesObject);
        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");
            for (Module module : FecuritySQ.get().getModuleList()) {
                module.load(modulesObject.getAsJsonObject(module.getName()));
            }
        }
    }
}