package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import org.lwjgl.opengl.*;
import com.ibm.icu.text.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.*;
import optfine.*;
import java.io.*;
import java.util.*;

public class FontRenderer implements IResourceManagerReloadListener
{
    public float scaleFactor;
    private static final String[] I;
    private final TextureManager renderEngine;
    private float alpha;
    private boolean unicodeFlag;
    private boolean underlineStyle;
    public boolean enabled;
    private int textColor;
    private boolean bidiFlag;
    private float red;
    private float green;
    private boolean boldStyle;
    private static final String __OBFID;
    public GameSettings gameSettings;
    private boolean italicStyle;
    private float posX;
    public ResourceLocation locationFontTextureBase;
    private boolean randomStyle;
    private byte[] glyphWidth;
    public int FONT_HEIGHT;
    private float[] charWidth;
    private static final ResourceLocation[] unicodePageLocations;
    private boolean strikethroughStyle;
    public Random fontRandom;
    private ResourceLocation locationFontTexture;
    private float posY;
    private float blue;
    private int[] colorCode;
    
    public String trimStringToWidth(final String s, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        float n2 = 0.0f;
        int length;
        if (b) {
            length = s.length() - " ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int n3 = length;
        int length2;
        if (b) {
            length2 = -" ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            length2 = " ".length();
        }
        final int n4 = length2;
        int n5 = "".length();
        int n6 = "".length();
        int n7 = n3;
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (n7 >= 0 && n7 < s.length() && n2 < n) {
            final char char1 = s.charAt(n7);
            final float charWidthFloat = this.getCharWidthFloat(char1);
            if (n5 != 0) {
                n5 = "".length();
                if (char1 != (0x4A ^ 0x26) && char1 != (0x5A ^ 0x16)) {
                    if (char1 == (0x78 ^ 0xA) || char1 == (0xC8 ^ 0x9A)) {
                        n6 = "".length();
                        "".length();
                        if (1 == 2) {
                            throw null;
                        }
                    }
                }
                else {
                    n6 = " ".length();
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
            }
            else if (charWidthFloat < 0.0f) {
                n5 = " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n2 += charWidthFloat;
                if (n6 != 0) {
                    ++n2;
                }
            }
            if (n2 > n) {
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            else {
                if (b) {
                    sb.insert("".length(), char1);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    sb.append(char1);
                }
                n7 += n4;
            }
        }
        return sb.toString();
    }
    
    private void loadGlyphTexture(final int n) {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(n));
    }
    
    private String trimStringNewline(String substring) {
        "".length();
        if (true != true) {
            throw null;
        }
        while (substring != null && substring.endsWith(FontRenderer.I[0x90 ^ 0x99])) {
            substring = substring.substring("".length(), substring.length() - " ".length());
        }
        return substring;
    }
    
    private int sizeStringToWidth(final String s, final int n) {
        final int length = s.length();
        float n2 = 0.0f;
        int i = "".length();
        int n3 = -" ".length();
        int n4 = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < length) {
            final char char1 = s.charAt(i);
            Label_0244: {
                switch (char1) {
                    case 10: {
                        --i;
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                        break Label_0244;
                    }
                    case 32: {
                        n3 = i;
                        break;
                    }
                    case 167: {
                        if (i >= length - " ".length()) {
                            break Label_0244;
                        }
                        ++i;
                        final char char2 = s.charAt(i);
                        if (char2 == (0x6C ^ 0x0) || char2 == (0x14 ^ 0x58)) {
                            n4 = " ".length();
                            break Label_0244;
                        }
                        if (char2 != (0xDB ^ 0xA9) && char2 != (0xF9 ^ 0xAB) && !isFormatColor(char2)) {
                            break Label_0244;
                        }
                        n4 = "".length();
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                        break Label_0244;
                    }
                }
                n2 += this.getCharWidthFloat(char1);
                if (n4 != 0) {
                    ++n2;
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
            }
            if (char1 == (0x4C ^ 0x46)) {
                n3 = ++i;
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
                break;
            }
            else if (n2 > n) {
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        int n5;
        if (i != length && n3 != -" ".length() && n3 < i) {
            n5 = n3;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            n5 = i;
        }
        return n5;
    }
    
    private void readFontTexture() {
        BufferedImage bufferedImage;
        try {
            bufferedImage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n = width / (0x87 ^ 0x97);
        final int n2 = height / (0xA2 ^ 0xB2);
        final float scaleFactor = width / 128.0f;
        this.scaleFactor = scaleFactor;
        final int[] array = new int[width * height];
        bufferedImage.getRGB("".length(), "".length(), width, height, array, "".length(), width);
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < 48 + 38 - 51 + 221) {
            final int n3 = i % (0x5E ^ 0x4E);
            final int n4 = i / (0x95 ^ 0x85);
            "".length();
            int j = n - " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (j >= 0) {
                final int n5 = n3 * n + j;
                int n6 = " ".length();
                int length = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (length < n2 && n6 != 0) {
                    if ((array[n5 + (n4 * n2 + length) * width] >> (0x40 ^ 0x58) & 134 + 158 - 199 + 162) > (0x3A ^ 0x2A)) {
                        n6 = "".length();
                    }
                    ++length;
                }
                if (n6 == 0) {
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    --j;
                }
            }
            if (i == (0xED ^ 0xAC)) {
                i = i;
            }
            if (i == (0x1B ^ 0x3B)) {
                if (n <= (0x7 ^ 0xF)) {
                    j = (int)(2.0f * scaleFactor);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                else {
                    j = (int)(1.5f * scaleFactor);
                }
            }
            this.charWidth[i] = (j + " ".length()) / scaleFactor + 1.0f;
            ++i;
        }
        this.readCustomCharWidths();
    }
    
    private void resetStyles() {
        this.randomStyle = ("".length() != 0);
        this.boldStyle = ("".length() != 0);
        this.italicStyle = ("".length() != 0);
        this.underlineStyle = ("".length() != 0);
        this.strikethroughStyle = ("".length() != 0);
    }
    
    private float renderDefaultChar(final int n, final boolean b) {
        final int n2 = n % (0xA9 ^ 0xB9) * (0x6C ^ 0x64);
        final int n3 = n / (0xA2 ^ 0xB2) * (0x1C ^ 0x14);
        int n4;
        if (b) {
            n4 = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        this.renderEngine.bindTexture(this.locationFontTexture);
        final float n6 = this.charWidth[n];
        final float n7 = 7.99f;
        GL11.glBegin(0x7B ^ 0x7E);
        GL11.glTexCoord2f(n2 / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + n5, this.posY, 0.0f);
        GL11.glTexCoord2f(n2 / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX - n5, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n2 + n7 - 1.0f) / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + n7 - 1.0f + n5, this.posY, 0.0f);
        GL11.glTexCoord2f((n2 + n7 - 1.0f) / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX + n7 - 1.0f - n5, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return n6;
    }
    
    private int renderString(String bidiReorder, final float posX, final float posY, int n, final boolean b) {
        if (bidiReorder == null) {
            return "".length();
        }
        if (this.bidiFlag) {
            bidiReorder = this.bidiReorder(bidiReorder);
        }
        if ((n & -(56243980 + 37277713 - 87076750 + 60663921)) == 0x0) {
            n |= -(14912868 + 9825262 - 13245432 + 5284518);
        }
        if (b) {
            n = ((n & 13366959 + 11069973 - 20183719 + 12326623) >> "  ".length() | (n & -(16464523 + 13967299 - 16873396 + 3218790)));
        }
        this.red = (n >> (0x2C ^ 0x3C) & 116 + 174 - 69 + 34) / 255.0f;
        this.blue = (n >> (0x5B ^ 0x53) & 41 + 84 + 74 + 56) / 255.0f;
        this.green = (n & 248 + 48 - 48 + 7) / 255.0f;
        this.alpha = (n >> (0x45 ^ 0x5D) & 251 + 11 - 247 + 240) / 255.0f;
        GlStateManager.color(this.red, this.blue, this.green, this.alpha);
        this.posX = posX;
        this.posY = posY;
        this.renderStringAtPos(bidiReorder, b);
        return (int)this.posX;
    }
    
    public boolean getBidiFlag() {
        return this.bidiFlag;
    }
    
    public static String getFormatFromString(final String s) {
        String s2 = FontRenderer.I[0xB6 ^ 0xBA];
        int index = -" ".length();
        final int length = s.length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while ((index = s.indexOf(42 + 33 - 28 + 120, index + " ".length())) != -" ".length()) {
            if (index < length - " ".length()) {
                final char char1 = s.charAt(index + " ".length());
                if (isFormatColor(char1)) {
                    s2 = FontRenderer.I[0x9 ^ 0x4] + char1;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    if (!isFormatSpecial(char1)) {
                        continue;
                    }
                    s2 = String.valueOf(s2) + FontRenderer.I[0x2F ^ 0x21] + char1;
                }
            }
        }
        return s2;
    }
    
    public int splitStringWidth(final String s, final int n) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(s, n).size();
    }
    
    private static void I() {
        (I = new String[0x8E ^ 0x98])["".length()] = I("\u0006\u001a\n}fufe{`u", "EVUMV");
        FontRenderer.I[" ".length()] = I("\t5*9H\b6==\u000f0)-7\u0002\u001ct&$\t", "oZDMg");
        FontRenderer.I["  ".length()] = I("\u0095\u0085·\u009e\u0080\u009e\u0089¦\u0082\u009f\u008f\u009b\u0096£\u0155\u0165\u0175\u0127\u0105\u0114\u010a\u0130\u0100\u0128\u024dUDuVJUDUwhv`Ppm}m_}fxjZf{gwAc|b|LlqiyKi\n\u0014\u00066\u0012\u000f\u0013\u0003=\u001f\u0000\u001e\b8\u0018\u0005\u0005\u0015'\u0005\u001e\u0000\u0012\"\u000e\u0013\u000f\u001f)\u000b\u0014\n$\u00144)1!\u00131\"<.\u001e:';+\u0005'8&0\u0000 =-=\u000f-6(:u\u0091¶¼¦\u0091¶¯²®\u009e¾¥»¨±\u0093\u0083³\u0082\u0081 ¸®½\u008a\u0080\u0096\u00ad\u00e7\u00ad\u0081\u01d8´©\u0086¬»\u0084\u00ee\u00cf\u00e9\u00e4\u00f9\u00f9\u00c9\u00f7\u00e1\u00ee\u25d5\u25e7\u25c5\u2548\u2571\u2525\u2517\u2500\u251f\u2536\u2515\u2522\u250b\u2516\u250e\u2554\u2561\u2562\u2566\u2549\u2544\u2549\u2508\u2515\u250f\u2510\u251c\u2530\u252a\u2505\u2528\u2512\u253e\u252e\u2530\u251d\u252d\u2504\u2519\u253e\u252e\u256d\u255a\u25c2\u25d1\u25c8\u25e5\u25d6\u03fb\u03e7\u03d7\u03b5\u03f5\u0389\u03e9\u0380\u03d3\u03ce\u03e3\u03e1\u225a\u2270\u225e\u2263\u2234\u00f5\u2210\u2232\u236a\u2374³\u223d\u00e6\u2253\u00e2\u225e\u200a\u00e4\u25eaU", "UDuVJ");
        FontRenderer.I["   ".length()] = I("\u0013/\u0002\u0000\r\u0015/\t[\u001e\b$\u000e[\r\t#\u0019\u001b\u001c\u0002\u0015\n\u0015\u001f\u0002\u0015_DJ\u001fd\n\u001a\u001f", "gJztx");
        FontRenderer.I[0x4B ^ 0x4F] = I("`eCa^ebFjS16\u00126\u000f6?\u001d?\u0004?&", "PTqRj");
        FontRenderer.I[0x18 ^ 0x1D] = I("\u0086©\u008aº\u008f\u008d¥\u009b¦\u0090\u009c·«\u0087\u015a\u0176\u0159\u011a\u0121\u011b\u0119\u011c\u013d\u010c\u0242FhHrEFhhSgeLmTbnAbYikFgBtt[|GsqPqH~zUvM\u0005\u0007*\u000b6\u0000\u0000/\u0000;\u000f\r$\u0005<\n\u00169\u001a!\u0011\u0013>\u001f*\u001c\u001c3\u0014/\u001b\u0019\b)\u0010&\"\r.\u0015-/\u0002#\u001e((\u00078\u000375\u001c=\u00042>\u00112\t9;\u0016Hµ¹¯\u008a¬\u0092 ¡\u0082£\u009aª¨\u0084\u008c·\u008c ®¼\u0084·½\u0091·¤\u0099¾\u00cb\u0090¥\u01d7§\u0085»\u0088´\u0097\u00c2\u00f2\u00cd\u00eb\u00ea\u00d5\u00f4\u00d3\u00ee\u00fd\u25f9\u25da\u25e1\u2547\u2562\u2509\u252a\u2524\u2510\u2525\u2539\u251f\u252f\u2519\u251d\u2578\u255c\u2546\u2569\u255a\u2568\u2574\u252c\u251a\u251c\u253c\u2521\u2514\u2525\u2516\u2504\u252f\u251a\u2521\u2523\u2531\u2510\u2520\u2516\u252d\u2502\u2550\u257e\u25cd\u25c2\u25e4\u25d8\u25f2\u03f4\u03f4\u03fb\u0388\u03d1\u0386\u03fa\u03ac\u03ee\u03ea\u03ec\u03f2\u2276\u224d\u227a\u226c\u2227\u00d9\u222d\u2216\u2365\u2367\u009f\u2200\u00c2\u225c\u00f1\u2272\u2037\u00c0\u25e5F", "FhHrE");
        FontRenderer.I[0xA1 ^ 0xA7] = I("ª¥\u0097\u009a°¡©\u0086\u0086¯°»¶§\u0165\u015a\u0155\u0107\u0101\u0124\u0135\u0110\u0120\u012c\u027djdURzjdusXI@pt]BM\u007fyVGJzbKXWagL]\\lhAVYkm:+&\u0016\u0016?,#\u001d\u001b0!(\u0018\u001c5:5\u0007\u0001.?2\u0002\n#0?\t\u000f$5\u000440\u0019\u000e\u000135\u0012\u0003\u000e>>\u0017\u0004\u000b%#\b\u0019\u0010 $\r\u0012\u001d/)\u0006\u0017\u001aU\u0095\u0086\u0083\u0086±²\u009f\u008d\u008e¾º\u0095\u0084\u0088\u0091\u0097³\u008c¢¡¤\u0088\u0091\u009dª\u0084¦\u0092\u00c7\u008d\u0085\u01e8\u008b\u0089¦¨\u008b»\u00ce\u00ef\u00ed\u00d4\u00c6\u00d9\u00e9\u00f3\u00d1\u00d1\u25f5\u25c7\u25c1\u2578\u254e\u2505\u2537\u2504\u252f\u2509\u2535\u2502\u250f\u2526\u2531\u2574\u2541\u2566\u2556\u2576\u2564\u2569\u250c\u2525\u2530\u2530\u253c\u2534\u251a\u253a\u2508\u2532\u253a\u251e\u250f\u253d\u250d\u2500\u2529\u2501\u250e\u254d\u255e\u25f2\u25ee\u25e8\u25c5\u25d2\u03cb\u03d8\u03f7\u0395\u03f1\u03b9\u03d6\u03a0\u03f3\u03ca\u03d3\u03de\u227a\u2250\u225a\u2253\u220b\u00d5\u2230\u2236\u235a\u234b\u0093\u221d\u00e2\u2263\u00dd\u227e\u202a\u00e0\u25daj", "jdURz");
        FontRenderer.I[0x9F ^ 0x98] = I("´\u0088±¡\u0080¿\u0084 ½\u009f®\u0096\u0090\u009c\u0155\u0144\u0178\u0121\u013a\u0114\u012b\u013d\u0106\u0117\u024dtIsiJtISHhWmVOm\\`YBfYg\\Y{FzG\\|CqJSqHtMV\n5\u000b0-\u000f2\u000e; \u0000?\u0005>'\u0005$\u0018!:\u001e!\u001f$1\u0013.\u0012/4\u0014+)\u0012\u000b)\u0010,\u0015\u000e\"\u001d#\u0018\u0005'\u001a&\u0003\u00188\u0007=\u0006\u001f=\f0\t\u00126\t7s®¶\u009d«\u0097\u0089¯\u0093£\u0098\u0081¥\u009a¥·¬\u0083\u0092\u008f\u0087\u009f¸\u008f°\u008c¿\u0096\u008c\u00ea«¾\u01d8\u0095¤\u0080\u0093»¥\u00e3\u00c9\u00d6\u00e4\u00d8\u00f4\u00cf\u00c8\u00e1\u00cf\u25d8\u25e1\u25fa\u2548\u2550\u2528\u2511\u253f\u251f\u2517\u2518\u2524\u2534\u2516\u252f\u2559\u2567\u255d\u2566\u2568\u2549\u254f\u2537\u2515\u252e\u251d\u251a\u250f\u252a\u2524\u2525\u2514\u2501\u252e\u2511\u2510\u252b\u253b\u2519\u251f\u2523\u256b\u2565\u25c2\u25f0\u25c5\u25e3\u25e9\u03fb\u03c6\u03da\u03b3\u03ca\u0389\u03c8\u038d\u03d5\u03f1\u03e3\u03c0\u2257\u2276\u2261\u2263\u2215\u00f8\u2216\u220d\u236a\u2355¾\u223b\u00d9\u2253\u00c3\u2253\u200c\u00db\u25eat", "tIsiJ");
        FontRenderer.I[0xAF ^ 0xA7] = I("±«\u0084\u009f\u0084º§\u0095\u0083\u009b«µ¥¢\u0151\u0141\u015b\u0114\u0104\u0110\u012e\u011e\u0133\u0129\u0249qjFWNqjfvlRNcqiYCl|b\\Dig\u007fCYrbxFR\u007fmuMWxh\u000e0(\u0005\u0013\u000b7-\u000e\u001e\u0004:&\u000b\u0019\u0001!;\u0014\u0004\u001a$<\u0011\u000f\u0017+1\u001a\n\u0010.\n'5-\u0015\u000f 0&\u0018\u0000-;#\u001f\u00056&<\u0002\u001e3!9\t\u0013<,2\f\u0014F\u0090²\u0098\u0088¢·«\u0096\u0080\u00ad¿¡\u009f\u0086\u0082\u0092\u0087\u0097¬²¡¼\u008a\u0093¹\u0081\u0092\u0089\u00c9\u009e\u0080\u01dc\u0090\u0087µ\u00ad¿ \u00c0\u00fc\u00e8\u00e0\u00dd\u00d7\u00fa\u00f6\u00e5\u00ca\u25fb\u25d4\u25c4\u254c\u2555\u250b\u2524\u2501\u251b\u2512\u253b\u2511\u250a\u2512\u252a\u257a\u2552\u2563\u2562\u256d\u256a\u257a\u2509\u2511\u252b\u253e\u252f\u2531\u252e\u2521\u2506\u2521\u253f\u252a\u2514\u2533\u251e\u2505\u251d\u251a\u2500\u255e\u255b\u25c6\u25f5\u25e6\u25d6\u25d7\u03ff\u03c3\u03f9\u0386\u03f4\u038d\u03cd\u03ae\u03e0\u03cf\u03e7\u03c5\u2274\u2243\u225f\u2267\u2210\u00db\u2223\u2233\u236e\u2350\u009d\u220e\u00e7\u2257\u00c6\u2270\u2039\u00e5\u25eeq", "qjFWN");
        FontRenderer.I[0x6B ^ 0x62] = I("D", "NIQmI");
        FontRenderer.I[0x75 ^ 0x7F] = I("f", "lLMkD");
        FontRenderer.I[0x7C ^ 0x77] = I("g", "mGZMN");
        FontRenderer.I[0xAE ^ 0xA2] = I("", "Abiqs");
        FontRenderer.I[0x35 ^ 0x38] = I("\u00cb", "ldmMT");
        FontRenderer.I[0x3D ^ 0x33] = I("\u00e3", "DhcJa");
        FontRenderer.I[0x39 ^ 0x36] = I("aUBf]dRGmP0\u0006\u00131\f7", "QdpUi");
        FontRenderer.I[0x42 ^ 0x52] = I("x\u00074\u0015", "VwZrz");
        FontRenderer.I[0x26 ^ 0x37] = I("_\u0014\u001e.\u0000\u0014\u0016\u0018(\u0015\u0002", "qdlAp");
        FontRenderer.I[0x94 ^ 0x86] = I("\u001a!\u0002\u0010\u00078)C", "VNctn");
        FontRenderer.I[0xB9 ^ 0xAA] = I("\u0006\u0013/\u0001\u001c_", "qzKut");
        FontRenderer.I[0x83 ^ 0x97] = I("\u0012\r,'\u0003\u0014\r'|", "fhTSv");
        FontRenderer.I[0x54 ^ 0x41] = I("8\u0011\u0011\b\u00116\u001a\u0004\u001bJ", "Uraie");
    }
    
    public void drawSplitString(String trimStringNewline, final int n, final int n2, final int n3, final int textColor) {
        this.resetStyles();
        this.textColor = textColor;
        trimStringNewline = this.trimStringNewline(trimStringNewline);
        this.renderSplitString(trimStringNewline, n, n2, n3, "".length() != 0);
    }
    
    private float getCharWidthFloat(final char c) {
        if (c == 86 + 151 - 174 + 104) {
            return -1.0f;
        }
        if (c == (0x53 ^ 0x73)) {
            return this.charWidth[0x65 ^ 0x45];
        }
        final int index = FontRenderer.I[0xB ^ 0x3].indexOf(c);
        if (c > '\0' && index != -" ".length() && !this.unicodeFlag) {
            return this.charWidth[index];
        }
        if (this.glyphWidth[c] != 0) {
            int length = this.glyphWidth[c] >>> (0x4 ^ 0x0);
            int n = this.glyphWidth[c] & (0x68 ^ 0x67);
            if (n > (0x44 ^ 0x43)) {
                n = (0x39 ^ 0x36);
                length = "".length();
            }
            return (++n - length) / "  ".length() + " ".length();
        }
        return 0.0f;
    }
    
    private void renderSplitString(final String s, final int n, int n2, final int n3, final boolean b) {
        final Iterator<String> iterator = this.listFormattedStringToWidth(s, n3).iterator();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.renderStringAligned(iterator.next(), n, n2, n3, this.textColor, b);
            n2 += this.FONT_HEIGHT;
        }
    }
    
    private float func_181559_a(final char c, final boolean b) {
        if (c == (0x39 ^ 0x19)) {
            return this.charWidth[c];
        }
        final int index = FontRenderer.I["  ".length()].indexOf(c);
        float n;
        if (index != -" ".length() && !this.unicodeFlag) {
            n = this.renderDefaultChar(index, b);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n = this.renderUnicodeChar(c, b);
        }
        return n;
    }
    
    private String bidiReorder(final String s) {
        try {
            final Bidi bidi = new Bidi(new ArabicShaping(0x31 ^ 0x39).shape(s), 45 + 117 - 123 + 88);
            bidi.setReorderingMode("".length());
            return bidi.writeReordered("  ".length());
        }
        catch (ArabicShapingException ex) {
            return s;
        }
    }
    
    String wrapFormattedStringToWidth(final String s, final int n) {
        final int sizeStringToWidth = this.sizeStringToWidth(s, n);
        if (s.length() <= sizeStringToWidth) {
            return s;
        }
        final String substring = s.substring("".length(), sizeStringToWidth);
        final char char1 = s.charAt(sizeStringToWidth);
        int n2;
        if (char1 != (0xD ^ 0x2D) && char1 != (0x21 ^ 0x2B)) {
            n2 = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        final StringBuilder sb = new StringBuilder(String.valueOf(getFormatFromString(substring)));
        final int n4 = sizeStringToWidth;
        int n5;
        if (n3 != 0) {
            n5 = " ".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        return String.valueOf(substring) + FontRenderer.I[0x64 ^ 0x6F] + this.wrapFormattedStringToWidth(sb.append(s.substring(n4 + n5)).toString(), n);
    }
    
    static {
        I();
        __OBFID = FontRenderer.I["".length()];
        unicodePageLocations = new ResourceLocation[111 + 70 - 168 + 243];
    }
    
    private ResourceLocation getUnicodePageLocation(final int n) {
        if (FontRenderer.unicodePageLocations[n] == null) {
            final ResourceLocation[] unicodePageLocations = FontRenderer.unicodePageLocations;
            final String s = FontRenderer.I["   ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = n;
            unicodePageLocations[n] = new ResourceLocation(String.format(s, array));
            FontRenderer.unicodePageLocations[n] = getHdFontLocation(FontRenderer.unicodePageLocations[n]);
        }
        return FontRenderer.unicodePageLocations[n];
    }
    
    public void setUnicodeFlag(final boolean unicodeFlag) {
        this.unicodeFlag = unicodeFlag;
    }
    
    public void setBidiFlag(final boolean bidiFlag) {
        this.bidiFlag = bidiFlag;
    }
    
    public List listFormattedStringToWidth(final String s, final int n) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, n).split(FontRenderer.I[0x5F ^ 0x55]));
    }
    
    public int getStringWidth(final String s) {
        if (s == null) {
            return "".length();
        }
        float n = 0.0f;
        int n2 = "".length();
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < s.length()) {
            float charWidthFloat = this.getCharWidthFloat(s.charAt(i));
            if (charWidthFloat < 0.0f && i < s.length() - " ".length()) {
                ++i;
                final char char1 = s.charAt(i);
                if (char1 != (0xD8 ^ 0xB4) && char1 != (0x5 ^ 0x49)) {
                    if (char1 == (0x12 ^ 0x60) || char1 == (0x52 ^ 0x0)) {
                        n2 = "".length();
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                }
                else {
                    n2 = " ".length();
                }
                charWidthFloat = 0.0f;
            }
            n += charWidthFloat;
            if (n2 != 0 && charWidthFloat > 0.0f) {
                ++n;
            }
            ++i;
        }
        return (int)n;
    }
    
    private int renderStringAligned(final String s, int n, final int n2, final int n3, final int n4, final boolean b) {
        if (this.bidiFlag) {
            n = n + n3 - this.getStringWidth(this.bidiReorder(s));
        }
        return this.renderString(s, n, n2, n4, b);
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getColorCode(final char c) {
        final int index = FontRenderer.I[0x1D ^ 0x12].indexOf(c);
        int n;
        if (index >= 0 && index < this.colorCode.length) {
            n = this.colorCode[index];
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = 1639626 + 9816938 - 3722152 + 9042803;
        }
        return n;
    }
    
    public FontRenderer(final GameSettings gameSettings, final ResourceLocation resourceLocation, final TextureManager renderEngine, final boolean unicodeFlag) {
        this.charWidth = new float[144 + 197 - 123 + 38];
        this.FONT_HEIGHT = (0x71 ^ 0x78);
        this.fontRandom = new Random();
        this.glyphWidth = new byte[53654 + 41643 - 43244 + 13483];
        this.colorCode = new int[0x46 ^ 0x66];
        this.enabled = (" ".length() != 0);
        this.scaleFactor = 1.0f;
        this.gameSettings = gameSettings;
        this.locationFontTextureBase = resourceLocation;
        this.locationFontTexture = resourceLocation;
        this.renderEngine = renderEngine;
        this.unicodeFlag = unicodeFlag;
        renderEngine.bindTexture(this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase));
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < (0x5A ^ 0x7A)) {
            final int n = (i >> "   ".length() & " ".length()) * (0x67 ^ 0x32);
            int n2 = (i >> "  ".length() & " ".length()) * (64 + 138 - 173 + 141) + n;
            int n3 = (i >> " ".length() & " ".length()) * (81 + 117 - 138 + 110) + n;
            int n4 = (i >> "".length() & " ".length()) * (57 + 52 - 52 + 113) + n;
            if (i == (0x49 ^ 0x4F)) {
                n2 += 85;
            }
            if (gameSettings.anaglyph) {
                final int n5 = (n2 * (0xBA ^ 0xA4) + n3 * (0x80 ^ 0xBB) + n4 * (0x66 ^ 0x6D)) / (0x1A ^ 0x7E);
                final int n6 = (n2 * (0xC ^ 0x12) + n3 * (0x2C ^ 0x6A)) / (0x3F ^ 0x5B);
                final int n7 = (n2 * (0x7D ^ 0x63) + n4 * (0xC2 ^ 0x84)) / (0x12 ^ 0x76);
                n2 = n5;
                n3 = n6;
                n4 = n7;
            }
            if (i >= (0x5F ^ 0x4F)) {
                n2 /= (0x27 ^ 0x23);
                n3 /= (0x6E ^ 0x6A);
                n4 /= (0x4A ^ 0x4E);
            }
            this.colorCode[i] = ((n2 & 150 + 248 - 169 + 26) << (0x8D ^ 0x9D) | (n3 & 157 + 190 - 257 + 165) << (0x6A ^ 0x62) | (n4 & 64 + 38 - 2 + 155));
            ++i;
        }
        this.readGlyphSizes();
    }
    
    private float renderUnicodeChar(final char c, final boolean b) {
        if (this.glyphWidth[c] == 0) {
            return 0.0f;
        }
        this.loadGlyphTexture(c / (182 + 157 - 242 + 159));
        final int n = this.glyphWidth[c] >>> (0x44 ^ 0x40);
        final int n2 = this.glyphWidth[c] & (0x18 ^ 0x17);
        final float n3 = n;
        final float n4 = n2 + " ".length();
        final float n5 = c % (0x51 ^ 0x41) * (0x8 ^ 0x18) + n3;
        final float n6 = (c & 242 + 249 - 483 + 247) / (0x10 ^ 0x0) * (0xD2 ^ 0xC2);
        final float n7 = n4 - n3 - 0.02f;
        float n8;
        if (b) {
            n8 = 1.0f;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n8 = 0.0f;
        }
        final float n9 = n8;
        GL11.glBegin(0x27 ^ 0x22);
        GL11.glTexCoord2f(n5 / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n9, this.posY, 0.0f);
        GL11.glTexCoord2f(n5 / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX - n9, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f + n9, this.posY, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f - n9, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (n4 - n3) / 2.0f + 1.0f;
    }
    
    public int getCharWidth(final char c) {
        return Math.round(this.getCharWidthFloat(c));
    }
    
    public int drawStringWithShadow(final String s, final float n, final float n2, final int n3) {
        return this.drawString(s, n, n2, n3, " ".length() != 0);
    }
    
    public int drawString(final String s, final float n, final float n2, final int n3, final boolean b) {
        GlStateManager.enableAlpha();
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.renderString(s, n + 1.0f, n2 + 1.0f, n3, (boolean)(" ".length() != 0)), this.renderString(s, n, n2, n3, (boolean)("".length() != 0)));
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n4 = this.renderString(s, n, n2, n3, "".length() != 0);
        }
        return n4;
    }
    
    private static boolean isFormatColor(final char c) {
        if ((c < (0xF1 ^ 0xC1) || c > (0x14 ^ 0x2D)) && (c < (0xD3 ^ 0xB2) || c > (0xF9 ^ 0x9F)) && (c < (0x1A ^ 0x5B) || c > (0x43 ^ 0x5))) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private void renderStringAtPos(final String s, final boolean b) {
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < s.length()) {
            char char1 = s.charAt(i);
            if (char1 == 150 + 71 - 88 + 34 && i + " ".length() < s.length()) {
                int index = FontRenderer.I[0xB3 ^ 0xB7].indexOf(s.toLowerCase().charAt(i + " ".length()));
                if (index < (0x52 ^ 0x42)) {
                    this.randomStyle = ("".length() != 0);
                    this.boldStyle = ("".length() != 0);
                    this.strikethroughStyle = ("".length() != 0);
                    this.underlineStyle = ("".length() != 0);
                    this.italicStyle = ("".length() != 0);
                    if (index < 0 || index > (0xB4 ^ 0xBB)) {
                        index = (0x36 ^ 0x39);
                    }
                    if (b) {
                        index += 16;
                    }
                    final int textColor = this.colorCode[index];
                    this.textColor = textColor;
                    GlStateManager.color((textColor >> (0x2A ^ 0x3A)) / 255.0f, (textColor >> (0x79 ^ 0x71) & 102 + 33 + 22 + 98) / 255.0f, (textColor & 236 + 62 - 143 + 100) / 255.0f, this.alpha);
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else if (index == (0xA9 ^ 0xB9)) {
                    this.randomStyle = (" ".length() != 0);
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else if (index == (0x9C ^ 0x8D)) {
                    this.boldStyle = (" ".length() != 0);
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                else if (index == (0x13 ^ 0x1)) {
                    this.strikethroughStyle = (" ".length() != 0);
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                }
                else if (index == (0x56 ^ 0x45)) {
                    this.underlineStyle = (" ".length() != 0);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else if (index == (0x2C ^ 0x38)) {
                    this.italicStyle = (" ".length() != 0);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (index == (0x51 ^ 0x44)) {
                    this.randomStyle = ("".length() != 0);
                    this.boldStyle = ("".length() != 0);
                    this.strikethroughStyle = ("".length() != 0);
                    this.underlineStyle = ("".length() != 0);
                    this.italicStyle = ("".length() != 0);
                    GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                }
                ++i;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                int n = FontRenderer.I[0x92 ^ 0x97].indexOf(char1);
                if (this.randomStyle && n != -" ".length()) {
                    char char2;
                    do {
                        n = this.fontRandom.nextInt(FontRenderer.I[0x57 ^ 0x51].length());
                        char2 = FontRenderer.I[0x78 ^ 0x7F].charAt(n);
                    } while (this.getCharWidth(char1) != this.getCharWidth(char2));
                    char1 = char2;
                }
                float n2;
                if (this.unicodeFlag) {
                    n2 = 0.5f;
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    n2 = 1.0f / this.scaleFactor;
                }
                final float n3 = n2;
                int n4;
                if ((char1 == '\0' || n == -" ".length() || this.unicodeFlag) && b) {
                    n4 = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n4 = "".length();
                }
                final int n5 = n4;
                if (n5 != 0) {
                    this.posX -= n3;
                    this.posY -= n3;
                }
                float func_181559_a = this.func_181559_a(char1, this.italicStyle);
                if (n5 != 0) {
                    this.posX += n3;
                    this.posY += n3;
                }
                if (this.boldStyle) {
                    this.posX += n3;
                    if (n5 != 0) {
                        this.posX -= n3;
                        this.posY -= n3;
                    }
                    this.func_181559_a(char1, this.italicStyle);
                    this.posX -= n3;
                    if (n5 != 0) {
                        this.posX += n3;
                        this.posY += n3;
                    }
                    func_181559_a += n3;
                }
                if (this.strikethroughStyle) {
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldRenderer.begin(0x54 ^ 0x53, DefaultVertexFormats.POSITION);
                    worldRenderer.pos(this.posX, this.posY + this.FONT_HEIGHT / "  ".length(), 0.0).endVertex();
                    worldRenderer.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT / "  ".length(), 0.0).endVertex();
                    worldRenderer.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT / "  ".length() - 1.0f, 0.0).endVertex();
                    worldRenderer.pos(this.posX, this.posY + this.FONT_HEIGHT / "  ".length() - 1.0f, 0.0).endVertex();
                    instance.draw();
                    GlStateManager.enableTexture2D();
                }
                if (this.underlineStyle) {
                    final Tessellator instance2 = Tessellator.getInstance();
                    final WorldRenderer worldRenderer2 = instance2.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldRenderer2.begin(0x7E ^ 0x79, DefaultVertexFormats.POSITION);
                    int length;
                    if (this.underlineStyle) {
                        length = -" ".length();
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                    else {
                        length = "".length();
                    }
                    final int n6 = length;
                    worldRenderer2.pos(this.posX + n6, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT - 1.0f, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + n6, this.posY + this.FONT_HEIGHT - 1.0f, 0.0).endVertex();
                    instance2.draw();
                    GlStateManager.enableTexture2D();
                }
                this.posX += func_181559_a;
            }
            ++i;
        }
    }
    
    private void readGlyphSizes() {
        InputStream inputStream = null;
        try {
            inputStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(FontRenderer.I[" ".length()])).getInputStream();
            inputStream.read(this.glyphWidth);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < FontRenderer.unicodePageLocations.length) {
            FontRenderer.unicodePageLocations[i] = null;
            ++i;
        }
        this.readFontTexture();
    }
    
    private static ResourceLocation getHdFontLocation(final ResourceLocation resourceLocation) {
        if (!Config.isCustomFonts()) {
            return resourceLocation;
        }
        if (resourceLocation == null) {
            return resourceLocation;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final String s = FontRenderer.I[0xBE ^ 0xAA];
        final String s2 = FontRenderer.I[0x57 ^ 0x42];
        if (!resourcePath.startsWith(s)) {
            return resourceLocation;
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(s2) + resourcePath.substring(s.length()));
        ResourceLocation resourceLocation3;
        if (Config.hasResource(Config.getResourceManager(), resourceLocation2)) {
            resourceLocation3 = resourceLocation2;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            resourceLocation3 = resourceLocation;
        }
        return resourceLocation3;
    }
    
    private static boolean isFormatSpecial(final char c) {
        if ((c < (0x64 ^ 0xF) || c > (0xDE ^ 0xB1)) && (c < (0x3 ^ 0x48) || c > (0x1C ^ 0x53)) && c != (0xB7 ^ 0xC5) && c != (0xD0 ^ 0x82)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public int drawString(final String s, final int n, final int n2, final int n3) {
        int n4;
        if (!this.enabled) {
            n4 = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            n4 = this.drawString(s, n, n2, n3, "".length() != 0);
        }
        return n4;
    }
    
    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }
    
    private void readCustomCharWidths() {
        final String resourcePath = this.locationFontTexture.getResourcePath();
        final String s = FontRenderer.I[0x3E ^ 0x2E];
        if (resourcePath.endsWith(s)) {
            final String string = String.valueOf(resourcePath.substring("".length(), resourcePath.length() - s.length())) + FontRenderer.I[0x57 ^ 0x46];
            try {
                final InputStream resourceStream = Config.getResourceStream(Config.getResourceManager(), new ResourceLocation(this.locationFontTexture.getResourceDomain(), string));
                if (resourceStream == null) {
                    return;
                }
                Config.log(FontRenderer.I[0x1A ^ 0x8] + string);
                final Properties properties = new Properties();
                properties.load(resourceStream);
                final Iterator<String> iterator = ((Hashtable<String, V>)properties).keySet().iterator();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String s2 = iterator.next();
                    final String s3 = FontRenderer.I[0x32 ^ 0x21];
                    if (s2.startsWith(s3)) {
                        final int int1 = Config.parseInt(s2.substring(s3.length()), -" ".length());
                        if (int1 < 0 || int1 >= this.charWidth.length) {
                            continue;
                        }
                        final float float1 = Config.parseFloat(properties.getProperty(s2), -1.0f);
                        if (float1 < 0.0f) {
                            continue;
                        }
                        this.charWidth[int1] = float1;
                    }
                }
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            catch (FileNotFoundException ex2) {
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String trimStringToWidth(final String s, final int n) {
        return this.trimStringToWidth(s, n, "".length() != 0);
    }
}
