package net;

import net.minecraft.network.play.server.S18PacketEntityTeleport;

public class aks {
   public static int b(S18PacketEntityTeleport var0) {
      return var0.getEntityId();
   }

   public static int g(S18PacketEntityTeleport var0) {
      return var0.getX();
   }

   public static int a(S18PacketEntityTeleport var0) {
      return var0.getY();
   }

   public static int e(S18PacketEntityTeleport var0) {
      return var0.getZ();
   }

   public static byte c(S18PacketEntityTeleport var0) {
      return var0.getYaw();
   }

   public static byte d(S18PacketEntityTeleport var0) {
      return var0.getPitch();
   }

   public static boolean f(S18PacketEntityTeleport var0) {
      return var0.getOnGround();
   }
}
