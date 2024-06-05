/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.Sys
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.opengl.PixelFormat
 *  org.lwjgl.util.glu.GLU
 */
package optfine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import optfine.VersionCheckThread;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Config {
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.8.8";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "E1";
    public static final String VERSION = "OptiFine_1.8.8_HD_U_E1";
    private static String newRelease = null;
    private static boolean notify64BitJava = false;
    public static String openGlVersion = null;
    public static String openGlRenderer = null;
    public static String openGlVendor = null;
    public static boolean fancyFogAvailable = false;
    public static boolean occlusionAvailable = false;
    private static GameSettings gameSettings = null;
    private static Minecraft minecraft = null;
    private static boolean initialized = false;
    private static Thread minecraftThread = null;
    private static DisplayMode desktopDisplayMode = null;
    private static int antialiasingLevel = 0;
    private static int availableProcessors = 0;
    public static boolean zoomMode = false;
    private static int texturePackClouds = 0;
    public static boolean waterOpacityChanged = false;
    private static boolean fullscreenModeChecked = false;
    private static boolean desktopModeChecked = false;
    private static PrintStream systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
    public static final Boolean DEF_FOG_FANCY = true;
    public static final Float DEF_FOG_START = Float.valueOf(0.2f);
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE = false;
    public static final Boolean DEF_OCCLUSION_ENABLED = false;
    public static final Integer DEF_MIPMAP_LEVEL = 0;
    public static final Integer DEF_MIPMAP_TYPE = 9984;
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1f);
    public static final Boolean DEF_LOAD_CHUNKS_FAR = false;
    public static final Integer DEF_PRELOADED_CHUNKS = 0;
    public static final Integer DEF_CHUNKS_LIMIT = 25;
    public static final Integer DEF_UPDATES_PER_FRAME = 3;
    public static final Boolean DEF_DYNAMIC_UPDATES = false;
    private static long lastActionTime = System.currentTimeMillis();

    public static String getVersion() {
        return VERSION;
    }

    public static void initGameSettings(GameSettings p_initGameSettings_0_) {
        gameSettings = p_initGameSettings_0_;
        minecraft = Minecraft.getMinecraft();
        desktopDisplayMode = Display.getDesktopDisplayMode();
        Config.updateAvailableProcessors();
    }

    public static void initDisplay() {
        Config.checkInitialized();
        antialiasingLevel = Config.gameSettings.ofAaLevel;
        Config.checkDisplaySettings();
        Config.checkDisplayMode();
        minecraftThread = Thread.currentThread();
        Config.updateThreadPriorities();
    }

    public static void checkInitialized() {
        if (initialized) return;
        if (!Display.isCreated()) return;
        initialized = true;
        Config.checkOpenGlCaps();
        Config.startVersionCheckThread();
    }

    private static void checkOpenGlCaps() {
        Config.log("");
        Config.log(Config.getVersion());
        Config.log("" + new Date());
        Config.log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        Config.log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        Config.log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        Config.log("LWJGL: " + Sys.getVersion());
        openGlVersion = GL11.glGetString((int)7938);
        openGlRenderer = GL11.glGetString((int)7937);
        openGlVendor = GL11.glGetString((int)7936);
        Config.log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
        Config.log("OpenGL Version: " + Config.getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            Config.log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!(fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance)) {
            Config.log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!(occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query)) {
            Config.log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        int i = Minecraft.getGLMaximumTextureSize();
        Config.dbg("Maximum texture size: " + i + "x" + i);
    }

    public static boolean isFancyFogAvailable() {
        return fancyFogAvailable;
    }

    public static boolean isOcclusionAvailable() {
        return occlusionAvailable;
    }

    public static String getOpenGlVersionString() {
        int i = Config.getOpenGlVersion();
        return "" + i / 10 + "." + i % 10;
    }

    private static int getOpenGlVersion() {
        if (!GLContext.getCapabilities().OpenGL11) {
            return 10;
        }
        if (!GLContext.getCapabilities().OpenGL12) {
            return 11;
        }
        if (!GLContext.getCapabilities().OpenGL13) {
            return 12;
        }
        if (!GLContext.getCapabilities().OpenGL14) {
            return 13;
        }
        if (!GLContext.getCapabilities().OpenGL15) {
            return 14;
        }
        if (!GLContext.getCapabilities().OpenGL20) {
            return 15;
        }
        if (!GLContext.getCapabilities().OpenGL21) {
            return 20;
        }
        if (!GLContext.getCapabilities().OpenGL30) {
            return 21;
        }
        if (!GLContext.getCapabilities().OpenGL31) {
            return 30;
        }
        if (!GLContext.getCapabilities().OpenGL32) {
            return 31;
        }
        if (!GLContext.getCapabilities().OpenGL33) {
            return 32;
        }
        if (GLContext.getCapabilities().OpenGL40) return 40;
        return 33;
    }

    public static void updateThreadPriorities() {
        Config.updateAvailableProcessors();
        int i = 8;
        if (!Config.isSingleProcessor()) {
            minecraftThread.setPriority(10);
            Config.setThreadPriority("Server thread", 5);
            return;
        }
        if (Config.isSmoothWorld()) {
            minecraftThread.setPriority(10);
            Config.setThreadPriority("Server thread", 1);
            return;
        }
        minecraftThread.setPriority(5);
        Config.setThreadPriority("Server thread", 5);
    }

    private static void setThreadPriority(String p_setThreadPriority_0_, int p_setThreadPriority_1_) {
        try {
            ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
            if (threadgroup == null) {
                return;
            }
            int i = (threadgroup.activeCount() + 10) * 2;
            Thread[] athread = new Thread[i];
            threadgroup.enumerate(athread, false);
            int j = 0;
            while (j < athread.length) {
                Thread thread = athread[j];
                if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_)) {
                    thread.setPriority(p_setThreadPriority_1_);
                }
                ++j;
            }
            return;
        }
        catch (Throwable throwable) {
            Config.dbg(throwable.getClass().getName() + ": " + throwable.getMessage());
        }
    }

    public static boolean isMinecraftThread() {
        if (Thread.currentThread() != minecraftThread) return false;
        return true;
    }

    private static void startVersionCheckThread() {
        VersionCheckThread versioncheckthread = new VersionCheckThread();
        versioncheckthread.start();
    }

    public static int getMipmapType() {
        if (gameSettings == null) {
            return DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9986;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (!Config.isMultiTexture()) return 9986;
                return 9985;
            }
            case 3: {
                if (!Config.isMultiTexture()) return 9986;
                return 9987;
            }
        }
        return 9986;
    }

    public static boolean isUseAlphaFunc() {
        float f = Config.getAlphaFuncLevel();
        if (!(f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5f)) return false;
        return true;
    }

    public static float getAlphaFuncLevel() {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy() {
        if (!Config.isFancyFogAvailable()) {
            return false;
        }
        if (Config.gameSettings.ofFogType != 2) return false;
        return true;
    }

    public static boolean isFogFast() {
        if (Config.gameSettings.ofFogType != 1) return false;
        return true;
    }

    public static boolean isFogOff() {
        if (Config.gameSettings.ofFogType != 3) return false;
        return true;
    }

    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }

    public static void dbg(String p_dbg_0_) {
        systemOut.print("[OptiFine] ");
        systemOut.println(p_dbg_0_);
    }

    public static void warn(String p_warn_0_) {
        systemOut.print("[OptiFine] [WARN] ");
        systemOut.println(p_warn_0_);
    }

    public static void error(String p_error_0_) {
        systemOut.print("[OptiFine] [ERROR] ");
        systemOut.println(p_error_0_);
    }

    public static void log(String p_log_0_) {
        Config.dbg(p_log_0_);
    }

    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }

    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }

    public static boolean isRainFancy() {
        if (Config.gameSettings.ofRain == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (Config.gameSettings.ofRain != 2) return false;
        return true;
    }

    public static boolean isRainOff() {
        if (Config.gameSettings.ofRain != 3) return false;
        return true;
    }

    public static boolean isCloudsFancy() {
        if (Config.gameSettings.ofClouds != 0) {
            if (Config.gameSettings.ofClouds != 2) return false;
            return true;
        }
        if (texturePackClouds == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (texturePackClouds != 2) return false;
        return true;
    }

    public static boolean isCloudsOff() {
        if (Config.gameSettings.ofClouds != 3) return false;
        return true;
    }

    public static void updateTexturePackClouds() {
        texturePackClouds = 0;
        IResourceManager iresourcemanager = Config.getResourceManager();
        if (iresourcemanager == null) return;
        try {
            InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (inputstream == null) {
                return;
            }
            Properties properties = new Properties();
            properties.load(inputstream);
            inputstream.close();
            String s = properties.getProperty("clouds");
            if (s == null) {
                return;
            }
            Config.dbg("Texture pack clouds: " + s);
            s = s.toLowerCase();
            if (s.equals("fast")) {
                texturePackClouds = 1;
            }
            if (!s.equals("fancy")) return;
            texturePackClouds = 2;
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static boolean isTreesFancy() {
        if (Config.gameSettings.ofTrees == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (Config.gameSettings.ofTrees != 2) return false;
        return true;
    }

    public static boolean isDroppedItemsFancy() {
        if (Config.gameSettings.ofDroppedItems == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (Config.gameSettings.ofDroppedItems != 2) return false;
        return true;
    }

    public static int limit(int p_limit_0_, int p_limit_1_, int p_limit_2_) {
        int n;
        if (p_limit_0_ < p_limit_1_) {
            n = p_limit_1_;
            return n;
        }
        if (p_limit_0_ > p_limit_2_) {
            n = p_limit_2_;
            return n;
        }
        n = p_limit_0_;
        return n;
    }

    public static float limit(float p_limit_0_, float p_limit_1_, float p_limit_2_) {
        float f;
        if (p_limit_0_ < p_limit_1_) {
            f = p_limit_1_;
            return f;
        }
        if (p_limit_0_ > p_limit_2_) {
            f = p_limit_2_;
            return f;
        }
        f = p_limit_0_;
        return f;
    }

    public static float limitTo1(float p_limitTo1_0_) {
        if (p_limitTo1_0_ < 0.0f) {
            return 0.0f;
        }
        if (p_limitTo1_0_ > 1.0f) {
            return 1.0f;
        }
        float f = p_limitTo1_0_;
        return f;
    }

    public static boolean isAnimatedWater() {
        if (Config.gameSettings.ofAnimatedWater == 2) return false;
        return true;
    }

    public static boolean isGeneratedWater() {
        if (Config.gameSettings.ofAnimatedWater != 1) return false;
        return true;
    }

    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }

    public static boolean isAnimatedLava() {
        if (Config.gameSettings.ofAnimatedLava == 2) return false;
        return true;
    }

    public static boolean isGeneratedLava() {
        if (Config.gameSettings.ofAnimatedLava != 1) return false;
        return true;
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

    private static Method getMethod(Class p_getMethod_0_, String p_getMethod_1_, Object[] p_getMethod_2_) {
        Method[] amethod = p_getMethod_0_.getMethods();
        int i = 0;
        while (true) {
            if (i >= amethod.length) {
                Config.warn("No method found for: " + p_getMethod_0_.getName() + "." + p_getMethod_1_ + "(" + Config.arrayToString(p_getMethod_2_) + ")");
                return null;
            }
            Method method = amethod[i];
            if (method.getName().equals(p_getMethod_1_) && method.getParameterTypes().length == p_getMethod_2_.length) {
                return method;
            }
            ++i;
        }
    }

    public static String arrayToString(Object[] p_arrayToString_0_) {
        if (p_arrayToString_0_ == null) {
            return "";
        }
        StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
        int i = 0;
        while (i < p_arrayToString_0_.length) {
            Object object = p_arrayToString_0_[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(String.valueOf(object));
            ++i;
        }
        return stringbuffer.toString();
    }

    public static String arrayToString(int[] p_arrayToString_0_) {
        if (p_arrayToString_0_ == null) {
            return "";
        }
        StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
        int i = 0;
        while (i < p_arrayToString_0_.length) {
            int j = p_arrayToString_0_[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(String.valueOf(j));
            ++i;
        }
        return stringbuffer.toString();
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

    public static InputStream getResourceStream(ResourceLocation p_getResourceStream_0_) throws IOException {
        return Config.getResourceStream(minecraft.getResourceManager(), p_getResourceStream_0_);
    }

    public static InputStream getResourceStream(IResourceManager p_getResourceStream_0_, ResourceLocation p_getResourceStream_1_) throws IOException {
        IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
        if (iresource == null) {
            return null;
        }
        InputStream inputStream = iresource.getInputStream();
        return inputStream;
    }

    public static IResource getResource(ResourceLocation p_getResource_0_) throws IOException {
        return minecraft.getResourceManager().getResource(p_getResource_0_);
    }

    public static boolean hasResource(ResourceLocation p_hasResource_0_) {
        try {
            IResource iresource = Config.getResource(p_hasResource_0_);
            if (iresource == null) return false;
            return true;
        }
        catch (IOException var2) {
            return false;
        }
    }

    public static boolean hasResource(IResourceManager p_hasResource_0_, ResourceLocation p_hasResource_1_) {
        try {
            IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
            if (iresource == null) return false;
            return true;
        }
        catch (IOException var3) {
            return false;
        }
    }

    public static IResourcePack[] getResourcePacks() {
        ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
        List<ResourcePackRepository.Entry> list = resourcepackrepository.getRepositoryEntries();
        ArrayList<IResourcePack> list1 = new ArrayList<IResourcePack>();
        Iterator<ResourcePackRepository.Entry> iterator = list.iterator();
        while (iterator.hasNext()) {
            ResourcePackRepository.Entry resourcepackrepository$entry = iterator.next();
            list1.add(resourcepackrepository$entry.getResourcePack());
        }
        return list1.toArray(new IResourcePack[list1.size()]);
    }

    public static String getResourcePackNames() {
        if (minecraft == null) {
            return "";
        }
        if (minecraft.getResourcePackRepository() == null) {
            return "";
        }
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        if (airesourcepack.length <= 0) {
            return Config.getDefaultResourcePack().getPackName();
        }
        String[] astring = new String[airesourcepack.length];
        int i = 0;
        while (i < airesourcepack.length) {
            astring[i] = airesourcepack[i].getPackName();
            ++i;
        }
        return Config.arrayToString(astring);
    }

    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation p_isFromDefaultResourcePack_0_) {
        IResourcePack iresourcepack = Config.getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
        if (iresourcepack != Config.getDefaultResourcePack()) return false;
        return true;
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation p_getDefiningResourcePack_0_) {
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        int i = airesourcepack.length - 1;
        while (true) {
            if (i < 0) {
                if (!Config.getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_)) return null;
                return Config.getDefaultResourcePack();
            }
            IResourcePack iresourcepack = airesourcepack[i];
            if (iresourcepack.resourceExists(p_getDefiningResourcePack_0_)) {
                return iresourcepack;
            }
            --i;
        }
    }

    public static RenderGlobal getRenderGlobal() {
        if (minecraft == null) {
            return null;
        }
        RenderGlobal renderGlobal = Config.minecraft.renderGlobal;
        return renderGlobal;
    }

    public static int getMaxDynamicTileWidth() {
        return 64;
    }

    public static boolean isBetterGrass() {
        if (Config.gameSettings.ofBetterGrass == 3) return false;
        return true;
    }

    public static boolean isBetterGrassFancy() {
        if (Config.gameSettings.ofBetterGrass != 2) return false;
        return true;
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
        if (Config.gameSettings.ofVignette == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (Config.gameSettings.ofVignette != 2) return false;
        return true;
    }

    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }

    public static void sleep(long p_sleep_0_) {
        try {
            Thread.currentThread();
            Thread.sleep(p_sleep_0_);
            return;
        }
        catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }
    }

    public static boolean isTimeDayOnly() {
        if (Config.gameSettings.ofTime != 1) return false;
        return true;
    }

    public static boolean isTimeDefault() {
        if (Config.gameSettings.ofTime == 0) return true;
        if (Config.gameSettings.ofTime == 2) return true;
        return false;
    }

    public static boolean isTimeNightOnly() {
        if (Config.gameSettings.ofTime != 3) return false;
        return true;
    }

    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }

    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }

    public static int getAntialiasingLevel() {
        return antialiasingLevel;
    }

    public static boolean between(int p_between_0_, int p_between_1_, int p_between_2_) {
        if (p_between_0_ < p_between_1_) return false;
        if (p_between_0_ > p_between_2_) return false;
        return true;
    }

    public static boolean isMultiTexture() {
        if (Config.getAnisotropicFilterLevel() > 1) {
            return true;
        }
        if (Config.getAntialiasingLevel() <= 0) return false;
        return true;
    }

    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }

    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }

    public static Dimension getFullscreenDimension() {
        Dimension dimension;
        if (desktopDisplayMode == null) {
            return null;
        }
        if (gameSettings == null) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String s = Config.gameSettings.ofFullscreenMode;
        if (s.equals("Default")) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String[] astring = Config.tokenize(s, " x");
        if (astring.length < 2) {
            dimension = new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
            return dimension;
        }
        dimension = new Dimension(Config.parseInt(astring[0], -1), Config.parseInt(astring[1], -1));
        return dimension;
    }

    public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
        try {
            int n;
            if (p_parseInt_0_ == null) {
                n = p_parseInt_1_;
                return n;
            }
            n = Integer.parseInt(p_parseInt_0_);
            return n;
        }
        catch (NumberFormatException var3) {
            return p_parseInt_1_;
        }
    }

    public static float parseFloat(String p_parseFloat_0_, float p_parseFloat_1_) {
        try {
            float f;
            if (p_parseFloat_0_ == null) {
                f = p_parseFloat_1_;
                return f;
            }
            f = Float.parseFloat(p_parseFloat_0_);
            return f;
        }
        catch (NumberFormatException var3) {
            return p_parseFloat_1_;
        }
    }

    public static String[] tokenize(String p_tokenize_0_, String p_tokenize_1_) {
        StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
        ArrayList<String> list = new ArrayList<String>();
        while (stringtokenizer.hasMoreTokens()) {
            String s = stringtokenizer.nextToken();
            list.add(s);
        }
        return list.toArray(new String[list.size()]);
    }

    public static DisplayMode getDesktopDisplayMode() {
        return desktopDisplayMode;
    }

    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
            ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
            int i = 0;
            while (true) {
                if (i >= adisplaymode.length) {
                    DisplayMode[] adisplaymode1 = list.toArray(new DisplayMode[list.size()]);
                    Comparator comparator = new Comparator(){

                        public int compare(Object p_compare_1_, Object p_compare_2_) {
                            int n;
                            DisplayMode displaymode1 = (DisplayMode)p_compare_1_;
                            DisplayMode displaymode2 = (DisplayMode)p_compare_2_;
                            if (displaymode1.getWidth() != displaymode2.getWidth()) {
                                n = displaymode2.getWidth() - displaymode1.getWidth();
                                return n;
                            }
                            if (displaymode1.getHeight() == displaymode2.getHeight()) return 0;
                            n = displaymode2.getHeight() - displaymode1.getHeight();
                            return n;
                        }
                    };
                    Arrays.sort(adisplaymode1, comparator);
                    return adisplaymode1;
                }
                DisplayMode displaymode = adisplaymode[i];
                if (desktopDisplayMode == null || displaymode.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == desktopDisplayMode.getFrequency()) {
                    list.add(displaymode);
                }
                ++i;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new DisplayMode[]{desktopDisplayMode};
        }
    }

    public static String[] getFullscreenModes() {
        DisplayMode[] adisplaymode = Config.getFullscreenDisplayModes();
        String[] astring = new String[adisplaymode.length];
        int i = 0;
        while (i < adisplaymode.length) {
            String s;
            DisplayMode displaymode = adisplaymode[i];
            astring[i] = s = "" + displaymode.getWidth() + "x" + displaymode.getHeight();
            ++i;
        }
        return astring;
    }

    public static DisplayMode getDisplayMode(Dimension p_getDisplayMode_0_) throws LWJGLException {
        DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
        int i = 0;
        while (i < adisplaymode.length) {
            DisplayMode displaymode = adisplaymode[i];
            if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height) {
                if (desktopDisplayMode == null) return displaymode;
                if (displaymode.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == desktopDisplayMode.getFrequency()) {
                    return displaymode;
                }
            }
            ++i;
        }
        return desktopDisplayMode;
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

    public static void checkGlError(String p_checkGlError_0_) {
        int i = GL11.glGetError();
        if (i == 0) return;
        String s = GLU.gluErrorString((int)i);
        Config.error("OpenGlError: " + i + " (" + s + "), at: " + p_checkGlError_0_);
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
        if (Config.gameSettings.ofConnectedTextures == 3) return false;
        return true;
    }

    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }

    public static boolean isConnectedTexturesFancy() {
        if (Config.gameSettings.ofConnectedTextures != 2) return false;
        return true;
    }

    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }

    public static boolean isTranslucentBlocksFancy() {
        if (Config.gameSettings.ofTranslucentBlocks == 0) {
            boolean bl = Config.gameSettings.fancyGraphics;
            return bl;
        }
        if (Config.gameSettings.ofTranslucentBlocks != 2) return false;
        return true;
    }

    public static String[] readLines(File p_readLines_0_) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
        InputStreamReader inputstreamreader = new InputStreamReader((InputStream)fileinputstream, "ASCII");
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        String s;
        while ((s = bufferedreader.readLine()) != null) {
            list.add(s);
        }
        return list.toArray(new String[list.size()]);
    }

    public static String readFile(File p_readFile_0_) throws IOException {
        FileInputStream fileinputstream = new FileInputStream(p_readFile_0_);
        return Config.readInputStream(fileinputstream, "ASCII");
    }

    public static String readInputStream(InputStream p_readInputStream_0_) throws IOException {
        return Config.readInputStream(p_readInputStream_0_, "ASCII");
    }

    public static String readInputStream(InputStream p_readInputStream_0_, String p_readInputStream_1_) throws IOException {
        InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        StringBuffer stringbuffer = new StringBuffer();
        String s;
        while ((s = bufferedreader.readLine()) != null) {
            stringbuffer.append(s);
            stringbuffer.append("\n");
        }
        return stringbuffer.toString();
    }

    public static GameSettings getGameSettings() {
        return gameSettings;
    }

    public static String getNewRelease() {
        return newRelease;
    }

    public static void setNewRelease(String p_setNewRelease_0_) {
        newRelease = p_setNewRelease_0_;
    }

    public static int compareRelease(String p_compareRelease_0_, String p_compareRelease_1_) {
        int j;
        String[] astring1;
        String s1;
        String[] astring = Config.splitRelease(p_compareRelease_0_);
        String s = astring[0];
        if (!s.equals(s1 = (astring1 = Config.splitRelease(p_compareRelease_1_))[0])) {
            return s.compareTo(s1);
        }
        int i = Config.parseInt(astring[1], -1);
        if (i != (j = Config.parseInt(astring1[1], -1))) {
            return i - j;
        }
        String s2 = astring[2];
        String s3 = astring1[2];
        return s2.compareTo(s3);
    }

    private static String[] splitRelease(String p_splitRelease_0_) {
        int i;
        if (p_splitRelease_0_ == null || p_splitRelease_0_.length() <= 0) return new String[]{"", "", ""};
        String s = p_splitRelease_0_.substring(0, 1);
        if (p_splitRelease_0_.length() <= 1) {
            return new String[]{s, "", ""};
        }
        for (i = 1; i < p_splitRelease_0_.length() && Character.isDigit(p_splitRelease_0_.charAt(i)); ++i) {
        }
        String s1 = p_splitRelease_0_.substring(1, i);
        if (i >= p_splitRelease_0_.length()) {
            return new String[]{s, s1, ""};
        }
        String s2 = p_splitRelease_0_.substring(i);
        return new String[]{s, s1, s2};
    }

    public static int intHash(int p_intHash_0_) {
        p_intHash_0_ = p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16;
        p_intHash_0_ += p_intHash_0_ << 3;
        p_intHash_0_ ^= p_intHash_0_ >> 4;
        p_intHash_0_ *= 668265261;
        p_intHash_0_ ^= p_intHash_0_ >> 15;
        return p_intHash_0_;
    }

    public static int getRandom(BlockPos p_getRandom_0_, int p_getRandom_1_) {
        int i = Config.intHash(p_getRandom_1_ + 37);
        i = Config.intHash(i + p_getRandom_0_.getX());
        i = Config.intHash(i + p_getRandom_0_.getZ());
        return Config.intHash(i + p_getRandom_0_.getY());
    }

    public static WorldServer getWorldServer() {
        if (minecraft == null) {
            return null;
        }
        WorldClient world = Config.minecraft.theWorld;
        if (world == null) {
            return null;
        }
        if (!minecraft.isIntegratedServerRunning()) {
            return null;
        }
        IntegratedServer integratedserver = minecraft.getIntegratedServer();
        if (integratedserver == null) {
            return null;
        }
        WorldProvider worldprovider = world.provider;
        if (worldprovider == null) {
            return null;
        }
        int i = worldprovider.getDimensionId();
        try {
            return integratedserver.worldServerForDimension(i);
        }
        catch (NullPointerException var5) {
            return null;
        }
    }

    public static int getAvailableProcessors() {
        return availableProcessors;
    }

    public static void updateAvailableProcessors() {
        availableProcessors = Runtime.getRuntime().availableProcessors();
    }

    public static boolean isSingleProcessor() {
        if (Config.getAvailableProcessors() > 1) return false;
        return true;
    }

    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }

    public static boolean isLazyChunkLoading() {
        if (!Config.isSingleProcessor()) {
            return false;
        }
        boolean bl = Config.gameSettings.ofLazyChunkLoading;
        return bl;
    }

    public static int getChunkViewDistance() {
        if (gameSettings != null) return Config.gameSettings.renderDistanceChunks;
        return 10;
    }

    public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
        if (p_equals_0_ == p_equals_1_) {
            return true;
        }
        if (p_equals_0_ == null) {
            return false;
        }
        boolean bl = p_equals_0_.equals(p_equals_1_);
        return bl;
    }

    public static void checkDisplaySettings() {
        if (Config.getAntialiasingLevel() <= 0) return;
        int i = Config.getAntialiasingLevel();
        DisplayMode displaymode = Display.getDisplayMode();
        Config.dbg("FSAA Samples: " + i);
        try {
            Display.destroy();
            Display.setDisplayMode((DisplayMode)displaymode);
            Display.create((PixelFormat)new PixelFormat().withDepthBits(24).withSamples(i));
            Display.setResizable((boolean)false);
            Display.setResizable((boolean)true);
            return;
        }
        catch (LWJGLException lwjglexception2) {
            Config.warn("Error setting FSAA: " + i + "x");
            lwjglexception2.printStackTrace();
            try {
                Display.setDisplayMode((DisplayMode)displaymode);
                Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
                Display.setResizable((boolean)false);
                Display.setResizable((boolean)true);
                return;
            }
            catch (LWJGLException lwjglexception1) {
                lwjglexception1.printStackTrace();
                try {
                    Display.setDisplayMode((DisplayMode)displaymode);
                    Display.create();
                    Display.setResizable((boolean)false);
                    Display.setResizable((boolean)true);
                    return;
                }
                catch (LWJGLException lwjglexception) {
                    lwjglexception.printStackTrace();
                }
            }
        }
    }

    private static ByteBuffer readIconImage(File p_readIconImage_0_) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        int[] nArray = aint;
        int n = nArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                bytebuffer.flip();
                return bytebuffer;
            }
            int i = nArray[n2];
            bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
            ++n2;
        }
    }

    public static void checkDisplayMode() {
        try {
            if (minecraft.isFullScreen()) {
                if (fullscreenModeChecked) {
                    return;
                }
                fullscreenModeChecked = true;
                desktopModeChecked = false;
                DisplayMode displaymode = Display.getDisplayMode();
                Dimension dimension = Config.getFullscreenDimension();
                if (dimension == null) {
                    return;
                }
                if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
                    return;
                }
                DisplayMode displaymode1 = Config.getDisplayMode(dimension);
                if (displaymode1 == null) {
                    return;
                }
                Display.setDisplayMode((DisplayMode)displaymode1);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = 1;
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = 1;
                }
                if (Config.minecraft.currentScreen != null) {
                    ScaledResolution scaledresolution = new ScaledResolution(minecraft);
                    int i = scaledresolution.getScaledWidth();
                    int j = scaledresolution.getScaledHeight();
                    Config.minecraft.currentScreen.setWorldAndResolution(minecraft, i, j);
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
                Config.updateFramebufferSize();
                Display.setFullscreen((boolean)true);
                Config.minecraft.gameSettings.updateVSync();
                GlStateManager.enableTexture2D();
                return;
            }
            if (desktopModeChecked) {
                return;
            }
            desktopModeChecked = true;
            fullscreenModeChecked = false;
            Config.minecraft.gameSettings.updateVSync();
            Display.update();
            GlStateManager.enableTexture2D();
            Display.setResizable((boolean)false);
            Display.setResizable((boolean)true);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void updateFramebufferSize() {
        minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer == null) return;
        Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
    }

    public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_) {
        if (p_addObjectToArray_0_ == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int i = p_addObjectToArray_0_.length;
        int j = i + 1;
        Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
        System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
        aobject[i] = p_addObjectToArray_1_;
        return aobject;
    }

    public static Object[] addObjectsToArray(Object[] p_addObjectsToArray_0_, Object[] p_addObjectsToArray_1_) {
        if (p_addObjectsToArray_0_ == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (p_addObjectsToArray_1_.length == 0) {
            return p_addObjectsToArray_0_;
        }
        int i = p_addObjectsToArray_0_.length;
        int j = i + p_addObjectsToArray_1_.length;
        Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
        System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
        System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
        return aobject;
    }

    public static boolean isCustomItems() {
        return false;
    }

    public static boolean isActing() {
        boolean flag = Config.isActingNow();
        long i = System.currentTimeMillis();
        if (flag) {
            lastActionTime = i;
            return true;
        }
        if (i - lastActionTime >= 100L) return false;
        return true;
    }

    private static boolean isActingNow() {
        if (Mouse.isButtonDown((int)0)) {
            return true;
        }
        boolean bl = Mouse.isButtonDown((int)1);
        return bl;
    }

    public static void drawFps() {
        Minecraft minecraftx = minecraft;
        int i = Minecraft.getDebugFPS();
        String s = Config.getUpdates(Config.minecraft.debug);
        int j = Config.minecraft.renderGlobal.getCountActiveRenderers();
        int k = Config.minecraft.renderGlobal.getCountEntitiesRendered();
        int l = Config.minecraft.renderGlobal.getCountTileEntitiesRendered();
        String s1 = "" + i + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
        Config.minecraft.fontRendererObj.drawString(s1, 2.0f, 2.0f, -2039584);
    }

    private static String getUpdates(String p_getUpdates_0_) {
        int i = p_getUpdates_0_.indexOf(40);
        if (i < 0) {
            return "";
        }
        int j = p_getUpdates_0_.indexOf(32, i);
        if (j < 0) {
            return "";
        }
        String string = p_getUpdates_0_.substring(i + 1, j);
        return string;
    }

    public static int getBitsOs() {
        String s = System.getenv("ProgramFiles(X86)");
        if (s == null) return 32;
        return 64;
    }

    public static int getBitsJre() {
        String[] astring = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            String s1 = System.getProperty(s);
            if (s1 != null && s1.contains("64")) {
                return 64;
            }
            ++i;
        }
        return 32;
    }

    public static boolean isNotify64BitJava() {
        return notify64BitJava;
    }

    public static void setNotify64BitJava(boolean p_setNotify64BitJava_0_) {
        notify64BitJava = p_setNotify64BitJava_0_;
    }

    public static boolean isConnectedModels() {
        return false;
    }

    public static String fillLeft(String p_fillLeft_0_, int p_fillLeft_1_, char p_fillLeft_2_) {
        if (p_fillLeft_0_ == null) {
            p_fillLeft_0_ = "";
        }
        if (p_fillLeft_0_.length() >= p_fillLeft_1_) {
            return p_fillLeft_0_;
        }
        StringBuffer stringbuffer = new StringBuffer(p_fillLeft_0_);
        while (stringbuffer.length() < p_fillLeft_1_ - p_fillLeft_0_.length()) {
            stringbuffer.append(p_fillLeft_2_);
        }
        return stringbuffer.toString() + p_fillLeft_0_;
    }

    public static String fillRight(String p_fillRight_0_, int p_fillRight_1_, char p_fillRight_2_) {
        if (p_fillRight_0_ == null) {
            p_fillRight_0_ = "";
        }
        if (p_fillRight_0_.length() >= p_fillRight_1_) {
            return p_fillRight_0_;
        }
        StringBuffer stringbuffer = new StringBuffer(p_fillRight_0_);
        while (stringbuffer.length() < p_fillRight_1_) {
            stringbuffer.append(p_fillRight_2_);
        }
        return stringbuffer.toString();
    }
}

