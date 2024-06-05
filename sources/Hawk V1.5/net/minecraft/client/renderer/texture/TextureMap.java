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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.ConnectedTextures;
import optifine.CustomItems;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadersmod.client.ShadersTex;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private int iconGridCountX;
   private int mipmapLevels;
   private TextureAtlasSprite[] iconGrid;
   private final String basePath;
   private int iconGridSize;
   private double iconGridSizeV;
   public int atlasWidth;
   private boolean skipFirst;
   private final TextureAtlasSprite missingImage;
   private final Map mapRegisteredSprites;
   public int atlasHeight;
   private final List listAnimatedSprites;
   private static final Logger logger = LogManager.getLogger();
   private double iconGridSizeU;
   private final Map mapUploadedSprites;
   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
   private final IIconCreator field_174946_m;
   private static final String __OBFID = "CL_00001058";
   public static final ResourceLocation field_174945_f = new ResourceLocation("missingno");
   private int iconGridCountY;
   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));

   public TextureAtlasSprite getAtlasSprite(String var1) {
      TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapUploadedSprites.get(var1);
      if (var2 == null) {
         var2 = this.missingImage;
      }

      return var2;
   }

   public TextureMap(String var1) {
      this(var1, (IIconCreator)null);
   }

   private boolean isAbsoluteLocation(ResourceLocation var1) {
      String var2 = var1.getResourcePath();
      return this.isAbsoluteLocationPath(var2);
   }

   private int detectMaxMipmapLevel(Map var1, IResourceManager var2) {
      int var3 = this.detectMinimumSpriteSize(var1, var2, 20);
      if (var3 < 16) {
         var3 = 16;
      }

      var3 = MathHelper.roundUpToPowerOfTwo(var3);
      if (var3 > 16) {
         Config.log(String.valueOf((new StringBuilder("Sprite size: ")).append(var3)));
      }

      int var4 = MathHelper.calculateLogBaseTwo(var3);
      if (var4 < 4) {
         var4 = 4;
      }

      return var4;
   }

   public boolean isTextureBound() {
      int var1 = GlStateManager.getBoundTexture();
      int var2 = this.getGlTextureId();
      return var1 == var2;
   }

   public void loadTextureAtlas(IResourceManager var1) {
      ShadersTex.resManager = var1;
      Config.dbg(String.valueOf((new StringBuilder("Multitexture: ")).append(Config.isMultiTexture())));
      if (Config.isMultiTexture()) {
         Iterator var2 = this.mapUploadedSprites.values().iterator();

         while(var2.hasNext()) {
            TextureAtlasSprite var3 = (TextureAtlasSprite)var2.next();
            var3.deleteSpriteTexture();
         }
      }

      ConnectedTextures.updateIcons(this);
      CustomItems.updateIcons(this);
      int var28 = Minecraft.getGLMaximumTextureSize();
      Stitcher var29 = new Stitcher(var28, var28, true, 0, this.mipmapLevels);
      this.mapUploadedSprites.clear();
      this.listAnimatedSprites.clear();
      int var4 = Integer.MAX_VALUE;
      Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this);
      int var5 = this.getMinSpriteSize();
      this.iconGridSize = var5;
      int var6 = 1 << this.mipmapLevels;
      Iterator var7 = this.mapRegisteredSprites.entrySet().iterator();

      int var8;
      List var9;
      int var16;
      while(var7.hasNext() && !this.skipFirst) {
         Entry var10 = (Entry)var7.next();
         TextureAtlasSprite var11 = (TextureAtlasSprite)var10.getValue();
         ResourceLocation var12 = new ResourceLocation(var11.getIconName());
         ResourceLocation var13 = this.completeResourceLocation(var12, 0);
         if (var11.hasCustomLoader(var1, var12)) {
            if (!var11.load(var1, var12)) {
               var4 = Math.min(var4, Math.min(var11.getIconWidth(), var11.getIconHeight()));
               var29.addSprite(var11);
            }

            Config.dbg(String.valueOf((new StringBuilder("Custom loader: ")).append(var11)));
         } else {
            try {
               IResource var14 = ShadersTex.loadResource(var1, var13);
               BufferedImage[] var15 = new BufferedImage[1 + this.mipmapLevels];
               var15[0] = TextureUtil.func_177053_a(var14.getInputStream());
               if (var15 != null) {
                  var8 = var15[0].getWidth();
                  if (var8 < var5 || this.mipmapLevels > 0) {
                     var15[0] = this.mipmapLevels > 0 ? TextureUtils.scaleToPowerOfTwo(var15[0], var5) : TextureUtils.scaleMinTo(var15[0], var5);
                     var16 = var15[0].getWidth();
                     if (var16 != var8) {
                        if (!TextureUtils.isPowerOfTwo(var8)) {
                           Config.log(String.valueOf((new StringBuilder("Scaled non power of 2: ")).append(var11.getIconName()).append(", ").append(var8).append(" -> ").append(var16)));
                        } else {
                           Config.log(String.valueOf((new StringBuilder("Scaled too small texture: ")).append(var11.getIconName()).append(", ").append(var8).append(" -> ").append(var16)));
                        }
                     }
                  }
               }

               TextureMetadataSection var39 = (TextureMetadataSection)var14.getMetadata("texture");
               if (var39 != null) {
                  var9 = var39.getListMipmaps();
                  int var17;
                  if (!var9.isEmpty()) {
                     int var18 = var15[0].getWidth();
                     var17 = var15[0].getHeight();
                     if (MathHelper.roundUpToPowerOfTwo(var18) != var18 || MathHelper.roundUpToPowerOfTwo(var17) != var17) {
                        throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                     }
                  }

                  Iterator var44 = var9.iterator();

                  while(var44.hasNext()) {
                     var17 = (Integer)var44.next();
                     if (var17 > 0 && var17 < var15.length - 1 && var15[var17] == null) {
                        ResourceLocation var19 = this.completeResourceLocation(var12, var17);

                        try {
                           var15[var17] = TextureUtil.func_177053_a(ShadersTex.loadResource(var1, var19).getInputStream());
                        } catch (IOException var25) {
                           logger.error("Unable to load miplevel {} from: {}", new Object[]{var17, var19, var25});
                        }
                     }
                  }
               }

               AnimationMetadataSection var42 = (AnimationMetadataSection)var14.getMetadata("animation");
               var11.func_180598_a(var15, var42);
            } catch (RuntimeException var26) {
               logger.error(String.valueOf((new StringBuilder("Unable to parse metadata from ")).append(var13)), var26);
               ReflectorForge.FMLClientHandler_trackBrokenTexture(var13, var26.getMessage());
               continue;
            } catch (IOException var27) {
               logger.error(String.valueOf((new StringBuilder("Using missing texture, unable to load ")).append(var13).append(", ").append(var27.getClass().getName())));
               ReflectorForge.FMLClientHandler_trackMissingTexture(var13);
               continue;
            }

            var4 = Math.min(var4, Math.min(var11.getIconWidth(), var11.getIconHeight()));
            int var35 = Math.min(Integer.lowestOneBit(var11.getIconWidth()), Integer.lowestOneBit(var11.getIconHeight()));
            if (var35 < var6) {
               logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{var13, var11.getIconWidth(), var11.getIconHeight(), MathHelper.calculateLogBaseTwo(var6), MathHelper.calculateLogBaseTwo(var35)});
               var6 = var35;
            }

            var29.addSprite(var11);
         }
      }

      int var30 = Math.min(var4, var6);
      int var31 = MathHelper.calculateLogBaseTwo(var30);
      if (var31 < 0) {
         var31 = 0;
      }

      if (var31 < this.mipmapLevels) {
         logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.basePath, this.mipmapLevels, var31, var30});
         this.mipmapLevels = var31;
      }

      Iterator var32 = this.mapRegisteredSprites.values().iterator();

      while(var32.hasNext() && !this.skipFirst) {
         TextureAtlasSprite var33 = (TextureAtlasSprite)var32.next();

         try {
            var33.generateMipmaps(this.mipmapLevels);
         } catch (Throwable var24) {
            CrashReport var37 = CrashReport.makeCrashReport(var24, "Applying mipmap");
            CrashReportCategory var40 = var37.makeCategory("Sprite being mipmapped");
            var40.addCrashSectionCallable("Sprite name", new Callable(this, var33) {
               private final TextureAtlasSprite val$var281;
               private static final String __OBFID = "CL_00001059";
               final TextureMap this$0;

               public String call() {
                  return this.val$var281.getIconName();
               }

               public Object call() throws Exception {
                  return this.call();
               }

               {
                  this.this$0 = var1;
                  this.val$var281 = var2;
               }
            });
            var40.addCrashSectionCallable("Sprite size", new Callable(this, var33) {
               private static final String __OBFID = "CL_00001060";
               private final TextureAtlasSprite val$var281;
               final TextureMap this$0;

               public String call() {
                  return String.valueOf((new StringBuilder(String.valueOf(this.val$var281.getIconWidth()))).append(" x ").append(this.val$var281.getIconHeight()));
               }

               {
                  this.this$0 = var1;
                  this.val$var281 = var2;
               }

               public Object call() throws Exception {
                  return this.call();
               }
            });
            var40.addCrashSectionCallable("Sprite frames", new Callable(this, var33) {
               private static final String __OBFID = "CL_00001061";
               final TextureMap this$0;
               private final TextureAtlasSprite val$var281;

               public String call() {
                  return String.valueOf((new StringBuilder(String.valueOf(this.val$var281.getFrameCount()))).append(" frames"));
               }

               public Object call() throws Exception {
                  return this.call();
               }

               {
                  this.this$0 = var1;
                  this.val$var281 = var2;
               }
            });
            var40.addCrashSection("Mipmap levels", this.mipmapLevels);
            throw new ReportedException(var37);
         }
      }

      this.missingImage.generateMipmaps(this.mipmapLevels);
      var29.addSprite(this.missingImage);
      this.skipFirst = false;

      try {
         var29.doStitch();
      } catch (StitcherException var23) {
         throw var23;
      }

      logger.info("Created: {}x{} {}-atlas", new Object[]{var29.getCurrentWidth(), var29.getCurrentHeight(), this.basePath});
      if (Config.isShaders()) {
         ShadersTex.allocateTextureMap(this.getGlTextureId(), this.mipmapLevels, var29.getCurrentWidth(), var29.getCurrentHeight(), var29, this);
      } else {
         TextureUtil.func_180600_a(this.getGlTextureId(), this.mipmapLevels, var29.getCurrentWidth(), var29.getCurrentHeight());
      }

      HashMap var34 = Maps.newHashMap(this.mapRegisteredSprites);
      Iterator var36 = var29.getStichSlots().iterator();

      TextureAtlasSprite var38;
      while(var36.hasNext()) {
         var38 = (TextureAtlasSprite)var36.next();
         if (Config.isShaders()) {
            ShadersTex.setIconName(ShadersTex.setSprite(var38).getIconName());
         }

         String var41 = var38.getIconName();
         var34.remove(var41);
         this.mapUploadedSprites.put(var41, var38);

         try {
            if (Config.isShaders()) {
               ShadersTex.uploadTexSubForLoadAtlas(var38.getFrameTextureData(0), var38.getIconWidth(), var38.getIconHeight(), var38.getOriginX(), var38.getOriginY(), false, false);
            } else {
               TextureUtil.uploadTextureMipmap(var38.getFrameTextureData(0), var38.getIconWidth(), var38.getIconHeight(), var38.getOriginX(), var38.getOriginY(), false, false);
            }
         } catch (Throwable var22) {
            CrashReport var45 = CrashReport.makeCrashReport(var22, "Stitching texture atlas");
            CrashReportCategory var47 = var45.makeCategory("Texture being stitched together");
            var47.addCrashSection("Atlas path", this.basePath);
            var47.addCrashSection("Sprite", var38);
            throw new ReportedException(var45);
         }

         if (var38.hasAnimationMetadata()) {
            this.listAnimatedSprites.add(var38);
         }
      }

      var36 = var34.values().iterator();

      while(var36.hasNext()) {
         var38 = (TextureAtlasSprite)var36.next();
         var38.copyFrom(this.missingImage);
      }

      if (Config.isMultiTexture()) {
         var16 = var29.getCurrentWidth();
         var8 = var29.getCurrentHeight();
         var9 = var29.getStichSlots();
         Iterator var43 = var9.iterator();

         while(var43.hasNext()) {
            TextureAtlasSprite var46 = (TextureAtlasSprite)var43.next();
            var46.sheetWidth = var16;
            var46.sheetHeight = var8;
            var46.mipmapLevels = this.mipmapLevels;
            TextureAtlasSprite var48 = var46.spriteSingle;
            if (var48 != null) {
               var48.sheetWidth = var16;
               var48.sheetHeight = var8;
               var48.mipmapLevels = this.mipmapLevels;
               var46.bindSpriteTexture();
               boolean var20 = false;
               boolean var21 = true;
               TextureUtil.uploadTextureMipmap(var48.getFrameTextureData(0), var48.getIconWidth(), var48.getIconHeight(), var48.getOriginX(), var48.getOriginY(), var20, var21);
            }
         }

         Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
      }

      Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
      this.updateIconGrid(var29.getCurrentWidth(), var29.getCurrentHeight());
      if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
         Config.dbg(String.valueOf((new StringBuilder("Exporting texture map to: ")).append(this.basePath).append("_x.png")));
         TextureUtil.func_177055_a(this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, var29.getCurrentWidth(), var29.getCurrentHeight());
      }

   }

   public boolean setTextureEntry(String var1, TextureAtlasSprite var2) {
      if (!this.mapRegisteredSprites.containsKey(var1)) {
         this.mapRegisteredSprites.put(var1, var2);
         if (var2.getIndexInMap() < 0) {
            var2.setIndexInMap(this.mapRegisteredSprites.size());
         }

         return true;
      } else {
         return false;
      }
   }

   public TextureAtlasSprite getTextureExtry(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return (TextureAtlasSprite)this.mapRegisteredSprites.get(var2.toString());
   }

   public void loadTexture(IResourceManager var1) throws IOException {
      ShadersTex.resManager = var1;
      if (this.field_174946_m != null) {
         this.func_174943_a(var1, this.field_174946_m);
      }

   }

   public TextureMap(String var1, IIconCreator var2, boolean var3) {
      this.iconGrid = null;
      this.iconGridSize = -1;
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGridSizeU = -1.0D;
      this.iconGridSizeV = -1.0D;
      this.skipFirst = false;
      this.atlasWidth = 0;
      this.atlasHeight = 0;
      this.listAnimatedSprites = Lists.newArrayList();
      this.mapRegisteredSprites = Maps.newHashMap();
      this.mapUploadedSprites = Maps.newHashMap();
      this.missingImage = new TextureAtlasSprite("missingno");
      this.basePath = var1;
      this.field_174946_m = var2;
      this.skipFirst = var3 && ENABLE_SKIP;
   }

   private void updateIconGrid(int var1, int var2) {
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGrid = null;
      if (this.iconGridSize > 0) {
         this.iconGridCountX = var1 / this.iconGridSize;
         this.iconGridCountY = var2 / this.iconGridSize;
         this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
         this.iconGridSizeU = 1.0D / (double)this.iconGridCountX;
         this.iconGridSizeV = 1.0D / (double)this.iconGridCountY;
         Iterator var3 = this.mapUploadedSprites.values().iterator();

         while(var3.hasNext()) {
            TextureAtlasSprite var4 = (TextureAtlasSprite)var3.next();
            double var5 = 0.5D / (double)var1;
            double var7 = 0.5D / (double)var2;
            double var9 = (double)Math.min(var4.getMinU(), var4.getMaxU()) + var5;
            double var11 = (double)Math.min(var4.getMinV(), var4.getMaxV()) + var7;
            double var13 = (double)Math.max(var4.getMinU(), var4.getMaxU()) - var5;
            double var15 = (double)Math.max(var4.getMinV(), var4.getMaxV()) - var7;
            int var17 = (int)(var9 / this.iconGridSizeU);
            int var18 = (int)(var11 / this.iconGridSizeV);
            int var19 = (int)(var13 / this.iconGridSizeU);
            int var20 = (int)(var15 / this.iconGridSizeV);

            for(int var21 = var17; var21 <= var19; ++var21) {
               if (var21 >= 0 && var21 < this.iconGridCountX) {
                  for(int var22 = var18; var22 <= var20; ++var22) {
                     if (var22 >= 0 && var22 < this.iconGridCountX) {
                        int var23 = var22 * this.iconGridCountX + var21;
                        this.iconGrid[var23] = var4;
                     } else {
                        Config.warn(String.valueOf((new StringBuilder("Invalid grid V: ")).append(var22).append(", icon: ").append(var4.getIconName())));
                     }
                  }
               } else {
                  Config.warn(String.valueOf((new StringBuilder("Invalid grid U: ")).append(var21).append(", icon: ").append(var4.getIconName())));
               }
            }
         }
      }

   }

   public TextureAtlasSprite func_174944_f() {
      return this.missingImage;
   }

   public int getCountRegisteredSprites() {
      return this.mapRegisteredSprites.size();
   }

   public TextureAtlasSprite func_174942_a(ResourceLocation var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapRegisteredSprites.get(var1.toString());
         if (var2 == null) {
            var2 = TextureAtlasSprite.func_176604_a(var1);
            this.mapRegisteredSprites.put(var1.toString(), var2);
            if (var2 instanceof TextureAtlasSprite && var2.getIndexInMap() < 0) {
               var2.setIndexInMap(this.mapRegisteredSprites.size());
            }
         }

         return var2;
      }
   }

   public ResourceLocation completeResourceLocation(ResourceLocation var1, int var2) {
      return this.isAbsoluteLocation(var1) ? (var2 == 0 ? new ResourceLocation(var1.getResourceDomain(), String.valueOf((new StringBuilder(String.valueOf(var1.getResourcePath()))).append(".png"))) : new ResourceLocation(var1.getResourceDomain(), String.valueOf((new StringBuilder(String.valueOf(var1.getResourcePath()))).append("mipmap").append(var2).append(".png")))) : (var2 == 0 ? new ResourceLocation(var1.getResourceDomain(), String.format("%s/%s%s", this.basePath, var1.getResourcePath(), ".png")) : new ResourceLocation(var1.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", this.basePath, var1.getResourcePath(), var2, ".png")));
   }

   public void func_174943_a(IResourceManager var1, IIconCreator var2) {
      this.mapRegisteredSprites.clear();
      var2.func_177059_a(this);
      if (this.mipmapLevels >= 4) {
         this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, var1);
         Config.log(String.valueOf((new StringBuilder("Mipmap levels: ")).append(this.mipmapLevels)));
      }

      this.initMissingImage();
      this.deleteGlTexture();
      this.loadTextureAtlas(var1);
   }

   private int getMinSpriteSize() {
      int var1 = 1 << this.mipmapLevels;
      if (var1 < 8) {
         var1 = 8;
      }

      return var1;
   }

   private int detectMinimumSpriteSize(Map var1, IResourceManager var2, int var3) {
      HashMap var4 = new HashMap();
      Set var5 = var1.entrySet();
      Iterator var6 = var5.iterator();

      int var7;
      int var15;
      while(var6.hasNext()) {
         Entry var8 = (Entry)var6.next();
         TextureAtlasSprite var9 = (TextureAtlasSprite)var8.getValue();
         ResourceLocation var10 = new ResourceLocation(var9.getIconName());
         ResourceLocation var11 = this.completeResourceLocation(var10, 0);
         if (!var9.hasCustomLoader(var2, var10)) {
            try {
               IResource var12 = var2.getResource(var11);
               if (var12 != null) {
                  InputStream var13 = var12.getInputStream();
                  if (var13 != null) {
                     Dimension var14 = TextureUtils.getImageSize(var13, "png");
                     if (var14 != null) {
                        var7 = var14.width;
                        var15 = MathHelper.roundUpToPowerOfTwo(var7);
                        if (!var4.containsKey(var15)) {
                           var4.put(var15, 1);
                        } else {
                           int var16 = (Integer)var4.get(var15);
                           var4.put(var15, var16 + 1);
                        }
                     }
                  }
               }
            } catch (Exception var17) {
            }
         }
      }

      int var18 = 0;
      Set var19 = var4.keySet();
      TreeSet var20 = new TreeSet(var19);

      int var21;
      int var22;
      for(Iterator var23 = var20.iterator(); var23.hasNext(); var18 += var22) {
         var21 = (Integer)var23.next();
         var22 = (Integer)var4.get(var21);
      }

      int var24 = 16;
      var21 = 0;
      var22 = var18 * var3 / 100;
      Iterator var25 = var20.iterator();

      do {
         if (!var25.hasNext()) {
            return var24;
         }

         var15 = (Integer)var25.next();
         var7 = (Integer)var4.get(var15);
         var21 += var7;
         if (var15 > var24) {
            var24 = var15;
         }
      } while(var21 <= var22);

      return var24;
   }

   private void initMissingImage() {
      int var1 = this.getMinSpriteSize();
      int[] var2 = this.getMissingImageData(var1);
      this.missingImage.setIconWidth(var1);
      this.missingImage.setIconHeight(var1);
      int[][] var3 = new int[this.mipmapLevels + 1][];
      var3[0] = var2;
      this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][]{var3}));
      this.missingImage.setIndexInMap(0);
   }

   private boolean isTerrainAnimationActive(TextureAtlasSprite var1) {
      return var1 != TextureUtils.iconWaterStill && var1 != TextureUtils.iconWaterFlow ? (var1 != TextureUtils.iconLavaStill && var1 != TextureUtils.iconLavaFlow ? (var1 != TextureUtils.iconFireLayer0 && var1 != TextureUtils.iconFireLayer1 ? (var1 == TextureUtils.iconPortal ? Config.isAnimatedPortal() : (var1 != TextureUtils.iconClock && var1 != TextureUtils.iconCompass ? Config.isAnimatedTerrain() : true)) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
   }

   public void updateAnimations() {
      if (Config.isShaders()) {
         ShadersTex.updatingTex = this.getMultiTexID();
      }

      TextureUtil.bindTexture(this.getGlTextureId());
      Iterator var1 = this.listAnimatedSprites.iterator();

      while(var1.hasNext()) {
         TextureAtlasSprite var2 = (TextureAtlasSprite)var1.next();
         if (this.isTerrainAnimationActive(var2)) {
            var2.updateAnimation();
         }
      }

      if (Config.isMultiTexture()) {
         Iterator var5 = this.listAnimatedSprites.iterator();

         label51:
         while(true) {
            TextureAtlasSprite var3;
            TextureAtlasSprite var4;
            do {
               do {
                  if (!var5.hasNext()) {
                     TextureUtil.bindTexture(this.getGlTextureId());
                     break label51;
                  }

                  var3 = (TextureAtlasSprite)var5.next();
               } while(!this.isTerrainAnimationActive(var3));

               var4 = var3.spriteSingle;
            } while(var4 == null);

            if (var3 == TextureUtils.iconClock || var3 == TextureUtils.iconCompass) {
               var4.frameCounter = var3.frameCounter;
            }

            var3.bindSpriteTexture();
            var4.updateAnimation();
         }
      }

      if (Config.isShaders()) {
         ShadersTex.updatingTex = null;
      }

   }

   public TextureAtlasSprite getIconByUV(double var1, double var3) {
      if (this.iconGrid == null) {
         return null;
      } else {
         int var5 = (int)(var1 / this.iconGridSizeU);
         int var6 = (int)(var3 / this.iconGridSizeV);
         int var7 = var6 * this.iconGridCountX + var5;
         return var7 >= 0 && var7 <= this.iconGrid.length ? this.iconGrid[var7] : null;
      }
   }

   private int[] getMissingImageData(int var1) {
      BufferedImage var2 = new BufferedImage(16, 16, 2);
      var2.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
      BufferedImage var3 = TextureUtils.scaleToPowerOfTwo(var2, var1);
      int[] var4 = new int[var1 * var1];
      var3.getRGB(0, 0, var1, var1, var4, 0, var1);
      return var4;
   }

   public TextureMap(String var1, IIconCreator var2) {
      this(var1, var2, false);
   }

   public TextureAtlasSprite getSpriteSafe(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return (TextureAtlasSprite)this.mapRegisteredSprites.get(var2.toString());
   }

   public TextureMap(String var1, boolean var2) {
      this(var1, (IIconCreator)null, var2);
   }

   public void tick() {
      this.updateAnimations();
   }

   private boolean isAbsoluteLocationPath(String var1) {
      String var2 = var1.toLowerCase();
      return var2.startsWith("mcpatcher/") || var2.startsWith("optifine/");
   }

   public void setMipmapLevels(int var1) {
      this.mipmapLevels = var1;
   }
}
