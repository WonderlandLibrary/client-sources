package net;

import java.awt.image.BufferedImage;
import net.acE;
import net.tD;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.shadersmod.client.MultiTexID;
import net.shadersmod.client.ShadersTex;

public class a_P {
   public static void a(AbstractTexture var0, int var1) {
      ShadersTex.deleteTextures(var0, var1);
   }

   public static MultiTexID a(AbstractTexture var0) {
      return ShadersTex.getMultiTexID(var0);
   }

   public static int[] a(int var0, int var1) {
      return ShadersTex.createAIntImage(var0, var1);
   }

   public static void a(MultiTexID var0, int[] var1, int var2, int var3, boolean var4, boolean var5) {
      ShadersTex.setupTexture(var0, var1, var2, var3, var4, var5);
   }

   public static ITextureObject a() {
      return ShadersTex.createDefaultTexture();
   }

   public static void a(MultiTexID var0) {
      ShadersTex.bindNSTextures(var0);
   }

   public static void a(ITextureObject var0) {
      ShadersTex.bindTexture(var0);
   }

   public static void a(int var0, int var1, int var2, DynamicTexture var3) {
      ShadersTex.initDynamicTexture(var0, var1, var2, var3);
   }

   public static void a(int var0, int[] var1, int var2, int var3, DynamicTexture var4) {
      ShadersTex.updateDynamicTexture(var0, var1, var2, var3, var4);
   }

   public static int a(int var0, BufferedImage var1, boolean var2, boolean var3, IResourceManager var4, ResourceLocation var5, MultiTexID var6) {
      int var7 = tD.F();
      int var10000 = ShadersTex.a(var0, var1, var2, var3, var4, var5, var6);
      if(acE.b() == null) {
         ++var7;
         tD.c(var7);
      }

      return var10000;
   }

   public static void a(int[][] var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      int var7 = tD.F();
      ShadersTex.b(var0, var1, var2, var3, var4, var5, var6);
   }

   public static void a(TextureManager var0, ResourceLocation var1) {
      ShadersTex.bindTextureMapForUpdateAndRender(var0, var1);
   }
}
