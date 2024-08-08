package com.example.editme.util.config;

import com.example.editme.modules.Module;
import com.example.editme.util.module.ModuleManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationUtil {
   public static boolean isFilenameValid(String var0) {
      File var1 = new File(var0);

      try {
         var1.getCanonicalPath();
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public static void saveConfigurationUnsafe() throws IOException {
      Path var0 = Paths.get(getConfigName());
      if (!Files.exists(var0, new LinkOption[0])) {
         Files.createFile(var0);
      }

      ConfigurationProducer.saveConfiguration(var0);
      ModuleManager.getModules().forEach(Module::destroy);
   }

   public static String getConfigName() {
      return "EDITMEConfig.json";
   }

   public static void loadConfiguration() {
      try {
         loadConfigurationUnsafe();
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static void loadConfigurationUnsafe() throws IOException {
      String var0 = getConfigName();
      Path var1 = Paths.get(var0);
      if (Files.exists(var1, new LinkOption[0])) {
         ConfigurationProducer.loadConfiguration(var1);
      }
   }

   public static void saveConfiguration() {
      try {
         saveConfigurationUnsafe();
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }
}
