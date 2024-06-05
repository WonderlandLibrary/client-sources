package hawk.config;

import com.lukflug.panelstudio.settings.KeybindSetting;
import hawk.Client;
import hawk.config.util.Configuration;
import hawk.config.util.ConfigurationAPI;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Config {
   public static boolean load() {
      try {
         Configuration var0 = ConfigurationAPI.loadExistingConfiguration(new File("HawkConfig.json"));
         Iterator var2 = Client.modules.iterator();

         while(var2.hasNext()) {
            Module var1 = (Module)var2.next();
            if (var0.get(var1.name) instanceof Boolean) {
               var1.setEnabled((Boolean)var0.get(var1.name));
            }

            Iterator var4 = var1.settings.iterator();

            while(var4.hasNext()) {
               Setting var3 = (Setting)var4.next();
               if (var3 instanceof BooleanSetting && var0.get(var3.name) instanceof Boolean) {
                  ((BooleanSetting)var3).setEnabled((Boolean)var0.get(var3.name));
               }

               if (var3 instanceof ModeSetting && var0.get(var3.name) instanceof String) {
                  ((ModeSetting)var3).setMode((String)var0.get(var3.name));
               }

               if (var3 instanceof NumberSetting && var0.get(var3.name) instanceof Double) {
                  ((NumberSetting)var3).setValue((Double)var0.get(var3.name));
               }

               if (var3 instanceof KeybindSetting && var0.get(String.valueOf((new StringBuilder(String.valueOf(var1.name))).append(" key"))) instanceof Integer) {
                  ((KeybindSetting)var3).setKey((Integer)var0.get(String.valueOf((new StringBuilder(String.valueOf(var1.name))).append(" key"))));
               }
            }
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return true;
   }

   public static void save() {
      Configuration var0 = ConfigurationAPI.newConfiguration(new File("HawkConfig.json"));
      Iterator var2 = Client.modules.iterator();

      while(var2.hasNext()) {
         Module var1 = (Module)var2.next();
         var0.set(var1.name, var1.toggled);
         Iterator var4 = var1.settings.iterator();

         while(var4.hasNext()) {
            Setting var3 = (Setting)var4.next();
            if (var3 instanceof BooleanSetting) {
               var0.set(var3.name, ((BooleanSetting)var3).isEnabled());
            }

            if (var3 instanceof ModeSetting) {
               var0.set(var3.name, ((ModeSetting)var3).getMode());
            }

            if (var3 instanceof NumberSetting) {
               var0.set(var3.name, ((NumberSetting)var3).getValue());
            }

            if (var3 instanceof KeybindSetting) {
               var0.set(String.valueOf((new StringBuilder(String.valueOf(var1.name))).append(" key")), ((KeybindSetting)var3).getKey());
            }
         }
      }

      try {
         var0.save();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
