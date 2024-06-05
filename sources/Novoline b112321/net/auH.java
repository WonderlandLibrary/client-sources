package net;

import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

public class auH {
   public static PotionEffect g(S1DPacketEntityEffect var0) {
      return var0.getEffect();
   }

   public static int e(S1DPacketEntityEffect var0) {
      return var0.getEntityId();
   }

   public static byte b(S1DPacketEntityEffect var0) {
      return var0.getEffectId();
   }

   public static int a(S1DPacketEntityEffect var0) {
      return var0.getDuration();
   }

   public static byte f(S1DPacketEntityEffect var0) {
      return var0.getAmplifier();
   }

   public static boolean c(S1DPacketEntityEffect var0) {
      return var0.func_179707_f();
   }

   public static boolean d(S1DPacketEntityEffect var0) {
      return var0.func_149429_c();
   }
}
