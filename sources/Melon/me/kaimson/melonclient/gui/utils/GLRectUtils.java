package me.kaimson.melonclient.gui.utils;

import org.lwjgl.opengl.*;
import me.kaimson.melonclient.utils.*;

public class GLRectUtils extends FontUtils
{
    public static void drawRect(float left, float top, float right, float bottom, final int color) {
        if (left < right) {
            final float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final float j = top;
            top = bottom;
            bottom = j;
        }
        final bfx tessellator = bfx.a();
        final bfd worldrenderer = tessellator.c();
        bfl.l();
        bfl.x();
        bfl.a(770, 771, 1, 0);
        GuiUtils.setGlColor(color);
        worldrenderer.a(7, bms.e);
        worldrenderer.b((double)left, (double)bottom, 0.0).d();
        worldrenderer.b((double)right, (double)bottom, 0.0).d();
        worldrenderer.b((double)right, (double)top, 0.0).d();
        worldrenderer.b((double)left, (double)top, 0.0).d();
        tessellator.b();
        bfl.w();
        bfl.k();
    }
    
    public static void drawColoredRect(final float x, final float y, final float x2, final float y2, final int color, final int color2) {
        bfl.x();
        bfl.l();
        final bfx tessellator = bfx.a();
        final bfd worldRenderer = tessellator.c();
        worldRenderer.a(7, bms.e);
        GuiUtils.setGlColor(color);
        worldRenderer.b((double)x, (double)y2, 0.0).d();
        GuiUtils.setGlColor(color2);
        worldRenderer.b((double)x2, (double)y2, 0.0).d();
        worldRenderer.b((double)x2, (double)y, 0.0).d();
        GuiUtils.setGlColor(color);
        worldRenderer.b((double)x, (double)y, 0.0).d();
        tessellator.b();
        bfl.k();
        bfl.w();
    }
    
    public static void drawGradientRect(final int left, final int top, final int right, final int bottom, final int coltl, final int coltr, final int colbl, final int colbr, final int zLevel) {
        drawGradientRect((float)left, (float)top, (float)right, (float)bottom, coltl, coltr, colbl, colbr, zLevel);
    }
    
    public static void drawGradientRect(final float left, final float top, final float right, final float bottom, final int coltl, final int coltr, final int colbl, final int colbr, final int zLevel) {
        bfl.x();
        bfl.l();
        bfl.c();
        bfl.a(770, 771, 1, 0);
        bfl.j(7425);
        final bfx tessellator = bfx.a();
        final bfd buffer = tessellator.c();
        buffer.a(7, bms.f);
        buffer.b((double)right, (double)top, (double)zLevel).b((coltr & 0xFF0000) >> 16, (coltr & 0xFF00) >> 8, coltr & 0xFF, (coltr & 0xFF000000) >>> 24).d();
        buffer.b((double)left, (double)top, (double)zLevel).b((coltl & 0xFF0000) >> 16, (coltl & 0xFF00) >> 8, coltl & 0xFF, (coltl & 0xFF000000) >>> 24).d();
        buffer.b((double)left, (double)bottom, (double)zLevel).b((colbl & 0xFF0000) >> 16, (colbl & 0xFF00) >> 8, colbl & 0xFF, (colbl & 0xFF000000) >>> 24).d();
        buffer.b((double)right, (double)bottom, (double)zLevel).b((colbr & 0xFF0000) >> 16, (colbr & 0xFF00) >> 8, colbr & 0xFF, (colbr & 0xFF000000) >>> 24).d();
        tessellator.b();
        bfl.j(7424);
        bfl.k();
        bfl.d();
        bfl.w();
    }
    
    public static void drawRectOutline(final float left, final float top, final float right, final float bottom, final int color) {
        final float width = 0.55f;
        drawRect(left - width, top - width, right + width, top, color);
        drawRect(right, top, right + width, bottom, color);
        drawRect(left - width, bottom, right + width, bottom + width, color);
        drawRect(left - width, top, left, bottom, color);
    }
    
    public static void drawRoundedRect(final float paramInt1, final float paramInt2, final float paramInt3, final float paramInt4, final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        bfl.c(f2, f3, f4, f1);
        drawRoundedRect(paramInt1, paramInt2, paramInt3, paramInt4, radius);
    }
    
