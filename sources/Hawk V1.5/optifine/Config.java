package optifine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
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
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import org.apache.commons.io.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import shadersmod.client.Shaders;

public class Config {
   private static String newRelease = null;
   private static DefaultResourcePack defaultResourcePack = null;
   public static boolean zoomMode = false;
   public static final String OF_RELEASE = "H6";
   public static final String OF_EDITION = "HD_U";
   private static ModelManager modelManager = null;
   private static boolean fullscreenModeChecked = false;
   public static boolean waterOpacityChanged = false;
   public static String openGlVendor = null;
   private static PrintStream systemOut;
   private static Thread minecraftThread = null;
   private static GameSettings gameSettings = null;
   private static boolean initialized = false;
   public static String openGlRenderer = null;
   private static boolean desktopModeChecked = false;
   private static int texturePackClouds = 0;
   public static final String VERSION = "OptiFine_1.8_HD_U_H6";
   public static boolean occlusionAvailable = false;
   public static String openGlVersion = null;
   public static final String OF_NAME = "OptiFine";
   public static boolean fancyFogAvailable = false;
   private static boolean notify64BitJava = false;
   private static Minecraft minecraft = null;
   private static int availableProcessors = 0;
   public static final Float DEF_ALPHA_FUNC_LEVEL;
   public static final String MC_VERSION = "1.8";
   private static int antialiasingLevel = 0;
   private static DisplayMode desktopDisplayMode = null;

