package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 15:58
 */
public class CustomFont extends FontRenderer {

    public java.awt.Font UIFont1;
    private UnicodeFont unicodeFont = null;

    public CustomFont(String path, int size) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), Minecraft.getMinecraft().isUnicode());

        try {
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(path));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, size);

            unicodeFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            unicodeFont.addAsciiGlyphs();
            unicodeFont.loadGlyphs();
            FONT_HEIGHT = unicodeFont.getHeight("abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMOPQRSTUVWXYZÜÄäü1234567890") / 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        } else {
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
            x *= 2;
            y *= 2;

            float red = (float) (color >> 16 & 255) / 255.0F;
            float blue = (float) (color >> 8 & 255) / 255.0F;
            float green = (float) (color & 255) / 255.0F;
            float alpha = (float) (color >> 24 & 255) / 255.0F;

            boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
            boolean lighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
            boolean texture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
            if (!blend)
                GL11.glEnable(GL11.GL_BLEND);
            if (lighting)
                GL11.glDisable(GL11.GL_LIGHTING);
            if (texture)
                GL11.glDisable(GL11.GL_TEXTURE_2D);


            for (int i = 0; i < text.length(); ++i) {
                char c0 = text.charAt(i);
                if (c0 == 167 && i + 1 < text.length()) {
                    int i1 = "0123456789abcdefklmnor".indexOf(text.toLowerCase().charAt(i + 1));
                    if (i1 < 16) {
                        if (i1 < 0 || i1 > 15) {
                            i1 = 15;
                        }
                        int j1 = this.colorCode[i1];
                        if (Config.isCustomColors()) {
                            j1 = CustomColors.getTextColor(i1, j1);
                        }
                        red = (float) (j1 >> 16 & 255) / 255.0F;
                        blue = (float) (j1 >> 8 & 255) / 255.0F;
                        green = (float) (j1 & 255) / 255.0F;
                        ++i;
                    }
                } else {
                    GlStateManager.color(1, 1, 1, 1);
                    unicodeFont.drawString(x, y, Character.toString(c0), new org.newdawn.slick.Color(red, blue, green, alpha));
                    x += this.getCharWidth(c0);
                }
            }
            if (texture)
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            if (lighting)
                GL11.glEnable(GL11.GL_LIGHTING);
            if (!blend)
                GL11.glDisable(GL11.GL_BLEND);
            GlStateManager.bindTexture(0);
            GlStateManager.color(0, 0, 0, 0);
            GL11.glPopMatrix();
            return  x;
        }
    }

    @Override
    public float drawString(String text, float x, float y, int color) {
        return this.renderString(text, x, y, color, false);
    }

    @Override
    public float drawStringWithShadow(String text, float x, float y, int color) {
        this.renderString(EnumChatFormatting.getTextWithoutFormattingCodes(text), x + 0.3F, y + 0.3F, Color.BLACK.getRGB(), true);
        return this.renderString(text, x, y, color, true);
    }

    @Override
    public int getCharWidth(char character) {
        return unicodeFont.getWidth(EnumChatFormatting.getTextWithoutFormattingCodes(Character.toString(character)));
    }

    @Override
    public int getStringWidth(String text) {
        float width = 0.0F;
        String str = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        for (char c : str.toCharArray()) {
            width += unicodeFont.getWidth(Character.toString(c));
        }
        return (int) (width / 2.0F);
    }
}
