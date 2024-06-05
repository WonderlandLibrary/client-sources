package net;

import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.modules.configurations.ConfigManager;
import net.acE;

public class o3 {
   private static acE[] b;

   public static void a(ConfigManager var0, String var1) {
      ConfigCommand.saveConfig(var0, var1);
   }

   public static void c(ConfigManager var0, String var1) {
      ConfigCommand.deleteConfig(var0, var1);
   }

   public static void b(ConfigManager var0, String var1) {
      ConfigCommand.loadConfig(var0, var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[3]);
      }

   }
}
