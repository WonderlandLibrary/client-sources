package net.shadersmod.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.aYz;
import net.qT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.shadersmod.client.MultiTexID;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;
import net.shadersmod.common.SMCLog;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ShadersTex {
   public static final int initialBufferSize = 1048576;
   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
   public static int[] intArray = new int[1048576];
   public static final int f = 0;
   public static final int defNormTexColor = -8421377;
   public static final int q = 0;
   public static Map multiTexMap = new HashMap();
   public static TextureMap updatingTextureMap = null;
   public static TextureAtlasSprite updatingSprite = null;
   public static MultiTexID updatingTex = null;
   public static MultiTexID boundTex = null;
   public static int updatingPage = 0;
   public static String iconName = null;
   public static IResourceManager resManager = null;
   static ResourceLocation resLocation = null;
   static int imageSize = 0;

   public static IntBuffer getIntBuffer(int var0) {
      if(intBuffer.capacity() < var0) {
         int var1 = roundUpPOT(var0);
         byteBuffer = BufferUtils.createByteBuffer(var1 * 4);
         intBuffer = byteBuffer.asIntBuffer();
      }

      return intBuffer;
   }

   public static int[] getIntArray(int var0) {
      String[] var1 = ShaderOption.p();
      if(intArray == null) {
         intArray = new int[1048576];
      }

      if(intArray.length < var0) {
         intArray = new int[roundUpPOT(var0)];
      }

      return intArray;
   }

   public static int roundUpPOT(int var0) {
      int var1 = var0 - 1;
      var1 = var1 | var1 >> 1;
      var1 = var1 | var1 >> 2;
      var1 = var1 | var1 >> 4;
      var1 = var1 | var1 >> 8;
      var1 = var1 | var1 >> 16;
      return var1 + 1;
   }

   public static int c(int var0) {
      ShaderOption.p();
      int var2 = 0;
      if((var0 & -65536) != 0) {
         var2 += 16;
         var0 >>= 16;
      }

      if((var0 & '\uff00') != 0) {
         var2 += 8;
         var0 >>= 8;
      }

      if((var0 & 240) != 0) {
         var2 += 4;
         var0 >>= 4;
      }

      if((var0 & 6) != 0) {
         var2 += 2;
         var0 >>= 2;
      }

      if((var0 & 2) != 0) {
         ++var2;
      }

      return var2;
   }

   public static IntBuffer fillIntBuffer(int var0, int var1) {
      int[] var2 = getIntArray(var0);
      IntBuffer var3 = getIntBuffer(var0);
      Arrays.fill((int[])intArray, 0, var0, var1);
      intBuffer.put(intArray, 0, var0);
      return intBuffer;
   }

   public static int[] createAIntImage(int var0) {
      int[] var1 = new int[var0 * 3];
      Arrays.fill((int[])var1, 0, var0, 0);
      Arrays.fill(var1, var0, var0 * 2, -8421377);
      Arrays.fill((int[])var1, var0 * 2, var0 * 3, 0);
      return var1;
   }

   public static int[] createAIntImage(int var0, int var1) {
      int[] var2 = new int[var0 * 3];
      Arrays.fill((int[])var2, 0, var0, var1);
      Arrays.fill(var2, var0, var0 * 2, -8421377);
      Arrays.fill((int[])var2, var0 * 2, var0 * 3, 0);
      return var2;
   }

   public static MultiTexID getMultiTexID(AbstractTexture var0) {
      MultiTexID var1 = var0.multiTex;
      int var2 = var0.getGlTextureId();
      var1 = (MultiTexID)multiTexMap.get(Integer.valueOf(var2));
      var1 = new MultiTexID(var2, GL11.glGenTextures(), GL11.glGenTextures());
      multiTexMap.put(Integer.valueOf(var2), var1);
      var0.multiTex = var1;
      return var1;
   }

   public static void deleteTextures(AbstractTexture var0, int var1) {
      ShaderOption.p();
      MultiTexID var3 = var0.multiTex;
      if(var3 != null) {
         var0.multiTex = null;
         multiTexMap.remove(Integer.valueOf(var3.base));
         GlStateManager.deleteTexture(var3.norm);
         GlStateManager.deleteTexture(var3.spec);
         if(var3.base != var1) {
            SMCLog.warning("Error : MultiTexID.base mismatch: " + var3.base + ", texid: " + var1);
            GlStateManager.deleteTexture(var3.base);
         }
      }

   }

   public static void bindNSTextures(int var0, int var1) {
      String[] var2 = ShaderOption.p();
      if(Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == '蓀') {
         GlStateManager.setActiveTexture('蓂');
         GlStateManager.bindTexture(var0);
         GlStateManager.setActiveTexture('蓃');
         GlStateManager.bindTexture(var1);
         GlStateManager.setActiveTexture('蓀');
      }

   }

   public static void bindNSTextures(MultiTexID var0) {
      bindNSTextures(var0.norm, var0.spec);
   }

   public static void bindTextures(int var0, int var1, int var2) {
      String[] var3 = ShaderOption.p();
      if(Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == '蓀') {
         GlStateManager.setActiveTexture('蓂');
         GlStateManager.bindTexture(var1);
         GlStateManager.setActiveTexture('蓃');
         GlStateManager.bindTexture(var2);
         GlStateManager.setActiveTexture('蓀');
      }

      GlStateManager.bindTexture(var0);
   }

   public static void bindTextures(MultiTexID var0) {
      ShaderOption.p();
      boundTex = var0;
      if(Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == '蓀') {
         if(Shaders.configNormalMap) {
            GlStateManager.setActiveTexture('蓂');
            GlStateManager.bindTexture(var0.norm);
         }

         if(Shaders.configSpecularMap) {
            GlStateManager.setActiveTexture('蓃');
            GlStateManager.bindTexture(var0.spec);
         }

         GlStateManager.setActiveTexture('蓀');
      }

      GlStateManager.bindTexture(var0.base);
   }

   public static void bindTexture(ITextureObject var0) {
      ShaderOption.p();
      int var2 = var0.getGlTextureId();
      if(var0 instanceof TextureMap) {
         Shaders.atlasSizeX = ((TextureMap)var0).atlasWidth;
         Shaders.atlasSizeY = ((TextureMap)var0).atlasHeight;
      }

      Shaders.atlasSizeX = 0;
      Shaders.atlasSizeY = 0;
      bindTextures(var0.getMultiTexID());
   }

   public static void bindTextureMapForUpdateAndRender(TextureManager var0, ResourceLocation var1) {
      TextureMap var2 = (TextureMap)var0.getTexture(var1);
      Shaders.atlasSizeX = var2.atlasWidth;
      Shaders.atlasSizeY = var2.atlasHeight;
      bindTextures(updatingTex = var2.getMultiTexID());
   }

   public static void bindTextures(int var0) {
      MultiTexID var1 = (MultiTexID)multiTexMap.get(Integer.valueOf(var0));
      bindTextures(var1);
   }

   public static void initDynamicTexture(int var0, int var1, int var2, DynamicTexture var3) {
      MultiTexID var4 = var3.getMultiTexID();
      int[] var5 = var3.getTextureData();
      int var6 = var1 * var2;
      Arrays.fill(var5, var6, var6 * 2, -8421377);
      Arrays.fill((int[])var5, var6 * 2, var6 * 3, 0);
      TextureUtil.allocateTexture(var4.base, var1, var2);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      TextureUtil.allocateTexture(var4.norm, var1, var2);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      TextureUtil.allocateTexture(var4.spec, var1, var2);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      GlStateManager.bindTexture(var4.base);
   }

   public static void updateDynamicTexture(int var0, int[] var1, int var2, int var3, DynamicTexture var4) {
      MultiTexID var5 = var4.getMultiTexID();
      GlStateManager.bindTexture(var5.base);
      updateDynTexSubImage1(var1, var2, var3, 0, 0, 0);
      GlStateManager.bindTexture(var5.norm);
      updateDynTexSubImage1(var1, var2, var3, 0, 0, 1);
      GlStateManager.bindTexture(var5.spec);
      updateDynTexSubImage1(var1, var2, var3, 0, 0, 2);
      GlStateManager.bindTexture(var5.base);
   }

   public static void updateDynTexSubImage1(int[] var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 * var2;
      IntBuffer var7 = getIntBuffer(var6);
      var7.clear();
      int var8 = var5 * var6;
      if(var0.length >= var8 + var6) {
         var7.put(var0, var8, var6).position(0).limit(var6);
         GL11.glTexSubImage2D(3553, 0, var3, var4, var1, var2, '胡', '荧', var7);
         var7.clear();
      }

   }

   public static ITextureObject createDefaultTexture() {
      DynamicTexture var0 = new DynamicTexture(1, 1);
      var0.getTextureData()[0] = -1;
      var0.updateDynamicTexture();
      return var0;
   }

   public static void allocateTextureMap(int var0, int var1, int var2, int var3, Stitcher var4, TextureMap var5) {
      SMCLog.info("allocateTextureMap " + var1 + " " + var2 + " " + var3 + " ");
      ShaderOption.p();
      updatingTextureMap = var5;
      var5.atlasWidth = var2;
      var5.atlasHeight = var3;
      MultiTexID var7 = getMultiTexID(var5);
      updatingTex = var7;
      TextureUtil.allocateTextureImpl(var7.base, var1, var2, var3);
      if(Shaders.configNormalMap) {
         TextureUtil.allocateTextureImpl(var7.norm, var1, var2, var3);
      }

      if(Shaders.configSpecularMap) {
         TextureUtil.allocateTextureImpl(var7.spec, var1, var2, var3);
      }

      GlStateManager.bindTexture(var0);
   }

   public static TextureAtlasSprite setSprite(TextureAtlasSprite var0) {
      updatingSprite = var0;
      return var0;
   }

   public static String setIconName(String var0) {
      iconName = var0;
      return var0;
   }

   public static void uploadTexSubForLoadAtlas(int[][] var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      ShaderOption.p();
      aYz.a(var0, var1, var2, var3, var4, var5, var6);
      boolean var8 = false;
      if(Shaders.configNormalMap) {
         int[][] var9 = readImageAndMipmaps(iconName + "_n", var1, var2, var0.length, var8, -8421377);
         GlStateManager.bindTexture(updatingTex.norm);
         aYz.a(var9, var1, var2, var3, var4, var5, var6);
      }

      if(Shaders.configSpecularMap) {
         int[][] var10 = readImageAndMipmaps(iconName + "_s", var1, var2, var0.length, var8, 0);
         GlStateManager.bindTexture(updatingTex.spec);
         aYz.a(var10, var1, var2, var3, var4, var5, var6);
      }

      GlStateManager.bindTexture(updatingTex.base);
   }

   public static int[][] readImageAndMipmaps(String var0, int var1, int var2, int var3, boolean var4, int var5) {
      ShaderOption.p();
      int[][] var7 = new int[var3][];
      int[] var8;
      var7[0] = var8 = new int[var1 * var2];
      boolean var9 = false;
      BufferedImage var10 = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(var0), 0));
      if(var10 != null) {
         int var11 = var10.getWidth();
         int var12 = var10.getHeight();
         if(var11 + (var4?16:0) == var1) {
            var9 = true;
            qT.b(var10, 0, 0, var11, var11, var8, 0, var11);
         }
      }

      if(!var9) {
         Arrays.fill(var8, var5);
      }

      GlStateManager.bindTexture(updatingTex.spec);
      var7 = genMipmapsSimple(var7.length - 1, var1, var7);
      return var7;
   }

   public static BufferedImage readImage(ResourceLocation param0) {
      // $FF: Couldn't be decompiled
   }

   public static int[][] genMipmapsSimple(int var0, int var1, int[][] var2) {
      ShaderOption.p();
      int var4 = 1;
      if(var4 <= var0) {
         if(var2[var4] == null) {
            int var5 = var1 >> var4;
            int var6 = var5 * 2;
            int[] var7 = var2[var4 - 1];
            int[] var8 = var2[var4] = new int[var5 * var5];
            int var9 = 0;
            if(var9 < var5) {
               int var10 = 0;
               if(var10 < var5) {
                  int var11 = var9 * 2 * var6 + var10 * 2;
                  var8[var9 * var5 + var10] = blend4Simple(var7[var11], var7[var11 + 1], var7[var11 + var6], var7[var11 + var6 + 1]);
                  ++var10;
               }

               ++var9;
            }
         }

         ++var4;
      }

      return var2;
   }

   public static void b(int[][] var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      ShaderOption.p();
      aYz.a(var0, var1, var2, var3, var4, var5, var6);
      if(Shaders.configNormalMap || Shaders.configSpecularMap) {
         if(Shaders.configNormalMap) {
            GlStateManager.bindTexture(updatingTex.norm);
            uploadTexSub1(var0, var1, var2, var3, var4, 1);
         }

         if(Shaders.configSpecularMap) {
            GlStateManager.bindTexture(updatingTex.spec);
            uploadTexSub1(var0, var1, var2, var3, var4, 2);
         }

         GlStateManager.bindTexture(updatingTex.base);
      }

   }

   public static void uploadTexSub1(int[][] var0, int var1, int var2, int var3, int var4, int var5) {
      ShaderOption.p();
      int var7 = var1 * var2;
      IntBuffer var8 = getIntBuffer(var7);
      int var9 = var0.length;
      int var10 = 0;
      if(var2 > 0 && var10 < var9) {
         int var15 = var1 * var2;
         int[] var16 = var0[var10];
         var8.clear();
         if(var16.length >= var15 * (var5 + 1)) {
            var8.put(var16, var15 * var5, var15).position(0).limit(var15);
            GL11.glTexSubImage2D(3553, var10, var3, var4, var1, var2, '胡', '荧', var8);
         }

         int var11 = var1 >> 1;
         int var12 = var2 >> 1;
         int var13 = var3 >> 1;
         int var14 = var4 >> 1;
         ++var10;
      }

      var8.clear();
   }

   public static int b(int var0, int var1, int var2, int var3) {
      int var5 = var0 >>> 24 & 255;
      ShaderOption.p();
      int var6 = var1 >>> 24 & 255;
      int var7 = var2 >>> 24 & 255;
      int var8 = var3 >>> 24 & 255;
      int var9 = var5 + var6 + var7 + var8;
      int var10 = (var9 + 2) / 4;
      if(var9 != 0) {
         ;
      }

      byte var11 = 4;
      var5 = 1;
      var6 = 1;
      var7 = 1;
      var8 = 1;
      int var12 = (var11 + 1) / 2;
      int var13 = var10 << 24 | ((var0 >>> 16 & 255) * var5 + (var1 >>> 16 & 255) * var6 + (var2 >>> 16 & 255) * var7 + (var3 >>> 16 & 255) * var8 + var12) / var11 << 16 | ((var0 >>> 8 & 255) * var5 + (var1 >>> 8 & 255) * var6 + (var2 >>> 8 & 255) * var7 + (var3 >>> 8 & 255) * var8 + var12) / var11 << 8 | ((var0 >>> 0 & 255) * var5 + (var1 >>> 0 & 255) * var6 + (var2 >>> 0 & 255) * var7 + (var3 >>> 0 & 255) * var8 + var12) / var11 << 0;
      return var13;
   }

   public static int blend4Simple(int var0, int var1, int var2, int var3) {
      int var4 = ((var0 >>> 24 & 255) + (var1 >>> 24 & 255) + (var2 >>> 24 & 255) + (var3 >>> 24 & 255) + 2) / 4 << 24 | ((var0 >>> 16 & 255) + (var1 >>> 16 & 255) + (var2 >>> 16 & 255) + (var3 >>> 16 & 255) + 2) / 4 << 16 | ((var0 >>> 8 & 255) + (var1 >>> 8 & 255) + (var2 >>> 8 & 255) + (var3 >>> 8 & 255) + 2) / 4 << 8 | ((var0 >>> 0 & 255) + (var1 >>> 0 & 255) + (var2 >>> 0 & 255) + (var3 >>> 0 & 255) + 2) / 4 << 0;
      return var4;
   }

   public static void genMipmapAlpha(int[] var0, int var1, int var2, int var3) {
      ShaderOption.p();
      Math.min(var2, var3);
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      if(var2 > 1 && var3 > 1) {
         var8 = var1 + var2 * var3;
         var9 = var2 / 2;
         var10 = var3 / 2;
         int var12 = 0;
         if(var12 < var10) {
            int var13 = var8 + var12 * var9;
            int var14 = var1 + var12 * 2 * var2;
            int var15 = 0;
            if(var15 < var9) {
               var0[var13 + var15] = b(var0[var14 + var15 * 2], var0[var14 + var15 * 2 + 1], var0[var14 + var2 + var15 * 2], var0[var14 + var2 + var15 * 2 + 1]);
               ++var15;
            }

            ++var12;
         }

         ++var11;
      }

      if(var11 > 0) {
         --var11;
         int var6 = var2 >> var11;
         int var7 = var3 >> var11;
         int var5 = var8 - var6 * var7;
         int var20 = 0;
         if(var20 < var7) {
            int var22 = 0;
            if(var22 < var6) {
               if(var0[var5] == 0) {
                  var0[var5] = var0[var8 + var20 / 2 * var9 + var22 / 2] & 16777215;
               }

               int var19 = var5 + 1;
               ++var22;
            }

            ++var20;
         }
      }

   }

   public static void genMipmapSimple(int[] var0, int var1, int var2, int var3) {
      Math.min(var2, var3);
      ShaderOption.p();
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      if(var2 > 1 && var3 > 1) {
         var8 = var1 + var2 * var3;
         var9 = var2 / 2;
         var10 = var3 / 2;
         int var12 = 0;
         if(var12 < var10) {
            int var13 = var8 + var12 * var9;
            int var14 = var1 + var12 * 2 * var2;
            int var15 = 0;
            if(var15 < var9) {
               var0[var13 + var15] = blend4Simple(var0[var14 + var15 * 2], var0[var14 + var15 * 2 + 1], var0[var14 + var2 + var15 * 2], var0[var14 + var2 + var15 * 2 + 1]);
               ++var15;
            }

            ++var12;
         }

         ++var11;
      }

      if(var11 > 0) {
         --var11;
         int var6 = var2 >> var11;
         int var7 = var3 >> var11;
         int var5 = var8 - var6 * var7;
         int var20 = 0;
         if(var20 < var7) {
            int var22 = 0;
            if(var22 < var6) {
               if(var0[var5] == 0) {
                  var0[var5] = var0[var8 + var20 / 2 * var9 + var22 / 2] & 16777215;
               }

               int var19 = var5 + 1;
               ++var22;
            }

            ++var20;
         }
      }

   }

   public static boolean isSemiTransparent(int[] var0, int var1, int var2) {
      ShaderOption.p();
      int var4 = var1 * var2;
      if(var0[0] >>> 24 == 255 && var0[var4 - 1] == 0) {
         return true;
      } else {
         int var5 = 0;
         if(var5 < var4) {
            int var6 = var0[var5] >>> 24;
            if(var6 != 0 && var6 != 255) {
               return true;
            }

            ++var5;
         }

         return false;
      }
   }

   public static void updateSubTex1(int[] var0, int var1, int var2, int var3, int var4) {
      int var6 = 0;
      String[] var5 = ShaderOption.p();
      if(var2 > 0) {
         GL11.glCopyTexSubImage2D(3553, var6, var3, var4, 0, 0, var1, var2);
         ++var6;
         int var7 = var1 / 2;
         int var8 = var2 / 2;
         int var9 = var3 / 2;
         int var10 = var4 / 2;
      }

   }

   public static void setupTexture(MultiTexID var0, int[] var1, int var2, int var3, boolean var4, boolean var5) {
      String[] var6 = ShaderOption.p();
      int var7 = var4?9729:9728;
      int var8 = var5?10496:10497;
      int var9 = var2 * var3;
      IntBuffer var10 = getIntBuffer(var9);
      var10.clear();
      var10.put(var1, 0, var9).position(0).limit(var9);
      GlStateManager.bindTexture(var0.base);
      GL11.glTexImage2D(3553, 0, 6408, var2, var3, 0, '胡', '荧', var10);
      GL11.glTexParameteri(3553, 10241, var7);
      GL11.glTexParameteri(3553, 10240, var7);
      GL11.glTexParameteri(3553, 10242, var8);
      GL11.glTexParameteri(3553, 10243, var8);
      var10.put(var1, var9, var9).position(0).limit(var9);
      GlStateManager.bindTexture(var0.norm);
      GL11.glTexImage2D(3553, 0, 6408, var2, var3, 0, '胡', '荧', var10);
      GL11.glTexParameteri(3553, 10241, var7);
      GL11.glTexParameteri(3553, 10240, var7);
      GL11.glTexParameteri(3553, 10242, var8);
      GL11.glTexParameteri(3553, 10243, var8);
      var10.put(var1, var9 * 2, var9).position(0).limit(var9);
      GlStateManager.bindTexture(var0.spec);
      GL11.glTexImage2D(3553, 0, 6408, var2, var3, 0, '胡', '荧', var10);
      GL11.glTexParameteri(3553, 10241, var7);
      GL11.glTexParameteri(3553, 10240, var7);
      GL11.glTexParameteri(3553, 10242, var8);
      GL11.glTexParameteri(3553, 10243, var8);
      GlStateManager.bindTexture(var0.base);
   }

   public static void updateSubImage(MultiTexID var0, int[] var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      ShaderOption.p();
      int var9 = var2 * var3;
      IntBuffer var10 = getIntBuffer(var9);
      var10.clear();
      var10.put(var1, 0, var9);
      var10.position(0).limit(var9);
      GlStateManager.bindTexture(var0.base);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexSubImage2D(3553, 0, var4, var5, var2, var3, '胡', '荧', var10);
      if(var1.length == var9 * 3) {
         var10.clear();
         var10.put(var1, var9, var9).position(0);
         var10.position(0).limit(var9);
      }

      GlStateManager.bindTexture(var0.norm);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexSubImage2D(3553, 0, var4, var5, var2, var3, '胡', '荧', var10);
      if(var1.length == var9 * 3) {
         var10.clear();
         var10.put(var1, var9 * 2, var9);
         var10.position(0).limit(var9);
      }

      GlStateManager.bindTexture(var0.spec);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexSubImage2D(3553, 0, var4, var5, var2, var3, '胡', '荧', var10);
      GlStateManager.setActiveTexture('蓀');
   }

   public static ResourceLocation getNSMapLocation(ResourceLocation var0, String var1) {
      String var2 = var0.getResourcePath();
      String[] var3 = var2.split(".png");
      String var4 = var3[0];
      return new ResourceLocation(var0.getResourceDomain(), var4 + "_" + var1 + ".png");
   }

   public static void loadNSMap(IResourceManager var0, ResourceLocation var1, int var2, int var3, int[] var4) {
      String[] var5 = ShaderOption.p();
      if(Shaders.configNormalMap) {
         loadNSMap1(var0, getNSMapLocation(var1, "n"), var2, var3, var4, var2 * var3, -8421377);
      }

      if(Shaders.configSpecularMap) {
         loadNSMap1(var0, getNSMapLocation(var1, "s"), var2, var3, var4, var2 * var3 * 2, 0);
      }

   }

   public static void loadNSMap1(IResourceManager var0, ResourceLocation var1, int var2, int var3, int[] var4, int var5, int var6) {
      ShaderOption.p();
      boolean var8 = false;
      IResourceManager var10000 = var0;
      ResourceLocation var10001 = var1;

      try {
         IResource var9 = var10000.getResource(var10001);
         BufferedImage var10 = ImageIO.read(var9.getInputStream());
         if(var10 != null && var10.getWidth() == var2 && var10.getHeight() == var3) {
            qT.b(var10, 0, 0, var2, var3, var4, var5, var2);
            var8 = true;
         }
      } catch (IOException var11) {
         ;
      }

      Arrays.fill(var4, var5, var5 + var2 * var3, var6);
   }

   public static int a(int var0, BufferedImage var1, boolean var2, boolean var3, IResourceManager var4, ResourceLocation var5, MultiTexID var6) {
      int var7 = var1.getWidth();
      int var8 = var1.getHeight();
      int var9 = var7 * var8;
      int[] var10 = getIntArray(var9 * 3);
      qT.b(var1, 0, 0, var7, var8, var10, 0, var7);
      loadNSMap(var4, var5, var7, var8, var10);
      setupTexture(var6, var10, var7, var8, var2, var3);
      return var0;
   }

   public static void mergeImage(int[] var0, int var1, int var2, int var3) {
   }

   public static int blendColor(int var0, int var1, int var2) {
      int var3 = 255 - var2;
      return ((var0 >>> 24 & 255) * var2 + (var1 >>> 24 & 255) * var3) / 255 << 24 | ((var0 >>> 16 & 255) * var2 + (var1 >>> 16 & 255) * var3) / 255 << 16 | ((var0 >>> 8 & 255) * var2 + (var1 >>> 8 & 255) * var3) / 255 << 8 | ((var0 >>> 0 & 255) * var2 + (var1 >>> 0 & 255) * var3) / 255 << 0;
   }

   public static void loadLayeredTexture(LayeredTexture var0, IResourceManager var1, List var2) {
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      ShaderOption.p();
      int[] var7 = null;
      Iterator var8 = var2.iterator();
      if(var8.hasNext()) {
         Object var9 = var8.next();

         try {
            ResourceLocation var10 = new ResourceLocation((String)var9);
            InputStream var11 = var1.getResource(var10).getInputStream();
            BufferedImage var12 = ImageIO.read(var11);
            if(var6 == 0) {
               var4 = var12.getWidth();
               var5 = var12.getHeight();
               var6 = var4 * var5;
               var7 = createAIntImage(var6, 0);
            }

            int[] var13 = getIntArray(var6 * 3);
            qT.b(var12, 0, 0, var4, var5, var13, 0, var4);
            loadNSMap(var1, var10, var4, var5, var13);
            int var14 = 0;
            if(var14 < var6) {
               int var15 = var13[var14] >>> 24 & 255;
               var7[var6 * 0 + var14] = blendColor(var13[var6 * 0 + var14], var7[var6 * 0 + var14], var15);
               var7[var6 * 1 + var14] = blendColor(var13[var6 * 1 + var14], var7[var6 * 1 + var14], var15);
               var7[var6 * 2 + var14] = blendColor(var13[var6 * 2 + var14], var7[var6 * 2 + var14], var15);
               ++var14;
            }
         } catch (IOException var16) {
            var16.printStackTrace();
         }
      }

      setupTexture(var0.getMultiTexID(), var7, var4, var5, false, false);
   }

   static void updateTextureMinMagFilter() {
      ShaderOption.p();
      TextureManager var1 = Minecraft.getInstance().getTextureManager();
      ITextureObject var2 = var1.getTexture(TextureMap.locationBlocksTexture);
      if(var2 != null) {
         MultiTexID var3 = var2.getMultiTexID();
         GlStateManager.bindTexture(var3.base);
         GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
         GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
         GlStateManager.bindTexture(var3.norm);
         GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
         GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
         GlStateManager.bindTexture(var3.spec);
         GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
         GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
         GlStateManager.bindTexture(0);
      }

   }

   public static IResource loadResource(IResourceManager var0, ResourceLocation var1) throws IOException {
      resManager = var0;
      resLocation = var1;
      return var0.getResource(var1);
   }

   public static int[] loadAtlasSprite(BufferedImage var0, int var1, int var2, int var3, int var4, int[] var5, int var6, int var7) {
      imageSize = var3 * var4;
      qT.b(var0, var1, var2, var3, var4, var5, var6, var7);
      loadNSMap(resManager, resLocation, var3, var4, var5);
      return var5;
   }

   public static int[][] getFrameTexData(int[][] var0, int var1, int var2, int var3) {
      ShaderOption.p();
      int var5 = var0.length;
      int[][] var6 = new int[var5][];
      int var7 = 0;
      if(var7 < var5) {
         int[] var8 = var0[var7];
         int var9 = (var1 >> var7) * (var2 >> var7);
         int[] var10 = new int[var9 * 3];
         var6[var7] = var10;
         int var11 = var8.length / 3;
         int var12 = var9 * var3;
         int var13 = 0;
         System.arraycopy(var8, var12, var10, var13, var9);
         var12 = var12 + var11;
         var13 = var13 + var9;
         System.arraycopy(var8, var12, var10, var13, var9);
         var12 = var12 + var11;
         var13 = var13 + var9;
         System.arraycopy(var8, var12, var10, var13, var9);
         ++var7;
      }

      return var6;
   }

   public static int[][] prepareAF(TextureAtlasSprite var0, int[][] var1, int var2, int var3) {
      boolean var4 = true;
      return var1;
   }

   public static void fixTransparentColor(TextureAtlasSprite var0, int[] var1) {
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
