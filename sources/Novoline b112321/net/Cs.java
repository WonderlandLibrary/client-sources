package net;

import net.minecraft.client.shader.Framebuffer;

public class Cs {
   private static boolean b;

   public static void d(Framebuffer var0) {
      var0.deleteFramebuffer();
   }

   public static void a(Framebuffer var0, boolean var1) {
      var0.bindFramebuffer(var1);
   }

   public static void a(Framebuffer var0, float var1, float var2, float var3, float var4) {
      var0.setFramebufferColor(var1, var2, var3, var4);
   }

   public static void a(Framebuffer var0) {
      var0.unbindFramebuffer();
   }

   public static void a(Framebuffer var0, int var1, int var2) {
      var0.framebufferRender(var1, var2);
   }

   public static void b(Framebuffer var0, int var1, int var2) {
      var0.createBindFramebuffer(var1, var2);
   }

   public static void a(Framebuffer var0, int var1) {
      var0.setFramebufferFilter(var1);
   }

   public static void c(Framebuffer var0) {
      var0.framebufferClear();
   }

   public static void b(Framebuffer var0) {
      var0.unbindFramebufferTexture();
   }

   public static void a(Framebuffer var0, int var1, int var2, boolean var3) {
      var0.framebufferRenderExt(var1, var2, var3);
   }

   public static void e(Framebuffer var0) {
      var0.bindFramebufferTexture();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
