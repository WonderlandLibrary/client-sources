package xyz.cucumber.base.file.files;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.clientsettings.ext.Setting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BloomSetting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BlurSetting;
import xyz.cucumber.base.utils.FileUtils;

public class ClientSettingsFile extends FileUtils {
   public ClientSettingsFile() {
      super("Gothaj", "client-settings.json");
   }

   @Override
   public void save() {
      try {
         JsonObject json = new JsonObject();

         for (Setting s : Client.INSTANCE.getClientSettings().getSettings()) {
            JsonObject jsonMod = new JsonObject();
            if (s instanceof BloomSetting) {
               jsonMod.addProperty("compression", ((BloomSetting)s).compression.getValue());
               jsonMod.addProperty("radius", ((BloomSetting)s).radius.getValue());
               jsonMod.addProperty("saturation", ((BloomSetting)s).saturation.getValue());
            }

            if (s instanceof BlurSetting) {
               jsonMod.addProperty("radius", ((BlurSetting)s).radius.getValue());
            }

            json.add(s.getName(), jsonMod);
         }

         PrintWriter save = new PrintWriter(new FileWriter(this.getFile()));
         save.println(prettyGson.toJson(json));
         save.close();
      } catch (Exception var5) {
      }
   }

   @Override
   public void load() {
      try {
         BufferedReader load = new BufferedReader(new FileReader(this.getFile()));
         JsonObject json = (JsonObject)jsonParser.parse(load);
         load.close();

         for (Entry<String, JsonElement> entry : json.entrySet()) {
            JsonObject module = (JsonObject)entry.getValue();
            Setting settingName = Client.INSTANCE.getClientSettings().getSettingByName(entry.getKey());
            if (settingName != null) {
               for (Setting s : Client.INSTANCE.getClientSettings().getSettings()) {
                  if (s instanceof BloomSetting) {
                     BloomSetting setting = (BloomSetting)s;
                     setting.compression.setValue((double)module.get("compression").getAsInt());
                     setting.radius.setValue((double)module.get("radius").getAsInt());
                     setting.saturation.setValue((double)module.get("saturation").getAsInt());
                  }

                  if (s instanceof BlurSetting) {
                     BlurSetting setting = (BlurSetting)s;
                     setting.radius.setValue((double)module.get("radius").getAsInt());
                  }
               }
            }
         }
      } catch (Exception var10) {
      }
   }
}
