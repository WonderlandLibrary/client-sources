// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.utils;

import org.lwjgl.opengl.GL11;
import com.klintos.twelve.gui.GameGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiUtils
{
    private static FontRenderer fr;
    
    static {
        GuiUtils.fr = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public static void drawCentredStringWithShadow(final String s, int x, final int y, final int colour) {
        x -= GuiUtils.fr.getStringWidth(s) / 2;
        GuiUtils.fr.func_175063_a(s, x, y, colour);
    }
    
    public static void drawRect(final int x, final int y, final int x2, final int y2, final int color) {
        GameGui.drawRect(x, y, x2, y2, color);
    }
    
    public static void drawRect(final float paramXStart, final float paramYStart, final float paramXEnd, final float paramYEnd, final int paramColor) {
        final float alpha = (paramColor >> 24 & 0xFF) / 255.0f;
        final float red = (paramColor >> 16 & 0xFF) / 255.0f;
        final float green = (paramColor >> 8 & 0xFF) / 255.0f;
        final float blue = (paramColor & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawPoint(final int x, final int y, final int color) {
        drawRect(x, y, x + 1, y + 1, color);
    }
    
    public static void drawVerticalLine(final int x, final int y, final int height, final int color) {
        drawRect(x, y, x + 1, height, color);
    }
    
    public static void drawHorizontalLine(final int x, final int y, final int width, final int color) {
        drawRect(x, y, width, y + 1, color);
    }
    
    public static void drawBorderedRect(final int x, final int y, final int x1, final int y1, final int bord, final int color) {
        drawRect(x + 1, y + 1, x1, y1, color);
        drawVerticalLine(x, y, y1, bord);
        drawVerticalLine(x1, y, y1, bord);
        drawHorizontalLine(x + 1, y, x1, bord);
        drawHorizontalLine(x, y1, x1 + 1, bord);
    }
    
    public static void drawFineBorderedRect(int x, int y, int x1, int y1, final int bord, final int color) {
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        drawRect(x + 1, y + 1, x1, y1, color);
        drawVerticalLine(x, y, y1, bord);
        drawVerticalLine(x1, y, y1, bord);
        drawHorizontalLine(x + 1, y, x1, bord);
        drawHorizontalLine(x, y1, x1 + 1, bord);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawBorderRectNoCorners(int x, int y, int x2, int y2, final int bord, final int color) {
        x *= 2;
        y *= 2;
        x2 *= 2;
        y2 *= 2;
        GL11.glScaled(0.5, 0.5, 0.5);
        drawRect(x + 1, y + 1, x2, y2, color);
        drawVerticalLine(x, y + 1, y2, bord);
        drawVerticalLine(x2, y + 1, y2, bord);
        drawHorizontalLine(x + 1, y, x2, bord);
        drawHorizontalLine(x + 1, y2, x2, bord);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawBorderedGradient(int x, int y, int x1, int y1, final int bord, final int gradTop, final int gradBot) {
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        final float f = (gradTop >> 24 & 0xFF) / 255.0f;
        final float f2 = (gradTop >> 16 & 0xFF) / 255.0f;
        final float f3 = (gradTop >> 8 & 0xFF) / 255.0f;
        final float f4 = (gradTop & 0xFF) / 255.0f;
        final float f5 = (gradBot >> 24 & 0xFF) / 255.0f;
        final float f6 = (gradBot >> 16 & 0xFF) / 255.0f;
        final float f7 = (gradBot >> 8 & 0xFF) / 255.0f;
        final float f8 = (gradBot & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d((double)x1, (double)(y + 1));
        GL11.glVertex2d((double)(x + 1), (double)(y + 1));
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d((double)(x + 1), (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        drawVLine(x, y, y1 - 1, bord);
        drawVLine(x1 - 1, y, y1 - 1, bord);
        drawHLine(x, x1 - 1, y, bord);
        drawHLine(x, x1 - 1, y1 - 1, bord);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawHLine(float par1, float par2, final float par3, final int par4) {
        if (par2 < par1) {
            final float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        drawRect(par1, par3, par2 + 1.0f, par3 + 1.0f, par4);
    }
    
    public static void drawVLine(final float par1, float par2, float par3, final int par4) {
        if (par3 < par2) {
            final float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }
    
    public static void drawGradientBorderedRect(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2, final int col3) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3042);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(x, y, x2, y2, col2, col3);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    public static void drawGradientRect(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawSidewaysGradientRect(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawBorderedCircle(int x, int y, float radius, final int outsideC, final int insideC) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        final float scale = 0.1f;
        GL11.glScalef(scale, scale, scale);
        x *= (int)(1.0f / scale);
        y *= (int)(1.0f / scale);
        radius *= 1.0f / scale;
        drawCircle(x, y, radius, insideC);
        drawUnfilledCircle(x, y, radius, 1.0f, outsideC);
        GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawUnfilledCircle(final int x, final int y, final float radius, final float lineWidth, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
    }
    
    public static void drawCircle(final int x, final int y, final float radius, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
    }
    
    public static double getAlphaFromHex(final int color) {
        return (color >> 24 & 0xFF) / 255.0f;
    }
    
    public static double getRedFromHex(final int color) {
        return (color >> 16 & 0xFF) / 255.0f;
    }
    
    public static double getGreenFromHex(final int color) {
        return (color >> 8 & 0xFF) / 255.0f;
    }
    
    public static double getBlueFromHex(final int color) {
        return (color & 0xFF) / 255.0f;
    }
}
