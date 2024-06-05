package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import net.aYz;
import net.qT;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.TextureUtils;
import net.shadersmod.client.Shaders;

public class TextureAtlasSprite {
   private final String iconName;
   protected List framesTextureData = Lists.newArrayList();
   protected int[][] interpolatedFrameData;
   private AnimationMetadataSection animationMetadata;
   protected boolean rotated;
   protected int originX;
   protected int originY;
   protected int width;
   protected int height;
   private float minU;
   private float maxU;
   private float minV;
   private float maxV;
   protected int frameCounter;
   protected int tickCounter;
   private static String locationNameClock = "builtin/clock";
   private static String locationNameCompass = "builtin/compass";
   private static final String n = "CL_00001062";
   private int indexInMap = -1;
   public float baseU;
   public float baseV;
   public int sheetWidth;
   public int sheetHeight;
   public int glSpriteTextureId = -1;
   public TextureAtlasSprite spriteSingle = null;
   public boolean isSpriteSingle = false;
   public int mipmapLevels = 0;
   public TextureAtlasSprite spriteNormal = null;
   public TextureAtlasSprite spriteSpecular = null;
   public boolean isShadersSprite = false;

   private TextureAtlasSprite(TextureAtlasSprite var1) {
      this.iconName = var1.iconName;
      this.isSpriteSingle = true;
   }

