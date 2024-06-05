package net;

import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;

public class gy {
   private static boolean b;

   public static ConfigOrigin b(ConfigValue var0) {
      return var0.origin();
   }

   public static ConfigValueType a(ConfigValue var0) {
      return var0.valueType();
   }

   public static Object c(ConfigValue var0) {
      return var0.unwrapped();
   }

   public static String a(ConfigValue var0, ConfigRenderOptions var1) {
      return var0.render(var1);
   }

   public static ConfigValue a(ConfigValue var0, ConfigOrigin var1) {
      return var0.withOrigin(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(c()) {
         b(true);
      }

   }
}
