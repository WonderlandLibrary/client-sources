package me.dinozoid.strife.config.settings;

import com.google.gson.*;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.Module;
import optfine.Json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class SettingsLoader {

    public void save() {
        JsonObject masterObject = new JsonObject();
        JsonObject settingsObject = new JsonObject();
        settingsObject.addProperty("Altening-API", Settings.ALTENING_API_KEY);
        settingsObject.addProperty("KingGen-API", Settings.KINGGEN_API_KEY);
        JsonObject keybindsObject = new JsonObject();
        for(Module module : StrifeClient.INSTANCE.moduleRepository().modules()) {
            keybindsObject.addProperty(module.name(), module.keybind());
        }
        masterObject.add("Settings", settingsObject);
        masterObject.add("Keybinds", keybindsObject);
        if (saveFile(masterObject)) System.out.println("Settings saved!");
        else System.out.println("Settings failed to save!");
    }

    public void load() {
        try {
            JsonObject object = new JsonParser().parse(new FileReader(StrifeClient.DIRECTORY + "/settings.json")).getAsJsonObject();
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
            FileWriter fileWriter = new FileWriter(StrifeClient.DIRECTORY + "/settings.json");
            gson.toJson(object, fileWriter);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
