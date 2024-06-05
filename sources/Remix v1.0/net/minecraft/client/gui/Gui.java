package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class Gui
{
    public static final ResourceLocation optionsBackground;
    public static final ResourceLocation statIcons;
    public static final ResourceLocation icons;
    public static float zLevel;
    
    public static void drawFilledCircle(final int xx, final int yy, final float radius, final Color col) {
        final byte sections = 50;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.sin(i * dAngle));
            final float y = (float)(radius * Math.cos(i * dAngle));
            GL11.glColor4f(col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, col.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawRect(int left, int top, int right, int bottom, final int color) {
        if (left < right) {
            final int var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            final int var5 = top;
            top = bottom;
            bottom = var5;
        }
        final float var6 = (color >> 24 & 0xFF) / 255.0f;
        final float var7 = (color >> 16 & 0xFF) / 255.0f;
        final float var8 = (color >> 8 & 0xFF) / 255.0f;
        final float var9 = (color & 0xFF) / 255.0f;
        final Tessellator var10 = Tessellator.getInstance();
        final WorldRenderer var11 = var10.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var7, var8, var9, var6);
        var11.startDrawingQuads();
        var11.addVertex(left, bottom, 0.0);
        var11.addVertex(right, bottom, 0.0);
        var11.addVertex(right, top, 0.0);
        var11.addVertex(left, top, 0.0);
        var10.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(int left, int top, int right, int bottom, final Color color) {
        if (left < right) {
            final int var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            final int var5 = top;
            top = bottom;
            bottom = var5;
        }
        final Tessellator var6 = Tessellator.getInstance();
        final WorldRenderer var7 = var6.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
        var7.startDrawingQuads();
        var7.addVertex(left, bottom, 0.0);
        var7.addVertex(right, bottom, 0.0);
        var7.addVertex(right, top, 0.0);
        var7.addVertex(left, top, 0.0);
        var6.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
    
    public static void drawCenteredString(final FontRenderer fontRendererIn, final String text, final int x, final int y, final int color) {
        fontRendererIn.func_175063_a(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }
    
    public static void drawModalRectWithCustomSizedTexture(final int x, final int y, final float u, final float v, final int width, final int height, final float textureWidth, final float textureHeight) {
        final float var8 = 1.0f / textureWidth;
        final float var9 = 1.0f / textureHeight;
        final Tessellator var10 = Tessellator.getInstance();
        final WorldRenderer var11 = var10.getWorldRenderer();
        var11.startDrawingQuads();
        var11.addVertexWithUV(x, y + height, 0.0, u * var8, (v + height) * var9);
        var11.addVertexWithUV(x + width, y + height, 0.0, (u + width) * var8, (v + height) * var9);
        var11.addVertexWithUV(x + width, y, 0.0, (u + width) * var8, v * var9);
        var11.addVertexWithUV(x, y, 0.0, u * var8, v * var9);
        var10.draw();
    }
    
    public static void drawImage(final ResourceLocation image, final int x, final int y, final int width, final int height) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }
    
    public static void drawScaledCustomSizeModalRect(final double x2, final double y2, final float u2, final float v2, final double uWidth, final double vHeight, final double width, final double height, final float tileWidth, final float tileHeight) {
        final float var10 = 1.0f / tileWidth;
        final float var11 = 1.0f / tileHeight;
        final Tessellator var12 = Tessellator.getInstance();
        final WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV(x2, y2 + height, 0.0, u2 * var10, (v2 + (float)vHeight) * var11);
        var13.addVertexWithUV(x2 + width, y2 + height, 0.0, (u2 + (float)uWidth) * var10, (v2 + (float)vHeight) * var11);
        var13.addVertexWithUV(x2 + width, y2, 0.0, (u2 + (float)uWidth) * var10, v2 * var11);
        var13.addVertexWithUV(x2, y2, 0.0, u2 * var10, v2 * var11);
        var12.draw();
    }
    
    public static void drawFilledCircle(final float xx, final float yy, final float radius, final Color col) {
        final int sections = 50;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.sin(i * dAngle));
            final float y = (float)(radius * Math.cos(i * dAngle));
            GL11.glColor4f(col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, col.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final int xx, final int yy, final float radius, final int col) {
        final float f = (col >> 24 & 0xFF) / 255.0f;
        final float f2 = (col >> 16 & 0xFF) / 255.0f;
        final float f3 = (col >> 8 & 0xFF) / 255.0f;
        final float f4 = (col & 0xFF) / 255.0f;
        final int sections = 50;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.sin(i * dAngle));
            final float y = (float)(radius * Math.cos(i * dAngle));
            GL11.glColor4f(f2, f3, f4, f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawCircle(final int x, final int y, final float radius, final int startPi, final int endPi, final int c) {
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.001f);
        final Tessellator tess = Tessellator.getInstance();
        final WorldRenderer render = tess.getWorldRenderer();
        for (double i = startPi; i < endPi; ++i) {
            final double cs = i * 3.141592653589793 / 180.0;
            final double ps = (i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            render.startDrawing(6);
            render.addVertex(x + outer[2], y + outer[3], 0.0);
            render.addVertex(x + outer[0], y + outer[1], 0.0);
            render.addVertex(x, y, 0.0);
            tess.draw();
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glEnable(3553);
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int startPi, final int endPi, final int c) {
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.001f);
        final Tessellator tess = Tessellator.getInstance();
        final WorldRenderer render = tess.getWorldRenderer();
        for (double i = startPi; i < endPi; ++i) {
            final double cs = i * 3.141592653589793 / 180.0;
            final double ps = (i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            render.startDrawing(6);
            render.addVertex(x + outer[2], y + outer[3], 0.0);
            render.addVertex(x + outer[0], y + outer[1], 0.0);
            render.addVertex(x, y, 0.0);
            tess.draw();
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glEnable(3553);
    }
    
    public static void drawFilledCircle(final float xx, final float yy, final float radius, final int col) {
        final float f = (col >> 24 & 0xFF) / 255.0f;
        final float f2 = (col >> 16 & 0xFF) / 255.0f;
        final float f3 = (col >> 8 & 0xFF) / 255.0f;
        final float f4 = (col & 0xFF) / 255.0f;
        final int sections = 50;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.sin(i * dAngle));
            final float y = (float)(radius * Math.cos(i * dAngle));
            GL11.glColor4f(f2, f3, f4, f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final int xx, final int yy, final float radius, final int col, final int xLeft, final int yAbove, final int xRight, final int yUnder) {
        final float f = (col >> 24 & 0xFF) / 255.0f;
        final float f2 = (col >> 16 & 0xFF) / 255.0f;
        final float f3 = (col >> 8 & 0xFF) / 255.0f;
        final float f4 = (col & 0xFF) / 255.0f;
        final int sections = 50;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.sin(i * dAngle));
            final float y = (float)(radius * Math.cos(i * dAngle));
            float xEnd = xx + x;
            float yEnd = yy + y;
            if (xEnd < xLeft) {
                xEnd = (float)xLeft;
            }
            if (xEnd > xRight) {
                xEnd = (float)xRight;
            }
            if (yEnd < yAbove) {
                yEnd = (float)yAbove;
            }
            if (yEnd > yUnder) {
                yEnd = (float)yUnder;
            }
            GL11.glColor4f(f2, f3, f4, f);
            GL11.glVertex2f(xEnd, yEnd);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawRainbowRectHorizontal(final int x, final int y, final int x1, final int y1, int segments, final float alpha) {
        if (segments < 1) {
            segments = 1;
        }
        if (segments > x1 - x) {
            segments = x1 - x;
        }
        final int segmentLength = (x1 - x) / segments;
        final long time = System.nanoTime();
        for (int i = 0; i < segments; ++i) {
            drawRect(x + segmentLength * i, y, x + (segmentLength + 1) * i, y1, rainbow(time, (float)i, alpha));
        }
    }
    
    public static void drawRainbowRectVertical(final int x, final int y, final int x1, final int y1, int segments, final float alpha) {
        if (segments < 1) {
            segments = 1;
        }
        if (segments > y1 - y) {
            segments = y1 - y;
        }
        final int segmentLength = (y1 - y) / segments;
        final long time = System.nanoTime();
        for (int i = 0; i < segments; ++i) {
            Minecraft.getMinecraft().ingameGUI.drawGradientRect(x, y + segmentLength * i - 1, x1, y + (segmentLength + 1) * i, rainbow(time, i * 0.1f, alpha).getRGB(), rainbow(time, (i + 0.1f) * 0.1f, alpha).getRGB());
        }
    }
    
    public static Color rainbow(final long time, final float count, final float fade) {
        final float hue = (time + (1.0f + count) * 2.0E8f) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
    
    public static void drawRect(final float x1, final float y1, final float x2, final float y2, final int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x2, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void color(final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
    }
    
    public static void drawCircle(final float xx, final float yy, final float radius, final Color col) {
        final byte sections = 70;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = 0; i < sections; ++i) {
            final float x = (float)(radius * Math.cos(i * dAngle));
            final float y = (float)(radius * Math.sin(i * dAngle));
            GL11.glColor4f(col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, col.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static float[] convertRGB(final int rgb) {
        final float a = (rgb >> 24 & 0xFF) / 255.0f;
        final float r = (rgb >> 16 & 0xFF) / 255.0f;
        final float g = (rgb >> 8 & 0xFF) / 255.0f;
        final float b = (rgb & 0xFF) / 255.0f;
        return new float[] { r, g, b, a };
    }
    
    public static void drawOutlinedDiagonallyRect(final float x, final float y, final float x2, final float y2, final float l1, final int outer, final int inner, final int distance) {
        drawDiagonallyRect(x, y, x2, y2, inner, distance);
        final float[] convCol = convertRGB(outer);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(convCol[0], convCol[1], convCol[2], convCol[3]);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + distance), (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)(x2 - distance), (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)(x + distance), (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)(x2 - distance), (double)y2);
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawDiagonallyRect(final float x, final float y, final float xw, final float yh, final int col, final int distance) {
        final float[] convCol = convertRGB(col);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(convCol[0], convCol[1], convCol[2], convCol[3]);
        GL11.glBegin(7);
        GL11.glVertex2d((double)xw, (double)y);
        GL11.glVertex2d((double)(x + distance), (double)y);
        GL11.glVertex2d((double)x, (double)yh);
        GL11.glVertex2d((double)(xw - distance), (double)yh);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
        GlStateManager.disableBlend();
        x += (float)(round / 2.0f + 0.0);
        y += (float)(round / 2.0f + 0.0);
        x2 -= (float)(round / 2.0f + 0.0);
        y2 -= (float)(round / 2.0f + 0.0);
        drawCircle(x2 - round / 2.0f, y + round / 2.0f, round, 0, 90, color);
        drawCircle(x + round / 2.0f, y + round / 2.0f, round, 90, 180, color);
        drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
        drawCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, 270, 360, color);
        drawRect(x - round / 2.0f, y + round / 2.0f, x2, y2 - round / 2.0f, color);
        drawRect(x2 + round / 2.0f - round / 2.0f, y + round / 2.0f, x2 + round / 2.0f, y2 - round / 2.0f, color);
        drawRect(x + round / 2.0f, y - round / 2.0f, x2 - round / 2.0f, y + round / 2.0f, color);
        drawRect(x + round / 2.0f, y2 - round / 2.0f + 0.0f, x2 - round / 2.0f, y2 + round / 2.0f + 0.0f, color);
        GlStateManager.disableBlend();
    }
    
    public static void drawRoundedRectTop(float x, float y, float x2, float y2, final float round, final int color) {
        GlStateManager.disableBlend();
        x += (float)(round / 2.0f + 0.0);
        y += (float)(round / 2.0f + 0.0);
        x2 -= (float)(round / 2.0f + 0.0);
        y2 -= (float)(round / 2.0f + 0.0);
        drawCircle(x2 - round / 2.0f, y + round / 2.0f, round, 0, 90, color);
        drawCircle(x + round / 2.0f, y + round / 2.0f, round, 90, 180, color);
        drawRect(x - round / 2.0f, y + round / 2.0f, x2, y2 + round / 2.0f, color);
        drawRect(x2 + round / 2.0f - round / 2.0f, y + round / 2.0f, x2 + round / 2.0f, y2 + round / 2.0f, color);
        drawRect(x + round / 2.0f, y - round / 2.0f, x2 - round / 2.0f, y + round / 2.0f, color);
        GlStateManager.disableBlend();
    }
    
    public static void drawRoundedRectBottem(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float)(round / 2.0f + 0.0);
        y += (float)(round / 2.0f + 0.0);
        x2 -= (float)(round / 2.0f + 0.0);
        y2 -= (float)(round / 2.0f + 0.0);
        drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
        drawCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, 270, 360, color);
        drawRect(x - round / 2.0f, y, x2 + round / 2.0f, y2 - round / 2.0f + 0.0f, color);
        drawRect(x + round / 2.0f, y2 - round / 2.0f + 0.0f, x2 - round / 2.0f, y2 + round / 2.0f + 0.0f, color);
    }
    
    public static void drawRoundedRectBottemLeft(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float)(round / 2.0f + 0.0);
        y += (float)(round / 2.0f + 0.0);
        x2 -= (float)(round / 2.0f + 0.0);
        y2 -= (float)(round / 2.0f + 0.0);
        drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
        drawRect(x - round / 2.0f - 0.0f, y, x + round / 2.0f - 0.0f, y2 - round / 2.0f, color);
        drawRect(x + round / 2.0f, y, x2 + round / 2.0f, y2 + round / 2.0f + 0.0f, color);
    }
    
    public static void drawRoundedRectBottemRight(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float)(round / 2.0f + 0.0);
        y += (float)(round / 2.0f + 0.0);
        x2 -= (float)(round / 2.0f + 0.0);
        y2 -= (float)(round / 2.0f + 0.0);
        drawCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, 270, 360, color);
        drawRect(x - round / 2.0f, y, x2 + round / 2.0f, y2 - round / 2.0f + 0.0f, color);
        drawRect(x - round / 2.0f, y2 - round / 2.0f + 0.0f, x2 - round / 2.0f, y2 + round / 2.0f + 0.0f, color);
    }
    
    public static void circle(final float x, final float y, final float radius, final int fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }
    
    public static void circle(final float x, final float y, final float radius, final Color fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }
    
    public static void arc(final float x, final float y, final float start, final float end, final float radius, final int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }
    
    public static void arc(final float x, final float y, final float start, final float end, final float radius, final Color color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }
    
    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h, final int color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        final Tessellator var15 = Tessellator.getInstance();
        final WorldRenderer var16 = var15.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float)Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float)Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float)Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float)Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
    }
    
    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h, final Color color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        if (color.getAlpha() > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float)Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float)Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float)Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float)Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int border, final int bottom, final int top) {
        enableGL2D();
        drawGradientRect(x, y, x1, y1, top, bottom);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawGradientRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public void drawHorizontalLine(int startX, int endX, final int y, final int color) {
        if (endX < startX) {
            final int var5 = startX;
            startX = endX;
            endX = var5;
        }
        drawRect(startX, y, endX + 1, y + 1, color);
    }
    
    protected void drawVerticalLine(final int x, int startY, int endY, final int color) {
        if (endY < startY) {
            final int var5 = startY;
            startY = endY;
            endY = var5;
        }
        drawRect(x, startY + 1, x + 1, endY, color);
    }
    
    public void drawGradientRect(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        final float var7 = (startColor >> 24 & 0xFF) / 255.0f;
        final float var8 = (startColor >> 16 & 0xFF) / 255.0f;
        final float var9 = (startColor >> 8 & 0xFF) / 255.0f;
        final float var10 = (startColor & 0xFF) / 255.0f;
        final float var11 = (endColor >> 24 & 0xFF) / 255.0f;
        final float var12 = (endColor >> 16 & 0xFF) / 255.0f;
        final float var13 = (endColor >> 8 & 0xFF) / 255.0f;
        final float var14 = (endColor & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator var15 = Tessellator.getInstance();
        final WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex(right, top, Gui.zLevel);
        var16.addVertex(left, top, Gui.zLevel);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, Gui.zLevel);
        var16.addVertex(right, bottom, Gui.zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }
    
    public void drawString(final FontRenderer fontRendererIn, final String text, final int x, final int y, final int color) {
        fontRendererIn.func_175063_a(text, (float)x, (float)y, color);
    }
    
    public void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(x + 0, y + height, Gui.zLevel, (textureX + 0) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + height, Gui.zLevel, (textureX + width) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + 0, Gui.zLevel, (textureX + width) * var7, (textureY + 0) * var8);
        var10.addVertexWithUV(x + 0, y + 0, Gui.zLevel, (textureX + 0) * var7, (textureY + 0) * var8);
        var9.draw();
    }
    
    public void renderHexagonSide(final int p_renderHexagonSide_1_, final float p_renderHexagonSide_2_, final float p_renderHexagonSide_3_, final int p_renderHexagonSide_4_, final int p_renderHexagonSide_5_) {
        final int i = (p_renderHexagonSide_1_ == 0) ? 5 : (p_renderHexagonSide_1_ - 1);
        final int j = p_renderHexagonSide_4_ + 32;
        final int k = p_renderHexagonSide_5_ + 32;
        final double d0 = 10.0;
        final double d2 = 32.0 + d0;
        double d3 = Math.sin(p_renderHexagonSide_1_ / 6.0 * 2.0 * 3.141592653589793) * d2 + j;
        double d4 = Math.cos(p_renderHexagonSide_1_ / 6.0 * 2.0 * 3.141592653589793) * d2 + k;
        double d5 = Math.sin(i / 6.0 * 2.0 * 3.141592653589793) * d2 + j;
        double d6 = Math.cos(i / 6.0 * 2.0 * 3.141592653589793) * d2 + k;
        final double d7 = (d6 - d4) / (d5 - d3);
        final double d8 = (d5 - d3) * p_renderHexagonSide_3_;
        final double d9 = (d5 - d3) * p_renderHexagonSide_2_;
        if (p_renderHexagonSide_1_ != 2 && p_renderHexagonSide_1_ != 5) {
            d5 = d3 + d9;
            d6 = d7 * (d3 + d9 - d3) + d4;
            d3 += d8;
            d4 += d7 * (d3 + d8 - d3);
        }
        else {
            d6 = d4 + (d6 - d4) * p_renderHexagonSide_3_;
            d4 += (d6 - d4) * p_renderHexagonSide_2_;
        }
        double d10 = Math.sin(p_renderHexagonSide_1_ / 6.0 * 2.0 * 3.141592653589793) * (d2 - d0) + j;
        double d11 = Math.cos(p_renderHexagonSide_1_ / 6.0 * 2.0 * 3.141592653589793) * (d2 - d0) + k;
        double d12 = Math.sin(i / 6.0 * 2.0 * 3.141592653589793) * (d2 - d0) + j;
        double d13 = Math.cos(i / 6.0 * 2.0 * 3.141592653589793) * (d2 - d0) + k;
        final double d14 = (d13 - d11) / (d12 - d10);
        final double d15 = (d12 - d10) * p_renderHexagonSide_3_;
        final double d16 = (d12 - d10) * p_renderHexagonSide_2_;
        if (p_renderHexagonSide_1_ != 2 && p_renderHexagonSide_1_ != 5) {
            d12 = d10 + d16;
            d13 = d14 * (d10 + d16 - d10) + d11;
            d10 += d15;
            d11 += d14 * (d10 + d15 - d10);
        }
        else {
            d13 = d11 + (d13 - d11) * p_renderHexagonSide_3_;
            d11 += (d13 - d11) * p_renderHexagonSide_2_;
        }
        GL11.glBegin(7);
        GlStateManager.color(1.0f, 0.8f, 0.32941177f, 1.0f);
        if (p_renderHexagonSide_1_ != 2 && p_renderHexagonSide_1_ != 5) {
            GL11.glVertex2d(d3, d4);
            GL11.glVertex2d(d5, d6);
            GL11.glVertex2d(d12, d13);
            GL11.glVertex2d(d10, d11);
        }
        else {
            GL11.glVertex2d(d3, d4);
            GL11.glVertex2d(d10, d11);
            GL11.glVertex2d(d12, d13);
            GL11.glVertex2d(d5, d6);
        }
        GL11.glEnd();
    }
    
    public void func_175174_a(final float p_175174_1_, final float p_175174_2_, final int p_175174_3_, final int p_175174_4_, final int p_175174_5_, final int p_175174_6_) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(p_175174_1_ + 0.0f, p_175174_2_ + p_175174_6_, Gui.zLevel, (p_175174_3_ + 0) * var7, (p_175174_4_ + p_175174_6_) * var8);
        var10.addVertexWithUV(p_175174_1_ + p_175174_5_, p_175174_2_ + p_175174_6_, Gui.zLevel, (p_175174_3_ + p_175174_5_) * var7, (p_175174_4_ + p_175174_6_) * var8);
        var10.addVertexWithUV(p_175174_1_ + p_175174_5_, p_175174_2_ + 0.0f, Gui.zLevel, (p_175174_3_ + p_175174_5_) * var7, (p_175174_4_ + 0) * var8);
        var10.addVertexWithUV(p_175174_1_ + 0.0f, p_175174_2_ + 0.0f, Gui.zLevel, (p_175174_3_ + 0) * var7, (p_175174_4_ + 0) * var8);
        var9.draw();
    }
    
    public void func_175175_a(final int p_175175_1_, final int p_175175_2_, final TextureAtlasSprite p_175175_3_, final int p_175175_4_, final int p_175175_5_) {
        final Tessellator var6 = Tessellator.getInstance();
        final WorldRenderer var7 = var6.getWorldRenderer();
        var7.startDrawingQuads();
        var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + p_175175_5_, Gui.zLevel, p_175175_3_.getMinU(), p_175175_3_.getMaxV());
        var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + p_175175_5_, Gui.zLevel, p_175175_3_.getMaxU(), p_175175_3_.getMaxV());
        var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + 0, Gui.zLevel, p_175175_3_.getMaxU(), p_175175_3_.getMinV());
        var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + 0, Gui.zLevel, p_175175_3_.getMinU(), p_175175_3_.getMinV());
        var6.draw();
    }
    
    static {
        optionsBackground = new ResourceLocation("textures/gui/options_background.png");
        statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
        icons = new ResourceLocation("textures/gui/icons.png");
    }
}
