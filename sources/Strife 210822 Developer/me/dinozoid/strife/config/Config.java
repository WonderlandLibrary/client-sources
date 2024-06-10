package me.dinozoid.strife.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.*;
import org.lwjgl.Sys;

import javax.swing.plaf.synth.SynthUI;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public final class Config {
    private String name;

    public Config(String name) {
        this.name = name;
    }

    public boolean save() {
        JsonObject jsonObject = new JsonObject();
        StrifeClient.INSTANCE.moduleRepository().modules().forEach(module -> {
            JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("Keybind", module.key());
            moduleObject.addProperty("Enabled", module.toggled());
            moduleObject.addProperty("Hidden", module.hidden());
            JsonObject settingsObject = new JsonObject();
            Module.propertyRepository().propertiesBy(module.getClass()).forEach(property -> {
                if (property instanceof DoubleProperty)
                    settingsObject.addProperty(property.label(), ((DoubleProperty) property).value());
                if (property instanceof HueProperty)
                    settingsObject.addProperty(property.label(), ((HueProperty) property).value());
                if (property.value() instanceof Enum)
                    settingsObject.addProperty(property.label(), String.valueOf(property.value()));
                if (property.value() instanceof Boolean)
                    settingsObject.addProperty(property.label(), ((Property<Boolean>) property).value());
//                if(property instanceof MultiSelectEnumProperty) {
//                    JsonObject enumObject = new JsonObject();
//                    MultiSelectEnumProperty selectEnum = (MultiSelectEnumProperty) property;
//                    Arrays.stream(selectEnum.values()).filter(selectEnum::selected).forEach(anEnum -> enumObject.addProperty("", anEnum.toString()));
//                    settingsObject.add(property.label(), enumObject);
//                }
            });
            moduleObject.add("Settings", settingsObject);
            jsonObject.add(module.name(), moduleObject);
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(ConfigRepository.CONFIG_DIRECTORY + "/" + name + ".json");
            gson.toJson(jsonObject, fileWriter);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load() {
        try {
            FileReader reader = new FileReader(ConfigRepository.CONFIG_DIRECTORY + "/" + name + ".json");
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            reader.close();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Module module = StrifeClient.INSTANCE.moduleRepository().moduleBy(entry.getKey());
                final JsonObject jsonModule = (JsonObject) entry.getValue();
                if (module.toggled() != jsonModule.get("Enabled").getAsBoolean()) {
                    module.toggled(jsonModule.get("Enabled").getAsBoolean());
                }
                module.key(jsonModule.get("Keybind").getAsInt());
                module.hidden(jsonModule.get("Hidden").getAsBoolean());
                for(Map.Entry<String, JsonElement> setting : jsonModule.get("Settings").getAsJsonObject().entrySet()) {
                    Property property = Module.propertyRepository().propertyBy(module.getClass(), setting.getKey());
                    if (property != null) {
                        if (property instanceof DoubleProperty)
                            property.value(setting.getValue().getAsDouble());
                        if (property instanceof EnumProperty) {
                            property.value(Enum.valueOf(property.type(), setting.getValue().getAsString()));
                        }
                        if (property.value() instanceof Boolean)
                            property.value(setting.getValue().getAsBoolean());
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        try {
            Files.delete(Paths.get(ConfigRepository.CONFIG_DIRECTORY + "/" + name + ".json"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }
}
