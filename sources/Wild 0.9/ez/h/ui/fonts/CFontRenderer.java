package ez.h.ui.fonts;

import org.lwjgl.opengl.*;
import java.awt.*;
import ez.h.*;
import ez.h.managers.*;
import java.util.*;

public class CFontRenderer
{
    private CFont boldFont;
    private CFont italicFont;
    private String colorcodeIdentifiers;
    private CFont font;
    private boolean bidi;
    private final int[] colorCode;
    public final Random fontRandom;
    public float FONT_HEIGHT;
    private final Color[] customColorCodes;
    private CFont boldItalicFont;
    
    public List<String> formatString(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        String string = "";
        int n2 = 29746 + 44588 - 73988 + 65189;
        for (int i = 0; i < s.toCharArray().length; ++i) {
            final char c = s.toCharArray()[i];
            if (c == 38 + 7 - 30 + 152 && i < s.toCharArray().length - 1) {
                n2 = s.toCharArray()[i + 1];
            }
            if (this.getStringWidth(string + c) < n) {
                string += c;
            }
            else {
                list.add(string);
                string = ((n2 == -1) ? String.valueOf(c) : ("§" + (char)n2 + c));
            }
        }
        if (!string.equals("")) {
            list.add(string);
        }
        return list;
    }
    
    public void drawCenteredString(final String s, final float n, final float n2, final int n3) {
        this.drawStringWithShadow(s, n - this.getStringWidth(s) / 2.0f, n2, n3);
    }
    
    public int getCharWidth(final char c) {
        return this.getStringWidth(Character.toString(c));
    }
    
    public void drawScaledString(final String s, final float n, final float n2, final int n3, final float n4, final boolean b) {
        GL11.glPushMatrix();
        GL11.glScalef(n4, n4, 1.0f);
        this.drawString(s, n / n4, n2 / n4, n3, b);
        GL11.glPopMatrix();
    }
    
    public void drawGlowString(final String s, final float n, final float n2, final int n3, final int n4) {
        final float n5 = (n3 >> (0x22 ^ 0x3A) & 252 + 225 - 284 + 62) / 255.0f;
        final float n6 = (n3 >> (0x14 ^ 0x4) & 177 + 208 - 239 + 109) / 255.0f;
        final float n7 = (n3 >> 8 & 105 + 210 - 126 + 66) / 255.0f;
        final float n8 = (n3 & 176 + 97 - 120 + 102) / 255.0f;
        final float n9 = (n4 >> (0x42 ^ 0x5A) & 111 + 147 - 6 + 3) / 255.0f;
        final float n10 = (n4 >> (0x71 ^ 0x61) & 5 + 103 - 84 + 231) / 255.0f;
        final float n11 = (n4 >> 8 & 219 + 246 - 308 + 98) / 255.0f;
        final float n12 = (n4 & 202 + 217 - 325 + 161) / 255.0f;
        bus.z();
        bus.m();
        bus.d();
        bus.a(499 + 317 - 186 + 140, 548 + 609 - 891 + 505, 1, 0);
        bus.j(7338 + 7275 - 12092 + 4904);
        this.drawString(s, n, n2, new Color(n6, n7, n8, n5).getRGB());
        this.drawString(s, n, n2 + 1.0f, new Color(n6, n7, n8, n5).getRGB());
        this.drawString(s, n, n2, new Color(n10, n11, n12, n9).getRGB());
        this.drawString(s, n, n2 - 1.0f, new Color(n10, n11, n12, n9).getRGB());
        bus.j(5210 + 2733 - 4884 + 4365);
        bus.l();
        bus.e();
        bus.y();
    }
    
    private void drawLine(final double n, final double n2, final double n3, final double n4, final float n5) {
        GL11.glDisable(3249 + 2640 - 4039 + 1703);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glEnable(2877 + 1231 - 4104 + 3549);
    }
    
