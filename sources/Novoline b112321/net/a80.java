package net;

import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import net.a6t;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;

public class a80 {
   private static String[] b;

   public static a6t a() {
      return HoconConfigurationLoader.b();
   }

   public static ConfigurationNode a(HoconConfigurationLoader var0, ConfigurationOptions var1) {
      return var0.load(var1);
   }

   public static void a(HoconConfigurationLoader var0, ConfigurationNode var1) {
      var0.save(var1);
   }

   public static ConfigurationOptions c(HoconConfigurationLoader var0) {
      return var0.getDefaultOptions();
   }

   public static ConfigurationNode a(HoconConfigurationLoader var0) {
      return var0.load();
   }

   public static ConfigRenderOptions b() {
      return HoconConfigurationLoader.defaultRenderOptions();
   }

   public static ConfigParseOptions c() {
      return HoconConfigurationLoader.defaultParseOptions();
   }

   public static ConfigurationNode b(HoconConfigurationLoader var0) {
      return var0.createEmptyNode();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] d() {
      return b;
   }

   static {
      if(d() == null) {
         b(new String[1]);
      }

   }
}
