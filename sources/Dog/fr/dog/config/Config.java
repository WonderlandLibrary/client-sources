package fr.dog.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.dog.Dog;
import fr.dog.property.Property;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ColorProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.theme.Theme;
import fr.dog.util.InstanceAccess;
import fr.dog.util.system.FileUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.util.Map;

@Getter
@Setter
public class Config implements InstanceAccess {

    private final File directory = new File(mc.mcDataDir, "/dog/configs");

    private final String name;

    public Config(String name) {
        this.name = name;
    }

    public void write() {
        JsonObject jsonObject = new JsonObject();
        JsonObject bObj = new JsonObject();
        bObj.addProperty("theme", Dog.getInstance().getThemeManager().getCurrentTheme().getName());
        jsonObject.add("info", bObj);
        Dog.getInstance().getModuleManager().getObjects().forEach(module -> {
            JsonObject mObject = new JsonObject();

            mObject.addProperty("enabled", module.isEnabled());
            mObject.addProperty("bind", module.getKeyBind());
            mObject.addProperty("CustomName", module.getCustomName());

            JsonObject vObject = new JsonObject();

            module.getPropertyList().forEach(property -> {
                if (property instanceof BooleanProperty booleanProperty)
                    vObject.addProperty(property.getLabel(), booleanProperty.getValue());
                if (property instanceof ModeProperty modeProperty)
                    vObject.addProperty(property.getLabel(), modeProperty.getIndex());
                if (property instanceof NumberProperty numberProperty)
                    vObject.addProperty(property.getLabel(), numberProperty.getValue());
                if (property instanceof ColorProperty colorProperty)
                    vObject.addProperty(property.getLabel(), colorProperty.getValue().getRGB());
            });

            mObject.add("values", vObject);
            jsonObject.add(module.getName(), mObject);
        });

        FileUtil.writeJsonToFile(jsonObject, new File(directory, name + ".json").getAbsolutePath());
    }

    public void read() {
        JsonObject config = FileUtil.readJsonFromFile(
                new File(directory, name + ".json"
                ).getAbsolutePath());

        assert config != null : "Config is undefined";
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            if(entry.getKey().equals("info")){
                try {
                    JsonObject json = (JsonObject) entry.getValue();
                    Theme theme = Dog.getInstance().getThemeManager().getThemeByName(json.get("theme").getAsString());
                    Dog.getInstance().getThemeManager().setCurrentTheme(theme);
                }catch (Exception ignored){

                }

            }
            Dog.getInstance().getModuleManager().getObjects().forEach(module -> {
                if (entry.getKey().equalsIgnoreCase(module.getName())) {
                    JsonObject json = (JsonObject) entry.getValue();

                    module.setEnabled(json.get("enabled").getAsBoolean());
                    module.setKeyBind(json.get("bind").getAsInt());


                    try {
                        module.setCustomName(json.get("CustomName").getAsString());
                    }catch (Exception ignored){
                        // old config
                    }

                    JsonObject values = json.get("values").getAsJsonObject();

                    for (Map.Entry<String, JsonElement> value : values.entrySet()) {
                        if (module.getValueByName(value.getKey()) != null) {
                            try {
                                Property<?> property = module.getValueByName(value.getKey());

                                if (property instanceof BooleanProperty booleanProperty)
                                    booleanProperty.setValue(value.getValue().getAsBoolean());
                                if (property instanceof ModeProperty modeProperty)
                                    modeProperty.setIndexValue(value.getValue().getAsInt());
                                if (property instanceof NumberProperty numberProperty)
                                    numberProperty.setValue(value.getValue().getAsFloat());
                                if (property instanceof ColorProperty colorProperty) {
                                    colorProperty.setValue(new Color(value.getValue().getAsInt()));
                                }
                            } catch (Exception ignored) { /* */ }
                        }
                    }
                }
            });
        }
    }
}