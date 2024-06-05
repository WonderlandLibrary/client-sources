/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Vector2f
 */
package drunkclient.beta.UTILS;

import java.awt.Color;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GuiUtils {
    private static ScaledResolution scaledResolution;
    static int fade;
    static boolean fadeIn;
    static boolean fadeOut;

    public static void drawBar(float Value2, float ValueMaximum, float x, float y, float width, float height, int color) {
        Gui.drawRect(x, y, x + width * (Value2 / ValueMaximum), y + height, color);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
    }

    public static void drawOutlineRect(float drawX, float drawY, float drawWidth, float drawHeight, int color) {
        GuiUtils.drawRect(drawX, drawY, drawWidth, drawY + 1.0f, color);
        GuiUtils.drawRect(drawX, drawY + 1.0f, drawX + 1.0f, drawHeight, color);
        GuiUtils.drawRect(drawWidth - 1.0f, drawY + 1.0f, drawWidth, drawHeight - 1.0f, color);
        GuiUtils.drawRect(drawX + 1.0f, drawHeight - 1.0f, drawWidth, drawHeight, color);
    }

    public static void draw2DRect(double left, double top, double right, double bottom, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        float var11 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var6 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0);
        worldrenderer.pos(right, bottom, 0.0);
        worldrenderer.pos(right, top, 0.0);
        worldrenderer.pos(left, top, 0.0);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSpecialGradRect(int left, int top, int right, int bottom, int color1, int color2) {
        GuiUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GuiUtils.glColor(color1);
        GL11.glVertex2f((float)left, (float)top);
        GL11.glVertex2f((float)left, (float)bottom);
        GuiUtils.glColor(color2);
        GL11.glVertex2f((float)right, (float)bottom);
        GL11.glVertex2f((float)right, (float)top);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GuiUtils.disableGL2D();
        float f3 = (float)(new Color(0, 0, 0, 0).getRGB() >> 24 & 0xFF) / 255.0f;
        float f = (float)(new Color(0, 0, 0, 0).getRGB() >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(new Color(0, 0, 0, 0).getRGB() >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(new Color(0, 0, 0, 0).getRGB() & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void drawRect(Rectangle rectangle, int color) {
        GuiUtils.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        GuiUtils.enableGL2D();
        GuiUtils.glColor(color);
        GuiUtils.drawRect(x, y, x1, y1);
        GuiUtils.disableGL2D();
    }

    public static void drawRect(Vector2f pos, Vector2f end, int color) {
        Gui.drawRect((int)pos.getX(), (int)pos.getY(), (int)end.getX(), (int)end.getY(), color);
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
        GuiUtils.enableGL2D();
        GuiUtils.glColor(internalColor);
        GuiUtils.drawRect(x + width, y + width, x1 - width, y1 - width);
        GuiUtils.glColor(borderColor);
        GuiUtils.drawRect(x + width, y, x1 - width, y + width);
        GuiUtils.drawRect(x, y, x + width, y1);
        GuiUtils.drawRect(x1 - width, y, x1, y1);
        if (width != 1.069f) {
            // empty if block
        }
        GuiUtils.drawRect(x + width, y1 - width, x1 - width, y1);
        GuiUtils.disableGL2D();
    }

    public static void drawClickguiRect(float x, float y, float x1, float y1, int insideC, int borderC) {
        GuiUtils.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GuiUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, insideC);
        GuiUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, insideC);
        GuiUtils.drawHLine(x, x1 - 1.0f, y, insideC);
        GuiUtils.drawHLine(x, x1 - 2.0f, y1 - 1.0f, insideC);
        GuiUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GuiUtils.drawVLine(x + 1.0f, y, y1, insideC);
        GuiUtils.drawVLine(x1 - 2.0f, y, y1, insideC);
        GuiUtils.drawHLine(x, x1 - 1.0f, y + 1.0f, insideC);
        GuiUtils.drawHLine(x, x1 - 2.0f, y1 - 2.0f, insideC);
        GuiUtils.drawVLine(x + 2.0f, y, y1, insideC);
        GuiUtils.drawVLine(x1 - 3.0f, y, y1, insideC);
        GuiUtils.drawHLine(x, x1 - 4.0f, y + 2.0f, insideC);
        GuiUtils.drawHLine(x, x1 - 2.0f, y1 - 3.0f, insideC);
        GuiUtils.drawVLine(x + 3.0f, y + 3.0f, y1 - 3.0f, borderC);
        GuiUtils.drawVLine(x1 - 4.0f, y + 3.0f, y1 - 3.0f, borderC);
        GuiUtils.drawHLine(x + 3.0f, x1 - 4.0f, y + 3.0f, borderC);
        GuiUtils.drawHLine(x + 3.0f, x1 - 4.0f, y1 - 4.0f, borderC);
        GuiUtils.drawVLine(x + 4.0f, y + 3.0f, y1 - 3.0f, borderC);
        GuiUtils.drawVLine(x1 - 5.0f, y + 3.0f, y1 - 3.0f, borderC);
        GuiUtils.drawHLine(x + 3.0f, x1 - 4.0f, y + 4.0f, borderC);
        GuiUtils.drawHLine(x + 3.0f, x1 - 4.0f, y1 - 5.0f, borderC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GuiUtils.disableGL2D();
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
        GuiUtils.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GuiUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, borderC);
        GuiUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
        GuiUtils.drawHLine(x, x1 - 1.0f, y, borderC);
        GuiUtils.drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
        GuiUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GuiUtils.disableGL2D();
    }

    public static void drawBorderedRectNoTop(float x, float y, float x1, float y1, float bottomWidth, int insideC, int borderC) {
        GuiUtils.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GuiUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, borderC);
        GuiUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
        GuiUtils.drawHLine(x, x + (bottomWidth *= 2.0f), y1 - 1.0f, borderC);
        GuiUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GuiUtils.disableGL2D();
    }

    public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside, int border) {
        GuiUtils.enableGL2D();
        GuiUtils.drawRect(x, y, x1, y1, inside);
        GuiUtils.glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GuiUtils.disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        GuiUtils.enableGL2D();
        GuiUtils.drawGradientRect(x, y, x1, y1, top, bottom);
        GuiUtils.glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GuiUtils.disableGL2D();
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glHint((int)3154, (int)4354);
    }

    public static void post3D() {
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        GuiUtils.enableGL2D();
        GuiUtils.drawRect1(x + 0.5f, y, x1 - 0.5f, y + 0.5f, insideC);
        GuiUtils.drawRect1(x + 0.5f, y1 - 0.5f, x1 - 0.5f, y1, insideC);
        GuiUtils.drawRect1(x, y + 0.5f, x1, y1 - 0.5f, insideC);
        GuiUtils.disableGL2D();
    }

    public static void drawRoundedRect1(double x, double y, double x1, double y1, int borderC, int insideC) {
        GuiUtils.enableGL2D();
        GuiUtils.drawRect1((float)x + 0.5f, (float)y, (float)(x + x1 - 0.5), (float)(y + y1 + 0.5), insideC);
        GuiUtils.drawRect1((float)x + 0.5f, (float)y - 0.5f, (float)(x + x1 - 0.5), (float)(y + y1), insideC);
        GuiUtils.drawRect1((float)x, (float)(y + y1 + 0.5), (float)(x + x1), (float)(y + y1), insideC);
        GuiUtils.disableGL2D();
    }

    public static void drawSpecialRoundedRect(int glFlag, int left, int top, int right, int bottom, int color) {
        GuiUtils.enableGL2D();
        GuiUtils.drawRect1((float)left + 0.5f, top, (float)right - 0.5f, (float)top + 0.5f, color);
        GuiUtils.drawRect1((float)left + 0.5f, (float)bottom - 0.5f, (float)right - 0.5f, bottom, color);
        GuiUtils.drawRect1(left, (float)top + 0.5f, right, (float)bottom - 0.5f, color);
        GuiUtils.disableGL2D();
        float f3 = (float)(new Color(0, 0, 0, 0).getRGB() >> 24 & 0xFF) / 255.0f;
        float f = (float)(new Color(0, 0, 0, 0).getRGB() >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(new Color(0, 0, 0, 0).getRGB() >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(new Color(0, 0, 0, 0).getRGB() & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect1(float x, float y, float x1, float y1, int color) {
        GuiUtils.enableGL2D();
        GuiUtils.glColor(color);
        GuiUtils.drawRect(x, y, x1, y1);
        GuiUtils.disableGL2D();
    }

    public static void drawRect1(float x, float y, float x1, float y1) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
    }

    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        GuiUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GuiUtils.glColor(topColor);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GuiUtils.glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GuiUtils.disableGL2D();
    }

    public static void drawGradientRect1(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        GuiUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GuiUtils.glColor(topColor);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GuiUtils.glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GuiUtils.disableGL2D();
    }

    public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor) {
        float x = rectangle.x;
        float y = rectangle.y;
        float x2 = rectangle.x + rectangle.width;
        float y2 = rectangle.y + rectangle.height;
        GuiUtils.enableGL2D();
        GuiUtils.glColor(internalColor);
        GuiUtils.drawRect(x + width, y + width, x2 - width, y2 - width);
        GuiUtils.glColor(borderColor);
        GuiUtils.drawRect(x + 1.0f, y, x2 - 1.0f, y + width);
        GuiUtils.drawRect(x, y, x + width, y2);
        GuiUtils.drawRect(x2 - width, y, x2, y2);
        GuiUtils.drawRect(x + 1.0f, y2 - width, x2 - 1.0f, y2);
        GuiUtils.disableGL2D();
    }

    public static void drawGradientHRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        GuiUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GuiUtils.glColor(topColor);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y1);
        GuiUtils.glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GuiUtils.disableGL2D();
    }

    public static void drawSpecialGradRect(int glFlag, int left, int top, int right, int bottom, int color1, int color2) {
        GuiUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GuiUtils.glColor(color1);
        GL11.glVertex2f((float)left, (float)top);
        GL11.glVertex2f((float)left, (float)bottom);
        GuiUtils.glColor(color2);
        GL11.glVertex2f((float)right, (float)bottom);
        GL11.glVertex2f((float)right, (float)top);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GuiUtils.disableGL2D();
        float f3 = (float)(new Color(0, 0, 0, 0).getRGB() >> 24 & 0xFF) / 255.0f;
        float f = (float)(new Color(0, 0, 0, 0).getRGB() >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(new Color(0, 0, 0, 0).getRGB() >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(new Color(0, 0, 0, 0).getRGB() & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(glFlag, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GuiUtils.glColor(col1);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GuiUtils.glColor(col2);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        GuiUtils.enableGL2D();
        GL11.glPushMatrix();
        GuiUtils.glColor(col1);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GuiUtils.drawGradientRect(x, y, x2, y2, col2, col3);
        GuiUtils.disableGL2D();
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569f * (float)redRGB;
        float green = 0.003921569f * (float)greenRGB;
        float blue = 0.003921569f * (float)blueRGB;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
        float yc;
        float xc;
        float a;
        int i;
        float f1 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, (double)0.0);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f1);
        GL11.glLineWidth((float)width);
        if (angle > 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i < angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)radius);
                yc = (float)(Math.sin(a) * (double)radius);
                GL11.glVertex2f((float)xc, (float)yc);
                ++i;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i > angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)(-radius));
                yc = (float)(Math.sin(a) * (double)(-radius));
                GL11.glVertex2f((float)xc, (float)yc);
                --i;
            }
            GL11.glEnd();
        }
        GuiUtils.disableGL2D();
        GL11.glDisable((int)3479);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float x, float y, float x1, int y1) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }
        GuiUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float var5 = y;
            y = x1;
            x1 = var5;
        }
        GuiUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }

    public static void drawHLine(float x, float y, float x1, int y1, int y2) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }
        GuiUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }

    public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a) {
        GuiUtils.enableGL2D();
        GL11.glColor4f((float)r, (float)g, (float)b, (float)a);
        GuiUtils.drawRect(x, y, x1, y1);
        GuiUtils.disableGL2D();
    }

    public static void drawRect(float x, float y, float x1, float y1) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
    }

    public static void drawAnimatedUnfilledCircle(float x, float y, float radius, float lineWidth, int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glLineWidth((float)lineWidth);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)2);
        fade = 1080;
        int i = 0;
        while (true) {
            if (i > fade) {
                GL11.glEnd();
                GL11.glDisable((int)2848);
                return;
            }
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * Math.PI / 270.0) * (double)radius), (double)((double)y + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
            ++i;
        }
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        r *= 2.0f;
        cx *= 2.0f;
        cy *= 2.0f;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r;
        float y = 0.0f;
        GuiUtils.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)2);
        int ii = 0;
        while (true) {
            if (ii >= num_segments) {
                GL11.glEnd();
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                GuiUtils.disableGL2D();
                return;
            }
            GL11.glVertex2f((float)(x + cx), (float)(y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            ++ii;
        }
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(c & 0xFF) / 255.0f;
        GuiUtils.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)6);
        int i = 0;
        while (true) {
            if (i > 360) {
                GL11.glEnd();
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                GuiUtils.disableGL2D();
                return;
            }
            double x = Math.sin((double)i * Math.PI / 180.0) * r;
            double y = Math.cos((double)i * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)((double)cx + x), (double)((double)cy + y));
            ++i;
        }
    }

    public static void drawSmallString(String s, int x, int y, int color) {
        GL11.glPushMatrix();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(s, x * 2, y * 2, color);
        GL11.glPopMatrix();
    }

    public static void drawLargeString(String text, int x, int y, int color) {
        GL11.glPushMatrix();
        GL11.glScalef((float)1.5f, (float)1.5f, (float)1.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, x *= 2, y, color);
        GL11.glPopMatrix();
    }

    public static ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    static {
        fade = 0;
        fadeIn = true;
        fadeOut = false;
    }
}

