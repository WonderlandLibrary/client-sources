package net;

import net.acE;
import viaversion.viabackwards.api.data.BackwardsMappings;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import viaversion.viaversion.api.protocol.ClientboundPacketType;

public class a_r {
   private static int b;

   public static void a(Protocol1_13To1_13_1 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static BackwardsMappings a(Protocol1_13To1_13_1 var0) {
      return var0.getMappingData();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 69;
   }

   static {
      if(c() != 0) {
         b(109);
      }

   }
}
