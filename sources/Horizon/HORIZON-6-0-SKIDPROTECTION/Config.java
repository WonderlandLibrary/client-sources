package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;
import java.lang.reflect.Array;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.PixelFormat;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.LWJGLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.util.Properties;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import java.util.Date;
import org.lwjgl.opengl.Display;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.PrintStream;
import org.lwjgl.opengl.DisplayMode;

public class Config
{
    public static final String HorizonCode_Horizon_È = "OptiFine";
    public static final String Â = "1.8";
    public static final String Ý = "HD_U";
    public static final String Ø­áŒŠá = "B2";
    public static final String Âµá€ = "OptiFine_1.8_HD_U_B2";
    private static String Šáƒ;
    public static String Ó;
    public static String à;
    public static String Ø;
    private static GameSettings Ï­Ðƒà;
    private static Minecraft áŒŠà;
    private static boolean ŠÄ;
    private static Thread Ñ¢á;
    private static DisplayMode ŒÏ;
    private static int Çªà¢;
    private static int Ê;
    public static boolean áŒŠÆ;
    private static int ÇŽÉ;
    public static boolean áˆºÑ¢Õ;
    private static boolean ˆá;
    private static boolean ÇŽÕ;
    private static PrintStream É;
    public static final Boolean ÂµÈ;
    public static final Float á;
    public static final Boolean ˆÏ­;
    public static final Boolean £á;
    public static final Integer Å;
    public static final Integer £à;
    public static final Float µà;
    public static final Boolean ˆà;
    public static final Integer ¥Æ;
    public static final Integer Ø­à;
    public static final Integer µÕ;
    public static final Boolean Æ;
    private static long áƒ;
    
    static {
        Config.Šáƒ = null;
        Config.Ó = null;
        Config.à = null;
        Config.Ø = null;
        Config.Ï­Ðƒà = null;
        Config.áŒŠà = null;
        Config.ŠÄ = false;
        Config.Ñ¢á = null;
        Config.ŒÏ = null;
        Config.Çªà¢ = 0;
        Config.Ê = 0;
        Config.áŒŠÆ = false;
        Config.ÇŽÉ = 0;
        Config.áˆºÑ¢Õ = false;
        Config.ˆá = false;
        Config.ÇŽÕ = false;
        Config.É = new PrintStream(new FileOutputStream(FileDescriptor.out));
        ÂµÈ = true;
        á = 0.2f;
        ˆÏ­ = false;
        £á = false;
        Å = 0;
        £à = 9984;
        µà = 0.1f;
        ˆà = false;
        ¥Æ = 0;
        Ø­à = 25;
        µÕ = 3;
        Æ = false;
        Config.áƒ = System.currentTimeMillis();
    }
    
    public static String HorizonCode_Horizon_È() {
        return "OptiFine_1.8_HD_U_B2";
    }
    
    public static void HorizonCode_Horizon_È(final GameSettings settings) {
        Config.Ï­Ðƒà = settings;
        Config.áŒŠà = Minecraft.áŒŠà();
        Config.ŒÏ = Display.getDesktopDisplayMode();
        áŒŠá€();
    }
    
    public static void Â() {
        Ý();
        Config.Çªà¢ = Config.Ï­Ðƒà.µà;
        Ðƒáƒ();
        Ðƒà();
        Config.Ñ¢á = Thread.currentThread();
        à();
    }
    
    public static void Ý() {
        if (!Config.ŠÄ && Display.isCreated()) {
            Config.ŠÄ = true;
            ÂµÕ();
            Ø­Ñ¢á€();
        }
    }
    
