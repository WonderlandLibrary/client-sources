package net;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class a0y {
   public static int c(S12PacketEntityVelocity var0) {
      return var0.getEntityID();
   }

   public static int a(S12PacketEntityVelocity var0) {
      return var0.getMotionX();
   }

   public static int d(S12PacketEntityVelocity var0) {
      return var0.getMotionY();
   }

   public static int b(S12PacketEntityVelocity var0) {
      return var0.getMotionZ();
   }
}
