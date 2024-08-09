package dev.excellent.impl.client.config;

import dev.excellent.Excellent;
import dev.excellent.impl.util.file.FileManager;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager extends ArrayList<ConfigFile> {

    public static File CONFIG_DIRECTORY;

    public void init() {
        CONFIG_DIRECTORY = new File(FileManager.DIRECTORY, "configs");
        if (!CONFIG_DIRECTORY.exists()) {
            if (CONFIG_DIRECTORY.mkdir()) {
                System.out.println("Папка с конфигами клиента успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки с конфигами клиента");
            }
        }

        this.update();

    }

    public ConfigFile get(final String config, final boolean allowKey) {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, config + "." + Excellent.getInst().getInfo().getNamespace());

        final ConfigFile configFile = new ConfigFile(file);
        if (allowKey) configFile.allowKeyCodeLoading();

        return configFile;
    }

    public ConfigFile get(final String config) {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, config + "." + Excellent.getInst().getInfo().getNamespace());

        final ConfigFile configFile = new ConfigFile(file);
        configFile.allowKeyCodeLoading();

        return configFile;
    }

    public void set() {
        set("default");
    }

    public void set(final String config) {
        final File file = new File(CONFIG_DIRECTORY, config + "." + Excellent.getInst().getInfo().getNamespace());
        ConfigFile configFile = get(config);

        if (configFile == null) {
            configFile = new ConfigFile(file);
            add(configFile);

            System.out.println("Creating new config...");
        }
        configFile.write();
    }

    public boolean update() {
        clear();
        final File[] files = CONFIG_DIRECTORY.listFiles();
        if (files == null)
            return false;

        for (final File file : files) {
            if (file.getName().endsWith("." + Excellent.getInst().getInfo().getNamespace())) {
                add(new ConfigFile(file));
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
}