    private static void ÂµÕ() {
        Ø­áŒŠá("");
        Ø­áŒŠá(HorizonCode_Horizon_È());
        Ø­áŒŠá(new StringBuilder().append(new Date()).toString());
        Ø­áŒŠá("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        Ø­áŒŠá("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        Ø­áŒŠá("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        Ø­áŒŠá("LWJGL: " + Sys.getVersion());
        Config.Ó = GL11.glGetString(7938);
        Config.à = GL11.glGetString(7937);
        Config.Ø = GL11.glGetString(7936);
        Ø­áŒŠá("OpenGL: " + Config.à + ", version " + Config.Ó + ", " + Config.Ø);
        Ø­áŒŠá("OpenGL Version: " + Ó());
        if (!GLContext.getCapabilities().OpenGL12) {
            Ø­áŒŠá("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!GLContext.getCapabilities().GL_NV_fog_distance) {
            Ø­áŒŠá("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!GLContext.getCapabilities().GL_ARB_occlusion_query) {
            Ø­áŒŠá("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        final int maxTexSize = Minecraft.Ñ¢á();
        HorizonCode_Horizon_È("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
    }
    
    public static boolean Ø­áŒŠá() {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }
    
    public static boolean Âµá€() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
    
    public static String Ó() {
        final int ver = Š();
        final String verStr = ver / 10 + "." + ver % 10;
        return verStr;
    }
    
    private static int Š() {
        return GLContext.getCapabilities().OpenGL11 ? (GLContext.getCapabilities().OpenGL12 ? (GLContext.getCapabilities().OpenGL13 ? (GLContext.getCapabilities().OpenGL14 ? (GLContext.getCapabilities().OpenGL15 ? (GLContext.getCapabilities().OpenGL20 ? (GLContext.getCapabilities().OpenGL21 ? (GLContext.getCapabilities().OpenGL30 ? (GLContext.getCapabilities().OpenGL31 ? (GLContext.getCapabilities().OpenGL32 ? (GLContext.getCapabilities().OpenGL33 ? (GLContext.getCapabilities().OpenGL40 ? 40 : 33) : 32) : 31) : 30) : 21) : 20) : 15) : 14) : 13) : 12) : 11) : 10;
    }
    
    public static void à() {
        áŒŠá€();
        final boolean ELEVATED_PRIORITY = true;
        if (Šà() <= 1 && !ˆà¢()) {
            Config.Ñ¢á.setPriority(5);
            Â("Server thread", 5);
        }
        else {
            Config.Ñ¢á.setPriority(10);
            Â("Server thread", 1);
        }
    }
    
    private static void Â(final String prefix, final int priority) {
        try {
            final ThreadGroup e = Thread.currentThread().getThreadGroup();
            if (e == null) {
                return;
            }
            final int num = (e.activeCount() + 10) * 2;
            final Thread[] ts = new Thread[num];
            e.enumerate(ts, false);
            for (int i = 0; i < ts.length; ++i) {
                final Thread t = ts[i];
                if (t != null && t.getName().startsWith(prefix)) {
                    t.setPriority(priority);
                }
            }
        }
        catch (Throwable var7) {
            HorizonCode_Horizon_È(String.valueOf(var7.getClass().getName()) + ": " + var7.getMessage());
        }
    }
    
    public static boolean Ø() {
        return Thread.currentThread() == Config.Ñ¢á;
    }
    
    private static void Ø­Ñ¢á€() {
        final VersionCheckThread vct = new VersionCheckThread();
        vct.start();
    }
    
    public static int áŒŠÆ() {
        if (Config.Ï­Ðƒà == null) {
            return Config.£à;
        }
        switch (Config.Ï­Ðƒà.áˆºÑ¢Õ) {
            case 0: {
                return 9984;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (ˆÉ()) {
                    return 9985;
                }
                return 9986;
            }
            case 3: {
                if (ˆÉ()) {
                    return 9987;
                }
                return 9986;
            }
            default: {
                return 9984;
            }
        }
    }
    
    public static boolean áˆºÑ¢Õ() {
        final float alphaFuncLevel = ÂµÈ();
        return alphaFuncLevel > Config.µà + 1.0E-5f;
    }
    
    public static float ÂµÈ() {
        return Config.µà;
    }
    
    public static boolean á() {
        return Ø­áŒŠá() && Config.Ï­Ðƒà.Ø == 2;
    }
    
    public static boolean ˆÏ­() {
        return Config.Ï­Ðƒà.Ø == 1;
    }
    
    public static boolean £á() {
        return Config.Ï­Ðƒà.Ø == 3;
    }
    
    public static float Å() {
        return Config.Ï­Ðƒà.áŒŠÆ;
    }
    
    public static boolean £à() {
        return Config.Ï­Ðƒà.ÂµÈ;
    }
    
    public static int µà() {
        return Config.Ï­Ðƒà.á;
    }
    
    public static void HorizonCode_Horizon_È(final String s) {
        Config.É.print("[OptiFine] ");
        Config.É.println(s);
    }
    
    public static void Â(final String s) {
        Config.É.print("[OptiFine] [WARN] ");
        Config.É.println(s);
    }
    
    public static void Ý(final String s) {
        Config.É.print("[OptiFine] [ERROR] ");
        Config.É.println(s);
    }
    
    public static void Ø­áŒŠá(final String s) {
        HorizonCode_Horizon_È(s);
    }
    
    public static int ˆà() {
        return Config.Ï­Ðƒà.ÇŽÉ;
    }
    
    public static boolean ¥Æ() {
        return Config.Ï­Ðƒà.ÇŽÕ;
    }
    
    public static boolean Ø­à() {
        return (Config.Ï­Ðƒà.µÕ == 0) ? Config.Ï­Ðƒà.Û : (Config.Ï­Ðƒà.µÕ == 2);
    }
    
    public static boolean µÕ() {
        return Config.Ï­Ðƒà.µÕ == 3;
    }
    
    public static boolean Æ() {
        return (Config.Ï­Ðƒà.ˆà != 0) ? (Config.Ï­Ðƒà.ˆà == 2) : ((Config.ÇŽÉ != 0) ? (Config.ÇŽÉ == 2) : Config.Ï­Ðƒà.Û);
    }
    
    public static boolean Šáƒ() {
        return Config.Ï­Ðƒà.ˆà == 3;
    }
    
    public static void Ï­Ðƒà() {
        Config.ÇŽÉ = 0;
        final IResourceManager rm = ˆáŠ();
        if (rm != null) {
            try {
                final InputStream e = rm.HorizonCode_Horizon_È(new ResourceLocation_1975012498("mcpatcher/color.properties")).Â();
                if (e == null) {
                    return;
                }
                final Properties props = new Properties();
                props.load(e);
                e.close();
                String cloudStr = props.getProperty("clouds");
                if (cloudStr == null) {
                    return;
                }
                HorizonCode_Horizon_È("Texture pack clouds: " + cloudStr);
                cloudStr = cloudStr.toLowerCase();
                if (cloudStr.equals("fast")) {
                    Config.ÇŽÉ = 1;
                }
                if (cloudStr.equals("fancy")) {
                    Config.ÇŽÉ = 2;
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public static boolean áŒŠà() {
        return (Config.Ï­Ðƒà.Ø­à == 0) ? Config.Ï­Ðƒà.Û : (Config.Ï­Ðƒà.Ø­à == 2);
    }
    
    public static boolean ŠÄ() {
        return (Config.Ï­Ðƒà.Æ == 0) ? Config.Ï­Ðƒà.Û : (Config.Ï­Ðƒà.Æ == 2);
    }
    
    public static int HorizonCode_Horizon_È(final int val, final int min, final int max) {
        return (val < min) ? min : ((val > max) ? max : val);
    }
    
    public static float HorizonCode_Horizon_È(final float val, final float min, final float max) {
        return (val < min) ? min : ((val > max) ? max : val);
    }
    
    public static float HorizonCode_Horizon_È(final float val) {
        return (val < 0.0f) ? 0.0f : ((val > 1.0f) ? 1.0f : val);
    }
    
    public static boolean Ñ¢á() {
        return Config.Ï­Ðƒà.Ñ¢Â != 2;
    }
    
    public static boolean ŒÏ() {
        return Config.Ï­Ðƒà.Ñ¢Â == 1;
    }
    
    public static boolean Çªà¢() {
        return Config.Ï­Ðƒà.ÇŽá€;
    }
    
    public static boolean Ê() {
        return Config.Ï­Ðƒà.Ï­à != 2;
    }
    
    public static boolean ÇŽÉ() {
        return Config.Ï­Ðƒà.Ï­à == 1;
    }
    
    public static boolean ˆá() {
        return Config.Ï­Ðƒà.áˆºáˆºÈ;
    }
    
    public static boolean ÇŽÕ() {
        return Config.Ï­Ðƒà.Ï;
    }
    
    public static boolean É() {
        return Config.Ï­Ðƒà.Ô;
    }
    
    public static boolean áƒ() {
        return Config.Ï­Ðƒà.ÇªÓ;
    }
    
    public static boolean á€() {
        return Config.Ï­Ðƒà.áˆºÏ;
    }
    
    public static boolean Õ() {
        return Config.Ï­Ðƒà.ˆáƒ;
    }
    
    public static boolean à¢() {
        return Config.Ï­Ðƒà.Œ;
    }
    
    public static boolean ŠÂµà() {
        return Config.Ï­Ðƒà.£Ï;
    }
    
    public static boolean ¥à() {
        return Config.Ï­Ðƒà.Ø­á;
    }
    
    public static boolean Âµà() {
        return Config.Ï­Ðƒà.ˆÉ;
    }
    
    public static float Ç() {
        return Config.Ï­Ðƒà.£à;
    }
    
    private static Method HorizonCode_Horizon_È(final Class cls, final String methodName, final Object[] params) {
        final Method[] methods = cls.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            final Method m = methods[i];
            if (m.getName().equals(methodName) && m.getParameterTypes().length == params.length) {
                return m;
            }
        }
        Â("No method found for: " + cls.getName() + "." + methodName + "(" + HorizonCode_Horizon_È(params) + ")");
        return null;
    }
    
    public static String HorizonCode_Horizon_È(final Object[] arr) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final Object obj = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(obj));
        }
        return buf.toString();
    }
    
    public static String HorizonCode_Horizon_È(final int[] arr) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final int x = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(x));
        }
        return buf.toString();
    }
    
    public static Minecraft È() {
        return Config.áŒŠà;
    }
    
    public static TextureManager áŠ() {
        return Config.áŒŠà.¥à();
    }
    
    public static IResourceManager ˆáŠ() {
        return Config.áŒŠà.Âµà();
    }
    
    public static InputStream HorizonCode_Horizon_È(final ResourceLocation_1975012498 location) throws IOException {
        return HorizonCode_Horizon_È(Config.áŒŠà.Âµà(), location);
    }
    
    public static InputStream HorizonCode_Horizon_È(final IResourceManager resourceManager, final ResourceLocation_1975012498 location) throws IOException {
        final IResource res = resourceManager.HorizonCode_Horizon_È(location);
        return (res == null) ? null : res.Â();
    }
    
    public static IResource Â(final ResourceLocation_1975012498 location) throws IOException {
        return Config.áŒŠà.Âµà().HorizonCode_Horizon_È(location);
    }
    
    public static boolean Ý(final ResourceLocation_1975012498 location) {
        try {
            final IResource e = Â(location);
            return e != null;
        }
        catch (IOException var2) {
            return false;
        }
    }
    
    public static boolean Â(final IResourceManager resourceManager, final ResourceLocation_1975012498 location) {
        try {
            final IResource e = resourceManager.HorizonCode_Horizon_È(location);
            return e != null;
        }
        catch (IOException var3) {
            return false;
        }
    }
    
    public static IResourcePack[] áŒŠ() {
        final ResourcePackRepository rep = Config.áŒŠà.Ç();
        final List entries = rep.Ý();
        final ArrayList list = new ArrayList();
        for (final ResourcePackRepository.HorizonCode_Horizon_È entry : entries) {
            list.add(entry.Ý());
        }
        final IResourcePack[] rps2 = list.toArray(new IResourcePack[list.size()]);
        return rps2;
    }
    
    public static String £ÂµÄ() {
        if (Config.áŒŠà == null) {
            return "";
        }
        if (Config.áŒŠà.Ç() == null) {
            return "";
        }
        final IResourcePack[] rps = áŒŠ();
        if (rps.length <= 0) {
            return Ø­Âµ().Â();
        }
        final String[] names = new String[rps.length];
        for (int nameStr = 0; nameStr < rps.length; ++nameStr) {
            names[nameStr] = rps[nameStr].Â();
        }
        final String var3 = HorizonCode_Horizon_È(names);
        return var3;
    }
    
    public static IResourcePack Ø­Âµ() {
        return Config.áŒŠà.Ç().HorizonCode_Horizon_È;
    }
    
    public static boolean Ø­áŒŠá(final ResourceLocation_1975012498 loc) {
        final IResourcePack rp = Âµá€(loc);
        return rp == Ø­Âµ();
    }
    
    public static IResourcePack Âµá€(final ResourceLocation_1975012498 loc) {
        final IResourcePack[] rps = áŒŠ();
        for (int i = rps.length - 1; i >= 0; --i) {
            final IResourcePack rp = rps[i];
            if (rp.Â(loc)) {
                return rp;
            }
        }
        if (Ø­Âµ().Â(loc)) {
            return Ø­Âµ();
        }
        return null;
    }
    
    public static RenderGlobal Ä() {
        return (Config.áŒŠà == null) ? null : Config.áŒŠà.áˆºÑ¢Õ;
    }
    
    public static int Ñ¢Â() {
        return 64;
    }
    
    public static boolean Ï­à() {
        return Config.Ï­Ðƒà.Šáƒ != 3;
    }
    
    public static boolean áˆºáˆºÈ() {
        return Config.Ï­Ðƒà.Šáƒ == 2;
    }
    
    public static boolean ÇŽá€() {
        return Config.Ï­Ðƒà.Ñ¢á;
    }
    
    public static boolean Ï() {
        return Config.Ï­Ðƒà.ŒÏ;
    }
    
    public static boolean Ô() {
        return Config.Ï­Ðƒà.Ê;
    }
    
    public static boolean ÇªÓ() {
        return Config.Ï­Ðƒà.Çªà¢;
    }
    
    public static void HorizonCode_Horizon_È(final long ms) {
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        }
        catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }
    
    public static boolean áˆºÏ() {
        return Config.Ï­Ðƒà.É == 1;
    }
    
    public static boolean ˆáƒ() {
        return Config.Ï­Ðƒà.É == 0 || Config.Ï­Ðƒà.É == 2;
    }
    
    public static boolean Œ() {
        return Config.Ï­Ðƒà.É == 3;
    }
    
    public static boolean £Ï() {
        return Config.Ï­Ðƒà.áƒ;
    }
    
    public static int Ø­á() {
        return Config.Çªà¢;
    }
    
    public static boolean Â(final int val, final int min, final int max) {
        return val >= min && val <= max;
    }
    
    public static boolean ˆÉ() {
        return false;
    }
    
    public static boolean Ï­Ï­Ï() {
        return Config.Ï­Ðƒà.Ï­Ï­Ï;
    }
    
    public static boolean £Â() {
        return Config.Ï­Ðƒà.á€;
    }
    
    public static Dimension £Ó() {
        if (Config.ŒÏ == null) {
            return null;
        }
        if (Config.Ï­Ðƒà == null) {
            return new Dimension(Config.ŒÏ.getWidth(), Config.ŒÏ.getHeight());
        }
        final String dimStr = Config.Ï­Ðƒà.Õ;
        if (dimStr.equals("Default")) {
            return new Dimension(Config.ŒÏ.getWidth(), Config.ŒÏ.getHeight());
        }
        final String[] dimStrs = HorizonCode_Horizon_È(dimStr, " x");
        return (dimStrs.length < 2) ? new Dimension(Config.ŒÏ.getWidth(), Config.ŒÏ.getHeight()) : new Dimension(HorizonCode_Horizon_È(dimStrs[0], -1), HorizonCode_Horizon_È(dimStrs[1], -1));
    }
    
    public static int HorizonCode_Horizon_È(final String str, final int defVal) {
        try {
            return (str == null) ? defVal : Integer.parseInt(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }
    
    public static float HorizonCode_Horizon_È(final String str, final float defVal) {
        try {
            return (str == null) ? defVal : Float.parseFloat(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }
    
    public static String[] HorizonCode_Horizon_È(final String str, final String delim) {
        final StringTokenizer tok = new StringTokenizer(str, delim);
        final ArrayList list = new ArrayList();
        while (tok.hasMoreTokens()) {
            final String strs = tok.nextToken();
            list.add(strs);
        }
        final String[] strs2 = list.toArray(new String[list.size()]);
        return strs2;
    }
    
    public static DisplayMode ˆÐƒØ­à() {
        return Config.ŒÏ;
    }
    
    public static DisplayMode[] £Õ() {
        try {
            final DisplayMode[] e = Display.getAvailableDisplayModes();
            final ArrayList list = new ArrayList();
            for (int fsModes = 0; fsModes < e.length; ++fsModes) {
                final DisplayMode comp = e[fsModes];
                if (Config.ŒÏ == null || (comp.getBitsPerPixel() == Config.ŒÏ.getBitsPerPixel() && comp.getFrequency() == Config.ŒÏ.getFrequency())) {
                    list.add(comp);
                }
            }
            final DisplayMode[] var5 = list.toArray(new DisplayMode[list.size()]);
            final Comparator var6 = new Comparator() {
                @Override
                public int compare(final Object o1, final Object o2) {
                    final DisplayMode dm1 = (DisplayMode)o1;
                    final DisplayMode dm2 = (DisplayMode)o2;
                    return (dm1.getWidth() != dm2.getWidth()) ? (dm2.getWidth() - dm1.getWidth()) : ((dm1.getHeight() != dm2.getHeight()) ? (dm2.getHeight() - dm1.getHeight()) : 0);
                }
            };
            Arrays.sort(var5, var6);
            return var5;
        }
        catch (Exception var7) {
            var7.printStackTrace();
            return new DisplayMode[] { Config.ŒÏ };
        }
    }
    
    public static String[] Ï­Ô() {
        final DisplayMode[] modes = £Õ();
        final String[] names = new String[modes.length];
        for (int i = 0; i < modes.length; ++i) {
            final DisplayMode mode = modes[i];
            final String name = mode.getWidth() + "x" + mode.getHeight();
            names[i] = name;
        }
        return names;
    }
    
    public static DisplayMode HorizonCode_Horizon_È(final Dimension dim) throws LWJGLException {
        final DisplayMode[] modes = Display.getAvailableDisplayModes();
        for (int i = 0; i < modes.length; ++i) {
            final DisplayMode dm = modes[i];
            if (dm.getWidth() == dim.width && dm.getHeight() == dim.height && (Config.ŒÏ == null || (dm.getBitsPerPixel() == Config.ŒÏ.getBitsPerPixel() && dm.getFrequency() == Config.ŒÏ.getFrequency()))) {
                return dm;
            }
        }
        return Config.ŒÏ;
    }
    
    public static boolean Œà() {
        return Config.Ï­Ðƒà.£Â;
    }
    
    public static boolean Ðƒá() {
        return Config.Ï­Ðƒà.£Ó;
    }
    
    public static boolean ˆÏ() {
        return Config.Ï­Ðƒà.à¢;
    }
    
    public static boolean áˆºÇŽØ() {
        return Config.Ï­Ðƒà.ŠÂµà;
    }
    
    public static void Âµá€(final String loc) {
        final int i = GL11.glGetError();
        if (i != 0) {
            final String text = GLU.gluErrorString(i);
            Ý("OpenGlError: " + i + " (" + text + "), at: " + loc);
        }
    }
    
    public static boolean ÇªÂµÕ() {
        return Config.Ï­Ðƒà.¥à;
    }
    
    public static boolean áŒŠÏ() {
        return Config.Ï­Ðƒà.Ç;
    }
    
    public static boolean áŒŠáŠ() {
        return Config.Ï­Ðƒà.È;
    }
    
    public static boolean ˆÓ() {
        return Config.Ï­Ðƒà.Âµà;
    }
    
    public static boolean ¥Ä() {
        return Config.Ï­Ðƒà.áŠ;
    }
    
    public static boolean ÇªÔ() {
        return Config.Ï­Ðƒà.ˆáŠ != 3;
    }
    
    public static boolean Û() {
        return Config.Ï­Ðƒà.áŒŠ;
    }
    
    public static boolean ŠÓ() {
        return Config.Ï­Ðƒà.ˆáŠ == 2;
    }
    
    public static boolean ÇŽá() {
        return Config.Ï­Ðƒà.Ø­Âµ;
    }
    
    public static boolean Ñ¢à() {
        return (Config.Ï­Ðƒà.Ä == 0) ? Config.Ï­Ðƒà.Û : (Config.Ï­Ðƒà.Ä == 2);
    }
    
    public static String[] HorizonCode_Horizon_È(final File file) throws IOException {
        final ArrayList list = new ArrayList();
        final FileInputStream fis = new FileInputStream(file);
        final InputStreamReader isr = new InputStreamReader(fis, "ASCII");
        final BufferedReader br = new BufferedReader(isr);
        while (true) {
            final String lines = br.readLine();
            if (lines == null) {
                break;
            }
            list.add(lines);
        }
        final String[] lines2 = list.toArray(new String[list.size()]);
        return lines2;
    }
    
    public static String Â(final File file) throws IOException {
        final FileInputStream fin = new FileInputStream(file);
        return HorizonCode_Horizon_È(fin, "ASCII");
    }
    
    public static String HorizonCode_Horizon_È(final InputStream in) throws IOException {
        return HorizonCode_Horizon_È(in, "ASCII");
    }
    
    public static String HorizonCode_Horizon_È(final InputStream in, final String encoding) throws IOException {
        final InputStreamReader inr = new InputStreamReader(in, encoding);
        final BufferedReader br = new BufferedReader(inr);
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static GameSettings ÇªØ­() {
        return Config.Ï­Ðƒà;
    }
    
    public static String £áŒŠá() {
        return Config.Šáƒ;
    }
    
    public static void Ó(String newRelease) {
        newRelease = newRelease;
    }
    
    public static int Â(final String rel1, final String rel2) {
        final String[] rels1 = à(rel1);
        final String[] rels2 = à(rel2);
        final String branch1 = rels1[0];
        final String branch2 = rels2[0];
        if (!branch1.equals(branch2)) {
            return branch1.compareTo(branch2);
        }
        final int rev1 = HorizonCode_Horizon_È(rels1[1], -1);
        final int rev2 = HorizonCode_Horizon_È(rels2[1], -1);
        if (rev1 != rev2) {
            return rev1 - rev2;
        }
        final String suf1 = rels1[2];
        final String suf2 = rels2[2];
        return suf1.compareTo(suf2);
    }
    
    private static String[] à(final String relStr) {
        if (relStr == null || relStr.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final String branch = relStr.substring(0, 1);
        if (relStr.length() <= 1) {
            return new String[] { branch, "", "" };
        }
        int pos;
        for (pos = 1; pos < relStr.length() && Character.isDigit(relStr.charAt(pos)); ++pos) {}
        final String revision = relStr.substring(1, pos);
        if (pos >= relStr.length()) {
            return new String[] { branch, revision, "" };
        }
        final String suffix = relStr.substring(pos);
        return new String[] { branch, revision, suffix };
    }
    
    public static int HorizonCode_Horizon_È(int x) {
        x = (x ^ 0x3D ^ x >> 16);
        x += x << 3;
        x ^= x >> 4;
        x *= 668265261;
        x ^= x >> 15;
        return x;
    }
    
    public static int HorizonCode_Horizon_È(final BlockPos blockPos, final int face) {
        int rand = HorizonCode_Horizon_È(face + 37);
        rand = HorizonCode_Horizon_È(rand + blockPos.HorizonCode_Horizon_È());
        rand = HorizonCode_Horizon_È(rand + blockPos.Ý());
        rand = HorizonCode_Horizon_È(rand + blockPos.Â());
        return rand;
    }
    
    public static WorldServer áˆº() {
        if (Config.áŒŠà == null) {
            return null;
        }
        final WorldClient world = Config.áŒŠà.áŒŠÆ;
        if (world == null) {
            return null;
        }
        if (!Config.áŒŠà.Ê()) {
            return null;
        }
        final IntegratedServer is = Config.áŒŠà.ˆá();
        if (is == null) {
            return null;
        }
        final WorldProvider wp = world.£à;
        if (wp == null) {
            return null;
        }
        final int wd = wp.µà();
        try {
            final WorldServer e = is.HorizonCode_Horizon_È(wd);
            return e;
        }
        catch (NullPointerException var5) {
            return null;
        }
    }
    
    public static int Šà() {
        return Config.Ê;
    }
    
    public static void áŒŠá€() {
        Config.Ê = Runtime.getRuntime().availableProcessors();
    }
    
    public static boolean ¥Ï() {
        return Šà() <= 1;
    }
    
    public static boolean ˆà¢() {
        return Config.Ï­Ðƒà.£á;
    }
    
    public static boolean Ñ¢Ç() {
        return ¥Ï() && Config.Ï­Ðƒà.Å;
    }
    
    public static int £É() {
        if (Config.Ï­Ðƒà == null) {
            return 10;
        }
        final int chunkDistance = Config.Ï­Ðƒà.Ý;
        return chunkDistance;
    }
    
    public static boolean HorizonCode_Horizon_È(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }
    
    public static void Ðƒáƒ() {
        if (Ø­á() > 0) {
            final int samples = Ø­á();
            final DisplayMode displayMode = Display.getDisplayMode();
            HorizonCode_Horizon_È("FSAA Samples: " + samples);
            try {
                Display.destroy();
                Display.setDisplayMode(displayMode);
                Display.create(new PixelFormat().withDepthBits(24).withSamples(samples));
            }
            catch (LWJGLException var9) {
                Â("Error setting FSAA: " + samples + "x");
                var9.printStackTrace();
                try {
                    Display.setDisplayMode(displayMode);
                    Display.create(new PixelFormat().withDepthBits(24));
                }
                catch (LWJGLException var10) {
                    var10.printStackTrace();
                    try {
                        Display.setDisplayMode(displayMode);
                        Display.create();
                    }
                    catch (LWJGLException var11) {
                        var11.printStackTrace();
                    }
                }
            }
            if (Util_1252169911.HorizonCode_Horizon_È() != Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá) {
                try {
                    final File e = new File(Config.áŒŠà.ŒÏ, "assets");
                    final ByteBuffer bufIcon16 = Ý(new File(e, "/icons/icon_16x16.png"));
                    final ByteBuffer bufIcon17 = Ý(new File(e, "/icons/icon_32x32.png"));
                    final ByteBuffer[] buf = { bufIcon16, bufIcon17 };
                    Display.setIcon(buf);
                }
                catch (IOException var12) {
                    HorizonCode_Horizon_È(String.valueOf(var12.getClass().getName()) + ": " + var12.getMessage());
                }
            }
        }
    }
    
    private static ByteBuffer Ý(final File par1File) throws IOException {
        final BufferedImage var2 = ImageIO.read(par1File);
        final int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        final ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        final int[] var5 = var3;
        for (int var6 = var3.length, var7 = 0; var7 < var6; ++var7) {
            final int var8 = var5[var7];
            var4.putInt(var8 << 8 | (var8 >> 24 & 0xFF));
        }
        var4.flip();
        return var4;
    }
    
    public static void Ðƒà() {
        try {
            if (Config.áŒŠà.á€()) {
                if (Config.ˆá) {
                    return;
                }
                Config.ˆá = true;
                Config.ÇŽÕ = false;
                final DisplayMode e = Display.getDisplayMode();
                final Dimension dim = £Ó();
                if (dim == null) {
                    return;
                }
                if (e.getWidth() == dim.width && e.getHeight() == dim.height) {
                    return;
                }
                final DisplayMode newMode = HorizonCode_Horizon_È(dim);
                if (newMode == null) {
                    return;
                }
                Display.setDisplayMode(newMode);
                Config.áŒŠà.Ó = Display.getDisplayMode().getWidth();
                Config.áŒŠà.à = Display.getDisplayMode().getHeight();
                if (Config.áŒŠà.Ó <= 0) {
                    Config.áŒŠà.Ó = 1;
                }
                if (Config.áŒŠà.à <= 0) {
                    Config.áŒŠà.à = 1;
                }
                if (Config.áŒŠà.¥Æ != null) {
                    final ScaledResolution sr = new ScaledResolution(Config.áŒŠà, Config.áŒŠà.Ó, Config.áŒŠà.à);
                    final int sw = sr.HorizonCode_Horizon_È();
                    final int sh = sr.Â();
                    Config.áŒŠà.¥Æ.HorizonCode_Horizon_È(Config.áŒŠà, sw, sh);
                }
                Config.áŒŠà.Ø­à = new LoadingScreenRenderer(Config.áŒŠà);
                Ñ¢Ó();
                Display.setFullscreen(true);
                Config.áŒŠà.ŠÄ.áŒŠÆ();
                GlStateManager.µÕ();
            }
            else {
                if (Config.ÇŽÕ) {
                    return;
                }
                Config.ÇŽÕ = true;
                Config.ˆá = false;
                Config.áŒŠà.ŠÄ.áŒŠÆ();
                Display.update();
                GlStateManager.µÕ();
            }
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }
    
    private static void Ñ¢Ó() {
        Config.áŒŠà.Ý().HorizonCode_Horizon_È(Config.áŒŠà.Ó, Config.áŒŠà.à);
        if (Config.áŒŠà.µÕ != null) {
            Config.áŒŠà.µÕ.HorizonCode_Horizon_È(Config.áŒŠà.Ó, Config.áŒŠà.à);
        }
    }
    
    public static Object[] HorizonCode_Horizon_È(final Object[] arr, final Object obj) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int arrLen = arr.length;
        final int newLen = arrLen + 1;
        final Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }
    
    public static Object[] HorizonCode_Horizon_È(final Object[] arr, final Object[] objs) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objs.length == 0) {
            return arr;
        }
        final int arrLen = arr.length;
        final int newLen = arrLen + objs.length;
        final Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        System.arraycopy(objs, 0, newArr, arrLen, objs.length);
        return newArr;
    }
    
    public static boolean ¥É() {
        return false;
    }
    
    public static boolean £ÇªÓ() {
        final boolean acting = Ø­Æ();
        final long timeNowMs = System.currentTimeMillis();
        if (acting) {
            Config.áƒ = timeNowMs;
            return true;
        }
        return timeNowMs - Config.áƒ < 100L;
    }
    
    private static boolean Ø­Æ() {
        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
    }
}
