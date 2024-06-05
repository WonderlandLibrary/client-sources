package net;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class a_e {
   private static int b;

   public static void c(TextureManager var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ITextureObject b(TextureManager var0, ResourceLocation var1) {
      return var0.getTexture(var1);
   }

   public static boolean a(TextureManager var0, ResourceLocation var1, ITextureObject var2) {
      return var0.loadTexture(var1, var2);
   }

   public static void b(TextureManager var0) {
      var0.tick();
   }

   public static boolean a(TextureManager var0, ResourceLocation var1, ITickableTextureObject var2) {
      return var0.loadTickableTexture(var1, var2);
   }

   public static void a(TextureManager var0, ResourceLocation var1) {
      var0.deleteTexture(var1);
   }

   public static ResourceLocation a(TextureManager var0, String var1, DynamicTexture var2) {
      return var0.getDynamicTextureLocation(var1, var2);
   }

   public static void a(TextureManager var0) {
      var0.reloadBannerTextures();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 34;
   }

   static {
      if(b() != 0) {
         b(80);
      }

   }
}
