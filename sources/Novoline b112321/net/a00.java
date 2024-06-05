package net;

import net.acE;
import net.aq0;
import net.aqw;
import net.ay_;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;

public class a00 {
   private static int[] b;

   public static void a(ay_ var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static aq0 b(ay_ var0) {
      return var0.a();
   }

   public static void a(ay_ var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static aqw a(ay_ var0) {
      return var0.e();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[3]);
      }

   }
}
