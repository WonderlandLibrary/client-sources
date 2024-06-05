package net;

import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.ConfigManager;
import java.nio.file.Path;
import java.util.List;

public class apm {
   private static String[] b;

   public static String c() {
      return ConfigManager.getExtension();
   }

   public static Path c(ConfigManager var0) {
      return var0.getConfigsFolder();
   }

   public static boolean b(ConfigManager var0, String var1) {
      return var0.save(var1);
   }

   public static Path a(ConfigManager var0, String var1) {
      return var0.getConfigPath(var1);
   }

   public static void a(ConfigManager var0, Path var1, boolean var2) {
      var0.load(var1, var2);
   }

   public static ModuleManager b(ConfigManager var0) {
      return var0.getModuleManager();
   }

   public static List a(ConfigManager var0) {
      return var0.getConfigs();
   }

   public static void a(ConfigManager var0, String var1, boolean var2) {
      var0.load(var1, var2);
   }

   public static String d(ConfigManager var0, String var1) {
      return var0.saveToString(var1);
   }

   public static boolean c(ConfigManager var0, String var1) {
      return var0.delete(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
