package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.aYz;
import net.qT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.Reflector;
import net.optifine.TextureUtils;
import net.shadersmod.client.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
   private final List listAnimatedSprites;
   private final Map mapRegisteredSprites;
   private final Map mapUploadedSprites;
   private final String basePath;
   private final IIconCreator iconCreator;
   private int mipmapLevels;
   private final TextureAtlasSprite missingImage;
   private TextureAtlasSprite[] iconGrid;
   private final int iconGridSize;
   private int iconGridCountX;
   private int iconGridCountY;
   private double iconGridSizeU;
   private double iconGridSizeV;
   private int counterIndexInMap;
   public int atlasWidth;
   public int atlasHeight;

   public TextureMap(String var1) {
      this(var1, (IIconCreator)null);
   }

   public TextureMap(String var1, boolean var2) {
      this(var1, (IIconCreator)null, var2);
   }

   public TextureMap(String var1, IIconCreator var2) {
      this(var1, var2, false);
   }

   public TextureMap(String var1, IIconCreator var2, boolean var3) {
      this.iconGrid = null;
      this.iconGridSize = -1;
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGridSizeU = -1.0D;
      this.iconGridSizeV = -1.0D;
      this.counterIndexInMap = 0;
      this.atlasWidth = 0;
      this.atlasHeight = 0;
      this.listAnimatedSprites = Lists.newArrayList();
      this.mapRegisteredSprites = Maps.newHashMap();
      this.mapUploadedSprites = Maps.newHashMap();
      this.missingImage = new TextureAtlasSprite("missingno");
      this.basePath = var1;
      this.iconCreator = var2;
   }

   private void initMissingImage() {
      int var1 = this.getMinSpriteSize();
      int[] var2 = this.getMissingImageData(var1);
      this.missingImage.setIconWidth(var1);
      this.missingImage.setIconHeight(var1);
      int[][] var3 = new int[this.mipmapLevels + 1][];
      var3[0] = var2;
      this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][]{var3}));
      this.missingImage.setIndexInMap(this.counterIndexInMap++);
   }

   public void loadTexture(IResourceManager var1) throws IOException {
      ShadersTex.resManager = var1;
      if(this.iconCreator != null) {
         this.loadSprites(var1, this.iconCreator);
      }

   }

   public void loadSprites(IResourceManager var1, IIconCreator var2) {
      this.mapRegisteredSprites.clear();
      this.counterIndexInMap = 0;
      var2.registerSprites(this);
      if(this.mipmapLevels >= 4) {
         this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, var1);
         Config.log("Mipmap levels: " + this.mipmapLevels);
      }

      this.initMissingImage();
      this.deleteGlTexture();
      this.loadTextureAtlas(var1);
   }

   public void loadTextureAtlas(IResourceManager var1) {
      Config.dbg("Multitexture: " + Config.isMultiTexture());
      if(Config.isMultiTexture()) {
         for(Object var3 : this.mapUploadedSprites.values()) {
            ((TextureAtlasSprite)var3).deleteSpriteTexture();
         }
      }

      ConnectedTextures.updateIcons(this);
      int var27 = Minecraft.getGLMaximumTextureSize();
      Stitcher var28 = new Stitcher(var27, var27, true, 0, this.mipmapLevels);
      this.mapUploadedSprites.clear();
      this.listAnimatedSprites.clear();
      int var4 = Integer.MAX_VALUE;
      Reflector.a(Reflector.cy, new Object[]{this});
      int var5 = this.getMinSpriteSize();
      int var6 = 1 << this.mipmapLevels;

      for(Object var8 : this.mapRegisteredSprites.entrySet()) {
         TextureAtlasSprite var9 = (TextureAtlasSprite)((Entry)var8).getValue();
         ResourceLocation var10 = new ResourceLocation(var9.getIconName());
         ResourceLocation var11 = this.completeResourceLocation(var10, 0);
         if(!var9.hasCustomLoader(var1, var10)) {
            try {
               IResource var12 = var1.getResource(var11);
               BufferedImage[] var13 = new BufferedImage[1 + this.mipmapLevels];
               var13[0] = TextureUtil.a(var12.getInputStream());
               if(this.mipmapLevels > 0) {
                  int var14 = var13[0].getWidth();
                  var13[0] = TextureUtils.scaleToPowerOfTwo(var13[0], var5);
                  int var15 = var13[0].getWidth();
                  if(!TextureUtils.isPowerOfTwo(var14)) {
                     Config.log("Scaled non power of 2: " + var9.getIconName() + ", " + var14 + " -> " + var15);
                  }
               }

               TextureMetadataSection var48 = (TextureMetadataSection)var12.getMetadata("texture");
               List var51 = var48.getListMipmaps();
               if(!var51.isEmpty()) {
                  int var16 = var13[0].getWidth();
                  int var17 = var13[0].getHeight();
                  if(MathHelper.roundUpToPowerOfTwo(var16) != var16 || MathHelper.roundUpToPowerOfTwo(var17) != var17) {
                     throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                  }
               }

               for(Object var58 : var51) {
                  int var18 = ((Integer)var58).intValue();
                  if(var18 < var13.length - 1 && var13[var18] == null) {
                     ResourceLocation var19 = this.completeResourceLocation(var10, var18);

                     try {
                        var13[var18] = TextureUtil.a(var1.getResource(var19).getInputStream());
                     } catch (IOException var24) {
                        LOGGER.error("Unable to load mip-level {} from: {}", new Object[]{Integer.valueOf(var18), var19, var24});
                     }
                  }
               }

               AnimationMetadataSection var52 = (AnimationMetadataSection)var12.getMetadata("animation");
               var9.loadSprite(var13, var52);
            } catch (RuntimeException var25) {
               LOGGER.error("Unable to parse metadata from " + var11, var25);
               continue;
            } catch (IOException var26) {
               LOGGER.error("Using missing texture, unable to load " + var11 + ", " + var26.getClass().getName());
               continue;
            }

            var4 = Math.min(var4, Math.min(var9.getIconWidth(), var9.getIconHeight()));
            int var42 = Math.min(Integer.lowestOneBit(var9.getIconWidth()), Integer.lowestOneBit(var9.getIconHeight()));
            if(var42 < var6) {
               LOGGER.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{var11, Integer.valueOf(var9.getIconWidth()), Integer.valueOf(var9.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(var6)), Integer.valueOf(MathHelper.calculateLogBaseTwo(var42))});
               var6 = var42;
            }

            var28.addSprite(var9);
         } else if(!var9.load(var1, var10)) {
            var4 = Math.min(var4, Math.min(var9.getIconWidth(), var9.getIconHeight()));
            var28.addSprite(var9);
         }
      }

      int var29 = Math.min(var4, var6);
      int var30 = MathHelper.calculateLogBaseTwo(var29);
      var30 = 0;
      if(var30 < this.mipmapLevels) {
         LOGGER.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(var30), Integer.valueOf(var29)});
         this.mipmapLevels = var30;
      }

      for(Object var34 : this.mapRegisteredSprites.values()) {
         TextureAtlasSprite var38 = (TextureAtlasSprite)var34;
         TextureAtlasSprite var10000 = var38;
         TextureMap var10001 = this;

         try {
            var10000.generateMipmaps(var10001.mipmapLevels);
         } catch (Throwable var23) {
            CrashReport var45 = CrashReport.makeCrashReport(var23, "Applying mipmap");
            CrashReportCategory var49 = var45.makeCategory("Sprite being mipmapped");
            var49.addCrashSectionCallable("Sprite name", var38::getIconName);
            var49.addCrashSectionCallable("Sprite size", TextureMap::lambda$loadTextureAtlas$0);
            var49.addCrashSectionCallable("Sprite frames", TextureMap::lambda$loadTextureAtlas$1);
            var49.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
            throw new ReportedException(var45);
         }
      }

      this.missingImage.generateMipmaps(this.mipmapLevels);
      var28.addSprite(this.missingImage);
      Stitcher var60 = var28;

      try {
         var60.doStitch();
      } catch (StitcherException var22) {
         throw var22;
      }

      LOGGER.info("Created: {}x{} {}-atlas", new Object[]{Integer.valueOf(var28.getCurrentWidth()), Integer.valueOf(var28.getCurrentHeight()), this.basePath});
      TextureUtil.allocateTextureImpl(this.getGlTextureId(), this.mipmapLevels, var28.getCurrentWidth(), var28.getCurrentHeight());
      HashMap var33 = Maps.newHashMap(this.mapRegisteredSprites);

      for(Object var39 : var28.getStichSlots()) {
         TextureAtlasSprite var43 = (TextureAtlasSprite)var39;
         String var46 = var43.getIconName();
         var33.remove(var46);
         this.mapUploadedSprites.put(var46, var43);
         TextureAtlasSprite var61 = var43;
         byte var62 = 0;

         try {
            aYz.a(var61.getFrameTextureData(var62), var43.getIconWidth(), var43.getIconHeight(), var43.getOriginX(), var43.getOriginY(), false, false);
         } catch (Throwable var21) {
            CrashReport var53 = CrashReport.makeCrashReport(var21, "Stitching texture atlas");
            CrashReportCategory var56 = var53.makeCategory("Texture being stitched together");
            var56.addCrashSection("Atlas path", this.basePath);
            var56.addCrashSection("Sprite", var43);
            throw new ReportedException(var53);
         }

         if(var43.hasAnimationMetadata()) {
            this.listAnimatedSprites.add(var43);
         }
      }

      for(Object var40 : var33.values()) {
         ((TextureAtlasSprite)var40).copyFrom(this.missingImage);
      }

      if(Config.isMultiTexture()) {
         int var37 = var28.getCurrentWidth();
         int var41 = var28.getCurrentHeight();

         for(Object var47 : var28.getStichSlots()) {
            TextureAtlasSprite var50 = (TextureAtlasSprite)var47;
            var50.sheetWidth = var37;
            var50.sheetHeight = var41;
            var50.mipmapLevels = this.mipmapLevels;
            TextureAtlasSprite var54 = var50.spriteSingle;
            var54.sheetWidth = var37;
            var54.sheetHeight = var41;
            var54.mipmapLevels = this.mipmapLevels;
            var50.bindSpriteTexture();
            boolean var57 = false;
            boolean var59 = true;
            aYz.a(var54.getFrameTextureData(0), var54.getIconWidth(), var54.getIconHeight(), var54.getOriginX(), var54.getOriginY(), false, true);
         }

         Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
      }

      Reflector.a(Reflector.cG, new Object[]{this});
      if(Config.equals(System.getProperty("saveTextureMap"), "true")) {
         TextureUtil.saveGlTexture(this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, var28.getCurrentWidth(), var28.getCurrentHeight());
      }

   }

   public ResourceLocation completeResourceLocation(ResourceLocation var1, int var2) {
      return this.isAbsoluteLocation(var1)?new ResourceLocation(var1.getResourceDomain(), var1.getResourcePath() + ".png"):new ResourceLocation(var1.getResourceDomain(), String.format("%s/%s%s", new Object[]{this.basePath, var1.getResourcePath(), ".png"}));
   }

   public TextureAtlasSprite getAtlasSprite(String var1) {
      TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapUploadedSprites.get(var1);
      var2 = this.missingImage;
      return var2;
   }

   public void updateAnimations() {
      if(Config.isShaders()) {
         ShadersTex.updatingTex = this.getMultiTexID();
      }

      boolean var1 = false;
      boolean var2 = false;
      TextureUtil.bindTexture(this.getGlTextureId());

      for(Object var4 : this.listAnimatedSprites) {
         TextureAtlasSprite var5 = (TextureAtlasSprite)var4;
         if(this.isTerrainAnimationActive(var5)) {
            var5.updateAnimation();
            if(var5.spriteNormal != null) {
               var1 = true;
            }

            if(var5.spriteSpecular != null) {
               var2 = true;
            }
         }
      }

      if(Config.isMultiTexture()) {
         for(Object var12 : this.listAnimatedSprites) {
            TextureAtlasSprite var15 = (TextureAtlasSprite)var12;
            if(this.isTerrainAnimationActive(var15)) {
               TextureAtlasSprite var6 = var15.spriteSingle;
               if(var15 == TextureUtils.iconClock || var15 == TextureUtils.iconCompass) {
                  var6.frameCounter = var15.frameCounter;
               }

               var15.bindSpriteTexture();
               var6.updateAnimation();
            }
         }

         TextureUtil.bindTexture(this.getGlTextureId());
      }

      if(Config.isShaders()) {
         TextureUtil.bindTexture(this.getMultiTexID().norm);

         for(Object var13 : this.listAnimatedSprites) {
            TextureAtlasSprite var16 = (TextureAtlasSprite)var13;
            if(var16.spriteNormal != null && this.isTerrainAnimationActive(var16)) {
               if(var16 == TextureUtils.iconClock || var16 == TextureUtils.iconCompass) {
                  var16.spriteNormal.frameCounter = var16.frameCounter;
               }

               var16.spriteNormal.updateAnimation();
            }
         }

         TextureUtil.bindTexture(this.getMultiTexID().spec);

         for(Object var14 : this.listAnimatedSprites) {
            TextureAtlasSprite var17 = (TextureAtlasSprite)var14;
            if(var17.spriteSpecular != null && this.isTerrainAnimationActive(var17)) {
               if(var17 == TextureUtils.iconClock || var17 == TextureUtils.iconCompass) {
                  var17.spriteNormal.frameCounter = var17.frameCounter;
               }

               var17.spriteSpecular.updateAnimation();
            }
         }

         TextureUtil.bindTexture(this.getGlTextureId());
      }

      if(Config.isShaders()) {
         ShadersTex.updatingTex = null;
      }

   }

   public TextureAtlasSprite b(ResourceLocation var1) {
      throw new IllegalArgumentException("Location cannot be null!");
   }

   public void tick() {
      this.updateAnimations();
   }

   public TextureAtlasSprite getMissingSprite() {
      return this.missingImage;
   }

   public TextureAtlasSprite getTextureExtry(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return (TextureAtlasSprite)this.mapRegisteredSprites.get(var2.toString());
   }

   public boolean setTextureEntry(String var1, TextureAtlasSprite var2) {
      if(!this.mapRegisteredSprites.containsKey(var1)) {
         this.mapRegisteredSprites.put(var1, var2);
         if(var2.getIndexInMap() < 0) {
            var2.setIndexInMap(this.counterIndexInMap++);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean setTextureEntry(TextureAtlasSprite var1) {
      return this.setTextureEntry(var1.getIconName(), var1);
   }

   public String getBasePath() {
      return this.basePath;
   }

   public int getMipmapLevels() {
      return this.mipmapLevels;
   }

   public void setMipmapLevels(int var1) {
      this.mipmapLevels = var1;
   }

   private boolean isAbsoluteLocation(ResourceLocation var1) {
      String var2 = var1.getResourcePath();
      return this.isAbsoluteLocationPath(var2);
   }

   private boolean isAbsoluteLocationPath(String var1) {
      String var2 = var1.toLowerCase();
      return var2.startsWith("mcpatcher/") || var2.startsWith("optifine/");
   }

   public TextureAtlasSprite getSpriteSafe(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return (TextureAtlasSprite)this.mapRegisteredSprites.get(var2.toString());
   }

   private boolean isTerrainAnimationActive(TextureAtlasSprite var1) {
      return var1 != TextureUtils.iconWaterStill && var1 != TextureUtils.iconWaterFlow?(var1 != TextureUtils.iconLavaStill && var1 != TextureUtils.iconLavaFlow?(var1 != TextureUtils.iconFireLayer0 && var1 != TextureUtils.iconFireLayer1?(var1 == TextureUtils.iconPortal?Config.isAnimatedPortal():var1 == TextureUtils.iconClock || var1 == TextureUtils.iconCompass || Config.isAnimatedTerrain()):Config.isAnimatedFire()):Config.i()):Config.a();
   }

   public int getCountRegisteredSprites() {
      return this.counterIndexInMap;
   }

   private int detectMaxMipmapLevel(Map var1, IResourceManager var2) {
      int var3 = this.detectMinimumSpriteSize(var1, var2, 20);
      if(var3 < 16) {
         var3 = 16;
      }

      var3 = MathHelper.roundUpToPowerOfTwo(var3);
      if(var3 > 16) {
         Config.log("Sprite size: " + var3);
      }

      int var4 = MathHelper.calculateLogBaseTwo(var3);
      if(var4 < 4) {
         var4 = 4;
      }

      return var4;
   }

   private int detectMinimumSpriteSize(Map var1, IResourceManager var2, int var3) {
      HashMap var4 = new HashMap();

      for(Object var6 : var1.entrySet()) {
         TextureAtlasSprite var7 = (TextureAtlasSprite)((Entry)var6).getValue();
         ResourceLocation var8 = new ResourceLocation(var7.getIconName());
         ResourceLocation var9 = this.completeResourceLocation(var8, 0);
         if(!var7.hasCustomLoader(var2, var8)) {
            try {
               IResource var10 = var2.getResource(var9);
               InputStream var11 = var10.getInputStream();
               Dimension var12 = TextureUtils.getImageSize(var11, "png");
               int var13 = var12.width;
               int var14 = MathHelper.roundUpToPowerOfTwo(var13);
               if(!var4.containsKey(Integer.valueOf(var14))) {
                  var4.put(Integer.valueOf(var14), Integer.valueOf(1));
               } else {
                  int var15 = ((Integer)var4.get(Integer.valueOf(var14))).intValue();
                  var4.put(Integer.valueOf(var14), Integer.valueOf(var15 + 1));
               }
            } catch (Exception var16) {
               ;
            }
         }
      }

      int var17 = 0;
      Set var18 = var4.keySet();
      TreeSet var19 = new TreeSet(var18);

      int var20;
      for(Iterator var22 = var19.iterator(); var22.hasNext(); var17 += var20) {
         int var24 = ((Integer)var22.next()).intValue();
         var20 = ((Integer)var4.get(Integer.valueOf(var24))).intValue();
      }

      int var23 = 16;
      int var25 = 0;
      var20 = var17 * var3 / 100;

      for(Object var27 : var19) {
         int var28 = ((Integer)var27).intValue();
         int var29 = ((Integer)var4.get(Integer.valueOf(var28))).intValue();
         var25 += var29;
         if(var28 > var23) {
            var23 = var28;
         }

         if(var25 > var20) {
            return var23;
         }
      }

      return var23;
   }

   private int getMinSpriteSize() {
      int var1 = 1 << this.mipmapLevels;
      if(var1 < 8) {
         var1 = 8;
      }

      return var1;
   }

   private int[] getMissingImageData(int var1) {
      BufferedImage var2 = new BufferedImage(16, 16, 2);
      qT.a(var2, 0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
      BufferedImage var3 = TextureUtils.scaleToPowerOfTwo(var2, var1);
      int[] var4 = new int[var1 * var1];
      qT.b(var3, 0, 0, var1, var1, var4, 0, var1);
      return var4;
   }

   public boolean isTextureBound() {
      int var1 = GlStateManager.getBoundTexture();
      int var2 = this.getGlTextureId();
      return var1 == var2;
   }

   private void updateIconGrid(int var1, int var2) {
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGrid = null;
      if(this.iconGridSize > 0) {
         this.iconGridCountX = var1 / this.iconGridSize;
         this.iconGridCountY = var2 / this.iconGridSize;
         this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
         this.iconGridSizeU = 1.0D / (double)this.iconGridCountX;
         this.iconGridSizeV = 1.0D / (double)this.iconGridCountY;

         for(Object var4 : this.mapUploadedSprites.values()) {
            TextureAtlasSprite var5 = (TextureAtlasSprite)var4;
            double var6 = 0.5D / (double)var1;
            double var8 = 0.5D / (double)var2;
            double var10 = (double)Math.min(var5.getMinU(), var5.getMaxU()) + var6;
            double var12 = (double)Math.min(var5.getMinV(), var5.getMaxV()) + var8;
            double var14 = (double)Math.max(var5.getMinU(), var5.getMaxU()) - var6;
            double var16 = (double)Math.max(var5.getMinV(), var5.getMaxV()) - var8;
            int var18 = (int)(var10 / this.iconGridSizeU);
            int var19 = (int)(var12 / this.iconGridSizeV);
            int var20 = (int)(var14 / this.iconGridSizeU);
            int var21 = (int)(var16 / this.iconGridSizeV);

            for(int var22 = var18; var22 <= var20; ++var22) {
               if(var22 < this.iconGridCountX) {
                  for(int var23 = var19; var23 <= var21; ++var23) {
                     if(var23 < this.iconGridCountX) {
                        int var24 = var23 * this.iconGridCountX + var22;
                        this.iconGrid[var24] = var5;
                     } else {
                        Config.warn("Invalid grid V: " + var23 + ", icon: " + var5.getIconName());
                     }
                  }
               } else {
                  Config.warn("Invalid grid U: " + var22 + ", icon: " + var5.getIconName());
               }
            }
         }
      }

   }

   public TextureAtlasSprite getIconByUV(double var1, double var3) {
      if(this.iconGrid == null) {
         return null;
      } else {
         int var5 = (int)(var1 / this.iconGridSizeU);
         int var6 = (int)(var3 / this.iconGridSizeV);
         int var7 = var6 * this.iconGridCountX + var5;
         return var7 <= this.iconGrid.length?this.iconGrid[var7]:null;
      }
   }

   private static String lambda$loadTextureAtlas$1(TextureAtlasSprite var0) throws Exception {
      return var0.getFrameCount() + " frames";
   }

   private static String lambda$loadTextureAtlas$0(TextureAtlasSprite var0) throws Exception {
      return var0.getIconWidth() + " x " + var0.getIconHeight();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