   protected TextureAtlasSprite(String var1) {
      this.iconName = var1;
      if(Config.isMultiTexture()) {
         this.spriteSingle = new TextureAtlasSprite(this);
      }

   }

   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation var0) {
      String var1 = var0.toString();
      return (TextureAtlasSprite)(locationNameClock.equals(var1)?new TextureClock(var1):(locationNameCompass.equals(var1)?new TextureCompass(var1):new TextureAtlasSprite(var1)));
   }

   public static void setLocationNameClock(String var0) {
      locationNameClock = var0;
   }

   public static void setLocationNameCompass(String var0) {
      locationNameCompass = var0;
   }

   public void initSprite(int var1, int var2, int var3, int var4, boolean var5) {
      this.originX = var3;
      this.originY = var4;
      this.rotated = var5;
      float var6 = (float)(0.009999999776482582D / (double)var1);
      float var7 = (float)(0.009999999776482582D / (double)var2);
      this.minU = (float)var3 / (float)((double)var1) + var6;
      this.maxU = (float)(var3 + this.width) / (float)((double)var1) - var6;
      this.minV = (float)var4 / (float)var2 + var7;
      this.maxV = (float)(var4 + this.height) / (float)var2 - var7;
      this.baseU = Math.min(this.minU, this.maxU);
      this.baseV = Math.min(this.minV, this.maxV);
      if(this.spriteSingle != null) {
         this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
      }

      if(this.spriteNormal != null) {
         this.spriteNormal.initSprite(var1, var2, var3, var4, var5);
      }

      if(this.spriteSpecular != null) {
         this.spriteSpecular.initSprite(var1, var2, var3, var4, var5);
      }

   }

   public void copyFrom(TextureAtlasSprite var1) {
      this.originX = var1.originX;
      this.originY = var1.originY;
      this.width = var1.width;
      this.height = var1.height;
      this.rotated = var1.rotated;
      this.minU = var1.minU;
      this.maxU = var1.maxU;
      this.minV = var1.minV;
      this.maxV = var1.maxV;
      if(this.spriteSingle != null) {
         this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
      }

   }

   public int getOriginX() {
      return this.originX;
   }

   public int getOriginY() {
      return this.originY;
   }

   public int getIconWidth() {
      return this.width;
   }

   public void setIconWidth(int var1) {
      this.width = var1;
      if(this.spriteSingle != null) {
         this.spriteSingle.setIconWidth(this.width);
      }

   }

   public int getIconHeight() {
      return this.height;
   }

   public void setIconHeight(int var1) {
      this.height = var1;
      if(this.spriteSingle != null) {
         this.spriteSingle.setIconHeight(this.height);
      }

   }

   public float getMinU() {
      return this.minU;
   }

   public float getMaxU() {
      return this.maxU;
   }

   public float getInterpolatedU(double var1) {
      float var3 = this.maxU - this.minU;
      return this.minU + var3 * (float)var1 / 16.0F;
   }

   public float getMinV() {
      return this.minV;
   }

   public float getMaxV() {
      return this.maxV;
   }

   public float getInterpolatedV(double var1) {
      float var3 = this.maxV - this.minV;
      return this.minV + var3 * ((float)var1 / 16.0F);
   }

   public String getIconName() {
      return this.iconName;
   }

   public void updateAnimation() {
      if(this.animationMetadata != null) {
         ++this.tickCounter;
         if(this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
            int var2 = this.animationMetadata.getFrameCount() == 0?this.framesTextureData.size():this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % var2;
            this.tickCounter = 0;
            int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
            boolean var4 = false;
            boolean var5 = this.isSpriteSingle;
            if(var1 != var3 && var3 < this.framesTextureData.size()) {
               aYz.a((int[][])((int[][])this.framesTextureData.get(var3)), this.width, this.height, this.originX, this.originY, var4, var5);
            }
         } else if(this.animationMetadata.isInterpolate()) {
            this.updateAnimationInterpolated();
         }
      }

   }

   private void updateAnimationInterpolated() {
      double var1 = 1.0D - (double)this.tickCounter / (double)this.animationMetadata.getFrameTimeSingle(this.frameCounter);
      int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
      int var4 = this.animationMetadata.getFrameCount() == 0?this.framesTextureData.size():this.animationMetadata.getFrameCount();
      int var5 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % var4);
      if(var3 != var5 && var5 < this.framesTextureData.size()) {
         int[][] var6 = (int[][])((int[][])this.framesTextureData.get(var3));
         int[][] var7 = (int[][])((int[][])this.framesTextureData.get(var5));
         if(this.interpolatedFrameData == null || this.interpolatedFrameData.length != var6.length) {
            this.interpolatedFrameData = new int[var6.length][];
         }

         for(int var8 = 0; var8 < var6.length; ++var8) {
            if(this.interpolatedFrameData[var8] == null) {
               this.interpolatedFrameData[var8] = new int[var6[var8].length];
            }

            if(var8 < var7.length && var7[var8].length == var6[var8].length) {
               for(int var9 = 0; var9 < var6[var8].length; ++var9) {
                  int var10 = var6[var8][var9];
                  int var11 = var7[var8][var9];
                  int var12 = (int)((double)((var10 & 16711680) >> 16) * var1 + (double)((var11 & 16711680) >> 16) * (1.0D - var1));
                  int var13 = (int)((double)((var10 & '\uff00') >> 8) * var1 + (double)((var11 & '\uff00') >> 8) * (1.0D - var1));
                  int var14 = (int)((double)(var10 & 255) * var1 + (double)(var11 & 255) * (1.0D - var1));
                  this.interpolatedFrameData[var8][var9] = var10 & -16777216 | var12 << 16 | var13 << 8 | var14;
               }
            }
         }

         aYz.a(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
      }

   }

   public int[][] getFrameTextureData(int var1) {
      return (int[][])((int[][])this.framesTextureData.get(var1));
   }

   public int getFrameCount() {
      return this.framesTextureData.size();
   }

   public void loadSprite(BufferedImage[] var1, AnimationMetadataSection var2) throws IOException {
      this.resetSprite();
      int var3 = var1[0].getWidth();
      int var4 = var1[0].getHeight();
      this.width = var3;
      this.height = var4;
      int[][] var5 = new int[var1.length][];

      for(int var6 = 0; var6 < var1.length; ++var6) {
         BufferedImage var7 = var1[var6];
         if(var7.getWidth() != var3 >> var6 || var7.getHeight() != var4 >> var6) {
            throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[]{Integer.valueOf(var6), Integer.valueOf(var7.getWidth()), Integer.valueOf(var7.getHeight()), Integer.valueOf(var3 >> var6), Integer.valueOf(var4 >> var6)}));
         }

         var5[var6] = new int[var7.getWidth() * var7.getHeight()];
         qT.b(var7, 0, 0, var7.getWidth(), var7.getHeight(), var5[var6], 0, var7.getWidth());
      }

      if(var4 != var3) {
         throw new RuntimeException("broken aspect ratio and not an animation");
      } else {
         this.framesTextureData.add(var5);
         if(!this.isShadersSprite) {
            if(Config.isShaders()) {
               this.loadShadersSprites();
            }

            for(Object var14 : this.framesTextureData) {
               int[][] var8 = (int[][])((int[][])var14);
               if(!this.iconName.startsWith("minecraft:blocks/leaves_")) {
                  for(int[] var12 : var8) {
                     this.fixTransparentColor(var12);
                  }
               }
            }

            if(this.spriteSingle != null) {
               this.spriteSingle.loadSprite(var1, var2);
            }
         }

      }
   }

   public void generateMipmaps(int var1) {
      ArrayList var2 = Lists.newArrayList();

      for(int var3 = 0; var3 < this.framesTextureData.size(); ++var3) {
         int[][] var4 = (int[][])((int[][])this.framesTextureData.get(var3));
         ArrayList var10000 = var2;
         int var10001 = var1;
         TextureAtlasSprite var10002 = this;

         try {
            var10000.add(TextureUtil.generateMipmapData(var10001, var10002.width, var4));
         } catch (Throwable var8) {
            CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
            CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
            var7.addCrashSection("Frame index", Integer.valueOf(var3));
            var7.addCrashSectionCallable("Frame sizes", TextureAtlasSprite::lambda$generateMipmaps$0);
            throw new ReportedException(var6);
         }
      }

      this.setFramesTextureData(var2);
      if(this.spriteSingle != null) {
         this.spriteSingle.generateMipmaps(var1);
      }

   }

   private void allocateFrameTextureData(int var1) {
      if(this.framesTextureData.size() <= var1) {
         for(int var2 = this.framesTextureData.size(); var2 <= var1; ++var2) {
            this.framesTextureData.add((Object)null);
         }
      }

      if(this.spriteSingle != null) {
         this.spriteSingle.allocateFrameTextureData(var1);
      }

   }

   private static int[][] getFrameTextureData(int[][] var0, int var1, int var2, int var3) {
      int[][] var4 = new int[var0.length][];

      for(int var5 = 0; var5 < var0.length; ++var5) {
         int[] var6 = var0[var5];
         var4[var5] = new int[(var1 >> var5) * (var2 >> var5)];
         System.arraycopy(var6, var3 * var4[var5].length, var4[var5], 0, var4[var5].length);
      }

      return var4;
   }

   public void clearFramesTextureData() {
      this.framesTextureData.clear();
      if(this.spriteSingle != null) {
         this.spriteSingle.clearFramesTextureData();
      }

   }

   public boolean hasAnimationMetadata() {
      return this.animationMetadata != null;
   }

   private void resetSprite() {
      this.animationMetadata = null;
      this.setFramesTextureData(Lists.newArrayList());
      this.frameCounter = 0;
      this.tickCounter = 0;
      if(this.spriteSingle != null) {
         this.spriteSingle.resetSprite();
      }

   }

   public String toString() {
      return "TextureAtlasSprite{name=\'" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
   }

   public boolean hasCustomLoader(IResourceManager var1, ResourceLocation var2) {
      return false;
   }

   public boolean load(IResourceManager var1, ResourceLocation var2) {
      return true;
   }

   public int getIndexInMap() {
      return this.indexInMap;
   }

   public void setIndexInMap(int var1) {
      this.indexInMap = var1;
   }

   private void fixTransparentColor(int[] var1) {
      long var2 = 0L;
      long var4 = 0L;
      long var6 = 0L;
      long var8 = 0L;

      for(int var13 : var1) {
         int var14 = var13 >> 24 & 255;
         if(var14 >= 16) {
            int var15 = var13 >> 16 & 255;
            int var16 = var13 >> 8 & 255;
            int var17 = var13 & 255;
            var2 += (long)var15;
            var4 += (long)var16;
            var6 += (long)var17;
            ++var8;
         }
      }

      if(var8 > 0L) {
         int var18 = (int)(var2 / var8);
         int var19 = (int)(var4 / var8);
         int var20 = (int)(var6 / var8);
         int var21 = var18 << 16 | var19 << 8 | var20;

         for(int var22 = 0; var22 < var1.length; ++var22) {
            int var23 = var1[var22];
            int var24 = var23 >> 24 & 255;
            if(var24 <= 16) {
               var1[var22] = var21;
            }
         }
      }

   }

   public double getSpriteU16(float var1) {
      float var2 = this.maxU - this.minU;
      return (double)((var1 - this.minU) / var2 * 16.0F);
   }

   public double getSpriteV16(float var1) {
      float var2 = this.maxV - this.minV;
      return (double)((var1 - this.minV) / var2 * 16.0F);
   }

   public void bindSpriteTexture() {
      if(this.glSpriteTextureId < 0) {
         this.glSpriteTextureId = TextureUtil.glGenTextures();
         TextureUtil.allocateTextureImpl(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
         TextureUtils.applyAnisotropicLevel();
      }

      TextureUtils.bindTexture(this.glSpriteTextureId);
   }

   public void deleteSpriteTexture() {
      if(this.glSpriteTextureId >= 0) {
         TextureUtil.deleteTexture(this.glSpriteTextureId);
         this.glSpriteTextureId = -1;
      }

   }

   public float toSingleU(float var1) {
      var1 = var1 - this.baseU;
      float var2 = (float)this.sheetWidth / (float)this.width;
      var1 = var1 * var2;
      return var1;
   }

   public float toSingleV(float var1) {
      var1 = var1 - this.baseV;
      float var2 = (float)this.sheetHeight / (float)this.height;
      var1 = var1 * var2;
      return var1;
   }

   public List getFramesTextureData() {
      ArrayList var1 = new ArrayList();
      var1.addAll(this.framesTextureData);
      return var1;
   }

   public void setFramesTextureData(List var1) {
      this.framesTextureData = var1;
      if(this.spriteSingle != null) {
         this.spriteSingle.setFramesTextureData(var1);
      }

   }

   public AnimationMetadataSection getAnimationMetadata() {
      return this.animationMetadata;
   }

   public void setAnimationMetadata(AnimationMetadataSection var1) {
      this.animationMetadata = var1;
   }

   private void loadShadersSprites() {
      this.mipmapLevels = Config.getTextureMap().getMipmapLevels();
      if(Shaders.configNormalMap) {
         String var1 = this.iconName + "_n";
         ResourceLocation var2 = new ResourceLocation(var1);
         var2 = Config.getTextureMap().completeResourceLocation(var2, 0);
         if(Config.hasResource(var2)) {
            try {
               TextureAtlasSprite var3 = new TextureAtlasSprite(var1);
               var3.isShadersSprite = true;
               var3.copyFrom(this);
               var3.a(var2, this.mipmapLevels + 1);
               var3.generateMipmaps(this.mipmapLevels);
               this.spriteNormal = var3;
            } catch (IOException var5) {
               Config.warn("Error loading normal texture: " + var1);
               Config.warn(var5.getClass().getName() + ": " + var5.getMessage());
            }
         }
      }

      if(Shaders.configSpecularMap) {
         String var6 = this.iconName + "_s";
         ResourceLocation var8 = new ResourceLocation(var6);
         var8 = Config.getTextureMap().completeResourceLocation(var8, 0);
         if(Config.hasResource(var8)) {
            try {
               TextureAtlasSprite var10 = new TextureAtlasSprite(var6);
               var10.isShadersSprite = true;
               var10.copyFrom(this);
               var10.a(var8, this.mipmapLevels + 1);
               var10.generateMipmaps(this.mipmapLevels);
               this.spriteSpecular = var10;
            } catch (IOException var4) {
               Config.warn("Error loading specular texture: " + var6);
               Config.warn(var4.getClass().getName() + ": " + var4.getMessage());
            }
         }
      }

   }

   public void a(ResourceLocation var1, int var2) throws IOException {
      IResource var3 = Config.getResource(var1);
      BufferedImage var4 = TextureUtil.a(var3.getInputStream());
      if(this.width != var4.getWidth()) {
         var4 = TextureUtils.scaleImage(var4, this.width);
      }

      AnimationMetadataSection var5 = (AnimationMetadataSection)var3.getMetadata("animation");
      int[][] var6 = new int[var2][];
      var6[0] = new int[var4.getWidth() * var4.getHeight()];
      qT.b(var4, 0, 0, var4.getWidth(), var4.getHeight(), var6[0], 0, var4.getWidth());
      this.framesTextureData.add(var6);
   }

   private static String lambda$generateMipmaps$0(int[][] var0) throws Exception {
      StringBuilder var1 = new StringBuilder();

      for(int[] var5 : var0) {
         if(var1.length() > 0) {
            var1.append(", ");
         }

         var1.append("null");
      }

      return var1.toString();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
