package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Gui
{
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    protected float zLevel;

    /**
     * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
     */
    protected void drawHorizontalLine(int startX, int endX, int y, int color)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    protected void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }
    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */

    public static void drawRect(double d, double e, double g, double h, int color)
    {
        if (d < g)
        {
            int i = (int) d;
            d = g;
            g = i;
        }

        if (e < h)
        {
            int j = (int) e;
            e = h;
            h = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)d, (double)h, 0.0D).endVertex();
        worldrenderer.pos((double)g, (double)h, 0.0D).endVertex();
        worldrenderer.pos((double)g, (double)e, 0.0D).endVertex();
        worldrenderer.pos((double)d, (double)e, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRect(int left, int top, int right, int bottom, int color)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     */
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        worldrenderer.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        worldrenderer.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0) * f1)).endVertex();
        worldrenderer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + 0) * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
     */
    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }
    public static void circle(float f2, float g2, float round, int color) {
    }

    public static void drawCircle(float f2, float g2, float radius, Color color) {
    }

    public static void drawRainbowRectVertical(int x, int y, int x1, int y1, int segments, float alpha) {
        if(segments < 1) {
            segments = 1;
        }
        if(segments > y1 - y) {
            segments = y1 - y;
        }
        int segmentLength = (y1 - y) / segments;
        long time = System.nanoTime();
        for(int i = 0; i < segments; ++i) {
            Minecraft.getMinecraft().ingameGUI.drawGradientRect(x, y + segmentLength * i - 1, x1, y + (segmentLength + 1) * i, rainbow(time, (float)i * 0.1F, alpha).getRGB(), rainbow(time, ((float)i + 0.1F) * 0.1F, alpha).getRGB());
        }
    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float)time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int)color);
        return new Color((float)c.getRed() / 255.0F * fade, (float)c.getGreen() / 255.0F * fade, (float)c.getBlue() / 255.0F * fade, (float)c.getAlpha() / 255.0F);
    }
    public static int rainbow2(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 0.75f, 1f).getRGB();
    }
    public static void drawFilledCircle(float xx, float yy, float radius, int col) {
        float f = (float)(col >> 24 & 255) / 255.0f;
        float f1 = (float)(col >> 16 & 255) / 255.0f;
        float f2 = (float)(col >> 8 & 255) / 255.0f;
        float f3 = (float)(col & 255) / 255.0f;
        int sections = 50;
        double dAngle = 6.283185307179586 / (double)sections;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        int i = 0;
        while (i < sections) {
            float x = (float)((double)radius * Math.sin((double)((double)i * dAngle)));
            float y = (float)((double)radius * Math.cos((double)((double)i * dAngle)));
            GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
            ++i;
        }
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }
    public static void drawFilledCircle(float xx, float yy, float radius, Color col) {
        int sections = 50;
        double dAngle = 6.283185307179586 / (double)sections;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        int i = 0;
        while (i < sections) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)((float)col.getRed() / 255.0f), (float)((float)col.getGreen() / 255.0f), (float)((float)col.getBlue() / 255.0f), (float)((float)col.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
            ++i;
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawLine(float x, float y, float x2, float y2, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GlStateManager.color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, y, 0.0).endVertex();
        worldrenderer.pos(x2, y2, 0.0).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

//    public static void drawRoundedRectTop(float x, float y, float x2, float y2, float round, int color) {
//        GlStateManager.disableBlend();
//        x = (float) ((double) x + ((double) (round / 2.0f) + 0));
//        y = (float) ((double) y + ((double) (round / 2.0f) + 0));
//        x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0));
//        y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0));
//        drawCircle((int) x2 - (int) round / 2, (int) y + (int) round / 2, round, 0, 90, color);
//        drawCircle((int) x + (int) round / 2, (int) y + (int) round / 2, round, 90, 180, color);
//        drawRect(x - round / 2.0f, y + round / 2.0f, x2, y2 + round / 2.0f, color);
//        drawRect(x2 + round / 2.0f - (round / 2), y + round / 2.0f, x2 + round / 2.0f, y2 + round / 2, color);
//        drawRect(x + round / 2.0f, y - round / 2.0f, x2 - round / 2.0f, y + round / 2.0f, color);
//
//        GlStateManager.disableBlend();
//    }

//    public static void drawCircle(int x, int y, float radius, int startPi, int endPi, int c) {
//        float f = ((c >> 24) & 0xff) / 255F;
//        float f1 = ((c >> 16) & 0xff) / 255F;
//        float f2 = ((c >> 8) & 0xff) / 255F;
//        float f3 = (c & 0xff) / 255F;
//        GL11.glColor4f(f1, f2, f3, f);
//        GlStateManager.enableAlpha();
//        GlStateManager.enableBlend();
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//        GlStateManager.alphaFunc(516, 0.001F);
//        Tessellator tess = Tessellator.getInstance();
//        WorldRenderer render = tess.getWorldRenderer();
//
//        for (double i = startPi; i < endPi; i++) {
//            double cs = i * Math.PI / 180;
//            double ps = (i - 1) * Math.PI / 180;
//            double[] outer = new double[]
//                    {
//                            Math.cos(cs) * radius, -Math.sin(cs) * radius,
//                            Math.cos(ps) * radius, -Math.sin(ps) * radius
//                    };
//            render.startDrawing(6);
//            render.addVertex(x + outer[2], y + outer[3], 0);
//            render.addVertex(x + outer[0], y + outer[1], 0);
//            render.addVertex(x, y, 0);
//            tess.draw();
//        }
//
//        GlStateManager.color(0.0F, 0.0F, 0.0F);
//        GlStateManager.disableBlend();
//        GlStateManager.alphaFunc(516, 0.1F);
//        GlStateManager.disableAlpha();
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//    }
//    public static void drawCircle(float x, float y, float radius, int startPi, int endPi, int c) {
//        float f = ((c >> 24) & 0xff) / 255F;
//        float f1 = ((c >> 16) & 0xff) / 255F;
//        float f2 = ((c >> 8) & 0xff) / 255F;
//        float f3 = (c & 0xff) / 255F;
//        GL11.glColor4f(f1, f2, f3, f);
//        GlStateManager.enableAlpha();
//        GlStateManager.enableBlend();
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//        GlStateManager.alphaFunc(516, 0.001F);
//        Tessellator tess = Tessellator.getInstance();
//        WorldRenderer render = tess.getWorldRenderer();
//
//        for (double i = startPi; i < endPi; i++) {
//            double cs = i * Math.PI / 180;
//            double ps = (i - 1) * Math.PI / 180;
//            double[] outer = new double[]
//                    {
//                            Math.cos(cs) * radius, -Math.sin(cs) * radius,
//                            Math.cos(ps) * radius, -Math.sin(ps) * radius
//                    };
//            render.startDrawing(6);
//            render.addVertex(x + outer[2], y + outer[3], 0);
//            render.addVertex(x + outer[0], y + outer[1], 0);
//            render.addVertex(x, y, 0);
//            tess.draw();
//        }
//
//        GlStateManager.color(0.0F, 0.0F, 0.0F);
//        GlStateManager.disableBlend();
//        GlStateManager.alphaFunc(516, 0.1F);
//        GlStateManager.disableAlpha();
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//    }

//    public static void drawRoundedRectBottem(float x, float y, float x2, float y2, float round, int color) {
//        x = (float) ((double) x + ((double) (round / 2.0f) + 0));
//        y = (float) ((double) y + ((double) (round / 2.0f) + 0));
//        x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0));
//        y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0));
//        Gui.drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
//        Gui.drawCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, 270, 360, color);
//        Gui.drawRect(x - round / 2.0f, y, x2 + round / 2.0f, y2 - round / 2.0f + 0f, color);
//        Gui.drawRect(x + round / 2.0f, y2 - round / 2.0f + 0f, x2 - round / 2.0f, y2 + round / 2.0f + 0f, color);
//    }
//
//    public static void drawRoundedRectBottemLeft(float x, float y, float x2, float y2, float round, int color) {
//        x = (float) ((double) x + ((double) (round / 2.0f) + 0));
//        y = (float) ((double) y + ((double) (round / 2.0f) + 0));
//        x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0));
//        y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0));
//        Gui.drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
//        Gui.drawRect(x - round / 2.0f - 0f, y, x + round / 2.0f - 0f, y2 - round / 2.0f, color);
//        Gui.drawRect(x + round / 2.0f, y, x2 + round / 2.0f, y2 + round / 2.0f + 0f, color);
//    }


    public static void circle(float x, float y, float radius, Color fill) {
        arc(x, y, 0.0F, 360.0F, radius, fill);
    }

    public static void arc(float x, float y, float start, float end, float radius, int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }


    private static void arcEllipse(float x, float y, float start, float end, float radius, float radius2, int color) {
        arcEllipse(x,y,start,end,radius,radius2,color);

    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
        float temp = 0.0F;

        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
        float i;
        float ldx;
        float ldy;

        if ((float) color.getAlpha() > 0.5F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(GL11.GL_LINE_STRIP);

            for (i = end; i >= start; i -= 4.0F) {
                ldx = (float) Math.cos((double) i * Math.PI / 180.0D) * w * 1.001F;
                ldy = (float) Math.sin((double) i * Math.PI / 180.0D) * h * 1.001F;
                GL11.glVertex2f(x + ldx, y + ldy);
            }

            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        for (i = end; i >= start; i -= 4.0F) {
            ldx = (float) Math.cos((double) i * Math.PI / 180.0D) * w;
            ldy = (float) Math.sin((double) i * Math.PI / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }

        GL11.glEnd();
        GlStateManager.disableBlend();
    }
    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable((int) GL11.GL_DEPTH_TEST);
        GL11.glEnable((int) GL11.GL_BLEND);
        GL11.glDepthMask((boolean) false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) GL11.GL_BLEND);
        GL11.glEnable((int) GL11.GL_DEPTH_TEST);
    }
//    public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
//        drawRect(x, y, x2, y2, col2);
//        float f = (float) (col1 >> 24 & 255) / 255.0F;
//        float f1 = (float) (col1 >> 16 & 255) / 255.0F;
//        float f2 = (float) (col1 >> 8 & 255) / 255.0F;
//        float f3 = (float) (col1 & 255) / 255.0F;
//        GL11.glEnable(3042);
//        GL11.glDisable(3553);
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(2848);
//        GL11.glPushMatrix();
//        GL11.glColor4f(f1, f2, f3, f);
//        GL11.glLineWidth(l1);
//        GL11.glBegin(1);
//        GL11.glVertex2d((double) x, (double) y);
//        GL11.glVertex2d((double) x, (double) y2);
//        GL11.glVertex2d((double) x2, (double) y2);
//        GL11.glVertex2d((double) x2, (double) y);
//        GL11.glVertex2d((double) x, (double) y);
//        GL11.glVertex2d((double) x2, (double) y);
//        GL11.glVertex2d((double) x, (double) y2);
//        GL11.glVertex2d((double) x2, (double) y2);
//        GL11.glEnd();
//        GL11.glPopMatrix();
//        GL11.glEnable(3553);
//        GL11.glDisable(3042);
//        GL11.glDisable(2848);
//    }
//


    public static void arc(float x, float y, float start, float end, float radius, Color color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

//    public static void drawRoundRect(double d, double e, double g, double h, int color, int i)
//    {
//        drawRect(d+1, e, g-1, h, color);
//        drawRect(d, e+1, d+1, h-1, color);
//        drawRect(d+1, e+1, d+0.5, e+0.5, color);
//        drawRect(d+1, e+1, d+0.5, e+0.5, color);
//        drawRect(g-1, e+1, g-0.5, e+0.5, color);
//        drawRect(g-1, e+1, g, h-1, color);
//        drawRect(d+1, h-1, d+0.5, h-0.5, color);
//        drawRect(g-1, h-1, g-0.5, h-0.5, color);
//    }
//
//    public static void drawModalRectWithCustomSizedTexture(float f, double d, float u, float v, double h, double e, double i, double g)
//    {
//        double var8 = 1.0F / i;
//        double var9 = 1.0F / g;
//        Tessellator var10 = Tessellator.getInstance();
//        WorldRenderer var11 = var10.getWorldRenderer();
//        var11.startDrawingQuads();
//        var11.addVertexWithUV((double)f, (double)(d + e), 0.0D, (double)(u * var8), (double)((v + (float)e) * var9));
//        var11.addVertexWithUV((double)(f + h), (double)(d + e), 0.0D, (double)((u + (float)h) * var8), (double)((v + (float)e) * var9));
//        var11.addVertexWithUV((double)(f + h), (double)d, 0.0D, (double)((u + (float)h) * var8), (double)(v * var9));
//        var11.addVertexWithUV((double)f, (double)d, 0.0D, (double)(u * var8), (double)(v * var9));
//        var10.draw();
//
//    }
    public static void drawRainbowRectHorizontal(int x, int y, int x1, int y1, int segments, float alpha) {
        if (segments < 1) {
            segments = 1;
        }
        if (segments > x1 - x) {
            segments = x1 - x;
        }
        int segmentLength = (x1 - x) / segments;
        long time = System.nanoTime();
        for (int i = 0; i < segments; ++i) {
            Gui.drawRect((float)(x + segmentLength * i), (float)y, (float)x1, (float)y1, Gui.rainbow2(time, (float)i * 0.15f, alpha));
        }
    }

    public static Color rainbow2(long time, float count, float fade) {
        float hue = ((float)time + (1.0f + count) * 2.0E8f) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 0.55f, 1.0f)), 16);
        Color c = new Color((int)color);
        return new Color((float)c.getRed() / 255.0f * fade, (float)c.getGreen() / 255.0f * fade, (float)c.getBlue() / 255.0f * fade, (float)c.getAlpha() / 255.0f);
    }

    public static void drawRect(float left, float top, float right, float bottom, Color color) {
        float tessellator;
        if (left < right) {
            tessellator = left;
            left = right;
            right = tessellator;
        }
        if (top < bottom) {
            tessellator = top;
            top = bottom;
            bottom = tessellator;
        }
        Tessellator tessellator1 = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator1.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}
