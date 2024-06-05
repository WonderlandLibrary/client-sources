package net;

import org.lwjgl.opengl.GL30;

public class _j {
   public static void a(int var0) {
      GL30.glGenerateMipmap(var0);
   }

   public static String a(int var0, int var1) {
      return GL30.glGetStringi(var0, var1);
   }
}
