package client.metaware.api.config;

import client.metaware.Metaware;
import client.metaware.api.module.api.Module;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.api.properties.property.impl.HueProperty;
import client.metaware.impl.managers.ConfigManager;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        Metaware.INSTANCE.getModuleManager().getModules().forEach(module -> {
            JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("Keybind", module.getKey());
            moduleObject.addProperty("Enabled", module.isToggled());
            JsonObject settingsObject = new JsonObject();
            Module.getPropertyRepository().propertiesBy(module.getClass()).forEach(property -> {
                if (property instanceof DoubleProperty)
                    settingsObject.addProperty(property.label(), ((DoubleProperty) property).getValue());
                if (property instanceof HueProperty)
                    settingsObject.addProperty(property.label(), ((HueProperty) property).getValue());
                if (property.getValue() instanceof Enum)
                    settingsObject.addProperty(property.label(), String.valueOf(property.getValue()));
                if (property.getValue() instanceof Boolean)
                    settingsObject.addProperty(property.label(), ((Property<Boolean>) property).getValue());
            });
            moduleObject.add("Settings", settingsObject);
            jsonObject.add(module.getName(), moduleObject);
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(ConfigManager.CONFIG_DIRECTORY + "/" + name + ".json");
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
            FileReader reader = new FileReader(ConfigManager.CONFIG_DIRECTORY + "/" + name + ".json");
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            reader.close();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Module module = Metaware.INSTANCE.getModuleManager().getModuleByName(entry.getKey());
                final JsonObject jsonModule = (JsonObject) entry.getValue();
                if (module.isToggled() != jsonModule.get("Enabled").getAsBoolean()) {
                    module.toggled(jsonModule.get("Enabled").getAsBoolean());
                }
                module.setKey(jsonModule.get("Keybind").getAsInt());
                JsonObject settingObject = jsonModule.get("Settings").getAsJsonObject();

                for(Map.Entry<String, JsonElement> setting : settingObject.entrySet()) {
                    Property property = Module.getPropertyRepository().propertyBy(module.getClass(), setting.getKey());
                    if (property != null) {
                        if (property instanceof DoubleProperty)
                            property.setValue(setting.getValue().getAsDouble());
                        if (property instanceof EnumProperty)
                            property.setValue(Enum.valueOf(property.type(), setting.getValue().getAsString()));
                        if (property.getValue() instanceof Boolean)
                            property.setValue(setting.getValue().getAsBoolean());
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
            Files.delete(Paths.get(ConfigManager.CONFIG_DIRECTORY + "/" + name + ".json"));
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
