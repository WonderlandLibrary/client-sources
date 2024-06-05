package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.InputStream;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.qT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import optifine.BetterGrass;
import optifine.BetterSnow;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomItems;
import optifine.CustomSky;
import optifine.Lang;
import optifine.MatchBlock;
import optifine.NaturalTextures;
import optifine.RandomMobs;
import optifine.SmartLeaves;
import optifine.TextureAnimations;
import optifine.TextureUtils$1;
import optifine.TextureUtils$2;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import shadersmod.client.Shaders;

public class TextureUtils {
   public static final String al = "grass_top";
   public static final String Q = "stone";
   public static final String aj = "dirt";
   public static final String J = "coarse_dirt";
   public static final String Z = "grass_side";
   public static final String aC = "stone_slab_side";
   public static final String aF = "stone_slab_top";
   public static final String C = "bedrock";
   public static final String M = "sand";
   public static final String aE = "gravel";
   public static final String G = "log_oak";
   public static final String r = "log_big_oak";
   public static final String ac = "log_acacia";
   public static final String p = "log_spruce";
   public static final String i = "log_birch";
   public static final String as = "log_jungle";
   public static final String B = "log_oak_top";
   public static final String ag = "log_big_oak_top";
   public static final String aA = "log_acacia_top";
   public static final String aw = "log_spruce_top";
   public static final String aB = "log_birch_top";
   public static final String L = "log_jungle_top";
   public static final String H = "leaves_oak";
   public static final String P = "leaves_big_oak";
   public static final String v = "leaves_acacia";
   public static final String e = "leaves_birch";
   public static final String aG = "leaves_spruce";
   public static final String ar = "leaves_jungle";
   public static final String ad = "gold_ore";
   public static final String k = "iron_ore";
   public static final String ak = "coal_ore";
   public static final String o = "obsidian";
   public static final String aD = "grass_side_overlay";
   public static final String c = "snow";
   public static final String aH = "grass_side_snowed";
   public static final String X = "mycelium_side";
   public static final String D = "mycelium_top";
   public static final String ax = "diamond_ore";
   public static final String au = "redstone_ore";
   public static final String j = "lapis_ore";
   public static final String U = "cactus_side";
   public static final String b = "clay";
   public static final String d = "farmland_wet";
   public static final String f = "farmland_dry";
   public static final String aq = "netherrack";
   public static final String l = "soul_sand";
   public static final String u = "glowstone";
   public static final String ab = "leaves_spruce";
   public static final String W = "leaves_spruce_opaque";
   public static final String ap = "end_stone";
   public static final String an = "sandstone_top";
   public static final String n = "sandstone_bottom";
   public static final String z = "redstone_lamp_off";
   public static final String q = "redstone_lamp_on";
   public static final String S = "water_still";
   public static final String x = "water_flow";
   public static final String av = "lava_still";
   public static final String F = "lava_flow";
   public static final String ao = "fire_layer_0";
   public static final String g = "fire_layer_1";
   public static final String E = "portal";
   public static final String ay = "glass";
   public static final String at = "glass_pane_top";
   public static final String ai = "compass";
   public static final String N = "clock";
   public static TextureAtlasSprite iconGrassTop;
   public static TextureAtlasSprite iconGrassSide;
   public static TextureAtlasSprite iconGrassSideOverlay;
   public static TextureAtlasSprite iconSnow;
   public static TextureAtlasSprite iconGrassSideSnowed;
   public static TextureAtlasSprite iconMyceliumSide;
   public static TextureAtlasSprite iconMyceliumTop;
   public static TextureAtlasSprite iconWaterStill;
   public static TextureAtlasSprite iconWaterFlow;
   public static TextureAtlasSprite iconLavaStill;
   public static TextureAtlasSprite iconLavaFlow;
   public static TextureAtlasSprite iconPortal;
   public static TextureAtlasSprite iconFireLayer0;
   public static TextureAtlasSprite iconFireLayer1;
   public static TextureAtlasSprite iconGlass;
   public static TextureAtlasSprite iconGlassPaneTop;
   public static TextureAtlasSprite iconCompass;
   public static TextureAtlasSprite iconClock;
   public static final String K = "minecraft:blocks/";
   public static final String T = "minecraft:items/";
   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);

   public static void update() {
      TextureMap var0 = getTextureMapBlocks();
      String var1 = "minecraft:blocks/";
      iconGrassTop = var0.getSpriteSafe(var1 + "grass_top");
      iconGrassSide = var0.getSpriteSafe(var1 + "grass_side");
      iconGrassSideOverlay = var0.getSpriteSafe(var1 + "grass_side_overlay");
      iconSnow = var0.getSpriteSafe(var1 + "snow");
      iconGrassSideSnowed = var0.getSpriteSafe(var1 + "grass_side_snowed");
      iconMyceliumSide = var0.getSpriteSafe(var1 + "mycelium_side");
      iconMyceliumTop = var0.getSpriteSafe(var1 + "mycelium_top");
      iconWaterStill = var0.getSpriteSafe(var1 + "water_still");
      iconWaterFlow = var0.getSpriteSafe(var1 + "water_flow");
      iconLavaStill = var0.getSpriteSafe(var1 + "lava_still");
      iconLavaFlow = var0.getSpriteSafe(var1 + "lava_flow");
      iconFireLayer0 = var0.getSpriteSafe(var1 + "fire_layer_0");
      iconFireLayer1 = var0.getSpriteSafe(var1 + "fire_layer_1");
      iconPortal = var0.getSpriteSafe(var1 + "portal");
      iconGlass = var0.getSpriteSafe(var1 + "glass");
      iconGlassPaneTop = var0.getSpriteSafe(var1 + "glass_pane_top");
      String var2 = "minecraft:items/";
      iconCompass = var0.getSpriteSafe(var2 + "compass");
      iconClock = var0.getSpriteSafe(var2 + "clock");
   }

   public static BufferedImage fixTextureDimensions(String var0, BufferedImage var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0.startsWith("/mob/zombie") || var0.startsWith("/mob/pigzombie")) {
         int var3 = var1.getWidth();
         int var4 = var1.getHeight();
         if(var3 == var4 * 2) {
            BufferedImage var5 = new BufferedImage(var3, var4 * 2, 2);
            Graphics2D var6 = var5.createGraphics();
            var6.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            var6.drawImage(var1, 0, 0, var3, var4, (ImageObserver)null);
            return var5;
         }
      }

      return var1;
   }

   public static int e(int var0) {
      MatchBlock.b();
      int var2 = 1;
      if(var2 < var0) {
         var2 *= 2;
      }

      return var2;
   }

   public static int d(int var0) {
      MatchBlock.b();
      byte var2 = 1;
      int var3 = 0;
      if(var2 < var0) {
         int var10000 = var2 * 2;
         ++var3;
      }

      return var3;
   }

   public static int b(int var0) {
      MatchBlock.b();
      int var2 = 1;
      int var3 = 0;
      if(var3 < var0) {
         var2 *= 2;
         ++var3;
      }

      return var2;
   }

   public static ITextureObject getTexture(ResourceLocation var0) {
      MatchBlock.b();
      ITextureObject var2 = Config.ax().b(var0);
      if(var2 != null) {
         return var2;
      } else if(!Config.hasResource(var0)) {
         return null;
      } else {
         SimpleTexture var3 = new SimpleTexture(var0);
         Config.ax().a((ResourceLocation)var0, (ITextureObject)var3);
         return var3;
      }
   }

   public static void resourcesReloaded(IResourceManager var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(getTextureMapBlocks() != null) {
         Config.dbg("*** Reloading custom textures ***");
         CustomSky.reset();
         TextureAnimations.reset();
         update();
         NaturalTextures.update();
         BetterGrass.update();
         BetterSnow.update();
         TextureAnimations.update();
         CustomColors.update();
         CustomSky.update();
         RandomMobs.resetTextures();
         CustomItems.updateModels();
         Shaders.resourcesReloaded();
         Lang.resourcesReloaded();
         Config.Y();
         SmartLeaves.updateLeavesModels();
         Config.ax().tick();
      }

   }

   public static TextureMap getTextureMapBlocks() {
      return Minecraft.getMinecraft().getTextureMapBlocks();
   }

   public static void registerResourceListener() {
      MatchBlock.b();
      IResourceManager var1 = Config.getResourceManager();
      if(var1 instanceof IReloadableResourceManager) {
         IReloadableResourceManager var2 = (IReloadableResourceManager)var1;
         TextureUtils$1 var3 = new TextureUtils$1();
         var2.registerReloadListener(var3);
      }

      TextureUtils$2 var4 = new TextureUtils$2();
      ResourceLocation var5 = new ResourceLocation("optifine/TickableTextures");
      Config.ax().a((ResourceLocation)var5, (ITickableTextureObject)var4);
   }

   public static String fixResourcePath(String var0, String var1) {
      MatchBlock.b();
      String var3 = "assets/minecraft/";
      if(var0.startsWith(var3)) {
         var0 = var0.substring(var3.length());
         return var0;
      } else if(var0.startsWith("./")) {
         var0 = var0.substring(2);
         if(!var1.endsWith("/")) {
            var1 = var1 + "/";
         }

         var0 = var1 + var0;
         return var0;
      } else {
         if(var0.startsWith("/~")) {
            var0 = var0.substring(1);
         }

         String var4 = "mcpatcher/";
         if(var0.startsWith("~/")) {
            var0 = var0.substring(2);
            var0 = var4 + var0;
            return var0;
         } else if(var0.startsWith("/")) {
            var0 = var4 + var0.substring(1);
            return var0;
         } else {
            return var0;
         }
      }
   }

   public static String getBasePath(String var0) {
      int var1 = var0.lastIndexOf(47);
      return "";
   }

   public static void applyAnisotropicLevel() {
      PacketRemapper[] var0 = MatchBlock.b();
      if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
         float var1 = GL11.glGetFloat('蓿');
         float var2 = (float)Config.getAnisotropicFilterLevel();
         var2 = Math.min(var2, var1);
         GL11.glTexParameterf(3553, '蓾', var2);
      }

   }

   public static void bindTexture(int var0) {
      GlStateManager.bindTexture(var0);
   }

   public static boolean isPowerOfTwo(int var0) {
      MatchBlock.b();
      int var2 = MathHelper.roundUpToPowerOfTwo(var0);
      return var2 == var0;
   }

   public static BufferedImage scaleImage(BufferedImage var0, int var1) {
      int var3 = var0.getWidth();
      int var4 = var0.getHeight();
      int var5 = var4 * var1 / var3;
      MatchBlock.b();
      BufferedImage var6 = new BufferedImage(var1, var5, 2);
      Graphics2D var7 = var6.createGraphics();
      Object var8 = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
      if(var1 < var3 || var1 % var3 != 0) {
         var8 = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
      }

      var7.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var8);
      var7.drawImage(var0, 0, 0, var1, var5, (ImageObserver)null);
      return var6;
   }

   public static BufferedImage scaleToPowerOfTwo(BufferedImage var0, int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var0;
      } else {
         int var3 = var0.getWidth();
         int var4 = var0.getHeight();
         int var5 = Math.max(var3, var1);
         var5 = MathHelper.roundUpToPowerOfTwo(var5);
         if(var5 == var3) {
            return var0;
         } else {
            int var6 = var4 * var5 / var3;
            BufferedImage var7 = new BufferedImage(var5, var6, 2);
            Graphics2D var8 = var7.createGraphics();
            Object var9 = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
            if(var5 % var3 != 0) {
               var9 = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            }

            var8.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var9);
            var8.drawImage(var0, 0, 0, var5, var6, (ImageObserver)null);
            return var7;
         }
      }
   }

   public static BufferedImage scaleMinTo(BufferedImage var0, int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var0;
      } else {
         int var3 = var0.getWidth();
         int var4 = var0.getHeight();
         if(var3 >= var1) {
            return var0;
         } else {
            int var5 = var3;
            if(var3 < var1) {
               var5 = var3 * 2;
            }

            int var6 = var4 * var5 / var3;
            BufferedImage var7 = new BufferedImage(var5, var6, 2);
            Graphics2D var8 = var7.createGraphics();
            Object var9 = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
            var8.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var9);
            var8.drawImage(var0, 0, 0, var5, var6, (ImageObserver)null);
            return var7;
         }
      }
   }

   public static Dimension getImageSize(InputStream param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static void dbgMipmaps(TextureAtlasSprite var0) {
      MatchBlock.b();
      int[][] var2 = var0.getFrameTextureData(0);
      int var3 = 0;
      if(var3 < var2.length) {
         int[] var4 = var2[var3];
         Config.dbg("" + var3 + ": " + var4);
         Config.dbg("" + var3 + ": " + var4.length);
         ++var3;
      }

   }

   public static void saveGlTexture(String var0, int var1, int var2, int var3, int var4) {
      MatchBlock.b();
      bindTexture(var1);
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      File var6 = new File(var0);
      File var7 = var6.getParentFile();
      if(var7 != null) {
         var7.mkdirs();
      }

      int var8 = 0;
      if(var8 < 16) {
         File var9 = new File(var0 + "_" + var8 + ".png");
         var9.delete();
         ++var8;
      }

      var8 = 0;
      if(var8 <= var2) {
         File var21 = new File(var0 + "_" + var8 + ".png");
         int var10 = var3 >> var8;
         int var11 = var4 >> var8;
         int var12 = var10 * var11;
         IntBuffer var13 = BufferUtils.createIntBuffer(var12);
         int[] var14 = new int[var12];
         GL11.glGetTexImage(3553, var8, '胡', '荧', var13);
         var13.get(var14);
         BufferedImage var15 = new BufferedImage(var10, var11, 2);
         qT.a(var15, 0, 0, var10, var11, var14, 0, var10);

         try {
            ImageIO.write(var15, "png", var21);
            Config.dbg("Exported: " + var21);
         } catch (Exception var17) {
            Config.warn("Error writing: " + var21);
            Config.warn("" + var17.getClass().getName() + ": " + var17.getMessage());
         }

         ++var8;
      }

   }

   public static int getGLMaximumTextureSize() {
      MatchBlock.b();
      int var1 = 65536;
      GL11.glTexImage2D('聤', 0, 6408, var1, var1, 0, 6408, 5121, (IntBuffer)null);
      int var2 = GL11.glGetError();
      int var3 = GL11.glGetTexLevelParameteri('聤', 0, 4096);
      if(var3 != 0) {
         return var1;
      } else {
         var1 = var1 >> 1;
         return -1;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
