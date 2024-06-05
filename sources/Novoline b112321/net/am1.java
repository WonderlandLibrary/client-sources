package net;

import viaversion.viafabric.util.ProtocolUtils;

public class am1 {
   private static String b;

   public static String a(int var0) {
      return ProtocolUtils.getProtocolName(var0);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("yzSCVc");
      }

   }
}
