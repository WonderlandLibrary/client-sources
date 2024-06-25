package cc.slack.features.config;

import cc.slack.utils.other.PrintUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;

public class configManager {
   public static final Map<String, Config> configs = new HashMap();
   private static final File configFolder;
   private Config activeConfig;
   public static String currentConfig;

   private static void refresh() {
      File[] var0 = (File[])Objects.requireNonNull(configFolder.listFiles());
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         File file = var0[var2];
         if (file.isFile() && file.getName().endsWith(".json")) {
            String name = file.getName().replaceAll(".json", "");
            Config config = new Config(name);
            configs.put(config.getName(), config);
         }
      }

   }

   public static void init() {
      if (!configFolder.mkdirs()) {
         PrintUtil.print("Failed to make config folder");
      } else {
         refresh();
         if (getConfig("default") == null) {
            Config config = new Config("default");
            config.write();
            configs.put(config.getName(), config);
         } else {
            getConfig("default").read();
         }

      }
   }

   public static void stop() {
      if (getConfig(currentConfig) == null) {
         Config config = new Config(currentConfig);
         config.write();
      } else {
         getConfig(currentConfig).write();
      }

   }

   public static Config getConfig(String name) {
      Optional var10000 = configs.keySet().stream().filter((key) -> {
         return key.equalsIgnoreCase(name);
      }).findFirst();
      Map var10001 = configs;
      var10001.getClass();
      return (Config)var10000.map(var10001::get).orElse((Object)null);
   }

   public static Set<String> getConfigList() {
      return configs.keySet();
   }

   public static void saveConfig(String configName) {
      if (configName == "default") {
         PrintUtil.message("Cannot save config as 'default'.");
      } else {
         try {
            if (getConfig(configName) == null) {
               Config config = new Config(configName);
               config.write();
            } else {
               getConfig(configName).write();
            }
         } catch (Exception var2) {
            PrintUtil.message("Failed to save config.");
            PrintUtil.message(var2.getMessage());
         }

         PrintUtil.print("Saved config " + configName + ".");
      }
   }

   public static boolean delete(String configName) {
      Config existingConfig = getConfig(configName);
      if (configName != "default" && configName != currentConfig && existingConfig != null) {
         File configFile = new File(existingConfig.getDirectory().toString());
         if (configFile.exists()) {
            boolean deleted = configFile.delete();
            if (!deleted) {
               PrintUtil.message("Error: Unable to delete the config file");
               return false;
            }
         }

         return true;
      } else {
         PrintUtil.message("Cannot delete config: " + configName + ".");
         return false;
      }
   }

   public static void loadConfig(String configName) {
      refresh();
      if (getConfig(configName) != null) {
         PrintUtil.message("Loaded config " + configName + ".");
         getConfig(configName).read();
      } else {
         PrintUtil.message("Failed to load config.");
      }

   }

   public Config getActiveConfig() {
      return this.activeConfig;
   }

   static {
      configFolder = new File(Minecraft.getMinecraft().mcDataDir, "/SlackClient/configs");
      currentConfig = "default";
   }
}