    protected String wrapFormattedStringToWidth(final String s, final int n) {
        final int sizeStringToWidth = this.sizeStringToWidth(s, n);
        if (s.length() <= sizeStringToWidth) {
            return s;
        }
        final String substring = s.substring(0, sizeStringToWidth);
        final char char1 = s.charAt(sizeStringToWidth);
        return substring + "\n" + this.wrapFormattedStringToWidth(getFormatFromString(substring) + s.substring(sizeStringToWidth + ((char1 == (0x9D ^ 0xBD) || char1 == (0xB6 ^ 0xBC)) ? 1 : 0)), n);
    }
    
    public void setAntiAliasing(final boolean b) {
        this.font.setAntiAlias(b);
        this.boldFont.setAntiAlias(b);
        this.italicFont.setAntiAlias(b);
        this.boldItalicFont.setAntiAlias(b);
    }
    
    public int drawStringWithGlow(final String s, final float n, final float n2, final int n3) {
        GL11.glPushMatrix();
        this.drawString(s, n + 0.5f, n2 + 0.5f, n3, true);
        bus.b(3354 + 3350 - 4216 + 1065, 1126 + 5291 - 4238 + 8062, 9122 + 870 - 6851 + 6588);
        bus.b(120 + 827 - 348 + 2954, 7468 + 8958 - 6409 + 223, 1787 + 5702 - 753 + 2993);
        bus.a(1163 + 2684 - 3604 + 3310, 0, 0, 0, 0, 0, 213 + 42 - 159 + 160, 199 + 243 - 314 + 128);
        bus.m();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.a(true, true, true, false);
        final bve a = bve.a();
        final buk c = a.c();
        c.a(7, cdy.i);
        bus.d();
        this.drawString(s, n, n2, n3, false);
        for (int i = 0; i < 3; ++i) {
            final float n4 = 1.0f / (i + 1);
            final int stringWidth = this.getStringWidth(s);
            final int stringHeight = this.getStringHeight(s);
            final float n5 = (i - 1) / 256.0f;
            c.b((double)stringWidth, (double)stringHeight, 0.0).a((double)(0.0f + n5), 1.0).a(1.0f, 1.0f, 1.0f, n4).d();
            c.b((double)stringWidth, 0.0, 0.0).a((double)(1.0f + n5), 1.0).a(1.0f, 1.0f, 1.0f, n4).d();
            c.b(0.0, 0.0, 0.0).a((double)(1.0f + n5), 0.0).a(1.0f, 1.0f, 1.0f, n4).d();
            c.b(0.0, (double)stringHeight, 0.0).a((double)(0.0f + n5), 0.0).a(1.0f, 1.0f, 1.0f, n4).d();
        }
        a.b();
        bus.e();
        bus.a(true, true, true, true);
        GL11.glPopMatrix();
        return 0;
    }
    
    private void setupMinecraftColorcodes() {
        for (int i = 0; i < (0x2B ^ 0xB); ++i) {
            final int n = (i >> 3 & 0x1) * (0x17 ^ 0x42);
            int n2 = (i >> 2 & 0x1) * (137 + 4 - 109 + 138) + n;
            int n3 = (i >> 1 & 0x1) * (88 + 26 - 86 + 142) + n;
            int n4 = (i >> 0 & 0x1) * (127 + 81 - 191 + 153) + n;
            if (i == 6) {
                n2 += 85;
            }
            if (i >= (0x19 ^ 0x9)) {
                n2 /= 4;
                n3 /= 4;
                n4 /= 4;
            }
            this.colorCode[i] = ((n2 & 248 + 42 - 167 + 132) << (0x1D ^ 0xD) | (n3 & 7 + 244 - 48 + 52) << 8 | (n4 & 21 + 233 - 168 + 169));
        }
    }
    
    public Color getColor(final int n, final float n2) {
        return new Color((n >> (0x9D ^ 0x8D)) / 255.0f, (n >> 8 & 161 + 163 - 292 + 223) / 255.0f, (n & 88 + 211 - 249 + 205) / 255.0f, n2);
    }
    
