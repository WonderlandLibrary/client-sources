package me.kaimson.melonclient.config;

import me.kaimson.melonclient.config.utils.*;
import java.io.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import java.util.*;
import me.kaimson.melonclient.*;
import com.google.gson.*;

public class GeneralConfig extends Config
{
    public static final GeneralConfig INSTANCE;
    
    public GeneralConfig() {
        super("general", "json", 0.1);
    }
    
    @Override
    public void saveConfig() {
        this.createStructure();
        final JsonObject configFileJson = new JsonObject();
        configFileJson.addProperty("version", this.version);
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
            try {
                for (final Setting setting : SettingsManager.INSTANCE.settings) {
                    if (!setting.hasValue()) {
                        continue;
                    }
                    SettingWrapper.addSettingKey(configFileJson, setting, setting.getObject());
                }
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(configFileJson.toString())));
            }
            finally {
                if (Collections.singletonList(writer).get(0) != null) {
                    writer.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void loadConfig() {
        this.createStructure();
        final JsonObject configFileJson = this.loadJsonFile(this.configFile);
        this.getNonNull(configFileJson, "version", jsonElement -> Client.info("Detected " + this.name + " version: " + jsonElement.getAsDouble() + " => " + this.version, new Object[0]));
        for (final Setting setting : SettingsManager.INSTANCE.settings) {
            if (!setting.hasValue()) {
                continue;
            }
            this.getNonNull(configFileJson, setting.getKey(), jsonElement -> SettingWrapper.setValue(setting, jsonElement));
        }
    }
    
    static {
        INSTANCE = new GeneralConfig();
    }
}
