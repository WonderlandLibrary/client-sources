package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.lang.reflect.*;
import java.awt.*;
import java.util.*;
import org.lwjgl.*;
import org.lwjgl.util.glu.*;
import java.io.*;

public class Config
{
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.5.2";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "D2";
    public static final String VERSION = "OptiFine_1.5.2_HD_U_D2";
    private static String newRelease;
    private static GameSettings gameSettings;
    private static Minecraft minecraft;
    private static Thread minecraftThread;
    private static DisplayMode desktopDisplayMode;
    private static int antialiasingLevel;
    private static int availableProcessors;
    public static boolean zoomMode;
    private static int texturePackClouds;
    private static PrintStream systemOut;
    public static final Boolean DEF_FOG_FANCY;
    public static final Float DEF_FOG_START;
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE;
    public static final Boolean DEF_OCCLUSION_ENABLED;
    public static final Integer DEF_MIPMAP_LEVEL;
    public static final Integer DEF_MIPMAP_TYPE;
    public static final Float DEF_ALPHA_FUNC_LEVEL;
    public static final Boolean DEF_LOAD_CHUNKS_FAR;
    public static final Integer DEF_PRELOADED_CHUNKS;
    public static final Integer DEF_CHUNKS_LIMIT;
    public static final Integer DEF_UPDATES_PER_FRAME;
    public static final Boolean DEF_DYNAMIC_UPDATES;
    
    static {
        Config.newRelease = null;
        Config.gameSettings = null;
        Config.minecraft = null;
        Config.minecraftThread = null;
        Config.desktopDisplayMode = null;
        Config.antialiasingLevel = 0;
        Config.availableProcessors = 0;
        Config.zoomMode = false;
        Config.texturePackClouds = 0;
        Config.systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
        DEF_FOG_FANCY = true;
        DEF_FOG_START = 0.2f;
        DEF_OPTIMIZE_RENDER_DISTANCE = false;
        DEF_OCCLUSION_ENABLED = false;
        DEF_MIPMAP_LEVEL = 0;
        DEF_MIPMAP_TYPE = 9984;
        DEF_ALPHA_FUNC_LEVEL = 0.1f;
        DEF_LOAD_CHUNKS_FAR = false;
        DEF_PRELOADED_CHUNKS = 0;
        DEF_CHUNKS_LIMIT = 25;
        DEF_UPDATES_PER_FRAME = 3;
        DEF_DYNAMIC_UPDATES = false;
    }
    
    public static String getVersion() {
        return "OptiFine_1.5.2_HD_U_D2";
    }
    
