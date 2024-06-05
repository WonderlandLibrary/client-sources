package net;

import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.AbstractConfigurationLoader$Builder;
import org.jetbrains.annotations.NotNull;

public class a6t extends AbstractConfigurationLoader$Builder {
   private ConfigRenderOptions e = HoconConfigurationLoader.defaultRenderOptions();
   private ConfigParseOptions g = HoconConfigurationLoader.defaultParseOptions();
   private static int f;

   @NotNull
   public a6t a(@NotNull ConfigRenderOptions var1) {
      this.e = var1;
      return this;
   }

   @NotNull
   public ConfigRenderOptions f() {
      return this.e;
   }

   @NotNull
   public a6t a(ConfigParseOptions var1) {
      this.g = var1;
      return this;
   }

   @NotNull
   public ConfigParseOptions d() {
      return this.g;
   }

   @NotNull
   public HoconConfigurationLoader a() {
      return new HoconConfigurationLoader(this);
   }

   public static void b(int var0) {
      f = var0;
   }

   public static int e() {
      return f;
   }

   public static int c() {
      int var0 = e();
      return 60;
   }

   static {
      if(c() != 0) {
         b(30);
      }

   }
}
