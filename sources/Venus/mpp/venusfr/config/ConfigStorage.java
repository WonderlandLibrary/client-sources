/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mpp.venusfr.config.Config;
import mpp.venusfr.utils.drag.DragManager;
import net.minecraft.client.Minecraft;

public class ConfigStorage {
    public final Logger logger = Logger.getLogger(ConfigStorage.class.getName());
    public final File CONFIG_DIR;
    public final File AUTOCFG_DIR;
    public final File LAST_USED_CONFIG_FILE;
    public final JsonParser jsonParser;
    private String lastUsedConfig;

    public ConfigStorage() {
        this.CONFIG_DIR = new File(Minecraft.getInstance().gameDir, "\\venusfr\\configs");
        this.AUTOCFG_DIR = new File(this.CONFIG_DIR, "autocfg.cfg");
        this.LAST_USED_CONFIG_FILE = new File(this.CONFIG_DIR, "last_used_config.txt");
        this.jsonParser = new JsonParser();
        Runtime.getRuntime().addShutdownHook(new Thread(this::lambda$new$0));
        this.loadLastUsedConfig();
        if (this.lastUsedConfig != null) {
            this.loadConfiguration(this.lastUsedConfig);
        }
    }

    public void init() throws IOException {
        this.setupFolder();
    }

    public void setupFolder() {
        if (!this.CONFIG_DIR.exists()) {
            this.CONFIG_DIR.mkdirs();
        } else if (this.AUTOCFG_DIR.exists()) {
            this.loadConfiguration("autocfg");
            this.logger.log(Level.SEVERE, "Load system configuration...");
        } else {
            this.logger.log(Level.SEVERE, "Creating system configuration...");
            try {
                this.AUTOCFG_DIR.createNewFile();
                this.logger.log(Level.SEVERE, "Created!");
            } catch (IOException iOException) {
                this.logger.log(Level.SEVERE, "Failed to create system configuration file", iOException);
            }
        }
    }

    public boolean isEmpty() {
        return this.getConfigs().isEmpty();
    }

    public List<Config> getConfigs() {
        ArrayList<Config> arrayList = new ArrayList<Config>();
        File[] fileArray = this.CONFIG_DIR.listFiles();
        if (fileArray != null) {
            for (File file : fileArray) {
                String string;
                Config config;
                if (!file.isFile() || !file.getName().endsWith(".cfg") || (config = this.findConfig(string = file.getName().replace(".cfg", ""))) == null) continue;
                arrayList.add(config);
            }
        }
        return arrayList;
    }

    public void loadConfiguration(String string) {
        this.lastUsedConfig = string;
        Config config = this.findConfig(string);
        try {
            FileReader fileReader = new FileReader(config.getFile());
            JsonObject jsonObject = (JsonObject)this.jsonParser.parse(fileReader);
            config.loadConfig(jsonObject);
        } catch (FileNotFoundException fileNotFoundException) {
            this.logger.log(Level.WARNING, "Not Found Exception", fileNotFoundException);
        } catch (NullPointerException nullPointerException) {
            this.logger.log(Level.WARNING, "Fatal error in Config!", nullPointerException);
        }
    }

    public void saveConfiguration(String string) {
        Config config = new Config(string);
        String string2 = new GsonBuilder().setPrettyPrinting().create().toJson(config.saveConfig());
        try {
            FileWriter fileWriter = new FileWriter(config.getFile());
            fileWriter.write(string2);
            fileWriter.close();
        } catch (IOException iOException) {
            this.logger.log(Level.WARNING, "File not found!", iOException);
        } catch (NullPointerException nullPointerException) {
            this.logger.log(Level.WARNING, "Fatal Error in Config!", nullPointerException);
        }
    }

    public void saveAllConfigurations() throws IOException {
        for (Config config : this.getConfigs()) {
            this.saveConfiguration(config.getName());
        }
    }

    public Config findConfig(String string) {
        if (string == null) {
            return null;
        }
        if (new File(this.CONFIG_DIR, string + ".cfg").exists()) {
            return new Config(string);
        }
        return null;
    }

    private void saveLastUsedConfig() {
        try (FileWriter fileWriter = new FileWriter(this.LAST_USED_CONFIG_FILE);){
            fileWriter.write(this.lastUsedConfig);
        } catch (IOException iOException) {
            this.logger.log(Level.SEVERE, "Failed to save last used configuration.", iOException);
        }
    }

    private void loadLastUsedConfig() {
        if (this.LAST_USED_CONFIG_FILE.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.LAST_USED_CONFIG_FILE));){
                this.lastUsedConfig = bufferedReader.readLine();
            } catch (IOException iOException) {
                this.logger.log(Level.SEVERE, "Failed to load last used configuration.", iOException);
            }
        }
    }

    private void lambda$new$0() {
        try {
            this.saveAllConfigurations();
            this.saveLastUsedConfig();
            DragManager.save();
            this.logger.log(Level.INFO, "All configurations saved successfully.");
        } catch (IOException iOException) {
            this.logger.log(Level.SEVERE, "Failed to save configurations during shutdown.", iOException);
        }
    }
}

