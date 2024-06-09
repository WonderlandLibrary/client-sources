/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.newdawn.slick.Color
 *  org.newdawn.slick.SlickException
 *  org.newdawn.slick.UnicodeFont
 *  org.newdawn.slick.font.effects.ColorEffect
 */
package pw.vertexcode.util.font;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
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
            throw new RuntimeException((Throwable)exception);
        }
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }

    public UnicodeFontRenderer(InputStream streamFont, int size) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.font = new UnicodeFont(streamFont, size);
        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (SlickException exception) {
            throw new RuntimeException((Throwable)exception);
        }
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
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
        GL11.glBlendFunc((int)770, (int)771);
        this.font.drawString((float)(x *= 2), (float)(y *= 2), string, new org.newdawn.slick.Color(color));
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

    @Override
    public int drawStringWithShadow(String string, float x, float y, int color) {
        this.drawString(string, (int)x - 1, (int)y - 1, -16777216);
        return this.drawString(string, (int)x, (int)y, color);
    }

    @Override
    public int getCharWidth(char c) {
        return this.getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String string) {
        return this.font.getWidth(string) / 2;
    }

    public int getStringHeight(String string) {
        return this.font.getHeight(string) / 2;
    }

    public UnicodeFont getFont() {
        return this.font;
    }

    public void drawCenteredString(String title, int x, int y, int c) {
        this.drawString(title, x - this.getStringWidth(title) / 2, y, c);
    }
}