    private static void checkOpenGlCaps() {
        log("");
        log(getVersion());
        log(new StringBuilder().append(new Date()).toString());
        log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        log("LWJGL: " + Sys.getVersion());
        log("OpenGL: " + GL11.glGetString(7937) + " version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936));
        final int var0 = getOpenGlVersion();
        final String var2 = var0 / 10 + "." + var0 % 10;
        log("OpenGL Version: " + var2);
        if (!GLContext.getCapabilities().OpenGL12) {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!GLContext.getCapabilities().GL_NV_fog_distance) {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!GLContext.getCapabilities().GL_ARB_occlusion_query) {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        final int var3 = Minecraft.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + var3 + "x" + var3);
    }
    
    public static boolean isFancyFogAvailable() {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }
    
    public static boolean isOcclusionAvailable() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
    
    private static int getOpenGlVersion() {
        return GLContext.getCapabilities().OpenGL11 ? (GLContext.getCapabilities().OpenGL12 ? (GLContext.getCapabilities().OpenGL13 ? (GLContext.getCapabilities().OpenGL14 ? (GLContext.getCapabilities().OpenGL15 ? (GLContext.getCapabilities().OpenGL20 ? (GLContext.getCapabilities().OpenGL21 ? (GLContext.getCapabilities().OpenGL30 ? (GLContext.getCapabilities().OpenGL31 ? (GLContext.getCapabilities().OpenGL32 ? (GLContext.getCapabilities().OpenGL33 ? (GLContext.getCapabilities().OpenGL40 ? 40 : 33) : 32) : 31) : 30) : 21) : 20) : 15) : 14) : 13) : 12) : 11) : 10;
    }
    
    public static void setGameSettings(final GameSettings var0) {
        if (Config.gameSettings == null) {
            if (!Display.isCreated()) {
                return;
            }
            checkOpenGlCaps();
            startVersionCheckThread();
        }
        Config.gameSettings = var0;
        Config.minecraft = Config.gameSettings.mc;
        Config.minecraftThread = Thread.currentThread();
        if (Config.gameSettings != null) {
            Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
        }
        updateThreadPriorities();
    }
    
    public static void updateThreadPriorities() {
        try {
            final ThreadGroup var0 = Thread.currentThread().getThreadGroup();
            if (var0 == null) {
                return;
            }
            final int var2 = (var0.activeCount() + 10) * 2;
            final Thread[] var3 = new Thread[var2];
            var0.enumerate(var3, false);
            final byte var4 = 5;
            byte var5 = 5;
            if (isSmoothWorld()) {
                var5 = 3;
            }
            Config.minecraftThread.setPriority(var4);
            for (int var6 = 0; var6 < var3.length; ++var6) {
                final Thread var7 = var3[var6];
                if (var7 != null && var7 instanceof ThreadMinecraftServer) {
                    var7.setPriority(var5);
                }
            }
        }
        catch (Throwable var8) {
            dbg(var8.getMessage());
        }
    }
    
    public static boolean isMinecraftThread() {
        return Thread.currentThread() == Config.minecraftThread;
    }
    
    private static void startVersionCheckThread() {
        final VersionCheckThread var0 = new VersionCheckThread();
        var0.start();
    }
    
    public static boolean isUseMipmaps() {
        final int var0 = getMipmapLevel();
        return var0 > 0;
    }
    
    public static int getMipmapLevel() {
        return (Config.gameSettings == null) ? Config.DEF_MIPMAP_LEVEL : Config.gameSettings.ofMipmapLevel;
    }
    
    public static int getMipmapType() {
        if (Config.gameSettings == null) {
            return Config.DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9984;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (isMultiTexture()) {
                    return 9985;
                }
                return 9986;
            }
            case 3: {
                if (isMultiTexture()) {
                    return 9987;
                }
                return 9986;
            }
            default: {
                return 9984;
            }
        }
    }
    
