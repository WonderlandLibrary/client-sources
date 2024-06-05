package net;

import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.shader.ShaderUniform;

public class akD {
   public static ShaderUniform b(ShaderManager var0, String var1) {
      return var0.getShaderUniform(var1);
   }

   public static ShaderLoader e(ShaderManager var0) {
      return var0.getFragmentShaderLoader();
   }

   public static ShaderLoader a(ShaderManager var0) {
      return var0.getVertexShaderLoader();
   }

   public static int d(ShaderManager var0) {
      return var0.getProgram();
   }

   public static void a(ShaderManager var0, String var1, Object var2) {
      var0.addSamplerTexture(var1, var2);
   }

   public static ShaderUniform a(ShaderManager var0, String var1) {
      return var0.getShaderUniformOrDefault(var1);
   }

   public static void b(ShaderManager var0) {
      var0.useShader();
   }

   public static void c(ShaderManager var0) {
      var0.endShader();
   }
}
