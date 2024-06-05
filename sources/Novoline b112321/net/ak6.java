package net;

import java.awt.image.BufferedImage;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;

public class ak6 {
   private static String b;

   public static int[][] d(TextureAtlasSprite var0, int var1) {
      return var0.getFrameTextureData(var1);
   }

   public static float c(TextureAtlasSprite var0) {
      return var0.getMinU();
   }

   public static float d(TextureAtlasSprite var0) {
      return var0.getMaxU();
   }

   public static float m(TextureAtlasSprite var0) {
      return var0.getMinV();
   }

   public static float l(TextureAtlasSprite var0) {
      return var0.getMaxV();
   }

   public static int e(TextureAtlasSprite var0) {
      return var0.getIconWidth();
   }

   public static int k(TextureAtlasSprite var0) {
      return var0.getIconHeight();
   }

   public static float d(TextureAtlasSprite var0, float var1) {
      return var0.toSingleU(var1);
   }

   public static float b(TextureAtlasSprite var0, float var1) {
      return var0.toSingleV(var1);
   }

   public static float b(TextureAtlasSprite var0, double var1) {
      return var0.getInterpolatedU(var1);
   }

   public static float a(TextureAtlasSprite var0, double var1) {
      return var0.getInterpolatedV(var1);
   }

   public static int i(TextureAtlasSprite var0) {
      return var0.getFrameCount();
   }

   public static String h(TextureAtlasSprite var0) {
      return var0.getIconName();
   }

   public static int p(TextureAtlasSprite var0) {
      return var0.getIndexInMap();
   }

   public static double c(TextureAtlasSprite var0, float var1) {
      return var0.getSpriteU16(var1);
   }

   public static double a(TextureAtlasSprite var0, float var1) {
      return var0.getSpriteV16(var1);
   }

   public static void d(String var0) {
      TextureAtlasSprite.setLocationNameCompass(var0);
   }

   public static void c(String var0) {
      TextureAtlasSprite.setLocationNameClock(var0);
   }

   public static boolean b(TextureAtlasSprite var0) {
      return var0.hasAnimationMetadata();
   }

   public static void j(TextureAtlasSprite var0) {
      var0.clearFramesTextureData();
   }

   public static void b(TextureAtlasSprite var0, int var1) {
      var0.setIconWidth(var1);
   }

   public static void e(TextureAtlasSprite var0, int var1) {
      var0.setIconHeight(var1);
   }

   public static void a(TextureAtlasSprite var0, List var1) {
      var0.setFramesTextureData(var1);
   }

   public static void a(TextureAtlasSprite var0, int var1) {
      var0.setIndexInMap(var1);
   }

   public static void o(TextureAtlasSprite var0) {
      var0.deleteSpriteTexture();
   }

   public static boolean b(TextureAtlasSprite var0, IResourceManager var1, ResourceLocation var2) {
      return var0.hasCustomLoader(var1, var2);
   }

   public static void a(TextureAtlasSprite var0, BufferedImage[] var1, AnimationMetadataSection var2) {
      var0.loadSprite(var1, var2);
   }

   public static boolean a(TextureAtlasSprite var0, IResourceManager var1, ResourceLocation var2) {
      return var0.load(var1, var2);
   }

   public static void c(TextureAtlasSprite var0, int var1) {
      var0.generateMipmaps(var1);
   }

   public static int f(TextureAtlasSprite var0) {
      return var0.getOriginX();
   }

   public static int a(TextureAtlasSprite var0) {
      return var0.getOriginY();
   }

   public static void a(TextureAtlasSprite var0, TextureAtlasSprite var1) {
      var0.copyFrom(var1);
   }

   public static void g(TextureAtlasSprite var0) {
      var0.bindSpriteTexture();
   }

   public static void n(TextureAtlasSprite var0) {
      var0.updateAnimation();
   }

   public static void a(TextureAtlasSprite var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.initSprite(var1, var2, var3, var4, var5);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("w2SGwc");
      }

   }
}
