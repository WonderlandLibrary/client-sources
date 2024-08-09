/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
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
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.DynamicLights;
import net.optifine.GlErrors;
import net.optifine.VersionCheckThread;
import net.optifine.config.GlVersion;
import net.optifine.gui.GuiMessage;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;

public class Config {
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.16.5";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "G8";
    public static final String VERSION = "OptiFine_1.16.5_HD_U_G8";
    private static String build = null;
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
    private static int antialiasingLevel = 0;
    private static int availableProcessors = 0;
    public static boolean zoomMode = false;
    public static boolean zoomSmoothCamera = false;
    private static int texturePackClouds = 0;
    private static boolean fullscreenModeChecked = false;
    private static boolean desktopModeChecked = false;
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1f);
    private static final Logger LOGGER = LogManager.getLogger();
    public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
    private static String mcDebugLast = null;
    private static int fpsMinLast = 0;
    private static int chunkUpdatesLast = 0;
    private static AtlasTexture textureMapTerrain;
    private static long timeLastFrameMs;
    private static long averageFrameTimeMs;
    private static boolean showFrameTime;

    private Config() {
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getVersionDebug() {
        StringBuffer stringBuffer = new StringBuffer(32);
        if (Config.isDynamicLights()) {
            stringBuffer.append("DL: ");
            stringBuffer.append(String.valueOf(DynamicLights.getCount()));
            stringBuffer.append(", ");
        }
        stringBuffer.append(VERSION);
        String string = Shaders.getShaderPackName();
        if (string != null) {
            stringBuffer.append(", ");
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }

    public static void initGameSettings(GameSettings gameSettings) {
        if (Config.gameSettings == null) {
            Config.gameSettings = gameSettings;
            Config.updateAvailableProcessors();
            ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
            antialiasingLevel = Config.gameSettings.ofAaLevel;
        }
    }

    public static void initDisplay() {
        Config.checkInitialized();
        minecraftThread = Thread.currentThread();
        Config.updateThreadPriorities();
        Shaders.startup(Minecraft.getInstance());
    }

    public static void checkInitialized() {
        if (!initialized && Minecraft.getInstance().getMainWindow() != null) {
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
        Config.log("LWJGL: " + GLFW.glfwGetVersionString());
        openGlVersion = GL11.glGetString(7938);
        openGlRenderer = GL11.glGetString(7937);
        openGlVendor = GL11.glGetString(7936);
        Config.log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
        Config.log("OpenGL Version: " + Config.getOpenGlVersionString());
        GLCapabilities gLCapabilities = GL.getCapabilities();
        if (!gLCapabilities.OpenGL12) {
            Config.log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!(fancyFogAvailable = gLCapabilities.GL_NV_fog_distance)) {
            Config.log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!(occlusionAvailable = gLCapabilities.GL_ARB_occlusion_query)) {
            Config.log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        int n = TextureUtils.getGLMaximumTextureSize();
        Config.dbg("Maximum texture size: " + n + "x" + n);
    }

    public static String getBuild() {
        if (build == null) {
            try {
                InputStream inputStream = Config.getOptiFineResourceStream("/buildof.txt");
                if (inputStream == null) {
                    return null;
                }
                build = Config.readLines(inputStream)[0];
            } catch (Exception exception) {
                Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
                build = "";
            }
        }
        return build;
    }

    public static InputStream getOptiFineResourceStream(String string) {
        InputStream inputStream = ReflectorForge.getOptiFineResourceStream(string);
        return inputStream != null ? inputStream : Config.class.getResourceAsStream(string);
    }

    public static boolean isFancyFogAvailable() {
        return fancyFogAvailable;
    }

    public static boolean isOcclusionAvailable() {
        return occlusionAvailable;
    }

    public static int getMinecraftVersionInt() {
        if (minecraftVersionInt < 0) {
            String[] stringArray = Config.tokenize(MC_VERSION, ".");
            int n = 0;
            if (stringArray.length > 0) {
                n += 10000 * Config.parseInt(stringArray[0], 0);
            }
            if (stringArray.length > 1) {
                n += 100 * Config.parseInt(stringArray[5], 0);
            }
            if (stringArray.length > 2) {
                n += 1 * Config.parseInt(stringArray[5], 0);
            }
            minecraftVersionInt = n;
        }
        return minecraftVersionInt;
    }

    public static String getOpenGlVersionString() {
        GlVersion glVersion = Config.getGlVersion();
        return glVersion.getMajor() + "." + glVersion.getMinor() + "." + glVersion.getRelease();
    }

    private static GlVersion getGlVersionLwjgl() {
        GLCapabilities gLCapabilities = GL.getCapabilities();
        if (gLCapabilities.OpenGL44) {
            return new GlVersion(4, 4);
        }
        if (gLCapabilities.OpenGL43) {
            return new GlVersion(4, 3);
        }
        if (gLCapabilities.OpenGL42) {
            return new GlVersion(4, 2);
        }
        if (gLCapabilities.OpenGL41) {
            return new GlVersion(4, 1);
        }
        if (gLCapabilities.OpenGL40) {
            return new GlVersion(4, 0);
        }
        if (gLCapabilities.OpenGL33) {
            return new GlVersion(3, 3);
        }
        if (gLCapabilities.OpenGL32) {
            return new GlVersion(3, 2);
        }
        if (gLCapabilities.OpenGL31) {
            return new GlVersion(3, 1);
        }
        if (gLCapabilities.OpenGL30) {
            return new GlVersion(3, 0);
        }
        if (gLCapabilities.OpenGL21) {
            return new GlVersion(2, 1);
        }
        if (gLCapabilities.OpenGL20) {
            return new GlVersion(2, 0);
        }
        if (gLCapabilities.OpenGL15) {
            return new GlVersion(1, 5);
        }
        if (gLCapabilities.OpenGL14) {
            return new GlVersion(1, 4);
        }
        if (gLCapabilities.OpenGL13) {
            return new GlVersion(1, 3);
        }
        if (gLCapabilities.OpenGL12) {
            return new GlVersion(1, 2);
        }
        return gLCapabilities.OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0);
    }

    public static GlVersion getGlVersion() {
        if (glVersion == null) {
            String string = GL11.glGetString(7938);
            glVersion = Config.parseGlVersion(string, null);
            if (glVersion == null) {
                glVersion = Config.getGlVersionLwjgl();
            }
            if (glVersion == null) {
                glVersion = new GlVersion(1, 0);
            }
        }
        return glVersion;
    }

    public static GlVersion getGlslVersion() {
        String string;
        if (glslVersion == null && (glslVersion = Config.parseGlVersion(string = GL11.glGetString(35724), null)) == null) {
            glslVersion = new GlVersion(1, 10);
        }
        return glslVersion;
    }

    public static GlVersion parseGlVersion(String string, GlVersion glVersion) {
        try {
            if (string == null) {
                return glVersion;
            }
            Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
            Matcher matcher = pattern.matcher(string);
            if (!matcher.matches()) {
                return glVersion;
            }
            int n = Integer.parseInt(matcher.group(1));
            int n2 = Integer.parseInt(matcher.group(2));
            int n3 = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0;
            String string2 = matcher.group(5);
            return new GlVersion(n, n2, n3, string2);
        } catch (Exception exception) {
            Config.error("", exception);
            return glVersion;
        }
    }

    public static String[] getOpenGlExtensions() {
        if (openGlExtensions == null) {
            openGlExtensions = Config.detectOpenGlExtensions();
        }
        return openGlExtensions;
    }

    private static String[] detectOpenGlExtensions() {
        Object object;
        try {
            int n;
            object = Config.getGlVersion();
            if (((GlVersion)object).getMajor() >= 3 && (n = GL11.glGetInteger(33309)) > 0) {
                String[] stringArray = new String[n];
                for (int i = 0; i < n; ++i) {
                    stringArray[i] = GL30.glGetStringi(7939, i);
                }
                return stringArray;
            }
        } catch (Exception exception) {
            Config.error("", exception);
        }
        try {
            object = GL11.glGetString(7939);
            return ((String)object).split(" ");
        } catch (Exception exception) {
            Config.error("", exception);
            return new String[0];
        }
    }

    public static void updateThreadPriorities() {
        Config.updateAvailableProcessors();
        int n = 8;
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

    private static void setThreadPriority(String string, int n) {
        try {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            if (threadGroup == null) {
                return;
            }
            int n2 = (threadGroup.activeCount() + 10) * 2;
            Thread[] threadArray = new Thread[n2];
            threadGroup.enumerate(threadArray, true);
            for (int i = 0; i < threadArray.length; ++i) {
                Thread thread2 = threadArray[i];
                if (thread2 == null || !thread2.getName().startsWith(string)) continue;
                thread2.setPriority(n);
            }
        } catch (Throwable throwable) {
            Config.warn(throwable.getClass().getName() + ": " + throwable.getMessage());
        }
    }

    public static boolean isMinecraftThread() {
        return Thread.currentThread() == minecraftThread;
    }

    private static void startVersionCheckThread() {
        VersionCheckThread versionCheckThread = new VersionCheckThread();
        versionCheckThread.start();
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
                return 1;
            }
            case 1: {
                return 1;
            }
            case 2: {
                if (Config.isMultiTexture()) {
                    return 0;
                }
                return 1;
            }
            case 3: {
                if (Config.isMultiTexture()) {
                    return 0;
                }
                return 1;
            }
        }
        return 1;
    }

    public static boolean isUseAlphaFunc() {
        float f = Config.getAlphaFuncLevel();
        return f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5f;
    }

    public static float getAlphaFuncLevel() {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy() {
        if (!Config.isFancyFogAvailable()) {
            return true;
        }
        return Config.gameSettings.ofFogType == 2;
    }

    public static boolean isFogFast() {
        return Config.gameSettings.ofFogType == 1;
    }

    public static boolean isFogOff() {
        return Config.gameSettings.ofFogType == 3;
    }

    public static boolean isFogOn() {
        return Config.gameSettings.ofFogType != 3;
    }

    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }

    public static void detail(String string) {
        if (logDetail) {
            LOGGER.info("[OptiFine] " + string);
        }
    }

    public static void dbg(String string) {
        LOGGER.info("[OptiFine] " + string);
    }

    public static void warn(String string) {
        LOGGER.warn("[OptiFine] " + string);
    }

    public static void warn(String string, Throwable throwable) {
        LOGGER.warn("[OptiFine] " + string, throwable);
    }

    public static void error(String string) {
        LOGGER.error("[OptiFine] " + string);
    }

    public static void error(String string, Throwable throwable) {
        LOGGER.error("[OptiFine] " + string, throwable);
    }

    public static void log(String string) {
        Config.dbg(string);
    }

    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }

    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }

    public static boolean isGraphicsFancy() {
        return Config.gameSettings.graphicFanciness != GraphicsFanciness.FAST;
    }

    public static boolean isGraphicsFabulous() {
        return Config.gameSettings.graphicFanciness == GraphicsFanciness.FABULOUS;
    }

    public static boolean isRainFancy() {
        if (Config.gameSettings.ofRain == 0) {
            return Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofRain == 2;
    }

    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }

    public static boolean isCloudsFancy() {
        if (Config.gameSettings.ofClouds != 0) {
            return Config.gameSettings.ofClouds == 2;
        }
        if (Config.isShaders() && !Shaders.shaderPackClouds.isDefault()) {
            return Shaders.shaderPackClouds.isFancy();
        }
        if (texturePackClouds != 0) {
            return texturePackClouds == 2;
        }
        return Config.isGraphicsFancy();
    }

    public static boolean isCloudsOff() {
        if (Config.gameSettings.ofClouds != 0) {
            return Config.gameSettings.ofClouds == 3;
        }
        if (Config.isShaders() && !Shaders.shaderPackClouds.isDefault()) {
            return Shaders.shaderPackClouds.isOff();
        }
        if (texturePackClouds != 0) {
            return texturePackClouds == 3;
        }
        return true;
    }

    public static void updateTexturePackClouds() {
        texturePackClouds = 0;
        IResourceManager iResourceManager = Config.getResourceManager();
        if (iResourceManager != null) {
            try {
                InputStream inputStream = iResourceManager.getResource(new ResourceLocation("optifine/color.properties")).getInputStream();
                if (inputStream == null) {
                    return;
                }
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                String string = propertiesOrdered.getProperty("clouds");
                if (string == null) {
                    return;
                }
                Config.dbg("Texture pack clouds: " + string);
                string = string.toLowerCase();
                if (string.equals("fast")) {
                    texturePackClouds = 1;
                }
                if (string.equals("fancy")) {
                    texturePackClouds = 2;
                }
                if (string.equals("off")) {
                    texturePackClouds = 3;
                }
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static ModelManager getModelManager() {
        return Config.minecraft.getItemRenderer().modelManager;
    }

    public static boolean isTreesFancy() {
        if (Config.gameSettings.ofTrees == 0) {
            return Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofTrees != 1;
    }

    public static boolean isTreesSmart() {
        return Config.gameSettings.ofTrees == 4;
    }

    public static boolean isCullFacesLeaves() {
        if (Config.gameSettings.ofTrees == 0) {
            return !Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofTrees == 4;
    }

    public static boolean isDroppedItemsFancy() {
        if (Config.gameSettings.ofDroppedItems == 0) {
            return Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofDroppedItems == 2;
    }

    public static int limit(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        return n > n3 ? n3 : n;
    }

    public static long limit(long l, long l2, long l3) {
        if (l < l2) {
            return l2;
        }
        return l > l3 ? l3 : l;
    }

    public static float limit(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return f > f3 ? f3 : f;
    }

    public static double limit(double d, double d2, double d3) {
        if (d < d2) {
            return d2;
        }
        return d > d3 ? d3 : d;
    }

    public static float limitTo1(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        return f > 1.0f ? 1.0f : f;
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
        return Config.isShaders() && Shaders.aoLevel >= 0.0f ? Shaders.aoLevel : (float)Config.gameSettings.ofAoLevel;
    }

    public static String listToString(List list) {
        return Config.listToString(list, ", ");
    }

    public static String listToString(List list, String string) {
        if (list == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(list.size() * 5);
        for (int i = 0; i < list.size(); ++i) {
            Object e = list.get(i);
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(String.valueOf(e));
        }
        return stringBuffer.toString();
    }

    public static String arrayToString(Object[] objectArray) {
        return Config.arrayToString(objectArray, ", ");
    }

    public static String arrayToString(Object[] objectArray, String string) {
        if (objectArray == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(objectArray.length * 5);
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = objectArray[i];
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(String.valueOf(object));
        }
        return stringBuffer.toString();
    }

    public static String arrayToString(int[] nArray) {
        return Config.arrayToString(nArray, ", ");
    }

    public static String arrayToString(int[] nArray, String string) {
        if (nArray == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(nArray.length * 5);
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(String.valueOf(n));
        }
        return stringBuffer.toString();
    }

    public static String arrayToString(float[] fArray) {
        return Config.arrayToString(fArray, ", ");
    }

    public static String arrayToString(float[] fArray, String string) {
        if (fArray == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(fArray.length * 5);
        for (int i = 0; i < fArray.length; ++i) {
            float f = fArray[i];
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(String.valueOf(f));
        }
        return stringBuffer.toString();
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

    public static InputStream getResourceStream(ResourceLocation resourceLocation) throws IOException {
        return Config.getResourceStream(minecraft.getResourceManager(), resourceLocation);
    }

    public static InputStream getResourceStream(IResourceManager iResourceManager, ResourceLocation resourceLocation) throws IOException {
        IResource iResource = iResourceManager.getResource(resourceLocation);
        return iResource == null ? null : iResource.getInputStream();
    }

    public static IResource getResource(ResourceLocation resourceLocation) throws IOException {
        return minecraft.getResourceManager().getResource(resourceLocation);
    }

    public static boolean hasResource(ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            return true;
        }
        IResourcePack iResourcePack = Config.getDefiningResourcePack(resourceLocation);
        return iResourcePack != null;
    }

    public static boolean hasResource(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        try {
            IResource iResource = iResourceManager.getResource(resourceLocation);
            return iResource != null;
        } catch (IOException iOException) {
            return true;
        }
    }

    public static boolean hasResource(IResourcePack iResourcePack, ResourceLocation resourceLocation) {
        return iResourcePack != null && resourceLocation != null ? iResourcePack.resourceExists(ResourcePackType.CLIENT_RESOURCES, resourceLocation) : false;
    }

    public static IResourcePack[] getResourcePacks() {
        ResourcePackList resourcePackList = minecraft.getResourcePackList();
        Collection<ResourcePackInfo> collection = resourcePackList.getEnabledPacks();
        ArrayList<IResourcePack> arrayList = new ArrayList<IResourcePack>();
        for (ResourcePackInfo resourcePackInfo : collection) {
            IResourcePack iResourcePack = resourcePackInfo.getResourcePack();
            if (iResourcePack == Config.getDefaultResourcePack()) continue;
            arrayList.add(iResourcePack);
        }
        IResourcePack[] iResourcePackArray = arrayList.toArray(new IResourcePack[arrayList.size()]);
        return iResourcePackArray;
    }

    public static String getResourcePackNames() {
        if (minecraft.getResourceManager() == null) {
            return "";
        }
        IResourcePack[] iResourcePackArray = Config.getResourcePacks();
        if (iResourcePackArray.length <= 0) {
            return Config.getDefaultResourcePack().getName();
        }
        String[] stringArray = new String[iResourcePackArray.length];
        for (int i = 0; i < iResourcePackArray.length; ++i) {
            stringArray[i] = iResourcePackArray[i].getName();
        }
        return Config.arrayToString(stringArray);
    }

    public static VanillaPack getDefaultResourcePack() {
        return minecraft.getPackFinder().getVanillaPack();
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation resourceLocation) {
        return Config.getDefiningResourcePack(resourceLocation) == Config.getDefaultResourcePack();
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation resourceLocation) {
        ResourcePackList resourcePackList = minecraft.getResourcePackList();
        Collection<ResourcePackInfo> collection = resourcePackList.getEnabledPacks();
        List list = (List)collection;
        for (int i = list.size() - 1; i >= 0; --i) {
            ResourcePackInfo resourcePackInfo = (ResourcePackInfo)list.get(i);
            IResourcePack iResourcePack = resourcePackInfo.getResourcePack();
            if (!iResourcePack.resourceExists(ResourcePackType.CLIENT_RESOURCES, resourceLocation)) continue;
            return iResourcePack;
        }
        return null;
    }

    public static WorldRenderer getRenderGlobal() {
        return Config.minecraft.worldRenderer;
    }

    public static WorldRenderer getWorldRenderer() {
        return Config.minecraft.worldRenderer;
    }

    public static GameRenderer getGameRenderer() {
        return Config.minecraft.gameRenderer;
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

    public static boolean isSunTexture() {
        if (!Config.isSunMoonEnabled()) {
            return true;
        }
        return !Config.isShaders() || Shaders.isSun();
    }

    public static boolean isMoonTexture() {
        if (!Config.isSunMoonEnabled()) {
            return true;
        }
        return !Config.isShaders() || Shaders.isMoon();
    }

    public static boolean isVignetteEnabled() {
        if (Config.isShaders() && !Shaders.isVignette()) {
            return true;
        }
        if (Config.gameSettings.ofVignette == 0) {
            return Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofVignette == 2;
    }

    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }

    public static void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException interruptedException) {
            Config.error("", interruptedException);
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
        if (Config.getAnisotropicFilterLevel() > 1) {
            return false;
        }
        return Config.getAntialiasingLevel() > 0;
    }

    public static boolean between(int n, int n2, int n3) {
        return n >= n2 && n <= n3;
    }

    public static boolean between(float f, float f2, float f3) {
        return f >= f2 && f <= f3;
    }

    public static boolean between(double d, double d2, double d3) {
        return d >= d2 && d <= d3;
    }

    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }

    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }

    public static int parseInt(String string, int n) {
        try {
            if (string == null) {
                return n;
            }
            string = string.trim();
            return Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static int parseHexInt(String string, int n) {
        try {
            if (string == null) {
                return n;
            }
            if ((string = string.trim()).startsWith("0x")) {
                string = string.substring(2);
            }
            return Integer.parseInt(string, 16);
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static float parseFloat(String string, float f) {
        try {
            if (string == null) {
                return f;
            }
            string = string.trim();
            return Float.parseFloat(string);
        } catch (NumberFormatException numberFormatException) {
            return f;
        }
    }

    public static boolean parseBoolean(String string, boolean bl) {
        try {
            if (string == null) {
                return bl;
            }
            string = string.trim();
            return Boolean.parseBoolean(string);
        } catch (NumberFormatException numberFormatException) {
            return bl;
        }
    }

    public static Boolean parseBoolean(String string, Boolean bl) {
        try {
            if (string == null) {
                return bl;
            }
            if ((string = string.trim().toLowerCase()).equals("true")) {
                return Boolean.TRUE;
            }
            return string.equals("false") ? Boolean.FALSE : bl;
        } catch (NumberFormatException numberFormatException) {
            return bl;
        }
    }

    public static String[] tokenize(String string, String string2) {
        String[] stringArray;
        StringTokenizer stringTokenizer = new StringTokenizer(string, string2);
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        while (stringTokenizer.hasMoreTokens()) {
            stringArray = stringTokenizer.nextToken();
            arrayList.add(stringArray);
        }
        stringArray = arrayList.toArray(new String[arrayList.size()]);
        return stringArray;
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

    public static boolean isRandomEntities() {
        return Config.gameSettings.ofRandomEntities;
    }

    public static void checkGlError(String string) {
        int n = GlStateManager.getError();
        if (n != 0 && GlErrors.isEnabled(n)) {
            String string2 = Config.getGlErrorString(n);
            String string3 = String.format("OpenGL error: %s (%s), at: %s", n, string2, string);
            Config.error(string3);
            if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
                String string4 = I18n.format("of.message.openglError", n, string2);
                Config.minecraft.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(string4));
            }
        }
    }

    public static boolean isSmoothBiomes() {
        return Config.gameSettings.biomeBlendRadius > 0;
    }

    public static int getBiomeBlendRadius() {
        return Config.gameSettings.biomeBlendRadius;
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

    public static boolean isEmissiveTextures() {
        return Config.gameSettings.ofEmissiveTextures;
    }

    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings.ofConnectedTextures == 2;
    }

    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }

    public static boolean isTranslucentBlocksFancy() {
        if (Config.gameSettings.ofTranslucentBlocks == 0) {
            return Config.isGraphicsFancy();
        }
        return Config.gameSettings.ofTranslucentBlocks == 2;
    }

    public static boolean isShaders() {
        return Shaders.shaderPackLoaded;
    }

    public static String[] readLines(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return Config.readLines(fileInputStream);
    }

    public static String[] readLines(InputStream inputStream) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ASCII");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String readFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return Config.readInputStream(fileInputStream, "ASCII");
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        return Config.readInputStream(inputStream, "ASCII");
    }

    public static String readInputStream(InputStream inputStream, String string) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, string);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String string2;
            if ((string2 = bufferedReader.readLine()) == null) {
                inputStream.close();
                return stringBuffer.toString();
            }
            stringBuffer.append(string2);
            stringBuffer.append("\n");
        }
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        while (true) {
            int n;
            if ((n = inputStream.read(byArray)) < 0) {
                inputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(byArray, 0, n);
        }
    }

    public static GameSettings getGameSettings() {
        return gameSettings;
    }

    public static String getNewRelease() {
        return newRelease;
    }

    public static void setNewRelease(String string) {
        newRelease = string;
    }

    public static int compareRelease(String string, String string2) {
        int n;
        String[] stringArray;
        String string3;
        String[] stringArray2 = Config.splitRelease(string);
        String string4 = stringArray2[0];
        if (!string4.equals(string3 = (stringArray = Config.splitRelease(string2))[0])) {
            return string4.compareTo(string3);
        }
        int n2 = Config.parseInt(stringArray2[5], -1);
        if (n2 != (n = Config.parseInt(stringArray[5], -1))) {
            return n2 - n;
        }
        String string5 = stringArray2[5];
        String string6 = stringArray[5];
        if (!string5.equals(string6)) {
            if (string5.isEmpty()) {
                return 0;
            }
            if (string6.isEmpty()) {
                return 1;
            }
        }
        return string5.compareTo(string6);
    }

    private static String[] splitRelease(String string) {
        if (string != null && string.length() > 0) {
            Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
            Matcher matcher = pattern.matcher(string);
            if (!matcher.matches()) {
                return new String[]{"", "", ""};
            }
            String string2 = Config.normalize(matcher.group(1));
            String string3 = Config.normalize(matcher.group(2));
            String string4 = Config.normalize(matcher.group(3));
            return new String[]{string2, string3, string4};
        }
        return new String[]{"", "", ""};
    }

    public static int intHash(int n) {
        n = n ^ 0x3D ^ n >> 16;
        n += n << 3;
        n ^= n >> 4;
        return (n *= 668265261) ^ n >> 15;
    }

    public static int getRandom(BlockPos blockPos, int n) {
        int n2 = Config.intHash(n + 37);
        n2 = Config.intHash(n2 + blockPos.getX());
        n2 = Config.intHash(n2 + blockPos.getZ());
        return Config.intHash(n2 + blockPos.getY());
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
        return Config.gameSettings.ofLazyChunkLoading;
    }

    public static boolean isDynamicFov() {
        return Config.gameSettings.ofDynamicFov;
    }

    public static boolean isAlternateBlocks() {
        return Config.gameSettings.ofAlternateBlocks;
    }

    public static int getChunkViewDistance() {
        return gameSettings == null ? 10 : Config.gameSettings.renderDistanceChunks;
    }

    public static boolean equals(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        return object == null ? false : object.equals(object2);
    }

    public static boolean equalsOne(Object object, Object[] objectArray) {
        if (objectArray == null) {
            return true;
        }
        for (int i = 0; i < objectArray.length; ++i) {
            Object object2 = objectArray[i];
            if (!Config.equals(object, object2)) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsOne(int n, int[] nArray) {
        for (int i = 0; i < nArray.length; ++i) {
            if (nArray[i] != n) continue;
            return false;
        }
        return true;
    }

    public static boolean isSameOne(Object object, Object[] objectArray) {
        if (objectArray == null) {
            return true;
        }
        for (int i = 0; i < objectArray.length; ++i) {
            Object object2 = objectArray[i];
            if (object != object2) continue;
            return false;
        }
        return true;
    }

    public static String normalize(String string) {
        return string == null ? "" : string;
    }

    private static ByteBuffer readIconImage(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int[] nArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * nArray.length);
        for (int n : nArray) {
            byteBuffer.putInt(n << 8 | n >> 24 & 0xFF);
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    public static Object[] addObjectToArray(Object[] objectArray, Object object) {
        if (objectArray == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int n = objectArray.length;
        int n2 = n + 1;
        Object[] objectArray2 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n2);
        System.arraycopy(objectArray, 0, objectArray2, 0, n);
        objectArray2[n] = object;
        return objectArray2;
    }

    public static Object[] addObjectToArray(Object[] objectArray, Object object, int n) {
        ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList(objectArray));
        arrayList.add(n, object);
        Object[] objectArray2 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), arrayList.size());
        return arrayList.toArray(objectArray2);
    }

    public static Object[] addObjectsToArray(Object[] objectArray, Object[] objectArray2) {
        if (objectArray == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objectArray2.length == 0) {
            return objectArray;
        }
        int n = objectArray.length;
        int n2 = n + objectArray2.length;
        Object[] objectArray3 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n2);
        System.arraycopy(objectArray, 0, objectArray3, 0, n);
        System.arraycopy(objectArray2, 0, objectArray3, n, objectArray2.length);
        return objectArray3;
    }

    public static Object[] removeObjectFromArray(Object[] objectArray, Object object) {
        ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList(objectArray));
        arrayList.remove(object);
        return Config.collectionToArray(arrayList, objectArray.getClass().getComponentType());
    }

    public static Object[] collectionToArray(Collection collection, Class clazz) {
        if (collection == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + clazz);
        }
        Object[] objectArray = (Object[])Array.newInstance(clazz, collection.size());
        return collection.toArray(objectArray);
    }

    public static boolean isCustomItems() {
        return Config.gameSettings.ofCustomItems;
    }

    public static void drawFps(MatrixStack matrixStack) {
        int n = Config.getChunkUpdates();
        int n2 = Config.minecraft.worldRenderer.getCountActiveRenderers();
        int n3 = Config.minecraft.worldRenderer.getCountEntitiesRendered();
        int n4 = Config.minecraft.worldRenderer.getCountTileEntitiesRendered();
        String string = Config.getFpsString() + ", C: " + n2 + ", E: " + n3 + "+" + n4 + ", U: " + n;
        Config.minecraft.fontRenderer.drawString(matrixStack, string, 2.0f, 2.0f, -2039584);
    }

    public static String getFpsString() {
        int n = Config.getFpsAverage();
        int n2 = Config.getFpsMin();
        if (showFrameTime) {
            String string = String.format("%.1f", 1000.0 / (double)Config.limit(n, 1, Integer.MAX_VALUE));
            String string2 = String.format("%.1f", 1000.0 / (double)Config.limit(n2, 1, Integer.MAX_VALUE));
            return string + "/" + string2 + " ms";
        }
        return n + "/" + n2 + " fps";
    }

    public static boolean isShowFrameTime() {
        return showFrameTime;
    }

    public static int getFpsAverage() {
        return Minecraft.getInstance().getDebugFPS();
    }

    public static int getFpsMin() {
        return fpsMinLast;
    }

    public static int getChunkUpdates() {
        return chunkUpdatesLast;
    }

    public static void updateFpsMin() {
        int n;
        FrameTimer frameTimer = minecraft.getFrameTimer();
        long[] lArray = frameTimer.getFrames();
        int n2 = frameTimer.getIndex();
        if (n2 != (n = frameTimer.getLastIndex())) {
            long l;
            int n3 = Minecraft.getInstance().debugFPS;
            if (n3 <= 0) {
                n3 = 1;
            }
            long l2 = l = (long)(1.0 / (double)n3 * 1.0E9);
            long l3 = 0L;
            int n4 = MathHelper.normalizeAngle(n2 - 1, lArray.length);
            while (n4 != n && (double)l3 < 1.0E9) {
                long l4 = lArray[n4];
                if (l4 > l2) {
                    l2 = l4;
                }
                l3 += l4;
                n4 = MathHelper.normalizeAngle(n4 - 1, lArray.length);
            }
            double d = (double)l2 / 1.0E9;
            fpsMinLast = (int)(1.0 / d);
        }
    }

    private static void updateChunkUpdates() {
        chunkUpdatesLast = ChunkRenderDispatcher.renderChunksUpdated;
        ChunkRenderDispatcher.renderChunksUpdated = 0;
    }

    public static int getBitsOs() {
        String string = System.getenv("ProgramFiles(X86)");
        return string != null ? 64 : 32;
    }

    public static int getBitsJre() {
        String[] stringArray = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            String string2 = System.getProperty(string);
            if (string2 == null || !string2.contains("64")) continue;
            return 1;
        }
        return 1;
    }

    public static boolean isNotify64BitJava() {
        return notify64BitJava;
    }

    public static void setNotify64BitJava(boolean bl) {
        notify64BitJava = bl;
    }

    public static boolean isConnectedModels() {
        return true;
    }

    public static void showGuiMessage(String string, String string2) {
        GuiMessage guiMessage = new GuiMessage(Config.minecraft.currentScreen, string, string2);
        minecraft.displayGuiScreen(guiMessage);
    }

    public static int[] addIntToArray(int[] nArray, int n) {
        return Config.addIntsToArray(nArray, new int[]{n});
    }

    public static int[] addIntsToArray(int[] nArray, int[] nArray2) {
        if (nArray != null && nArray2 != null) {
            int n = nArray.length;
            int n2 = n + nArray2.length;
            int[] nArray3 = new int[n2];
            System.arraycopy(nArray, 0, nArray3, 0, n);
            for (int i = 0; i < nArray2.length; ++i) {
                nArray3[i + n] = nArray2[i];
            }
            return nArray3;
        }
        throw new NullPointerException("The given array is NULL");
    }

    public static void writeFile(File file, String string) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] byArray = string.getBytes("ASCII");
        fileOutputStream.write(byArray);
        fileOutputStream.close();
    }

    public static void setTextureMap(AtlasTexture atlasTexture) {
        textureMapTerrain = atlasTexture;
    }

    public static AtlasTexture getTextureMap() {
        return textureMapTerrain;
    }

    public static boolean isDynamicLights() {
        return Config.gameSettings.ofDynamicLights != 3;
    }

    public static boolean isDynamicLightsFast() {
        return Config.gameSettings.ofDynamicLights == 1;
    }

    public static boolean isDynamicHandLight() {
        if (!Config.isDynamicLights()) {
            return true;
        }
        return Config.isShaders() ? Shaders.isDynamicHandLight() : true;
    }

    public static boolean isCustomEntityModels() {
        return Config.gameSettings.ofCustomEntityModels;
    }

    public static boolean isCustomGuis() {
        return Config.gameSettings.ofCustomGuis;
    }

    public static int getScreenshotSize() {
        return Config.gameSettings.ofScreenshotSize;
    }

    public static int[] toPrimitive(Integer[] integerArray) {
        if (integerArray == null) {
            return null;
        }
        if (integerArray.length == 0) {
            return new int[0];
        }
        int[] nArray = new int[integerArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = integerArray[i];
        }
        return nArray;
    }

    public static boolean isRenderRegions() {
        if (Config.isMultiTexture()) {
            return true;
        }
        return Config.gameSettings.ofRenderRegions && GlStateManager.vboRegions;
    }

    public static boolean isVbo() {
        return GLX.useVbo();
    }

    public static boolean isSmoothFps() {
        return Config.gameSettings.ofSmoothFps;
    }

    public static boolean openWebLink(URI uRI) {
        Util.setExceptionOpenUrl(null);
        Util.getOSType().openURI(uRI);
        Exception exception = Util.getExceptionOpenUrl();
        return exception == null;
    }

    public static boolean isShowGlErrors() {
        return Config.gameSettings.ofShowGlErrors;
    }

    public static String arrayToString(boolean[] blArray, String string) {
        if (blArray == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(blArray.length * 5);
        for (int i = 0; i < blArray.length; ++i) {
            boolean bl = blArray[i];
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(String.valueOf(bl));
        }
        return stringBuffer.toString();
    }

    public static boolean isIntegratedServerRunning() {
        if (minecraft.getIntegratedServer() == null) {
            return true;
        }
        return minecraft.isIntegratedServerRunning();
    }

    public static IntBuffer createDirectIntBuffer(int n) {
        return GLAllocation.createDirectByteBuffer(n << 2).asIntBuffer();
    }

    public static String getGlErrorString(int n) {
        switch (n) {
            case 0: {
                return "No error";
            }
            case 1280: {
                return "Invalid enum";
            }
            case 1281: {
                return "Invalid value";
            }
            case 1282: {
                return "Invalid operation";
            }
            case 1283: {
                return "Stack overflow";
            }
            case 1284: {
                return "Stack underflow";
            }
            case 1285: {
                return "Out of memory";
            }
            case 1286: {
                return "Invalid framebuffer operation";
            }
        }
        return "Unknown";
    }

    public static boolean isKeyDown(int n) {
        return GLFW.glfwGetKey(minecraft.getMainWindow().getHandle(), n) == 1;
    }

    public static boolean isTrue(Boolean bl) {
        return bl != null && bl != false;
    }

    public static boolean isReloadingResources() {
        ResourceLoadProgressGui resourceLoadProgressGui;
        if (Config.minecraft.loadingGui == null) {
            return true;
        }
        return Config.minecraft.loadingGui instanceof ResourceLoadProgressGui && (resourceLoadProgressGui = (ResourceLoadProgressGui)Config.minecraft.loadingGui).isFadeOut();
    }

    public static boolean isQuadsToTriangles() {
        if (!Config.isShaders()) {
            return true;
        }
        return !Shaders.canRenderQuads();
    }

    public static void frameStart() {
        long l = System.currentTimeMillis();
        long l2 = l - timeLastFrameMs;
        timeLastFrameMs = l;
        l2 = Config.limit(l2, 1L, 1000L);
        averageFrameTimeMs = (averageFrameTimeMs + l2) / 2L;
        averageFrameTimeMs = Config.limit(averageFrameTimeMs, 1L, 1000L);
        if (Config.minecraft.debug != mcDebugLast) {
            mcDebugLast = Config.minecraft.debug;
            Config.updateFpsMin();
            Config.updateChunkUpdates();
        }
    }

    public static long getAverageFrameTimeMs() {
        return averageFrameTimeMs;
    }

    public static float getAverageFrameTimeSec() {
        return (float)Config.getAverageFrameTimeMs() / 1000.0f;
    }

    public static long getAverageFrameFps() {
        return 1000L / Config.getAverageFrameTimeMs();
    }

    public static void checkNull(Object object, String string) throws NullPointerException {
        if (object == null) {
            throw new NullPointerException(string);
        }
    }

    static {
        showFrameTime = Boolean.getBoolean("frame.time");
    }
}

