package net;

import net.acE;
import net.aqL;
import net.ayj;
import viaversion.viaversion.api.protocol.ClientboundPacketType;

public class a_b {
   private static acE[] b;

   public static void a(ayj var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static aqL a(ayj var0) {
      return var0.a();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[4]);
      }

   }
}
