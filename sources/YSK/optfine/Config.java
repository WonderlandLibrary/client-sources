package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.integrated.*;
import net.minecraft.world.*;
import java.awt.*;
import org.lwjgl.*;
import org.lwjgl.util.glu.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;

public class Config
{
    public static boolean zoomMode;
    private static DisplayMode desktopDisplayMode;
    public static String openGlVersion;
    public static final Float DEF_FOG_START;
    private static boolean initialized;
    private static long lastActionTime;
    private static int antialiasingLevel;
    private static String newRelease;
    private static boolean desktopModeChecked;
    public static final Float DEF_ALPHA_FUNC_LEVEL;
    public static final String OF_RELEASE;
    public static boolean fancyFogAvailable;
    public static String openGlRenderer;
    private static int texturePackClouds;
    public static final Boolean DEF_DYNAMIC_UPDATES;
    private static GameSettings gameSettings;
    public static final Integer DEF_PRELOADED_CHUNKS;
    public static final Integer DEF_MIPMAP_LEVEL;
    public static final Integer DEF_CHUNKS_LIMIT;
    public static final String OF_NAME;
    public static final Boolean DEF_LOAD_CHUNKS_FAR;
    public static final Boolean DEF_OCCLUSION_ENABLED;
    private static Thread minecraftThread;
    private static PrintStream systemOut;
    public static String openGlVendor;
    public static boolean waterOpacityChanged;
    public static final Integer DEF_UPDATES_PER_FRAME;
    public static final String MC_VERSION;
    public static final Integer DEF_MIPMAP_TYPE;
    public static final String VERSION;
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE;
    private static final String[] I;
    private static boolean notify64BitJava;
    public static boolean occlusionAvailable;
    private static boolean fullscreenModeChecked;
    public static final Boolean DEF_FOG_FANCY;
    private static Minecraft minecraft;
    public static final String OF_EDITION;
    private static int availableProcessors;
    
    static {
        I();
        VERSION = Config.I["".length()];
        OF_EDITION = Config.I[" ".length()];
        MC_VERSION = Config.I["  ".length()];
        OF_RELEASE = Config.I["   ".length()];
        OF_NAME = Config.I[0xA ^ 0xE];
        Config.newRelease = null;
        Config.notify64BitJava = ("".length() != 0);
        Config.openGlVersion = null;
        Config.openGlRenderer = null;
        Config.openGlVendor = null;
        Config.fancyFogAvailable = ("".length() != 0);
        Config.occlusionAvailable = ("".length() != 0);
        Config.gameSettings = null;
        Config.minecraft = null;
        Config.initialized = ("".length() != 0);
        Config.minecraftThread = null;
        Config.desktopDisplayMode = null;
        Config.antialiasingLevel = "".length();
        Config.availableProcessors = "".length();
        Config.zoomMode = ("".length() != 0);
        Config.texturePackClouds = "".length();
        Config.waterOpacityChanged = ("".length() != 0);
        Config.fullscreenModeChecked = ("".length() != 0);
        Config.desktopModeChecked = ("".length() != 0);
        Config.systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
        DEF_FOG_FANCY = (" ".length() != 0);
        DEF_FOG_START = 0.2f;
        DEF_OPTIMIZE_RENDER_DISTANCE = ("".length() != 0);
        DEF_OCCLUSION_ENABLED = ("".length() != 0);
        DEF_MIPMAP_LEVEL = "".length();
        DEF_MIPMAP_TYPE = 4754 + 3728 - 1626 + 3128;
        DEF_ALPHA_FUNC_LEVEL = 0.1f;
        DEF_LOAD_CHUNKS_FAR = ("".length() != 0);
        DEF_PRELOADED_CHUNKS = "".length();
        DEF_CHUNKS_LIMIT = (0x66 ^ 0x7F);
        DEF_UPDATES_PER_FRAME = "   ".length();
        DEF_DYNAMIC_UPDATES = ("".length() != 0);
        Config.lastActionTime = System.currentTimeMillis();
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }
    