    private String setupColorcodeIdentifier() {
        String string = "0123456789abcdefklmnor";
        for (int i = 0; i < this.customColorCodes.length; ++i) {
            if (this.customColorCodes[i] != null) {
                string += (char)i;
            }
        }
        return string;
    }
    
    public int getStringHeight(final String s) {
        if (s == null) {
            return 0;
        }
        return this.font.getStringHeight(s) / 2;
    }
    
    public void drawCenteredStringXY(final String s, final int n, final int n2, final int n3, final boolean b) {
        this.drawCenteredString(s, n, n2 - this.getHeight() / 2, n3, b);
    }
    
    public void setFont(final Font font, final boolean b, final int n) {
        synchronized (this) {
            this.font = new CFont(font, b, n);
            this.boldFont = new CFont(font.deriveFont(1), b, n);
            this.italicFont = new CFont(font.deriveFont(2), b, n);
            this.boldItalicFont = new CFont(font.deriveFont(3), b, n);
            this.FONT_HEIGHT = (float)this.getHeight();
        }
    }
    
    public CFont getFont() {
        return this.font;
    }
    
    public int getColorCode(final char c) {
        return this.colorCode["0123456789abcdef".indexOf(c)];
    }
    
    public String getFontName() {
        return this.font.getFont().getFontName();
    }
    
    public int getHeight() {
        return this.font.getHeight() / 2;
    }
    
    public boolean isAntiAliasing() {
        return this.font.isAntiAlias() && this.boldFont.isAntiAlias() && this.italicFont.isAntiAlias() && this.boldItalicFont.isAntiAlias();
    }
    
    private int sizeStringToWidth(final String s, final int n) {
        final int length = s.length();
        int n2 = 0;
        int i = 0;
        int n3 = -1;
        boolean b = false;
        while (i < length) {
            final char char1 = s.charAt(i);
            Label_0181: {
                switch (char1) {
                    case 10: {
                        --i;
                        break Label_0181;
                    }
                    case 167: {
                        if (i < length - 1) {
                            ++i;
                            final char char2 = s.charAt(i);
                            if (char2 != (0x70 ^ 0x1C) && char2 != (0x16 ^ 0x5A)) {
                                if (char2 == (0xD2 ^ 0xA0) || char2 == (0x1B ^ 0x49) || isFormatColor(char2)) {
                                    b = false;
                                }
                            }
                            else {
                                b = true;
                            }
                        }
                        break Label_0181;
                    }
                    case 32: {
                        n3 = i;
                        break;
                    }
                }
                n2 += this.getStringWidth(Character.toString(char1));
                if (b) {
                    ++n2;
                }
            }
            if (char1 == (0x7E ^ 0x74)) {
                n3 = ++i;
                break;
            }
            if (n2 > n) {
                break;
            }
            ++i;
        }
        return (i != length && n3 != -1 && n3 < i) ? n3 : i;
    }
    
    public CFontRenderer(final Font font, final boolean b, final int n) {
        this.fontRandom = new Random();
        this.customColorCodes = new Color[185 + 64 - 242 + 249];
        this.colorCode = new int[0xA6 ^ 0x86];
        this.colorcodeIdentifiers = "0123456789abcdefklmnor";
        this.setFont(font, b, n);
        this.customColorCodes[0x19 ^ 0x68] = new Color(0, 0x2A ^ 0x70, 147 + 127 - 213 + 102);
        this.colorcodeIdentifiers = this.setupColorcodeIdentifier();
        this.setupMinecraftColorcodes();
        this.FONT_HEIGHT = (float)this.getHeight();
    }
    
