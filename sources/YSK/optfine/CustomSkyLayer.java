package optfine;

import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class CustomSkyLayer
{
    public static final float[] DEFAULT_AXIS;
    private float[] axis;
    private int endFadeIn;
    private int endFadeOut;
    public String source;
    private float speed;
    private int blend;
    private int startFadeOut;
    private static final String[] I;
    public int textureId;
    private int startFadeIn;
    private boolean rotate;
    
    private boolean parseBoolean(final String s, final boolean b) {
        if (s == null) {
            return b;
        }
        if (s.toLowerCase().equals(CustomSkyLayer.I[0x1E ^ 0x12])) {
            return " ".length() != 0;
        }
        if (s.toLowerCase().equals(CustomSkyLayer.I[0x94 ^ 0x99])) {
            return "".length() != 0;
        }
        Config.warn(CustomSkyLayer.I[0x2A ^ 0x24] + s);
        return b;
    }
    
    private float getFadeBrightness(final int n) {
        if (this.timeBetween(n, this.startFadeIn, this.endFadeIn)) {
            return this.normalizeTime(n - this.startFadeIn) / this.normalizeTime(this.endFadeIn - this.startFadeIn);
        }
        if (this.timeBetween(n, this.endFadeIn, this.startFadeOut)) {
            return 1.0f;
        }
        if (this.timeBetween(n, this.startFadeOut, this.endFadeOut)) {
            return 1.0f - this.normalizeTime(n - this.startFadeOut) / this.normalizeTime(this.endFadeOut - this.startFadeOut);
        }
        return 0.0f;
    }
    
    private int normalizeTime(int i) {
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i >= 1333 + 1071 + 9245 + 12351) {
            i -= 24000;
        }
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < 0) {
            i += 24000;
        }
        return i;
    }
    
    public boolean isValid(final String s) {
        if (this.source == null) {
            Config.warn(CustomSkyLayer.I[0x77 ^ 0x62] + s);
            return "".length() != 0;
        }
        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(s));
        if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
            Config.warn(CustomSkyLayer.I[0xAD ^ 0xB5]);
            return "".length() != 0;
        }
        final int normalizeTime = this.normalizeTime(this.endFadeIn - this.startFadeIn);
        if (this.startFadeOut < 0) {
            this.startFadeOut = this.normalizeTime(this.endFadeOut - normalizeTime);
        }
        final int n = normalizeTime + this.normalizeTime(this.startFadeOut - this.endFadeIn) + this.normalizeTime(this.endFadeOut - this.startFadeOut) + this.normalizeTime(this.startFadeIn - this.endFadeOut);
        if (n != 18193 + 6590 - 6770 + 5987) {
            Config.warn(CustomSkyLayer.I[0x4 ^ 0x12] + n);
            return "".length() != 0;
        }
        if (this.speed < 0.0f) {
            Config.warn(CustomSkyLayer.I[0x1E ^ 0x9] + this.speed);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        final float[] default_AXIS = new float["   ".length()];
        default_AXIS["".length()] = 1.0f;
        default_AXIS[" ".length()] = 0.0f;
        default_AXIS["  ".length()] = 0.0f;
        DEFAULT_AXIS = default_AXIS;
    }
    
    public boolean isActive(final int n) {
        int n2;
        if (this.timeBetween(n, this.endFadeOut, this.startFadeIn)) {
            n2 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    public void render(final int n, final float n2, final float n3) {
        final float limit = Config.limit(n3 * this.getFadeBrightness(n), 0.0f, 1.0f);
        if (limit >= 1.0E-4f) {
            GlStateManager.bindTexture(this.textureId);
            Blender.setupBlend(this.blend, limit);
            GlStateManager.pushMatrix();
            if (this.rotate) {
                GlStateManager.rotate(n2 * 360.0f * this.speed, this.axis["".length()], this.axis[" ".length()], this.axis["  ".length()]);
            }
            final Tessellator instance = Tessellator.getInstance();
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 0x92 ^ 0x96);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(instance, " ".length());
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(instance, "".length());
            GlStateManager.popMatrix();
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 0x59 ^ 0x5C);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, "  ".length());
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, "   ".length());
            GlStateManager.popMatrix();
        }
    }
    
    private int parseTime(final String s) {
        if (s == null) {
            return -" ".length();
        }
        final String[] tokenize = Config.tokenize(s, CustomSkyLayer.I[0xA1 ^ 0xA8]);
        if (tokenize.length != "  ".length()) {
            Config.warn(CustomSkyLayer.I[0x7A ^ 0x70] + s);
            return -" ".length();
        }
        final String s2 = tokenize["".length()];
        final String s3 = tokenize[" ".length()];
        int int1 = Config.parseInt(s2, -" ".length());
        final int int2 = Config.parseInt(s3, -" ".length());
        if (int1 >= 0 && int1 <= (0x63 ^ 0x74) && int2 >= 0 && int2 <= (0x8E ^ 0xB5)) {
            int1 -= 6;
            if (int1 < 0) {
                int1 += 24;
            }
            return int1 * (564 + 4 - 87 + 519) + (int)(int2 / 60.0 * 1000.0);
        }
        Config.warn(CustomSkyLayer.I[0x2F ^ 0x24] + s);
        return -" ".length();
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean timeBetween(final int n, final int n2, final int n3) {
        int n4;
        if (n2 <= n3) {
            if (n >= n2 && n <= n3) {
                n4 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
        }
        else if (n < n2 && n > n3) {
            n4 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        return n4 != 0;
    }
    
    public CustomSkyLayer(final Properties properties, final String s) {
        this.source = null;
        this.startFadeIn = -" ".length();
        this.endFadeIn = -" ".length();
        this.startFadeOut = -" ".length();
        this.endFadeOut = -" ".length();
        this.blend = " ".length();
        this.rotate = ("".length() != 0);
        this.speed = 1.0f;
        this.axis = CustomSkyLayer.DEFAULT_AXIS;
        this.textureId = -" ".length();
        this.source = properties.getProperty(CustomSkyLayer.I["".length()], s);
        this.startFadeIn = this.parseTime(properties.getProperty(CustomSkyLayer.I[" ".length()]));
        this.endFadeIn = this.parseTime(properties.getProperty(CustomSkyLayer.I["  ".length()]));
        this.startFadeOut = this.parseTime(properties.getProperty(CustomSkyLayer.I["   ".length()]));
        this.endFadeOut = this.parseTime(properties.getProperty(CustomSkyLayer.I[0xC6 ^ 0xC2]));
        this.blend = Blender.parseBlend(properties.getProperty(CustomSkyLayer.I[0x20 ^ 0x25]));
        this.rotate = this.parseBoolean(properties.getProperty(CustomSkyLayer.I[0x87 ^ 0x81]), " ".length() != 0);
        this.speed = this.parseFloat(properties.getProperty(CustomSkyLayer.I[0xA3 ^ 0xA4]), 1.0f);
        this.axis = this.parseAxis(properties.getProperty(CustomSkyLayer.I[0x75 ^ 0x7D]), CustomSkyLayer.DEFAULT_AXIS);
    }
    
    private float parseFloat(final String s, final float n) {
        if (s == null) {
            return n;
        }
        final float float1 = Config.parseFloat(s, Float.MIN_VALUE);
        if (float1 == Float.MIN_VALUE) {
            Config.warn(CustomSkyLayer.I[0x72 ^ 0x7D] + s);
            return n;
        }
        return float1;
    }
    
    private float[] parseAxis(final String s, final float[] array) {
        if (s == null) {
            return array;
        }
        final String[] tokenize = Config.tokenize(s, CustomSkyLayer.I[0x49 ^ 0x59]);
        if (tokenize.length != "   ".length()) {
            Config.warn(CustomSkyLayer.I[0x58 ^ 0x49] + s);
            return array;
        }
        final float[] array2 = new float["   ".length()];
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < tokenize.length) {
            array2[i] = Config.parseFloat(tokenize[i], Float.MIN_VALUE);
            if (array2[i] == Float.MIN_VALUE) {
                Config.warn(CustomSkyLayer.I[0xA ^ 0x18] + s);
                return array;
            }
            if (array2[i] < -1.0f || array2[i] > 1.0f) {
                Config.warn(CustomSkyLayer.I[0x6A ^ 0x79] + s);
                return array;
            }
            ++i;
        }
        final float n = array2["".length()];
        final float n2 = array2[" ".length()];
        final float n3 = array2["  ".length()];
        if (n * n + n2 * n2 + n3 * n3 < 1.0E-5f) {
            Config.warn(CustomSkyLayer.I[0x4 ^ 0x10] + s);
            return array;
        }
        final float[] array3 = new float["   ".length()];
        array3["".length()] = n3;
        array3[" ".length()] = n2;
        array3["  ".length()] = -n;
        return array3;
    }
    
    private void renderSide(final Tessellator tessellator, final int n) {
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        final double n2 = n % "   ".length() / 3.0;
        final double n3 = n / "   ".length() / 2.0;
        worldRenderer.begin(0x98 ^ 0x9F, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-100.0, -100.0, -100.0).tex(n2, n3).endVertex();
        worldRenderer.pos(-100.0, -100.0, 100.0).tex(n2, n3 + 0.5).endVertex();
        worldRenderer.pos(100.0, -100.0, 100.0).tex(n2 + 0.3333333333333333, n3 + 0.5).endVertex();
        worldRenderer.pos(100.0, -100.0, -100.0).tex(n2 + 0.3333333333333333, n3).endVertex();
        tessellator.draw();
    }
    
    private static void I() {
        (I = new String[0xA9 ^ 0xB0])["".length()] = I("'&\u0000&\u00191", "TIuTz");
        CustomSkyLayer.I[" ".length()] = I("\u0002>4\u0004!7+1\u0013\u001c\u001f", "qJUvU");
        CustomSkyLayer.I["  ".length()] = I("\u0015\u0003\u0000(\u0007\u0014\b-\u0000", "pmdnf");
        CustomSkyLayer.I["   ".length()] = I("\u0006:\u00120\u00073/\u0017'<\u0000:", "uNsBs");
        CustomSkyLayer.I[0xB6 ^ 0xB2] = I("\u0000-.\u00041\u0001&\u00057$", "eCJBP");
        CustomSkyLayer.I[0x44 ^ 0x41] = I(":\u000b\u000b\u0006\t", "Xgnhm");
        CustomSkyLayer.I[0x67 ^ 0x61] = I("\u0015&\u0016\u0015\u0010\u0002", "gIbtd");
        CustomSkyLayer.I[0x3C ^ 0x3B] = I("\"\u0016\u0015\u00066", "QfpcR");
        CustomSkyLayer.I[0x3D ^ 0x35] = I("45.\u0010", "UMGcc");
        CustomSkyLayer.I[0x77 ^ 0x7E] = I("B", "xOjmW");
        CustomSkyLayer.I[0x75 ^ 0x7F] = I("(\u00168\u0007.\b\u001cn\u0012+\f\u001dtF", "axNfB");
        CustomSkyLayer.I[0xCA ^ 0xC1] = I("<\u001a\"9\u0018\u001c\u0010t,\u001d\u0018\u0011nx", "utTXt");
        CustomSkyLayer.I[0x92 ^ 0x9E] = I("1 6\u0002", "ERCgm");
        CustomSkyLayer.I[0x36 ^ 0x3B] = I("),\n\u0002\u0011", "OMfqt");
        CustomSkyLayer.I[0xF ^ 0x1] = I("$\u0019.;\u0004\u0006\u0019e7\u0004\u001e\u001b 4\u0005KW", "qwEUk");
        CustomSkyLayer.I[0x9C ^ 0x93] = I("\u0019\"\u0007$\u00019(Q3\f<9\u0014\u007fM", "PLqEm");
        CustomSkyLayer.I[0x4 ^ 0x14] = I("u", "USdMZ");
        CustomSkyLayer.I[0xD0 ^ 0xC1] = I("#\u0005\u0019;6\u0003\u000fO;\"\u0003\u0018Uz", "jkoZZ");
        CustomSkyLayer.I[0x1C ^ 0xE] = I("\u0007<$\u0019%'6r\u00191'!hX", "NRRxI");
        CustomSkyLayer.I[0x35 ^ 0x26] = I("\u0000?\u00024? 5T4+ \"T#2%$\u0011&ii", "IQtUS");
        CustomSkyLayer.I[0xA2 ^ 0xB6] = I("\"\"=,9\u0002(k,-\u0002?k;4\u00079.>oK", "kLKMU");
        CustomSkyLayer.I[0xA ^ 0x1F] = I("\u001f\u0005X\u0005=$\u0018\u001b\u0013r%\u000f\u0000\u0002'#\u000fBV", "QjxvR");
        CustomSkyLayer.I[0x57 ^ 0x41] = I(";756\u0014\u001b=c1\u0019\u0016<\n9W\u00148'27\u0007-c#\u0011\u001f<0{X\u0001,.w\u0011\u0001y.8\n\u0017y7?\u0019\u001cyqc\u0010Hy", "rYCWx");
        CustomSkyLayer.I[0x56 ^ 0x41] = I("&\u001c7#.\u0006\u0016a12\n\u0017%xb", "orABB");
        CustomSkyLayer.I[0x27 ^ 0x3F] = I("<(9)5\u001c\"o<0\u0018#<dy\u0007#>=0\u0007#+h8\u0007#uh*\u0001'=<\u001f\u0014\"*\u00017Yf*&=3'+-\u0010\u001bf.&=U#!,\u001f\u0014\"*\u0007,\u0001h", "uFOHY");
    }
}
