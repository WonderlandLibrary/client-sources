/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.newfont;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.NameSecurity;
import ru.govno.client.newfont.CFont;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class CFontRenderer
extends CFont {
    protected CFont.CharData[] boldChars = new CFont.CharData[167];
    protected CFont.CharData[] italicChars = new CFont.CharData[167];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[167];
    private final float[] charWidthFloat = new float[256];
    private final byte[] glyphWidth = new byte[65536];
    private final int[] charWidth = new int[256];
    private final int[] colorCode = new int[32];
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public float drawNoBSString(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return this.drawString(text, d, y2, color, false);
    }

    public int drawPassword(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        text = NameSecurity.replacedIfActive(text);
        float shadowWidth = this.drawString(text, x + 0.5, y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, double x, double y, int color) {
        text = NameSecurity.replacedIfActive(text);
        return this.drawString(text, x, y, color, false);
    }

    public void drawVGradientString(String text, double x, double y, int color, int color2) {
        GL11.glEnable(3089);
        for (double newY = y - 0.5; newY < y + (double)this.getHeight() + 3.0; newY += 0.5) {
            RenderUtils.scissor(0.0, (float)newY - 1.0f, 100000.0, 0.5);
            GL11.glTranslated(x, y, 0.0);
            this.drawString(text, 0.0, 0.0, ColorUtils.getOverallColorFrom(color, color2, (float)MathUtils.clamp((newY - y) / (double)((float)this.getHeight() + 3.0f), 0.0, 1.0)));
            GL11.glTranslated(-x, -y, 0.0);
        }
        GL11.glDisable(3089);
    }

    public void drawVHGradientString(String text, double x, double y, int color, int color2, int color3, int color4) {
        x -= (double)this.getStringWidth(text);
        float index = 0.0f;
        for (char c : text.toCharArray()) {
            int col1 = ColorUtils.getOverallColorFrom(color, color2, index / (float)this.getStringWidth(text));
            int col2 = ColorUtils.getOverallColorFrom(color4, color3, index / (float)this.getStringWidth(text));
            this.drawVGradientString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x, y, col1, col2);
            index += (float)this.getStringWidth(String.valueOf(c));
        }
    }

    public void drawHGradientString(String text, double x, double y, int color, int color2) {
        x -= (double)this.getStringWidth(text);
        float index = 0.0f;
        for (char c : text.toCharArray()) {
            int col1 = ColorUtils.getOverallColorFrom(color, color2, index / (float)this.getStringWidth(text));
            this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x, y, col1);
            index += (float)this.getStringWidth(String.valueOf(c));
        }
    }

    public void drawClientColoredString(String text, double x, double y, float alphaPC, boolean shadow) {
        x -= (double)this.getStringWidth(text);
        float index = 0.0f;
        for (char c : text.toCharArray()) {
            int col1 = ColorUtils.getOverallColorFrom(ClientColors.getColor1((int)index * 5), ClientColors.getColor2((int)index * 5), index / (float)this.getStringWidth(text));
            if (ColorUtils.getAlphaFromColor(col1 = ColorUtils.swapAlpha(col1, (float)ColorUtils.getAlphaFromColor(col1) * alphaPC)) >= 32) {
                if (shadow) {
                    this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x + 0.5, y + 0.5, ColorUtils.swapDark(col1, ColorUtils.getFullyBrightnessFromColor(col1) / 3.0f));
                }
                this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x, y, col1);
            }
            index += (float)this.getStringWidth(String.valueOf(c));
        }
    }

    public void drawClientColoredString(String text, double x, double y, float alphaPC, boolean shadow, int indexPlus) {
        x -= (double)this.getStringWidth(text);
        float index = 0.0f;
        for (char c : text.toCharArray()) {
            int col1 = ColorUtils.getOverallColorFrom(ClientColors.getColor1((int)index * 5 + indexPlus), ClientColors.getColor2((int)index * 5 + indexPlus), index / (float)this.getStringWidth(text));
            if (ColorUtils.getAlphaFromColor(col1 = ColorUtils.swapAlpha(col1, (float)ColorUtils.getAlphaFromColor(col1) * alphaPC)) >= 32) {
                if (shadow) {
                    this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x + 0.5, y + 0.5, ColorUtils.swapDark(col1, ColorUtils.getFullyBrightnessFromColor(col1) / 1.5f));
                }
                this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x, y, col1);
            }
            index += (float)this.getStringWidth(String.valueOf(c));
        }
    }

    public void drawClientColoredString(String text, double x, double y, float alphaPC, boolean shadow, int indexPlus, boolean reverseColor) {
        x -= (double)this.getStringWidth(text);
        float index = 0.0f;
        for (char c : text.toCharArray()) {
            float pc = index / (float)this.getStringWidth(text);
            int col1 = ColorUtils.getOverallColorFrom(ClientColors.getColor1((int)index * 5 + indexPlus), ClientColors.getColor2((int)index * 5 + indexPlus), reverseColor ? 1.0f - pc : pc);
            if (ColorUtils.getAlphaFromColor(col1 = ColorUtils.swapAlpha(col1, (float)ColorUtils.getAlphaFromColor(col1) * alphaPC)) >= 32) {
                if (shadow) {
                    this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x + 0.5, y + 0.5, ColorUtils.swapDark(col1, ColorUtils.getFullyBrightnessFromColor(col1) / 1.5f));
                }
                this.drawString(String.valueOf(c), (double)((float)this.getStringWidth(text) + index) + x, y, col1);
            }
            index += (float)this.getStringWidth(String.valueOf(c));
        }
    }

    public void drawStringWithOutline(String text, double x, double y, int color) {
        int alpha = ColorUtils.getAlphaFromColor(color) / 3;
        this.drawString(text, x - 0.5, y - 0.5, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x + 0.5, y + 0.5, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x + 0.5, y, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x, y + 0.5, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x - 0.5, y, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x, y - 0.5, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(color)), false);
        this.drawString(text, x, y, color, false);
    }

    public float drawCenteredString(String text, double x, double y, int color) {
        return this.drawString(text, x - (double)(this.getStringWidth(text) / 2), y, color);
    }

    public void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        this.drawStringWithShadow(text, x - (double)((float)this.getStringWidth(text) / 2.0f), y - (double)((float)this.getStringHeight(text) / 2.0f), color);
    }

    public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
        return this.drawStringWithShadow(text, x - (double)((float)this.getStringWidth(text) / 2.0f), y, color);
    }

    public void drawTotalCenteredString(String text, double x, double y, int color) {
        this.drawString(text, x - (double)((float)this.getStringWidth(text) / 2.0f), y - (double)((float)this.getStringHeight(text) / 2.0f), color);
    }

    public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
    }

    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        x -= 1.0;
        if (text == null || text.isEmpty()) {
            return 0.0f;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & new Color(20, 20, 20, 200).getRGB();
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        x *= 2.0;
        y = (y - 3.0) * 2.0;
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.color((float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f, alpha);
        int size = text.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (String.valueOf(character).equals("\u00a7")) {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                } catch (Exception exception) {
                    // empty catch block
                }
                if (colorIndex < 16) {
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float)(colorcode >> 16 & 0xFF) / 255.0f, (float)(colorcode >> 8 & 0xFF) / 255.0f, (float)(colorcode & 0xFF) / 255.0f, alpha);
                } else if (colorIndex == 21 || colorIndex == 17 || colorIndex == 18 || colorIndex == 19 || colorIndex == 20) {
                    GlStateManager.color((float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f, alpha);
                }
                ++i;
                continue;
            }
            if (character >= currentData.length || currentData == null || currentData[character] == null) continue;
            GL11.glBegin(4);
            this.drawChar(currentData, character, (float)x, (float)y);
            GL11.glEnd();
            x += (double)(currentData[character].width - 8 + this.charOffset);
        }
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        return (float)x / 2.0f;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        text = NameSecurity.replacedIfActive(text);
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7' && i < size) {
                int colorIndex = "0123456789abcdefklmnor\u0430\u0431\u0432\u0433\u0434\u0435\u0451\u0436\u0437\u0438\u0439\u043a\u043b\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b\u044c\u044d\u044e\u044f".indexOf(character);
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    currentData = italic ? this.boldItalicChars : this.boldChars;
                } else if (colorIndex == 20) {
                    italic = true;
                    currentData = bold ? this.boldItalicChars : this.italicChars;
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                ++i;
                continue;
            }
            if (character >= currentData.length || character < '\u0000' || currentData[character] == null) continue;
            width += currentData[character].width - 8 + this.charOffset;
        }
        return width / 2;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
    }

    private void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        if ((double)this.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            Object currentWord = "";
            int lastColorCode = 65535;
            String[] arrayOfString1 = words;
            int j = words.length;
            for (int i = 0; i < j; ++i) {
                String word = arrayOfString1[i];
                boolean ii = false;
                while (i < word.toCharArray().length) {
                    char c = word.toCharArray()[i];
                    if (c == '\u00a7' && i < word.toCharArray().length - 1) {
                        lastColorCode = word.toCharArray()[i + 1];
                    }
                    ++i;
                }
                if ((double)this.getStringWidth((String)currentWord + word + " ") < width) {
                    currentWord = (String)currentWord + word + " ";
                    continue;
                }
                finalWords.add((String)currentWord);
                currentWord = 167 + lastColorCode + word + " ";
            }
            if (((String)currentWord).length() > 0) {
                if ((double)this.getStringWidth((String)currentWord) < width) {
                    finalWords.add(167 + lastColorCode + (String)currentWord + " ");
                    currentWord = "";
                } else {
                    for (String s : this.formatString((String)currentWord, width)) {
                        finalWords.add(s);
                    }
                }
            }
        } else {
            finalWords.add(text);
        }
        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        Object currentWord = "";
        int lastColorCode = 65535;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (c == '\u00a7' && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }
            if ((double)this.getStringWidth((String)currentWord + c) < width) {
                currentWord = (String)currentWord + c;
                continue;
            }
            finalWords.add((String)currentWord);
            currentWord = 167 + lastColorCode + String.valueOf(c);
        }
        if (((String)currentWord).length() > 0) {
            finalWords.add((String)currentWord);
        }
        return finalWords;
    }

    String wrapFormattedStringToWidth(String str, int wrapWidth) {
        if (str.length() <= 1) {
            return str;
        }
        int i = this.sizeStringToWidth(str, wrapWidth);
        if (str.length() <= i) {
            return str;
        }
        String s = str.substring(0, i);
        char c0 = str.charAt(i);
        boolean flag = c0 == ' ' || c0 == '\n';
        String s1 = CFontRenderer.getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
        return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth);
    }

    public static String getFormatFromString(String text) {
        Object s = "";
        int i = -1;
        int j = text.length();
        while ((i = text.indexOf(167, i + 1)) != -1) {
            if (i >= j - 1) continue;
            char c0 = text.charAt(i + 1);
            if (CFontRenderer.isFormatColor(c0)) {
                s = "\u00a7" + c0;
                continue;
            }
            if (!CFontRenderer.isFormatSpecial(c0)) continue;
            s = (String)s + "\u00a7" + c0;
        }
        return s;
    }

    private int sizeStringToWidth(String str, int wrapWidth) {
        int j;
        str = NameSecurity.replacedIfActive(str);
        int i = str.length();
        float f = 0.0f;
        int k = -1;
        boolean flag = false;
        for (j = 0; j < i; ++j) {
            char c0 = str.charAt(j);
            switch (c0) {
                case '\n': {
                    --j;
                    break;
                }
                case ' ': {
                    k = j;
                }
                default: {
                    f += this.getCharWidthFloat(c0);
                    if (!flag) break;
                    f += 1.0f;
                    break;
                }
                case '\u00a7': {
                    char c1;
                    if (j >= i - 1) break;
                    if ((c1 = str.charAt(++j)) != 'l' && c1 != 'L') {
                        if (c1 != 'r' && c1 != 'R' && !CFontRenderer.isFormatColor(c1)) break;
                        flag = false;
                        break;
                    }
                    flag = true;
                }
            }
            if (c0 == '\n') {
                k = ++j;
                break;
            }
            if (Math.round(f) > wrapWidth) break;
        }
        return j != i && k != -1 && k < j ? k : j;
    }

    private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
        if (p_getCharWidthFloat_1_ == '\u00a7') {
            return -1.0f;
        }
        if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != '\u00a0') {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_getCharWidthFloat_1_);
            if (p_getCharWidthFloat_1_ > '\u0000' && i != -1) {
                return this.charWidthFloat[i];
            }
            if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
                int j = this.glyphWidth[p_getCharWidthFloat_1_] & 0xFF;
                int k = j >>> 4;
                int l = j & 0xF;
                return (++l - k) / 2 + 1;
            }
            return 0.0f;
        }
        return this.charWidthFloat[32];
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0f;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;
        for (int k = i; k >= 0 && k < text.length() && f < (float)width; k += j) {
            char c0 = text.charAt(k);
            float f1 = this.getCharWidthFloat(c0);
            if (flag) {
                flag = false;
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag1 = false;
                    }
                } else {
                    flag1 = true;
                }
            } else if (f1 < 0.0f) {
                flag = true;
            } else {
                f += f1;
                if (flag1) {
                    f += 1.0f;
                }
            }
            if (f > (float)width) break;
            if (reverse) {
                stringbuilder.insert(0, c0);
                continue;
            }
            stringbuilder.append(c0);
        }
        return stringbuilder.toString();
    }

    private static boolean isFormatSpecial(char formatChar) {
        return formatChar >= 'k' && formatChar <= 'o' || formatChar >= 'K' && formatChar <= 'O' || formatChar == 'r' || formatChar == 'R';
    }

    private static boolean isFormatColor(char colorChar) {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index >> 0 & 1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }
}

