package net.minecraft.client.renderer;

import optfine.*;
import net.minecraft.client.settings.*;
import oshi.*;
import oshi.hardware.*;
import net.minecraft.client.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class OpenGlHelper
{
    private static boolean openGL14;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    public static int GL_COMPILE_STATUS;
    public static int GL_ARRAY_BUFFER;
    private static boolean shadersAvailable;
    public static int GL_COMBINE_RGB;
    private static final String __OBFID;
    public static int GL_LINK_STATUS;
    public static boolean field_181063_b;
    public static int GL_SOURCE1_ALPHA;
    public static boolean vboSupported;
    public static int GL_OPERAND0_RGB;
    private static String field_183030_aa;
    public static boolean field_181062_Q;
    public static boolean extBlendFuncSeparate;
    public static int GL_TEXTURE2;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    public static int GL_DEPTH_ATTACHMENT;
    public static int GL_SOURCE1_RGB;
    public static int defaultTexUnit;
    public static int GL_OPERAND1_ALPHA;
    public static int GL_COMBINE_ALPHA;
    public static int GL_COLOR_ATTACHMENT0;
    public static int GL_FRAMEBUFFER;
    public static int GL_OPERAND1_RGB;
    public static int GL_OPERAND2_ALPHA;
    private static final String[] I;
    public static int GL_INTERPOLATE;
    private static int framebufferType;
    private static boolean arbTextureEnvCombine;
    public static int GL_COMBINE;
    public static int GL_STATIC_DRAW;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static float lastBrightnessX;
    public static int GL_SOURCE2_ALPHA;
    private static String logText;
    public static int lightmapTexUnit;
    public static boolean shadersSupported;
    public static boolean openGL21;
    public static int GL_PRIMARY_COLOR;
    public static int GL_FRAGMENT_SHADER;
    private static boolean arbMultitexture;
    public static int GL_VERTEX_SHADER;
    public static int GL_SOURCE2_RGB;
    public static boolean nvidia;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static int GL_OPERAND2_RGB;
    public static int GL_FRAMEBUFFER_COMPLETE;
    private static boolean arbShaders;
    public static int GL_SOURCE0_RGB;
    public static int GL_OPERAND0_ALPHA;
    public static int GL_PREVIOUS;
    private static boolean arbVbo;
    public static boolean framebufferSupported;
    public static float lastBrightnessY;
    public static int GL_SOURCE0_ALPHA;
    public static int GL_CONSTANT;
    public static int GL_RENDERBUFFER;
    
    public static void glUniform1(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(n, intBuffer);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            GL20.glUniform1(n, intBuffer);
        }
    }
    
    public static String func_183029_j() {
        String field_183030_aa;
        if (OpenGlHelper.field_183030_aa == null) {
            field_183030_aa = OpenGlHelper.I[0xEE ^ 0xAE];
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            field_183030_aa = OpenGlHelper.field_183030_aa;
        }
        return field_183030_aa;
    }
    
    private static void I() {
        (I = new String[0xE6 ^ 0xA7])["".length()] = I("\u000f?+FI|CEGNu", "Lstvy");
        OpenGlHelper.I[" ".length()] = I("", "VrDkj");
        OpenGlHelper.I["  ".length()] = I("-=\u0010<\rX\u000f+\u00105\u0015;\u0015&\u0003\f+\u0001&\u001f\n+WX", "xNyRj");
        OpenGlHelper.I["   ".length()] = I(",\u0016\u000f/5Y\"*acWVF,'\u0015\u0011\u000f57\u0001\u0011\u00133;\u0017\u0002HK", "yefAR");
        OpenGlHelper.I[0x12 ^ 0x16] = I("\u0016#\u000e\u00187c\u001154\u000f75\u001f\u0002%158\u0013>5\u000f\u0004\u0019=!9\t\u0013~I", "CPgvP");
        OpenGlHelper.I[0x15 ^ 0x10] = I("\r\u0015.\u001f\u001fx!\u000bQIvUg\u0005\u001d \u00122\u0003\u001dx\u0005(\u001c\u001a1\b\"\u0003\u000bvl", "XfGqx");
        OpenGlHelper.I[0xA8 ^ 0xAE] = I("\u001f\u0011\u0006,1j\u0004\u001d#;/\u0000\u001a$0/\u0010O-4 \u0007\f6%j\u0000\n!7?\u0011\nb", "JboBV");
        OpenGlHelper.I[0x25 ^ 0x22] = I("\u001f\u001e#;?\u001cNu{Hp\u00075u\u000b%\u001e6:\n$\u000b\"u\u0019>\nf&\u001d \u000f44\f5N$9\u001d>\n/;\u001fp\u00075u\u000b%\u001e6:\n$\u000b\"{r", "PnFUx");
        OpenGlHelper.I[0x82 ^ 0x8A] = I("\f $\u001c\u0005?\u0013\u000b&\u00018\u0014\u0000&\u0011\u0012\u001d\u0004)\u0006.\u0006F*\u0010m\u0001\u00133\u0013\"\u0000\u0012&\u0007m\u0013\b'C>\u0017\u0016\"\u0011,\u0006\u0003c\u0001!\u0017\b'\n#\u0015F*\u0010m\u0001\u00133\u0013\"\u0000\u0012&\u0007cx", "MrfCc");
        OpenGlHelper.I[0x6B ^ 0x62] = I("\r\u001f\u0012\u001e/:&+$+=! $;\u0017($+,+3f(:h4319'52$-fM", "HGFAI");
        OpenGlHelper.I[0x1F ^ 0x15] = I("*\r\u0007U$\u0017\u000b\u001d\u0012q\u0002\u0010\u0012\u00184\u0006\u0017\u0015\u00134\u0016B\u001c\u0017;\u0001\u0001\u0007\u0006q\u0006\u0007\u0010\u0014$\u0017\u0007S", "dbsuQ");
        OpenGlHelper.I[0x0 ^ 0xB] = I("\u00062\t)$\u0005b]iWi+\u001fg", "IBlGc");
        OpenGlHelper.I[0x80 ^ 0x8C] = I("", "tlPxI");
        OpenGlHelper.I[0x3C ^ 0x31] = I("-79E", "CXMev");
        OpenGlHelper.I[0x35 ^ 0x3B] = I("\u001f\u001f\u001b\u0003\f\u001e\u001e\u000e\u0017OL", "ljksc");
        OpenGlHelper.I[0x92 ^ 0x9D] = I("35\u000282\u001a\b8\u0003\u000f\u0010\u00188\u0004\u000f\u0005\b&\u0006\"\u0017\u00193G9\u0005M", "vmVgP");
        OpenGlHelper.I[0x4B ^ 0x5B] = I("", "ySJNe");
        OpenGlHelper.I[0x30 ^ 0x21] = I(" -!W", "NBUwC");
        OpenGlHelper.I[0x47 ^ 0x55] = I("\u0016\u00168\u001d \u0017\u0017-\tcE", "ecHmO");
        OpenGlHelper.I[0x1F ^ 0xC] = I("\b\u0015)\u00000\u000bE\u007f@Gg\f?N", "GeLnw");
        OpenGlHelper.I[0x7A ^ 0x6E] = I("", "tIPNI");
        OpenGlHelper.I[0x4 ^ 0x11] = I("-:\u0005i", "CUqIj");
        OpenGlHelper.I[0x27 ^ 0x31] = I("4\u0000\u0018\u001995\u0001\r\rzg", "GuhiV");
        OpenGlHelper.I[0x8C ^ 0x9B] = I("\u0005'!*\u00176\u0014\u000e\u0010\u00131\u0013\u0005\u0010\u0003\u001b\u001a\u0001\u001f\u0014'\u0001C\u001c\u0002d", "Ducuq");
        OpenGlHelper.I[0x56 ^ 0x4E] = I("", "lyLwK");
        OpenGlHelper.I[0x15 ^ 0xC] = I("\n\u00019R", "dnMrf");
        OpenGlHelper.I[0x3 ^ 0x19] = I("\u0003\u0018?(;\u0002\u0019*<xP\f!<t", "pmOXT");
        OpenGlHelper.I[0x63 ^ 0x78] = I("\u0013\u0010\u001e\u0017 $)'-$#.,-4\t'(\"#5<j!5v", "VHJHF");
        OpenGlHelper.I[0x5B ^ 0x47] = I("", "GUayN");
        OpenGlHelper.I[0x6D ^ 0x70] = I("7%7d", "YJCDE");
        OpenGlHelper.I[0xBF ^ 0xA1] = I(";\u001b\n6!:\u001a\u001f\"`B", "HnzFN");
        OpenGlHelper.I[0xBA ^ 0xA5] = I("&\u001f76\r\u0007\u0004v3\u001a\u0010W", "uwVRh");
        OpenGlHelper.I[0x3A ^ 0x1A] = I("", "nIYSb");
        OpenGlHelper.I[0x96 ^ 0xB7] = I("(,\u001cQ", "FChqR");
        OpenGlHelper.I[0x8C ^ 0xAE] = I("#\u001b$ \u001d#\u000f),Q \b&(\u00041\be", "BmEIq");
        OpenGlHelper.I[0x8 ^ 0x2B] = I("+\u0014\n\u00163(D]VED\r\u001cX\u0007\u0011\u0014\u001f\u0017\u0006\u0010\u0001\u000bV~", "ddoxt");
        OpenGlHelper.I[0x9C ^ 0xB8] = I("\u0014\u001f6\u0017+=,\u0010-*\n\"\u0016\"=69\u0007dx\u0014\u001f6\u0017.0?\u0000- \n>\u001c)<0?Xh9;)T\t\n\u0017\u0012\u0012:92 \u0011&,\n>\u001c)<0?T)*0m\u0007=(%\"\u0006<=1c~", "UMtHX");
        OpenGlHelper.I[0xBE ^ 0x9B] = I("\u001f\u001a\u001d'\f\u001cJJgzp\u0003\u000bi", "PjxIK");
        OpenGlHelper.I[0x88 ^ 0xAE] = I("", "sBVct");
        OpenGlHelper.I[0x70 ^ 0x57] = I("\u0001\u0005\"a", "ojVAa");
        OpenGlHelper.I[0x95 ^ 0xBD] = I(">/\u0000\u001a\u000e?.\u0015\u000eMm", "MZpja");
        OpenGlHelper.I[0x3E ^ 0x17] = I("7\u0019\f> \u001e**\u0004!)$,\u000b6\u0015?=A:\u0005k", "vKNaS");
        OpenGlHelper.I[0x1C ^ 0x36] = I("", "ZEnJL");
        OpenGlHelper.I[0x5 ^ 0x2E] = I("%\u001f\u001eg", "KpjGV");
        OpenGlHelper.I[0x62 ^ 0x4E] = I("\u0007;\u0014\u0005-\u0006:\u0001\u0011nT", "tNduB");
        OpenGlHelper.I[0x7D ^ 0x50] = I("5!\u0010\u0018:\u0011\u0001&\"4+\u0000:&(\u0011\u0001r.?T", "tsRGL");
        OpenGlHelper.I[0x85 ^ 0xAB] = I("", "fzyHg");
        OpenGlHelper.I[0x2 ^ 0x2D] = I("*\u000b\u000en", "DdzNG");
        OpenGlHelper.I[0x38 ^ 0x8] = I("7\f\u001b4\u00156\r\u000e Vd\u0018\u0005 Z", "DykDz");
        OpenGlHelper.I[0xF3 ^ 0xC2] = I("\u0016\b \u0007\u001c%;\u00055\u001f9.=+\u00126>\u0007*Z>)B", "WZbXz");
        OpenGlHelper.I[0x62 ^ 0x50] = I("", "mSNmm");
        OpenGlHelper.I[0xB3 ^ 0x80] = I(")>>z", "GQJZr");
        OpenGlHelper.I[0x2B ^ 0x1F] = I("\u0000\u0002\u0000\u0000+\u0001\u0003\u0015\u0014jy", "swppD");
        OpenGlHelper.I[0x7A ^ 0x4F] = I("(\u0013\u001f\u001e?'", "FevzV");
        OpenGlHelper.I[0x77 ^ 0x41] = I(";\u0001\u0019\u0014x\f13G", "mCVgX");
        OpenGlHelper.I[0x73 ^ 0x44] = I("", "iQvDr");
        OpenGlHelper.I[0x27 ^ 0x1F] = I("\u0005\u001f0S", "kpDsv");
        OpenGlHelper.I[0xB3 ^ 0x8A] = I(")\u000e\u00113/)\u001a\u001c?c*\u001d\u0013;6;\u001dP", "HxpZC");
        OpenGlHelper.I[0xB0 ^ 0x8A] = I("*\u0013\u000f\u001b \u000e39!.4#8\"0\u000e3\u0012+4\u0001$.0v\u00022m7#\u001b1\"6\"\u000e%cN", "kAMDV");
        OpenGlHelper.I[0x5A ^ 0x61] = I(",9/\u0017\u0005/i{WwC 9Y1\u00169:\u00160\u0017,.WH", "cIJyB");
        OpenGlHelper.I[0x1F ^ 0x23] = I("# \u000e", "BTgPH");
        OpenGlHelper.I[0x77 ^ 0x4A] = I("F/*YI\u0010", "cKRyl");
        OpenGlHelper.I[0x26 ^ 0x18] = I("%=F", "yNmqA");
        OpenGlHelper.I[0x80 ^ 0xBF] = I("W", "wyGCc");
        OpenGlHelper.I[0x0 ^ 0x40] = I("L&+\u0011\t\u001f$+D", "pSEzg");
    }
    
    static {
        I();
        __OBFID = OpenGlHelper.I["".length()];
        OpenGlHelper.logText = OpenGlHelper.I[" ".length()];
        OpenGlHelper.lastBrightnessX = 0.0f;
        OpenGlHelper.lastBrightnessY = 0.0f;
    }
    
    public static int glGenFramebuffers() {
        if (!OpenGlHelper.framebufferSupported) {
            return -" ".length();
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glGenFramebuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    public static void initializeTextures() {
        Config.initDisplay();
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        int arbMultitexture;
        if (capabilities.GL_ARB_multitexture && !capabilities.OpenGL13) {
            arbMultitexture = " ".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            arbMultitexture = "".length();
        }
        OpenGlHelper.arbMultitexture = (arbMultitexture != 0);
        int arbTextureEnvCombine;
        if (capabilities.GL_ARB_texture_env_combine && !capabilities.OpenGL13) {
            arbTextureEnvCombine = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            arbTextureEnvCombine = "".length();
        }
        OpenGlHelper.arbTextureEnvCombine = (arbTextureEnvCombine != 0);
        if (OpenGlHelper.arbMultitexture) {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I["  ".length()];
            OpenGlHelper.defaultTexUnit = 26250 + 17043 - 24927 + 15618;
            OpenGlHelper.lightmapTexUnit = 19891 + 1766 + 3281 + 9047;
            OpenGlHelper.GL_TEXTURE2 = 17841 + 27708 - 40741 + 29178;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I["   ".length()];
            OpenGlHelper.defaultTexUnit = 21978 + 9692 - 1802 + 4116;
            OpenGlHelper.lightmapTexUnit = 23426 + 9224 - 29880 + 31215;
            OpenGlHelper.GL_TEXTURE2 = 15655 + 5201 + 10189 + 2941;
        }
        if (OpenGlHelper.arbTextureEnvCombine) {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x4C ^ 0x48];
            OpenGlHelper.GL_COMBINE = 17486 + 12995 - 15595 + 19274;
            OpenGlHelper.GL_INTERPOLATE = 23178 + 29558 - 47198 + 28627;
            OpenGlHelper.GL_PRIMARY_COLOR = 23921 + 29904 - 48808 + 29150;
            OpenGlHelper.GL_CONSTANT = 29908 + 30054 - 55718 + 29922;
            OpenGlHelper.GL_PREVIOUS = 16631 + 20903 - 36967 + 33601;
            OpenGlHelper.GL_COMBINE_RGB = 32897 + 33279 - 58931 + 26916;
            OpenGlHelper.GL_SOURCE0_RGB = 25756 + 4513 - 23129 + 27036;
            OpenGlHelper.GL_SOURCE1_RGB = 26635 + 32272 - 39695 + 14965;
            OpenGlHelper.GL_SOURCE2_RGB = 15370 + 1435 - 6942 + 24315;
            OpenGlHelper.GL_OPERAND0_RGB = 28221 + 11883 - 21667 + 15755;
            OpenGlHelper.GL_OPERAND1_RGB = 11489 + 28889 - 32412 + 26227;
            OpenGlHelper.GL_OPERAND2_RGB = 3931 + 1533 + 22060 + 6670;
            OpenGlHelper.GL_COMBINE_ALPHA = 24506 + 7991 - 6218 + 7883;
            OpenGlHelper.GL_SOURCE0_ALPHA = 25463 + 6719 - 6462 + 8464;
            OpenGlHelper.GL_SOURCE1_ALPHA = 15965 + 20085 - 12292 + 10427;
            OpenGlHelper.GL_SOURCE2_ALPHA = 12545 + 26898 - 26136 + 20879;
            OpenGlHelper.GL_OPERAND0_ALPHA = 1635 + 25663 - 22465 + 29367;
            OpenGlHelper.GL_OPERAND1_ALPHA = 28427 + 31929 - 59571 + 33416;
            OpenGlHelper.GL_OPERAND2_ALPHA = 15176 + 11454 - 5657 + 13229;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x20 ^ 0x25];
            OpenGlHelper.GL_COMBINE = 5579 + 22936 - 3757 + 9402;
            OpenGlHelper.GL_INTERPOLATE = 1541 + 14344 - 8830 + 27110;
            OpenGlHelper.GL_PRIMARY_COLOR = 6515 + 23322 - 2189 + 6519;
            OpenGlHelper.GL_CONSTANT = 26693 + 10450 - 32939 + 29962;
            OpenGlHelper.GL_PREVIOUS = 5042 + 18950 - 19240 + 29416;
            OpenGlHelper.GL_COMBINE_RGB = 11035 + 23719 - 30569 + 29976;
            OpenGlHelper.GL_SOURCE0_RGB = 29093 + 22090 - 30734 + 13727;
            OpenGlHelper.GL_SOURCE1_RGB = 23514 + 26687 - 21286 + 5262;
            OpenGlHelper.GL_SOURCE2_RGB = 26913 + 32507 - 27328 + 2086;
            OpenGlHelper.GL_OPERAND0_RGB = 15890 + 16840 - 32169 + 33631;
            OpenGlHelper.GL_OPERAND1_RGB = 233 + 30411 + 145 + 3404;
            OpenGlHelper.GL_OPERAND2_RGB = 24894 + 21209 - 35485 + 23576;
            OpenGlHelper.GL_COMBINE_ALPHA = 11053 + 26885 - 33426 + 29650;
            OpenGlHelper.GL_SOURCE0_ALPHA = 21963 + 13087 - 25217 + 24351;
            OpenGlHelper.GL_SOURCE1_ALPHA = 12464 + 22425 - 28631 + 27927;
            OpenGlHelper.GL_SOURCE2_ALPHA = 15933 + 3836 + 5128 + 9289;
            OpenGlHelper.GL_OPERAND0_ALPHA = 17593 + 31659 - 35859 + 20807;
            OpenGlHelper.GL_OPERAND1_ALPHA = 7470 + 11865 - 5364 + 20230;
            OpenGlHelper.GL_OPERAND2_ALPHA = 31323 + 21402 - 22092 + 3569;
        }
        int extBlendFuncSeparate;
        if (capabilities.GL_EXT_blend_func_separate && !capabilities.OpenGL14) {
            extBlendFuncSeparate = " ".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            extBlendFuncSeparate = "".length();
        }
        OpenGlHelper.extBlendFuncSeparate = (extBlendFuncSeparate != 0);
        int openGL14;
        if (!capabilities.OpenGL14 && !capabilities.GL_EXT_blend_func_separate) {
            openGL14 = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            openGL14 = " ".length();
        }
        OpenGlHelper.openGL14 = (openGL14 != 0);
        int framebufferSupported;
        if (OpenGlHelper.openGL14 && (capabilities.GL_ARB_framebuffer_object || capabilities.GL_EXT_framebuffer_object || capabilities.OpenGL30)) {
            framebufferSupported = " ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            framebufferSupported = "".length();
        }
        OpenGlHelper.framebufferSupported = (framebufferSupported != 0);
        if (OpenGlHelper.framebufferSupported) {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x71 ^ 0x77];
            if (capabilities.OpenGL30) {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x5E ^ 0x59];
                OpenGlHelper.framebufferType = "".length();
                OpenGlHelper.GL_FRAMEBUFFER = 15296 + 1025 + 16599 + 3240;
                OpenGlHelper.GL_RENDERBUFFER = 14234 + 21302 - 10975 + 11600;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 20054 + 17774 - 1852 + 88;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 29988 + 1551 - 25788 + 30345;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 1286 + 13062 + 17735 + 3970;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 2717 + 18932 + 7791 + 6614;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 25205 + 33732 - 29190 + 6308;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 31850 + 25535 - 29566 + 8240;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 35174 + 24696 - 53696 + 29886;
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else if (capabilities.GL_ARB_framebuffer_object) {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x92 ^ 0x9A];
                OpenGlHelper.framebufferType = " ".length();
                OpenGlHelper.GL_FRAMEBUFFER = 17544 + 26860 - 13857 + 5613;
                OpenGlHelper.GL_RENDERBUFFER = 16074 + 2305 + 5050 + 12732;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 16738 + 19555 - 1008 + 779;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 3720 + 34930 - 20197 + 17643;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 6360 + 5522 + 23105 + 1066;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 11107 + 29775 - 31932 + 27105;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 2842 + 17437 + 13213 + 2562;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 19108 + 35024 - 53833 + 35760;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 18537 + 15992 - 14904 + 16435;
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (capabilities.GL_EXT_framebuffer_object) {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0xBB ^ 0xB2];
                OpenGlHelper.framebufferType = "  ".length();
                OpenGlHelper.GL_FRAMEBUFFER = 9891 + 11455 - 5654 + 20468;
                OpenGlHelper.GL_RENDERBUFFER = 2720 + 23901 - 18705 + 28245;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 22513 + 19186 - 12772 + 7137;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 10827 + 32737 - 39616 + 32148;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 10008 + 33140 - 8162 + 1067;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 31450 + 24175 - 53775 + 34205;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 16353 + 21348 - 27269 + 25622;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 25067 + 18022 - 26741 + 19711;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 19760 + 19650 - 9630 + 6280;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x99 ^ 0x93];
            final StringBuilder append = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x98 ^ 0x93]);
            String s;
            if (capabilities.OpenGL14) {
                s = OpenGlHelper.I[0x8E ^ 0x82];
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                s = OpenGlHelper.I[0x3B ^ 0x36];
            }
            OpenGlHelper.logText = append.append(s).append(OpenGlHelper.I[0x1A ^ 0x14]).toString();
            final StringBuilder append2 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0xB0 ^ 0xBF]);
            String s2;
            if (capabilities.GL_EXT_blend_func_separate) {
                s2 = OpenGlHelper.I[0xA ^ 0x1A];
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                s2 = OpenGlHelper.I[0xA8 ^ 0xB9];
            }
            OpenGlHelper.logText = append2.append(s2).append(OpenGlHelper.I[0x0 ^ 0x12]).toString();
            final StringBuilder append3 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x46 ^ 0x55]);
            String s3;
            if (capabilities.OpenGL30) {
                s3 = OpenGlHelper.I[0x33 ^ 0x27];
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                s3 = OpenGlHelper.I[0x3B ^ 0x2E];
            }
            OpenGlHelper.logText = append3.append(s3).append(OpenGlHelper.I[0xB2 ^ 0xA4]).toString();
            final StringBuilder append4 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0xA3 ^ 0xB4]);
            String s4;
            if (capabilities.GL_ARB_framebuffer_object) {
                s4 = OpenGlHelper.I[0xBC ^ 0xA4];
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                s4 = OpenGlHelper.I[0x9E ^ 0x87];
            }
            OpenGlHelper.logText = append4.append(s4).append(OpenGlHelper.I[0x43 ^ 0x59]).toString();
            final StringBuilder append5 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0xB8 ^ 0xA3]);
            String s5;
            if (capabilities.GL_EXT_framebuffer_object) {
                s5 = OpenGlHelper.I[0xBA ^ 0xA6];
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                s5 = OpenGlHelper.I[0x48 ^ 0x55];
            }
            OpenGlHelper.logText = append5.append(s5).append(OpenGlHelper.I[0xB1 ^ 0xAF]).toString();
        }
        OpenGlHelper.openGL21 = capabilities.OpenGL21;
        int shadersAvailable;
        if (!OpenGlHelper.openGL21 && (!capabilities.GL_ARB_vertex_shader || !capabilities.GL_ARB_fragment_shader || !capabilities.GL_ARB_shader_objects)) {
            shadersAvailable = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            shadersAvailable = " ".length();
        }
        OpenGlHelper.shadersAvailable = (shadersAvailable != 0);
        final StringBuilder append6 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x84 ^ 0x9B]);
        String s6;
        if (OpenGlHelper.shadersAvailable) {
            s6 = OpenGlHelper.I[0x2B ^ 0xB];
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            s6 = OpenGlHelper.I[0x64 ^ 0x45];
        }
        OpenGlHelper.logText = append6.append(s6).append(OpenGlHelper.I[0xE1 ^ 0xC3]).toString();
        if (OpenGlHelper.shadersAvailable) {
            if (capabilities.OpenGL21) {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0xBF ^ 0x9C];
                OpenGlHelper.arbShaders = ("".length() != 0);
                OpenGlHelper.GL_LINK_STATUS = 4477 + 17518 - 3935 + 17654;
                OpenGlHelper.GL_COMPILE_STATUS = 3865 + 12387 - 12833 + 32294;
                OpenGlHelper.GL_VERTEX_SHADER = 21242 + 19202 - 28836 + 24025;
                OpenGlHelper.GL_FRAGMENT_SHADER = 9887 + 28728 - 9153 + 6170;
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0xB5 ^ 0x91];
                OpenGlHelper.arbShaders = (" ".length() != 0);
                OpenGlHelper.GL_LINK_STATUS = 25833 + 30928 - 48373 + 27326;
                OpenGlHelper.GL_COMPILE_STATUS = 25013 + 17165 - 21859 + 15394;
                OpenGlHelper.GL_VERTEX_SHADER = 30908 + 31369 - 55645 + 29001;
                OpenGlHelper.GL_FRAGMENT_SHADER = 2934 + 25758 - 16548 + 23488;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else {
            final StringBuilder append7 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x28 ^ 0xD]);
            String s7;
            if (capabilities.OpenGL21) {
                s7 = OpenGlHelper.I[0x1 ^ 0x27];
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                s7 = OpenGlHelper.I[0x46 ^ 0x61];
            }
            OpenGlHelper.logText = append7.append(s7).append(OpenGlHelper.I[0x29 ^ 0x1]).toString();
            final StringBuilder append8 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0xED ^ 0xC4]);
            String s8;
            if (capabilities.GL_ARB_shader_objects) {
                s8 = OpenGlHelper.I[0x23 ^ 0x9];
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                s8 = OpenGlHelper.I[0x3C ^ 0x17];
            }
            OpenGlHelper.logText = append8.append(s8).append(OpenGlHelper.I[0x51 ^ 0x7D]).toString();
            final StringBuilder append9 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x53 ^ 0x7E]);
            String s9;
            if (capabilities.GL_ARB_vertex_shader) {
                s9 = OpenGlHelper.I[0x5 ^ 0x2B];
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                s9 = OpenGlHelper.I[0x64 ^ 0x4B];
            }
            OpenGlHelper.logText = append9.append(s9).append(OpenGlHelper.I[0x52 ^ 0x62]).toString();
            final StringBuilder append10 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x12 ^ 0x23]);
            String s10;
            if (capabilities.GL_ARB_fragment_shader) {
                s10 = OpenGlHelper.I[0x2D ^ 0x1F];
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                s10 = OpenGlHelper.I[0x64 ^ 0x57];
            }
            OpenGlHelper.logText = append10.append(s10).append(OpenGlHelper.I[0x1 ^ 0x35]).toString();
        }
        int shadersSupported;
        if (OpenGlHelper.framebufferSupported && OpenGlHelper.shadersAvailable) {
            shadersSupported = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            shadersSupported = "".length();
        }
        OpenGlHelper.shadersSupported = (shadersSupported != 0);
        final String lowerCase = GL11.glGetString(1052 + 1092 - 944 + 6736).toLowerCase();
        OpenGlHelper.nvidia = lowerCase.contains(OpenGlHelper.I[0x2D ^ 0x18]);
        int arbVbo;
        if (!capabilities.OpenGL15 && capabilities.GL_ARB_vertex_buffer_object) {
            arbVbo = " ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            arbVbo = "".length();
        }
        OpenGlHelper.arbVbo = (arbVbo != 0);
        int vboSupported;
        if (!capabilities.OpenGL15 && !OpenGlHelper.arbVbo) {
            vboSupported = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            vboSupported = " ".length();
        }
        OpenGlHelper.vboSupported = (vboSupported != 0);
        final StringBuilder append11 = new StringBuilder(String.valueOf(OpenGlHelper.logText)).append(OpenGlHelper.I[0x3A ^ 0xC]);
        String s11;
        if (OpenGlHelper.vboSupported) {
            s11 = OpenGlHelper.I[0x11 ^ 0x26];
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            s11 = OpenGlHelper.I[0x49 ^ 0x71];
        }
        OpenGlHelper.logText = append11.append(s11).append(OpenGlHelper.I[0x24 ^ 0x1D]).toString();
        if (OpenGlHelper.vboSupported) {
            if (OpenGlHelper.arbVbo) {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x25 ^ 0x1F];
                OpenGlHelper.GL_STATIC_DRAW = 1199 + 20062 - 5353 + 19136;
                OpenGlHelper.GL_ARRAY_BUFFER = 33302 + 8157 - 19073 + 12576;
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                OpenGlHelper.logText = String.valueOf(OpenGlHelper.logText) + OpenGlHelper.I[0x39 ^ 0x2];
                OpenGlHelper.GL_STATIC_DRAW = 19484 + 34647 - 37131 + 18044;
                OpenGlHelper.GL_ARRAY_BUFFER = 15614 + 21007 - 23105 + 21446;
            }
        }
        OpenGlHelper.field_181063_b = lowerCase.contains(OpenGlHelper.I[0x6 ^ 0x3A]);
        if (OpenGlHelper.field_181063_b) {
            if (OpenGlHelper.vboSupported) {
                OpenGlHelper.field_181062_Q = (" ".length() != 0);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else {
                GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0f);
            }
        }
        try {
            final Processor[] processors = new SystemInfo().getHardware().getProcessors();
            final String s12 = OpenGlHelper.I[0x43 ^ 0x7E];
            final Object[] array = new Object["  ".length()];
            array["".length()] = processors.length;
            array[" ".length()] = processors["".length()];
            OpenGlHelper.field_183030_aa = String.format(s12, array).replaceAll(OpenGlHelper.I[0x16 ^ 0x28], OpenGlHelper.I[0x21 ^ 0x1E]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (Throwable t) {}
    }
    
    public static void glUniform4(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(n, floatBuffer);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            GL20.glUniform4(n, floatBuffer);
        }
    }
    
    public static void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferTexture2DEXT(n, n2, n3, n4, n5);
                    break;
                }
            }
        }
    }
    
    public static void glDeleteFramebuffers(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glDeleteFramebuffers(n);
                    "".length();
                    if (!true) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteFramebuffers(n);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteFramebuffersEXT(n);
                    break;
                }
            }
        }
    }
    
    public static void glUniform4(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(n, intBuffer);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            GL20.glUniform4(n, intBuffer);
        }
    }
    
    public static int glCreateProgram() {
        int n;
        if (OpenGlHelper.arbShaders) {
            n = ARBShaderObjects.glCreateProgramObjectARB();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = GL20.glCreateProgram();
        }
        return n;
    }
    
    public static void glUniform3(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(n, floatBuffer);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            GL20.glUniform3(n, floatBuffer);
        }
    }
    
    public static void glBlendFunc(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.openGL14) {
            if (OpenGlHelper.extBlendFuncSeparate) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(n, n2, n3, n4);
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                GL14.glBlendFuncSeparate(n, n2, n3, n4);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            GL11.glBlendFunc(n, n2);
        }
    }
    
    public static String glGetProgramInfoLog(final int n, final int n2) {
        String s;
        if (OpenGlHelper.arbShaders) {
            s = ARBShaderObjects.glGetInfoLogARB(n, n2);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            s = GL20.glGetProgramInfoLog(n, n2);
        }
        return s;
    }
    
    public static void glUniform1(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(n, floatBuffer);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            GL20.glUniform1(n, floatBuffer);
        }
    }
    
    public static int glGetProgrami(final int n, final int n2) {
        int n3;
        if (OpenGlHelper.arbShaders) {
            n3 = ARBShaderObjects.glGetObjectParameteriARB(n, n2);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n3 = GL20.glGetProgrami(n, n2);
        }
        return n3;
    }
    
    public static void glDeleteProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(n);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            GL20.glDeleteProgram(n);
        }
    }
    
    public static void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glRenderbufferStorage(n, n2, n3, n4);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glRenderbufferStorage(n, n2, n3, n4);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glRenderbufferStorageEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
    
    public static void glUniformMatrix2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix2ARB(n, b, floatBuffer);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            GL20.glUniformMatrix2(n, b, floatBuffer);
        }
    }
    
    public static void glBindFramebuffer(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glBindFramebuffer(n, n2);
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindFramebuffer(n, n2);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindFramebufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static void setActiveTexture(final int n) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glActiveTextureARB(n);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            GL13.glActiveTexture(n);
        }
    }
    
    public static void setLightmapTextureCoords(final int n, final float lastBrightnessX, final float lastBrightnessY) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glMultiTexCoord2fARB(n, lastBrightnessX, lastBrightnessY);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            GL13.glMultiTexCoord2f(n, lastBrightnessX, lastBrightnessY);
        }
        if (n == OpenGlHelper.lightmapTexUnit) {
            OpenGlHelper.lastBrightnessX = lastBrightnessX;
            OpenGlHelper.lastBrightnessY = lastBrightnessY;
        }
    }
    
    public static void glBindRenderbuffer(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glBindRenderbuffer(n, n2);
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindRenderbuffer(n, n2);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindRenderbufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static void glDeleteRenderbuffers(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glDeleteRenderbuffers(n);
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteRenderbuffers(n);
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
                    break;
                }
            }
        }
    }
    
    public static void glUniform2(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(n, floatBuffer);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            GL20.glUniform2(n, floatBuffer);
        }
    }
    
    public static void glDeleteShader(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(n);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            GL20.glDeleteShader(n);
        }
    }
    
    public static int glGenRenderbuffers() {
        if (!OpenGlHelper.framebufferSupported) {
            return -" ".length();
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glGenRenderbuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenRenderbuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    public static void glDeleteBuffers(final int n) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glDeleteBuffersARB(n);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            GL15.glDeleteBuffers(n);
        }
    }
    
    public static void glLinkProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glLinkProgramARB(n);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            GL20.glLinkProgram(n);
        }
    }
    
    public static String getLogText() {
        return OpenGlHelper.logText;
    }
    
    public static void glUniform3(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(n, intBuffer);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            GL20.glUniform3(n, intBuffer);
        }
    }
    
    public static int glGetShaderi(final int n, final int n2) {
        int n3;
        if (OpenGlHelper.arbShaders) {
            n3 = ARBShaderObjects.glGetObjectParameteriARB(n, n2);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n3 = GL20.glGetShaderi(n, n2);
        }
        return n3;
    }
    
    public static void glUniformMatrix4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix4ARB(n, b, floatBuffer);
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            GL20.glUniformMatrix4(n, b, floatBuffer);
        }
    }
    
    public static void glAttachShader(final int n, final int n2) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glAttachObjectARB(n, n2);
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            GL20.glAttachShader(n, n2);
        }
    }
    
    public static void glUniform2(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(n, intBuffer);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            GL20.glUniform2(n, intBuffer);
        }
    }
    
    public static int glCheckFramebufferStatus(final int n) {
        if (!OpenGlHelper.framebufferSupported) {
            return -" ".length();
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glCheckFramebufferStatus(n);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus(n);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(n);
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    public static void glUniformMatrix3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix3ARB(n, b, floatBuffer);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            GL20.glUniformMatrix3(n, b, floatBuffer);
        }
    }
    
    public static void glUseProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUseProgramObjectARB(n);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            GL20.glUseProgram(n);
        }
    }
    
    public static void glBindBuffer(final int n, final int n2) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBindBufferARB(n, n2);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            GL15.glBindBuffer(n, n2);
        }
    }
    
    public static boolean areShadersSupported() {
        return OpenGlHelper.shadersSupported;
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int glCreateShader(final int n) {
        int n2;
        if (OpenGlHelper.arbShaders) {
            n2 = ARBShaderObjects.glCreateShaderObjectARB(n);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n2 = GL20.glCreateShader(n);
        }
        return n2;
    }
    
    public static boolean useVbo() {
        int n;
        if (Config.isMultiTexture()) {
            n = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else if (OpenGlHelper.vboSupported && Minecraft.getMinecraft().gameSettings.useVbo) {
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
    
    public static String glGetShaderInfoLog(final int n, final int n2) {
        String s;
        if (OpenGlHelper.arbShaders) {
            s = ARBShaderObjects.glGetInfoLogARB(n, n2);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            s = GL20.glGetShaderInfoLog(n, n2);
        }
        return s;
    }
    
    public static void glShaderSource(final int n, final ByteBuffer byteBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glShaderSourceARB(n, byteBuffer);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            GL20.glShaderSource(n, byteBuffer);
        }
    }
    
    public static int glGetAttribLocation(final int n, final CharSequence charSequence) {
        int n2;
        if (OpenGlHelper.arbShaders) {
            n2 = ARBVertexShader.glGetAttribLocationARB(n, charSequence);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            n2 = GL20.glGetAttribLocation(n, charSequence);
        }
        return n2;
    }
    
    public static void glUniform1i(final int n, final int n2) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1iARB(n, n2);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            GL20.glUniform1i(n, n2);
        }
    }
    
    public static void setClientActiveTexture(final int n) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glClientActiveTextureARB(n);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            GL13.glClientActiveTexture(n);
        }
    }
    
    public static void glCompileShader(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glCompileShaderARB(n);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            GL20.glCompileShader(n);
        }
    }
    
    public static boolean isFramebufferEnabled() {
        int n;
        if (Config.isFastRender()) {
            n = "".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (Config.getAntialiasingLevel() > 0) {
            n = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (OpenGlHelper.framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable) {
            n = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static int glGenBuffers() {
        int n;
        if (OpenGlHelper.arbVbo) {
            n = ARBVertexBufferObject.glGenBuffersARB();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n = GL15.glGenBuffers();
        }
        return n;
    }
    
    public static void glBufferData(final int n, final ByteBuffer byteBuffer, final int n2) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBufferDataARB(n, byteBuffer, n2);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            GL15.glBufferData(n, byteBuffer, n2);
        }
    }
    
    public static int glGetUniformLocation(final int n, final CharSequence charSequence) {
        int n2;
        if (OpenGlHelper.arbShaders) {
            n2 = ARBShaderObjects.glGetUniformLocationARB(n, charSequence);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            n2 = GL20.glGetUniformLocation(n, charSequence);
        }
        return n2;
    }
    
    public static void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glFramebufferRenderbuffer(n, n2, n3, n4);
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferRenderbuffer(n, n2, n3, n4);
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
}
