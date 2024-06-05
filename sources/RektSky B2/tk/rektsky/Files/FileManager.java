package tk.rektsky.Files;

import org.yaml.snakeyaml.*;
import tk.rektsky.*;
import tk.rektsky.Module.Settings.*;
import java.nio.charset.*;
import java.io.*;
import tk.rektsky.Module.*;
import java.util.*;
import tk.rektsky.Utils.*;

public class FileManager
{
    public static final File FILES_DIR;
    public static final String FILE_PATH = "rektsky/";
    public static final String CONFIG_PATH = "rektsky/configs/";
    public static final String SETTINGS_FILE = "rektsky/settings";
    public static Map<String, Object> config;
    
    public static void init() {
        FileManager.FILES_DIR.mkdirs();
        generateConfig();
    }
    
    public static InputStream getFile(final String pathWithoutSlash) {
        final File file = new File("rektsky/" + pathWithoutSlash);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            try {
                inputStream = new FileInputStream(file);
            }
            catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
        return inputStream;
    }
    
    public static Object parseYAML(final String pathWithoutSlash) {
        final Yaml yaml = new Yaml();
        final InputStream file = getFile(pathWithoutSlash);
        return yaml.load(file);
    }
    
    public static void saveSettings() {
        replaceAndSaveSettings();
        RektLogger.log("Saving Settings...");
    }
    
    private static void generateConfig() {
        generateConfigYaml();
    }
    
    public static void getConfig() {
    }
    
    public static void replaceAndSaveSettings() {
        final File file = new File("rektsky/settings.yml");
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            final Yaml yaml = new Yaml();
            final Map<String, Object> data = new HashMap<String, Object>();
            for (final Module m : ModulesManager.getModules()) {
                final Map<String, Object> module = new HashMap<String, Object>();
                final Map<String, Object> settings = new HashMap<String, Object>();
                module.put("enabled", m.isToggled());
                for (final Setting s : m.settings) {
                    settings.put(s.name, s.getValue());
                }
                module.put("settings", settings);
                module.put("key", m.keyCode);
                data.put(m.name, module);
            }
            out.write(yaml.dump(data).getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void generateConfigYaml() {
        final File file = new File("rektsky/settings.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                replaceAndSaveSettings();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            for (final YamlUtil.ConfiguredModule module : YamlUtil.getModuleSetting()) {
                ModulesManager.loadModuleSetting(module);
            }
        }
    }
    
    static {
        FILES_DIR = new File("rektsky/");
    }
}
