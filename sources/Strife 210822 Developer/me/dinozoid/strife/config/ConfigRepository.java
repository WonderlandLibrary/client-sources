package me.dinozoid.strife.config;

import me.dinozoid.strife.StrifeClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ConfigRepository {

    public static Path CONFIG_DIRECTORY = null;
    private final List<Config> CONFIGS = new ArrayList<>();

    private Config currentConfig;

    public void init() {
        CONFIG_DIRECTORY = Paths.get(String.valueOf(StrifeClient.DIRECTORY), "configs");
        CONFIGS.clear();
        try {
            CONFIG_DIRECTORY.toFile().mkdirs();
            Files.list(CONFIG_DIRECTORY).map(Path::toFile).forEach(file -> CONFIGS.add(new Config(file.getName().replace(".json", ""))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean load(Config config) {
        if(config != null)
            if(config.load()) {
                currentConfig = config;
                return true;
            }
        return false;
    }

    public boolean add(Config config) {
        CONFIGS.add(config);
        return config.save();
    }

    public boolean remove(Config config) {
        CONFIGS.remove(config);
        return config.delete();
    }

    public boolean save(Config config) {
        if(config != null)
            return config.save();
        return false;
    }

    public boolean load(String name) {
        return load(configBy(name));
    }

    public boolean save(String name) {
        return save(configBy(name));
    }

    public Config configBy(String name) {
        return CONFIGS.stream().filter(config -> config.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Config> configs() {
        return CONFIGS;
    }

    public Config currentConfig() {
        return currentConfig;
    }

}
