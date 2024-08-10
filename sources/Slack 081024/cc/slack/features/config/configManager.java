package cc.slack.features.config;

import cc.slack.utils.other.PrintUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.*;

@Getter
public class configManager {
    public static final Map<String, Config> configs = new HashMap<>();

    private static final File configFolder = new File(Minecraft.getMinecraft().mcDataDir, "/" + "SlackClient" + "/configs");

    private Config activeConfig;

    public static String currentConfig = "default";

    private static void refresh() {
        for (File file : Objects.requireNonNull(configFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                String name = file.getName().replaceAll(".json", "");
                Config config = new Config(name);
                configs.put(config.getName(), config);
            }
        }
    }

    public static void init() {
        if (!configFolder.mkdirs()) {
            PrintUtil.print("Failed to make config folder");
            return;
        }

        refresh();

        if (getConfig("default") == null) {
            Config config = new Config("default");
            config.write();
            configs.put(config.getName(), config);
        } else getConfig("default").read();
    }

    public static void stop() {
        if (getConfig(currentConfig) == null) {
            Config config = new Config(currentConfig);
            config.write();
        } else getConfig(currentConfig).write();
    }

    public static Config getConfig(String name) {
        return configs.keySet().stream().filter(key -> key.equalsIgnoreCase(name)).findFirst().map(configs::get).orElse(null);
    }

    public static Set<String> getConfigList() {
        return configs.keySet();
    }

    public static void saveConfig(String configName){
        if (configName == "default") {
            PrintUtil.message("Cannot save config as 'default'.");
            return;
        }
        try {
            if (getConfig(configName) == null) {
                Config config = new Config(configName);
                config.write();
            } else getConfig(configName).write();
        } catch (Exception e) {
            PrintUtil.message("Failed to save config.");
            PrintUtil.message(e.getMessage());
            return;
        }

        PrintUtil.message("Saved config " + configName + ".");
    }

    public static boolean delete(String configName) {
        Config existingConfig = getConfig(configName);

        if (configName == "default" || configName == currentConfig || existingConfig == null) {
            PrintUtil.message("Cannot delete config: " + configName + ".");
            return false;
        }

        File configFile = new File(existingConfig.getDirectory().toString() + "/" + existingConfig.getName() + ".json");

        if (configFile.exists()) {
            boolean deleted = configFile.delete();
            if (!deleted) {
                PrintUtil.message("Error: Unable to delete the config file");
                return false;
            }
        }
        return true;
    }

    public static void loadConfig(String configName) {
        refresh();

        if (getConfig(configName) != null) {
            PrintUtil.message("Loaded config " + configName + ".");
            getConfig(configName).read();
        } else {
            PrintUtil.message("Failed to load config.");
        }
    }
}
