package me.kaimson.melonclient.config;

import java.util.*;
import me.kaimson.melonclient.config.utils.*;
import com.google.common.collect.*;
import me.kaimson.melonclient.*;

public class ConfigManager
{
    public static final ConfigManager INSTANCE;
    private final List<Config> configs;
    
    public ConfigManager() {
        (this.configs = (List<Config>)Lists.newArrayList()).add(ModuleConfig.INSTANCE);
        this.configs.add(GeneralConfig.INSTANCE);
    }
    
    public void saveAll() {
        final long start;
        this.configs.forEach(config -> {
            start = System.currentTimeMillis();
            Client.info("Saving " + config.name + "...", new Object[0]);
            config.saveConfig();
            Client.info("Saved in " + (System.currentTimeMillis() - start) + "ms!", new Object[0]);
        });
    }
    
    public void loadAll() {
        final long start;
        this.configs.forEach(config -> {
            start = System.currentTimeMillis();
            Client.info("Loading " + config.name + "...", new Object[0]);
            config.loadConfig();
            Client.info("Loaded in " + (System.currentTimeMillis() - start) + "ms!", new Object[0]);
        });
    }
    
    static {
        INSTANCE = new ConfigManager();
    }
}