    private static void drawRoundedRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5) {
        final int i = 18;
        final float f1 = 90.0f / i;
        bfl.E();
        bfl.x();
        bfl.l();
        bfl.p();
        bfl.g();
        bfl.b(770, 771);
        bfl.a(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glBegin(5);
        GL11.glVertex2f(paramFloat1 + paramFloat5, paramFloat2);
        GL11.glVertex2f(paramFloat1 + paramFloat5, paramFloat4);
        GL11.glVertex2f(paramFloat3 - paramFloat5, paramFloat2);
        GL11.glVertex2f(paramFloat3 - paramFloat5, paramFloat4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(paramFloat1, paramFloat2 + paramFloat5);
        GL11.glVertex2f(paramFloat1 + paramFloat5, paramFloat2 + paramFloat5);
        GL11.glVertex2f(paramFloat1, paramFloat4 - paramFloat5);
        GL11.glVertex2f(paramFloat1 + paramFloat5, paramFloat4 - paramFloat5);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(paramFloat3, paramFloat2 + paramFloat5);
        GL11.glVertex2f(paramFloat3 - paramFloat5, paramFloat2 + paramFloat5);
        GL11.glVertex2f(paramFloat3, paramFloat4 - paramFloat5);
        GL11.glVertex2f(paramFloat3 - paramFloat5, paramFloat4 - paramFloat5);
        GL11.glEnd();
        GL11.glBegin(6);
        float f2 = paramFloat3 - paramFloat5;
        float f3 = paramFloat2 + paramFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float)(f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 - paramFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat2 + paramFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float)(f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 - paramFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float)(f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 + paramFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = paramFloat3 - paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float)(f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 + paramFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        bfl.o();
        bfl.k();
        bfl.h();
        bfl.w();
        bfl.F();
    }
    
    public static void drawRoundedOutline(final int x, final int y, final int x2, final int y2, final float radius, final float width, final int color) {
        GuiUtils.setGlColor(color);
        drawRoundedOutline((float)x, (float)y, (float)x2, (float)y2, radius, width);
    }
    
    public static void drawRoundedOutline(final float x, final float y, final float x2, final float y2, final float radius, final float width, final int color) {
        GuiUtils.setGlColor(color);
        drawRoundedOutline(x, y, x2, y2, radius, width);
    }
    
    private static void drawRoundedOutline(final float x, final float y, final float x2, final float y2, final float radius, final float width) {
        final int i = 18;
        final int j = 90 / i;
        bfl.x();
        bfl.l();
        bfl.p();
        bfl.g();
        bfl.b(770, 771);
        bfl.a(770, 771, 1, 0);
        GL11.glEnable(2848);
        if (width != 1.0f) {
            GL11.glLineWidth(width);
        }
        GL11.glBegin(3);
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x2 - radius, y);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y + radius);
        GL11.glVertex2f(x2, y2 - radius);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2 - radius, y2 - 0.1f);
        GL11.glVertex2f(x + radius, y2 - 0.1f);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x + 0.1f, y2 - radius);
        GL11.glVertex2f(x + 0.1f, y + radius);
        GL11.glEnd();
        float f1 = x2 - radius;
        float f2 = y + radius;
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = 90 - k * j;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        f1 = x2 - radius;
        f2 = y2 - radius;
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 270;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y2 - radius;
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 90;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y + radius;
        for (int k = 0; k <= i; ++k) {
            final int m = 270 - k * j;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (width != 1.0f) {
            GL11.glLineWidth(1.0f);
        }
        bfl.o();
        bfl.k();
        bfl.h();
        bfl.w();
    }
    
    public static void drawRoundedOutlineGradient(final float x, final float y, final float x2, final float y2, final float radius, final float width, final int color, final int color2) {
        final int i = 18;
        final int j = 90 / i;
        bfl.x();
        bfl.l();
        bfl.p();
        bfl.g();
        bfl.b(770, 771);
        bfl.a(770, 771, 1, 0);
        if (width != 1.0f) {
            GL11.glLineWidth(width);
        }
        GuiUtils.setGlColor(color);
        GL11.glShadeModel(7425);
        GL11.glBegin(3);
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x2 - radius, y);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y + radius);
        GuiUtils.setGlColor(color2);
        GL11.glVertex2f(x2, y2 - radius);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2 - radius, y2 - 0.1f);
        GL11.glVertex2f(x + radius, y2 - 0.1f);
        GL11.glEnd();
        GL11.glBegin(3);
        GuiUtils.setGlColor(color2);
        GL11.glVertex2f(x + 0.1f, y2 - radius);
        GuiUtils.setGlColor(color);
        GL11.glVertex2f(x + 0.1f, y + radius);
        GL11.glEnd();
        float f1 = x2 - radius;
        float f2 = y + radius;
        GuiUtils.setGlColor(color);
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = 90 - k * j;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        f1 = x2 - radius;
        f2 = y2 - radius;
        GuiUtils.setGlColor(color2);
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 270;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GuiUtils.setGlColor(color2);
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y2 - radius;
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 90;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GuiUtils.setGlColor(color);
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y + radius;
        for (int k = 0; k <= i; ++k) {
            final int m = 270 - k * j;
            GL11.glVertex2f((float)(f1 + radius * MathUtil.getRightAngle(m)), (float)(f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        if (width != 1.0f) {
            GL11.glLineWidth(1.0f);
        }
        bfl.o();
        bfl.k();
        bfl.h();
        bfl.w();
    }
}
