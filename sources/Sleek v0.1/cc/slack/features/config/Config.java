package cc.slack.features.config;

import cc.slack.Slack;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ColorValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.api.settings.impl.StringValue;
import cc.slack.features.modules.api.settings.impl.SubCatagory;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.FileUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public class Config extends mc {
   private final File directory;
   private final String name;

   public Config(String name) {
      this.directory = new File(Minecraft.getMinecraft().mcDataDir, "/SlackClient/configs");
      this.name = name;
   }

   public void write() {
      JsonObject jsonObject = new JsonObject();
      Iterator var2 = Slack.getInstance().getModuleManager().getModules().iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         JsonObject moduleJson = new JsonObject();
         moduleJson.addProperty("state", module.isToggle());
         moduleJson.addProperty("bind", module.getKey());
         JsonObject valueJson = new JsonObject();
         module.getSetting().forEach((property) -> {
            if (property instanceof BooleanValue) {
               valueJson.addProperty(property.getName(), (Boolean)((BooleanValue)property).getValue());
            }

            if (property instanceof ModeValue) {
               valueJson.addProperty(property.getName(), ((ModeValue)property).getIndex());
            }

            if (property instanceof NumberValue) {
               if (((NumberValue)property).getMinimum() instanceof Integer) {
                  valueJson.addProperty(property.getName(), (Integer)((Integer)property.getValue()));
               }

               if (((NumberValue)property).getMinimum() instanceof Float) {
                  valueJson.addProperty(property.getName(), (Float)((Float)property.getValue()));
               }

               if (((NumberValue)property).getMinimum() instanceof Double) {
                  valueJson.addProperty(property.getName(), (Double)((Double)property.getValue()));
               }
            }

            if (property instanceof ColorValue) {
               valueJson.addProperty(property.getName(), ((Color)((ColorValue)property).getValue()).getRGB());
            }

            if (property instanceof StringValue) {
               valueJson.addProperty(property.getName(), (String)((StringValue)property).getValue());
            }

            if (property instanceof SubCatagory) {
               valueJson.addProperty(property.getName(), (Boolean)((SubCatagory)property).getValue());
            }

         });
         moduleJson.add("values", valueJson);
         jsonObject.add(module.getName(), moduleJson);
      }

      FileUtil.writeJsonToFile(jsonObject, (new File(this.directory, this.name + ".json")).getAbsolutePath());
   }

   public void read() {
      JsonObject config = FileUtil.readJsonFromFile((new File(this.directory, this.name + ".json")).getAbsolutePath());
      configManager.currentConfig = this.name;
      Iterator var2 = config.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, JsonElement> entry = (Entry)var2.next();
         Slack.getInstance().getModuleManager().getModules().forEach((module) -> {
            if (((String)entry.getKey()).equalsIgnoreCase(module.getName())) {
               JsonObject json = (JsonObject)entry.getValue();
               module.setToggle(json.get("state").getAsBoolean());
               module.setKey(json.get("bind").getAsInt());
               JsonObject values = json.get("values").getAsJsonObject();
               Iterator var4 = values.entrySet().iterator();

               while(var4.hasNext()) {
                  Entry<String, JsonElement> value = (Entry)var4.next();
                  if (module.getValueByName((String)value.getKey()) != null) {
                     try {
                        Value v = module.getValueByName((String)value.getKey());
                        if (v instanceof BooleanValue) {
                           ((BooleanValue)v).setValue(((JsonElement)value.getValue()).getAsBoolean());
                        }

                        if (v instanceof ModeValue) {
                           ((ModeValue)v).setIndex(((JsonElement)value.getValue()).getAsInt());
                        }

                        if (v instanceof NumberValue) {
                           if (((NumberValue)v).getMinimum() instanceof Integer) {
                              v.setValue(((JsonElement)value.getValue()).getAsInt());
                           }

                           if (((NumberValue)v).getMinimum() instanceof Float) {
                              v.setValue(((JsonElement)value.getValue()).getAsFloat());
                           }

                           if (((NumberValue)v).getMinimum() instanceof Double) {
                              v.setValue(((JsonElement)value.getValue()).getAsDouble());
                           }
                        }

                        if (v instanceof ColorValue) {
                           v.setValue(new Color(((JsonElement)value.getValue()).getAsInt()));
                        }

                        if (v instanceof StringValue) {
                           v.setValue(((JsonElement)value.getValue()).getAsString());
                        }

                        if (v instanceof SubCatagory) {
                           v.setValue(((JsonElement)value.getValue()).getAsBoolean());
                        }
                     } catch (Exception var7) {
                     }
                  }
               }
            }

         });
      }

   }

   public File getDirectory() {
      return this.directory;
   }

   public String getName() {
      return this.name;
   }
}
