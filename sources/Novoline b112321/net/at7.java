package net;

import cc.novoline.utils.shader.GLSLSandboxShader;

public class at7 {
   private static boolean b;

   public static void a(GLSLSandboxShader var0, int var1, int var2, float var3, float var4, float var5) {
      var0.useShader(var1, var2, var3, var4, var5);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