    private int drawLine(String s, final float n, final float n2, int n3, final boolean b) {
        if (s == null) {
            return 0;
        }
        if (Main.getFeatureByName("NameProtect").isEnabled()) {
            s = s.replace(bib.z().af.c(), a.n + "#HIDDEN#" + a.v);
            final Iterator<String> iterator = FriendManager.friends.iterator();
            while (iterator.hasNext()) {
                s = s.replace(iterator.next(), a.j + "#FRIEND#" + a.v);
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslated(n - 1.5, n2 + 0.5, 0.0);
        final boolean glGetBoolean = GL11.glGetBoolean(2394 + 1178 - 3341 + 2811);
        bus.e();
        GL11.glEnable(1480 + 1015 - 2322 + 2869);
        GL11.glBlendFunc(94 + 157 - 214 + 733, 725 + 405 - 902 + 543);
        GL11.glEnable(2738 + 467 - 2188 + 2536);
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 1947954 + 4209817 + 6363397 + 4058668) >> 2 | (n3 & 0xFF000000));
        }
        final float n4 = (n3 >> (0x7 ^ 0x17) & 65 + 176 - 52 + 66) / 255.0f;
        final float n5 = (n3 >> 8 & 133 + 163 - 279 + 238) / 255.0f;
        final float n6 = (n3 & 34 + 210 - 106 + 117) / 255.0f;
        final float n7 = (n3 >> (0x79 ^ 0x61) & 2 + 219 - 120 + 154) / 255.0f;
        final Color color = new Color(n4, n5, n6, n7);
        if (s.contains("§")) {
            final String[] split = s.split("§");
            Color color2 = color;
            CFont cFont = this.font;
            int n8 = 0;
            boolean b2 = false;
            boolean b3 = false;
            boolean b4 = false;
            boolean b5 = false;
            boolean b6 = false;
            for (int i = 0; i < split.length; ++i) {
                if (split[i].length() > 0) {
                    if (i == 0) {
                        cFont.drawString(split[i], n8, 0.0, color2, b);
                        n8 += cFont.getStringWidth(split[i]);
                    }
                    else {
                        final String substring = split[i].substring(1);
                        final char char1 = split[i].charAt(0);
                        final int index = this.colorcodeIdentifiers.indexOf(char1);
                        if (index != -1) {
                            if (index < (0x1F ^ 0xF)) {
                                color2 = this.getColor(this.colorCode[index], n7);
                                b3 = false;
                                b4 = false;
                                b2 = false;
                                b6 = false;
                                b5 = false;
                            }
                            else if (index == (0xA3 ^ 0xB3)) {
                                b2 = true;
                            }
                            else if (index == (0x6F ^ 0x7E)) {
                                b3 = true;
                            }
                            else if (index == (0xD2 ^ 0xC0)) {
                                b5 = true;
                            }
                            else if (index == (0x84 ^ 0x97)) {
                                b6 = true;
                            }
                            else if (index == (0x22 ^ 0x36)) {
                                b4 = true;
                            }
                            else if (index == (0x37 ^ 0x22)) {
                                b3 = false;
                                b4 = false;
                                b2 = false;
                                b6 = false;
                                b5 = false;
                                color2 = color;
                            }
                            else if (index > (0x80 ^ 0x95)) {
                                final Color color3 = this.customColorCodes[char1];
                                color2 = new Color(color3.getRed() / 255.0f, color3.getGreen() / 255.0f, color3.getBlue() / 255.0f, n7);
                            }
                        }
                        if (b3 && b4) {
                            this.boldItalicFont.drawString(b2 ? this.toRandom(cFont, substring) : substring, n8, 0.0, color2, b);
                            cFont = this.boldItalicFont;
                        }
                        else if (b3) {
                            this.boldFont.drawString(b2 ? this.toRandom(cFont, substring) : substring, n8, 0.0, color2, b);
                            cFont = this.boldFont;
                        }
                        else if (b4) {
                            this.italicFont.drawString(b2 ? this.toRandom(cFont, substring) : substring, n8, 0.0, color2, b);
                            cFont = this.italicFont;
                        }
                        else {
                            this.font.drawString(b2 ? this.toRandom(cFont, substring) : substring, n8, 0.0, color2, b);
                            cFont = this.font;
                        }
                        final float n9 = this.font.getHeight() / 16.0f;
                        final int stringHeight = cFont.getStringHeight(substring);
                        if (b5) {
                            this.drawLine(n8 / 2.0 + 1.0, stringHeight / 3, (n8 + cFont.getStringWidth(substring)) / 2.0 + 1.0, stringHeight / 3, n9);
                        }
                        if (b6) {
                            this.drawLine(n8 / 2.0 + 1.0, stringHeight / 2, (n8 + cFont.getStringWidth(substring)) / 2.0 + 1.0, stringHeight / 2, n9);
                        }
                        n8 += cFont.getStringWidth(substring);
                    }
                }
            }
        }
        else {
            this.font.drawString(s, 0.0, 0.0, color, b);
        }
        if (!glGetBoolean) {
            GL11.glDisable(1397 + 2019 - 3053 + 2679);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return (int)(n + this.getStringWidth(s));
    }
    
