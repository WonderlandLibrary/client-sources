package net.optifine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.acE;
import net.ahc;
import net.qT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository$Entry;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.optifine.DisplayModeComparator;
import net.optifine.DynamicLights;
import net.optifine.GlVersion;
import net.optifine.GuiMessage;
import net.optifine.MatchBlock;
import net.optifine.Reflector;
import net.optifine.TextureUtils;
import net.optifine.VersionCheckThread;
import net.shadersmod.client.Shaders;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Config {
   public static final String p = "OptiFine";
   public static final String c = "1.8.8";
   public static final String l = "HD_U";
   public static final String o = "H8";
   public static final String D = "OptiFine_1.8.8_HD_U_H8";
   private static String newRelease = null;
   private static boolean notify64BitJava = false;
   public static String openGlVersion = null;
   public static String openGlRenderer = null;
   public static String openGlVendor = null;
   public static String[] openGlExtensions = null;
   public static GlVersion glVersion = null;
   public static GlVersion glslVersion = null;
   public static int minecraftVersionInt = -1;
   public static boolean fancyFogAvailable = false;
   public static boolean occlusionAvailable = false;
   private static GameSettings gameSettings = null;
   private static Minecraft minecraft = Minecraft.getInstance();
   private static boolean initialized = false;
   private static Thread minecraftThread = null;
   private static DisplayMode desktopDisplayMode = null;
   private static DisplayMode[] displayModes = null;
   private static int antialiasingLevel = 0;
   private static int availableProcessors = 0;
   public static boolean zoomMode = false;
   private static int texturePackClouds = 0;
   public static boolean waterOpacityChanged = false;
   private static boolean fullscreenModeChecked = false;
   private static boolean desktopModeChecked = false;
   private static DefaultResourcePack defaultResourcePackLazy = null;
   public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
   private static final Logger LOGGER = LogManager.getLogger();

   public static String getVersion() {
      return "OptiFine_1.8.8_HD_U_H8";
   }

   public static String getVersionDebug() {
      MatchBlock.b();
      StringBuilder var1 = new StringBuilder(32);
      if(al()) {
         var1.append("DL: ");
         var1.append(DynamicLights.getCount());
         var1.append(", ");
      }

      var1.append("OptiFine_1.8.8_HD_U_H8");
      String var2 = Shaders.getShaderPackName();
      if(var2 != null) {
         var1.append(", ");
         var1.append(var2);
      }

      return var1.toString();
   }

   public static void initGameSettings(GameSettings var0) {
      acE[] var1 = MatchBlock.b();
      if(gameSettings == null) {
         gameSettings = var0;
         desktopDisplayMode = Display.getDesktopDisplayMode();
         updateAvailableProcessors();
         ahc.a((String)"net.optifine.ForgeSplashCompatible", (Object)Boolean.TRUE);
      }

   }

   public static void initDisplay() {
      checkInitialized();
      antialiasingLevel = gameSettings.ofAaLevel;
      checkDisplaySettings();
      checkDisplayMode();
      minecraftThread = Thread.currentThread();
      updateThreadPriorities();
      Shaders.startup(Minecraft.getInstance());
   }

   public static void checkInitialized() {
      acE[] var0 = MatchBlock.b();
      if(!initialized && Display.isCreated()) {
         initialized = true;
         checkOpenGlCaps();
         startVersionCheckThread();
      }

   }

   private static void checkOpenGlCaps() {
      log("");
      MatchBlock.b();
      log(getVersion());
      log("Build: " + getBuild());
      log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
      log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
      log("LWJGL: " + Sys.getVersion());
      openGlVersion = GL11.glGetString(7938);
      openGlRenderer = GL11.glGetString(7937);
      openGlVendor = GL11.glGetString(7936);
      log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
      log("OpenGL Version: " + getOpenGlVersionString());
      if(!GLContext.getCapabilities().OpenGL12) {
         log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
      }

      fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance;
      if(!fancyFogAvailable) {
         log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
      }

      occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query;
      if(!occlusionAvailable) {
         log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
      }

      int var1 = TextureUtils.getGLMaximumTextureSize();
      dbg("Maximum texture size: " + var1 + "x" + var1);
   }

   private static String getBuild() {
      acE[] var0 = MatchBlock.b();
      Class var10000 = Config.class;
      String var10001 = "/buildof.txt";

      try {
         InputStream var1 = var10000.getResourceAsStream(var10001);
         return var1 == null?null:readLines(var1)[0];
      } catch (Exception var2) {
         warn("" + var2.getClass().getName() + ": " + var2.getMessage());
         return null;
      }
   }

   public static boolean isFancyFogAvailable() {
      return fancyFogAvailable;
   }

   public static boolean isOcclusionAvailable() {
      return occlusionAvailable;
   }

   public static int getMinecraftVersionInt() {
      acE[] var0 = MatchBlock.b();
      if(minecraftVersionInt < 0) {
         String[] var1 = tokenize("1.8.8", ".");
         int var2 = 0;
         if(var1.length > 0) {
            var2 += 10000 * parseInt(var1[0], 0);
         }

         if(var1.length > 1) {
            var2 += 100 * parseInt(var1[1], 0);
         }

         if(var1.length > 2) {
            var2 += parseInt(var1[2], 0);
         }

         minecraftVersionInt = var2;
      }

      return minecraftVersionInt;
   }

   public static String getOpenGlVersionString() {
      GlVersion var0 = getGlVersion();
      return "" + var0.getMajor() + "." + var0.getMinor() + "." + var0.getRelease();
   }

   private static GlVersion getGlVersionLwjgl() {
      acE[] var0 = MatchBlock.b();
      return GLContext.getCapabilities().OpenGL44?new GlVersion(4, 4):(GLContext.getCapabilities().OpenGL43?new GlVersion(4, 3):(GLContext.getCapabilities().OpenGL42?new GlVersion(4, 2):(GLContext.getCapabilities().OpenGL41?new GlVersion(4, 1):(GLContext.getCapabilities().OpenGL40?new GlVersion(4, 0):(GLContext.getCapabilities().OpenGL33?new GlVersion(3, 3):(GLContext.getCapabilities().OpenGL32?new GlVersion(3, 2):(GLContext.getCapabilities().OpenGL31?new GlVersion(3, 1):(GLContext.getCapabilities().OpenGL30?new GlVersion(3, 0):(GLContext.getCapabilities().OpenGL21?new GlVersion(2, 1):(GLContext.getCapabilities().OpenGL20?new GlVersion(2, 0):(GLContext.getCapabilities().OpenGL15?new GlVersion(1, 5):(GLContext.getCapabilities().OpenGL14?new GlVersion(1, 4):(GLContext.getCapabilities().OpenGL13?new GlVersion(1, 3):(GLContext.getCapabilities().OpenGL12?new GlVersion(1, 2):(GLContext.getCapabilities().OpenGL11?new GlVersion(1, 1):new GlVersion(1, 0))))))))))))))));
   }

   public static GlVersion getGlVersion() {
      acE[] var0 = MatchBlock.b();
      if(glVersion == null) {
         String var1 = GL11.glGetString(7938);
         glVersion = parseGlVersion(var1, (GlVersion)null);
         if(glVersion == null) {
            glVersion = getGlVersionLwjgl();
         }

         if(glVersion == null) {
            glVersion = new GlVersion(1, 0);
         }
      }

      return glVersion;
   }

   public static GlVersion getGlslVersion() {
      acE[] var0 = MatchBlock.b();
      if(glslVersion == null) {
         String var1 = GL11.glGetString('讌');
         glslVersion = parseGlVersion(var1, (GlVersion)null);
         if(glslVersion == null) {
            glslVersion = new GlVersion(1, 10);
         }
      }

      return glslVersion;
   }

   public static GlVersion parseGlVersion(String var0, GlVersion var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         String var10000 = "([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?";

         try {
            Pattern var3 = Pattern.compile(var10000);
            Matcher var4 = var3.matcher(var0);
            if(!var4.matches()) {
               return var1;
            } else {
               int var5 = Integer.parseInt(var4.group(1));
               int var6 = Integer.parseInt(var4.group(2));
               int var7 = var4.group(4) != null?Integer.parseInt(var4.group(4)):0;
               String var8 = var4.group(5);
               return new GlVersion(var5, var6, var7, var8);
            }
         } catch (Exception var9) {
            var9.printStackTrace();
            return var1;
         }
      }
   }

   public static String[] getOpenGlExtensions() {
      acE[] var0 = MatchBlock.b();
      if(openGlExtensions == null) {
         openGlExtensions = detectOpenGlExtensions();
      }

      return openGlExtensions;
   }

   private static String[] detectOpenGlExtensions() {
      acE[] var0 = MatchBlock.b();

      try {
         GlVersion var1 = getGlVersion();
         if(var1.getMajor() >= 3) {
            int var2 = GL11.glGetInteger('舝');
            if(var2 > 0) {
               String[] var3 = new String[var2];
               int var4 = 0;
               if(var4 < var2) {
                  var3[var4] = GL30.glGetStringi(7939, var4);
                  ++var4;
               }

               return var3;
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      short var10000 = 7939;

      try {
         String var7 = GL11.glGetString(var10000);
         return var7.split(" ");
      } catch (Exception var5) {
         var5.printStackTrace();
         return new String[0];
      }
   }

   public static void updateThreadPriorities() {
      MatchBlock.b();
      updateAvailableProcessors();
      boolean var1 = true;
      if(isSingleProcessor()) {
         if(isSmoothWorld()) {
            minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 1);
         }

         minecraftThread.setPriority(5);
         setThreadPriority("Server thread", 5);
      }

      minecraftThread.setPriority(10);
      setThreadPriority("Server thread", 5);
   }

   private static void setThreadPriority(String param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isMinecraftThread() {
      return Thread.currentThread() == minecraftThread;
   }

   private static void startVersionCheckThread() {
      VersionCheckThread var0 = new VersionCheckThread();
      var0.start();
   }

   public static boolean be() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.mipmapLevels > 0;
   }

   public static int getMipmapLevels() {
      return gameSettings.mipmapLevels;
   }

   public static int getMipmapType() {
      switch(gameSettings.ofMipmapType) {
      case 2:
         if(isMultiTexture()) {
            return 9985;
         }

         return 9986;
      case 3:
         if(isMultiTexture()) {
            return 9987;
         }

         return 9986;
      default:
         return 9986;
      }
   }

   public static boolean isUseAlphaFunc() {
      MatchBlock.b();
      float var1 = getAlphaFuncLevel();
      return var1 > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F;
   }

   public static float getAlphaFuncLevel() {
      return DEF_ALPHA_FUNC_LEVEL.floatValue();
   }

   public static boolean isFogFancy() {
      acE[] var0 = MatchBlock.b();
      return isFancyFogAvailable() && gameSettings.ofFogType == 2;
   }

   public static boolean ad() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofFogType == 1;
   }

   public static boolean aT() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofFogType == 3;
   }

   public static float getFogStart() {
      return gameSettings.ofFogStart;
   }

   public static void dbg(String var0) {
      LOGGER.info("[OptiFine] " + var0);
   }

   public static void warn(String var0) {
      LOGGER.warn("[OptiFine] " + var0);
   }

   public static void error(String var0) {
      LOGGER.error("[OptiFine] " + var0);
   }

   public static void log(String var0) {
      dbg(var0);
   }

   public static int getUpdatesPerFrame() {
      return gameSettings.ofChunkUpdates;
   }

   public static boolean isDynamicUpdates() {
      return gameSettings.ofChunkUpdatesDynamic;
   }

   public static boolean as() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofRain == 0?gameSettings.fancyGraphics:gameSettings.ofRain == 2;
   }

   public static boolean aV() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofRain == 3;
   }

   public static boolean isCloudsFancy() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofClouds != 0?gameSettings.ofClouds == 2:(isShaders() && !Shaders.shaderPackClouds.b()?Shaders.shaderPackClouds.d():(texturePackClouds != 0?texturePackClouds == 2:gameSettings.fancyGraphics));
   }

   public static boolean isCloudsOff() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofClouds != 0?gameSettings.ofClouds == 3:(isShaders() && !Shaders.shaderPackClouds.b()?Shaders.shaderPackClouds.c():texturePackClouds == 3);
   }

   public static void Y() {
      texturePackClouds = 0;
      MatchBlock.b();
      IResourceManager var1 = getResourceManager();
      if(var1 != null) {
         try {
            InputStream var2 = var1.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            return;
         } catch (Exception var3) {
            ;
         }
      }

   }

   public static ModelManager getModelManager() {
      return minecraft.getRenderItem().modelManager;
   }

   public static boolean O() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTrees == 0?gameSettings.fancyGraphics:gameSettings.ofTrees != 1;
   }

   public static boolean y() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTrees == 4;
   }

   public static boolean a7() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTrees == 0?!gameSettings.fancyGraphics:gameSettings.ofTrees == 4;
   }

   public static boolean E() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofDroppedItems == 0?gameSettings.fancyGraphics:gameSettings.ofDroppedItems == 2;
   }

   public static int limit(int var0, int var1, int var2) {
      return var0 < var1?var1:Math.min(var0, var2);
   }

   public static float limit(float var0, float var1, float var2) {
      acE[] var3 = MatchBlock.b();
      return var0 < var1?var1:Math.min(var0, var2);
   }

   public static double limit(double var0, double var2, double var4) {
      acE[] var6 = MatchBlock.b();
      return var0 < var2?var2:Math.min(var0, var4);
   }

   public static float limitTo1(float var0) {
      acE[] var1 = MatchBlock.b();
      return var0 < 0.0F?0.0F:Math.min(var0, 1.0F);
   }

   public static boolean a() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofAnimatedWater != 2;
   }

   public static boolean az() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofAnimatedWater == 1;
   }

   public static boolean isAnimatedPortal() {
      return gameSettings.ofAnimatedPortal;
   }

   public static boolean i() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofAnimatedLava != 2;
   }

   public static boolean L() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofAnimatedLava == 1;
   }

   public static boolean isAnimatedFire() {
      return gameSettings.ofAnimatedFire;
   }

   public static boolean isAnimatedRedstone() {
      return gameSettings.ofAnimatedRedstone;
   }

   public static boolean isAnimatedExplosion() {
      return gameSettings.ofAnimatedExplosion;
   }

   public static boolean isAnimatedFlame() {
      return gameSettings.ofAnimatedFlame;
   }

   public static boolean isAnimatedSmoke() {
      return gameSettings.ofAnimatedSmoke;
   }

   public static boolean isVoidParticles() {
      return gameSettings.ofVoidParticles;
   }

   public static boolean isWaterParticles() {
      return gameSettings.ofWaterParticles;
   }

   public static boolean isRainSplash() {
      return gameSettings.ofRainSplash;
   }

   public static boolean isPortalParticles() {
      return gameSettings.ofPortalParticles;
   }

   public static boolean isPotionParticles() {
      return gameSettings.ofPotionParticles;
   }

   public static boolean isFireworkParticles() {
      return gameSettings.ofFireworkParticles;
   }

   public static float getAmbientOcclusionLevel() {
      acE[] var0 = MatchBlock.b();
      return isShaders() && Shaders.aoLevel >= 0.0F?Shaders.aoLevel:gameSettings.ofAoLevel;
   }

   public static String a(Object[] var0) {
      return "";
   }

   public static String a(int[] var0) {
      acE[] var1 = MatchBlock.b();
      return "";
   }

   public static Minecraft getMinecraft() {
      return minecraft;
   }

   public static TextureManager getTextureManager() {
      return minecraft.getTextureManager();
   }

   public static IResourceManager getResourceManager() {
      return minecraft.getResourceManager();
   }

   public static InputStream getResourceStream(ResourceLocation var0) throws IOException {
      return getResourceStream(minecraft.getResourceManager(), var0);
   }

   public static InputStream getResourceStream(IResourceManager var0, ResourceLocation var1) throws IOException {
      MatchBlock.b();
      IResource var3 = var0.getResource(var1);
      return var3 == null?null:var3.getInputStream();
   }

   public static IResource getResource(ResourceLocation var0) throws IOException {
      return minecraft.getResourceManager().getResource(var0);
   }

   public static boolean hasResource(ResourceLocation var0) {
      IResourcePack var1 = getDefiningResourcePack(var0);
      return true;
   }

   public static boolean hasResource(IResourceManager var0, ResourceLocation var1) {
      IResourceManager var10000 = var0;
      ResourceLocation var10001 = var1;

      try {
         IResource var2 = var10000.getResource(var10001);
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public static IResourcePack[] getResourcePacks() {
      MatchBlock.b();
      ResourcePackRepository var1 = minecraft.getResourcePackRepository();
      List var2 = var1.getRepositoryEntries();
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         var3.add(((ResourcePackRepository$Entry)var5).getResourcePack());
      }

      if(var1.getResourcePackInstance() != null) {
         var3.add(var1.getResourcePackInstance());
      }

      return (IResourcePack[])((IResourcePack[])var3.toArray(new IResourcePack[0]));
   }

   public static String getResourcePackNames() {
      acE[] var0 = MatchBlock.b();
      if(minecraft.getResourcePackRepository() == null) {
         return "";
      } else {
         IResourcePack[] var1 = getResourcePacks();
         if(var1.length <= 0) {
            return getDefaultResourcePack().getPackName();
         } else {
            String[] var2 = new String[var1.length];
            int var3 = 0;
            if(var3 < var1.length) {
               var2[var3] = var1[var3].getPackName();
               ++var3;
            }

            return a((Object[])var2);
         }
      }
   }

   public static DefaultResourcePack getDefaultResourcePack() {
      acE[] var0 = MatchBlock.b();
      if(defaultResourcePackLazy == null) {
         Minecraft var1 = Minecraft.getInstance();
         defaultResourcePackLazy = (DefaultResourcePack)Reflector.getFieldValue(var1, Reflector.Minecraft_defaultResourcePack);
         if(defaultResourcePackLazy == null) {
            ResourcePackRepository var2 = var1.getResourcePackRepository();
            if(var2 != null) {
               defaultResourcePackLazy = (DefaultResourcePack)var2.rprDefaultResourcePack;
            }
         }
      }

      return defaultResourcePackLazy;
   }

   public static boolean isFromDefaultResourcePack(ResourceLocation var0) {
      IResourcePack var1 = getDefiningResourcePack(var0);
      return var1 == getDefaultResourcePack();
   }

   public static IResourcePack getDefiningResourcePack(ResourceLocation var0) {
      MatchBlock.b();
      ResourcePackRepository var2 = minecraft.getResourcePackRepository();
      IResourcePack var3 = var2.getResourcePackInstance();
      if(var3 != null && var3.resourceExists(var0)) {
         return var3;
      } else {
         List var4 = (List)Reflector.getFieldValue(var2, Reflector.ResourcePackRepository_repositoryEntries);
         if(var4 != null) {
            int var5 = var4.size() - 1;
            ResourcePackRepository$Entry var6 = (ResourcePackRepository$Entry)var4.get(var5);
            IResourcePack var7 = var6.getResourcePack();
            if(var7.resourceExists(var0)) {
               return var7;
            }

            --var5;
         }

         return getDefaultResourcePack().resourceExists(var0)?getDefaultResourcePack():null;
      }
   }

   public static RenderGlobal getRenderGlobal() {
      return minecraft.renderGlobal;
   }

   public static boolean T() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofBetterGrass != 3;
   }

   public static boolean ai() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofBetterGrass == 2;
   }

   public static boolean isWeatherEnabled() {
      return gameSettings.ofWeather;
   }

   public static boolean isSkyEnabled() {
      return gameSettings.ofSky;
   }

   public static boolean isSunMoonEnabled() {
      return gameSettings.ofSunMoon;
   }

   public static boolean isSunTexture() {
      acE[] var0 = MatchBlock.b();
      return isSunMoonEnabled() && (!isShaders() || Shaders.isSun());
   }

   public static boolean isMoonTexture() {
      acE[] var0 = MatchBlock.b();
      return isSunMoonEnabled() && (!isShaders() || Shaders.isMoon());
   }

   public static boolean isVignetteEnabled() {
      boolean var10000;
      label0: {
         acE[] var0 = MatchBlock.b();
         if(!isShaders() || Shaders.isVignette()) {
            if(gameSettings.ofVignette == 0) {
               if(gameSettings.fancyGraphics) {
                  break label0;
               }
            } else if(gameSettings.ofVignette == 2) {
               break label0;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   public static boolean isStarsEnabled() {
      return gameSettings.ofStars;
   }

   public static void sleep(long var0) {
      long var10000 = var0;

      try {
         Thread.sleep(var10000);
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

   }

   public static boolean bd() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTime == 1;
   }

   public static boolean aQ() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTime == 0;
   }

   public static boolean a_() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTime == 2;
   }

   public static boolean isClearWater() {
      return gameSettings.ofClearWater;
   }

   public static int getAnisotropicFilterLevel() {
      return gameSettings.ofAfLevel;
   }

   public static boolean isAnisotropicFiltering() {
      acE[] var0 = MatchBlock.b();
      return getAnisotropicFilterLevel() > 1;
   }

   public static int getAntialiasingLevel() {
      return antialiasingLevel;
   }

   public static boolean isAntialiasing() {
      acE[] var0 = MatchBlock.b();
      return getAntialiasingLevel() > 0;
   }

   public static boolean isAntialiasingConfigured() {
      acE[] var0 = MatchBlock.b();
      return getGameSettings().ofAaLevel > 0;
   }

   public static boolean isMultiTexture() {
      return getAnisotropicFilterLevel() > 1 || getAntialiasingLevel() > 0;
   }

   public static boolean between(int var0, int var1, int var2) {
      acE[] var3 = MatchBlock.b();
      return var0 >= var1 && var0 <= var2;
   }

   public static boolean isDrippingWaterLava() {
      return gameSettings.ofDrippingWaterLava;
   }

   public static boolean isBetterSnow() {
      return gameSettings.ofBetterSnow;
   }

   public static Dimension getFullscreenDimension() {
      acE[] var0 = MatchBlock.b();
      if(desktopDisplayMode == null) {
         return null;
      } else if(gameSettings == null) {
         return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
      } else {
         String var1 = gameSettings.ofFullscreenMode;
         if(var1.equals("Default")) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
         } else {
            String[] var2 = tokenize(var1, " x");
            return var2.length < 2?new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()):new Dimension(parseInt(var2[0], -1), parseInt(var2[1], -1));
         }
      }
   }

   public static int parseInt(String var0, int var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         String var10000 = var0;

         try {
            var0 = var10000.trim();
            return Integer.parseInt(var0);
         } catch (NumberFormatException var4) {
            return var1;
         }
      }
   }

   public static float parseFloat(String var0, float var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         String var10000 = var0;

         try {
            var0 = var10000.trim();
            return Float.parseFloat(var0);
         } catch (NumberFormatException var4) {
            return var1;
         }
      }
   }

   public static boolean parseBoolean(String var0, boolean var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         String var10000 = var0;

         try {
            var0 = var10000.trim();
            return Boolean.parseBoolean(var0);
         } catch (NumberFormatException var4) {
            return var1;
         }
      }
   }

   public static String[] tokenize(String var0, String var1) {
      MatchBlock.b();
      StringTokenizer var3 = new StringTokenizer(var0, var1);
      ArrayList var4 = new ArrayList();
      if(var3.hasMoreTokens()) {
         String var5 = var3.nextToken();
         var4.add(var5);
      }

      return (String[])((String[])var4.toArray(new String[0]));
   }

   public static DisplayMode getDesktopDisplayMode() {
      return desktopDisplayMode;
   }

   public static DisplayMode[] getDisplayModes() {
      acE[] var0 = MatchBlock.b();
      if(displayModes == null) {
         try {
            DisplayMode[] var1 = Display.getAvailableDisplayModes();
            Set var2 = getDisplayModeDimensions(var1);
            ArrayList var3 = new ArrayList();
            Iterator var4 = var2.iterator();
            if(var4.hasNext()) {
               Dimension var5 = (Dimension)var4.next();
               DisplayMode[] var6 = getDisplayModes(var1, var5);
               DisplayMode var7 = getDisplayMode(var6, desktopDisplayMode);
               var3.add(var7);
            }

            DisplayMode[] var9 = (DisplayMode[])((DisplayMode[])var3.toArray(new DisplayMode[0]));
            Arrays.sort(var9, new DisplayModeComparator());
            return var9;
         } catch (Exception var8) {
            var8.printStackTrace();
            displayModes = new DisplayMode[]{desktopDisplayMode};
         }
      }

      return displayModes;
   }

   public static DisplayMode getLargestDisplayMode() {
      MatchBlock.b();
      DisplayMode[] var1 = getDisplayModes();
      if(var1 != null && var1.length >= 1) {
         DisplayMode var2 = var1[var1.length - 1];
         return desktopDisplayMode.getWidth() > var2.getWidth()?desktopDisplayMode:(desktopDisplayMode.getWidth() == var2.getWidth() && desktopDisplayMode.getHeight() > var2.getHeight()?desktopDisplayMode:var2);
      } else {
         return desktopDisplayMode;
      }
   }

   private static Set getDisplayModeDimensions(DisplayMode[] var0) {
      MatchBlock.b();
      HashSet var2 = new HashSet();
      int var4 = var0.length;
      int var5 = 0;
      if(var5 < var4) {
         DisplayMode var6 = var0[var5];
         Dimension var7 = new Dimension(var6.getWidth(), var6.getHeight());
         var2.add(var7);
         ++var5;
      }

      return var2;
   }

   private static DisplayMode[] getDisplayModes(DisplayMode[] var0, Dimension var1) {
      MatchBlock.b();
      ArrayList var3 = new ArrayList();
      int var5 = var0.length;
      int var6 = 0;
      if(var6 < var5) {
         DisplayMode var7 = var0[var6];
         if((double)var7.getWidth() == var1.getWidth() && (double)var7.getHeight() == var1.getHeight()) {
            var3.add(var7);
         }

         ++var6;
      }

      return (DisplayMode[])((DisplayMode[])var3.toArray(new DisplayMode[0]));
   }

   private static DisplayMode getDisplayMode(DisplayMode[] var0, DisplayMode var1) {
      acE[] var2 = MatchBlock.b();
      int var4 = var0.length;
      int var5 = 0;
      if(var5 < var4) {
         DisplayMode var6 = var0[var5];
         if(var6.getBitsPerPixel() == var1.getBitsPerPixel() && var6.getFrequency() == var1.getFrequency()) {
            return var6;
         }

         ++var5;
      }

      if(var0.length <= 0) {
         return null;
      } else {
         Arrays.sort(var0, new DisplayModeComparator());
         return var0[var0.length - 1];
      }
   }

   public static String[] getDisplayModeNames() {
      MatchBlock.b();
      DisplayMode[] var1 = getDisplayModes();
      String[] var2 = new String[var1.length];
      int var3 = 0;
      if(var3 < var1.length) {
         DisplayMode var4 = var1[var3];
         String var5 = "" + var4.getWidth() + "x" + var4.getHeight();
         var2[var3] = var5;
         ++var3;
      }

      return var2;
   }

   public static DisplayMode a(Dimension var0) {
      MatchBlock.b();
      DisplayMode[] var2 = getDisplayModes();
      int var4 = var2.length;
      int var5 = 0;
      if(var5 < var4) {
         DisplayMode var6 = var2[var5];
         if(var6.getWidth() == var0.width && var6.getHeight() == var0.height) {
            return var6;
         }

         ++var5;
      }

      return desktopDisplayMode;
   }

   public static boolean isAnimatedTerrain() {
      return gameSettings.ofAnimatedTerrain;
   }

   public static boolean isAnimatedTextures() {
      return gameSettings.ofAnimatedTextures;
   }

   public static boolean isSwampColors() {
      return gameSettings.ofSwampColors;
   }

   public static boolean isRandomMobs() {
      return gameSettings.ofRandomMobs;
   }

   public static void checkGlError(String var0) {
      MatchBlock.b();
      int var2 = GL11.glGetError();
      if(var2 != 0) {
         String var3 = GLU.gluErrorString(var2);
         error("OpenGlError: " + var2 + " (" + var3 + "), at: " + var0);
      }

   }

   public static boolean isSmoothBiomes() {
      return gameSettings.ofSmoothBiomes;
   }

   public static boolean isCustomColors() {
      return gameSettings.ofCustomColors;
   }

   public static boolean isCustomSky() {
      return gameSettings.ofCustomSky;
   }

   public static boolean isCustomFonts() {
      return gameSettings.ofCustomFonts;
   }

   public static boolean isShowCapes() {
      return gameSettings.ofShowCapes;
   }

   public static boolean k() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofConnectedTextures != 3;
   }

   public static boolean isNaturalTextures() {
      return gameSettings.ofNaturalTextures;
   }

   public static boolean B() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofConnectedTextures == 2;
   }

   public static boolean isTranslucentBlocksFancy() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofTranslucentBlocks == 0?gameSettings.fancyGraphics:gameSettings.ofTranslucentBlocks == 2;
   }

   public static boolean isShaders() {
      return Shaders.shaderPackLoaded;
   }

   public static String[] readLines(File var0) throws IOException {
      FileInputStream var1 = new FileInputStream(var0);
      return readLines((InputStream)var1);
   }

   public static String[] readLines(InputStream var0) throws IOException {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      InputStreamReader var3 = new InputStreamReader(var0, StandardCharsets.US_ASCII);
      BufferedReader var4 = new BufferedReader(var3);
      String var5 = var4.readLine();
      return (String[])((String[])var2.toArray(new String[0]));
   }

   public static String readFile(File var0) throws IOException {
      FileInputStream var1 = new FileInputStream(var0);
      return readInputStream(var1, "ASCII");
   }

   public static String readInputStream(InputStream var0) throws IOException {
      return readInputStream(var0, "ASCII");
   }

   public static String readInputStream(InputStream var0, String var1) throws IOException {
      InputStreamReader var3 = new InputStreamReader(var0, var1);
      BufferedReader var4 = new BufferedReader(var3);
      MatchBlock.b();
      StringBuilder var5 = new StringBuilder();
      String var6 = var4.readLine();
      return var5.toString();
   }

   public static void readAll(InputStream var0) throws IOException {
      MatchBlock.b();
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = new byte[1024];
      int var4 = var0.read(var3);
      var0.close();
      var2.toByteArray();
   }

   public static GameSettings getGameSettings() {
      return gameSettings;
   }

   public static String getNewRelease() {
      return newRelease;
   }

   public static void setNewRelease(String var0) {
      newRelease = var0;
   }

   public static int compareRelease(String var0, String var1) {
      String[] var3 = splitRelease(var0);
      String[] var4 = splitRelease(var1);
      String var5 = var3[0];
      MatchBlock.b();
      String var6 = var4[0];
      if(!var5.equals(var6)) {
         return var5.compareTo(var6);
      } else {
         int var7 = parseInt(var3[1], -1);
         int var8 = parseInt(var4[1], -1);
         if(var7 != var8) {
            return var7 - var8;
         } else {
            String var9 = var3[2];
            String var10 = var4[2];
            if(!var9.equals(var10)) {
               if(var9.isEmpty()) {
                  return 1;
               }

               if(var10.isEmpty()) {
                  return -1;
               }
            }

            return var9.compareTo(var10);
         }
      }
   }

   private static String[] splitRelease(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 != null && var0.length() > 0) {
         Pattern var2 = Pattern.compile("([A-Z])([0-9]+)(.*)");
         Matcher var3 = var2.matcher(var0);
         if(!var3.matches()) {
            return new String[]{"", "", ""};
         } else {
            String var4 = c(var3.group(1));
            String var5 = c(var3.group(2));
            String var6 = c(var3.group(3));
            return new String[]{var4, var5, var6};
         }
      } else {
         return new String[]{"", "", ""};
      }
   }

   public static int intHash(int var0) {
      var0 = var0 ^ 61 ^ var0 >> 16;
      var0 = var0 + (var0 << 3);
      var0 = var0 ^ var0 >> 4;
      var0 = var0 * 668265261;
      var0 = var0 ^ var0 >> 15;
      return var0;
   }

   public static int getRandom(BlockPos var0, int var1) {
      int var2 = intHash(var1 + 37);
      var2 = intHash(var2 + var0.getX());
      var2 = intHash(var2 + var0.getZ());
      var2 = intHash(var2 + var0.getY());
      return var2;
   }

   public static WorldServer ag() {
      MatchBlock.b();
      WorldClient var1 = minecraft.world;
      return null;
   }

   public static int getAvailableProcessors() {
      return availableProcessors;
   }

   public static void updateAvailableProcessors() {
      availableProcessors = Runtime.getRuntime().availableProcessors();
   }

   public static boolean isSingleProcessor() {
      acE[] var0 = MatchBlock.b();
      return getAvailableProcessors() <= 1;
   }

   public static boolean isSmoothWorld() {
      return gameSettings.ofSmoothWorld;
   }

   public static boolean isLazyChunkLoading() {
      acE[] var0 = MatchBlock.b();
      return isSingleProcessor() && gameSettings.ofLazyChunkLoading;
   }

   public static boolean isDynamicFov() {
      return gameSettings.ofDynamicFov;
   }

   public static boolean isAlternateBlocks() {
      return gameSettings.allowBlockAlternatives;
   }

   public static int getChunkViewDistance() {
      acE[] var0 = MatchBlock.b();
      return gameSettings == null?10:gameSettings.renderDistanceChunks;
   }

   public static boolean equals(Object var0, Object var1) {
      return Objects.equals(var0, var1);
   }

   public static boolean equalsOne(Object var0, Object[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var1 != null) {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            Object var6 = var1[var5];
            if(equals(var0, var6)) {
               return true;
            }

            ++var5;
         }
      }

      return false;
   }

   public static boolean isSameOne(Object var0, Object[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var1 != null) {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            Object var6 = var1[var5];
            if(var0 == var6) {
               return true;
            }

            ++var5;
         }
      }

      return false;
   }

   public static String c(String var0) {
      acE[] var1 = MatchBlock.b();
      return var0 == null?"":var0;
   }

   public static void checkDisplaySettings() {
      MatchBlock.b();
      int var1 = getAntialiasingLevel();
      DisplayMode var2 = Display.getDisplayMode();
      dbg("FSAA Samples: " + var1);

      try {
         Display.destroy();
         Display.setDisplayMode(var2);
         Display.create((new PixelFormat()).withDepthBits(24).withSamples(var1));
         Display.setResizable(false);
         Display.setResizable(true);
      } catch (LWJGLException var16) {
         warn("Error setting FSAA: " + var1 + "x");
         var16.printStackTrace();

         try {
            Display.setDisplayMode(var2);
            Display.create((new PixelFormat()).withDepthBits(24));
            Display.setResizable(false);
            Display.setResizable(true);
         } catch (LWJGLException var15) {
            var15.printStackTrace();
            DisplayMode var10000 = var2;

            try {
               Display.setDisplayMode(var10000);
               Display.create();
               Display.setResizable(false);
               Display.setResizable(true);
            } catch (LWJGLException var14) {
               var14.printStackTrace();
            }
         }
      }

      if(!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
         InputStream var3 = null;
         InputStream var4 = null;

         try {
            var3 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
            var4 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
            if(var3 != null && var4 != null) {
               Display.setIcon(new ByteBuffer[]{readIconImage(var3), readIconImage(var4)});
            }
         } catch (IOException var12) {
            warn("Error setting window icon: " + var12.getClass().getName() + ": " + var12.getMessage());
         } finally {
            IOUtils.closeQuietly(var3);
            IOUtils.closeQuietly(var4);
         }
      }

   }

   private static ByteBuffer readIconImage(InputStream var0) throws IOException {
      BufferedImage var2 = ImageIO.read(var0);
      MatchBlock.b();
      int[] var3 = qT.b(var2, 0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
      int var6 = var3.length;
      int var7 = 0;
      if(var7 < var6) {
         int var8 = var3[var7];
         var4.putInt(var8 << 8 | var8 >> 24 & 255);
         ++var7;
      }

      var4.flip();
      return var4;
   }

   public static void checkDisplayMode() {
      acE[] var0 = MatchBlock.b();

      try {
         if(minecraft.isFullScreen()) {
            if(fullscreenModeChecked) {
               return;
            }

            fullscreenModeChecked = true;
            desktopModeChecked = false;
            DisplayMode var1 = Display.getDisplayMode();
            Dimension var2 = getFullscreenDimension();
            return;
         }

         if(desktopModeChecked) {
            return;
         }

         desktopModeChecked = true;
         fullscreenModeChecked = false;
         minecraft.gameSettings.updateVSync();
         Display.update();
         GlStateManager.enableTexture2D();
         Display.setResizable(false);
         Display.setResizable(true);
      } catch (Exception var3) {
         var3.printStackTrace();
         gameSettings.ofFullscreenMode = "Default";
         gameSettings.saveOfOptions();
      }

   }

   public static void ay() {
      MatchBlock.b();
      minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
      if(minecraft.entityRenderer != null) {
         minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
      }

   }

   public static Object[] addObjectToArray(Object[] var0, Object var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         throw new NullPointerException("The given array is NULL");
      } else {
         int var3 = var0.length;
         int var4 = var3 + 1;
         Object[] var5 = (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var4));
         System.arraycopy(var0, 0, var5, 0, var3);
         var5[var3] = var1;
         return var5;
      }
   }

   public static Object[] addObjectToArray(Object[] var0, Object var1, int var2) {
      ArrayList var3 = new ArrayList(Arrays.asList(var0));
      var3.add(var2, var1);
      Object[] var4 = (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var3.size()));
      return var3.toArray(var4);
   }

   public static Object[] addObjectsToArray(Object[] var0, Object[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         throw new NullPointerException("The given array is NULL");
      } else if(var1.length == 0) {
         return var0;
      } else {
         int var3 = var0.length;
         int var4 = var3 + var1.length;
         Object[] var5 = (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var4));
         System.arraycopy(var0, 0, var5, 0, var3);
         System.arraycopy(var1, 0, var5, var3, var1.length);
         return var5;
      }
   }

   public static boolean isCustomItems() {
      return gameSettings.ofCustomItems;
   }

   public static void drawFps() {
      int var0 = Minecraft.getInstance().getDebugFPS();
      String var1 = getUpdates(minecraft.debug);
      int var2 = minecraft.renderGlobal.getCountActiveRenderers();
      int var3 = minecraft.renderGlobal.getCountEntitiesRendered();
      int var4 = minecraft.renderGlobal.getCountTileEntitiesRendered();
      String var5 = "" + var0 + " fps, C: " + var2 + ", E: " + var3 + "+" + var4 + ", U: " + var1;
      minecraft.fontRendererObj.drawString(var5, 2.0F, 2.0F, -2039584);
   }

   private static String getUpdates(String var0) {
      MatchBlock.b();
      int var2 = var0.indexOf(40);
      if(var2 < 0) {
         return "";
      } else {
         int var3 = var0.indexOf(32, var2);
         return "";
      }
   }

   public static int getBitsOs() {
      String var0 = System.getenv("ProgramFiles(X86)");
      return 64;
   }

   public static int getBitsJre() {
      String[] var1 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      MatchBlock.b();
      int var3 = var1.length;
      int var4 = 0;
      if(var4 < var3) {
         String var5 = var1[var4];
         String var6 = System.getProperty(var5);
         if(var6.contains("64")) {
            return 64;
         }

         ++var4;
      }

      return 32;
   }

   public static boolean isNotify64BitJava() {
      return notify64BitJava;
   }

   public static void setNotify64BitJava(boolean var0) {
      notify64BitJava = var0;
   }

   public static boolean isConnectedModels() {
      return false;
   }

   public static void showGuiMessage(String var0, String var1) {
      GuiMessage var2 = new GuiMessage(minecraft.currentScreen, var0, var1);
      minecraft.displayGuiScreen(var2);
   }

   public static int[] addIntToArray(int[] var0, int var1) {
      return addIntsToArray(var0, new int[]{var1});
   }

   public static int[] addIntsToArray(int[] var0, int[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         int var3 = var0.length;
         int var4 = var3 + var1.length;
         int[] var5 = new int[var4];
         System.arraycopy(var0, 0, var5, 0, var3);
         System.arraycopy(var1, 0, var5, var3, var1.length);
         return var5;
      } else {
         throw new NullPointerException("The given array is NULL");
      }
   }

   public static DynamicTexture getMojangLogoTexture(DynamicTexture param0) {
      // $FF: Couldn't be decompiled
   }

   public static void writeFile(File var0, String var1) throws IOException {
      FileOutputStream var2 = new FileOutputStream(var0);
      byte[] var3 = var1.getBytes(StandardCharsets.US_ASCII);
      var2.write(var3);
      var2.close();
   }

   public static TextureMap getTextureMap() {
      return getMinecraft().getTextureMapBlocks();
   }

   public static boolean al() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofDynamicLights != 3;
   }

   public static boolean aO() {
      acE[] var0 = MatchBlock.b();
      return gameSettings.ofDynamicLights == 1;
   }

   public static boolean isDynamicHandLight() {
      acE[] var0 = MatchBlock.b();
      return al() && (!isShaders() || Shaders.isDynamicHandLight());
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
