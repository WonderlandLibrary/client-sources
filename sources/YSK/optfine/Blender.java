package optfine;

import net.minecraft.client.renderer.*;

public class Blender
{
    public static final int BLEND_DEFAULT;
    public static final int BLEND_BURN;
    public static final int BLEND_SCREEN;
    public static final int BLEND_ALPHA;
    public static final int BLEND_REPLACE;
    public static final int BLEND_ADD;
    public static final int BLEND_SUBSTRACT;
    private static final String[] I;
    public static final int BLEND_DODGE;
    public static final int BLEND_MULTIPLY;
    
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void clearBlend(final float n) {
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(236 + 568 - 477 + 443, " ".length());
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
    }
    
    static {
        I();
        BLEND_BURN = (0x7C ^ 0x79);
        BLEND_ALPHA = "".length();
        BLEND_DEFAULT = " ".length();
        BLEND_REPLACE = (0xF ^ 0x8);
        BLEND_SCREEN = (0x8 ^ 0xE);
        BLEND_ADD = " ".length();
        BLEND_SUBSTRACT = "  ".length();
        BLEND_MULTIPLY = "   ".length();
        BLEND_DODGE = (0x3D ^ 0x39);
    }
    
    private static void I() {
        (I = new String[0x27 ^ 0x2E])["".length()] = I("\u000f\u001f\u001c;\u0019", "nslSx");
        Blender.I[" ".length()] = I("5\u001e=", "TzYmB");
        Blender.I["  ".length()] = I("\u001808>\"\n&.", "kEZJP");
        Blender.I["   ".length()] = I("\u0002\u0010&$.\u001f\t3", "oeJPG");
        Blender.I[0x43 ^ 0x47] = I("-8\r\u0002\u000f", "IWiej");
        Blender.I[0xB ^ 0xE] = I("\u0004\u00115!", "fdGOu");
        Blender.I[0x69 ^ 0x6F] = I("\u0014\u0006#\u00073\t", "geQbV");
        Blender.I[0x4C ^ 0x4B] = I("\u001c( =\u0000\r(", "nMPQa");
        Blender.I[0x1B ^ 0x13] = I("$\u001b\u0011$.\u0006\u001bZ(-\u0014\u001b\u001epa", "quzJA");
    }
    
    public static int parseBlend(String trim) {
        if (trim == null) {
            return " ".length();
        }
        trim = trim.toLowerCase().trim();
        if (trim.equals(Blender.I["".length()])) {
            return "".length();
        }
        if (trim.equals(Blender.I[" ".length()])) {
            return " ".length();
        }
        if (trim.equals(Blender.I["  ".length()])) {
            return "  ".length();
        }
        if (trim.equals(Blender.I["   ".length()])) {
            return "   ".length();
        }
        if (trim.equals(Blender.I[0x7 ^ 0x3])) {
            return 0xAA ^ 0xAE;
        }
        if (trim.equals(Blender.I[0xA2 ^ 0xA7])) {
            return 0x50 ^ 0x55;
        }
        if (trim.equals(Blender.I[0xB6 ^ 0xB0])) {
            return 0x46 ^ 0x40;
        }
        if (trim.equals(Blender.I[0x37 ^ 0x30])) {
            return 0x9A ^ 0x9D;
        }
        Config.warn(Blender.I[0x15 ^ 0x1D] + trim);
        return " ".length();
    }
    
    public static void setupBlend(final int n, final float n2) {
        switch (n) {
            case 0: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(752 + 446 - 692 + 264, 393 + 474 - 439 + 343);
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                "".length();
                if (3 == 1) {
                    throw null;
                }
                break;
            }
            case 1: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(76 + 289 - 66 + 471, " ".length());
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(523 + 140 - 593 + 705, "".length());
                GlStateManager.color(n2, n2, n2, 1.0f);
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 3: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(643 + 688 - 562 + 5, 273 + 168 - 404 + 734);
                GlStateManager.color(n2, n2, n2, n2);
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            case 4: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(" ".length(), " ".length());
                GlStateManager.color(n2, n2, n2, 1.0f);
                "".length();
                if (2 < 1) {
                    throw null;
                }
                break;
            }
            case 5: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc("".length(), 100 + 392 - 210 + 487);
                GlStateManager.color(n2, n2, n2, 1.0f);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                break;
            }
            case 6: {
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(" ".length(), 224 + 199 + 62 + 284);
                GlStateManager.color(n2, n2, n2, 1.0f);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
                break;
            }
            case 7: {
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
        }
        GlStateManager.enableTexture2D();
    }
}
