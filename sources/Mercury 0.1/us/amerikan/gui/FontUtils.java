/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontUtils {
    private Minecraft mc = Minecraft.getMinecraft();
    private final UnicodeFont unicodeFont;
    private final int[] colorCodes = new int[32];
    private int fontType;
    private int size;
    private String fontName;
    private float kerning;

    public FontUtils(String fontName, int fontType, int size) {
        this(fontName, fontType, size, 0.0f);
    }

    public FontUtils(String fontName, int fontType, int size, float kerning) {
        this.fontName = fontName;
        this.fontType = fontType;
        this.size = size;
        this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        for (int i2 = 0; i2 < 32; ++i2) {
            int shadow = (i2 >> 3 & 1) * 85;
            int red = (i2 >> 2 & 1) * 170 + shadow;
            int green = (i2 >> 1 & 1) * 170 + shadow;
            int blue = (i2 >> 0 & 1) * 170 + shadow;
            if (i2 == 6) {
                red += 85;
            }
            if (i2 >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i2] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }
    }

    public int drawString(String text, float x2, float y2, int color) {
        y2 *= 2.0f;
        float originalX = x2 *= 2.0f;
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        boolean blend = GL11.glIsEnabled(3042);
        boolean lighting = GL11.glIsEnabled(2896);
        boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        for (char c2 : characters) {
            if (c2 == '\r') {
                x2 = originalX;
            }
            if (c2 == '\n') {
                y2 += this.getHeight(Character.toString(c2)) * 2.0f;
            }
            if (c2 != '\u00a7' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '\u00a7')) {
                this.unicodeFont.drawString(x2, y2, Character.toString(c2), new org.newdawn.slick.Color(currentColor));
                x2 += this.getWidth(Character.toString(c2)) * 2.0f;
            } else if (c2 == ' ') {
                x2 += (float)this.unicodeFont.getSpaceWidth();
            } else if (c2 == '\u00a7' && index != characters.length - 1) {
                int col;
                int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                if (codeIndex < 0) continue;
                currentColor = col = this.colorCodes[codeIndex];
            }
            ++index;
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
        return (int)x2;
    }

    public int drawStringWithShadow(String text, float x2, float y2, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x2 + 0.5f, y2 + 0.5f, 0);
        return this.drawString(text, x2, y2, color);
    }

    public void drawCenteredString(String text, float x2, float y2, int color) {
        this.drawString(text, x2 - (float)((int)this.getWidth(text) / 2), y2, color);
    }

    public void drawCenteredStringWithShadow(String text, float x2, float y2, int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x2 + 0.5f, y2 + 0.5f, color);
        this.drawCenteredString(text, x2, y2, color);
    }

    public float getWidth(String s2) {
        float width = 0.0f;
        String str = StringUtils.stripControlCodes(s2);
        for (char c2 : str.toCharArray()) {
            width += (float)this.unicodeFont.getWidth(Character.toString(c2)) + this.kerning;
        }
        return width / 2.0f;
    }

    public float getCharWidth(char c2) {
        return this.unicodeFont.getWidth(String.valueOf(c2));
    }

    public float getHeight(String s2) {
        return (float)this.unicodeFont.getHeight(s2) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }

    public String trimStringToWidth(String par1Str, int par2) {
        StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        int var6 = 0;
        int var7 = 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < (float)par2; var10 += var7) {
            char var11 = par1Str.charAt(var10);
            float var12 = this.getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0f) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9) {
                    var5 += 1.0f;
                }
            }
            if (var5 > (float)par2) break;
            var4.append(var11);
        }
        return var4.toString();
    }
}

