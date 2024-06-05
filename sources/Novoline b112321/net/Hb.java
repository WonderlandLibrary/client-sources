package net;

import net.minecraft.block.Block$SoundType;

public class Hb {
   public static String b(Block$SoundType var0) {
      return var0.getStepSound();
   }

   public static float c(Block$SoundType var0) {
      return var0.getVolume();
   }

   public static float e(Block$SoundType var0) {
      return var0.getFrequency();
   }

   public static String d(Block$SoundType var0) {
      return var0.getPlaceSound();
   }

   public static String a(Block$SoundType var0) {
      return var0.getBreakSound();
   }
}
