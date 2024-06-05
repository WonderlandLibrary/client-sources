package net;

import net.ayd;
import net.cQ;
import viaversion.viabackwards.api.entities.storage.EntityTracker$ProtocolEntityTracker;

public class alg {
   private static boolean b;

   public static EntityTracker$ProtocolEntityTracker a(cQ var0, ayd var1) {
      return var0.a(var1);
   }

   public static void b(cQ var0, ayd var1) {
      var0.b(var1);
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
