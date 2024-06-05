package alphentus.utils.fontrenderer;

import java.awt.Color;
import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * @author avox
 * @since avox on 17/07/2020.
 */

public class UnicodeFontRenderer extends FontRenderer {
    private final UnicodeFont font;

    public UnicodeFontRenderer(Font awtFont) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.font = new UnicodeFont(awtFont);
        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect(Color.WHITE));

        try {
            this.font.loadGlyphs();
        } catch (SlickException var3) {
            throw new RuntimeException(var3);
        }

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;

        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }


            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }

    }


    public float drawString(String string, float x, float y, int color) {
        if (string == null) {
            return 0.0F;
        } else {
            GL11.glPushMatrix();
            GL11.glScaled(0.5D, 0.5D, 0.5D);
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

            x *= 2.0F;
            y *= 2.0F;

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }


            float red = (float) (color >> 16 & 255) / 255.0F;
            float blue = (float) (color >> 8 & 255) / 255.0F;
            float green = (float) (color & 255) / 255.0F;
            float alpha = (float) (color >> 24 & 255) / 255.0F;
            GlStateManager.color(red, blue, green, alpha);

            this.font.drawString(x, y, string, new org.newdawn.slick.Color(red, blue, green, alpha));
            if (texture) {
                GL11.glEnable(3553);
            }

            if (lighting) {
                GL11.glEnable(2896);
            }

            if (!blend) {
                GL11.glDisable(3042);
            }

            GlStateManager.color(0.0F, 0.0F, 0.0F);
            GL11.glPopMatrix();
            GlStateManager.bindTexture(0);
            return (float) ((int) x);
        }
    }

    public int drawStringWithShadow(String text, float x, float y, int color, boolean useShadow) {
        if(useShadow)
        this.drawString(EnumChatFormatting.getTextWithoutFormattingCodes(text), x +0.5F, y + 0.5F, -16777216);
        return (int) this.drawString(text, x, y, color);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(EnumChatFormatting.getTextWithoutFormattingCodes(text), x +0.5F, y + 0.5F, -16777216);
        return (int) this.drawString(text, x, y, color);
    }

    public int getCharWidth(char c) {
        return this.getStringWidth(Character.toString(c));
    }

    public int getStringWidth(String string) {
        return this.font.getWidth(string) / 2;
    }

    public int getStringHeight(String string) {
        return this.font.getHeight(string) / 2;
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y, color);
    }
}
