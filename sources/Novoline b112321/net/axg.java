package net;

import net.minecraft.network.play.server.S1BPacketEntityAttach;

public class axg {
   public static int c(S1BPacketEntityAttach var0) {
      return var0.getEntityId();
   }

   public static int b(S1BPacketEntityAttach var0) {
      return var0.getVehicleEntityId();
   }

   public static int a(S1BPacketEntityAttach var0) {
      return var0.getLeash();
   }
}
