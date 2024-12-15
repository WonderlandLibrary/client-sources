package com.alan.clients.util.file.config;

import com.alan.clients.util.file.FileManager;
import com.alan.clients.util.file.FileType;
import lombok.Getter;
import rip.vantage.commons.util.time.StopWatch;

import java.io.File;
import java.util.ArrayList;
public class ConfigManager extends ArrayList<ConfigFile> {

    public static final File CONFIG_DIRECTORY = new File(FileManager.DIRECTORY, "configs");
    @Getter
    private ConfigFile latestConfig;
    private final StopWatch stopWatch = new StopWatch();

    public void init() {
        if (!CONFIG_DIRECTORY.exists()) {
            CONFIG_DIRECTORY.mkdir();
        }

        this.stopWatch.setMillis(5000L);
        this.update();
    }

    public void setupLatestConfig() {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, "latest.json");
        final ConfigFile configFile = new ConfigFile(file, FileType.CONFIG);
        configFile.saveKeyCodes();
        configFile.read();

        latestConfig = configFile;
    }

    public ConfigFile get(final String config) {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, config + ".json");

        return new ConfigFile(file, FileType.CONFIG);
    }

    public void set(final String config, boolean saveKeyCodes) {
        final File file = new File(CONFIG_DIRECTORY, config + ".json");
        ConfigFile configFile = get(config);

        if (saveKeyCodes) configFile.saveKeyCodes();

        if (configFile == null) {
            configFile = new ConfigFile(file, FileType.CONFIG);
            add(configFile);

            System.out.println("Creating new config...");
        } else {
            System.out.println("Overwriting existing config...");
        }

        configFile.write();

        System.out.println("Config saved to files.");
    }

    public boolean update() {
        clear();

        final File[] files = CONFIG_DIRECTORY.listFiles();

        if (files == null)
            return false;

        for (final File file : files) {
            if (file.getName().endsWith(".json")) {
                add(new ConfigFile(file, FileType.CONFIG, file.getName().replace(".json", "")));
            }
        }

        return true;
    }

    public boolean delete(final String config) {
        final ConfigFile configFile = get(config);

        if (configFile == null)
            return false;

        remove(configFile);
        return configFile.getFile().delete();
    }

    public StopWatch getStopWatch() {
        return stopWatch;
    }
}