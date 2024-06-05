package net;

import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;

public class a4s {
   public static void a(ShaderGroup var0) {
      var0.deleteShaderGroup();
   }

   public static void a(ShaderGroup var0, int var1, int var2) {
      var0.createBindFramebuffers(var1, var2);
   }

   public static void a(ShaderGroup var0, float var1) {
      var0.loadShaderGroup(var1);
   }

   public static Framebuffer a(ShaderGroup var0, String var1) {
      return var0.getFramebufferRaw(var1);
   }

   public static String b(ShaderGroup var0) {
      return var0.getShaderGroupName();
   }
}
