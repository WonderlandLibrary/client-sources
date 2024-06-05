package net;

import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderManager;

public class a21 {
   public static ShaderLinkHelper a() {
      return ShaderLinkHelper.getStaticShaderLinkHelper();
   }

   public static int a(ShaderLinkHelper var0) {
      return var0.createProgram();
   }

   public static void a(ShaderLinkHelper var0, ShaderManager var1) {
      var0.linkProgram(var1);
   }

   public static void b(ShaderLinkHelper var0, ShaderManager var1) {
      var0.deleteShader(var1);
   }

   public static void b() {
      ShaderLinkHelper.setNewStaticShaderLinkHelper();
   }
}
