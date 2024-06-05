package net;

import net.shadersmod.client.ShaderMacros;

public class tA {
   public static String a() {
      return ShaderMacros.getPrefixMacro();
   }

   public static String d() {
      return ShaderMacros.getOs();
   }

   public static String c() {
      return ShaderMacros.getVendor();
   }

   public static String e() {
      return ShaderMacros.getRenderer();
   }

   public static String[] b() {
      return ShaderMacros.getExtensions();
   }
}
