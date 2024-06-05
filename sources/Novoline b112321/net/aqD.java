package net;

import net.minecraft.network.play.server.S39PacketPlayerAbilities;

public class aqD {
   public static boolean a(S39PacketPlayerAbilities var0) {
      return var0.isAllowFlying();
   }

   public static boolean e(S39PacketPlayerAbilities var0) {
      return var0.isCreative();
   }

   public static boolean f(S39PacketPlayerAbilities var0) {
      return var0.isFlying();
   }

   public static boolean d(S39PacketPlayerAbilities var0) {
      return var0.isDisabledDamage();
   }

   public static float c(S39PacketPlayerAbilities var0) {
      return var0.getFlySpeed();
   }

   public static float b(S39PacketPlayerAbilities var0) {
      return var0.getWalkSpeed();
   }
}
