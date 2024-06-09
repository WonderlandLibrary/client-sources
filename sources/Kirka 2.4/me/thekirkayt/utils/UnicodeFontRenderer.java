/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontRenderer
extends FontRenderer {
    private final UnicodeFont font;

    public UnicodeFontRenderer(Font awtFont) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.font = new UnicodeFont(awtFont);
        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (SlickException exception) {
            throw new RuntimeException(exception);
        }
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789") / 2;
    }

    @Override
    public int drawString(String string, int x, int y, int color) {
        if (string == null) {
            return 0;
        }
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
        y *= 2;
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        float firstRed = red;
        float firstGreen = green;
        float firstBlue = blue;
        int startX = x *= 2;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '\u00a7' && i + 1 < string.length()) {
                int colorCode;
                char oneMore = Character.toLowerCase(string.charAt(i + 1));
                if (oneMore == 'n') {
                    y += this.FONT_HEIGHT + 2;
                    x = startX;
                }
                if ((colorCode = "0123456789abcdefklmnorg".indexOf(oneMore)) < 16) {
                    try {
                        int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                        red = (float)(newColor >> 16) / 255.0f;
                        green = (float)(newColor >> 8 & 255) / 255.0f;
                        blue = (float)(newColor & 255) / 255.0f;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (oneMore == 'f') {
                    red = 1.0f;
                    blue = 1.0f;
                    green = 1.0f;
                } else if (oneMore == 'r') {
                    red = firstRed;
                    blue = firstBlue;
                    green = firstGreen;
                } else if (oneMore == 'g') {
                    red = 0.3f;
                    blue = 0.7f;
                    green = 1.0f;
                }
                ++i;
                continue;
            }
            try {
                char c = string.charAt(i);
                String s = String.valueOf(c);
                this.font.drawString(x, y, s, new org.newdawn.slick.Color(red, green, blue, alpha));
                x += this.getCharWidth(c);
                continue;
            }
            catch (ArrayIndexOutOfBoundsException indexException) {
                string.charAt(i);
            }
        }
        if (texture) {
            GL11.glEnable((int)3553);
        }
        if (lighting) {
            GL11.glEnable((int)2896);
        }
        if (!blend) {
            GL11.glDisable((int)3042);
        }
        GL11.glPopMatrix();
        return x;
    }

    public void drawStringWithShadow(String string, int x, int y, int color) {
        this.drawString(StringUtils.stripControlCodes(string), x + 1, y + 1, -16777216);
        this.drawString(string, x, y, color);
    }

    @Override
    public int func_175063_a(String string, float x, float y, int color) {
        return this.drawString(string, (int)x, (int)y, color);
    }

    @Override
    public int getCharWidth(char c) {
        return this.getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String string) {
        return this.font.getWidth(StringUtils.stripControlCodes(string));
    }

    public int getRealStringWidth(String string) {
        return this.font.getWidth(StringUtils.stripControlCodes(string)) / 2;
    }

    public int getStringHeight(String string) {
        return this.font.getHeight(StringUtils.stripControlCodes(string));
    }
}

