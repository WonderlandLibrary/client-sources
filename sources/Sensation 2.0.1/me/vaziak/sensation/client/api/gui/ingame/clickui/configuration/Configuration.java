package me.vaziak.sensation.client.api.gui.ingame.clickui.configuration;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author antja03
 */
public class Configuration {

    //The name of the configuration
    private String identifier;

    //The configuration file
    private final File configurationFile;

    //A list of cheat data
    private final ArrayList<CheatData> cheatData;

    //A list of hud element data
    private final ArrayList<HudElementData> hudElementData;

    //Whether or not the config should be automatically loaded on startup
    private boolean isDefault;

    public Configuration(File configurationFile, boolean refresh) {
        this.identifier = "";
        this.configurationFile = configurationFile;
        this.cheatData = new ArrayList<>();
        this.hudElementData = new ArrayList<>();
        this.isDefault = false;
        try {
            loadFile(refresh);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Configuration(String identifier) {
        this.identifier = identifier;
        this.configurationFile = new File(ConfigurationManager.CONFIGURATION_DIR + File.separator + identifier + ".conf");
        this.cheatData = new ArrayList<>();
        this.hudElementData = new ArrayList<>();

        this.isDefault = false;
        save();
    }

    /**
     * Reads the current settings and saves them into CheatData objects
     * which can later be loaded by calling load()
     */
    public void save() {
        Sensation.instance.cheatManager.getCheatRegistry().values()
                .forEach(cheat -> cheatData.add(new CheatData(cheat)));

        Sensation.instance.hud.getElementRegistry().values()
                .forEach(element -> hudElementData.add(new HudElementData(element)));

        saveFile();
    }

    /**
     * s
     * Writes the configuration data to the actual cheat objects
     * and overwrites previous settings
     */
    public void load() {
        cheatData.forEach(data -> {
            if (Sensation.instance.cheatManager.getCheatRegistry().containsKey(data.getName())) {
                Module cheat = Sensation.instance.cheatManager.getCheatRegistry().get(data.getName());
                cheat.setState(data.isState(), false);
                cheat.setBind(data.getBind());
                cheat.getPropertyRegistry().values().forEach(property -> {
                    if (data.getValueData().containsKey(property.getId())) {
                        property.setValue(data.getValueData().get(property.getId()));
                    }
                });
            }
        });

        hudElementData.forEach(data -> {
            if (Sensation.instance.hud.getElementRegistry().containsKey(data.getName())) {
                Element element = Sensation.instance.hud.getElementRegistry().get(data.getName());
                if (data.getQuadrant() != null)
                    element.setQuadrant(data.getQuadrant());

                if (data.getPositionX() != -1)
                    element.setPositionX(data.getPositionX());

                if (data.getPositionY() != -1)
                    element.setPositionY(data.getPositionY());

                element.getValueRegistry().values().forEach(value -> {
                    if (data.getValueData().containsKey(value.getId())) {
                        value.setValue(data.getValueData().get(value.getId()));
                    }
                });
            }
        });
    }

    public void saveFile() {
        try {
            if (!configurationFile.exists()) {
                configurationFile.createNewFile();
            }

            JsonObject configurationObject = new JsonObject();
            configurationObject.addProperty("default", isDefault);

            for (CheatData data : cheatData) {
                JsonObject cheatObject = new JsonObject();
                JsonObject valueObject = new JsonObject();
                data.getValueData().forEach(valueObject::addProperty);
                cheatObject.addProperty("state", data.isState());
                cheatObject.addProperty("bind", data.getBind());
                cheatObject.add("values", valueObject);
                configurationObject.add("cheat:" + data.getName(), cheatObject);
            }

            for (HudElementData data : hudElementData) {
                JsonObject hudElementObject = new JsonObject();
                JsonObject valueObject = new JsonObject();
                hudElementObject.addProperty("quadrant", data.getQuadrant().name());
                hudElementObject.addProperty("positionX", data.getPositionX());
                hudElementObject.addProperty("positionY", data.getPositionY());
                data.getValueData().forEach(valueObject::addProperty);
                hudElementObject.add("values", valueObject);
                configurationObject.add("hud_element:" + data.getName(), hudElementObject);
            }

            String jsonAsText = new GsonBuilder().create().toJson(configurationObject);

            FileWriter fileWriter = new FileWriter(configurationFile);
            fileWriter.write(jsonAsText);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!configurationFile.exists()) {
            try {
                configurationFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadFile(boolean refresh) {
        if (!configurationFile.getName().endsWith(".conf"))
            return;

        this.identifier = configurationFile.getName().replace(".conf", "");

        try {
            String text = FileUtils.readFileToString(configurationFile, Charset.defaultCharset());

            if (text.isEmpty())
                return;

            JsonElement configurationElement = new GsonBuilder().create().fromJson(text, JsonElement.class);

            if (configurationElement == null)
                return;

            if (!(configurationElement instanceof JsonObject))
                return;

            JsonObject configurationObject = configurationElement.getAsJsonObject();

            if (configurationObject.has("default")
                    && configurationObject.get("default").getAsBoolean())
                this.isDefault = true;

            for (Map.Entry<String, JsonElement> entry : configurationObject.entrySet()) {
                if (entry.getValue() instanceof JsonObject) {
                    if (!entry.getKey().startsWith("hud_element:")) {
                        JsonObject cheatObject = (JsonObject) entry.getValue();

                        HashMap<String, String> valueData = new HashMap<>();
                        if (cheatObject.has("values"))
                            cheatObject.getAsJsonObject("values").entrySet()
                                    .forEach(valueEntry -> valueData.put(valueEntry.getKey(), valueEntry.getValue().getAsString()));

                        cheatData.add(new CheatData(entry.getKey().replaceAll("cheat:", ""),
                                cheatObject.has("state") && cheatObject.get("state").getAsBoolean(),
                                cheatObject.has("bind") ? cheatObject.get("bind").getAsInt() : 0,
                                valueData));
                    } else {
                        JsonObject hudElementObject = (JsonObject) entry.getValue();

                        Quadrant quadrant = null;
                        if (hudElementObject.has("quadrant")) {
                            for (Quadrant q : Quadrant.values()) {
                                if (q.name().equalsIgnoreCase(hudElementObject.get("quadrant").getAsString())) {
                                    quadrant = q;
                                }
                            }
                        }

                        HashMap<String, String> valueData = new HashMap<>();
                        if (hudElementObject.has("values"))
                            hudElementObject.getAsJsonObject("values").entrySet()
                                    .forEach(valueEntry -> valueData.put(valueEntry.getKey(), valueEntry.getValue().getAsString()));

                        hudElementData.add(new HudElementData(entry.getKey().replaceAll("hud_element:", ""),
                                quadrant, hudElementObject.has("positionX") ? hudElementObject.get("positionX").getAsInt() : -1,
                                hudElementObject.has("positionY") ? hudElementObject.get("positionY").getAsInt() : -1, valueData));
                    }
                }
            }

            if (!refresh && this.isDefault)
                load();
        } catch (IOException | JsonSyntaxException e) {
            //Silently catch
        }
    }

    public void delete() {
        if (configurationFile.exists())
            configurationFile.delete();
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
