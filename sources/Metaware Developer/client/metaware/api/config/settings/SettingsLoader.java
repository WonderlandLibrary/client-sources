package client.metaware.api.config.settings;

import client.metaware.Metaware;
import client.metaware.api.module.api.Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;

public final class SettingsLoader {

    public void save() {
        JsonObject masterObject = new JsonObject();
        JsonObject settingsObject = new JsonObject();
        settingsObject.addProperty("Altening-API", Settings.ALTENING_API_KEY);
        settingsObject.addProperty("KingGen-API", Settings.KINGGEN_API_KEY);
        JsonObject keybindsObject = new JsonObject();
        for(Module module : Metaware.INSTANCE.getModuleManager().getModules()) {
            keybindsObject.addProperty(module.getName(), module.getKey());
        }
        masterObject.add("Settings", settingsObject);
        masterObject.add("Keybinds", keybindsObject);
        if (saveFile(masterObject)) System.out.println("Settings saved!");
        else System.out.println("Settings failed to save!");
    }

    public void load() {
        try {
            JsonObject object = new JsonParser().parse(new FileReader(Metaware.INSTANCE.getDirectory() + "/settings.json")).getAsJsonObject();
            JsonObject settingsObject = object.get("Settings").getAsJsonObject();
            JsonObject keybindsObject = object.get("Keybinds").getAsJsonObject();
            Settings.ALTENING_API_KEY = settingsObject.get("Altening-API").getAsString();
            Settings.KINGGEN_API_KEY = settingsObject.get("KingGen-API").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean saveFile(JsonObject object) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(Metaware.INSTANCE.getDirectory() + "/settings.json");
            gson.toJson(object, fileWriter);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
