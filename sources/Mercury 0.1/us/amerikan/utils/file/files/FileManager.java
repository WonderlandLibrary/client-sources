/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils.file.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import us.amerikan.amerikan;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;

public class FileManager {
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    private static JsonParser jsonParser = new JsonParser();
    public final File MERCURY_DIR;
    private final File CONFIG;
    private final File info;

    public FileManager() {
        this.MERCURY_DIR = new File(String.format("%s%sMercury%s", Minecraft.getMinecraft().mcDataDir, File.separator, File.separator));
        this.CONFIG = new File(this.MERCURY_DIR, "config.json");
        this.info = new File(this.MERCURY_DIR, "info.json");
    }

    public void Initialization() {
        if (!this.MERCURY_DIR.exists()) {
            this.MERCURY_DIR.mkdir();
        }
        if (!this.CONFIG.exists()) {
            this.saveModules();
        } else {
            this.loadModules();
        }
    }

    public void loadModules() {
        try {
            BufferedReader loadJson = new BufferedReader(new FileReader(this.CONFIG));
            JsonObject moduleJason = (JsonObject)jsonParser.parse(loadJson);
            loadJson.close();
            for (Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                Module mods = ModuleManager.getModuleByName(entry.getKey());
                if (mods == null) continue;
                JsonObject jsonMod = (JsonObject)entry.getValue();
                boolean enabled = jsonMod.get("enabled").getAsBoolean();
                if (enabled) {
                    mods.toggle();
                }
                if (amerikan.setmgr.getSettingsByMod(mods) != null) {
                    for (Setting s2 : amerikan.setmgr.getSettingsByMod(mods)) {
                        if (s2.isCheck()) {
                            boolean bvalue = jsonMod.get(s2.getName()).getAsBoolean();
                            s2.setValBoolean(bvalue);
                            continue;
                        }
                        if (s2.isSlider()) {
                            double bvalue = jsonMod.get(s2.getName()).getAsDouble();
                            s2.setValDouble(bvalue);
                            continue;
                        }
                        if (!s2.isCombo()) continue;
                        String bvalue = jsonMod.get(s2.getName()).getAsString();
                        s2.setValString(bvalue);
                    }
                }
                mods.setKeybind(jsonMod.get("bind").getAsInt());
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void saveModules() {
        try {
            JsonObject json = new JsonObject();
            for (Module mod : amerikan.modulemanager.getModules()) {
                JsonObject jsonModules = new JsonObject();
                jsonModules.addProperty("enabled", mod.isEnabled());
                jsonModules.addProperty("bind", mod.getKeybind());
                if (amerikan.setmgr.getSettingsByMod(mod) != null) {
                    for (Setting s2 : amerikan.setmgr.getSettingsByMod(mod)) {
                        if (s2.isCheck()) {
                            jsonModules.addProperty(s2.getName(), s2.getValBoolean());
                            continue;
                        }
                        if (s2.isSlider()) {
                            jsonModules.addProperty(s2.getName(), s2.getValDouble());
                            continue;
                        }
                        if (!s2.isCombo()) continue;
                        jsonModules.addProperty(s2.getName(), s2.getValString());
                    }
                }
                json.add(mod.getName(), jsonModules);
            }
            PrintWriter saveJson = new PrintWriter(new FileWriter(this.CONFIG));
            saveJson.println(gsonPretty.toJson(json));
            saveJson.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}

