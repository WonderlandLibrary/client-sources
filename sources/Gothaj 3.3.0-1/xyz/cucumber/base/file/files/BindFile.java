package xyz.cucumber.base.file.files;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.FileUtils;

public class BindFile extends FileUtils {
   public BindFile() {
      super("Gothaj", "binds.json");
   }

   @Override
   public void save() {
      try {
         JsonObject json = new JsonObject();

         for (Mod m : Client.INSTANCE.getModuleManager().getModules()) {
            JsonObject jsonMod = new JsonObject();
            jsonMod.addProperty("key", m.getKey());
            json.add(m.getName(), jsonMod);
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
            Mod mod = Client.INSTANCE.getModuleManager().getModule(entry.getKey());
            if (mod != null) {
               JsonObject module = (JsonObject)entry.getValue();
               mod.setKey(module.get("key").getAsInt());
            }
         }
      } catch (Exception var7) {
      }
   }
}
