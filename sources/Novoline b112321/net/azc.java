package net;

import org.lwjgl.opengl.ARBVertexShader;

public class azc {
   public static void a(int var0, int var1, CharSequence var2) {
      ARBVertexShader.glBindAttribLocationARB(var0, var1, var2);
   }

   public static int a(int var0, CharSequence var1) {
      return ARBVertexShader.glGetAttribLocationARB(var0, var1);
   }
}
