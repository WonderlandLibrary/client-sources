package net;

import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderManager;
import org.lwjgl.util.vector.Matrix4f;

public class aB1 {
   public static void a(Shader var0, String var1, Object var2, int var3, int var4) {
      var0.addAuxFramebuffer(var1, var2, var3, var4);
   }

   public static ShaderManager b(Shader var0) {
      return var0.getShaderManager();
   }

   public static void a(Shader var0) {
      var0.deleteShader();
   }

   public static void a(Shader var0, Matrix4f var1) {
      var0.setProjectionMatrix(var1);
   }

   public static void a(Shader var0, float var1) {
      var0.loadShader(var1);
   }
}