    public void drawCenteredStringNoShadow(final String s, final float n, final float n2, final int n3) {
        this.drawString(s, n - this.getStringWidth(s) / 2.0f, n2, n3);
    }
    
    private static boolean isFormatSpecial(final char c) {
        return (c >= (0x11 ^ 0x7A) && c <= (0x11 ^ 0x7E)) || (c >= (0x25 ^ 0x6E) && c <= (0x7A ^ 0x35)) || c == (0x7F ^ 0xD) || c == (0x6C ^ 0x3E);
    }
    
    public int getSize() {
        return this.font.getFont().getSize();
    }
    
    private String toRandom(final CFont cFont, final String s) {
        String string = "";
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (g.a(charArray[i])) {
                string += "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8?\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1???®¬???«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f?\u25a0\u0000".toCharArray()[this.fontRandom.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8?\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1???®¬???«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f?\u25a0\u0000".length())];
            }
        }
        return string;
    }
    
    public void drawCenteredString(final String s, final int n, final int n2, final int n3, final boolean b) {
        if (b) {
            this.drawStringWithShadow(s, (float)(n - this.getStringWidth(s) / 2), (float)n2, n3);
        }
        else {
            this.drawString(s, (float)(n - this.getStringWidth(s) / 2), (float)n2, n3);
        }
    }
    
    public int drawStringWithShadow(String s, final float n, final float n2, final int n3) {
        if (Main.getFeatureByName("NameProtect").isEnabled()) {
            final Iterator<String> iterator = FriendManager.friends.iterator();
            while (iterator.hasNext()) {
                s = s.replace(iterator.next(), a.j + "#FRIEND#" + a.v);
            }
            s = s.replace(bib.z().af.c(), a.n + "#HIDDEN#" + a.v);
        }
        this.drawString(s, n + 0.5f, n2 + 0.5f, n3, true);
        this.drawString(s, n, n2, n3, false);
        return 0;
    }
    
    public static String getFormatFromString(final String s) {
        String s2 = "";
        int index = -1;
        final int length = s.length();
        while ((index = s.indexOf(25 + 66 - 71 + 147, index + 1)) != -1) {
            if (index < length - 1) {
                final char char1 = s.charAt(index + 1);
                if (isFormatColor(char1)) {
                    s2 = "§" + char1;
                }
                else {
                    if (!isFormatSpecial(char1)) {
                        continue;
                    }
                    s2 = s2 + "§" + char1;
                }
            }
        }
        return s2;
    }
    
    public int drawString(String s, final float n, final float n2, final int n3) {
        if (Main.getFeatureByName("NameProtect").isEnabled()) {
            final Iterator<String> iterator = FriendManager.friends.iterator();
            while (iterator.hasNext()) {
                s = s.replace(iterator.next(), a.j + "#FRIEND#" + a.v);
            }
            s = s.replace(bib.z().af.c(), a.n + "#HIDDEN#" + a.v);
        }
        return this.drawString(s, n, n2, n3, false);
    }
    
