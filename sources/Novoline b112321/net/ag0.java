package net;

import net.acE;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;

public class ag0 {
   private static acE[] b;

   public static void a(EntityPackets1_14 var0) {
      var0.f();
   }

   public static int a(EntityPackets1_14 var0, int var1) {
      return var0.getOldEntityId(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[1]);
      }

   }
}
