package Hydro.config;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import com.google.gson.JsonObject;

import Hydro.Client;
import Hydro.module.Module;

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

    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();

        for (Module module : Client.instance.moduleManager.getModules())
            modulesObject.add(module.getName(), module.save());

        jsonObject.add("Modules", modulesObject);

        return jsonObject;
    }

    public void load(JsonObject object) {
        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");

            for (Module module : Client.instance.moduleManager.getModules())
                if (modulesObject.has(module.getName()))
                    module.load(modulesObject.getAsJsonObject(module.getName()));
            
        }
    }
}
