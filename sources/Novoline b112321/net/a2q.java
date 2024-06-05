package net;

import net.minecraft.network.play.server.S29PacketSoundEffect;

public class a2q {
   public static double f(S29PacketSoundEffect var0) {
      return var0.getX();
   }

   public static double c(S29PacketSoundEffect var0) {
      return var0.getY();
   }

   public static double a(S29PacketSoundEffect var0) {
      return var0.getZ();
   }

   public static String e(S29PacketSoundEffect var0) {
      return var0.getSoundName();
   }

   public static float d(S29PacketSoundEffect var0) {
      return var0.getVolume();
   }

   public static float b(S29PacketSoundEffect var0) {
      return var0.getPitch();
   }
}
