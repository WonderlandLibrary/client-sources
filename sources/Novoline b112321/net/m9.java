package net;

import net.aRX;
import net.acE;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data.MappingData;

public class m9 {
   private static String[] b;

   public static MappingData a(aRX var0) {
      return var0.b();
   }

   public static Object a(aRX var0, Class var1) {
      return var0.get(var1);
   }

   public static void a(aRX var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(aRX var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
