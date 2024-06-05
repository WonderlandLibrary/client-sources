package net;

import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.configurations.ClientConfig;
import cc.novoline.modules.configurations.ConfigManager;

public class a_f {
   public static ModuleArrayMap a(ClientConfig var0) {
      return var0.getModules();
   }

   public static ClientConfig a(ConfigManager var0, String var1) {
      return ClientConfig.of(var0, var1);
   }
}
