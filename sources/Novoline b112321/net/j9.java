package net;

import viaversion.viafabric.ViaFabric;

public class j9 {
   private static String b;

   public static String a() {
      return ViaFabric.getVersion();
   }

   public static void a(ViaFabric var0) {
      var0.onInitialize();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("clWyIb");
      }

   }
}
