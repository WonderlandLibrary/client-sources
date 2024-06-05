package net;

import com.typesafe.config.ConfigRenderOptions;

public class arv {
   public static ConfigRenderOptions a() {
      return ConfigRenderOptions.defaults();
   }

   public static ConfigRenderOptions a(ConfigRenderOptions var0, boolean var1) {
      return var0.setOriginComments(var1);
   }

   public static ConfigRenderOptions b(ConfigRenderOptions var0, boolean var1) {
      return var0.setJson(var1);
   }
}
