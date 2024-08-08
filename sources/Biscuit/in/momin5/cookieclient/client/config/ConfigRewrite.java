/*package in.momin5.cookieclient.client.config;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.Setting;
import in.momin5.cookieclient.api.setting.SettingManager;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigRewrite {

    public static String mainFolder = CookieClient.MOD_NAME.toLowerCase() + "/";
    public static String configFolder = mainFolder + "config/";

    public static String modulesFile = configFolder + "modules.json";
    public static String GUIFile = configFolder + "gui.json";
    public static String HUDFile = configFolder + "hud.json";

    CopyOnWriteArrayList<ConcurrentLinkedQueue<JSONObject>> concurrentLinkedQueueCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public ConfigRewrite() {
        initialize();
    }

    public static void initialize() {
        try {
            createFolders();
            createFiles();
        } catch (IOException exception) { exception.printStackTrace(); }
    }
    public static void createFolders() throws IOException {
        if (!Files.exists(Paths.get(mainFolder))) Files.createDirectory(Paths.get(mainFolder));
        if (!Files.exists(Paths.get(configFolder))) Files.createDirectory(Paths.get(configFolder));
    }
    public static void createFiles() throws IOException {
        if (!Files.exists(Paths.get(modulesFile))) Files.createFile(Paths.get(modulesFile));
        if (!Files.exists(Paths.get(GUIFile))) Files.createFile(Paths.get(GUIFile));
        if (!Files.exists(Paths.get(HUDFile))) Files.createFile(Paths.get(HUDFile));
    }

    public static void save() {
        saveModules();
        saveGUI();
        saveHUD();
    }
    public static void load() {
        try {
            loadModules();
            loadGUI();
            loadHUD();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveModules() {

        JSONArray modulesArray = new JSONArray();

        for (Module module : ModuleManager.getModules()) {

            JSONObject moduleObj = new JSONObject(new HashMap<String, Object>());
            JSONArray settingsArr = new JSONArray();

            moduleObj.put("name", module.getName());
            moduleObj.put("bind", module.getBind());
            moduleObj.put("enabled", module.isEnabled());


            for (Setting setting : module.getSettings()) {
                JSONObject settingObj = new JSONObject();
                if (setting instanceof SettingBoolean) {

                    SettingBoolean set = (SettingBoolean) setting;

                    settingObj.put("name", set.name);
                    settingObj.put("value", set.isEnabled());
                    settingsArr.add(settingObj);
                }

                if (setting instanceof SettingNumber) {

                    SettingNumber set = (SettingNumber) setting;

                    settingObj.put("name", set.name);
                    settingObj.put("value", set.getValue());
                    settingObj.put("max", set.getMaximumValue());
                    settingObj.put("min", set.getMinimumValue());
                    settingObj.put("increment", set.getIncrement());
                    settingsArr.add(settingObj);
                }

                if (setting instanceof SettingMode) {

                    SettingMode set = (SettingMode) setting;

                    settingObj.put("name", set.name);
                    settingObj.put("value", set.getMode());
                    settingsArr.add(settingObj);
                }

                if (setting instanceof SettingColor) {

                    SettingColor set = (SettingColor) setting;

                    settingObj.put("name", set.name);
                    settingObj.put("value", set.toInteger());
                    settingsArr.add(settingObj);
                }

                CookieClient.LOGGER.info("awdapenis w");

            }
            moduleObj.put("settings", settingsArr);
            modulesArray.add(moduleObj);
        }

        try (FileWriter writer = new FileWriter(modulesFile)) { writer.write(modulesArray.toJSONString()); }
        catch (IOException exception) { exception.printStackTrace(); }

    }
    public static void saveGUI() {}
    public static void saveHUD() {}

    public static void loadModules() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(modulesFile);
        JSONArray modulesArray = (JSONArray) parser.parse(reader);
        for (Object object : modulesArray) {
            JSONObject moduleObject = (JSONObject) object;
            Module module = ModuleManager.getModuleInnit((String) moduleObject.get("name"));
            module.setBind((int) moduleObject.get("bind"));
            module.setEnabled((boolean) moduleObject.get("enabled"));
            JSONArray settingsArray = (JSONArray) parser.parse("settings");
            for (Object object1 : settingsArray) {
                JSONObject settingObject = (JSONObject) object1;

                for(Module m : ModuleManager.getModules()) {
                    if (SettingManager.getSettingsByMod(module) != null) {
                        for (Setting setting : SettingManager.getSettingsForModule(module)) {
                            if (setting instanceof SettingBoolean) {
                                ((SettingBoolean) setting).setEnabled(Boolean.parseBoolean("value"));
                            }

                            if (setting instanceof SettingNumber) {
                                ((SettingNumber) setting).setValue(Double.parseDouble("value"));
                            }

                            if(setting instanceof SettingMode){
                                ((SettingMode)setting).setMode("value");
                            }

                            if(setting instanceof SettingColor){
                                ((SettingColor)setting).fromInteger(Integer.parseInt("value"));
                            }

                        }
                    }
                }
            }
        }
        reader.close();
    }
    public static void loadGUI() throws IOException {}
    public static void loadHUD() throws IOException {}
}
*/