   private static ByteBuffer readIconImage(InputStream var0) throws IOException {
      BufferedImage var1 = ImageIO.read(var0);
      int[] var2 = var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), (int[])null, 0, var1.getWidth());
      ByteBuffer var3 = ByteBuffer.allocate(4 * var2.length);
      int[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var4[var6];
         var3.putInt(var7 << 8 | var7 >> 24 & 255);
      }

      var3.flip();
      return var3;
   }

   public static int getBitsOs() {
      String var0 = System.getenv("ProgramFiles(X86)");
      return var0 != null ? 64 : 32;
   }

   public static float getFogStart() {
      return gameSettings.ofFogStart;
   }

   public static String readInputStream(InputStream var0) throws IOException {
      return readInputStream(var0, "ASCII");
   }

   public static boolean isFogFancy() {
      return !isFancyFogAvailable() ? false : gameSettings.ofFogType == 2;
   }

   public static void updateTexturePackClouds() {
      texturePackClouds = 0;
      IResourceManager var0 = getResourceManager();
      if (var0 != null) {
         try {
            InputStream var1 = var0.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (var1 == null) {
               return;
            }

            Properties var2 = new Properties();
            var2.load(var1);
            var1.close();
            String var3 = var2.getProperty("clouds");
            if (var3 == null) {
               return;
            }

            dbg(String.valueOf((new StringBuilder("Texture pack clouds: ")).append(var3)));
            var3 = var3.toLowerCase();
            if (var3.equals("fast")) {
               texturePackClouds = 1;
            }

            if (var3.equals("fancy")) {
               texturePackClouds = 2;
            }

            if (var3.equals("off")) {
               texturePackClouds = 3;
            }
         } catch (Exception var4) {
         }
      }

   }

   public static String getResourcePackNames() {
      if (minecraft == null) {
         return "";
      } else if (minecraft.getResourcePackRepository() == null) {
         return "";
      } else {
         IResourcePack[] var0 = getResourcePacks();
         if (var0.length <= 0) {
            return getDefaultResourcePack().getPackName();
         } else {
            String[] var1 = new String[var0.length];

            for(int var2 = 0; var2 < var0.length; ++var2) {
               var1[var2] = var0[var2].getPackName();
            }

            String var3 = arrayToString((Object[])var1);
            return var3;
         }
      }
   }

   public static boolean isSingleProcessor() {
      return getAvailableProcessors() <= 1;
   }

   public static boolean isTreesSmart() {
      return gameSettings.ofTrees == 4;
   }

   public static String fillLeft(String var0, int var1, char var2) {
      if (var0 == null) {
         var0 = "";
      }

      if (var0.length() >= var1) {
         return var0;
      } else {
         StringBuffer var3 = new StringBuffer(var0);

         while(var3.length() < var1 - var0.length()) {
            var3.append(var2);
         }

         return String.valueOf((new StringBuilder(String.valueOf(var3.toString()))).append(var0));
      }
   }

   public static boolean isOcclusionAvailable() {
      return occlusionAvailable;
   }

   public static int[] addIntToArray(int[] var0, int var1) {
      return addIntsToArray(var0, new int[]{var1});
   }

   public static int getRandom(BlockPos var0, int var1) {
      int var2 = intHash(var1 + 37);
      var2 = intHash(var2 + var0.getX());
      var2 = intHash(var2 + var0.getZ());
      var2 = intHash(var2 + var0.getY());
      return var2;
   }

   private static void checkOpenGlCaps() {
      log("");
      log(getVersion());
      log(String.valueOf((new StringBuilder("Build: ")).append(getBuild())));
      log(String.valueOf((new StringBuilder("OS: ")).append(System.getProperty("os.name")).append(" (").append(System.getProperty("os.arch")).append(") version ").append(System.getProperty("os.version"))));
      log(String.valueOf((new StringBuilder("Java: ")).append(System.getProperty("java.version")).append(", ").append(System.getProperty("java.vendor"))));
      log(String.valueOf((new StringBuilder("VM: ")).append(System.getProperty("java.vm.name")).append(" (").append(System.getProperty("java.vm.info")).append("), ").append(System.getProperty("java.vm.vendor"))));
      log(String.valueOf((new StringBuilder("LWJGL: ")).append(Sys.getVersion())));
      openGlVersion = GL11.glGetString(7938);
      openGlRenderer = GL11.glGetString(7937);
      openGlVendor = GL11.glGetString(7936);
      log(String.valueOf((new StringBuilder("OpenGL: ")).append(openGlRenderer).append(", version ").append(openGlVersion).append(", ").append(openGlVendor)));
      log(String.valueOf((new StringBuilder("OpenGL Version: ")).append(getOpenGlVersionString())));
      if (!GLContext.getCapabilities().OpenGL12) {
         log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
      }

      fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance;
      if (!fancyFogAvailable) {
         log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
      }

      occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query;
      if (!occlusionAvailable) {
         log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
      }

      int var0 = Minecraft.getGLMaximumTextureSize();
      dbg(String.valueOf((new StringBuilder("Maximum texture size: ")).append(var0).append("x").append(var0)));
   }

   public static boolean isSkyEnabled() {
      return gameSettings.ofSky;
   }

   public static boolean isDynamicLights() {
      return gameSettings.ofDynamicLights != 3;
   }

   public static void checkGlError(String var0) {
      int var1 = GL11.glGetError();
      if (var1 != 0) {
         String var2 = GLU.gluErrorString(var1);
         error(String.valueOf((new StringBuilder("OpenGlError: ")).append(var1).append(" (").append(var2).append("), at: ").append(var0)));
      }

   }

   public static boolean isFogFast() {
      return gameSettings.ofFogType == 1;
   }

   public static boolean isDrippingWaterLava() {
      return gameSettings.ofDrippingWaterLava;
   }

   private static String getBuild() {
      try {
         InputStream var0 = Config.class.getResourceAsStream("/buildof.txt");
         if (var0 == null) {
            return null;
         } else {
            String var1 = readLines(var0)[0];
            return var1;
         }
      } catch (Exception var2) {
         warn(String.valueOf((new StringBuilder()).append(var2.getClass().getName()).append(": ").append(var2.getMessage())));
         return null;
      }
   }

   public static TextureMap getTextureMap() {
      return getMinecraft().getTextureMapBlocks();
   }

   public static Object[] addObjectToArray(Object[] var0, Object var1) {
      if (var0 == null) {
         throw new NullPointerException("The given array is NULL");
      } else {
         int var2 = var0.length;
         int var3 = var2 + 1;
         Object[] var4 = (Object[])Array.newInstance(var0.getClass().getComponentType(), var3);
         System.arraycopy(var0, 0, var4, 0, var2);
         var4[var2] = var1;
         return var4;
      }
   }

   public static InputStream getResourceStream(IResourceManager var0, ResourceLocation var1) throws IOException {
      IResource var2 = var0.getResource(var1);
      return var2 == null ? null : var2.getInputStream();
   }

   public static boolean isVoidParticles() {
      return gameSettings.ofVoidParticles;
   }

   public static String normalize(String var0) {
      return var0 == null ? "" : var0;
   }

   public static boolean isGeneratedWater() {
      return gameSettings.ofAnimatedWater == 1;
   }

   public static float limit(float var0, float var1, float var2) {
      return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
   }

   public static Minecraft getMinecraft() {
      return minecraft;
   }

   public static float getAmbientOcclusionLevel() {
      return gameSettings.ofAoLevel;
   }

   public static boolean isTimeDayOnly() {
      return gameSettings.ofTime == 1;
   }

   public static DefaultResourcePack getDefaultResourcePack() {
      if (defaultResourcePack == null) {
         Minecraft var0 = Minecraft.getMinecraft();

         try {
            Field[] var1 = var0.getClass().getDeclaredFields();

            for(int var2 = 0; var2 < var1.length; ++var2) {
               Field var3 = var1[var2];
               if (var3.getType() == DefaultResourcePack.class) {
                  var3.setAccessible(true);
                  defaultResourcePack = (DefaultResourcePack)var3.get(var0);
                  break;
               }
            }
         } catch (Exception var4) {
            warn(String.valueOf((new StringBuilder("Error getting default resource pack: ")).append(var4.getClass().getName()).append(": ").append(var4.getMessage())));
         }

         if (defaultResourcePack == null) {
            ResourcePackRepository var5 = var0.getResourcePackRepository();
            if (var5 != null) {
               defaultResourcePack = (DefaultResourcePack)var5.rprDefaultResourcePack;
            }
         }
      }

      return defaultResourcePack;
   }

   public static boolean isPotionParticles() {
      return gameSettings.ofPotionParticles;
   }

   public static boolean equals(Object var0, Object var1) {
      return var0 == var1 ? true : (var0 == null ? false : var0.equals(var1));
   }

   public static boolean isConnectedModels() {
      return false;
   }

   public static IResource getResource(ResourceLocation var0) throws IOException {
      return minecraft.getResourceManager().getResource(var0);
   }

   public static boolean isCustomFonts() {
      return gameSettings.ofCustomFonts;
   }

   public static float parseFloat(String var0, float var1) {
      try {
         if (var0 == null) {
            return var1;
         } else {
            var0 = var0.trim();
            return Float.parseFloat(var0);
         }
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   public static boolean isBetterGrass() {
      return gameSettings.ofBetterGrass != 3;
   }

   public static boolean isConnectedTexturesFancy() {
      return gameSettings.ofConnectedTextures == 2;
   }

   public static boolean isRainFancy() {
      return gameSettings.ofRain == 0 ? gameSettings.fancyGraphics : gameSettings.ofRain == 2;
   }

   public static int getBitsJre() {
      String[] var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for(int var1 = 0; var1 < var0.length; ++var1) {
         String var2 = var0[var1];
         String var3 = System.getProperty(var2);
         if (var3 != null && var3.contains("64")) {
            return 64;
         }
      }

      return 32;
   }

   public static float limitTo1(float var0) {
      return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
   }

   private static void startVersionCheckThread() {
      VersionCheckThread var0 = new VersionCheckThread();
      var0.start();
   }

   public static int limit(int var0, int var1, int var2) {
      return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
   }

   public static DisplayMode getDesktopDisplayMode() {
      return desktopDisplayMode;
   }

   public static boolean isMultiTexture() {
      return getAnisotropicFilterLevel() > 1 ? true : getAntialiasingLevel() > 0;
   }

   private static void setThreadPriority(String var0, int var1) {
      try {
         ThreadGroup var2 = Thread.currentThread().getThreadGroup();
         if (var2 == null) {
            return;
         }

         int var3 = (var2.activeCount() + 10) * 2;
         Thread[] var4 = new Thread[var3];
         var2.enumerate(var4, false);

         for(int var5 = 0; var5 < var4.length; ++var5) {
            Thread var6 = var4[var5];
            if (var6 != null && var6.getName().startsWith(var0)) {
               var6.setPriority(var1);
            }
         }
      } catch (Throwable var7) {
         warn(String.valueOf((new StringBuilder(String.valueOf(var7.getClass().getName()))).append(": ").append(var7.getMessage())));
      }

   }

   public static IResourcePack getDefiningResourcePack(ResourceLocation var0) {
      IResourcePack[] var1 = getResourcePacks();

      for(int var2 = var1.length - 1; var2 >= 0; --var2) {
         IResourcePack var3 = var1[var2];
         if (var3.resourceExists(var0)) {
            return var3;
         }
      }

      return getDefaultResourcePack().resourceExists(var0) ? getDefaultResourcePack() : null;
   }

   public static boolean isShowCapes() {
      return gameSettings.ofShowCapes;
   }

   public static DisplayMode[] getFullscreenDisplayModes() {
      try {
         DisplayMode[] var0 = Display.getAvailableDisplayModes();
         ArrayList var1 = new ArrayList();

         for(int var2 = 0; var2 < var0.length; ++var2) {
            DisplayMode var3 = var0[var2];
            if (desktopDisplayMode == null || var3.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && var3.getFrequency() == desktopDisplayMode.getFrequency()) {
               var1.add(var3);
            }
         }

         DisplayMode[] var5 = (DisplayMode[])var1.toArray(new DisplayMode[var1.size()]);
         Comparator var6 = new Comparator() {
            public int compare(Object var1, Object var2) {
               DisplayMode var3 = (DisplayMode)var1;
               DisplayMode var4 = (DisplayMode)var2;
               return var3.getWidth() != var4.getWidth() ? var4.getWidth() - var3.getWidth() : (var3.getHeight() != var4.getHeight() ? var4.getHeight() - var3.getHeight() : 0);
            }
         };
         Arrays.sort(var5, var6);
         return var5;
      } catch (Exception var4) {
         var4.printStackTrace();
         return new DisplayMode[]{desktopDisplayMode};
      }
   }

   public static String fillRight(String var0, int var1, char var2) {
      if (var0 == null) {
         var0 = "";
      }

      if (var0.length() >= var1) {
         return var0;
      } else {
         StringBuffer var3 = new StringBuffer(var0);

         while(var3.length() < var1) {
            var3.append(var2);
         }

         return var3.toString();
      }
   }

   public static void writeFile(File var0, String var1) throws IOException {
      FileOutputStream var2 = new FileOutputStream(var0);
      byte[] var3 = var1.getBytes("ASCII");
      var2.write(var3);
      var2.close();
   }

   public static boolean isConnectedTextures() {
      return gameSettings.ofConnectedTextures != 3;
   }

   public static int getMipmapLevels() {
      return gameSettings.mipmapLevels;
   }

   public static boolean isCustomSky() {
      return gameSettings.ofCustomSky;
   }

   public static boolean isFromDefaultResourcePack(ResourceLocation var0) {
      IResourcePack var1 = getDefiningResourcePack(var0);
      return var1 == getDefaultResourcePack();
   }

   public static void updateThreadPriorities() {
      updateAvailableProcessors();
      boolean var0 = true;
      if (isSingleProcessor()) {
         if (isSmoothWorld()) {
            minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 1);
         } else {
            minecraftThread.setPriority(5);
            setThreadPriority("Server thread", 5);
         }
      } else {
         minecraftThread.setPriority(10);
         setThreadPriority("Server thread", 5);
      }

   }

   private static int getOpenGlVersion() {
      return !GLContext.getCapabilities().OpenGL11 ? 10 : (!GLContext.getCapabilities().OpenGL12 ? 11 : (!GLContext.getCapabilities().OpenGL13 ? 12 : (!GLContext.getCapabilities().OpenGL14 ? 13 : (!GLContext.getCapabilities().OpenGL15 ? 14 : (!GLContext.getCapabilities().OpenGL20 ? 15 : (!GLContext.getCapabilities().OpenGL21 ? 20 : (!GLContext.getCapabilities().OpenGL30 ? 21 : (!GLContext.getCapabilities().OpenGL31 ? 30 : (!GLContext.getCapabilities().OpenGL32 ? 31 : (!GLContext.getCapabilities().OpenGL33 ? 32 : (!GLContext.getCapabilities().OpenGL40 ? 33 : 40)))))))))));
   }

   public static int intHash(int var0) {
      var0 = var0 ^ 61 ^ var0 >> 16;
      var0 += var0 << 3;
      var0 ^= var0 >> 4;
      var0 *= 668265261;
      var0 ^= var0 >> 15;
      return var0;
   }

   public static boolean isAntialiasing() {
      return getAntialiasingLevel() > 0;
   }

   public static String arrayToString(Object[] var0) {
      if (var0 == null) {
         return "";
      } else {
         StringBuffer var1 = new StringBuffer(var0.length * 5);

         for(int var2 = 0; var2 < var0.length; ++var2) {
            Object var3 = var0[var2];
            if (var2 > 0) {
               var1.append(", ");
            }

            var1.append(String.valueOf(var3));
         }

         return var1.toString();
      }
   }

   public static boolean isAnimatedWater() {
      return gameSettings.ofAnimatedWater != 2;
   }

   public static String arrayToString(int[] var0) {
      if (var0 == null) {
         return "";
      } else {
         StringBuffer var1 = new StringBuffer(var0.length * 5);

         for(int var2 = 0; var2 < var0.length; ++var2) {
            int var3 = var0[var2];
            if (var2 > 0) {
               var1.append(", ");
            }

            var1.append(String.valueOf(var3));
         }

         return var1.toString();
      }
   }

   public static boolean isMinecraftThread() {
      return Thread.currentThread() == minecraftThread;
   }

   public static boolean isFastRender() {
      return gameSettings.ofFastRender;
   }

   public static int getAntialiasingLevel() {
      return antialiasingLevel;
   }

   public static int getMipmapType() {
      switch(gameSettings.ofMipmapType) {
      case 0:
         return 9986;
      case 1:
         return 9986;
      case 2:
         if (isMultiTexture()) {
            return 9985;
         }

         return 9986;
      case 3:
         if (isMultiTexture()) {
            return 9987;
         }

         return 9986;
      default:
         return 9986;
      }
   }

   public static boolean isFireworkParticles() {
      return gameSettings.ofFireworkParticles;
   }

   public static String getOpenGlVersionString() {
      int var0 = getOpenGlVersion();
      String var1 = String.valueOf((new StringBuilder()).append(var0 / 10).append(".").append(var0 % 10));
      return var1;
   }

   public static String readFile(File var0) throws IOException {
      FileInputStream var1 = new FileInputStream(var0);
      return readInputStream(var1, "ASCII");
   }

   public static boolean isCustomItems() {
      return gameSettings.ofCustomItems;
   }

   public static boolean isWaterParticles() {
      return gameSettings.ofWaterParticles;
   }

   public static String[] readLines(InputStream var0) throws IOException {
      ArrayList var1 = new ArrayList();
      InputStreamReader var2 = new InputStreamReader(var0, "ASCII");
      BufferedReader var3 = new BufferedReader(var2);

      while(true) {
         String var4 = var3.readLine();
         if (var4 == null) {
            String[] var5 = (String[])var1.toArray(new String[var1.size()]);
            return var5;
         }

         var1.add(var4);
      }
   }

   public static boolean isTranslucentBlocksFancy() {
      return gameSettings.ofTranslucentBlocks == 0 ? gameSettings.fancyGraphics : gameSettings.ofTranslucentBlocks == 2;
   }

   public static boolean isRainOff() {
      return gameSettings.ofRain == 3;
   }

   public static boolean isDroppedItemsFancy() {
      return gameSettings.ofDroppedItems == 0 ? gameSettings.fancyGraphics : gameSettings.ofDroppedItems == 2;
   }

   public static boolean isCloudsFancy() {
      return gameSettings.ofClouds != 0 ? gameSettings.ofClouds == 2 : (isShaders() && !Shaders.shaderPackClouds.isDefault() ? Shaders.shaderPackClouds.isFancy() : (texturePackClouds != 0 ? texturePackClouds == 2 : gameSettings.fancyGraphics));
   }

   public static boolean isDynamicFov() {
      return gameSettings.ofDynamicFov;
   }

   public static String[] getFullscreenModes() {
      DisplayMode[] var0 = getFullscreenDisplayModes();
      String[] var1 = new String[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         DisplayMode var3 = var0[var2];
         String var4 = String.valueOf((new StringBuilder()).append(var3.getWidth()).append("x").append(var3.getHeight()));
         var1[var2] = var4;
      }

      return var1;
   }

   public static boolean isStarsEnabled() {
      return gameSettings.ofStars;
   }

   public static boolean isClearWater() {
      return gameSettings.ofClearWater;
   }

   public static Object[] addObjectsToArray(Object[] var0, Object[] var1) {
      if (var0 == null) {
         throw new NullPointerException("The given array is NULL");
      } else if (var1.length == 0) {
         return var0;
      } else {
         int var2 = var0.length;
         int var3 = var2 + var1.length;
         Object[] var4 = (Object[])Array.newInstance(var0.getClass().getComponentType(), var3);
         System.arraycopy(var0, 0, var4, 0, var2);
         System.arraycopy(var1, 0, var4, var2, var1.length);
         return var4;
      }
   }

   public static int getAvailableProcessors() {
      return availableProcessors;
   }

   public static boolean isCustomColors() {
      return gameSettings.ofCustomColors;
   }

   public static int getChunkViewDistance() {
      if (gameSettings == null) {
         return 10;
      } else {
         int var0 = gameSettings.renderDistanceChunks;
         return var0;
      }
   }

   public static DynamicTexture getMojangLogoTexture(DynamicTexture var0) {
      try {
         ResourceLocation var1 = new ResourceLocation("textures/gui/title/mojang.png");
         InputStream var2 = getResourceStream(var1);
         if (var2 == null) {
            return var0;
         } else {
            BufferedImage var3 = ImageIO.read(var2);
            if (var3 == null) {
               return var0;
            } else {
               DynamicTexture var4 = new DynamicTexture(var3);
               return var4;
            }
         }
      } catch (Exception var5) {
         warn(String.valueOf((new StringBuilder(String.valueOf(var5.getClass().getName()))).append(": ").append(var5.getMessage())));
         return var0;
      }
   }

   public static boolean isFogOff() {
      return gameSettings.ofFogType == 3;
   }

   public static int compareRelease(String var0, String var1) {
      String[] var2 = splitRelease(var0);
      String[] var3 = splitRelease(var1);
      String var4 = var2[0];
      String var5 = var3[0];
      if (!var4.equals(var5)) {
         return var4.compareTo(var5);
      } else {
         int var6 = parseInt(var2[1], -1);
         int var7 = parseInt(var3[1], -1);
         if (var6 != var7) {
            return var6 - var7;
         } else {
            String var8 = var2[2];
            String var9 = var3[2];
            if (!var8.equals(var9)) {
               if (var8.isEmpty()) {
                  return 1;
               }

               if (var9.isEmpty()) {
                  return -1;
               }
            }

            return var8.compareTo(var9);
         }
      }
   }

   public static IResourcePack[] getResourcePacks() {
      ResourcePackRepository var0 = minecraft.getResourcePackRepository();
      List var1 = var0.getRepositoryEntries();
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
         var2.add(var4.getResourcePack());
      }

      if (var0.getResourcePackInstance() != null) {
         var2.add(var0.getResourcePackInstance());
      }

      IResourcePack[] var5 = (IResourcePack[])var2.toArray(new IResourcePack[var2.size()]);
      return var5;
   }

   public static boolean between(int var0, int var1, int var2) {
      return var0 >= var1 && var0 <= var2;
   }

   public static ModelManager getModelManager() {
      return modelManager;
   }

   public static WorldServer getWorldServer() {
      if (minecraft == null) {
         return null;
      } else {
         WorldClient var0 = minecraft.theWorld;
         if (var0 == null) {
            return null;
         } else if (!minecraft.isIntegratedServerRunning()) {
            return null;
         } else {
            IntegratedServer var1 = minecraft.getIntegratedServer();
            if (var1 == null) {
               return null;
            } else {
               WorldProvider var2 = var0.provider;
               if (var2 == null) {
                  return null;
               } else {
                  int var3 = var2.getDimensionId();

                  try {
                     WorldServer var4 = var1.worldServerForDimension(var3);
                     return var4;
                  } catch (NullPointerException var5) {
                     return null;
                  }
               }
            }
         }
      }
   }

   public static int getUpdatesPerFrame() {
      return gameSettings.ofChunkUpdates;
   }

   public static boolean isDynamicUpdates() {
      return gameSettings.ofChunkUpdatesDynamic;
   }

   public static RenderGlobal getRenderGlobal() {
      return minecraft == null ? null : minecraft.renderGlobal;
   }

   private static String getUpdates(String var0) {
      int var1 = var0.indexOf(40);
      if (var1 < 0) {
         return "";
      } else {
         int var2 = var0.indexOf(32, var1);
         return var2 < 0 ? "" : var0.substring(var1 + 1, var2);
      }
   }

   public static IResourceManager getResourceManager() {
      return minecraft.getResourceManager();
   }

   public static boolean hasResource(IResourceManager var0, ResourceLocation var1) {
      try {
         IResource var2 = var0.getResource(var1);
         return var2 != null;
      } catch (IOException var3) {
         return false;
      }
   }

   public static int[] addIntsToArray(int[] var0, int[] var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.length;
         int var3 = var2 + var1.length;
         int[] var4 = new int[var3];
         System.arraycopy(var0, 0, var4, 0, var2);

         for(int var5 = 0; var5 < var1.length; ++var5) {
            var4[var5 + var2] = var1[var5];
         }

         return var4;
      } else {
         throw new NullPointerException("The given array is NULL");
      }
   }

   public static boolean isTimeNightOnly() {
      return gameSettings.ofTime == 2;
   }

   public static void sleep(long var0) {
      try {
         Thread.currentThread();
         Thread.sleep(var0);
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

   }

   public static void setModelManager(ModelManager var0) {
   }

   public static boolean isTimeDefault() {
      return gameSettings.ofTime == 0;
   }

   private static String[] splitRelease(String var0) {
      if (var0 != null && var0.length() > 0) {
         Pattern var1 = Pattern.compile("([A-Z])([0-9]+)(.*)");
         Matcher var2 = var1.matcher(var0);
         if (!var2.matches()) {
            return new String[]{"", "", ""};
         } else {
            String var3 = normalize(var2.group(1));
            String var4 = normalize(var2.group(2));
            String var5 = normalize(var2.group(3));
            return new String[]{var3, var4, var5};
         }
      } else {
         return new String[]{"", "", ""};
      }
   }

   public static boolean isWeatherEnabled() {
      return gameSettings.ofWeather;
   }

   public static TextureManager getTextureManager() {
      return minecraft.getTextureManager();
   }

   public static DisplayMode getDisplayMode(Dimension var0) throws LWJGLException {
      DisplayMode[] var1 = Display.getAvailableDisplayModes();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         DisplayMode var3 = var1[var2];
         if (var3.getWidth() == var0.width && var3.getHeight() == var0.height && (desktopDisplayMode == null || var3.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && var3.getFrequency() == desktopDisplayMode.getFrequency())) {
            return var3;
         }
      }

      return desktopDisplayMode;
   }

   public static boolean isAnimatedFire() {
      return gameSettings.ofAnimatedFire;
   }

   public static void updateFramebufferSize() {
      minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
      if (minecraft.entityRenderer != null) {
         minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
      }

   }

   public static void dbg(String var0) {
      systemOut.print("[OptiFine] ");
      systemOut.println(var0);
   }

   public static boolean isMipmaps() {
      return gameSettings.mipmapLevels > 0;
   }

   public static boolean isAnisotropicFiltering() {
      return getAnisotropicFilterLevel() > 1;
   }

   public static void drawFps() {
      Minecraft var0 = minecraft;
      int var1 = Minecraft.func_175610_ah();
      String var2 = getUpdates(minecraft.debug);
      int var3 = minecraft.renderGlobal.getCountActiveRenderers();
      int var4 = minecraft.renderGlobal.getCountEntitiesRendered();
      int var5 = minecraft.renderGlobal.getCountTileEntitiesRendered();
      String var6 = String.valueOf((new StringBuilder()).append(var1).append(" fps, C: ").append(var3).append(", E: ").append(var4).append("+").append(var5).append(", U: ").append(var2));
      minecraft.fontRendererObj.drawString(var6, 2.0D, 2.0D, -2039584);
   }

   public static Object[] addObjectToArray(Object[] var0, Object var1, int var2) {
      ArrayList var3 = new ArrayList(Arrays.asList(var0));
      var3.add(var2, var1);
      Object[] var4 = (Object[])Array.newInstance(var0.getClass().getComponentType(), var3.size());
      return var3.toArray(var4);
   }

   public static boolean isLazyChunkLoading() {
      return !isSingleProcessor() ? false : gameSettings.ofLazyChunkLoading;
   }

   public static boolean isNotify64BitJava() {
      return notify64BitJava;
   }

   public static Dimension getFullscreenDimension() {
      if (desktopDisplayMode == null) {
         return null;
      } else if (gameSettings == null) {
         return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
      } else {
         String var0 = gameSettings.ofFullscreenMode;
         if (var0.equals("Default")) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
         } else {
            String[] var1 = tokenize(var0, " x");
            return var1.length < 2 ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(var1[0], -1), parseInt(var1[1], -1));
         }
      }
   }

   public static boolean hasResource(ResourceLocation var0) {
      try {
         IResource var1 = getResource(var0);
         return var1 != null;
      } catch (IOException var2) {
         return false;
      }
   }

   public static boolean isAnimatedTextures() {
      return gameSettings.ofAnimatedTextures;
   }

   public static double limit(double var0, double var2, double var4) {
      return var0 < var2 ? var2 : (var0 > var4 ? var4 : var0);
   }

   public static void showGuiMessage(String var0, String var1) {
      GuiMessage var2 = new GuiMessage(minecraft.currentScreen, var0, var1);
      minecraft.displayGuiScreen(var2);
   }

   public static boolean isRandomMobs() {
      return gameSettings.ofRandomMobs;
   }

   public static boolean isAnimatedTerrain() {
      return gameSettings.ofAnimatedTerrain;
   }

   public static int getAnisotropicFilterLevel() {
      return gameSettings.ofAfLevel;
   }

   public static boolean isUseAlphaFunc() {
      float var0 = getAlphaFuncLevel();
      return var0 > DEF_ALPHA_FUNC_LEVEL + 1.0E-5F;
   }

   public static boolean isGeneratedLava() {
      return gameSettings.ofAnimatedLava == 1;
   }

   public static float getAlphaFuncLevel() {
      return DEF_ALPHA_FUNC_LEVEL;
   }

   public static void setNewRelease(String var0) {
   }

   public static void checkDisplaySettings() {
      int var0 = getAntialiasingLevel();
      if (var0 > 0) {
         DisplayMode var1 = Display.getDisplayMode();
         dbg(String.valueOf((new StringBuilder("FSAA Samples: ")).append(var0)));

         try {
            Display.destroy();
            Display.setDisplayMode(var1);
            Display.create((new PixelFormat()).withDepthBits(24).withSamples(var0));
            Display.setResizable(false);
            Display.setResizable(true);
         } catch (LWJGLException var15) {
            warn(String.valueOf((new StringBuilder("Error setting FSAA: ")).append(var0).append("x")));
            var15.printStackTrace();

            try {
               Display.setDisplayMode(var1);
               Display.create((new PixelFormat()).withDepthBits(24));
               Display.setResizable(false);
               Display.setResizable(true);
            } catch (LWJGLException var14) {
               var14.printStackTrace();

               try {
                  Display.setDisplayMode(var1);
                  Display.create();
                  Display.setResizable(false);
                  Display.setResizable(true);
               } catch (LWJGLException var13) {
                  var13.printStackTrace();
               }
            }
         }

         if (!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
            InputStream var2 = null;
            InputStream var3 = null;

            try {
               var2 = getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
               var3 = getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
               if (var2 != null && var3 != null) {
                  Display.setIcon(new ByteBuffer[]{readIconImage(var2), readIconImage(var3)});
               }
            } catch (IOException var11) {
               warn(String.valueOf((new StringBuilder("Error setting window icon: ")).append(var11.getClass().getName()).append(": ").append(var11.getMessage())));
            } finally {
               IOUtils.closeQuietly(var2);
               IOUtils.closeQuietly(var3);
            }
         }
      }

   }

   public static boolean isShaders() {
      return Shaders.shaderPackLoaded;
   }

   public static boolean isSmoothBiomes() {
      return gameSettings.ofSmoothBiomes;
   }

   public static void updateAvailableProcessors() {
      availableProcessors = Runtime.getRuntime().availableProcessors();
   }

   public static void warn(String var0) {
      systemOut.print("[OptiFine] [WARN] ");
      systemOut.println(var0);
   }

   public static String[] tokenize(String var0, String var1) {
      StringTokenizer var2 = new StringTokenizer(var0, var1);
      ArrayList var3 = new ArrayList();

      while(var2.hasMoreTokens()) {
         String var4 = var2.nextToken();
         var3.add(var4);
      }

      String[] var5 = (String[])var3.toArray(new String[var3.size()]);
      return var5;
   }

   public static boolean isTreesFancy() {
      return gameSettings.ofTrees == 0 ? gameSettings.fancyGraphics : gameSettings.ofTrees != 1;
   }

   public static byte[] readAll(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[1024];

      while(true) {
         int var3 = var0.read(var2);
         if (var3 < 0) {
            var0.close();
            byte[] var4 = var1.toByteArray();
            return var4;
         }

         var1.write(var2, 0, var3);
      }
   }

   public static boolean isAnimatedRedstone() {
      return gameSettings.ofAnimatedRedstone;
   }

   public static void log(String var0) {
      dbg(var0);
   }

   public static boolean isAnimatedSmoke() {
      return gameSettings.ofAnimatedSmoke;
   }

   public static boolean isBetterGrassFancy() {
      return gameSettings.ofBetterGrass == 2;
   }

   public static void initDisplay() {
      checkInitialized();
      antialiasingLevel = gameSettings.ofAaLevel;
      checkDisplaySettings();
      checkDisplayMode();
      minecraftThread = Thread.currentThread();
      updateThreadPriorities();
      Shaders.startup(Minecraft.getMinecraft());
   }

   public static InputStream getResourceStream(ResourceLocation var0) throws IOException {
      return getResourceStream(minecraft.getResourceManager(), var0);
   }

   public static String readInputStream(InputStream var0, String var1) throws IOException {
      InputStreamReader var2 = new InputStreamReader(var0, var1);
      BufferedReader var3 = new BufferedReader(var2);
      StringBuffer var4 = new StringBuffer();

      while(true) {
         String var5 = var3.readLine();
         if (var5 == null) {
            return var4.toString();
         }

         var4.append(var5);
         var4.append("\n");
      }
   }

   private static Method getMethod(Class var0, String var1, Object[] var2) {
      Method[] var3 = var0.getMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         Method var5 = var3[var4];
         if (var5.getName().equals(var1) && var5.getParameterTypes().length == var2.length) {
            return var5;
         }
      }

      warn(String.valueOf((new StringBuilder("No method found for: ")).append(var0.getName()).append(".").append(var1).append("(").append(arrayToString(var2)).append(")")));
      return null;
   }

   public static void setNotify64BitJava(boolean var0) {
      notify64BitJava = var0;
   }

   public static void initGameSettings(GameSettings var0) {
      if (gameSettings == null) {
         gameSettings = var0;
         minecraft = Minecraft.getMinecraft();
         desktopDisplayMode = Display.getDesktopDisplayMode();
         updateAvailableProcessors();
         ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
      }

   }

   public static boolean isAnimatedExplosion() {
      return gameSettings.ofAnimatedExplosion;
   }

   public static boolean isDynamicLightsFast() {
      return gameSettings.ofDynamicLights == 1;
   }

   public static boolean isAnimatedLava() {
      return gameSettings.ofAnimatedLava != 2;
   }

   public static boolean isAnimatedPortal() {
      return gameSettings.ofAnimatedPortal;
   }

   public static boolean parseBoolean(String var0, boolean var1) {
      try {
         if (var0 == null) {
            return var1;
         } else {
            var0 = var0.trim();
            return Boolean.parseBoolean(var0);
         }
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   public static GameSettings getGameSettings() {
      return gameSettings;
   }

   public static boolean isFancyFogAvailable() {
      return fancyFogAvailable;
   }

   public static boolean isAnimatedFlame() {
      return gameSettings.ofAnimatedFlame;
   }

   public static boolean isVignetteEnabled() {
      return gameSettings.ofVignette == 0 ? gameSettings.fancyGraphics : gameSettings.ofVignette == 2;
   }

   public static boolean isSwampColors() {
      return gameSettings.ofSwampColors;
   }

   public static boolean isCloudsOff() {
      return gameSettings.ofClouds != 0 ? gameSettings.ofClouds == 3 : (isShaders() && !Shaders.shaderPackClouds.isDefault() ? Shaders.shaderPackClouds.isOff() : (texturePackClouds != 0 ? texturePackClouds == 3 : false));
   }

   public static int parseInt(String var0, int var1) {
      try {
         if (var0 == null) {
            return var1;
         } else {
            var0 = var0.trim();
            return Integer.parseInt(var0);
         }
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   public static boolean isBetterSnow() {
      return gameSettings.ofBetterSnow;
   }

   public static boolean isDynamicHandLight() {
      return !isDynamicLights() ? false : (isShaders() ? Shaders.isDynamicHandLight() : true);
   }

   public static boolean isPortalParticles() {
      return gameSettings.ofPortalParticles;
   }

   public static boolean isCullFacesLeaves() {
      return gameSettings.ofTrees == 0 ? !gameSettings.fancyGraphics : gameSettings.ofTrees == 4;
   }

   public static String getNewRelease() {
      return newRelease;
   }

   public static String getVersion() {
      return "OptiFine_1.8_HD_U_H6";
   }

   public static void checkDisplayMode() {
      try {
         if (minecraft.isFullScreen()) {
            if (fullscreenModeChecked) {
               return;
            }

            fullscreenModeChecked = true;
            desktopModeChecked = false;
            DisplayMode var0 = Display.getDisplayMode();
            Dimension var1 = getFullscreenDimension();
            if (var1 == null) {
               return;
            }

            if (var0.getWidth() == var1.width && var0.getHeight() == var1.height) {
               return;
            }

            DisplayMode var2 = getDisplayMode(var1);
            if (var2 == null) {
               return;
            }

            Display.setDisplayMode(var2);
            minecraft.displayWidth = Display.getDisplayMode().getWidth();
            minecraft.displayHeight = Display.getDisplayMode().getHeight();
            if (minecraft.displayWidth <= 0) {
               minecraft.displayWidth = 1;
            }

            if (minecraft.displayHeight <= 0) {
               minecraft.displayHeight = 1;
            }

            if (minecraft.currentScreen != null) {
               ScaledResolution var3 = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
               int var4 = var3.getScaledWidth();
               int var5 = var3.getScaledHeight();
               minecraft.currentScreen.setWorldAndResolution(minecraft, var4, var5);
            }

            minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
            updateFramebufferSize();
            Display.setFullscreen(true);
            minecraft.gameSettings.updateVSync();
            GlStateManager.func_179098_w();
         } else {
            if (desktopModeChecked) {
               return;
            }

            desktopModeChecked = true;
            fullscreenModeChecked = false;
            minecraft.gameSettings.updateVSync();
            Display.update();
            GlStateManager.func_179098_w();
            Display.setResizable(false);
            Display.setResizable(true);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   static {
      systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
      DEF_ALPHA_FUNC_LEVEL = 0.1F;
   }

   public static boolean isAntialiasingConfigured() {
      return getGameSettings().ofAaLevel > 0;
   }

   public static String getVersionDebug() {
      StringBuffer var0 = new StringBuffer(32);
      if (isDynamicLights()) {
         var0.append("DL: ");
         var0.append(String.valueOf(DynamicLights.getCount()));
         var0.append(", ");
      }

      var0.append("OptiFine_1.8_HD_U_H6");
      String var1 = Shaders.getShaderPackName();
      if (var1 != null) {
         var0.append(", ");
         var0.append(var1);
      }

      return var0.toString();
   }

   public static boolean isSunMoonEnabled() {
      return gameSettings.ofSunMoon;
   }

   public static boolean isNaturalTextures() {
      return gameSettings.ofNaturalTextures;
   }

   public static boolean isRainSplash() {
      return gameSettings.ofRainSplash;
   }

   public static void checkInitialized() {
      if (!initialized && Display.isCreated()) {
         initialized = true;
         checkOpenGlCaps();
         startVersionCheckThread();
      }

   }

   public static boolean isSmoothWorld() {
      return gameSettings.ofSmoothWorld;
   }

   public static String[] readLines(File var0) throws IOException {
      FileInputStream var1 = new FileInputStream(var0);
      return readLines((InputStream)var1);
   }

   public static void error(String var0) {
      systemOut.print("[OptiFine] [ERROR] ");
      systemOut.println(var0);
   }
}
