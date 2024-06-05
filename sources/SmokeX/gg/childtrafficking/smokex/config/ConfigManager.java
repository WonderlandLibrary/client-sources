// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.FileWriter;
import com.google.gson.GsonBuilder;
import java.util.Iterator;
import java.util.Objects;
import gg.childtrafficking.smokex.SmokeXClient;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public final class ConfigManager
{
    private List<Config> configs;
    private File configDirectory;
    private Config currentConfig;
    
    public ConfigManager() {
        this.configs = new ArrayList<Config>();
    }
    
    public void init() {
        this.configDirectory = new File(SmokeXClient.getInstance().getClientDirectory(), "configs");
        if (!this.configDirectory.isDirectory()) {
            this.configDirectory.mkdirs();
        }
        for (final File file : Objects.requireNonNull(this.configDirectory.listFiles())) {
            this.configs.add(new Config(file.getName().replace(".json", "")));
        }
    }
    
    public Config getConfig(final String name) {
        for (final Config config : this.configs) {
            if (config.getName().equalsIgnoreCase(name)) {
                return config;
            }
        }
        return null;
    }
    
    public boolean save(final String name) {
        if (this.getConfig(name) == null) {
            this.configs.add(new Config(name));
        }
        return this.save(this.getConfig(name));
    }
    
    public boolean save(final Config config) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final FileWriter fileWriter = new FileWriter(this.configDirectory + "/" + config.getName() + ".json");
            gson.toJson((JsonElement)config.serialize(), (Appendable)fileWriter);
            fileWriter.close();
            System.out.println("Saved " + config.getName());
            this.currentConfig = config;
            return true;
        }
        catch (final Exception e) {
            System.out.println("Failed to save " + config.getName());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean load(final String name) {
        final Config config = this.getConfig(name);
        if (config == null) {
            for (final File file : Objects.requireNonNull(this.configDirectory.listFiles())) {
                if (file.getName().replace(".json", "").equalsIgnoreCase(name)) {
                    this.configs.add(new Config(name));
                    return this.load(this.getConfig(name));
                }
            }
            return false;
        }
        return this.load(this.getConfig(name));
    }
    
    public boolean load(final Config config) {
        try {
            this.currentConfig = config;
            final JsonParser jsonParser = new JsonParser();
            return config.deserialize(jsonParser.parse(new String(Files.readAllBytes(Paths.get(this.configDirectory + "/" + config.getName() + ".json", new String[0])))));
        }
        catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public File getConfigDirectory() {
        return this.configDirectory;
    }
    
    public List<Config> getConfigs() {
        return this.configs;
    }
    
    public Config getCurrentConfig() {
        return this.currentConfig;
    }
}
