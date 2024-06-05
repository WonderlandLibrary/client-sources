package cn.Hanabi.utils.fontmanager;

import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import ClassSub.*;

public class UnicodeFontRenderer extends FontRenderer
{
    private final Class139 font;
    public HashMap<String, Float> widthMap;
    public HashMap<String, Float> heightMap;
    
    public UnicodeFontRenderer(final Font awtFont) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.widthMap = new HashMap<String, Float>();
        this.heightMap = new HashMap<String, Float>();
        (this.font = new Class139(awtFont)).addAsciiGlyphs();
        this.font.getEffects().add(new Class38(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (Class341 exception) {
            throw new RuntimeException(exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    public UnicodeFontRenderer(final Font awtFont, final int fontPageStart, final int fontPageEnd) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.widthMap = new HashMap<String, Float>();
        this.heightMap = new HashMap<String, Float>();
        (this.font = new Class139(awtFont)).addGlyphs(fontPageStart, fontPageEnd);
        this.font.getEffects().add(new Class38(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (Class341 exception) {
            throw new RuntimeException(exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    public UnicodeFontRenderer(final Font awtFont, final boolean bol) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.widthMap = new HashMap<String, Float>();
        this.heightMap = new HashMap<String, Float>();
        (this.font = new Class139(awtFont)).addAsciiGlyphs();
        this.font.addGlyphs(0, 65535);
        this.font.getEffects().add(new Class38(Color.WHITE));
        try {
            this.font.loadGlyphs();
        }
        catch (Class341 exception) {
            throw new RuntimeException(exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
    }
    
    public int drawString(final String string, final int x, final int y, final int color) {
        return this.drawString(string, x, y, color);
    }
    
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
        this.font.drawString(x, y, string, new Class26(color));
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
        GlStateManager.bindTexture(0);
        return (int)x;
    }
    
    public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(text, x + 1.0f, y + 1.0f, Class15.BLACK.c);
        return this.drawString(text, x, y, color);
    }
    
    public int getCharWidth(final char c) {
        return this.getStringWidth(Character.toString(c));
    }
    
    public int getStringWidth(final String string) {
        if (this.widthMap.containsKey(string)) {
            return (int)(Object)this.widthMap.get(string);
        }
        final float width = this.font.getWidth(string) / 2;
        this.widthMap.put(string, width);
        return (int)width;
    }
    
    public float getStringHeight(final String string) {
        if (this.heightMap.containsKey(string)) {
            return this.heightMap.get(string);
        }
        final float height = this.font.getHeight(string) / 2;
        this.heightMap.put(string, height);
        return height;
    }
    
    public int drawStringWithColor(String text, final float x, final float y, int color, final int alpha) {
        text = "§r" + text;
        float len = -1.0f;
        final String[] split;
        final String[] array = split = text.split("§");
        for (String str : split) {
            if (str.length() >= 1) {
                switch (str.charAt(0)) {
                    case '0': {
                        color = new Color(0, 0, 0).getRGB();
                        break;
                    }
                    case '1': {
                        color = new Color(0, 0, 170).getRGB();
                        break;
                    }
                    case '2': {
                        color = new Color(0, 170, 0).getRGB();
                        break;
                    }
                    case '3': {
                        color = new Color(0, 170, 170).getRGB();
                        break;
                    }
                    case '4': {
                        color = new Color(170, 0, 0).getRGB();
                        break;
                    }
                    case '5': {
                        color = new Color(170, 0, 170).getRGB();
                        break;
                    }
                    case '6': {
                        color = new Color(255, 170, 0).getRGB();
                        break;
                    }
                    case '7': {
                        color = new Color(170, 170, 170).getRGB();
                        break;
                    }
                    case '8': {
                        color = new Color(85, 85, 85).getRGB();
                        break;
                    }
                    case '9': {
                        color = new Color(85, 85, 255).getRGB();
                        break;
                    }
                    case 'a': {
                        color = new Color(85, 255, 85).getRGB();
                        break;
                    }
                    case 'b': {
                        color = new Color(85, 255, 255).getRGB();
                        break;
                    }
                    case 'c': {
                        color = new Color(255, 85, 85).getRGB();
                        break;
                    }
                    case 'd': {
                        color = new Color(255, 85, 255).getRGB();
                        break;
                    }
                    case 'e': {
                        color = new Color(255, 255, 85).getRGB();
                        break;
                    }
                    case 'f': {
                        color = new Color(255, 255, 255).getRGB();
                        break;
                    }
                    case 'r': {
                        color = new Color(255, 255, 255).getRGB();
                        break;
                    }
                }
                final Color col = new Color(color);
                str = str.substring(1, str.length());
                this.drawString(str, x + len + 0.5f, y + 0.5f, Class15.BLACK.c);
                this.drawString(str, x + len, y, this.getColor(col.getRed(), col.getGreen(), col.getBlue(), alpha));
                len += this.getStringWidth(str) + 1;
            }
        }
        return (int)len;
    }
    
    public int drawStringWithColor(String text, final float x, final float y, int color) {
        text = "§r" + text;
        float len = -1.0f;
        final String[] split;
        final String[] array = split = text.split("§");
        for (String str : split) {
            if (str.length() >= 1) {
                switch (str.charAt(0)) {
                    case '0': {
                        color = new Color(0, 0, 0).getRGB();
                        break;
                    }
                    case '1': {
                        color = new Color(0, 0, 170).getRGB();
                        break;
                    }
                    case '2': {
                        color = new Color(0, 170, 0).getRGB();
                        break;
                    }
                    case '3': {
                        color = new Color(0, 170, 170).getRGB();
                        break;
                    }
                    case '4': {
                        color = new Color(170, 0, 0).getRGB();
                        break;
                    }
                    case '5': {
                        color = new Color(170, 0, 170).getRGB();
                        break;
                    }
                    case '6': {
                        color = new Color(255, 170, 0).getRGB();
                        break;
                    }
                    case '7': {
                        color = new Color(170, 170, 170).getRGB();
                        break;
                    }
                    case '8': {
                        color = new Color(85, 85, 85).getRGB();
                        break;
                    }
                    case '9': {
                        color = new Color(85, 85, 255).getRGB();
                        break;
                    }
                    case 'a': {
                        color = new Color(85, 255, 85).getRGB();
                        break;
                    }
                    case 'b': {
                        color = new Color(85, 255, 255).getRGB();
                        break;
                    }
                    case 'c': {
                        color = new Color(255, 85, 85).getRGB();
                        break;
                    }
                    case 'd': {
                        color = new Color(255, 85, 255).getRGB();
                        break;
                    }
                    case 'e': {
                        color = new Color(255, 255, 85).getRGB();
                        break;
                    }
                    case 'f': {
                        color = new Color(255, 255, 255).getRGB();
                        break;
                    }
                    case 'r': {
                        color = new Color(255, 255, 255).getRGB();
                        break;
                    }
                }
                str = str.substring(1, str.length());
                this.drawString(str, x + len + 0.5f, y + 0.5f, Class15.BLACK.c);
                this.drawString(str, x + len, y, color);
                len += this.getStringWidth(str) + 1;
            }
        }
        return (int)len;
    }
    
    public int getColor(final int brightness, final int alpha) {
        return this.getColor(brightness, brightness, brightness, alpha);
    }
    
    public int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public void drawCenteredString(final String text, final float x, final float y, final int color) {
        this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public void drawOutlinedString(final String text, final float x, final float y, final int borderColor, final int color) {
        this.drawString(text, x - 0.5f, y, borderColor);
        this.drawString(text, x + 0.5f, y, borderColor);
        this.drawString(text, x, y - 0.5f, borderColor);
        this.drawString(text, x, y + 0.5f, borderColor);
        this.drawString(text, x, y, color);
    }
    
    public void drawCenterOutlinedString(final String text, final float x, final float y, final int borderColor, final int color) {
        this.drawString(text, x - this.getStringWidth(text) / 2 - 0.5f, y, borderColor);
        this.drawString(text, x - this.getStringWidth(text) / 2 + 0.5f, y, borderColor);
        this.drawString(text, x - this.getStringWidth(text) / 2, y - 0.5f, borderColor);
        this.drawString(text, x - this.getStringWidth(text) / 2, y + 0.5f, borderColor);
        this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
}
