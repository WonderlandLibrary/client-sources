package dev.stephen.nexus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleManager;
import dev.stephen.nexus.module.setting.*;
import dev.stephen.nexus.module.setting.impl.*;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import dev.stephen.nexus.utils.mc.ChatFormatting;
import dev.stephen.nexus.utils.mc.ChatUtils;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/// DO NOT LOOK AT THIS SHIT PLEASE

public class ConfigManager {
    private final ModuleManager moduleManager = Client.INSTANCE.getModuleManager();
    private final Gson gson;

    @Getter
    private final File configDir = new File(MinecraftClient.getInstance().runDirectory, "Nexus" + File.separator + "configs");

    public ConfigManager() {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void loadConfig(String configName) {
        File configFile = new File(configDir, configName + ".json");

        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        if (configFile.exists()) {
            ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Config loaded: " + configName);
        } else {
            ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Config not found: " + configName);
            return;
        }

        resetConfig();

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            for (Module module : moduleManager.getModules()) {
                if (json.has(module.getName())) {
                    JsonObject moduleJson = json.getAsJsonObject(module.getName());

                    if (moduleJson.has("enabled")) {
                        boolean enabled = moduleJson.get("enabled").getAsBoolean();
                        module.setEnabled(enabled);
                    }
                    if (moduleJson.has("bind")) {
                        int bind = moduleJson.get("bind").getAsInt();
                        module.setKey(bind);
                    }
                    for (Setting setting : module.getSettings()) {
                        if (setting instanceof BooleanSetting booleanSetting) {
                            if (moduleJson.has(setting.getName())) {
                                booleanSetting.setValue(moduleJson.get(setting.getName()).getAsBoolean());
                            }
                        } else if (setting instanceof NumberSetting numberSetting) {
                            if (moduleJson.has(setting.getName())) {
                                numberSetting.setValue(moduleJson.get(setting.getName()).getAsDouble());
                            }
                        } else if (setting instanceof ModeSetting modeSetting) {
                            if (moduleJson.has(setting.getName())) {
                                if (modeSetting.getModes().contains(moduleJson.get(setting.getName()).getAsString())) {
                                    modeSetting.setMode(moduleJson.get(setting.getName()).getAsString());
                                } else {
                                    modeSetting.setMode(modeSetting.getModes().getFirst());
                                }
                            }
                        } else if (setting instanceof NewModeSetting modeSetting) {
                            if (moduleJson.has(setting.getName())) {
                                if (modeSetting.getModeNames().contains(moduleJson.get(setting.getName()).getAsString())) {
                                    modeSetting.setMode(moduleJson.get(setting.getName()).getAsString());
                                } else {
                                    modeSetting.setMode(modeSetting.getModeNames().getFirst());
                                }
                            }
                        } else if (setting instanceof MultiModeSetting multiModeSetting) {
                            if (moduleJson.has(setting.getName())) {
                                JsonArray modesArray = moduleJson.getAsJsonArray(setting.getName());
                                Set<String> selectedModes = new HashSet<>();
                                for (int i = 0; i < modesArray.size(); i++) {
                                    String mode = modesArray.get(i).getAsString();
                                    if (multiModeSetting.getModes().contains(mode)) {
                                        selectedModes.add(mode);
                                    }
                                }
                                multiModeSetting.setSelsectedModes(selectedModes);
                            }
                        } else if (setting instanceof RangeSetting rangeSetting) {
                            if (moduleJson.has(setting.getName())) {
                                JsonObject rangeJson = moduleJson.getAsJsonObject(setting.getName());
                                rangeSetting.setValueMax(rangeJson.get("max").getAsDouble());
                                rangeSetting.setValueMin(rangeJson.get("min").getAsDouble());
                            }
                        } else if (setting instanceof StringSetting stringSetting) {
                            if (moduleJson.has(setting.getName())) {
                                stringSetting.setValue(moduleJson.get(stringSetting.getName()).getAsString());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(String configName) {
        File configFile = new File(configDir, configName + ".json");

        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        if (configFile.exists()) {
            try {
                configFile.delete();
                configFile.createNewFile();
                ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Config saved: " + configName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                configFile.createNewFile();
                ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Config created: " + configName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JsonObject json = new JsonObject();

        for (Module module : moduleManager.getModules()) {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("enabled", module.isEnabled());
            moduleJson.addProperty("bind", module.getKey());
            for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting booleanSetting) {
                    moduleJson.addProperty(setting.getName(), booleanSetting.getValue());
                } else if (setting instanceof NumberSetting numberSetting) {
                    moduleJson.addProperty(setting.getName(), numberSetting.getValue());
                } else if (setting instanceof ModeSetting modeSetting) {
                    moduleJson.addProperty(setting.getName(), modeSetting.getMode());
                } else if (setting instanceof NewModeSetting modeSetting) {
                    moduleJson.addProperty(setting.getName(), modeSetting.getCurrentMode().getName());
                } else if (setting instanceof MultiModeSetting multiModeSetting) {
                    JsonArray modesArray = new JsonArray();
                    for (String mode : multiModeSetting.getSelectedModes()) {
                        modesArray.add(mode);
                    }
                    moduleJson.add(setting.getName(), modesArray);
                } else if (setting instanceof RangeSetting rangeSetting) {
                    JsonObject rangeJson = new JsonObject();
                    rangeJson.addProperty("max", rangeSetting.getValueMax());
                    rangeJson.addProperty("min", rangeSetting.getValueMin());
                    moduleJson.add(setting.getName(), rangeJson);
                } else if (setting instanceof StringSetting stringSetting) {
                    moduleJson.addProperty(setting.getName(), stringSetting.getValue());
                }
            }

            json.add(module.getName(), moduleJson);
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCloudConfig(String configName) {
        final String urlString = "https://raw.githubusercontent.com/StephenIsTaken/Nexus-CloudAPI/main/configs/" + configName + ".json";

        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);

                JsonObject json = gson.fromJson(reader, JsonObject.class);

                reader.close();

                resetConfig();

                for (Module module : moduleManager.getModules()) {
                    if (json.has(module.getName())) {
                        JsonObject moduleJson = json.getAsJsonObject(module.getName());

                        if (moduleJson.has("enabled")) {
                            boolean enabled = moduleJson.get("enabled").getAsBoolean();
                            module.setEnabled(enabled);
                        }
                        if (moduleJson.has("bind")) {
                            int bind = moduleJson.get("bind").getAsInt();
                            module.setKey(bind);
                        }
                        for (Setting setting : module.getSettings()) {
                            if (setting instanceof BooleanSetting booleanSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    booleanSetting.setValue(moduleJson.get(setting.getName()).getAsBoolean());
                                }
                            } else if (setting instanceof NumberSetting numberSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    numberSetting.setValue(moduleJson.get(setting.getName()).getAsDouble());
                                }
                            } else if (setting instanceof ModeSetting modeSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    if (modeSetting.getModes().contains(moduleJson.get(setting.getName()).getAsString())) {
                                        modeSetting.setMode(moduleJson.get(setting.getName()).getAsString());
                                    } else {
                                        modeSetting.setMode(modeSetting.getModes().getFirst());
                                    }
                                }
                            } else if (setting instanceof NewModeSetting modeSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    if (modeSetting.getModeNames().contains(moduleJson.get(setting.getName()).getAsString())) {
                                        modeSetting.setMode(moduleJson.get(setting.getName()).getAsString());
                                    } else {
                                        modeSetting.setMode(modeSetting.getModeNames().getFirst());
                                    }
                                }
                            } else if (setting instanceof MultiModeSetting multiModeSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    JsonArray modesArray = moduleJson.getAsJsonArray(setting.getName());
                                    Set<String> selectedModes = new HashSet<>();
                                    for (int i = 0; i < modesArray.size(); i++) {
                                        String mode = modesArray.get(i).getAsString();
                                        if (multiModeSetting.getModes().contains(mode)) {
                                            selectedModes.add(mode);
                                        }
                                    }
                                    multiModeSetting.setSelsectedModes(selectedModes);
                                }
                            } else if (setting instanceof RangeSetting rangeSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    JsonObject rangeJson = moduleJson.getAsJsonObject(setting.getName());
                                    rangeSetting.setValueMax(rangeJson.get("max").getAsDouble());
                                    rangeSetting.setValueMin(rangeJson.get("min").getAsDouble());
                                }
                            } else if (setting instanceof StringSetting stringSetting) {
                                if (moduleJson.has(setting.getName())) {
                                    stringSetting.setValue(moduleJson.get(setting.getName()).getAsString());
                                }
                            }
                        }
                    }
                }
                ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Cloud config loaded: " + configName);
            } else {
                ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Failed to load cloud config: " + configName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ChatUtils.addMessageToChat(ChatFormatting.GRAY + "Error loading cloud config: " + configName);
        }
    }

    public String getCloudConfigList() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/StephenIsTaken/Nexus-CloudAPI/main/configs/configs.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public void resetConfig() {
        for (Module module : moduleManager.getModules()) {
            module.setEnabled(false);
            module.setKey(0);

            for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting booleanSetting) {
                    booleanSetting.setValue(false);
                }
                if (setting instanceof NumberSetting numberProperty) {
                    numberProperty.setValue(numberProperty.getMin());
                }
                if (setting instanceof RangeSetting rangeProperty) {
                    rangeProperty.setValueMin(rangeProperty.getValueMin());
                    rangeProperty.setValueMax(rangeProperty.getValueMax());
                }
                if (setting instanceof StringSetting stringSetting) {
                    stringSetting.setValue("");
                }
                if (setting instanceof ModeSetting modeProperty) {
                    modeProperty.setMode(modeProperty.getModes().getFirst());
                }
                if (setting instanceof NewModeSetting newModeSetting) {
                    newModeSetting.setMode(newModeSetting.getDefaultMode());
                }
                if (setting instanceof MultiModeSetting multiModeProperty) {
                    multiModeProperty.getSelectedModes().clear();
                }
            }
        }
    }
}