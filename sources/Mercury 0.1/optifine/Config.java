/*
 * Decompiled with CFR 0.145.
 */
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
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
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
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import optifine.DynamicLights;
import optifine.GuiMessage;
import optifine.ReflectorForge;
import optifine.VersionCheckThread;
import org.apache.commons.io.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import shadersmod.client.PropertyDefaultFastFancyOff;
import shadersmod.client.Shaders;

public class Config {
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.8";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "H6";
    public static final String VERSION = "OptiFine_1.8_HD_U_H6";
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
    private static DefaultResourcePack defaultResourcePack = null;
    private static ModelManager modelManager = null;
    private static PrintStream systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1f);

    public static String getVersion() {
        return VERSION;
    }

    public static String getVersionDebug() {
        StringBuffer sb2 = new StringBuffer(32);
        if (Config.isDynamicLights()) {
            sb2.append("DL: ");
            sb2.append(String.valueOf(DynamicLights.getCount()));
            sb2.append(", ");
        }
        sb2.append(VERSION);
        String shaderPack = Shaders.getShaderPackName();
        if (shaderPack != null) {
            sb2.append(", ");
            sb2.append(shaderPack);
        }
        return sb2.toString();
    }

    public static void initGameSettings(GameSettings settings) {
        if (gameSettings == null) {
            gameSettings = settings;
            minecraft = Minecraft.getMinecraft();
            desktopDisplayMode = Display.getDesktopDisplayMode();
            Config.updateAvailableProcessors();
            ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
        }
    }

    public static void initDisplay() {
        Config.checkInitialized();
        antialiasingLevel = Config.gameSettings.ofAaLevel;
        Config.checkDisplaySettings();
        Config.checkDisplayMode();
        minecraftThread = Thread.currentThread();
        Config.updateThreadPriorities();
        Shaders.startup(Minecraft.getMinecraft());
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
        Config.log("Build: " + Config.getBuild());
        Config.log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        Config.log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        Config.log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        Config.log("LWJGL: " + Sys.getVersion());
        openGlVersion = GL11.glGetString(7938);
        openGlRenderer = GL11.glGetString(7937);
        openGlVendor = GL11.glGetString(7936);
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
        int maxTexSize = Minecraft.getGLMaximumTextureSize();
        Config.dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
    }

    private static String getBuild() {
        InputStream e2;
        block3 : {
            try {
                e2 = Config.class.getResourceAsStream("/buildof.txt");
                if (e2 != null) break block3;
                return null;
            }
            catch (Exception var2) {
                Config.warn(var2.getClass().getName() + ": " + var2.getMessage());
                return null;
            }
        }
        String build = Config.readLines(e2)[0];
        return build;
    }

    public static boolean isFancyFogAvailable() {
        return fancyFogAvailable;
    }

    public static boolean isOcclusionAvailable() {
        return occlusionAvailable;
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
        if (Config.isSingleProcessor()) {
            if (Config.isSmoothWorld()) {
                minecraftThread.setPriority(10);
                Config.setThreadPriority("Server thread", 1);
            } else {
                minecraftThread.setPriority(5);
                Config.setThreadPriority("Server thread", 5);
            }
        } else {
            minecraftThread.setPriority(10);
            Config.setThreadPriority("Server thread", 5);
        }
    }

    private static void setThreadPriority(String prefix, int priority) {
        try {
            ThreadGroup e2 = Thread.currentThread().getThreadGroup();
            if (e2 == null) {
                return;
            }
            int num = (e2.activeCount() + 10) * 2;
            Thread[] ts2 = new Thread[num];
            e2.enumerate(ts2, false);
            for (int i2 = 0; i2 < ts2.length; ++i2) {
                Thread t2 = ts2[i2];
                if (t2 == null || !t2.getName().startsWith(prefix)) continue;
                t2.setPriority(priority);
            }
        }
        catch (Throwable var7) {
            Config.warn(String.valueOf(var7.getClass().getName()) + ": " + var7.getMessage());
        }
    }

    public static boolean isMinecraftThread() {
        return Thread.currentThread() == minecraftThread;
    }

    private static void startVersionCheckThread() {
        VersionCheckThread vct = new VersionCheckThread();
        vct.start();
    }

    public static boolean isMipmaps() {
        return Config.gameSettings.mipmapLevels > 0;
    }

    public static int getMipmapLevels() {
        return Config.gameSettings.mipmapLevels;
    }

    public static int getMipmapType() {
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9986;
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
        return 9986;
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

    public static void dbg(String s2) {
        systemOut.print("[OptiFine] ");
        systemOut.println(s2);
    }

    public static void warn(String s2) {
        systemOut.print("[OptiFine] [WARN] ");
        systemOut.println(s2);
    }

    public static void error(String s2) {
        systemOut.print("[OptiFine] [ERROR] ");
        systemOut.println(s2);
    }

    public static void log(String s2) {
        Config.dbg(s2);
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
        return Config.gameSettings.ofClouds != 0 ? Config.gameSettings.ofClouds == 2 : (Config.isShaders() && !Shaders.shaderPackClouds.isDefault() ? Shaders.shaderPackClouds.isFancy() : (texturePackClouds != 0 ? texturePackClouds == 2 : Config.gameSettings.fancyGraphics));
    }

    public static boolean isCloudsOff() {
        return Config.gameSettings.ofClouds != 0 ? Config.gameSettings.ofClouds == 3 : (Config.isShaders() && !Shaders.shaderPackClouds.isDefault() ? Shaders.shaderPackClouds.isOff() : (texturePackClouds != 0 ? texturePackClouds == 3 : false));
    }

    public static void updateTexturePackClouds() {
        texturePackClouds = 0;
        IResourceManager rm2 = Config.getResourceManager();
        if (rm2 != null) {
            try {
                InputStream e2 = rm2.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
                if (e2 == null) {
                    return;
                }
                Properties props = new Properties();
                props.load(e2);
                e2.close();
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
                if (cloudStr.equals("off")) {
                    texturePackClouds = 3;
                }
            }
            catch (Exception e2) {
                // empty catch block
            }
        }
    }

    public static void setModelManager(ModelManager modelManager) {
    }

    public static ModelManager getModelManager() {
        return modelManager;
    }

    public static boolean isTreesFancy() {
        return Config.gameSettings.ofTrees == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofTrees != 1;
    }

    public static boolean isTreesSmart() {
        return Config.gameSettings.ofTrees == 4;
    }

    public static boolean isCullFacesLeaves() {
        return Config.gameSettings.ofTrees == 0 ? !Config.gameSettings.fancyGraphics : Config.gameSettings.ofTrees == 4;
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

    public static double limit(double val, double min, double max) {
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

    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }

    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }

    private static Method getMethod(Class cls, String methodName, Object[] params) {
        Method[] methods = cls.getMethods();
        for (int i2 = 0; i2 < methods.length; ++i2) {
            Method m2 = methods[i2];
            if (!m2.getName().equals(methodName) || m2.getParameterTypes().length != params.length) continue;
            return m2;
        }
        Config.warn("No method found for: " + cls.getName() + "." + methodName + "(" + Config.arrayToString(params) + ")");
        return null;
    }

    public static String arrayToString(Object[] arr2) {
        if (arr2 == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr2.length * 5);
        for (int i2 = 0; i2 < arr2.length; ++i2) {
            Object obj = arr2[i2];
            if (i2 > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(obj));
        }
        return buf.toString();
    }

    public static String arrayToString(int[] arr2) {
        if (arr2 == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr2.length * 5);
        for (int i2 = 0; i2 < arr2.length; ++i2) {
            int x2 = arr2[i2];
            if (i2 > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(x2));
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
            IResource e2 = Config.getResource(location);
            return e2 != null;
        }
        catch (IOException var2) {
            return false;
        }
    }

    public static boolean hasResource(IResourceManager resourceManager, ResourceLocation location) {
        try {
            IResource e2 = resourceManager.getResource(location);
            return e2 != null;
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
        if (rep.getResourcePackInstance() != null) {
            list.add(rep.getResourcePackInstance());
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

    public static DefaultResourcePack getDefaultResourcePack() {
        if (defaultResourcePack == null) {
            ResourcePackRepository var5;
            Minecraft mc2 = Minecraft.getMinecraft();
            try {
                Field[] repository = mc2.getClass().getDeclaredFields();
                for (int i2 = 0; i2 < repository.length; ++i2) {
                    Field field = repository[i2];
                    if (field.getType() != DefaultResourcePack.class) continue;
                    field.setAccessible(true);
                    defaultResourcePack = (DefaultResourcePack)field.get(mc2);
                    break;
                }
            }
            catch (Exception var4) {
                Config.warn("Error getting default resource pack: " + var4.getClass().getName() + ": " + var4.getMessage());
            }
            if (defaultResourcePack == null && (var5 = mc2.getResourcePackRepository()) != null) {
                defaultResourcePack = (DefaultResourcePack)var5.rprDefaultResourcePack;
            }
        }
        return defaultResourcePack;
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation loc) {
        IResourcePack rp2 = Config.getDefiningResourcePack(loc);
        return rp2 == Config.getDefaultResourcePack();
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation loc) {
        IResourcePack[] rps = Config.getResourcePacks();
        for (int i2 = rps.length - 1; i2 >= 0; --i2) {
            IResourcePack rp2 = rps[i2];
            if (!rp2.resourceExists(loc)) continue;
            return rp2;
        }
        if (Config.getDefaultResourcePack().resourceExists(loc)) {
            return Config.getDefaultResourcePack();
        }
        return null;
    }

    public static RenderGlobal getRenderGlobal() {
        return minecraft == null ? null : Config.minecraft.renderGlobal;
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
        return Config.gameSettings.ofVignette == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofVignette == 2;
    }

    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }

    public static void sleep(long ms2) {
        try {
            Thread.currentThread();
            Thread.sleep(ms2);
        }
        catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }

    public static boolean isTimeDayOnly() {
        return Config.gameSettings.ofTime == 1;
    }

    public static boolean isTimeDefault() {
        return Config.gameSettings.ofTime == 0;
    }

    public static boolean isTimeNightOnly() {
        return Config.gameSettings.ofTime == 2;
    }

    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }

    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }

    public static boolean isAnisotropicFiltering() {
        return Config.getAnisotropicFilterLevel() > 1;
    }

    public static int getAntialiasingLevel() {
        return antialiasingLevel;
    }

    public static boolean isAntialiasing() {
        return Config.getAntialiasingLevel() > 0;
    }

    public static boolean isAntialiasingConfigured() {
        return Config.getGameSettings().ofAaLevel > 0;
    }

    public static boolean isMultiTexture() {
        return Config.getAnisotropicFilterLevel() > 1 ? true : Config.getAntialiasingLevel() > 0;
    }

    public static boolean between(int val, int min, int max) {
        return val >= min && val <= max;
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
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Integer.parseInt(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static float parseFloat(String str, float defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Float.parseFloat(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static boolean parseBoolean(String str, boolean defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Boolean.parseBoolean(str);
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
            DisplayMode[] e2 = Display.getAvailableDisplayModes();
            ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
            for (int fsModes = 0; fsModes < e2.length; ++fsModes) {
                DisplayMode comp = e2[fsModes];
                if (desktopDisplayMode != null && (comp.getBitsPerPixel() != desktopDisplayMode.getBitsPerPixel() || comp.getFrequency() != desktopDisplayMode.getFrequency())) continue;
                list.add(comp);
            }
            DisplayMode[] var5 = list.toArray(new DisplayMode[list.size()]);
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
        for (int i2 = 0; i2 < modes.length; ++i2) {
            String name;
            DisplayMode mode = modes[i2];
            names[i2] = name = mode.getWidth() + "x" + mode.getHeight();
        }
        return names;
    }

    public static DisplayMode getDisplayMode(Dimension dim) throws LWJGLException {
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        for (int i2 = 0; i2 < modes.length; ++i2) {
            DisplayMode dm2 = modes[i2];
            if (dm2.getWidth() != dim.width || dm2.getHeight() != dim.height || desktopDisplayMode != null && (dm2.getBitsPerPixel() != desktopDisplayMode.getBitsPerPixel() || dm2.getFrequency() != desktopDisplayMode.getFrequency())) continue;
            return dm2;
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
        int i2 = GL11.glGetError();
        if (i2 != 0) {
            String text = GLU.gluErrorString(i2);
            Config.error("OpenGlError: " + i2 + " (" + text + "), at: " + loc);
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
        return GameSettings.ofFastRender;
    }

    public static boolean isTranslucentBlocksFancy() {
        return Config.gameSettings.ofTranslucentBlocks == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofTranslucentBlocks == 2;
    }

    public static boolean isShaders() {
        return Shaders.shaderPackLoaded;
    }

    public static String[] readLines(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return Config.readLines(fis);
    }

    public static String[] readLines(InputStream is2) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        InputStreamReader isr = new InputStreamReader(is2, "ASCII");
        BufferedReader br2 = new BufferedReader(isr);
        do {
            String lines;
            if ((lines = br2.readLine()) == null) {
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

    public static String readInputStream(InputStream in2) throws IOException {
        return Config.readInputStream(in2, "ASCII");
    }

    public static String readInputStream(InputStream in2, String encoding) throws IOException {
        InputStreamReader inr = new InputStreamReader(in2, encoding);
        BufferedReader br2 = new BufferedReader(inr);
        StringBuffer sb2 = new StringBuffer();
        String line;
        while ((line = br2.readLine()) != null) {
            sb2.append(line);
            sb2.append("\n");
        }
        return sb2.toString();
    }

    public static byte[] readAll(InputStream is2) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        do {
            int bytes;
            if ((bytes = is2.read(buf)) < 0) {
                is2.close();
                byte[] bytes1 = baos.toByteArray();
                return bytes1;
            }
            baos.write(buf, 0, bytes);
        } while (true);
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
        String[] rels2;
        String branch2;
        int rev2;
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
        if (!suf1.equals(suf2)) {
            if (suf1.isEmpty()) {
                return 1;
            }
            if (suf2.isEmpty()) {
                return -1;
            }
        }
        return suf1.compareTo(suf2);
    }

    private static String[] splitRelease(String relStr) {
        if (relStr != null && relStr.length() > 0) {
            Pattern p2 = Pattern.compile("([A-Z])([0-9]+)(.*)");
            Matcher m2 = p2.matcher(relStr);
            if (!m2.matches()) {
                return new String[]{"", "", ""};
            }
            String branch = Config.normalize(m2.group(1));
            String revision = Config.normalize(m2.group(2));
            String suffix = Config.normalize(m2.group(3));
            return new String[]{branch, revision, suffix};
        }
        return new String[]{"", "", ""};
    }

    public static int intHash(int x2) {
        x2 = x2 ^ 61 ^ x2 >> 16;
        x2 += x2 << 3;
        x2 ^= x2 >> 4;
        x2 *= 668265261;
        x2 ^= x2 >> 15;
        return x2;
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
        WorldClient world = Config.minecraft.theWorld;
        if (world == null) {
            return null;
        }
        if (!minecraft.isIntegratedServerRunning()) {
            return null;
        }
        IntegratedServer is2 = minecraft.getIntegratedServer();
        if (is2 == null) {
            return null;
        }
        WorldProvider wp = world.provider;
        if (wp == null) {
            return null;
        }
        int wd2 = wp.getDimensionId();
        try {
            WorldServer e2 = is2.worldServerForDimension(wd2);
            return e2;
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

    public static boolean isDynamicFov() {
        return Config.gameSettings.ofDynamicFov;
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

    public static String normalize(String s2) {
        return s2 == null ? "" : s2;
    }

    public static void checkDisplaySettings() {
        block14 : {
            int samples = Config.getAntialiasingLevel();
            if (samples > 0) {
                DisplayMode displayMode = Display.getDisplayMode();
                Config.dbg("FSAA Samples: " + samples);
                try {
                    Display.destroy();
                    Display.setDisplayMode(displayMode);
                    Display.create(new PixelFormat().withDepthBits(24).withSamples(samples));
                    Display.setResizable(false);
                    Display.setResizable(true);
                }
                catch (LWJGLException var15) {
                    Config.warn("Error setting FSAA: " + samples + "x");
                    var15.printStackTrace();
                    try {
                        Display.setDisplayMode(displayMode);
                        Display.create(new PixelFormat().withDepthBits(24));
                        Display.setResizable(false);
                        Display.setResizable(true);
                    }
                    catch (LWJGLException var14) {
                        var14.printStackTrace();
                        try {
                            Display.setDisplayMode(displayMode);
                            Display.create();
                            Display.setResizable(false);
                            Display.setResizable(true);
                        }
                        catch (LWJGLException var13) {
                            var13.printStackTrace();
                        }
                    }
                }
                if (!Minecraft.isRunningOnMac && Config.getDefaultResourcePack() != null) {
                    InputStream var2 = null;
                    InputStream var3 = null;
                    try {
                        try {
                            var2 = Config.getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
                            var3 = Config.getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
                            if (var2 != null && var3 != null) {
                                Display.setIcon(new ByteBuffer[]{Config.readIconImage(var2), Config.readIconImage(var3)});
                            }
                        }
                        catch (IOException var11) {
                            Config.warn("Error setting window icon: " + var11.getClass().getName() + ": " + var11.getMessage());
                            IOUtils.closeQuietly(var2);
                            IOUtils.closeQuietly(var3);
                            break block14;
                        }
                    }
                    catch (Throwable throwable) {
                        IOUtils.closeQuietly(var2);
                        IOUtils.closeQuietly(var3);
                        throw throwable;
                    }
                    IOUtils.closeQuietly(var2);
                    IOUtils.closeQuietly(var3);
                }
            }
        }
    }

    private static ByteBuffer readIconImage(InputStream is2) throws IOException {
        BufferedImage var2 = ImageIO.read(is2);
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
                DisplayMode e2 = Display.getDisplayMode();
                Dimension dim = Config.getFullscreenDimension();
                if (dim == null) {
                    return;
                }
                if (e2.getWidth() == dim.width && e2.getHeight() == dim.height) {
                    return;
                }
                DisplayMode newMode = Config.getDisplayMode(dim);
                if (newMode == null) {
                    return;
                }
                Display.setDisplayMode(newMode);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = 1;
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = 1;
                }
                if (Config.minecraft.currentScreen != null) {
                    ScaledResolution sr2 = new ScaledResolution(minecraft, Config.minecraft.displayWidth, Config.minecraft.displayHeight);
                    int sw2 = sr2.getScaledWidth();
                    int sh2 = sr2.getScaledHeight();
                    Config.minecraft.currentScreen.setWorldAndResolution(minecraft, sw2, sh2);
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
                Config.updateFramebufferSize();
                Display.setFullscreen(true);
                Config.minecraft.gameSettings.updateVSync();
                GlStateManager.func_179098_w();
            } else {
                if (desktopModeChecked) {
                    return;
                }
                desktopModeChecked = true;
                fullscreenModeChecked = false;
                Config.minecraft.gameSettings.updateVSync();
                Display.update();
                GlStateManager.func_179098_w();
                Display.setResizable(false);
                Display.setResizable(true);
            }
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    public static void updateFramebufferSize() {
        minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }

    public static Object[] addObjectToArray(Object[] arr2, Object obj) {
        if (arr2 == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int arrLen = arr2.length;
        int newLen = arrLen + 1;
        Object[] newArr = (Object[])Array.newInstance(arr2.getClass().getComponentType(), newLen);
        System.arraycopy(arr2, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }

    public static Object[] addObjectToArray(Object[] arr2, Object obj, int index) {
        ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(arr2));
        list.add(index, obj);
        Object[] newArr = (Object[])Array.newInstance(arr2.getClass().getComponentType(), list.size());
        return list.toArray(newArr);
    }

    public static Object[] addObjectsToArray(Object[] arr2, Object[] objs) {
        if (arr2 == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objs.length == 0) {
            return arr2;
        }
        int arrLen = arr2.length;
        int newLen = arrLen + objs.length;
        Object[] newArr = (Object[])Array.newInstance(arr2.getClass().getComponentType(), newLen);
        System.arraycopy(arr2, 0, newArr, 0, arrLen);
        System.arraycopy(objs, 0, newArr, arrLen, objs.length);
        return newArr;
    }

    public static boolean isCustomItems() {
        return Config.gameSettings.ofCustomItems;
    }

    public static void drawFps() {
        Minecraft var10000 = minecraft;
        int fps = Minecraft.func_175610_ah();
        String updates = Config.getUpdates(Config.minecraft.debug);
        int renderersActive = Config.minecraft.renderGlobal.getCountActiveRenderers();
        int entities = Config.minecraft.renderGlobal.getCountEntitiesRendered();
        int tileEntities = Config.minecraft.renderGlobal.getCountTileEntitiesRendered();
        String fpsStr = fps + " fps, C: " + renderersActive + ", E: " + entities + "+" + tileEntities + ", U: " + updates;
        Config.minecraft.fontRendererObj.drawString(fpsStr, 2, 2, -2039584);
    }

    private static String getUpdates(String str) {
        int pos1 = str.indexOf(40);
        if (pos1 < 0) {
            return "";
        }
        int pos2 = str.indexOf(32, pos1);
        return pos2 < 0 ? "" : str.substring(pos1 + 1, pos2);
    }

    public static int getBitsOs() {
        String progFiles86 = System.getenv("ProgramFiles(X86)");
        return progFiles86 != null ? 64 : 32;
    }

    public static int getBitsJre() {
        String[] propNames = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        for (int i2 = 0; i2 < propNames.length; ++i2) {
            String propName = propNames[i2];
            String propVal = System.getProperty(propName);
            if (propVal == null || !propVal.contains("64")) continue;
            return 64;
        }
        return 32;
    }

    public static boolean isNotify64BitJava() {
        return notify64BitJava;
    }

    public static void setNotify64BitJava(boolean flag) {
        notify64BitJava = flag;
    }

    public static boolean isConnectedModels() {
        return false;
    }

    public static String fillLeft(String s2, int len, char fillChar) {
        if (s2 == null) {
            s2 = "";
        }
        if (s2.length() >= len) {
            return s2;
        }
        StringBuffer buf = new StringBuffer(s2);
        while (buf.length() < len - s2.length()) {
            buf.append(fillChar);
        }
        return String.valueOf(buf.toString()) + s2;
    }

    public static String fillRight(String s2, int len, char fillChar) {
        if (s2 == null) {
            s2 = "";
        }
        if (s2.length() >= len) {
            return s2;
        }
        StringBuffer buf = new StringBuffer(s2);
        while (buf.length() < len) {
            buf.append(fillChar);
        }
        return buf.toString();
    }

    public static void showGuiMessage(String line1, String line2) {
        GuiMessage gui = new GuiMessage(Config.minecraft.currentScreen, line1, line2);
        minecraft.displayGuiScreen(gui);
    }

    public static int[] addIntToArray(int[] intArray, int intValue) {
        return Config.addIntsToArray(intArray, new int[]{intValue});
    }

    public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
        if (intArray != null && copyFrom != null) {
            int arrLen = intArray.length;
            int newLen = arrLen + copyFrom.length;
            int[] newArray = new int[newLen];
            System.arraycopy(intArray, 0, newArray, 0, arrLen);
            for (int index = 0; index < copyFrom.length; ++index) {
                newArray[index + arrLen] = copyFrom[index];
            }
            return newArray;
        }
        throw new NullPointerException("The given array is NULL");
    }

    public static DynamicTexture getMojangLogoTexture(DynamicTexture texDefault) {
        try {
            ResourceLocation e2 = new ResourceLocation("textures/gui/title/mojang.png");
            InputStream in2 = Config.getResourceStream(e2);
            if (in2 == null) {
                return texDefault;
            }
            BufferedImage bi2 = ImageIO.read(in2);
            if (bi2 == null) {
                return texDefault;
            }
            DynamicTexture dt2 = new DynamicTexture(bi2);
            return dt2;
        }
        catch (Exception var5) {
            Config.warn(String.valueOf(var5.getClass().getName()) + ": " + var5.getMessage());
            return texDefault;
        }
    }

    public static void writeFile(File file, String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = str.getBytes("ASCII");
        fos.write(bytes);
        fos.close();
    }

    public static TextureMap getTextureMap() {
        return Config.getMinecraft().getTextureMapBlocks();
    }

    public static boolean isDynamicLights() {
        return Config.gameSettings.ofDynamicLights != 3;
    }

    public static boolean isDynamicLightsFast() {
        return Config.gameSettings.ofDynamicLights == 1;
    }

    public static boolean isDynamicHandLight() {
        return !Config.isDynamicLights() ? false : (Config.isShaders() ? Shaders.isDynamicHandLight() : true);
    }

}

