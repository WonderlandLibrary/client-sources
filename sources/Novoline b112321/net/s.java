package net;

import net.aZm;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.ChainedConfigurationTransformation;
import ninja.leaping.configurate.transformation.ConfigurationTransformation$Builder;

public abstract class s {
   public static final Object c = new Object();
   private static String b;

   public static ConfigurationTransformation$Builder c() {
      return new ConfigurationTransformation$Builder();
   }

   public static aZm a() {
      return new aZm();
   }

   public static s a(s... var0) {
      return new ChainedConfigurationTransformation(var0);
   }

   public abstract void apply(ConfigurationNode var1);

   static {
      b("NzwMcc");
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }
}
