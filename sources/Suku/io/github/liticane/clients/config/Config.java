package io.github.liticane.clients.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.*;
import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.util.misc.FileUtil;
import io.github.liticane.clients.feature.property.Property;
import io.github.liticane.clients.Client;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Config implements IMethods {

    private final File directory = new File(mc.mcDataDir, "/" + Client.NAME + "/Configs");

    private final String name;

    public Config(String name) {
        this.name = name;
    }

    public void write() {
        JsonObject jsonObject = new JsonObject();

        Client.INSTANCE.getModuleManager().forEach(module -> {
            JsonObject mObject = new JsonObject();

            mObject.addProperty("state", module.isToggled());
            mObject.addProperty("bind", module.getKeyBind());

            JsonObject vObject = new JsonObject();

            module.getProperties().forEach(property -> {
                if (property instanceof BooleanProperty)
                    vObject.addProperty(property.name, ((BooleanProperty) property).isToggled());

                if (property instanceof StringProperty)
                    vObject.addProperty(property.name, ((StringProperty) property).getMode());

                if (property instanceof NumberProperty)
                    vObject.addProperty(property.name, ((NumberProperty) property).getValue());

                if (property instanceof ColorProperty) {
                    vObject.addProperty(property.name,((ColorProperty) property).getColor().getRGB());
                }

                if (property instanceof InputProperty) {
                    vObject.addProperty(property.name, ((InputProperty) property).getString());
                }
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

        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            Client.INSTANCE.getModuleManager().forEach(module -> {
                if (entry.getKey().equalsIgnoreCase(module.getName())) {
                    JsonObject json = (JsonObject) entry.getValue();

                    module.setToggled(json.get("state").getAsBoolean());
                    module.setKeyBind(json.get("bind").getAsInt());

                    JsonObject values = json.get("values").getAsJsonObject();
                    for (Map.Entry<String, JsonElement> value : values.entrySet()) {
                        if (module.getValueByName(value.getKey()) != null) {
                            try {
                                Property v = module.getValueByName(value.getKey());

                                if (v instanceof BooleanProperty)
                                    ((BooleanProperty) v).setToggled(value.getValue().getAsBoolean());

                                if (v instanceof StringProperty)
                                    ((StringProperty) v).setMode(value.getValue().getAsString());

                                if (v instanceof NumberProperty)
                                    ((NumberProperty) v).setValue(value.getValue().getAsDouble());

                                if (v instanceof ColorProperty) {
                                    ((ColorProperty) v).setColor(new Color(value.getValue().getAsInt()));
                                }
                                if (v instanceof InputProperty) {
                                    ((InputProperty) v).setString(value.getValue().getAsString());
                                }
                            } catch (Exception e) {
                                // Empty Catch Block
                            }
                        }
                    }
                }
            });
        }
    }
}