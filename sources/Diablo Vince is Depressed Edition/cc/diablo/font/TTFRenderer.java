/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.newdawn.slick.Color
 *  org.newdawn.slick.UnicodeFont
 *  org.newdawn.slick.font.effects.ColorEffect
 */
package cc.diablo.font;

import java.awt.Color;
import java.awt.Font;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TTFRenderer {
    private final UnicodeFont unicodeFont;
    private final int[] colorCodes = new int[32];
    private final float spacing;

    public TTFRenderer(String fontName, int fontType, int size) {
        this(fontName, fontType, size, 0.0f);
    }

    public TTFRenderer(String fontName, int fontType, int size, float spacing) {
        this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
        this.spacing = spacing;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; ++i) {
            int shadow = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + shadow;
            int green = (i >> 1 & 1) * 170 + shadow;
            int blue = (i >> 0 & 1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }

    public int drawString(String text, float x, float y, int color) {
        y *= 2.0f;
        float originalX = x *= 2.0f;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        boolean blend = GL11.glIsEnabled((int)3042);
        boolean lighting = GL11.glIsEnabled((int)2896);
        boolean texture = GL11.glIsEnabled((int)3553);
        if (!blend) {
            GL11.glEnable((int)3042);
        }
        if (lighting) {
            GL11.glDisable((int)2896);
        }
        if (texture) {
            GL11.glDisable((int)3553);
        }
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        for (char c : characters) {
            if (c == '\r') {
                x = originalX;
            }
            if (c == '\n') {
                y += this.getHeight(Character.toString(c)) * 2.0f;
            }
            if (c != '\u00a7' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '\u00a7')) {
                this.unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
                x += this.getWidth(Character.toString(c)) * 2.0f;
            } else if (c == ' ') {
                x += (float)this.unicodeFont.getSpaceWidth();
            } else if (c == '\u00a7' && index != characters.length - 1) {
                int col;
                int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                if (codeIndex < 0) continue;
                currentColor = col = this.colorCodes[codeIndex];
            }
            ++index;
        }
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        if (texture) {
            GL11.glEnable((int)3553);
            GlStateManager.bindCurrentTexture();
            GlStateManager.bindTexture(0);
        }
        if (lighting) {
            GL11.glEnable((int)2896);
        }
        if (!blend) {
            GL11.glDisable((int)3042);
        }
        GL11.glPopMatrix();
        return (int)x;
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0);
        return this.drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float)((int)this.getWidth(text) / 2), y, color);
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0);
        this.drawCenteredString(text, x, y, color);
    }

    public float getWidth(String s) {
        float width = 0.0f;
        String str = StringUtils.stripControlCodes(s);
        for (char c : str.toCharArray()) {
            width += (float)this.unicodeFont.getWidth(Character.toString(c)) + this.spacing;
        }
        return width / 2.0f;
    }

    public float getCharWidth(char c) {
        return this.unicodeFont.getWidth(String.valueOf(c));
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }
}

