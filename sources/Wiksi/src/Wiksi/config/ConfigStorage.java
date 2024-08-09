package src.Wiksi.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigStorage {
    public final Logger logger = Logger.getLogger(ConfigStorage.class.getName());

    public final File CONFIG_DIR = new File(Minecraft.getInstance().gameDir, "\\Wiksi\\configs");
    public final File AUTOCFG_DIR = new File(CONFIG_DIR, "");
    public final File LAST_USED_CONFIG_FILE = new File(CONFIG_DIR, "last_used_config.txt");

    public final JsonParser jsonParser = new JsonParser();

    private String lastUsedConfig;

    public ConfigStorage() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                saveAllConfigurations();
                saveLastUsedConfig();
                logger.log(Level.INFO, "All configurations saved successfully.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to save configurations during shutdown.", e);
            }
        }));

        loadLastUsedConfig();
        if (lastUsedConfig != null) {
            loadConfiguration(lastUsedConfig);
        }
    }

    public void init() throws IOException {
        setupFolder();
    }

    public void setupFolder() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        } else if (AUTOCFG_DIR.exists()) {
            loadConfiguration("autocfg");
            logger.log(Level.SEVERE, "Load system configuration...");
        } else {
            logger.log(Level.SEVERE, "Creating system configuration...");
            try {
                AUTOCFG_DIR.createNewFile();
                logger.log(Level.SEVERE, "Created!");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create system configuration file", e);
            }
        }
    }

    public boolean isEmpty() {
        return getConfigs().isEmpty();
    }

    public List<Config> getConfigs() {
        List<Config> configs = new ArrayList<>();
        File[] configFiles = CONFIG_DIR.listFiles();

        if (configFiles != null) {
            for (File configFile : configFiles) {
                if (configFile.isFile() && configFile.getName().endsWith(".cfg")) {
                    String configName = configFile.getName().replace(".cfg", "");
                    Config config = findConfig(configName);
                    if (config != null) {
                        configs.add(config);
                    }
                }
            }
        }

        return configs;
    }

    public void loadConfiguration(String configuration) {
        lastUsedConfig = configuration;
        Config config = findConfig(configuration);
        try {
            FileReader reader = new FileReader(config.getFile());
            JsonObject object = (JsonObject) jsonParser.parse(reader);
            config.loadConfig(object);
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Not Found Exception", e);
        } catch (NullPointerException pointerException) {
            logger.log(Level.WARNING, "Fatal error in Config!", pointerException);
        }
    }

    public void saveConfiguration(String configuration) {
        Config config = new Config(configuration);
        String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson(config.saveConfig());
        try {
            FileWriter writer = new FileWriter(config.getFile());
            writer.write(contentPrettyPrint);
            writer.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "File not found!", e);
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Fatal Error in Config!", e);
        }
    }

    public void saveAllConfigurations() throws IOException {
        for (Config config : getConfigs()) {
            saveConfiguration(config.getName());
        }
    }

    public Config findConfig(String configName) {
        if (configName == null) return null;
        if (new File(CONFIG_DIR, configName + ".cfg").exists())
            return new Config(configName);
        return null;
    }

    private void saveLastUsedConfig() {
        try (FileWriter writer = new FileWriter(LAST_USED_CONFIG_FILE)) {
            writer.write(lastUsedConfig);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save last used configuration.", e);
        }
    }

    private void loadLastUsedConfig() {
        if (LAST_USED_CONFIG_FILE.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(LAST_USED_CONFIG_FILE))) {
                lastUsedConfig = reader.readLine();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load last used configuration.", e);
            }
        }
    }
}