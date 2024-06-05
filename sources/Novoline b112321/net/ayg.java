package net;

import net.shadersmod.client.ShaderUniformFloat4;

public class ayg {
   public static void a(ShaderUniformFloat4 var0, int var1) {
      var0.setProgram(var1);
   }

   public static void a(ShaderUniformFloat4 var0, float var1, float var2, float var3, float var4) {
      var0.setValue(var1, var2, var3, var4);
   }
}