    public int drawString(String replace, final float n, final float n2, final int n3, final boolean b) {
        if (Main.getFeatureByName("NameProtect").isEnabled() && replace.contains(bib.z().af.c())) {
            replace = replace.replace(bib.z().af.c(), a.n + "#HIDDEN#" + a.v);
        }
        int drawLine = 0;
        final String[] split = replace.split("\r");
        for (int i = 0; i < split.length; ++i) {
            drawLine = this.drawLine(split[i], n, n2 + i * this.getHeight(), n3, b);
        }
        return drawLine;
    }
    
    public List<String> wrapWords(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        if (this.getStringWidth(s) > n) {
            final String[] split = s.split(" ");
            String string = "";
            int n2 = 34704 + 23651 + 1673 + 5507;
            for (final String s2 : split) {
                for (int j = 0; j < s2.toCharArray().length; ++j) {
                    if (s2.toCharArray()[j] == 1 + 37 - 20 + 149 && j < s2.toCharArray().length - 1) {
                        n2 = s2.toCharArray()[j + 1];
                    }
                }
                if (this.getStringWidth(string + s2 + " ") < n) {
                    string = string + s2 + " ";
                }
                else {
                    list.add(string);
                    string = ((n2 == -1) ? (s2 + " ") : ("§" + (char)n2 + s2 + " "));
                }
            }
            if (!string.equals("")) {
                if (this.getStringWidth(string) < n) {
                    list.add((n2 == -1) ? (string + " ") : ("§" + (char)n2 + string + " "));
                }
                else {
                    final Iterator<String> iterator = this.formatString(string, n).iterator();
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
        }
        else {
            list.add(s);
        }
        return list;
    }
    
    public List<String> listFormattedStringToWidth(final String s, final int n) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, n).split("\n"));
    }
    
    private static boolean isFormatColor(final char c) {
        return (c >= (0x96 ^ 0xA6) && c <= (0x2C ^ 0x15)) || (c >= (0x3D ^ 0x5C) && c <= (0x40 ^ 0x26)) || (c >= (0x24 ^ 0x65) && c <= (0x7A ^ 0x3C));
    }
    
    public int getStringWidth(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.contains("§")) {
            final String[] split = s.split("§");
            CFont cFont = this.font;
            int n = 0;
            boolean b = false;
            boolean b2 = false;
            for (int i = 0; i < split.length; ++i) {
                if (split[i].length() > 0) {
                    if (i == 0) {
                        n += cFont.getStringWidth(split[i]);
                    }
                    else {
                        final String substring = split[i].substring(1);
                        final int index = this.colorcodeIdentifiers.indexOf(split[i].charAt(0));
                        if (index != -1) {
                            if (index < (0x80 ^ 0x90)) {
                                b = false;
                                b2 = false;
                            }
                            else if (index != (0x63 ^ 0x73)) {
                                if (index == (0x64 ^ 0x75)) {
                                    b = true;
                                }
                                else if (index != (0xF ^ 0x1D)) {
                                    if (index != (0x12 ^ 0x1)) {
                                        if (index == (0x27 ^ 0x33)) {
                                            b2 = true;
                                        }
                                        else if (index == (0xB1 ^ 0xA4)) {
                                            b = false;
                                            b2 = false;
                                        }
                                    }
                                }
                            }
                        }
                        if (b && b2) {
                            cFont = this.boldItalicFont;
                        }
                        else if (b) {
                            cFont = this.boldFont;
                        }
                        else if (b2) {
                            cFont = this.italicFont;
                        }
                        else {
                            cFont = this.font;
                        }
                        n += cFont.getStringWidth(substring);
                    }
                }
            }
            return n / 2;
        }
        return this.font.getStringWidth(s) / 2;
    }
}
