package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.awt.*;
import org.newdawn.slick.font.effects.*;
import org.newdawn.slick.*;
import org.lwjgl.opengl.*;

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
            throw new RuntimeException((Throwable)exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    @Override
    public int drawString(final String string, int x, int y, final int color) {
        if (string == null) {
            return -1;
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
        GL11.glBlendFunc(770, 771);
        this.font.drawString((float)(x *= 2), (float)(y *= 2), string, new org.newdawn.slick.Color(color));
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
        return this.getStringWidth(string);
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
        return this.font.getWidth(string) / 2;
    }
    
    public int getStringHeight(final String string) {
        return this.font.getHeight(string) / 2;
    }
}
