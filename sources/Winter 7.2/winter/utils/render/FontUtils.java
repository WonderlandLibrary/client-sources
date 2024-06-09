/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render;

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

    public FontUtils(String fontName, int fontType, int size) {
        this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
        try {
            this.unicodeFont.addAsciiGlyphs();
            this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        int i2 = 0;
        while (i2 < 32) {
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
            ++i2;
        }
    }

    public void drawString(String text, float x2, float y2, int color) {
        y2 *= 2.0f;
        float originalX = x2 *= 2.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        char[] array = characters;
        int length = array.length;
        int i2 = 0;
        while (i2 < length) {
            block9 : {
                char c2 = array[i2];
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
                    int codeIndex = "0123456789abcdef".indexOf(text.charAt(index + 1));
                    if (codeIndex < 0) break block9;
                    int n = currentColor = this.colorCodes[codeIndex];
                }
                ++index;
            }
            ++i2;
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public void drawStringWithShadow(String text, float x2, float y2, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x2 + 0.5f, y2 + 0.5f, 0);
        this.drawString(text, x2, y2, color);
    }

    public void drawCenteredString(String text, float x2, float y2, int color) {
        this.drawString(text, x2 / 2.0f - this.getWidth(text) / 2.0f, y2, color);
    }

    public void drawCenterdStringWidthShadow(String text, float x2, float y2, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x2 + 0.5f, y2 + 0.5f, color);
        this.drawString(text, x2, y2, color);
    }

    public float getWidth(String s) {
        float width = 0.0f;
        String s2 = StringUtils.stripControlCodes(s);
        char[] charArray = s2.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        while (i2 < length) {
            char c2 = charArray[i2];
            width += (float)this.unicodeFont.getWidth(Character.toString(c2));
            ++i2;
        }
        return width / 2.0f;
    }

    public int getWidthInt(String s) {
        int width = 0;
        String s2 = StringUtils.stripControlCodes(s);
        char[] charArray = s2.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        while (i2 < length) {
            char c2 = charArray[i2];
            width += this.unicodeFont.getWidth(Character.toString(c2));
            ++i2;
        }
        return width / 2;
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }
}

