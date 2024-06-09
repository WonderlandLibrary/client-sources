/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

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
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.VersionCheckThread;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Config {
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.8";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "B2";
    public static final String VERSION = "OptiFine_1.8_HD_U_B2";
    private static String newRelease = null;
    public static String openGlVersion = null;
    public static String openGlRenderer = null;
    public static String openGlVendor = null;
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

    public static void initGameSettings(GameSettings settings) {
        gameSettings = settings;
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
        if (!initialized && Display.isCreated()) {
            initialized = true;
            Config.checkOpenGlCaps();
            Config.startVersionCheckThread();
        }
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
        if (!GLContext.getCapabilities().GL_NV_fog_distance) {
            Config.log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!GLContext.getCapabilities().GL_ARB_occlusion_query) {
            Config.log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        int maxTexSize = Minecraft.getGLMaximumTextureSize();
        Config.dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
    }

    public static boolean isFancyFogAvailable() {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }

    public static boolean isOcclusionAvailable() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }

    public static String getOpenGlVersionString() {
        int ver = Config.getOpenGlVersion();
        String verStr = ver / 10 + "." + ver % 10;
        return verStr;
    }

    private static int getOpenGlVersion() {
        return !GLContext.getCapabilities().OpenGL11 ? 10 : (!GLContext.getCapabilities().OpenGL12 ? 11 : (!GLContext.getCapabilities().OpenGL13 ? 12 : (!GLContext.getCapabilities().OpenGL14 ? 13 : (!GLContext.getCapabilities().OpenGL15 ? 14 : (!GLContext.getCapabilities().OpenGL20 ? 15 : (!GLContext.getCapabilities().OpenGL21 ? 20 : (!GLContext.getCapabilities().OpenGL30 ? 21 : (!GLContext.getCapabilities().OpenGL31 ? 30 : (!GLContext.getCapabilities().OpenGL32 ? 31 : (!GLContext.getCapabilities().OpenGL33 ? 32 : (!GLContext.getCapabilities().OpenGL40 ? 33 : 40)))))))))));
    }

    public static void updateThreadPriorities() {
        Config.updateAvailableProcessors();
        boolean ELEVATED_PRIORITY = true;
        if (Config.getAvailableProcessors() <= 1 && !Config.isSmoothWorld()) {
            minecraftThread.setPriority(5);
            Config.setThreadPriority("Server thread", 5);
        } else {
            minecraftThread.setPriority(10);
            Config.setThreadPriority("Server thread", 1);
        }
    }

    private static void setThreadPriority(String prefix, int priority) {
        try {
            ThreadGroup e = Thread.currentThread().getThreadGroup();
            if (e == null) {
                return;
            }
            int num = (e.activeCount() + 10) * 2;
            Thread[] ts = new Thread[num];
            e.enumerate(ts, false);
            for (int i = 0; i < ts.length; ++i) {
                Thread t = ts[i];
                if (t == null || !t.getName().startsWith(prefix)) continue;
                t.setPriority(priority);
            }
        }
        catch (Throwable var7) {
            Config.dbg(String.valueOf(var7.getClass().getName()) + ": " + var7.getMessage());
        }
    }

    public static boolean isMinecraftThread() {
        return Thread.currentThread() == minecraftThread;
    }

    private static void startVersionCheckThread() {
        VersionCheckThread vct = new VersionCheckThread();
        vct.start();
    }

    public static int getMipmapType() {
        if (gameSettings == null) {
            return DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9984;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (Config.isMultiTexture()) {
                    return 9985;
                }
                return 9986;
            }
            case 3: {
                if (Config.isMultiTexture()) {
                    return 9987;
                }
                return 9986;
            }
        }
        return 9984;
    }

    public static boolean isUseAlphaFunc() {
        float alphaFuncLevel = Config.getAlphaFuncLevel();
        return alphaFuncLevel > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5f;
    }

    public static float getAlphaFuncLevel() {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy() {
        return !Config.isFancyFogAvailable() ? false : Config.gameSettings.ofFogType == 2;
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

    public static boolean isLoadChunksFar() {
        return Config.gameSettings.ofLoadFar;
    }

    public static int getPreloadedChunks() {
        return Config.gameSettings.ofPreloadedChunks;
    }

    public static void dbg(String s) {
        systemOut.print("[OptiFine] ");
        systemOut.println(s);
    }

    public static void warn(String s) {
        systemOut.print("[OptiFine] [WARN] ");
        systemOut.println(s);
    }

    public static void error(String s) {
        systemOut.print("[OptiFine] [ERROR] ");
        systemOut.println(s);
    }

    public static void log(String s) {
        Config.dbg(s);
    }

    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }

    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }

    public static boolean isRainFancy() {
        return Config.gameSettings.ofRain == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofRain == 2;
    }

    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }

    public static boolean isCloudsFancy() {
        return Config.gameSettings.ofClouds != 0 ? Config.gameSettings.ofClouds == 2 : (texturePackClouds != 0 ? texturePackClouds == 2 : Config.gameSettings.fancyGraphics);
    }

    public static boolean isCloudsOff() {
        return Config.gameSettings.ofClouds == 3;
    }

    public static void updateTexturePackClouds() {
        texturePackClouds = 0;
        IResourceManager rm = Config.getResourceManager();
        if (rm != null) {
            try {
                InputStream e = rm.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
                if (e == null) {
                    return;
                }
                Properties props = new Properties();
                props.load(e);
                e.close();
                String cloudStr = props.getProperty("clouds");
                if (cloudStr == null) {
                    return;
                }
                Config.dbg("Texture pack clouds: " + cloudStr);
                cloudStr = cloudStr.toLowerCase();
                if (cloudStr.equals("fast")) {
                    texturePackClouds = 1;
                }
                if (cloudStr.equals("fancy")) {
                    texturePackClouds = 2;
                }
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    public static boolean isTreesFancy() {
        return Config.gameSettings.ofTrees == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofTrees == 2;
    }

    public static boolean isDroppedItemsFancy() {
        return Config.gameSettings.ofDroppedItems == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofDroppedItems == 2;
    }

    public static int limit(int val, int min, int max) {
        return val < min ? min : (val > max ? max : val);
    }

    public static float limit(float val, float min, float max) {
        return val < min ? min : (val > max ? max : val);
    }

    public static float limitTo1(float val) {
        return val < 0.0f ? 0.0f : (val > 1.0f ? 1.0f : val);
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

    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }

    private static Method getMethod(Class cls, String methodName, Object[] params) {
        Method[] methods = cls.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (!m.getName().equals(methodName) || m.getParameterTypes().length != params.length) continue;
            return m;
        }
        Config.warn("No method found for: " + cls.getName() + "." + methodName + "(" + Config.arrayToString(params) + ")");
        return null;
    }

    public static String arrayToString(Object[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            Object obj = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(obj));
        }
        return buf.toString();
    }

    public static String arrayToString(int[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            int x = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(x));
        }
        return buf.toString();
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

    public static InputStream getResourceStream(ResourceLocation location) throws IOException {
        return Config.getResourceStream(minecraft.getResourceManager(), location);
    }

    public static InputStream getResourceStream(IResourceManager resourceManager, ResourceLocation location) throws IOException {
        IResource res = resourceManager.getResource(location);
        return res == null ? null : res.getInputStream();
    }

    public static IResource getResource(ResourceLocation location) throws IOException {
        return minecraft.getResourceManager().getResource(location);
    }

    public static boolean hasResource(ResourceLocation location) {
        try {
            IResource e = Config.getResource(location);
            return e != null;
        }
        catch (IOException var2) {
            return false;
        }
    }

    public static boolean hasResource(IResourceManager resourceManager, ResourceLocation location) {
        try {
            IResource e = resourceManager.getResource(location);
            return e != null;
        }
        catch (IOException var3) {
            return false;
        }
    }

    public static IResourcePack[] getResourcePacks() {
        ResourcePackRepository rep = minecraft.getResourcePackRepository();
        List entries = rep.getRepositoryEntries();
        ArrayList<IResourcePack> list = new ArrayList<IResourcePack>();
        for (ResourcePackRepository.Entry entry : entries) {
            list.add(entry.getResourcePack());
        }
        IResourcePack[] rps1 = list.toArray(new IResourcePack[list.size()]);
        return rps1;
    }

    public static String getResourcePackNames() {
        if (minecraft == null) {
            return "";
        }
        if (minecraft.getResourcePackRepository() == null) {
            return "";
        }
        IResourcePack[] rps = Config.getResourcePacks();
        if (rps.length <= 0) {
            return Config.getDefaultResourcePack().getPackName();
        }
        Object[] names = new String[rps.length];
        for (int nameStr = 0; nameStr < rps.length; ++nameStr) {
            names[nameStr] = rps[nameStr].getPackName();
        }
        String var3 = Config.arrayToString(names);
        return var3;
    }

    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation loc) {
        IResourcePack rp = Config.getDefiningResourcePack(loc);
        return rp == Config.getDefaultResourcePack();
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation loc) {
        IResourcePack[] rps = Config.getResourcePacks();
        for (int i = rps.length - 1; i >= 0; --i) {
            IResourcePack rp = rps[i];
            if (!rp.resourceExists(loc)) continue;
            return rp;
        }
        if (Config.getDefaultResourcePack().resourceExists(loc)) {
            return Config.getDefaultResourcePack();
        }
        return null;
    }

    public static RenderGlobal getRenderGlobal() {
        return minecraft == null ? null : Minecraft.renderGlobal;
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

    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }

    public static void sleep(long ms) {
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        }
        catch (InterruptedException var3) {
            var3.printStackTrace();
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

    public static int getAntialiasingLevel() {
        return antialiasingLevel;
    }

    public static boolean between(int val, int min, int max) {
        return val >= min && val <= max;
    }

    public static boolean isMultiTexture() {
        return false;
    }

    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }

    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }

    public static Dimension getFullscreenDimension() {
        if (desktopDisplayMode == null) {
            return null;
        }
        if (gameSettings == null) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String dimStr = Config.gameSettings.ofFullscreenMode;
        if (dimStr.equals("Default")) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String[] dimStrs = Config.tokenize(dimStr, " x");
        return dimStrs.length < 2 ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(Config.parseInt(dimStrs[0], -1), Config.parseInt(dimStrs[1], -1));
    }

    public static int parseInt(String str, int defVal) {
        try {
            return str == null ? defVal : Integer.parseInt(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static float parseFloat(String str, float defVal) {
        try {
            return str == null ? defVal : Float.parseFloat(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static String[] tokenize(String str, String delim) {
        StringTokenizer tok = new StringTokenizer(str, delim);
        ArrayList<String> list = new ArrayList<String>();
        while (tok.hasMoreTokens()) {
            String strs = tok.nextToken();
            list.add(strs);
        }
        String[] strs1 = list.toArray(new String[list.size()]);
        return strs1;
    }

    public static DisplayMode getDesktopDisplayMode() {
        return desktopDisplayMode;
    }

    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            DisplayMode[] e = Display.getAvailableDisplayModes();
            ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
            for (int fsModes = 0; fsModes < e.length; ++fsModes) {
                DisplayMode comp = e[fsModes];
                if (desktopDisplayMode != null && (comp.getBitsPerPixel() != desktopDisplayMode.getBitsPerPixel() || comp.getFrequency() != desktopDisplayMode.getFrequency())) continue;
                list.add(comp);
            }
            DisplayMode[] var5 = list.toArray((T[])new DisplayMode[list.size()]);
            Comparator var6 = new Comparator(){

                public int compare(Object o1, Object o2) {
                    DisplayMode dm1 = (DisplayMode)o1;
                    DisplayMode dm2 = (DisplayMode)o2;
                    return dm1.getWidth() != dm2.getWidth() ? dm2.getWidth() - dm1.getWidth() : (dm1.getHeight() != dm2.getHeight() ? dm2.getHeight() - dm1.getHeight() : 0);
                }
            };
            Arrays.sort(var5, var6);
            return var5;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            return new DisplayMode[]{desktopDisplayMode};
        }
    }

    public static String[] getFullscreenModes() {
        DisplayMode[] modes = Config.getFullscreenDisplayModes();
        String[] names = new String[modes.length];
        for (int i = 0; i < modes.length; ++i) {
            String name;
            DisplayMode mode = modes[i];
            names[i] = name = mode.getWidth() + "x" + mode.getHeight();
        }
        return names;
    }

    public static DisplayMode getDisplayMode(Dimension dim) throws LWJGLException {
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        for (int i = 0; i < modes.length; ++i) {
            DisplayMode dm = modes[i];
            if (dm.getWidth() != dim.width || dm.getHeight() != dim.height || desktopDisplayMode != null && (dm.getBitsPerPixel() != desktopDisplayMode.getBitsPerPixel() || dm.getFrequency() != desktopDisplayMode.getFrequency())) continue;
            return dm;
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

    public static void checkGlError(String loc) {
        int i = GL11.glGetError();
        if (i != 0) {
            String text = GLU.gluErrorString((int)i);
            Config.error("OpenGlError: " + i + " (" + text + "), at: " + loc);
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
        return Config.gameSettings.ofTranslucentBlocks == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofTranslucentBlocks == 2;
    }

    public static String[] readLines(File file) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader((InputStream)fis, "ASCII");
        BufferedReader br = new BufferedReader(isr);
        do {
            String lines;
            if ((lines = br.readLine()) == null) {
                String[] lines1 = list.toArray(new String[list.size()]);
                return lines1;
            }
            list.add(lines);
        } while (true);
    }

    public static String readFile(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        return Config.readInputStream(fin, "ASCII");
    }

    public static String readInputStream(InputStream in) throws IOException {
        return Config.readInputStream(in, "ASCII");
    }

    public static String readInputStream(InputStream in, String encoding) throws IOException {
        InputStreamReader inr = new InputStreamReader(in, encoding);
        BufferedReader br = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static GameSettings getGameSettings() {
        return gameSettings;
    }

    public static String getNewRelease() {
        return newRelease;
    }

    public static void setNewRelease(String newRelease) {
    }

    public static int compareRelease(String rel1, String rel2) {
        int rev2;
        String[] rels2;
        String branch2;
        String[] rels1 = Config.splitRelease(rel1);
        String branch1 = rels1[0];
        if (!branch1.equals(branch2 = (rels2 = Config.splitRelease(rel2))[0])) {
            return branch1.compareTo(branch2);
        }
        int rev1 = Config.parseInt(rels1[1], -1);
        if (rev1 != (rev2 = Config.parseInt(rels2[1], -1))) {
            return rev1 - rev2;
        }
        String suf1 = rels1[2];
        String suf2 = rels2[2];
        return suf1.compareTo(suf2);
    }

    private static String[] splitRelease(String relStr) {
        if (relStr != null && relStr.length() > 0) {
            int pos;
            String branch = relStr.substring(0, 1);
            if (relStr.length() <= 1) {
                return new String[]{branch, "", ""};
            }
            for (pos = 1; pos < relStr.length() && Character.isDigit(relStr.charAt(pos)); ++pos) {
            }
            String revision = relStr.substring(1, pos);
            if (pos >= relStr.length()) {
                return new String[]{branch, revision, ""};
            }
            String suffix = relStr.substring(pos);
            return new String[]{branch, revision, suffix};
        }
        return new String[]{"", "", ""};
    }

    public static int intHash(int x) {
        x = x ^ 61 ^ x >> 16;
        x += x << 3;
        x ^= x >> 4;
        x *= 668265261;
        x ^= x >> 15;
        return x;
    }

    public static int getRandom(BlockPos blockPos, int face) {
        int rand = Config.intHash(face + 37);
        rand = Config.intHash(rand + blockPos.getX());
        rand = Config.intHash(rand + blockPos.getZ());
        rand = Config.intHash(rand + blockPos.getY());
        return rand;
    }

    public static WorldServer getWorldServer() {
        if (minecraft == null) {
            return null;
        }
        WorldClient world = Minecraft.theWorld;
        if (world == null) {
            return null;
        }
        if (!minecraft.isIntegratedServerRunning()) {
            return null;
        }
        IntegratedServer is = minecraft.getIntegratedServer();
        if (is == null) {
            return null;
        }
        WorldProvider wp = world.provider;
        if (wp == null) {
            return null;
        }
        int wd = wp.getDimensionId();
        try {
            WorldServer e = is.worldServerForDimension(wd);
            return e;
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
        return Config.getAvailableProcessors() <= 1;
    }

    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }

    public static boolean isLazyChunkLoading() {
        return !Config.isSingleProcessor() ? false : Config.gameSettings.ofLazyChunkLoading;
    }

    public static int getChunkViewDistance() {
        if (gameSettings == null) {
            return 10;
        }
        int chunkDistance = Config.gameSettings.renderDistanceChunks;
        return chunkDistance;
    }

    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 ? true : (o1 == null ? false : o1.equals(o2));
    }

    public static void checkDisplaySettings() {
        if (Config.getAntialiasingLevel() > 0) {
            int samples = Config.getAntialiasingLevel();
            DisplayMode displayMode = Display.getDisplayMode();
            Config.dbg("FSAA Samples: " + samples);
            try {
                Display.destroy();
                Display.setDisplayMode((DisplayMode)displayMode);
                Display.create((PixelFormat)new PixelFormat().withDepthBits(24).withSamples(samples));
            }
            catch (LWJGLException var9) {
                Config.warn("Error setting FSAA: " + samples + "x");
                var9.printStackTrace();
                try {
                    Display.setDisplayMode((DisplayMode)displayMode);
                    Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
                }
                catch (LWJGLException var8) {
                    var8.printStackTrace();
                    try {
                        Display.setDisplayMode((DisplayMode)displayMode);
                        Display.create();
                    }
                    catch (LWJGLException var7) {
                        var7.printStackTrace();
                    }
                }
            }
            if (Util.getOSType() != Util.EnumOS.OSX) {
                try {
                    File e = new File(Config.minecraft.mcDataDir, "assets");
                    ByteBuffer bufIcon16 = Config.readIconImage(new File(e, "/icons/icon_16x16.png"));
                    ByteBuffer bufIcon32 = Config.readIconImage(new File(e, "/icons/icon_32x32.png"));
                    ByteBuffer[] buf = new ByteBuffer[]{bufIcon16, bufIcon32};
                    Display.setIcon((ByteBuffer[])buf);
                }
                catch (IOException var6) {
                    Config.dbg(String.valueOf(var6.getClass().getName()) + ": " + var6.getMessage());
                }
            }
        }
    }

    private static ByteBuffer readIconImage(File par1File) throws IOException {
        BufferedImage var2 = ImageIO.read(par1File);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        int[] var5 = var3;
        int var6 = var3.length;
        for (int var7 = 0; var7 < var6; ++var7) {
            int var8 = var5[var7];
            var4.putInt(var8 << 8 | var8 >> 24 & 255);
        }
        var4.flip();
        return var4;
    }

    public static void checkDisplayMode() {
        try {
            if (minecraft.isFullScreen()) {
                if (fullscreenModeChecked) {
                    return;
                }
                fullscreenModeChecked = true;
                desktopModeChecked = false;
                DisplayMode e = Display.getDisplayMode();
                Dimension dim = Config.getFullscreenDimension();
                if (dim == null) {
                    return;
                }
                if (e.getWidth() == dim.width && e.getHeight() == dim.height) {
                    return;
                }
                DisplayMode newMode = Config.getDisplayMode(dim);
                if (newMode == null) {
                    return;
                }
                Display.setDisplayMode((DisplayMode)newMode);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = 1;
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = 1;
                }
                if (Minecraft.currentScreen != null) {
                    ScaledResolution sr = new ScaledResolution(minecraft, Config.minecraft.displayWidth, Config.minecraft.displayHeight);
                    int sw = ScaledResolution.getScaledWidth();
                    int sh = ScaledResolution.getScaledHeight();
                    Minecraft.currentScreen.setWorldAndResolution(minecraft, sw, sh);
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
                Config.updateFramebufferSize();
                Display.setFullscreen((boolean)true);
                Config.minecraft.gameSettings.updateVSync();
                GlStateManager.bindTexture();
            } else {
                if (desktopModeChecked) {
                    return;
                }
                desktopModeChecked = true;
                fullscreenModeChecked = false;
                Config.minecraft.gameSettings.updateVSync();
                Display.update();
                GlStateManager.bindTexture();
            }
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    private static void updateFramebufferSize() {
        minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int arrLen = arr.length;
        int newLen = arrLen + 1;
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }

    public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objs.length == 0) {
            return arr;
        }
        int arrLen = arr.length;
        int newLen = arrLen + objs.length;
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        System.arraycopy(objs, 0, newArr, arrLen, objs.length);
        return newArr;
    }

    public static boolean isCustomItems() {
        return false;
    }

    public static boolean isActing() {
        boolean acting = Config.isActingNow();
        long timeNowMs = System.currentTimeMillis();
        if (acting) {
            lastActionTime = timeNowMs;
            return true;
        }
        return timeNowMs - lastActionTime < 100L;
    }

    private static boolean isActingNow() {
        return Mouse.isButtonDown((int)0) ? true : Mouse.isButtonDown((int)1);
    }

}

