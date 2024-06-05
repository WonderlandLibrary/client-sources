package net;

import net.acE;
import viaversion.viabackwards.api.data.BackwardsMappings;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;

public class c {
   private static acE[] b;

   public static void a(Protocol1_14_4To1_15 var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(Protocol1_14_4To1_15 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static BackwardsMappings a(Protocol1_14_4To1_15 var0) {
      return var0.getMappingData();
   }

   public static BlockItemPackets1_15 b(Protocol1_14_4To1_15 var0) {
      return var0.getBlockItemPackets();
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
