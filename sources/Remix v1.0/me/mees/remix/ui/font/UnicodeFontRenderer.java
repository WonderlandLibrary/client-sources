package me.mees.remix.ui.font;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.awt.*;
import org.newdawn.slick.font.effects.*;
import org.newdawn.slick.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

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
        catch (SlickException var3) {
            throw new RuntimeException((Throwable)var3);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    @Override
    public int drawString(final String string, float x, float y, final int color) {
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
        x *= 2.0f;
        y *= 2.0f;
        this.font.drawString(x, y, string, new org.newdawn.slick.Color(color));
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glPopMatrix();
        GlStateManager.bindCurrentTexture();
        return (int)x;
    }
    
    public float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(text, x + 1.0f, y + 1.0f, -16777216);
        return (float)this.drawString(text, x, y, color);
    }
    
    @Override
    public int getCharWidth(final char c) {
        return this.getStringWidth(Character.toString(c));
    }
    
    @Override
    public int getStringWidth(final String string) {
        return this.font.getWidth(string) / 2;
    }
    
    public int getStringHeight(final String string) {
        return this.font.getHeight(string) / 2;
    }
    
    public void drawCenteredString(final String text, final float x, final float y, final int color) {
        this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
}
