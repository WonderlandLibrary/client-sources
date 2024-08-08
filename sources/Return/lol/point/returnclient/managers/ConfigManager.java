package lol.point.returnclient.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lol.point.Return;
import lol.point.returnclient.util.client.OnlineConfig;
import lol.point.returnclient.util.system.FileUtil;
import lol.point.returnclient.util.system.NetworkUtil;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    public static final String DEFAULT_CONFIG_NAME = "default.json";
    public static final String DEFAULT_KEYBIND_CONFIG_NAME = "keybinds.json";

    @Getter
    private List<OnlineConfig> onlineConfigsList;

    public ConfigManager() {
        onlineConfigsList = getOnlineConfigs();
    }

    public void saveConfig(String name, String directory, JsonObject configData) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String newName = name.contains(".json") ? name : name + ".json";
        FileWriter writer = new FileWriter(directory + "/" + newName);
        gson.toJson(configData, writer);
        writer.close();
    }

    public void saveDefault(JsonObject configData) throws IOException {
        saveConfig(DEFAULT_CONFIG_NAME, "return", configData);
    }

    public void saveKeybinds(JsonObject configData) throws IOException {
        saveConfig(DEFAULT_KEYBIND_CONFIG_NAME, "return", configData);
    }

    public JsonObject loadConfig(String name, String directory) throws IOException {
        try (FileReader reader = new FileReader(directory + "/" + name)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    public JsonObject loadDefault() throws IOException {
        return loadConfig(DEFAULT_CONFIG_NAME, "return");
    }

    public JsonObject loadKeybinds() throws IOException {
        return loadConfig(DEFAULT_KEYBIND_CONFIG_NAME, "return");
    }

    public List<String> getAllConfigs() {
        List<String> configNames = new ArrayList<>();
        File configsDirectory = new File("return/configs");

        if (configsDirectory.exists() && configsDirectory.isDirectory()) {
            File[] configFiles = configsDirectory.listFiles();

            if (configFiles != null) {
                for (File configFile : configFiles) {
                    if (configFile.isFile()) {
                        configNames.add(configFile.getName());
                    }
                }
            }
        }

        return configNames;
    }

    private List<OnlineConfig> getOnlineConfigs() {
        List<OnlineConfig> configs = new ArrayList<>();
        try {
            String raw = NetworkUtil.raw("https://raw.githubusercontent.com/Return-Client/cloud/main/configs/config-list");
            String[] lines = raw.split("--");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String date = parts[1].trim();
                    String author = parts[2].trim();
                    String version = parts[3].trim();
                    configs.add(new OnlineConfig(name, author, date, version));
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return configs;
    }

}