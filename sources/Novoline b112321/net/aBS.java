package net;

import java.util.Set;
import net.minecraft.client.resources.data.AnimationMetadataSection;

public class aBS {
   public static int f(AnimationMetadataSection var0) {
      return var0.getFrameTime();
   }

   public static int d(AnimationMetadataSection var0) {
      return var0.getFrameWidth();
   }

   public static int c(AnimationMetadataSection var0) {
      return var0.getFrameHeight();
   }

   public static int a(AnimationMetadataSection var0) {
      return var0.getFrameCount();
   }

   public static boolean c(AnimationMetadataSection var0, int var1) {
      return var0.frameHasTime(var1);
   }

   public static int a(AnimationMetadataSection var0, int var1) {
      return var0.getFrameIndex(var1);
   }

   public static int b(AnimationMetadataSection var0, int var1) {
      return var0.getFrameTimeSingle(var1);
   }

   public static boolean e(AnimationMetadataSection var0) {
      return var0.isInterpolate();
   }

   public static Set b(AnimationMetadataSection var0) {
      return var0.getFrameIndexSet();
   }
}
