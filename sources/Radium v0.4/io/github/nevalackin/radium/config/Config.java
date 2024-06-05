package io.github.nevalackin.radium.config;

import com.google.gson.JsonObject;
import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.module.Module;

import java.io.File;
import java.io.IOException;

public final class Config implements Serializable {

    private final String name;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.CONFIGS_DIR, name + ConfigManager.EXTENTION);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
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

        for (Module module : RadiumClient.getInstance().getModuleManager().getModules())
            modulesObject.add(module.getLabel(), module.save());

        jsonObject.add("Modules", modulesObject);

        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");

            for (Module module : RadiumClient.getInstance().getModuleManager().getModules()) {
                if (modulesObject.has(module.getLabel()))
                    module.load(modulesObject.getAsJsonObject(module.getLabel()));
            }
        }
    }
}
