// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.config;

import xyz.niggfaclient.Client;
import java.util.Iterator;
import java.io.IOException;
import java.io.FileWriter;
import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.Reader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import org.apache.commons.io.FilenameUtils;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import xyz.niggfaclient.module.Manager;

public class ConfigManager extends Manager<Config>
{
    public static final File CONFIGS_DIR;
    
    public ConfigManager() {
        super(loadConfigs());
        ConfigManager.CONFIGS_DIR.mkdirs();
    }
    
    private static ArrayList<Config> loadConfigs() {
        final ArrayList<Config> loadedConfigs = new ArrayList<Config>();
        final File[] files = ConfigManager.CONFIGS_DIR.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (FilenameUtils.getExtension(file.getName()).equals("json")) {
                    loadedConfigs.add(new Config(FilenameUtils.removeExtension(file.getName())));
                }
            }
        }
        return loadedConfigs;
    }
    
    public boolean loadConfig(final String configName) {
        if (configName != null) {
            final Config config = this.findConfig(configName);
            if (config != null) {
                try {
                    final FileReader reader = new FileReader(config.getFile());
                    final JsonParser parser = new JsonParser();
                    final JsonObject object = (JsonObject)parser.parse(reader);
                    config.load(object);
                    return true;
                }
                catch (FileNotFoundException e) {
                    return false;
                }
            }
            return false;
        }
        return false;
    }
    
    public boolean saveConfig(final String configName) {
        if (configName != null) {
            Config config = this.findConfig(configName);
            if (config == null) {
                final Config newConfig;
                config = (newConfig = new Config(configName));
                this.getElements().add(newConfig);
            }
            final String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson(config.save());
            try {
                final FileWriter writer = new FileWriter(config.getFile());
                writer.write(contentPrettyPrint);
                writer.close();
                return true;
            }
            catch (IOException e) {
                return false;
            }
        }
        return false;
    }
    
    public Config findConfig(final String configName) {
        if (configName != null) {
            for (final Config config : this.getElements()) {
                if (config.getName().equalsIgnoreCase(configName)) {
                    return config;
                }
            }
            if (new File(ConfigManager.CONFIGS_DIR, configName + ".json").exists()) {
                return new Config(configName);
            }
        }
        return null;
    }
    
    public boolean deleteConfig(final String configName) {
        if (configName != null) {
            final Config config = this.findConfig(configName);
            if (config != null) {
                final File f = config.getFile();
                this.getElements().remove(config);
                return f.exists() && f.delete();
            }
        }
        return false;
    }
    
    static {
        CONFIGS_DIR = new File(Client.getInstance().folder + "/configs");
    }
}
