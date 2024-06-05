package net;

import org.lwjgl.opengl.GL14;

public class W1 {
   public static void a(int var0, int var1, int var2, int var3) {
      GL14.glBlendFuncSeparate(var0, var1, var2, var3);
   }

   public static void a(int var0) {
      GL14.glBlendEquation(var0);
   }
}
