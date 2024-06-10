// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.Color;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.awt.Font;
import org.newdawn.slick.UnicodeFont;
import net.minecraft.client.gui.FontRenderer;

public class UnicodeFontRenderer extends FontRenderer
{
    private final UnicodeFont font;
    
    public UnicodeFontRenderer(final Font awtFont) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        (this.font = new UnicodeFont(awtFont)).addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (SlickException exception) {
            throw new RuntimeException(exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    @Override
    public int drawString(final String string, int x, int y, final int color) {
        if (string == null) {
            return 0;
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean lighting = GL11.glIsEnabled(2896);
        final boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        x *= 2;
        y *= 2;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        final float firstRed = red;
        final float firstGreen = green;
        final float firstBlue = blue;
        final int startX = x;
        GL11.glColor4f(red, green, blue, alpha);
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '§' && i + 1 < string.length()) {
                final char oneMore = Character.toLowerCase(string.charAt(i + 1));
                if (oneMore == 'n') {
                    y += this.FONT_HEIGHT + 2;
                    x = startX;
                }
                final int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                if (colorCode < 16) {
                    try {
                        final int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                        red = (newColor >> 16) / 255.0f;
                        green = (newColor >> 8 & 0xFF) / 255.0f;
                        blue = (newColor & 0xFF) / 255.0f;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                else if (oneMore == 'f') {
                    red = 1.0f;
                    blue = 1.0f;
                    green = 1.0f;
                }
                else if (oneMore == 'r') {
                    red = firstRed;
                    blue = firstBlue;
                    green = firstGreen;
                }
                else if (oneMore == 'g') {
                    red = 0.3f;
                    blue = 0.7f;
                    green = 1.0f;
                }
                ++i;
            }
            else {
                try {
                    final char c = string.charAt(i);
                    final String s = String.valueOf(c);
                    this.font.drawString(x, y, s, new org.newdawn.slick.Color(red, green, blue, alpha));
                    x += this.getCharWidth(c);
                }
                catch (ArrayIndexOutOfBoundsException indexException) {
                    string.charAt(i);
                }
            }
        }
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
        return x;
    }
    
    public void drawStringWithShadow(final String string, final int x, final int y, final int color) {
        this.drawString(StringUtils.stripControlCodes(string), x + 1, y + 1, -16777216);
        this.drawString(string, x, y, color);
    }
    
    @Override
    public int func_175063_a(final String string, final float x, final float y, final int color) {
        return this.drawString(string, (int)x, (int)y, color);
    }
    
    @Override
    public int getCharWidth(final char c) {
        return this.getStringWidth(Character.toString(c));
    }
    
    @Override
    public int getStringWidth(final String string) {
        return this.font.getWidth(StringUtils.stripControlCodes(string));
    }
    
    public int getRealStringWidth(final String string) {
        return this.font.getWidth(StringUtils.stripControlCodes(string)) / 2;
    }
    
    public int getStringHeight(final String string) {
        return this.font.getHeight(StringUtils.stripControlCodes(string));
    }
}
