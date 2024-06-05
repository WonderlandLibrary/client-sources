package net;

import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.shadersmod.client.MultiTexID;

public class a8P {
   public static boolean d(TextureMap var0) {
      return var0.isTextureBound();
   }

   public static TextureAtlasSprite b(TextureMap var0, String var1) {
      return var0.getSpriteSafe(var1);
   }

   public static TextureAtlasSprite a(TextureMap var0, String var1) {
      return var0.getAtlasSprite(var1);
   }

   public static TextureAtlasSprite a(TextureMap var0, ResourceLocation var1) {
      return var0.b(var1);
   }

   public static void a(TextureMap var0, int var1) {
      var0.setMipmapLevels(var1);
   }

   public static void a(TextureMap var0, boolean var1, boolean var2) {
      var0.setBlurMipmapDirect(var1, var2);
   }

   public static TextureAtlasSprite a(TextureMap var0, double var1, double var3) {
      return var0.getIconByUV(var1, var3);
   }

   public static MultiTexID c(TextureMap var0) {
      return var0.getMultiTexID();
   }

   public static ResourceLocation a(TextureMap var0, ResourceLocation var1, int var2) {
      return var0.completeResourceLocation(var1, var2);
   }

   public static int a(TextureMap var0) {
      return var0.getCountRegisteredSprites();
   }

   public static void a(TextureMap var0, IResourceManager var1, IIconCreator var2) {
      var0.loadSprites(var1, var2);
   }

   public static TextureAtlasSprite e(TextureMap var0) {
      return var0.getMissingSprite();
   }

   public static int b(TextureMap var0) {
      return var0.getMipmapLevels();
   }
}
