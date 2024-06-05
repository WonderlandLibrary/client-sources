package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import java.util.*;
import org.lwjgl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.util.*;
import net.minecraft.server.integrated.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.lang.reflect.*;
import org.lwjgl.input.*;
import java.io.*;

public class Config
{
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.8.8";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "E1";
    public static final String VERSION = "OptiFine_1.8.8_HD_U_E1";
    private static String newRelease;
    private static boolean notify64BitJava;
    public static String openGlVersion;
    public static String openGlRenderer;
    public static String openGlVendor;
    public static boolean fancyFogAvailable;
    public static boolean occlusionAvailable;
    private static GameSettings gameSettings;
    private static Minecraft minecraft;
    private static boolean initialized;
    private static Thread minecraftThread;
    private static DisplayMode desktopDisplayMode;
    private static int antialiasingLevel;
    private static int availableProcessors;
    public static boolean zoomMode;
    private static int texturePackClouds;
    public static boolean waterOpacityChanged;
    private static boolean fullscreenModeChecked;
    private static boolean desktopModeChecked;
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
    private static long lastActionTime;
    
    public static String getVersion() {
        return "OptiFine_1.8.8_HD_U_E1";
    }
    
    public static void initGameSettings(final GameSettings p_initGameSettings_0_) {
        Config.gameSettings = p_initGameSettings_0_;
        Config.minecraft = Minecraft.getMinecraft();
        Config.desktopDisplayMode = Display.getDesktopDisplayMode();
        updateAvailableProcessors();
    }
    
    public static void initDisplay() {
        checkInitialized();
        Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
        checkDisplaySettings();
        checkDisplayMode();
        Config.minecraftThread = Thread.currentThread();
        updateThreadPriorities();
    }
    
    public static void checkInitialized() {
        if (!Config.initialized && Display.isCreated()) {
            Config.initialized = true;
            checkOpenGlCaps();
            startVersionCheckThread();
        }
    }
    
    private static void checkOpenGlCaps() {
        log("");
        log(getVersion());
        log("" + new Date());
        log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        log("LWJGL: " + Sys.getVersion());
        Config.openGlVersion = GL11.glGetString(7938);
        Config.openGlRenderer = GL11.glGetString(7937);
        Config.openGlVendor = GL11.glGetString(7936);
        log("OpenGL: " + Config.openGlRenderer + ", version " + Config.openGlVersion + ", " + Config.openGlVendor);
        log("OpenGL Version: " + getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!(Config.fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance)) {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!(Config.occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query)) {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        final int i = Minecraft.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + i + "x" + i);
    }
    
    public static boolean isFancyFogAvailable() {
        return Config.fancyFogAvailable;
    }
    
    public static boolean isOcclusionAvailable() {
        return Config.occlusionAvailable;
    }
    
    public static String getOpenGlVersionString() {
        final int i = getOpenGlVersion();
        final String s = "" + i / 10 + "." + i % 10;
        return s;
    }
    
    private static int getOpenGlVersion() {
        return GLContext.getCapabilities().OpenGL11 ? (GLContext.getCapabilities().OpenGL12 ? (GLContext.getCapabilities().OpenGL13 ? (GLContext.getCapabilities().OpenGL14 ? (GLContext.getCapabilities().OpenGL15 ? (GLContext.getCapabilities().OpenGL20 ? (GLContext.getCapabilities().OpenGL21 ? (GLContext.getCapabilities().OpenGL30 ? (GLContext.getCapabilities().OpenGL31 ? (GLContext.getCapabilities().OpenGL32 ? (GLContext.getCapabilities().OpenGL33 ? (GLContext.getCapabilities().OpenGL40 ? 40 : 33) : 32) : 31) : 30) : 21) : 20) : 15) : 14) : 13) : 12) : 11) : 10;
    }
    
