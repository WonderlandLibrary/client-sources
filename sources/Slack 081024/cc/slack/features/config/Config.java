package cc.slack.features.config;

import cc.slack.start.Slack;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.*;
import cc.slack.utils.client.IMinecraft;
import cc.slack.utils.other.FileUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.File;
import cc.slack.features.modules.api.Module;
import java.util.Map;

@Getter
@Setter
public class Config implements IMinecraft {

    private final File directory = new File(Minecraft.getMinecraft().mcDataDir, "/" + "SlackClient" + "/configs");

    private final String name;

    public Config(String name) {
        this.name = name;
    }

    public void write() {
        JsonObject jsonObject = new JsonObject();

        for (Module module : Slack.getInstance().getModuleManager().getModules()) {
            JsonObject moduleJson = new JsonObject();

            moduleJson.addProperty("state", module.isToggle());
            moduleJson.addProperty("bind", module.getKey());
            moduleJson.addProperty("render", module.render);

            JsonObject valueJson = new JsonObject();

            module.getSetting().forEach(property -> {
                if (property instanceof BooleanValue)
                    valueJson.addProperty(property.getName(), ((BooleanValue) property).getValue());

                if (property instanceof ModeValue)
                    valueJson.addProperty(property.getName(), ((ModeValue) property).getIndex());

                if (property instanceof NumberValue) {
                    if (((NumberValue) property).getMinimum() instanceof Integer) {
                        valueJson.addProperty(property.getName(), (Integer) (property.getValue()));
                    }
                    if (((NumberValue) property).getMinimum() instanceof Float) {
                        valueJson.addProperty(property.getName(), (Float) (property.getValue()));
                    }
                    if (((NumberValue) property).getMinimum() instanceof Double) {
                        valueJson.addProperty(property.getName(), (Double) (property.getValue()));
                    }
                    if (((NumberValue) property).getMinimum() instanceof Long) {
                        valueJson.addProperty(property.getName(), (Long) (property.getValue()));
                    }
                }

                if (property instanceof ColorValue) {
                    valueJson.addProperty(property.getName(),((ColorValue) property).getValue().getRGB());
                }

                if (property instanceof StringValue) {
                    valueJson.addProperty(property.getName(), ((StringValue) property).getValue());
                }

                if (property instanceof SubCatagory) {
                    valueJson.addProperty(property.getName(), ((SubCatagory) property).getValue());
                }
            });

            moduleJson.add("values", valueJson);
            jsonObject.add(module.getName(), moduleJson);
        }

        FileUtil.writeJsonToFile(jsonObject, new File(directory, name + ".json").getAbsolutePath());
    }
    public void read() {
        JsonObject config = FileUtil.readJsonFromFile(
                new File(directory, name + ".json"
                ).getAbsolutePath());
        configManager.currentConfig = name;
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            Slack.getInstance().getModuleManager().getModules().forEach(module -> {
                if (entry.getKey().equalsIgnoreCase(module.getName())) {
                    JsonObject json = (JsonObject) entry.getValue();

                    module.setToggle(json.get("state").getAsBoolean());
                    module.setKey(json.get("bind").getAsInt());
                    try {
                        module.render = json.get("render").getAsBoolean();
                    } catch (Exception e) {
                        module.render = true;
                    }

                    JsonObject values = json.get("values").getAsJsonObject();
                    for (Map.Entry<String, JsonElement> value : values.entrySet()) {
                        if (module.getValueByName(value.getKey()) != null) {
                            try {
                                Value v = module.getValueByName(value.getKey());

                                if (v instanceof BooleanValue)
                                    ((BooleanValue) v).setValue(value.getValue().getAsBoolean());

                                if (v instanceof ModeValue)
                                    ((ModeValue) v).setIndex(value.getValue().getAsInt());

                                if (v instanceof NumberValue) {
                                    if (((NumberValue) v).getMinimum() instanceof Integer)
                                        v.setValue(value.getValue().getAsInt());
                                    if (((NumberValue) v).getMinimum() instanceof Float)
                                        v.setValue(value.getValue().getAsFloat());
                                    if (((NumberValue) v).getMinimum() instanceof Double)
                                        v.setValue(value.getValue().getAsDouble());
                                    if (((NumberValue) v).getMinimum() instanceof Long)
                                        try {
                                            v.setValue(value.getValue().getAsLong());
                                        } catch (Exception e){
                                            v.setValue((int) value.getValue().getAsLong());
                                        }
                                }

                                if (v instanceof ColorValue) {
                                    v.setValue(new Color(value.getValue().getAsInt()));
                                }

                                if (v instanceof StringValue) {
                                    v.setValue(value.getValue().getAsString());
                                }

                                if (v instanceof SubCatagory) {
                                    v.setValue(value.getValue().getAsBoolean());
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