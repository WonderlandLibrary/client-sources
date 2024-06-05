package net;

import org.lwjgl.opengl.PixelFormat;

public class rf {
   public static PixelFormat b(PixelFormat var0, int var1) {
      return var0.withDepthBits(var1);
   }

   public static PixelFormat a(PixelFormat var0, int var1) {
      return var0.withSamples(var1);
   }
}
