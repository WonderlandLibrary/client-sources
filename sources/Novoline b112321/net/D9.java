package net;

import net.aq1;
import net.ayd;
import viaversion.viabackwards.api.entities.meta.MetaHandlerSettings;

public class D9 {
   private static String[] b;

   public static ayd c(aq1 var0) {
      return var0.c();
   }

   public static MetaHandlerSettings a(aq1 var0) {
      return var0.registerMetaHandler();
   }

   public static void b(aq1 var0) {
      var0.f();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[5]);
      }

   }
}