    public static void updateThreadPriorities() {
        updateAvailableProcessors();
        final int i = 8;
        if (isSingleProcessor()) {
            if (isSmoothWorld()) {
                Config.minecraftThread.setPriority(10);
                setThreadPriority("Server thread", 1);
            }
            else {
                Config.minecraftThread.setPriority(5);
                setThreadPriority("Server thread", 5);
            }
        }
        else {
            Config.minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 5);
        }
    }
    
    private static void setThreadPriority(final String p_setThreadPriority_0_, final int p_setThreadPriority_1_) {
        try {
            final ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
            if (threadgroup == null) {
                return;
            }
            final int i = (threadgroup.activeCount() + 10) * 2;
            final Thread[] athread = new Thread[i];
            threadgroup.enumerate(athread, false);
            for (int j = 0; j < athread.length; ++j) {
                final Thread thread = athread[j];
                if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_)) {
                    thread.setPriority(p_setThreadPriority_1_);
                }
            }
        }
        catch (Throwable throwable) {
            dbg(throwable.getClass().getName() + ": " + throwable.getMessage());
        }
    }
    
    public static boolean isMinecraftThread() {
        return Thread.currentThread() == Config.minecraftThread;
    }
    
    private static void startVersionCheckThread() {
        final VersionCheckThread versioncheckthread = new VersionCheckThread();
        versioncheckthread.start();
    }
    
    public static int getMipmapType() {
        if (Config.gameSettings == null) {
            return Config.DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9986;
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
                return 9986;
            }
        }
    }
    
    public static boolean isUseAlphaFunc() {
        final float f = getAlphaFuncLevel();
        return f > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f;
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static boolean isFogFancy() {
        return isFancyFogAvailable() && Config.gameSettings.ofFogType == 2;
    }
    
    public static boolean isFogFast() {
        return Config.gameSettings.ofFogType == 1;
    }
    
    public static boolean isFogOff() {
        return Config.gameSettings.ofFogType == 3;
    }
    
    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }
    
    public static void dbg(final String p_dbg_0_) {
        Config.systemOut.print("[OptiFine] ");
        Config.systemOut.println(p_dbg_0_);
    }
    
    public static void warn(final String p_warn_0_) {
        Config.systemOut.print("[OptiFine] [WARN] ");
        Config.systemOut.println(p_warn_0_);
    }
    
    public static void error(final String p_error_0_) {
        Config.systemOut.print("[OptiFine] [ERROR] ");
        Config.systemOut.println(p_error_0_);
    }
    
    public static void log(final String p_log_0_) {
        dbg(p_log_0_);
    }
    
    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static boolean isRainFancy() {
        return (Config.gameSettings.ofRain == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofRain == 2);
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
        final IResourceManager iresourcemanager = getResourceManager();
        if (iresourcemanager != null) {
            try {
                final InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
                if (inputstream == null) {
                    return;
                }
                final Properties properties = new Properties();
                properties.load(inputstream);
                inputstream.close();
                String s = properties.getProperty("clouds");
                if (s == null) {
                    return;
                }
                dbg("Texture pack clouds: " + s);
                s = s.toLowerCase();
                if (s.equals("fast")) {
                    Config.texturePackClouds = 1;
                }
                if (s.equals("fancy")) {
                    Config.texturePackClouds = 2;
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public static boolean isTreesFancy() {
        return (Config.gameSettings.ofTrees == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTrees == 2);
    }
    
    public static boolean isDroppedItemsFancy() {
        return (Config.gameSettings.ofDroppedItems == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofDroppedItems == 2);
    }
    
    public static int limit(final int p_limit_0_, final int p_limit_1_, final int p_limit_2_) {
        return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
    }
    
    public static float limit(final float p_limit_0_, final float p_limit_1_, final float p_limit_2_) {
        return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
    }
    
    public static float limitTo1(final float p_limitTo1_0_) {
        return (p_limitTo1_0_ < 0.0f) ? 0.0f : ((p_limitTo1_0_ > 1.0f) ? 1.0f : p_limitTo1_0_);
    }
    
    public static boolean isAnimatedWater() {
        return Config.gameSettings.ofAnimatedWater != 2;
    }
    
    public static boolean isGeneratedWater() {
        return Config.gameSettings.ofAnimatedWater == 1;
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }
    
    public static boolean isAnimatedLava() {
        return Config.gameSettings.ofAnimatedLava != 2;
    }
    
    public static boolean isGeneratedLava() {
        return Config.gameSettings.ofAnimatedLava == 1;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }
    
    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }
    
    private static Method getMethod(final Class p_getMethod_0_, final String p_getMethod_1_, final Object[] p_getMethod_2_) {
        final Method[] amethod = p_getMethod_0_.getMethods();
        for (int i = 0; i < amethod.length; ++i) {
            final Method method = amethod[i];
            if (method.getName().equals(p_getMethod_1_) && method.getParameterTypes().length == p_getMethod_2_.length) {
                return method;
            }
        }
        warn("No method found for: " + p_getMethod_0_.getName() + "." + p_getMethod_1_ + "(" + arrayToString(p_getMethod_2_) + ")");
        return null;
    }
    
    public static String arrayToString(final Object[] p_arrayToString_0_) {
        if (p_arrayToString_0_ == null) {
            return "";
        }
        final StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
        for (int i = 0; i < p_arrayToString_0_.length; ++i) {
            final Object object = p_arrayToString_0_[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(String.valueOf(object));
        }
        return stringbuffer.toString();
    }
    
    public static String arrayToString(final int[] p_arrayToString_0_) {
        if (p_arrayToString_0_ == null) {
            return "";
        }
        final StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
        for (int i = 0; i < p_arrayToString_0_.length; ++i) {
            final int j = p_arrayToString_0_[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(String.valueOf(j));
        }
        return stringbuffer.toString();
    }
    
    public static Minecraft getMinecraft() {
        return Config.minecraft;
    }
    
    public static TextureManager getTextureManager() {
        return Config.minecraft.getTextureManager();
    }
    
    public static IResourceManager getResourceManager() {
        return Config.minecraft.getResourceManager();
    }
    
    public static InputStream getResourceStream(final ResourceLocation p_getResourceStream_0_) throws IOException {
        return getResourceStream(Config.minecraft.getResourceManager(), p_getResourceStream_0_);
    }
    
    public static InputStream getResourceStream(final IResourceManager p_getResourceStream_0_, final ResourceLocation p_getResourceStream_1_) throws IOException {
        final IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
        return (iresource == null) ? null : iresource.getInputStream();
    }
    
    public static IResource getResource(final ResourceLocation p_getResource_0_) throws IOException {
        return Config.minecraft.getResourceManager().getResource(p_getResource_0_);
    }
    
    public static boolean hasResource(final ResourceLocation p_hasResource_0_) {
        try {
            final IResource iresource = getResource(p_hasResource_0_);
            return iresource != null;
        }
        catch (IOException var2) {
            return false;
        }
    }
    
    public static boolean hasResource(final IResourceManager p_hasResource_0_, final ResourceLocation p_hasResource_1_) {
        try {
            final IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
            return iresource != null;
        }
        catch (IOException var3) {
            return false;
        }
    }
    
    public static IResourcePack[] getResourcePacks() {
        final ResourcePackRepository resourcepackrepository = Config.minecraft.getResourcePackRepository();
        final List list = resourcepackrepository.getRepositoryEntries();
        final List list2 = new ArrayList();
        for (final Object resourcepackrepository$entry : list) {
            list2.add(((ResourcePackRepository.Entry)resourcepackrepository$entry).getResourcePack());
        }
        final IResourcePack[] airesourcepack = list2.toArray(new IResourcePack[list2.size()]);
        return airesourcepack;
    }
    
    public static String getResourcePackNames() {
        if (Config.minecraft == null) {
            return "";
        }
        if (Config.minecraft.getResourcePackRepository() == null) {
            return "";
        }
        final IResourcePack[] airesourcepack = getResourcePacks();
        if (airesourcepack.length <= 0) {
            return getDefaultResourcePack().getPackName();
        }
        final String[] astring = new String[airesourcepack.length];
        for (int i = 0; i < airesourcepack.length; ++i) {
            astring[i] = airesourcepack[i].getPackName();
        }
        final String s = arrayToString(astring);
        return s;
    }
    
    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }
    
    public static boolean isFromDefaultResourcePack(final ResourceLocation p_isFromDefaultResourcePack_0_) {
        final IResourcePack iresourcepack = getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
        return iresourcepack == getDefaultResourcePack();
    }
    
    public static IResourcePack getDefiningResourcePack(final ResourceLocation p_getDefiningResourcePack_0_) {
        final IResourcePack[] airesourcepack = getResourcePacks();
        for (int i = airesourcepack.length - 1; i >= 0; --i) {
            final IResourcePack iresourcepack = airesourcepack[i];
            if (iresourcepack.resourceExists(p_getDefiningResourcePack_0_)) {
                return iresourcepack;
            }
        }
        if (getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_)) {
            return getDefaultResourcePack();
        }
        return null;
    }
    
    public static RenderGlobal getRenderGlobal() {
        return (Config.minecraft == null) ? null : Config.minecraft.renderGlobal;
    }
    
    public static int getMaxDynamicTileWidth() {
        return 64;
    }
    
    public static boolean isBetterGrass() {
        return Config.gameSettings.ofBetterGrass != 3;
    }
    
    public static boolean isBetterGrassFancy() {
        return Config.gameSettings.ofBetterGrass == 2;
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }
    
    public static boolean isVignetteEnabled() {
        return (Config.gameSettings.ofVignette == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofVignette == 2);
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }
    
    public static void sleep(final long p_sleep_0_) {
        try {
            Thread.currentThread();
            Thread.sleep(p_sleep_0_);
        }
        catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }
    }
    
    public static boolean isTimeDayOnly() {
        return Config.gameSettings.ofTime == 1;
    }
    
    public static boolean isTimeDefault() {
        return Config.gameSettings.ofTime == 0 || Config.gameSettings.ofTime == 2;
    }
    
    public static boolean isTimeNightOnly() {
        return Config.gameSettings.ofTime == 3;
    }
    
    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }
    
    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }
    
    public static int getAntialiasingLevel() {
        return Config.antialiasingLevel;
    }
    
    public static boolean between(final int p_between_0_, final int p_between_1_, final int p_between_2_) {
        return p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_;
    }
    
    public static boolean isMultiTexture() {
        return getAnisotropicFilterLevel() > 1 || getAntialiasingLevel() > 0;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }
    
    public static Dimension getFullscreenDimension() {
        if (Config.desktopDisplayMode == null) {
            return null;
        }
        if (Config.gameSettings == null) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String s = Config.gameSettings.ofFullscreenMode;
        if (s.equals("Default")) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String[] astring = tokenize(s, " x");
        return (astring.length < 2) ? new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight()) : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
    }
    
    public static int parseInt(final String p_parseInt_0_, final int p_parseInt_1_) {
        try {
            return (p_parseInt_0_ == null) ? p_parseInt_1_ : Integer.parseInt(p_parseInt_0_);
        }
        catch (NumberFormatException var3) {
            return p_parseInt_1_;
        }
    }
    
    public static float parseFloat(final String p_parseFloat_0_, final float p_parseFloat_1_) {
        try {
            return (p_parseFloat_0_ == null) ? p_parseFloat_1_ : Float.parseFloat(p_parseFloat_0_);
        }
        catch (NumberFormatException var3) {
            return p_parseFloat_1_;
        }
    }
    
    public static String[] tokenize(final String p_tokenize_0_, final String p_tokenize_1_) {
        final StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
        final List list = new ArrayList();
        while (stringtokenizer.hasMoreTokens()) {
            final String s = stringtokenizer.nextToken();
            list.add(s);
        }
        final String[] astring = list.toArray(new String[list.size()]);
        return astring;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Config.desktopDisplayMode;
    }
    
    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            final DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
            final List list = new ArrayList();
            for (int i = 0; i < adisplaymode.length; ++i) {
                final DisplayMode displaymode = adisplaymode[i];
                if (Config.desktopDisplayMode == null || (displaymode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == Config.desktopDisplayMode.getFrequency())) {
                    list.add(displaymode);
                }
            }
            final DisplayMode[] adisplaymode2 = list.toArray(new DisplayMode[list.size()]);
            final Comparator comparator = new Comparator() {
                @Override
                public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                    final DisplayMode displaymode1 = (DisplayMode)p_compare_1_;
                    final DisplayMode displaymode2 = (DisplayMode)p_compare_2_;
                    return (displaymode1.getWidth() != displaymode2.getWidth()) ? (displaymode2.getWidth() - displaymode1.getWidth()) : ((displaymode1.getHeight() != displaymode2.getHeight()) ? (displaymode2.getHeight() - displaymode1.getHeight()) : 0);
                }
            };
            Arrays.sort(adisplaymode2, comparator);
            return adisplaymode2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new DisplayMode[] { Config.desktopDisplayMode };
        }
    }
    
    public static String[] getFullscreenModes() {
        final DisplayMode[] adisplaymode = getFullscreenDisplayModes();
        final String[] astring = new String[adisplaymode.length];
        for (int i = 0; i < adisplaymode.length; ++i) {
            final DisplayMode displaymode = adisplaymode[i];
            final String s = "" + displaymode.getWidth() + "x" + displaymode.getHeight();
            astring[i] = s;
        }
        return astring;
    }
    
    public static DisplayMode getDisplayMode(final Dimension p_getDisplayMode_0_) throws LWJGLException {
        final DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
        for (int i = 0; i < adisplaymode.length; ++i) {
            final DisplayMode displaymode = adisplaymode[i];
            if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height && (Config.desktopDisplayMode == null || (displaymode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == Config.desktopDisplayMode.getFrequency()))) {
                return displaymode;
            }
        }
        return Config.desktopDisplayMode;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }
    
    public static boolean isRandomMobs() {
        return Config.gameSettings.ofRandomMobs;
    }
    
    public static void checkGlError(final String p_checkGlError_0_) {
        final int i = GL11.glGetError();
        if (i != 0) {
            final String s = GLU.gluErrorString(i);
            error("OpenGlError: " + i + " (" + s + "), at: " + p_checkGlError_0_);
        }
    }
    
    public static boolean isSmoothBiomes() {
        return Config.gameSettings.ofSmoothBiomes;
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isConnectedTextures() {
        return Config.gameSettings.ofConnectedTextures != 3;
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }
    
    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings.ofConnectedTextures == 2;
    }
    
    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }
    
    public static boolean isTranslucentBlocksFancy() {
        return (Config.gameSettings.ofTranslucentBlocks == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTranslucentBlocks == 2);
    }
    
    public static String[] readLines(final File p_readLines_0_) throws IOException {
        final List list = new ArrayList();
        final FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
        final InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream, "ASCII");
        final BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        while (true) {
            final String s = bufferedreader.readLine();
            if (s == null) {
                break;
            }
            list.add(s);
        }
        final String[] astring = list.toArray(new String[list.size()]);
        return astring;
    }
    
    public static String readFile(final File p_readFile_0_) throws IOException {
        final FileInputStream fileinputstream = new FileInputStream(p_readFile_0_);
        return readInputStream(fileinputstream, "ASCII");
    }
    
    public static String readInputStream(final InputStream p_readInputStream_0_) throws IOException {
        return readInputStream(p_readInputStream_0_, "ASCII");
    }
    
    public static String readInputStream(final InputStream p_readInputStream_0_, final String p_readInputStream_1_) throws IOException {
        final InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
        final BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        final StringBuffer stringbuffer = new StringBuffer();
        while (true) {
            final String s = bufferedreader.readLine();
            if (s == null) {
                break;
            }
            stringbuffer.append(s);
            stringbuffer.append("\n");
        }
        return stringbuffer.toString();
    }
    
    public static GameSettings getGameSettings() {
        return Config.gameSettings;
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void setNewRelease(final String p_setNewRelease_0_) {
        Config.newRelease = p_setNewRelease_0_;
    }
    
    public static int compareRelease(final String p_compareRelease_0_, final String p_compareRelease_1_) {
        final String[] astring = splitRelease(p_compareRelease_0_);
        final String[] astring2 = splitRelease(p_compareRelease_1_);
        final String s = astring[0];
        final String s2 = astring2[0];
        if (!s.equals(s2)) {
            return s.compareTo(s2);
        }
        final int i = parseInt(astring[1], -1);
        final int j = parseInt(astring2[1], -1);
        if (i != j) {
            return i - j;
        }
        final String s3 = astring[2];
        final String s4 = astring2[2];
        return s3.compareTo(s4);
    }
    
    private static String[] splitRelease(final String p_splitRelease_0_) {
        if (p_splitRelease_0_ == null || p_splitRelease_0_.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final String s = p_splitRelease_0_.substring(0, 1);
        if (p_splitRelease_0_.length() <= 1) {
            return new String[] { s, "", "" };
        }
        int i;
        for (i = 1; i < p_splitRelease_0_.length() && Character.isDigit(p_splitRelease_0_.charAt(i)); ++i) {}
        final String s2 = p_splitRelease_0_.substring(1, i);
        if (i >= p_splitRelease_0_.length()) {
            return new String[] { s, s2, "" };
        }
        final String s3 = p_splitRelease_0_.substring(i);
        return new String[] { s, s2, s3 };
    }
    
    public static int intHash(int p_intHash_0_) {
        p_intHash_0_ = (p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16);
        p_intHash_0_ += p_intHash_0_ << 3;
        p_intHash_0_ ^= p_intHash_0_ >> 4;
        p_intHash_0_ *= 668265261;
        p_intHash_0_ ^= p_intHash_0_ >> 15;
        return p_intHash_0_;
    }
    
    public static int getRandom(final BlockPos p_getRandom_0_, final int p_getRandom_1_) {
        int i = intHash(p_getRandom_1_ + 37);
        i = intHash(i + p_getRandom_0_.getX());
        i = intHash(i + p_getRandom_0_.getZ());
        i = intHash(i + p_getRandom_0_.getY());
        return i;
    }
    
    public static WorldServer getWorldServer() {
        if (Config.minecraft == null) {
            return null;
        }
        final World world = Config.minecraft.theWorld;
        if (world == null) {
            return null;
        }
        if (!Config.minecraft.isIntegratedServerRunning()) {
            return null;
        }
        final IntegratedServer integratedserver = Config.minecraft.getIntegratedServer();
        if (integratedserver == null) {
            return null;
        }
        final WorldProvider worldprovider = world.provider;
        if (worldprovider == null) {
            return null;
        }
        final int i = worldprovider.getDimensionId();
        try {
            final WorldServer worldserver = integratedserver.worldServerForDimension(i);
            return worldserver;
        }
        catch (NullPointerException var5) {
            return null;
        }
    }
    
    public static int getAvailableProcessors() {
        return Config.availableProcessors;
    }
    
    public static void updateAvailableProcessors() {
        Config.availableProcessors = Runtime.getRuntime().availableProcessors();
    }
    
    public static boolean isSingleProcessor() {
        return getAvailableProcessors() <= 1;
    }
    
    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }
    
    public static boolean isLazyChunkLoading() {
        return isSingleProcessor() && Config.gameSettings.ofLazyChunkLoading;
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 10;
        }
        final int i = Config.gameSettings.renderDistanceChunks;
        return i;
    }
    
    public static boolean equals(final Object p_equals_0_, final Object p_equals_1_) {
        return p_equals_0_ == p_equals_1_ || (p_equals_0_ != null && p_equals_0_.equals(p_equals_1_));
    }
    
    public static void checkDisplaySettings() {
        if (getAntialiasingLevel() > 0) {
            final int i = getAntialiasingLevel();
            final DisplayMode displaymode = Display.getDisplayMode();
            dbg("FSAA Samples: " + i);
            try {
                Display.destroy();
                Display.setDisplayMode(displaymode);
                Display.create(new PixelFormat().withDepthBits(24).withSamples(i));
                Display.setResizable(false);
                Display.setResizable(true);
            }
            catch (LWJGLException lwjglexception2) {
                warn("Error setting FSAA: " + i + "x");
                lwjglexception2.printStackTrace();
                try {
                    Display.setDisplayMode(displaymode);
                    Display.create(new PixelFormat().withDepthBits(24));
                    Display.setResizable(false);
                    Display.setResizable(true);
                }
                catch (LWJGLException lwjglexception3) {
                    lwjglexception3.printStackTrace();
                    try {
                        Display.setDisplayMode(displaymode);
                        Display.create();
                        Display.setResizable(false);
                        Display.setResizable(true);
                    }
                    catch (LWJGLException lwjglexception4) {
                        lwjglexception4.printStackTrace();
                    }
                }
            }
        }
    }
    
    private static ByteBuffer readIconImage(final File p_readIconImage_0_) throws IOException {
        final BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        for (final int i : aint) {
            bytebuffer.putInt(i << 8 | (i >> 24 & 0xFF));
        }
        bytebuffer.flip();
        return bytebuffer;
    }
    
    public static void checkDisplayMode() {
        try {
            if (Config.minecraft.isFullScreen()) {
                if (Config.fullscreenModeChecked) {
                    return;
                }
                Config.fullscreenModeChecked = true;
                Config.desktopModeChecked = false;
                final DisplayMode displaymode = Display.getDisplayMode();
                final Dimension dimension = getFullscreenDimension();
                if (dimension == null) {
                    return;
                }
                if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
                    return;
                }
                final DisplayMode displaymode2 = getDisplayMode(dimension);
                if (displaymode2 == null) {
                    return;
                }
                Display.setDisplayMode(displaymode2);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = 1;
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = 1;
                }
                if (Config.minecraft.currentScreen != null) {
                    final ScaledResolution scaledresolution = new ScaledResolution(Config.minecraft);
                    final int i = scaledresolution.getScaledWidth();
                    final int j = scaledresolution.getScaledHeight();
                    Config.minecraft.currentScreen.setWorldAndResolution(Config.minecraft, i, j);
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(Config.minecraft);
                updateFramebufferSize();
                Display.setFullscreen(true);
                Config.minecraft.gameSettings.updateVSync();
                GlStateManager.enableTexture2D();
            }
            else {
                if (Config.desktopModeChecked) {
                    return;
                }
                Config.desktopModeChecked = true;
                Config.fullscreenModeChecked = false;
                Config.minecraft.gameSettings.updateVSync();
                Display.update();
                GlStateManager.enableTexture2D();
                Display.setResizable(false);
                Display.setResizable(true);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public static void updateFramebufferSize() {
        Config.minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }
    
    public static Object[] addObjectToArray(final Object[] p_addObjectToArray_0_, final Object p_addObjectToArray_1_) {
        if (p_addObjectToArray_0_ == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int i = p_addObjectToArray_0_.length;
        final int j = i + 1;
        final Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
        System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
        aobject[i] = p_addObjectToArray_1_;
        return aobject;
    }
    
    public static Object[] addObjectsToArray(final Object[] p_addObjectsToArray_0_, final Object[] p_addObjectsToArray_1_) {
        if (p_addObjectsToArray_0_ == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (p_addObjectsToArray_1_.length == 0) {
            return p_addObjectsToArray_0_;
        }
        final int i = p_addObjectsToArray_0_.length;
        final int j = i + p_addObjectsToArray_1_.length;
        final Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
        System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
        System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
        return aobject;
    }
    
    public static boolean isCustomItems() {
        return false;
    }
    
    public static boolean isActing() {
        final boolean flag = isActingNow();
        final long i = System.currentTimeMillis();
        if (flag) {
            Config.lastActionTime = i;
            return true;
        }
        return i - Config.lastActionTime < 100L;
    }
    
    private static boolean isActingNow() {
        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
    }
    
    public static void drawFps() {
        final Minecraft minecraftx = Config.minecraft;
        final int i = Minecraft.getDebugFPS();
        final String s = getUpdates(Config.minecraft.debug);
        final int j = Config.minecraft.renderGlobal.getCountActiveRenderers();
        final int k = Config.minecraft.renderGlobal.getCountEntitiesRendered();
        final int l = Config.minecraft.renderGlobal.getCountTileEntitiesRendered();
        final String s2 = "" + i + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
        Config.minecraft.fontRendererObj.drawString(s2, 2, 2, -2039584);
    }
    
    private static String getUpdates(final String p_getUpdates_0_) {
        final int i = p_getUpdates_0_.indexOf(40);
        if (i < 0) {
            return "";
        }
        final int j = p_getUpdates_0_.indexOf(32, i);
        return (j < 0) ? "" : p_getUpdates_0_.substring(i + 1, j);
    }
    
    public static int getBitsOs() {
        final String s = System.getenv("ProgramFiles(X86)");
        return (s != null) ? 64 : 32;
    }
    
    public static int getBitsJre() {
        final String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (int i = 0; i < astring.length; ++i) {
            final String s = astring[i];
            final String s2 = System.getProperty(s);
            if (s2 != null && s2.contains("64")) {
                return 64;
            }
        }
        return 32;
    }
    
    public static boolean isNotify64BitJava() {
        return Config.notify64BitJava;
    }
    
    public static void setNotify64BitJava(final boolean p_setNotify64BitJava_0_) {
        Config.notify64BitJava = p_setNotify64BitJava_0_;
    }
    
    public static boolean isConnectedModels() {
        return false;
    }
    
    public static String fillLeft(String p_fillLeft_0_, final int p_fillLeft_1_, final char p_fillLeft_2_) {
        if (p_fillLeft_0_ == null) {
            p_fillLeft_0_ = "";
        }
        if (p_fillLeft_0_.length() >= p_fillLeft_1_) {
            return p_fillLeft_0_;
        }
        final StringBuffer stringbuffer = new StringBuffer(p_fillLeft_0_);
        while (stringbuffer.length() < p_fillLeft_1_ - p_fillLeft_0_.length()) {
            stringbuffer.append(p_fillLeft_2_);
        }
        return stringbuffer.toString() + p_fillLeft_0_;
    }
    
    public static String fillRight(String p_fillRight_0_, final int p_fillRight_1_, final char p_fillRight_2_) {
        if (p_fillRight_0_ == null) {
            p_fillRight_0_ = "";
        }
        if (p_fillRight_0_.length() >= p_fillRight_1_) {
            return p_fillRight_0_;
        }
        final StringBuffer stringbuffer = new StringBuffer(p_fillRight_0_);
        while (stringbuffer.length() < p_fillRight_1_) {
            stringbuffer.append(p_fillRight_2_);
        }
        return stringbuffer.toString();
    }
    
    static {
        Config.newRelease = null;
        Config.notify64BitJava = false;
        Config.openGlVersion = null;
        Config.openGlRenderer = null;
        Config.openGlVendor = null;
        Config.fancyFogAvailable = false;
        Config.occlusionAvailable = false;
        Config.gameSettings = null;
        Config.minecraft = null;
        Config.initialized = false;
        Config.minecraftThread = null;
        Config.desktopDisplayMode = null;
        Config.antialiasingLevel = 0;
        Config.availableProcessors = 0;
        Config.zoomMode = false;
        Config.texturePackClouds = 0;
        Config.waterOpacityChanged = false;
        Config.fullscreenModeChecked = false;
        Config.desktopModeChecked = false;
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
        Config.lastActionTime = System.currentTimeMillis();
    }
}
