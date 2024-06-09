package io.github.liticane.clients.config;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.util.misc.ChatUtil;
import lombok.Getter;
import lombok.Setter;
import tv.twitch.chat.Chat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ConfigManager implements IMethods {

    private static final Map<String, Config> configs = new HashMap<>();

    private static final File configFolder = new File(mc.mcDataDir, "/" + Client.NAME + "/Configs");

    private Config activeConfig;

    private static void refresh() {
        for (File file : Objects.requireNonNull(configFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                String name = file.getName().replaceAll(".json", "");
                Config config = new Config(name);
                configs.put(config.getName(), config);
            }
        }
    }

    public void init() {
        configFolder.mkdirs();

        refresh();

        if (getConfig("default") == null) {
            Config config = new Config("default");
            config.write();
            configs.put(config.getName(), config);
        } else getConfig("default").read();
    }

    public void stop() {
        if (getConfig("default") == null) {
            Config config = new Config("default");
            config.write();
        } else getConfig("default").write();
    }

    public static Config getConfig(String name) {
        return configs.keySet().stream().filter(key -> key.equalsIgnoreCase(name)).findFirst().map(configs::get).orElse(null);
    }

    public static void saveConfig(String configName){
        if (getConfig(configName) == null) {
            Config config = new Config(configName);
            config.write();
        } else getConfig(configName).write();

        ChatUtil.display("Saved config " + configName + ".");
    }

    public static void loadConfig(String configName) {
        refresh();

        if (getConfig(configName) != null) {
            ChatUtil.display("Loaded config " + configName + ".");
            getConfig(configName).read();
        } else {
            ChatUtil.display("Failed to load config.");
        }
    }
}