    public static int getBitsJre() {
        final String[] array = new String["   ".length()];
        array["".length()] = Config.I[0x96 ^ 0xC2];
        array[" ".length()] = Config.I[0xC5 ^ 0x90];
        array["  ".length()] = Config.I[0xF0 ^ 0xA6];
        final String[] array2 = array;
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < array2.length) {
            final String property = System.getProperty(array2[i]);
            if (property != null && property.contains(Config.I[0x47 ^ 0x10])) {
                return 0x87 ^ 0xC7;
            }
            ++i;
        }
        return 0x4C ^ 0x6C;
    }
    
    public static boolean isTimeDefault() {
        if (Config.gameSettings.ofTime != 0 && Config.gameSettings.ofTime != "  ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public static IResourcePack getDefiningResourcePack(final ResourceLocation resourceLocation) {
        final IResourcePack[] resourcePacks = getResourcePacks();
        int i = resourcePacks.length - " ".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i >= 0) {
            final IResourcePack resourcePack = resourcePacks[i];
            if (resourcePack.resourceExists(resourceLocation)) {
                return resourcePack;
            }
            --i;
        }
        if (getDefaultResourcePack().resourceExists(resourceLocation)) {
            return getDefaultResourcePack();
        }
        return null;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static float parseFloat(final String s, final float n) {
        try {
            float float1;
            if (s == null) {
                float1 = n;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                float1 = Float.parseFloat(s);
            }
            return float1;
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static boolean isGeneratedWater() {
        if (Config.gameSettings.ofAnimatedWater == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static int parseInt(final String s, final int n) {
        try {
            int int1;
            if (s == null) {
                int1 = n;
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                int1 = Integer.parseInt(s);
            }
            return int1;
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static boolean isTimeDayOnly() {
        if (Config.gameSettings.ofTime == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean hasResource(final ResourceLocation resourceLocation) {
        try {
            if (getResource(resourceLocation) != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        catch (IOException ex) {
            return "".length() != 0;
        }
    }
    
    public static boolean isVignetteEnabled() {
        int n;
        if (Config.gameSettings.ofVignette == 0) {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofVignette == "  ".length()) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private static void checkOpenGlCaps() {
        log(Config.I[0x21 ^ 0x27]);
        log(getVersion());
        log(new StringBuilder().append(new Date()).toString());
        log(Config.I[0x35 ^ 0x32] + System.getProperty(Config.I[0x9C ^ 0x94]) + Config.I[0xBD ^ 0xB4] + System.getProperty(Config.I[0x60 ^ 0x6A]) + Config.I[0x7D ^ 0x76] + System.getProperty(Config.I[0x91 ^ 0x9D]));
        log(Config.I[0xB1 ^ 0xBC] + System.getProperty(Config.I[0x88 ^ 0x86]) + Config.I[0x8 ^ 0x7] + System.getProperty(Config.I[0x24 ^ 0x34]));
        log(Config.I[0x3B ^ 0x2A] + System.getProperty(Config.I[0x2E ^ 0x3C]) + Config.I[0x1F ^ 0xC] + System.getProperty(Config.I[0x3B ^ 0x2F]) + Config.I[0xB3 ^ 0xA6] + System.getProperty(Config.I[0xA8 ^ 0xBE]));
        log(Config.I[0xA4 ^ 0xB3] + Sys.getVersion());
        Config.openGlVersion = GL11.glGetString(7101 + 7461 - 12661 + 6037);
        Config.openGlRenderer = GL11.glGetString(1616 + 4120 - 1062 + 3263);
        Config.openGlVendor = GL11.glGetString(2550 + 3054 - 3901 + 6233);
        log(Config.I[0xBF ^ 0xA7] + Config.openGlRenderer + Config.I[0xA0 ^ 0xB9] + Config.openGlVersion + Config.I[0xA2 ^ 0xB8] + Config.openGlVendor);
        log(Config.I[0x44 ^ 0x5F] + getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            log(Config.I[0x3F ^ 0x23]);
        }
        if (!(Config.fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance)) {
            log(Config.I[0x72 ^ 0x6F]);
        }
        if (!(Config.occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query)) {
            log(Config.I[0x82 ^ 0x9C]);
        }
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        dbg(Config.I[0x9C ^ 0x83] + glMaximumTextureSize + Config.I[0x5C ^ 0x7C] + glMaximumTextureSize);
    }
    
    public static int getRandom(final BlockPos blockPos, final int n) {
        return intHash(intHash(intHash(intHash(n + (0x99 ^ 0xBC)) + blockPos.getX()) + blockPos.getZ()) + blockPos.getY());
    }
    
    public static Object[] addObjectToArray(final Object[] array, final Object o) {
        if (array == null) {
            throw new NullPointerException(Config.I[0xE8 ^ 0xA3]);
        }
        final int length = array.length;
        final Object[] array2 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + " ".length());
        System.arraycopy(array, "".length(), array2, "".length(), length);
        array2[length] = o;
        return array2;
    }
    
    public static boolean isCloudsOff() {
        if (Config.gameSettings.ofClouds == "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void setNewRelease(final String newRelease) {
        Config.newRelease = newRelease;
    }
    
    public static boolean isCustomItems() {
        return "".length() != 0;
    }
    
    public static String getOpenGlVersionString() {
        final int openGlVersion = getOpenGlVersion();
        return openGlVersion / (0x7C ^ 0x76) + Config.I[0x55 ^ 0x74] + openGlVersion % (0xBF ^ 0xB5);
    }
    
    private static int getOpenGlVersion() {
        int n;
        if (!GLContext.getCapabilities().OpenGL11) {
            n = (0xBD ^ 0xB7);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL12) {
            n = (0xA0 ^ 0xAB);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL13) {
            n = (0x77 ^ 0x7B);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL14) {
            n = (0x64 ^ 0x69);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL15) {
            n = (0x6C ^ 0x62);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL20) {
            n = (0x97 ^ 0x98);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL21) {
            n = (0xBD ^ 0xA9);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL30) {
            n = (0x58 ^ 0x4D);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL31) {
            n = (0x19 ^ 0x7);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL32) {
            n = (0x6 ^ 0x19);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL33) {
            n = (0x33 ^ 0x13);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (!GLContext.getCapabilities().OpenGL40) {
            n = (0xE6 ^ 0xC7);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n = (0xEF ^ 0xC7);
        }
        return n;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }
    
    public static IResourceManager getResourceManager() {
        return Config.minecraft.getResourceManager();
    }
    
    public static void drawFps() {
        final Minecraft minecraft = Config.minecraft;
        Config.minecraft.fontRendererObj.drawString(Minecraft.getDebugFPS() + Config.I[0x38 ^ 0x75] + Config.minecraft.renderGlobal.getCountActiveRenderers() + Config.I[0x4E ^ 0x0] + Config.minecraft.renderGlobal.getCountEntitiesRendered() + Config.I[0x49 ^ 0x6] + Config.minecraft.renderGlobal.getCountTileEntitiesRendered() + Config.I[0xE8 ^ 0xB8] + getUpdates(Config.minecraft.debug), "  ".length(), "  ".length(), -(1301351 + 905152 - 1676553 + 1509634));
    }
    
    public static boolean isFogFancy() {
        int n;
        if (!isFancyFogAvailable()) {
            n = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofFogType == "  ".length()) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static WorldServer getWorldServer() {
        if (Config.minecraft == null) {
            return null;
        }
        final WorldClient theWorld = Config.minecraft.theWorld;
        if (theWorld == null) {
            return null;
        }
        if (!Config.minecraft.isIntegratedServerRunning()) {
            return null;
        }
        final IntegratedServer integratedServer = Config.minecraft.getIntegratedServer();
        if (integratedServer == null) {
            return null;
        }
        final WorldProvider provider = theWorld.provider;
        if (provider == null) {
            return null;
        }
        final int dimensionId = provider.getDimensionId();
        try {
            return integratedServer.worldServerForDimension(dimensionId);
        }
        catch (NullPointerException ex) {
            return null;
        }
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 0x13 ^ 0x19;
        }
        return Config.gameSettings.renderDistanceChunks;
    }
    
    private static void setThreadPriority(final String s, final int priority) {
        try {
            final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            if (threadGroup == null) {
                return;
            }
            final Thread[] array = new Thread[(threadGroup.activeCount() + (0x1C ^ 0x16)) * "  ".length()];
            threadGroup.enumerate(array, "".length() != 0);
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < array.length) {
                final Thread thread = array[i];
                if (thread != null && thread.getName().startsWith(s)) {
                    thread.setPriority(priority);
                }
                ++i;
            }
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (Throwable t) {
            dbg(String.valueOf(t.getClass().getName()) + Config.I[0x20 ^ 0x5] + t.getMessage());
        }
    }
    
    public static int getAvailableProcessors() {
        return Config.availableProcessors;
    }
    
    public static String fillLeft(String s, final int n, final char c) {
        if (s == null) {
            s = Config.I[0xDA ^ 0x82];
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (sb.length() < n - s.length()) {
            sb.append(c);
        }
        return String.valueOf(sb.toString()) + s;
    }
    
    public static DisplayMode getDisplayMode(final Dimension dimension) throws LWJGLException {
        final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < availableDisplayModes.length) {
            final DisplayMode displayMode = availableDisplayModes[i];
            if (displayMode.getWidth() == dimension.width && displayMode.getHeight() == dimension.height && (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency()))) {
                return displayMode;
            }
            ++i;
        }
        return Config.desktopDisplayMode;
    }
    
    public static boolean isBetterGrassFancy() {
        if (Config.gameSettings.ofBetterGrass == "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isCloudsFancy() {
        int n;
        if (Config.gameSettings.ofClouds != 0) {
            if (Config.gameSettings.ofClouds == "  ".length()) {
                n = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else if (Config.texturePackClouds != 0) {
            if (Config.texturePackClouds == "  ".length()) {
                n = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
        }
        else {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
        }
        return n != 0;
    }
    
    public static boolean isTranslucentBlocksFancy() {
        int n;
        if (Config.gameSettings.ofTranslucentBlocks == 0) {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofTranslucentBlocks == "  ".length()) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static boolean isSmoothBiomes() {
        return Config.gameSettings.ofSmoothBiomes;
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }
    
    public static String readFile(final File file) throws IOException {
        return readInputStream(new FileInputStream(file), Config.I[0xBA ^ 0x85]);
    }
    
    public static Object[] addObjectsToArray(final Object[] array, final Object[] array2) {
        if (array == null) {
            throw new NullPointerException(Config.I[0x4A ^ 0x6]);
        }
        if (array2.length == 0) {
            return array;
        }
        final int length = array.length;
        final Object[] array3 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + array2.length);
        System.arraycopy(array, "".length(), array3, "".length(), length);
        System.arraycopy(array2, "".length(), array3, length, array2.length);
        return array3;
    }
    
    public static int intHash(int n) {
        n = (n ^ (0x67 ^ 0x5A) ^ n >> (0x99 ^ 0x89));
        n += n << "   ".length();
        n ^= n >> (0xAC ^ 0xA8);
        n *= 466677434 + 228978198 - 146687855 + 119297484;
        n ^= n >> (0xA2 ^ 0xAD);
        return n;
    }
    
    public static boolean isFogOff() {
        if (Config.gameSettings.ofFogType == "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void updateFramebufferSize() {
        Config.minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }
    
    public static void checkGlError(final String s) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            error(Config.I[0x80 ^ 0xBB] + glGetError + Config.I[0x9D ^ 0xA1] + GLU.gluErrorString(glGetError) + Config.I[0x30 ^ 0xD] + s);
        }
    }
    
    public static boolean isTreesFancy() {
        int n;
        if (Config.gameSettings.ofTrees == 0) {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofTrees == "  ".length()) {
            n = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static String readInputStream(final InputStream inputStream) throws IOException {
        return readInputStream(inputStream, Config.I[0x1 ^ 0x41]);
    }
    
    public static void log(final String s) {
        dbg(s);
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }
    
    public static boolean isNotify64BitJava() {
        return Config.notify64BitJava;
    }
    
    public static InputStream getResourceStream(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final IResource resource = resourceManager.getResource(resourceLocation);
        InputStream inputStream;
        if (resource == null) {
            inputStream = null;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            inputStream = resource.getInputStream();
        }
        return inputStream;
    }
    
    public static void updateTexturePackClouds() {
        Config.texturePackClouds = "".length();
        final IResourceManager resourceManager = getResourceManager();
        if (resourceManager != null) {
            try {
                final InputStream inputStream = resourceManager.getResource(new ResourceLocation(Config.I[0xAF ^ 0x86])).getInputStream();
                if (inputStream == null) {
                    return;
                }
                final Properties properties = new Properties();
                properties.load(inputStream);
                inputStream.close();
                final String property = properties.getProperty(Config.I[0x17 ^ 0x3D]);
                if (property == null) {
                    return;
                }
                dbg(Config.I[0xB5 ^ 0x9E] + property);
                final String lowerCase = property.toLowerCase();
                if (lowerCase.equals(Config.I[0x4E ^ 0x62])) {
                    Config.texturePackClouds = " ".length();
                }
                if (lowerCase.equals(Config.I[0x87 ^ 0xAA])) {
                    Config.texturePackClouds = "  ".length();
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }
    
    public static void initGameSettings(final GameSettings gameSettings) {
        Config.gameSettings = gameSettings;
        Config.minecraft = Minecraft.getMinecraft();
        Config.desktopDisplayMode = Display.getDesktopDisplayMode();
        updateAvailableProcessors();
    }
    
    private static void startVersionCheckThread() {
        new VersionCheckThread().start();
    }
    
    public static String fillRight(String s, final int n, final char c) {
        if (s == null) {
            s = Config.I[0xDD ^ 0x84];
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (sb.length() < n) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void checkInitialized() {
        if (!Config.initialized && Display.isCreated()) {
            Config.initialized = (" ".length() != 0);
            checkOpenGlCaps();
            startVersionCheckThread();
        }
    }
    
    public static String[] readLines(final File file) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Config.I[0xAC ^ 0x92]));
        do {
            final String line = bufferedReader.readLine();
            if (line == null) {
                return list.toArray(new String[list.size()]);
            }
            list.add(line);
            "".length();
        } while (3 >= 0);
        throw null;
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static boolean between(final int n, final int n2, final int n3) {
        if (n >= n2 && n <= n3) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static String arrayToString(final int[] array) {
        if (array == null) {
            return Config.I[0x70 ^ 0x44];
        }
        final StringBuffer sb = new StringBuffer(array.length * (0x12 ^ 0x17));
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < array.length) {
            final int n = array[i];
            if (i > 0) {
                sb.append(Config.I[0x24 ^ 0x11]);
            }
            sb.append(String.valueOf(n));
            ++i;
        }
        return sb.toString();
    }
    
    public static String getResourcePackNames() {
        if (Config.minecraft == null) {
            return Config.I[0x78 ^ 0x4E];
        }
        if (Config.minecraft.getResourcePackRepository() == null) {
            return Config.I[0xB0 ^ 0x87];
        }
        final IResourcePack[] resourcePacks = getResourcePacks();
        if (resourcePacks.length <= 0) {
            return getDefaultResourcePack().getPackName();
        }
        final String[] array = new String[resourcePacks.length];
        int i = "".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i < resourcePacks.length) {
            array[i] = resourcePacks[i].getPackName();
            ++i;
        }
        return arrayToString(array);
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }
    
    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }
    
    public static GameSettings getGameSettings() {
        return Config.gameSettings;
    }
    
    public static void updateThreadPriorities() {
        updateAvailableProcessors();
        if (isSingleProcessor()) {
            if (isSmoothWorld()) {
                Config.minecraftThread.setPriority(0x60 ^ 0x6A);
                setThreadPriority(Config.I[0x29 ^ 0xB], " ".length());
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                Config.minecraftThread.setPriority(0xA8 ^ 0xAD);
                setThreadPriority(Config.I[0x35 ^ 0x16], 0x6B ^ 0x6E);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
        }
        else {
            Config.minecraftThread.setPriority(0x83 ^ 0x89);
            setThreadPriority(Config.I[0xE4 ^ 0xC0], 0x47 ^ 0x42);
        }
    }
    
    public static String readInputStream(final InputStream inputStream, final String s) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, s));
        final StringBuffer sb = new StringBuffer();
        do {
            final String line = bufferedReader.readLine();
            if (line == null) {
                return sb.toString();
            }
            sb.append(line);
            sb.append(Config.I[0x27 ^ 0x66]);
            "".length();
        } while (4 != 0);
        throw null;
    }
    
    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }
    
    public static boolean isRainOff() {
        if (Config.gameSettings.ofRain == "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void dbg(final String s) {
        Config.systemOut.print(Config.I[0x21 ^ 0x7]);
        Config.systemOut.println(s);
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }
    
    public static String[] tokenize(final String s, final String s2) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, s2);
        final ArrayList<String> list = new ArrayList<String>();
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static int getMaxDynamicTileWidth() {
        return 0x83 ^ 0xC3;
    }
    
    public static boolean isGeneratedLava() {
        if (Config.gameSettings.ofAnimatedLava == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static Method getMethod(final Class clazz, final String s, final Object[] array) {
        final Method[] methods = clazz.getMethods();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < methods.length) {
            final Method method = methods[i];
            if (method.getName().equals(s) && method.getParameterTypes().length == array.length) {
                return method;
            }
            ++i;
        }
        warn(Config.I[0x31 ^ 0x1F] + clazz.getName() + Config.I[0x27 ^ 0x8] + s + Config.I[0x5C ^ 0x6C] + arrayToString(array) + Config.I[0x2A ^ 0x1B]);
        return null;
    }
    
    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }
    
    public static InputStream getResourceStream(final ResourceLocation resourceLocation) throws IOException {
        return getResourceStream(Config.minecraft.getResourceManager(), resourceLocation);
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }
    
    private static String getUpdates(final String s) {
        final int index = s.indexOf(0x5F ^ 0x77);
        if (index < 0) {
            return Config.I[0x1E ^ 0x4F];
        }
        final int index2 = s.indexOf(0x3 ^ 0x23, index);
        String substring;
        if (index2 < 0) {
            substring = Config.I[0x4D ^ 0x1F];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            substring = s.substring(index + " ".length(), index2);
        }
        return substring;
    }
    
    public static Dimension getFullscreenDimension() {
        if (Config.desktopDisplayMode == null) {
            return null;
        }
        if (Config.gameSettings == null) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String ofFullscreenMode = Config.gameSettings.ofFullscreenMode;
        if (ofFullscreenMode.equals(Config.I[0x98 ^ 0xA0])) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String[] tokenize = tokenize(ofFullscreenMode, Config.I[0x7C ^ 0x45]);
        Dimension dimension;
        if (tokenize.length < "  ".length()) {
            dimension = new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            dimension = new Dimension(parseInt(tokenize["".length()], -" ".length()), parseInt(tokenize[" ".length()], -" ".length()));
        }
        return dimension;
    }
    
    public static boolean isActing() {
        final boolean actingNow = isActingNow();
        final long currentTimeMillis = System.currentTimeMillis();
        if (actingNow) {
            Config.lastActionTime = currentTimeMillis;
            return " ".length() != 0;
        }
        if (currentTimeMillis - Config.lastActionTime < 100L) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isTimeNightOnly() {
        if (Config.gameSettings.ofTime == "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void initDisplay() {
        checkInitialized();
        Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
        checkDisplaySettings();
        checkDisplayMode();
        Config.minecraftThread = Thread.currentThread();
        updateThreadPriorities();
    }
    
    public static int limit(final int n, final int n2, final int n3) {
        int n4;
        if (n < n2) {
            n4 = n2;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (n > n3) {
            n4 = n3;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        return n4;
    }
    
    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }
    
    public static boolean isAnimatedLava() {
        if (Config.gameSettings.ofAnimatedLava != "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x6E ^ 0x34])["".length()] = I("?\u00036#?\u0019\u001d'\u0015H^Klr&87\u001d\u001f&5B", "psBJy");
        Config.I[" ".length()] = I("%3-\u0001", "mwrTZ");
        Config.I["  ".length()] = I("^}bhi", "oSZFQ");
        Config.I["   ".length()] = I("0^", "uoWRs");
        Config.I[0x54 ^ 0x50] = I("5=$\u0019!\u0013#5", "zMPpg");
        Config.I[0x2A ^ 0x2F] = I("\u00011\f\u001a/'/\u001d,X`yVK6\u0006\u0005'&6\u000bp", "NAxsi");
        Config.I[0x3C ^ 0x3A] = I("", "yJitO");
        Config.I[0xB9 ^ 0xBE] = I("\n\u0005Wl", "EVmLu");
        Config.I[0x8C ^ 0x84] = I("\t!i8\u0015\u000b7", "fRGVt");
        Config.I[0x1C ^ 0x15] = I("tK", "TcpCJ");
        Config.I[0x6D ^ 0x67] = I("\u0007!B'\u0011\u000b:", "hRlFc");
        Config.I[0x0 ^ 0xB] = I("Nl\u00025\u0000\u0014%\u001b>R", "gLtPr");
        Config.I[0x7 ^ 0xB] = I("9\u0015D\u0011*$\u0015\u0003\b!", "VfjgO");
        Config.I[0x94 ^ 0x99] = I("\u001e\u0005\u001c/rt", "TdjNH");
        Config.I[0xB ^ 0x5] = I("+\r\u0010\u0006b7\t\u0014\u0014%.\u0002", "AlfgL");
        Config.I[0x5C ^ 0x53] = I("ce", "OEjuN");
        Config.I[0x25 ^ 0x35] = I("\u0012-8\nl\u000e) \u000f-\n", "xLNkB");
        Config.I[0x5A ^ 0x4B] = I("\u0002\u000fSO", "TBioR");
        Config.I[0x54 ^ 0x46] = I("\r,\u001f\u000eo\u0011 G\u0001 \n(", "gMioA");
        Config.I[0x8 ^ 0x1B] = I("Rz", "rRvkb");
        Config.I[0x81 ^ 0x95] = I("\u0018\u0018\u0017\u0004C\u0004\u0014O\f\u0003\u0014\u0016", "ryaem");
        Config.I[0x73 ^ 0x66] = I("DXT", "mttNT");
        Config.I[0x7C ^ 0x6A] = I("\u00100\u0019\u0005H\f<A\u0012\u0003\u00145\u0000\u0016", "zQodf");
        Config.I[0x60 ^ 0x77] = I("\u001b?\u0000\b\u0014mH", "WhJOX");
        Config.I[0x44 ^ 0x5C] = I("!2\u000b8 \"xN", "nBnVg");
        Config.I[0x30 ^ 0x29] = I("|z=\u0004'#3$\u000fu", "PZKaU");
        Config.I[0x32 ^ 0x28] = I("\\C", "pcake");
        Config.I[0x77 ^ 0x6C] = I("'\u001c\u0004\u00036$L7\b\u0003\u001b\u0005\u000e\u0003KH", "hlamq");
        Config.I[0x20 ^ 0x3C] = I("<*\u001c%4?z4\"\u0003\u001e;\tk\u001f\u0016,\u001c'\u0000Iz7$\u0007S;\u000f*\u001a\u001f;\u001b'\u0016Sr>\u0007BAt>\u0007,'\u001f!\u001f&!\u001f&\u00062+\u00055\u000e%6\u0016P", "sZyKs");
        Config.I[0x37 ^ 0x2A] = I("\u0007*='\u0013\u0004z\u001e(:+#x/;/`x\u0007;<z9?5!69+8-zp\u000e\u0018\u0017\u0014\u000e\u00162'=\u0007-=;.9'7-s", "HZXIT");
        Config.I[0xAE ^ 0xB0] = I(":$)!\u00069t\u0003,\"\u0019!?<(\u001a:l,4\u00198%!&Ot\u0002 5U5:.(\u00195.#$U|\u000b\u0003\u001e4\u0006\u000e\u0010.\u00167 :2\u001c;\"\u00100\u00001>6h", "uTLOA");
        Config.I[0x20 ^ 0x3F] = I("\u001d\"7\u0007.%.o\u001a&(7:\u001c&p0&\u0014&jc", "PCOnC");
        Config.I[0x61 ^ 0x41] = I("\u0011", "iPFia");
        Config.I[0xA3 ^ 0x82] = I("H", "fHcEB");
        Config.I[0xAD ^ 0x8F] = I("!\u000e\u001f\u00041\u0000K\u0019\u001a&\u0017\n\t", "rkmrT");
        Config.I[0x1E ^ 0x3D] = I("\u0001\u001d\u0001\u00156 X\u0007\u000b!7\u0019\u0017", "RxscS");
        Config.I[0x3D ^ 0x19] = I("\u00007\u0006:\t!r\u0000$\u001e63\u0010", "SRtLl");
        Config.I[0x6D ^ 0x48] = I("Mc", "wCLLD");
        Config.I[0x5A ^ 0x7C] = I("3:1\u00168.\u001c/\u0007\fH", "huAbQ");
        Config.I[0xBF ^ 0x98] = I("\u001e\u000b\u001c\f'\u0003-\u0002\u001d\u0013e\u001f;9\u001c\u000b\u0019L", "EDlxN");
        Config.I[0x95 ^ 0xBD] = I("2\u0018!\u0010;/>?\u0001\u000fI\f\u00146\u0000&\u0005\fD", "iWQdR");
        Config.I[0xAF ^ 0x86] = I("\u0017\u0019\u001a2\u0007\u0019\u0012\u000f!\\\u0019\u0015\u0006<\u0001T\n\u0018<\u0003\u001f\b\u001e:\u0016\t", "zzjSs");
        Config.I[0x25 ^ 0xF] = I("2\u0004\u0005\u0003\u001e\"", "Qhjvz");
        Config.I[0x50 ^ 0x7B] = I("\u0003?\u001d8\u0016%?E<\u000241E/\u000f8/\u0001?Yw", "WZeLc");
        Config.I[0x13 ^ 0x3F] = I("\u00033\u0014 ", "eRgTe");
        Config.I[0x7 ^ 0x2A] = I("\n.\r%8", "lOcFA");
        Config.I[0x27 ^ 0x9] = I("\b7a>\u001520.7P 74=\u0014f>.!Jf", "FXASp");
        Config.I[0x54 ^ 0x7B] = I("z", "TXjaP");
        Config.I[0x23 ^ 0x13] = I("G", "oQNGL");
        Config.I[0x9B ^ 0xAA] = I("A", "hpYuM");
        Config.I[0xBD ^ 0x8F] = I("", "hhcjP");
        Config.I[0x81 ^ 0xB2] = I("[r", "wRoXc");
        Config.I[0x26 ^ 0x12] = I("", "gqmzO");
        Config.I[0x55 ^ 0x60] = I("mY", "Ayybm");
        Config.I[0x3E ^ 0x8] = I("", "OZHMv");
        Config.I[0x17 ^ 0x20] = I("", "Onzny");
        Config.I[0x22 ^ 0x1A] = I("\u0006)<3\f.8", "BLZRy");
        Config.I[0x7C ^ 0x45] = I("l\t", "LqRwh");
        Config.I[0x54 ^ 0x6E] = I("\u0012", "jBRNF");
        Config.I[0xA3 ^ 0x98] = I(")4\u001d! \n\u0001\n=\b\u0014~X", "fDxOg");
        Config.I[0xA5 ^ 0x99] = I("TF", "tndGq");
        Config.I[0x69 ^ 0x54] = I("SIr\r>@E", "zeRlJ");
        Config.I[0x1F ^ 0x21] = I("&\u000b\b3\"", "gXKzk");
        Config.I[0x33 ^ 0xC] = I("\u000f\t \u000e(", "NZcGa");
        Config.I[0x38 ^ 0x78] = I("9\u0007\u0010\n\u0011", "xTSCX");
        Config.I[0x6F ^ 0x2E] = I("l", "fpNIx");
        Config.I[0xDC ^ 0x9E] = I("", "nIBwT");
        Config.I[0x25 ^ 0x66] = I("", "xfOeu");
        Config.I[0x1 ^ 0x45] = I("", "XHQjI");
        Config.I[0xFB ^ 0xBE] = I("", "TMxHl");
        Config.I[0xD1 ^ 0x97] = I("", "mTxfM");
        Config.I[0xF2 ^ 0xB5] = I("", "ZVmjw");
        Config.I[0xF0 ^ 0xB8] = I("\f\".9R\u0019\u0010\u0002\b\u001e/\u0002UX", "Jqoxr");
        Config.I[0x58 ^ 0x11] = I("\u0004\"5\u0019\u001da#\"\u0002\u001b(> V)\u0012\u0011\u0006LO", "APGvo");
        Config.I[0x67 ^ 0x2D] = I("/", "WqsRU");
        Config.I[0x53 ^ 0x18] = I("%\u0003\u0006E2\u0018\u001d\u0006\u000bu\u0010\u0019\u0011\u0004,Q\u0002\u0010E\u001b$'/", "qkceU");
        Config.I[0x2B ^ 0x67] = I("%\u0007!H*\u0018\u0019!\u0006m\u0010\u001d6\t4Q\u00067H\u0003$#\b", "qoDhM");
        Config.I[0xE1 ^ 0xAC] = I("m\r%\u0003}m(oP", "MkUpQ");
        Config.I[0x66 ^ 0x28] = I("\u007fL\u001fTK", "SlZnk");
        Config.I[0xDD ^ 0x92] = I("b", "ILHxS");
        Config.I[0xFD ^ 0xAD] = I("XP\u0004Vy", "tpQlY");
        Config.I[0x8 ^ 0x59] = I("", "TrtOB");
        Config.I[0x3D ^ 0x6F] = I("", "OYlkU");
        Config.I[0xD9 ^ 0x8A] = I("6\u0006;\u0014\u0005\u0007\u0019\u0012\u001a\u001b\u0003\u0007|+OP]", "ftTsw");
        Config.I[0xC1 ^ 0x95] = I("'49L'&\"?L\"556L+;%2\u000e", "TAWbF");
        Config.I[0x4A ^ 0x1F] = I("\u000664O<\u00074w\u00178K;0\u00158\n=<", "eYYaU");
        Config.I[0x4C ^ 0x1A] = I("(\u0001X\u000f\u001b$\u001a", "Grvni");
        Config.I[0x4A ^ 0x1D] = I("bN", "Tzmlj");
        Config.I[0x6C ^ 0x34] = I("", "BDPfY");
        Config.I[0x5D ^ 0x4] = I("", "FaCxI");
    }
    
    public static boolean isRainFancy() {
        int n;
        if (Config.gameSettings.ofRain == 0) {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofRain == "  ".length()) {
            n = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static Minecraft getMinecraft() {
        return Config.minecraft;
    }
    
    public static boolean isConnectedTexturesFancy() {
        if (Config.gameSettings.ofConnectedTextures == "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
            final ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
            int i = "".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (i < availableDisplayModes.length) {
                final DisplayMode displayMode = availableDisplayModes[i];
                if (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency())) {
                    list.add(displayMode);
                }
                ++i;
            }
            final DisplayMode[] array = list.toArray(new DisplayMode[list.size()]);
            Arrays.sort(array, new Comparator() {
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    final DisplayMode displayMode = (DisplayMode)o;
                    final DisplayMode displayMode2 = (DisplayMode)o2;
                    int length;
                    if (displayMode.getWidth() != displayMode2.getWidth()) {
                        length = displayMode2.getWidth() - displayMode.getWidth();
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    else if (displayMode.getHeight() != displayMode2.getHeight()) {
                        length = displayMode2.getHeight() - displayMode.getHeight();
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                    else {
                        length = "".length();
                    }
                    return length;
                }
            });
            return array;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            final DisplayMode[] array2 = new DisplayMode[" ".length()];
            array2["".length()] = Config.desktopDisplayMode;
            return array2;
        }
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }
    
    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }
    
    private static String[] splitRelease(final String s) {
        if (s == null || s.length() <= 0) {
            final String[] array = new String["   ".length()];
            array["".length()] = Config.I[0x52 ^ 0x17];
            array[" ".length()] = Config.I[0xC0 ^ 0x86];
            array["  ".length()] = Config.I[0xE7 ^ 0xA0];
            return array;
        }
        final String substring = s.substring("".length(), " ".length());
        if (s.length() <= " ".length()) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = substring;
            array2[" ".length()] = Config.I[0xE8 ^ 0xAA];
            array2["  ".length()] = Config.I[0x59 ^ 0x1A];
            return array2;
        }
        int length = " ".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (length < s.length() && Character.isDigit(s.charAt(length))) {
            ++length;
        }
        final String substring2 = s.substring(" ".length(), length);
        if (length >= s.length()) {
            final String[] array3 = new String["   ".length()];
            array3["".length()] = substring;
            array3[" ".length()] = substring2;
            array3["  ".length()] = Config.I[0x47 ^ 0x3];
            return array3;
        }
        final String substring3 = s.substring(length);
        final String[] array4 = new String["   ".length()];
        array4["".length()] = substring;
        array4[" ".length()] = substring2;
        array4["  ".length()] = substring3;
        return array4;
    }
    
    public static boolean isAnimatedWater() {
        if (Config.gameSettings.ofAnimatedWater != "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void sleep(final long n) {
        try {
            Thread.currentThread();
            Thread.sleep(n);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }
    
    public static boolean isFogFast() {
        if (Config.gameSettings.ofFogType == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isConnectedModels() {
        return "".length() != 0;
    }
    
    public static boolean isSingleProcessor() {
        if (getAvailableProcessors() <= " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static String getVersion() {
        return Config.I[0x4B ^ 0x4E];
    }
    
    public static boolean equals(final Object o, final Object o2) {
        int n;
        if (o == o2) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (o == null) {
            n = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n = (o.equals(o2) ? 1 : 0);
        }
        return n != 0;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Config.desktopDisplayMode;
    }
    
    public static boolean isFromDefaultResourcePack(final ResourceLocation resourceLocation) {
        if (getDefiningResourcePack(resourceLocation) == getDefaultResourcePack()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static ByteBuffer readIconImage(final File file) throws IOException {
        final BufferedImage read = ImageIO.read(file);
        final int[] rgb = read.getRGB("".length(), "".length(), read.getWidth(), read.getHeight(), null, "".length(), read.getWidth());
        final ByteBuffer allocate = ByteBuffer.allocate((0x59 ^ 0x5D) * rgb.length);
        final int[] array;
        final int length = (array = rgb).length;
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < length) {
            final int n = array[i];
            allocate.putInt(n << (0xB5 ^ 0xBD) | (n >> (0x2 ^ 0x1A) & 96 + 119 + 31 + 9));
            ++i;
        }
        allocate.flip();
        return allocate;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }
    
    public static void updateAvailableProcessors() {
        Config.availableProcessors = Runtime.getRuntime().availableProcessors();
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }
    
    public static void checkDisplayMode() {
        try {
            if (Config.minecraft.isFullScreen()) {
                if (Config.fullscreenModeChecked) {
                    return;
                }
                Config.fullscreenModeChecked = (" ".length() != 0);
                Config.desktopModeChecked = ("".length() != 0);
                final DisplayMode displayMode = Display.getDisplayMode();
                final Dimension fullscreenDimension = getFullscreenDimension();
                if (fullscreenDimension == null) {
                    return;
                }
                if (displayMode.getWidth() == fullscreenDimension.width && displayMode.getHeight() == fullscreenDimension.height) {
                    return;
                }
                final DisplayMode displayMode2 = getDisplayMode(fullscreenDimension);
                if (displayMode2 == null) {
                    return;
                }
                Display.setDisplayMode(displayMode2);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = " ".length();
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = " ".length();
                }
                if (Config.minecraft.currentScreen != null) {
                    final ScaledResolution scaledResolution = new ScaledResolution(Config.minecraft);
                    Config.minecraft.currentScreen.setWorldAndResolution(Config.minecraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(Config.minecraft);
                updateFramebufferSize();
                Display.setFullscreen((boolean)(" ".length() != 0));
                Config.minecraft.gameSettings.updateVSync();
                GlStateManager.enableTexture2D();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                if (Config.desktopModeChecked) {
                    return;
                }
                Config.desktopModeChecked = (" ".length() != 0);
                Config.fullscreenModeChecked = ("".length() != 0);
                Config.minecraft.gameSettings.updateVSync();
                Display.update();
                GlStateManager.enableTexture2D();
                Display.setResizable((boolean)("".length() != 0));
                Display.setResizable((boolean)(" ".length() != 0));
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }
    
    public static String[] getFullscreenModes() {
        final DisplayMode[] fullscreenDisplayModes = getFullscreenDisplayModes();
        final String[] array = new String[fullscreenDisplayModes.length];
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < fullscreenDisplayModes.length) {
            final DisplayMode displayMode = fullscreenDisplayModes[i];
            array[i] = displayMode.getWidth() + Config.I[0x21 ^ 0x1B] + displayMode.getHeight();
            ++i;
        }
        return array;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }
    
    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }
    
    public static boolean isUseAlphaFunc() {
        if (getAlphaFuncLevel() > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static float limitTo1(final float n) {
        float n2;
        if (n < 0.0f) {
            n2 = 0.0f;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (n > 1.0f) {
            n2 = 1.0f;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    private static boolean isActingNow() {
        int n;
        if (Mouse.isButtonDown("".length())) {
            n = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = (Mouse.isButtonDown(" ".length()) ? 1 : 0);
        }
        return n != 0;
    }
    
    public static IResourcePack[] getResourcePacks() {
        final List<ResourcePackRepository.Entry> repositoryEntries = Config.minecraft.getResourcePackRepository().getRepositoryEntries();
        final ArrayList<IResourcePack> list = new ArrayList<IResourcePack>();
        final Iterator<ResourcePackRepository.Entry> iterator = repositoryEntries.iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.add(iterator.next().getResourcePack());
        }
        return list.toArray(new IResourcePack[list.size()]);
    }
    
    public static int getMipmapType() {
        if (Config.gameSettings == null) {
            return Config.DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9655 + 4698 - 4678 + 311;
            }
            case 1: {
                return 4316 + 1549 - 3411 + 7532;
            }
            case 2: {
                if (isMultiTexture()) {
                    return 7549 + 9020 - 13088 + 6504;
                }
                return 5878 + 6244 - 6151 + 4015;
            }
            case 3: {
                if (isMultiTexture()) {
                    return 4064 + 6403 - 8572 + 8092;
                }
                return 7991 + 4082 - 5546 + 3459;
            }
            default: {
                return 4342 + 2158 + 537 + 2949;
            }
        }
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }
    
    public static boolean isBetterGrass() {
        if (Config.gameSettings.ofBetterGrass != "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static boolean hasResource(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        try {
            if (resourceManager.getResource(resourceLocation) != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        catch (IOException ex) {
            return "".length() != 0;
        }
    }
    
    public static void checkDisplaySettings() {
        if (getAntialiasingLevel() > 0) {
            final int antialiasingLevel = getAntialiasingLevel();
            final DisplayMode displayMode = Display.getDisplayMode();
            dbg(Config.I[0x44 ^ 0xC] + antialiasingLevel);
            try {
                Display.destroy();
                Display.setDisplayMode(displayMode);
                Display.create(new PixelFormat().withDepthBits(0x65 ^ 0x7D).withSamples(antialiasingLevel));
                Display.setResizable((boolean)("".length() != 0));
                Display.setResizable((boolean)(" ".length() != 0));
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (LWJGLException ex) {
                warn(Config.I[0x2 ^ 0x4B] + antialiasingLevel + Config.I[0x55 ^ 0x1F]);
                ex.printStackTrace();
                try {
                    Display.setDisplayMode(displayMode);
                    Display.create(new PixelFormat().withDepthBits(0xE ^ 0x16));
                    Display.setResizable((boolean)("".length() != 0));
                    Display.setResizable((boolean)(" ".length() != 0));
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                catch (LWJGLException ex2) {
                    ex2.printStackTrace();
                    try {
                        Display.setDisplayMode(displayMode);
                        Display.create();
                        Display.setResizable((boolean)("".length() != 0));
                        Display.setResizable((boolean)(" ".length() != 0));
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                    }
                    catch (LWJGLException ex3) {
                        ex3.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static float limit(final float n, final float n2, final float n3) {
        float n4;
        if (n < n2) {
            n4 = n2;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (n > n3) {
            n4 = n3;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        return n4;
    }
    
    public static int getAntialiasingLevel() {
        return Config.antialiasingLevel;
    }
    
    public static TextureManager getTextureManager() {
        return Config.minecraft.getTextureManager();
    }
    
    public static String arrayToString(final Object[] array) {
        if (array == null) {
            return Config.I[0x58 ^ 0x6A];
        }
        final StringBuffer sb = new StringBuffer(array.length * (0xA ^ 0xF));
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < array.length) {
            final Object o = array[i];
            if (i > 0) {
                sb.append(Config.I[0xC ^ 0x3F]);
            }
            sb.append(String.valueOf(o));
            ++i;
        }
        return sb.toString();
    }
    
    public static IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        return Config.minecraft.getResourceManager().getResource(resourceLocation);
    }
    
    public static void error(final String s) {
        Config.systemOut.print(Config.I[0xB6 ^ 0x9E]);
        Config.systemOut.println(s);
    }
    
    public static int getBitsOs() {
        int n;
        if (System.getenv(Config.I[0xDB ^ 0x88]) != null) {
            n = (0x16 ^ 0x56);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n = (0x8E ^ 0xAE);
        }
        return n;
    }
    
    public static boolean isDroppedItemsFancy() {
        int n;
        if (Config.gameSettings.ofDroppedItems == 0) {
            n = (Config.gameSettings.fancyGraphics ? 1 : 0);
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (Config.gameSettings.ofDroppedItems == "  ".length()) {
            n = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }
    
    public static boolean isFancyFogAvailable() {
        return Config.fancyFogAvailable;
    }
    
    public static boolean isRandomMobs() {
        return Config.gameSettings.ofRandomMobs;
    }
    
    public static RenderGlobal getRenderGlobal() {
        RenderGlobal renderGlobal;
        if (Config.minecraft == null) {
            renderGlobal = null;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            renderGlobal = Config.minecraft.renderGlobal;
        }
        return renderGlobal;
    }
    
    public static boolean isLazyChunkLoading() {
        int n;
        if (!isSingleProcessor()) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = (Config.gameSettings.ofLazyChunkLoading ? 1 : 0);
        }
        return n != 0;
    }
    
    public static boolean isMinecraftThread() {
        if (Thread.currentThread() == Config.minecraftThread) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isOcclusionAvailable() {
        return Config.occlusionAvailable;
    }
    
    public static int compareRelease(final String s, final String s2) {
        final String[] splitRelease = splitRelease(s);
        final String[] splitRelease2 = splitRelease(s2);
        final String s3 = splitRelease["".length()];
        final String s4 = splitRelease2["".length()];
        if (!s3.equals(s4)) {
            return s3.compareTo(s4);
        }
        final int int1 = parseInt(splitRelease[" ".length()], -" ".length());
        final int int2 = parseInt(splitRelease2[" ".length()], -" ".length());
        if (int1 != int2) {
            return int1 - int2;
        }
        return splitRelease["  ".length()].compareTo(splitRelease2["  ".length()]);
    }
    
    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }
    
    public static void warn(final String s) {
        Config.systemOut.print(Config.I[0x4D ^ 0x6A]);
        Config.systemOut.println(s);
    }
    
    public static void setNotify64BitJava(final boolean notify64BitJava) {
        Config.notify64BitJava = notify64BitJava;
    }
    
    public static boolean isMultiTexture() {
        int n;
        if (getAnisotropicFilterLevel() > " ".length()) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (getAntialiasingLevel() > 0) {
            n = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static boolean isConnectedTextures() {
        if (Config.gameSettings.ofConnectedTextures != "   ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