    public static boolean isUseAlphaFunc() {
        final float var0 = getAlphaFuncLevel();
        return var0 > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f;
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static boolean isFogFancy() {
        return isFancyFogAvailable() && Config.gameSettings != null && Config.gameSettings.ofFogType == 2;
    }
    
    public static boolean isFogFast() {
        return Config.gameSettings != null && Config.gameSettings.ofFogType == 1;
    }
    
    public static boolean isFogOff() {
        return Config.gameSettings != null && Config.gameSettings.ofFogType == 3;
    }
    
    public static float getFogStart() {
        return (Config.gameSettings == null) ? Config.DEF_FOG_START : Config.gameSettings.ofFogStart;
    }
    
    public static boolean isOcclusionEnabled() {
        return (Config.gameSettings == null) ? Config.DEF_OCCLUSION_ENABLED : Config.gameSettings.advancedOpengl;
    }
    
    public static boolean isOcclusionFancy() {
        return isOcclusionEnabled() && Config.gameSettings != null && Config.gameSettings.ofOcclusionFancy;
    }
    
    public static boolean isLoadChunksFar() {
        return (Config.gameSettings == null) ? Config.DEF_LOAD_CHUNKS_FAR : Config.gameSettings.ofLoadFar;
    }
    
    public static int getPreloadedChunks() {
        return (Config.gameSettings == null) ? Config.DEF_PRELOADED_CHUNKS : Config.gameSettings.ofPreloadedChunks;
    }
    
    public static void dbg(final String var0) {
        Config.systemOut.print("[OptiFine] ");
        Config.systemOut.println(var0);
    }
    
    public static void log(final String var0) {
        dbg(var0);
    }
    
    public static int getUpdatesPerFrame() {
        return (Config.gameSettings != null) ? Config.gameSettings.ofChunkUpdates : 1;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings == null || Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static boolean isRainFancy() {
        return (Config.gameSettings.ofRain == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofRain == 2);
    }
    
    public static boolean isWaterFancy() {
        return (Config.gameSettings.ofWater == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofWater == 2);
    }
    
    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }
    
    public static boolean isCloudsFancy() {
        return (Config.gameSettings.ofClouds != 0) ? (Config.gameSettings.ofClouds == 2) : ((Config.texturePackClouds != 0) ? (Config.texturePackClouds == 2) : Config.gameSettings.fancyGraphics);
    }
    
    public static boolean isCloudsOff() {
        return Config.gameSettings.ofClouds == 3;
    }
    
    public static void updateTexturePackClouds() {
        Config.texturePackClouds = 0;
        final RenderEngine var0 = getRenderEngine();
        if (var0 != null) {
            final ITexturePack var2 = var0.getTexturePack().getSelectedTexturePack();
            if (var2 != null) {
                try {
                    final InputStream var3 = var2.getResourceAsStream("/color.properties");
                    if (var3 == null) {
                        return;
                    }
                    final Properties var4 = new Properties();
                    var4.load(var3);
                    var3.close();
                    String var5 = var4.getProperty("clouds");
                    if (var5 == null) {
                        return;
                    }
                    dbg("Texture pack clouds: " + var5);
                    var5 = var5.toLowerCase();
                    if (var5.equals("fast")) {
                        Config.texturePackClouds = 1;
                    }
                    if (var5.equals("fancy")) {
                        Config.texturePackClouds = 2;
                    }
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static boolean isTreesFancy() {
        return (Config.gameSettings.ofTrees == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTrees == 2);
    }
    
    public static boolean isGrassFancy() {
        return (Config.gameSettings.ofGrass == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofGrass == 2);
    }
    
    public static boolean isDroppedItemsFancy() {
        return (Config.gameSettings.ofDroppedItems == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofDroppedItems == 2);
    }
    
    public static int limit(final int var0, final int var1, final int var2) {
        return (var0 < var1) ? var1 : ((var0 > var2) ? var2 : var0);
    }
    
    public static float limit(final float var0, final float var1, final float var2) {
        return (var0 < var1) ? var1 : ((var0 > var2) ? var2 : var0);
    }
    
    public static float limitTo1(final float var0) {
        return (var0 < 0.0f) ? 0.0f : ((var0 > 1.0f) ? 1.0f : var0);
    }
    
    public static boolean isAnimatedWater() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedWater != 2;
    }
    
    public static boolean isGeneratedWater() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedWater == 1;
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedPortal;
    }
    
    public static boolean isAnimatedLava() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedLava != 2;
    }
    
    public static boolean isGeneratedLava() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedLava == 1;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedFire;
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedFlame;
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings == null || Config.gameSettings.ofVoidParticles;
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings == null || Config.gameSettings.ofWaterParticles;
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings == null || Config.gameSettings.ofRainSplash;
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings == null || Config.gameSettings.ofPortalParticles;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings == null || Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isDepthFog() {
        return Config.gameSettings == null || Config.gameSettings.ofDepthFog;
    }
    
    public static float getAmbientOcclusionLevel() {
        return (Config.gameSettings != null) ? Config.gameSettings.ofAoLevel : 0.0f;
    }
    
    private static Method getMethod(final Class var0, final String var1, final Object[] var2) {
        final Method[] var3 = var0.getMethods();
        for (int var4 = 0; var4 < var3.length; ++var4) {
            final Method var5 = var3[var4];
            if (var5.getName().equals(var1) && var5.getParameterTypes().length == var2.length) {
                return var5;
            }
        }
        dbg("No method found for: " + var0.getName() + "." + var1 + "(" + arrayToString(var2) + ")");
        return null;
    }
    
    public static String arrayToString(final Object[] var0) {
        if (var0 == null) {
            return "";
        }
        final StringBuffer var = new StringBuffer(var0.length * 5);
        for (int var2 = 0; var2 < var0.length; ++var2) {
            final Object var3 = var0[var2];
            if (var2 > 0) {
                var.append(", ");
            }
            var.append(String.valueOf(var3));
        }
        return var.toString();
    }
    
    public static String arrayToString(final int[] var0) {
        if (var0 == null) {
            return "";
        }
        final StringBuffer var = new StringBuffer(var0.length * 5);
        for (int var2 = 0; var2 < var0.length; ++var2) {
            final int var3 = var0[var2];
            if (var2 > 0) {
                var.append(", ");
            }
            var.append(String.valueOf(var3));
        }
        return var.toString();
    }
    
    public static Minecraft getMinecraft() {
        return Config.minecraft;
    }
    
    public static RenderEngine getRenderEngine() {
        return Config.minecraft.renderEngine;
    }
    
    public static RenderGlobal getRenderGlobal() {
        return (Config.minecraft == null) ? null : Config.minecraft.renderGlobal;
    }
    
    public static int getMaxDynamicTileWidth() {
        return 64;
    }
    
    public static Icon getSideGrassTexture(final IBlockAccess var0, int var1, int var2, int var3, final int var4, final Icon var5) {
        if (!isBetterGrass()) {
            return var5;
        }
        Icon var6 = TextureUtils.iconGrassTop;
        byte var7 = 2;
        if (var5 == TextureUtils.iconMycelSide) {
            var6 = TextureUtils.iconMycelTop;
            var7 = 110;
        }
        if (isBetterGrassFancy()) {
            --var2;
            switch (var4) {
                case 2: {
                    --var3;
                    break;
                }
                case 3: {
                    ++var3;
                    break;
                }
                case 4: {
                    --var1;
                    break;
                }
                case 5: {
                    ++var1;
                    break;
                }
            }
            final int var8 = var0.getBlockId(var1, var2, var3);
            if (var8 != var7) {
                return var5;
            }
        }
        return var6;
    }
    
    public static Icon getSideSnowGrassTexture(final IBlockAccess var0, int var1, final int var2, int var3, final int var4) {
        if (!isBetterGrass()) {
            return TextureUtils.iconSnowSide;
        }
        if (isBetterGrassFancy()) {
            switch (var4) {
                case 2: {
                    --var3;
                    break;
                }
                case 3: {
                    ++var3;
                    break;
                }
                case 4: {
                    --var1;
                    break;
                }
                case 5: {
                    ++var1;
                    break;
                }
            }
            final int var5 = var0.getBlockId(var1, var2, var3);
            if (var5 != 78 && var5 != 80) {
                return TextureUtils.iconSnowSide;
            }
        }
        return TextureUtils.iconSnow;
    }
    
    public static boolean isBetterGrass() {
        return Config.gameSettings != null && Config.gameSettings.ofBetterGrass != 3;
    }
    
    public static boolean isBetterGrassFancy() {
        return Config.gameSettings != null && Config.gameSettings.ofBetterGrass == 2;
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings == null || Config.gameSettings.ofWeather;
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings == null || Config.gameSettings.ofSky;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings == null || Config.gameSettings.ofSunMoon;
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings == null || Config.gameSettings.ofStars;
    }
    
    public static void sleep(final long var0) {
        try {
            Thread.currentThread();
            Thread.sleep(var0);
        }
        catch (InterruptedException var) {
            var.printStackTrace();
        }
    }
    
    public static boolean isTimeDayOnly() {
        return Config.gameSettings != null && Config.gameSettings.ofTime == 1;
    }
    
    public static boolean isTimeDefault() {
        return Config.gameSettings != null && (Config.gameSettings.ofTime == 0 || Config.gameSettings.ofTime == 2);
    }
    
    public static boolean isTimeNightOnly() {
        return Config.gameSettings != null && Config.gameSettings.ofTime == 3;
    }
    
    public static boolean isClearWater() {
        return Config.gameSettings != null && Config.gameSettings.ofClearWater;
    }
    
    public static int getAnisotropicFilterLevel() {
        return (Config.gameSettings == null) ? 1 : Config.gameSettings.ofAfLevel;
    }
    
    public static int getAntialiasingLevel() {
        return Config.antialiasingLevel;
    }
    
    public static boolean between(final int var0, final int var1, final int var2) {
        return var0 >= var1 && var0 <= var2;
    }
    
    public static boolean isMultiTexture() {
        return getAnisotropicFilterLevel() > 1 || getAntialiasingLevel() > 0;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings != null && Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings != null && Config.gameSettings.ofBetterSnow;
    }
    
    public static Dimension getFullscreenDimension() {
        if (Config.desktopDisplayMode == null) {
            return null;
        }
        if (Config.gameSettings == null) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String var0 = Config.gameSettings.ofFullscreenMode;
        if (var0.equals("Default")) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String[] var2 = tokenize(var0, " x");
        return (var2.length < 2) ? new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight()) : new Dimension(parseInt(var2[0], -1), parseInt(var2[1], -1));
    }
    
    public static int parseInt(final String var0, final int var1) {
        try {
            return (var0 == null) ? var1 : Integer.parseInt(var0);
        }
        catch (NumberFormatException var2) {
            return var1;
        }
    }
    
    public static float parseFloat(final String var0, final float var1) {
        try {
            return (var0 == null) ? var1 : Float.parseFloat(var0);
        }
        catch (NumberFormatException var2) {
            return var1;
        }
    }
    
    public static String[] tokenize(final String var0, final String var1) {
        final StringTokenizer var2 = new StringTokenizer(var0, var1);
        final ArrayList var3 = new ArrayList();
        while (var2.hasMoreTokens()) {
            final String var4 = var2.nextToken();
            var3.add(var4);
        }
        final String[] var5 = var3.toArray(new String[var3.size()]);
        return var5;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Config.desktopDisplayMode;
    }
    
    public static void setDesktopDisplayMode(final DisplayMode var0) {
        Config.desktopDisplayMode = var0;
    }
    
    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            final DisplayMode[] var0 = Display.getAvailableDisplayModes();
            final ArrayList var2 = new ArrayList();
            for (int var3 = 0; var3 < var0.length; ++var3) {
                final DisplayMode var4 = var0[var3];
                if (Config.desktopDisplayMode == null || (var4.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && var4.getFrequency() == Config.desktopDisplayMode.getFrequency())) {
                    var2.add(var4);
                }
            }
            final DisplayMode[] var5 = var2.toArray(new DisplayMode[var2.size()]);
            final Config$1 var6 = new Config$1();
            Arrays.sort(var5, var6);
            return var5;
        }
        catch (Exception var7) {
            var7.printStackTrace();
            return new DisplayMode[] { Config.desktopDisplayMode };
        }
    }
    
    public static String[] getFullscreenModes() {
        final DisplayMode[] var0 = getFullscreenDisplayModes();
        final String[] var2 = new String[var0.length];
        for (int var3 = 0; var3 < var0.length; ++var3) {
            final DisplayMode var4 = var0[var3];
            final String var5 = var4.getWidth() + "x" + var4.getHeight();
            var2[var3] = var5;
        }
        return var2;
    }
    
    public static DisplayMode getDisplayMode(final Dimension var0) throws LWJGLException {
        final DisplayMode[] var = Display.getAvailableDisplayModes();
        for (int var2 = 0; var2 < var.length; ++var2) {
            final DisplayMode var3 = var[var2];
            if (var3.getWidth() == var0.width && var3.getHeight() == var0.height && (Config.desktopDisplayMode == null || (var3.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && var3.getFrequency() == Config.desktopDisplayMode.getFrequency()))) {
                return var3;
            }
        }
        return Config.desktopDisplayMode;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isAnimatedItems() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedItems;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings == null || Config.gameSettings.ofAnimatedTextures;
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings == null || Config.gameSettings.ofSwampColors;
    }
    
    public static boolean isRandomMobs() {
        return Config.gameSettings == null || Config.gameSettings.ofRandomMobs;
    }
    
    public static void checkGlError(final String var0) {
        final int var = GL11.glGetError();
        if (var != 0) {
            final String var2 = GLU.gluErrorString(var);
            dbg("OpenGlError: " + var + " (" + var2 + "), at: " + var0);
        }
    }
    
    public static boolean isSmoothBiomes() {
        return Config.gameSettings == null || Config.gameSettings.ofSmoothBiomes;
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings == null || Config.gameSettings.ofCustomColors;
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings == null || Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings == null || Config.gameSettings.ofCustomFonts;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings == null || Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isConnectedTextures() {
        return Config.gameSettings != null && Config.gameSettings.ofConnectedTextures != 3;
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings != null && Config.gameSettings.ofNaturalTextures;
    }
    
    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings != null && Config.gameSettings.ofConnectedTextures == 2;
    }
    
    public static String[] readLines(final File var0) throws IOException {
        final ArrayList var = new ArrayList();
        final FileInputStream var2 = new FileInputStream(var0);
        final InputStreamReader var3 = new InputStreamReader(var2, "ASCII");
        final BufferedReader var4 = new BufferedReader(var3);
        while (true) {
            final String var5 = var4.readLine();
            if (var5 == null) {
                break;
            }
            var.add(var5);
        }
        final String[] var6 = var.toArray(new String[var.size()]);
        var4.close();
        return var6;
    }
    
    public static String readFile(final File var0) throws IOException {
        final FileInputStream var = new FileInputStream(var0);
        return readInputStream(var, "ASCII");
    }
    
    public static String readInputStream(final InputStream var0) throws IOException {
        return readInputStream(var0, "ASCII");
    }
    
    public static String readInputStream(final InputStream var0, final String var1) throws IOException {
        final InputStreamReader var2 = new InputStreamReader(var0, var1);
        final BufferedReader var3 = new BufferedReader(var2);
        final StringBuffer var4 = new StringBuffer();
        while (true) {
            final String var5 = var3.readLine();
            if (var5 == null) {
                break;
            }
            var4.append(var5);
            var4.append("\n");
        }
        return var4.toString();
    }
    
    public static GameSettings getGameSettings() {
        return Config.gameSettings;
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void setNewRelease(final String var0) {
        Config.newRelease = var0;
    }
    
    public static int compareRelease(final String var0, final String var1) {
        final String[] var2 = splitRelease(var0);
        final String[] var3 = splitRelease(var1);
        final String var4 = var2[0];
        final String var5 = var3[0];
        if (!var4.equals(var5)) {
            return var4.compareTo(var5);
        }
        final int var6 = parseInt(var2[1], -1);
        final int var7 = parseInt(var3[1], -1);
        if (var6 != var7) {
            return var6 - var7;
        }
        final String var8 = var2[2];
        final String var9 = var3[2];
        return var8.compareTo(var9);
    }
    
    private static String[] splitRelease(final String var0) {
        if (var0 == null || var0.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final String var = var0.substring(0, 1);
        if (var0.length() <= 1) {
            return new String[] { var, "", "" };
        }
        int var2;
        for (var2 = 1; var2 < var0.length() && Character.isDigit(var0.charAt(var2)); ++var2) {}
        final String var3 = var0.substring(1, var2);
        if (var2 >= var0.length()) {
            return new String[] { var, var3, "" };
        }
        final String var4 = var0.substring(var2);
        return new String[] { var, var3, var4 };
    }
    
    public static int intHash(int var0) {
        var0 = (var0 ^ 0x3D ^ var0 >> 16);
        var0 += var0 << 3;
        var0 ^= var0 >> 4;
        var0 *= 668265261;
        var0 ^= var0 >> 15;
        return var0;
    }
    
    public static int getRandom(final int var0, final int var1, final int var2, final int var3) {
        int var4 = intHash(var3 + 37);
        var4 = intHash(var4 + var0);
        var4 = intHash(var4 + var2);
        var4 = intHash(var4 + var1);
        return var4;
    }
    
    public static WorldServer getWorldServer() {
        if (Config.minecraft == null) {
            return null;
        }
        final WorldClient var0 = Minecraft.theWorld;
        if (var0 == null) {
            return null;
        }
        final WorldProvider var2 = var0.provider;
        if (var2 == null) {
            return null;
        }
        final int var3 = var2.dimensionId;
        final IntegratedServer var4 = Config.minecraft.getIntegratedServer();
        if (var4 == null) {
            return null;
        }
        final WorldServer var5 = var4.worldServerForDimension(var3);
        return var5;
    }
    
    public static int getAvailableProcessors() {
        if (Config.availableProcessors < 1) {
            Config.availableProcessors = Runtime.getRuntime().availableProcessors();
        }
        return Config.availableProcessors;
    }
    
    public static boolean isSingleProcessor() {
        return getAvailableProcessors() <= 1;
    }
    
    public static boolean isSmoothWorld() {
        return getAvailableProcessors() <= 1 && (Config.gameSettings == null || Config.gameSettings.ofSmoothWorld);
    }
    
    public static boolean isLazyChunkLoading() {
        return getAvailableProcessors() <= 1 && (Config.gameSettings == null || Config.gameSettings.ofLazyChunkLoading);
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 10;
        }
        final int var0 = Config.gameSettings.ofRenderDistanceFine / 16;
        return (var0 <= 16) ? 10 : var0;
    }
}
