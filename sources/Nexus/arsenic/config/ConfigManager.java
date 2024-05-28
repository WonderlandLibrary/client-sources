package arsenic.config;

import arsenic.main.Nexus;
import arsenic.utils.interfaces.ISerializable;
import arsenic.utils.java.FileUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class ConfigManager implements ISerializable {

    private final HashMap<String, ModuleConfig> configs = new HashMap<>();
    private ModuleConfig currentConfig;
    private ClientConfig clientConfig;
    private final File configDirectory = new File(FileUtils.getNexusFolderDirAsString() + File.separator + "Configs");

    public int initialize() {
        if (!configDirectory.isDirectory()) { configDirectory.mkdirs(); }
        System.out.println("initialized config manager");
        File clientConfigFile = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "Nexus", "clientConfig.json");
        clientConfig = new ClientConfig(clientConfigFile);

        reloadConfigs();
        if (!configs.isEmpty()) {
            clientConfig.loadConfig();
            if (currentConfig == null) { currentConfig = (ModuleConfig) configs.values().toArray()[0]; }
        } else {
            createConfig("default");
        }

        currentConfig.loadConfig();
        return configs.size();
    }

    public void reloadConfigs() {
        configs.clear();
        if (configDirectory.listFiles() == null || (Objects.requireNonNull(configDirectory.listFiles()).length == 0))
            return; // nothing to discover if there are no files in the directory

        for (File file : Objects.requireNonNull(configDirectory.listFiles())) {
            if (file.getName().endsWith(".json")) {
                ModuleConfig c = new ModuleConfig(file);
                configs.put(c.getName(), c);
            }
        }
    }

    public void createConfig(String name) {
        ModuleConfig config = new ModuleConfig(new File(configDirectory.getPath(), name + ".json"));
        configs.put(name, config);
        currentConfig = config;
    }

    public void loadConfig(String name) {
        currentConfig = configs.get(name);
        if (currentConfig == null) {
            Nexus.getNexus().getLogger().info("Config {} not found loading default config... ", name);
            if (!configs.containsKey("default")) { createConfig("default"); }
        }
        currentConfig.loadConfig();
        clientConfig.saveConfig();
    }

    public void deleteConfig(String name) {
        configs.remove(name).deleteConfig();
    }

    public void saveClientConfig() {
        clientConfig.saveConfig();
    }

    public ModuleConfig getCurrentConfig() { return currentConfig; }

    public Set<String> getConfigList() { return configs.keySet(); }

    public void saveConfig() { currentConfig.saveConfig(); }

    @Override
    public void loadFromJson(JsonObject obj) {
        loadConfig(obj.get("currentConfig").getAsString());
    }

    @Override
    public JsonObject saveInfoToJson(JsonObject obj) {
        obj.addProperty("currentConfig", currentConfig.getName());
        return obj;
    }

    @Override
    public String getJsonKey() {
        return "config";
    }

    // remember to only call this during events that the user can call eg closing
    // the clickgui, using commands etc.
    // if you don't then there is a potential of recursion

}
