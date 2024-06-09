// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.config;

import java.util.Iterator;
import com.google.gson.JsonElement;
import xyz.niggfaclient.module.Module;
import xyz.niggfaclient.Client;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.File;

public class Config implements Serializable
{
    private final String name;
    private final File file;
    
    public Config(final String name) {
        this.name = name;
        this.file = new File(ConfigManager.CONFIGS_DIR, name + ".json");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            }
            catch (IOException ex) {}
        }
    }
    
    @Override
    public JsonObject save() {
        final JsonObject jsonObject = new JsonObject();
        final JsonObject modulesObject = new JsonObject();
        for (final Module module : Client.getInstance().getModuleManager().getModules()) {
            modulesObject.add(module.getName(), module.save());
        }
        jsonObject.add("Modules", modulesObject);
        return jsonObject;
    }
    
    @Override
    public void load(final JsonObject object) {
        if (object.has("Modules")) {
            final JsonObject modulesObject = object.getAsJsonObject("Modules");
            for (final Module module : Client.getInstance().getModuleManager().getModules()) {
                if (modulesObject.has(module.getName())) {
                    module.load(modulesObject.getAsJsonObject(module.getName()));
                }
            }
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getName() {
        return this.name;
    }
}
