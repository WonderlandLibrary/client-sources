package net;

import net.optifine.Blender;

public class mC {
   public static void a(int var0, float var1) {
      Blender.setupBlend(var0, var1);
   }

   public static void a(float var0) {
      Blender.clearBlend(var0);
   }

   public static int a(String var0) {
      return Blender.parseBlend(var0);
   }
}
