/*     */ package optfine;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ public class TextureUtils
/*     */ {
/*     */   public static final String texGrassTop = "grass_top";
/*     */   public static final String texStone = "stone";
/*     */   public static final String texDirt = "dirt";
/*     */   public static final String texCoarseDirt = "coarse_dirt";
/*     */   public static final String texGrassSide = "grass_side";
/*     */   public static final String texStoneslabSide = "stone_slab_side";
/*     */   public static final String texStoneslabTop = "stone_slab_top";
/*     */   public static final String texBedrock = "bedrock";
/*     */   public static final String texSand = "sand";
/*     */   public static final String texGravel = "gravel";
/*     */   public static final String texLogOak = "log_oak";
/*     */   public static final String texLogBigOak = "log_big_oak";
/*     */   public static final String texLogAcacia = "log_acacia";
/*     */   public static final String texLogSpruce = "log_spruce";
/*     */   public static final String texLogBirch = "log_birch";
/*     */   public static final String texLogJungle = "log_jungle";
/*     */   public static final String texLogOakTop = "log_oak_top";
/*     */   public static final String texLogBigOakTop = "log_big_oak_top";
/*     */   public static final String texLogAcaciaTop = "log_acacia_top";
/*     */   public static final String texLogSpruceTop = "log_spruce_top";
/*     */   public static final String texLogBirchTop = "log_birch_top";
/*     */   public static final String texLogJungleTop = "log_jungle_top";
/*     */   public static final String texLeavesOak = "leaves_oak";
/*     */   public static final String texLeavesBigOak = "leaves_big_oak";
/*     */   public static final String texLeavesAcacia = "leaves_acacia";
/*     */   public static final String texLeavesBirch = "leaves_birch";
/*     */   public static final String texLeavesSpuce = "leaves_spruce";
/*     */   public static final String texLeavesJungle = "leaves_jungle";
/*     */   public static final String texGoldOre = "gold_ore";
/*     */   public static final String texIronOre = "iron_ore";
/*     */   public static final String texCoalOre = "coal_ore";
/*     */   public static final String texObsidian = "obsidian";
/*     */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*     */   public static final String texSnow = "snow";
/*     */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*     */   public static final String texMyceliumSide = "mycelium_side";
/*     */   public static final String texMyceliumTop = "mycelium_top";
/*     */   public static final String texDiamondOre = "diamond_ore";
/*     */   public static final String texRedstoneOre = "redstone_ore";
/*     */   public static final String texLapisOre = "lapis_ore";
/*     */   public static final String texCactusSide = "cactus_side";
/*     */   public static final String texClay = "clay";
/*     */   public static final String texFarmlandWet = "farmland_wet";
/*     */   public static final String texFarmlandDry = "farmland_dry";
/*     */   public static final String texNetherrack = "netherrack";
/*     */   public static final String texSoulSand = "soul_sand";
/*     */   public static final String texGlowstone = "glowstone";
/*     */   public static final String texLeavesSpruce = "leaves_spruce";
/*     */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*     */   public static final String texEndStone = "end_stone";
/*     */   public static final String texSandstoneTop = "sandstone_top";
/*     */   public static final String texSandstoneBottom = "sandstone_bottom";
/*     */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*     */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*     */   public static final String texWaterStill = "water_still";
/*     */   public static final String texWaterFlow = "water_flow";
/*     */   public static final String texLavaStill = "lava_still";
/*     */   public static final String texLavaFlow = "lava_flow";
/*     */   public static final String texFireLayer0 = "fire_layer_0";
/*     */   public static final String texFireLayer1 = "fire_layer_1";
/*     */   public static final String texPortal = "portal";
/*     */   public static final String texGlass = "glass";
/*     */   public static final String texGlassPaneTop = "glass_pane_top";
/*     */   public static TextureAtlasSprite iconGrassTop;
/*     */   public static TextureAtlasSprite iconGrassSide;
/*     */   public static TextureAtlasSprite iconGrassSideOverlay;
/*     */   public static TextureAtlasSprite iconSnow;
/*     */   public static TextureAtlasSprite iconGrassSideSnowed;
/*     */   public static TextureAtlasSprite iconMyceliumSide;
/*     */   public static TextureAtlasSprite iconMyceliumTop;
/*     */   public static TextureAtlasSprite iconWaterStill;
/*     */   public static TextureAtlasSprite iconWaterFlow;
/*     */   public static TextureAtlasSprite iconLavaStill;
/*     */   public static TextureAtlasSprite iconLavaFlow;
/*     */   public static TextureAtlasSprite iconPortal;
/*     */   public static TextureAtlasSprite iconFireLayer0;
/*     */   public static TextureAtlasSprite iconFireLayer1;
/*     */   public static TextureAtlasSprite iconGlass;
/*     */   public static TextureAtlasSprite iconGlassPaneTop;
/*     */   public static final String SPRITE_LOCATION_PREFIX = "minecraft:blocks/";
/* 113 */   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);
/*     */ 
/*     */   
/*     */   public static void update() {
/* 117 */     TextureMap texturemap = getTextureMapBlocks();
/*     */     
/* 119 */     if (texturemap != null) {
/*     */       
/* 121 */       String s = "minecraft:blocks/";
/* 122 */       iconGrassTop = texturemap.getSpriteSafe(String.valueOf(s) + "grass_top");
/* 123 */       iconGrassSide = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side");
/* 124 */       iconGrassSideOverlay = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_overlay");
/* 125 */       iconSnow = texturemap.getSpriteSafe(String.valueOf(s) + "snow");
/* 126 */       iconGrassSideSnowed = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_snowed");
/* 127 */       iconMyceliumSide = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_side");
/* 128 */       iconMyceliumTop = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_top");
/* 129 */       iconWaterStill = texturemap.getSpriteSafe(String.valueOf(s) + "water_still");
/* 130 */       iconWaterFlow = texturemap.getSpriteSafe(String.valueOf(s) + "water_flow");
/* 131 */       iconLavaStill = texturemap.getSpriteSafe(String.valueOf(s) + "lava_still");
/* 132 */       iconLavaFlow = texturemap.getSpriteSafe(String.valueOf(s) + "lava_flow");
/* 133 */       iconFireLayer0 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_0");
/* 134 */       iconFireLayer1 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_1");
/* 135 */       iconPortal = texturemap.getSpriteSafe(String.valueOf(s) + "portal");
/* 136 */       iconGlass = texturemap.getSpriteSafe(String.valueOf(s) + "glass");
/* 137 */       iconGlassPaneTop = texturemap.getSpriteSafe(String.valueOf(s) + "glass_pane_top");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage fixTextureDimensions(String p_fixTextureDimensions_0_, BufferedImage p_fixTextureDimensions_1_) {
/* 143 */     if (p_fixTextureDimensions_0_.startsWith("/mob/zombie") || p_fixTextureDimensions_0_.startsWith("/mob/pigzombie")) {
/*     */       
/* 145 */       int i = p_fixTextureDimensions_1_.getWidth();
/* 146 */       int j = p_fixTextureDimensions_1_.getHeight();
/*     */       
/* 148 */       if (i == j * 2) {
/*     */         
/* 150 */         BufferedImage bufferedimage = new BufferedImage(i, j * 2, 2);
/* 151 */         Graphics2D graphics2d = bufferedimage.createGraphics();
/* 152 */         graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 153 */         graphics2d.drawImage(p_fixTextureDimensions_1_, 0, 0, i, j, null);
/* 154 */         return bufferedimage;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return p_fixTextureDimensions_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ceilPowerOfTwo(int p_ceilPowerOfTwo_0_) {
/* 165 */     for (int i = 1; i < p_ceilPowerOfTwo_0_; i *= 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPowerOfTwo(int p_getPowerOfTwo_0_) {
/* 175 */     int i = 1;
/*     */ 
/*     */     
/* 178 */     for (int j = 0; i < p_getPowerOfTwo_0_; j++)
/*     */     {
/* 180 */       i *= 2;
/*     */     }
/*     */     
/* 183 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int twoToPower(int p_twoToPower_0_) {
/* 188 */     int i = 1;
/*     */     
/* 190 */     for (int j = 0; j < p_twoToPower_0_; j++)
/*     */     {
/* 192 */       i *= 2;
/*     */     }
/*     */     
/* 195 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void refreshBlockTextures() {}
/*     */ 
/*     */   
/*     */   public static ITextureObject getTexture(String p_getTexture_0_) {
/* 204 */     return getTexture(new ResourceLocation(p_getTexture_0_));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject getTexture(ResourceLocation p_getTexture_0_) {
/* 209 */     ITextureObject itextureobject = Config.getTextureManager().getTexture(p_getTexture_0_);
/*     */     
/* 211 */     if (itextureobject != null)
/*     */     {
/* 213 */       return itextureobject;
/*     */     }
/* 215 */     if (!Config.hasResource(p_getTexture_0_))
/*     */     {
/* 217 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 221 */     SimpleTexture simpletexture = new SimpleTexture(p_getTexture_0_);
/* 222 */     Config.getTextureManager().loadTexture(p_getTexture_0_, (ITextureObject)simpletexture);
/* 223 */     return (ITextureObject)simpletexture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded(IResourceManager p_resourcesReloaded_0_) {
/* 229 */     if (getTextureMapBlocks() != null) {
/*     */       
/* 231 */       Config.dbg("*** Reloading custom textures ***");
/* 232 */       CustomSky.reset();
/* 233 */       TextureAnimations.reset();
/* 234 */       update();
/* 235 */       NaturalTextures.update();
/* 236 */       BetterGrass.update();
/* 237 */       BetterSnow.update();
/* 238 */       TextureAnimations.update();
/* 239 */       CustomColorizer.update();
/* 240 */       CustomSky.update();
/* 241 */       RandomMobs.resetTextures();
/* 242 */       Config.updateTexturePackClouds();
/* 243 */       Config.getTextureManager().tick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMap getTextureMapBlocks() {
/* 249 */     return Minecraft.getMinecraft().getTextureMapBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerResourceListener() {
/* 254 */     IResourceManager iresourcemanager = Config.getResourceManager();
/*     */     
/* 256 */     if (iresourcemanager instanceof IReloadableResourceManager) {
/*     */       
/* 258 */       IReloadableResourceManager ireloadableresourcemanager = (IReloadableResourceManager)iresourcemanager;
/* 259 */       IResourceManagerReloadListener iresourcemanagerreloadlistener = new IResourceManagerReloadListener()
/*     */         {
/*     */           public void onResourceManagerReload(IResourceManager resourceManager)
/*     */           {
/* 263 */             TextureUtils.resourcesReloaded(resourceManager);
/*     */           }
/*     */         };
/* 266 */       ireloadableresourcemanager.registerReloadListener(iresourcemanagerreloadlistener);
/*     */     } 
/*     */     
/* 269 */     ITickableTextureObject itickabletextureobject = new ITickableTextureObject()
/*     */       {
/*     */         public void tick()
/*     */         {
/* 273 */           TextureAnimations.updateCustomAnimations();
/*     */         }
/*     */ 
/*     */         
/*     */         public void loadTexture(IResourceManager resourceManager) throws IOException {}
/*     */         
/*     */         public int getGlTextureId() {
/* 280 */           return 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setBlurMipmap(boolean p_174936_1_, boolean p_174936_2_) {}
/*     */ 
/*     */         
/*     */         public void restoreLastBlurMipmap() {}
/*     */       };
/* 289 */     ResourceLocation resourcelocation = new ResourceLocation("optifine/TickableTextures");
/* 290 */     Config.getTextureManager().loadTickableTexture(resourcelocation, itickabletextureobject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fixResourcePath(String p_fixResourcePath_0_, String p_fixResourcePath_1_) {
/* 295 */     String s = "assets/minecraft/";
/*     */     
/* 297 */     if (p_fixResourcePath_0_.startsWith(s)) {
/*     */       
/* 299 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(s.length());
/* 300 */       return p_fixResourcePath_0_;
/*     */     } 
/* 302 */     if (p_fixResourcePath_0_.startsWith("./")) {
/*     */       
/* 304 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(2);
/*     */       
/* 306 */       if (!p_fixResourcePath_1_.endsWith("/"))
/*     */       {
/* 308 */         p_fixResourcePath_1_ = String.valueOf(p_fixResourcePath_1_) + "/";
/*     */       }
/*     */       
/* 311 */       p_fixResourcePath_0_ = String.valueOf(p_fixResourcePath_1_) + p_fixResourcePath_0_;
/* 312 */       return p_fixResourcePath_0_;
/*     */     } 
/*     */ 
/*     */     
/* 316 */     String s1 = "mcpatcher/";
/*     */     
/* 318 */     if (p_fixResourcePath_0_.startsWith("~/")) {
/*     */       
/* 320 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(2);
/* 321 */       p_fixResourcePath_0_ = String.valueOf(s1) + p_fixResourcePath_0_;
/* 322 */       return p_fixResourcePath_0_;
/*     */     } 
/* 324 */     if (p_fixResourcePath_0_.startsWith("/")) {
/*     */       
/* 326 */       p_fixResourcePath_0_ = String.valueOf(s1) + p_fixResourcePath_0_.substring(1);
/* 327 */       return p_fixResourcePath_0_;
/*     */     } 
/*     */ 
/*     */     
/* 331 */     return p_fixResourcePath_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBasePath(String p_getBasePath_0_) {
/* 338 */     int i = p_getBasePath_0_.lastIndexOf('/');
/* 339 */     return (i < 0) ? "" : p_getBasePath_0_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyAnisotropicLevel() {
/* 344 */     if ((GLContext.getCapabilities()).GL_EXT_texture_filter_anisotropic) {
/*     */       
/* 346 */       float f = GL11.glGetFloat(34047);
/* 347 */       float f1 = Config.getAnisotropicFilterLevel();
/* 348 */       f1 = Math.min(f1, f);
/* 349 */       GL11.glTexParameterf(3553, 34046, f1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(int p_bindTexture_0_) {
/* 355 */     GlStateManager.bindTexture(p_bindTexture_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(int p_isPowerOfTwo_0_) {
/* 360 */     int i = MathHelper.roundUpToPowerOfTwo(p_isPowerOfTwo_0_);
/* 361 */     return (i == p_isPowerOfTwo_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleToPowerOfTwo(BufferedImage p_scaleToPowerOfTwo_0_, int p_scaleToPowerOfTwo_1_) {
/* 366 */     if (p_scaleToPowerOfTwo_0_ == null)
/*     */     {
/* 368 */       return p_scaleToPowerOfTwo_0_;
/*     */     }
/*     */ 
/*     */     
/* 372 */     int i = p_scaleToPowerOfTwo_0_.getWidth();
/* 373 */     int j = p_scaleToPowerOfTwo_0_.getHeight();
/* 374 */     int k = Math.max(i, p_scaleToPowerOfTwo_1_);
/* 375 */     k = MathHelper.roundUpToPowerOfTwo(k);
/*     */     
/* 377 */     if (k == i)
/*     */     {
/* 379 */       return p_scaleToPowerOfTwo_0_;
/*     */     }
/*     */ 
/*     */     
/* 383 */     int l = j * k / i;
/* 384 */     BufferedImage bufferedimage = new BufferedImage(k, l, 2);
/* 385 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 386 */     Object object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */     
/* 388 */     if (k % i != 0)
/*     */     {
/* 390 */       object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */     }
/*     */     
/* 393 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, object);
/* 394 */     graphics2d.drawImage(p_scaleToPowerOfTwo_0_, 0, 0, k, l, null);
/* 395 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dimension getImageSize(InputStream p_getImageSize_0_, String p_getImageSize_1_) {
/* 402 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(p_getImageSize_1_);
/*     */ 
/*     */ 
/*     */     
/* 406 */     while (iterator.hasNext()) {
/*     */       Dimension dimension;
/* 408 */       ImageReader imagereader = iterator.next();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 413 */         ImageInputStream imageinputstream = ImageIO.createImageInputStream(p_getImageSize_0_);
/* 414 */         imagereader.setInput(imageinputstream);
/* 415 */         int i = imagereader.getWidth(imagereader.getMinIndex());
/* 416 */         int j = imagereader.getHeight(imagereader.getMinIndex());
/* 417 */         dimension = new Dimension(i, j);
/*     */       }
/* 419 */       catch (IOException var11) {
/*     */         
/*     */         continue;
/*     */       }
/*     */       finally {
/*     */         
/* 425 */         imagereader.dispose();
/*     */       } 
/*     */       
/* 428 */       return dimension;
/*     */     } 
/*     */     
/* 431 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\TextureUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */