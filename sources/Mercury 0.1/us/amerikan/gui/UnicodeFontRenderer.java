/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.gui;

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
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }

    @Override
    public int drawString(String string, int x2, int y2, int color) {
        if (string == null) {
            return 0;
        }
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
        this.font.drawString(x2 *= 2, y2 *= 2, string, new org.newdawn.slick.Color(color));
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
        return x2;
    }

    @Override
    public int getCharWidth(char c2) {
        return this.getStringWidth(Character.toString(c2));
    }

    @Override
    public int getStringWidth(String string) {
        return this.font.getWidth(string) / 2;
    }

    public int getStringHeight(String string) {
        return this.font.getHeight(string) / 2;
    }

    public int drawCenteredString(String text, int x2, int y2, int color) {
        return this.drawString(text, x2 - this.getStringWidth(text) / 2 + 2, y2, color);
    }

    public int getRealStringWidth(String string) {
        return this.font.getWidth(StringUtils.stripControlCodes(string)) / 2;
    }
}

