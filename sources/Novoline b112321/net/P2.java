package net;

import net.acE;
import net.aqT;
import net.aqp;
import viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;

public class P2 {
   private static int[] b;

   public static void a(Protocol1_10To1_11 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(Protocol1_10To1_11 var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static aqp b(Protocol1_10To1_11 var0) {
      return var0.b();
   }

   public static aqT a(Protocol1_10To1_11 var0) {
      return var0.c();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
