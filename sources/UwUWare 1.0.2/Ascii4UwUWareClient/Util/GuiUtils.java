package Ascii4UwUWareClient.Util;

import java.awt.Color;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils {
    public static final RenderItem RENDER_ITEM;
    private static ScaledResolution scaledResolution;

    static {
        RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().modelManager);
    }


    public static void drawBar(float Value, float ValueMaximum, float x, float y, float width, float height, int color) {
        Gui.drawRect(x, y, (x + (width * (Value / ValueMaximum))),
                y + height, color);
    }
    public static void drawRect2(double x, double y, double width, double height, int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    public static void drawOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        drawRect(drawX, drawY, drawWidth, drawY + 1.0f, color);
        drawRect(drawX, drawY + 1.0f, drawX + 1.0f, drawHeight, color);
        drawRect(drawWidth - 1.0f, drawY + 1.0f, drawWidth, drawHeight - 1.0f, color);
        drawRect(drawX + 1.0f, drawHeight - 1.0f, drawWidth, drawHeight, color);
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

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D);
        worldrenderer.pos(right, bottom, 0.0D);
        worldrenderer.pos(right, top, 0.0D);
        worldrenderer.pos(left, top, 0.0D);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSpecialGradRect(int left, int top, int right, int bottom, int color1, int color2) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(color1);
        GL11.glVertex2f(left, top);
        GL11.glVertex2f(left, bottom);
        glColor(color2);
        GL11.glVertex2f(right, bottom);
        GL11.glVertex2f(right, top);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
        float f3 = (float) (new Color(0, 0, 0, 0).getRGB() >> 24 & 255) / 255.0F;
        float f = (float) (new Color(0, 0, 0, 0).getRGB() >> 16 & 255) / 255.0F;
        float f1 = (float) (new Color(0, 0, 0, 0).getRGB() >> 8 & 255) / 255.0F;
        float f2 = (float) (new Color(0, 0, 0, 0).getRGB() & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
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

    public static void drawRect(final Rectangle rectangle, final int color) {
        drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }

    public static void drawRect(Vector2f pos, Vector2f end, int color) {
        Gui.drawRect((int) pos.getX(), (int) pos.getY(), (int) end.getX(), (int) end.getY(), color);
    }

    public static void drawBorderedRect(final float x, final float y, final float x1, final float y1, final float width,
                                        final int internalColor, final int borderColor) {
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x + width, y, x1 - width, y + width);
        drawRect(x, y, x + width, y1);
        drawRect(x1 - width, y, x1, y1);
        if (width != 1.069f) {
        }
        drawRect(x + width, y1 - width, x1 - width, y1);
        disableGL2D();
    }

    public static void drawClickguiRect(float x, float y, float x1, float y1, final int insideC, final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1, insideC);

        drawVLine(x1 - 1.0f, y, y1, insideC);
        drawHLine(x, x1 - 1.0f, y, insideC);
        drawHLine(x, x1 - 2.0f, y1 - 1.0f, insideC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);

        drawVLine(x + 1, y, y1, insideC);
        drawVLine(x1 - 2.0f, y, y1, insideC);
        drawHLine(x, x1 - 1.0f, y + 1, insideC);
        drawHLine(x, x1 - 2.0f, y1 - 2.0f, insideC);

        drawVLine(x + 2, y, y1, insideC);
        drawVLine(x1 - 3.0f, y, y1, insideC);
        drawHLine(x, x1 - 4.0f, y + 2, insideC);
        drawHLine(x, x1 - 2.0f, y1 - 3.0f, insideC);

        drawVLine(x + 3, y + 3, y1 - 3, borderC);
        drawVLine(x1 - 4.0f, y + 3, y1 - 3, borderC);
        drawHLine(x + 3, x1 - 4, y + 3, borderC);
        drawHLine(x + 3, x1 - 4, y1 - 4.0f, borderC);

        drawVLine(x + 4, y + 3, y1 - 3, borderC);
        drawVLine(x1 - 5.0f, y + 3, y1 - 3, borderC);
        drawHLine(x + 3, x1 - 4, y + 4, borderC);
        drawHLine(x + 3, x1 - 4, y1 - 5.0f, borderC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, final int insideC, final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1, borderC);
        drawVLine(x1 - 1.0f, y, y1, borderC);
        drawHLine(x, x1 - 1.0f, y, borderC);
        drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawBorderedRectNoTop(float x, float y, float x1, float y1, float bottomWidth, final int insideC,
                                             final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        bottomWidth *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1, borderC);
        drawVLine(x1 - 1.0f, y, y1, borderC);
        drawHLine(x, x + bottomWidth, y1 - 1.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawBorderedRectReliant(final float x, final float y, final float x1, final float y1,
                                               final float lineWidth, final int inside, final int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
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

    public static void drawGradientBorderedRectReliant(final float x, final float y, final float x1, final float y1,
                                                       final float lineWidth, final int border, final int bottom,
                                                       final int top) {
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

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        enableGL2D();
        drawRect1(x + 0.5f, y, x1 - 0.5f, y + 0.5f, insideC);
        drawRect1(x + 0.5f, y1 - 0.5f, x1 - 0.5f, y1, insideC);
        drawRect1(x, y + 0.5f, x1, y1 - 0.5f, insideC);
        disableGL2D();
    }

    public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
        Gui.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
        Gui.drawRect(x, y + height, x + width, y + height - lineSize, borderColor);
    }



    public static void drawRoundedRect1(double x, double y, double x1, double y1, int borderC, int insideC) {
        enableGL2D();
        drawRect1((float)x + 0.5f,(float) y, (float)(x + x1 - 0.5f), (float)(y + y1 + 0.5f), insideC);
        drawRect1((float)x + 0.5f, (float)y - 0.5f, (float)(x + x1 - 0.5f), (float)(y + y1), insideC);
        drawRect1((float)x, (float)(y + y1 + 0.5f), (float)(x + x1), (float)(y + y1), insideC);
        disableGL2D();
    }


    public static void drawSpecialRoundedRect(int glFlag, int left, int top, int right, int bottom, int color) {
		enableGL2D();
		drawRect1(left + 0.5f, top, right - 0.5f, top + 0.5f, color);
		drawRect1(left + 0.5f, bottom - 0.5f, right - 0.5f, bottom, color);
		drawRect1(left, top + 0.5f, right, bottom - 0.5f, color);
		disableGL2D();
        float f3 = (float) (new Color(0, 0, 0, 0).getRGB() >> 24 & 255) / 255.0F;
        float f = (float) (new Color(0, 0, 0, 0).getRGB() >> 16 & 255) / 255.0F;
        float f1 = (float) (new Color(0, 0, 0, 0).getRGB() >> 8 & 255) / 255.0F;
        float f2 = (float) (new Color(0, 0, 0, 0).getRGB() & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//        GlStateManager.color(f, f1, f2, f3);
//        worldrenderer.begin(glFlag, DefaultVertexFormats.POSITION);
//        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
//        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
//        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
//        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
//        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect1(float x, float y, float x1, float y1, int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }

    public static void drawRect1(float x, float y, float x1, float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }

    // public static void drawRoundedRect(float x, float y, float x1, float y1,
    // final int borderC, final int insideC) {
    // enableGL2D();
    // x *= 2.0f;
    // y *= 2.0f;
    // x1 *= 2.0f;
    // y1 *= 2.0f;
    // GL11.glScalef(0.5f, 0.5f, 0.5f);
    // drawVLine(x, y + 1.0f, y1 - 2.0f, borderC);
    // drawVLine(x1 - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
    // drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
    // drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
    // drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
    // drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
    // drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
    // drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
    // drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
    // GL11.glScalef(2.0f, 2.0f, 2.0f);
    // disableGL2D();
    // }

    public static void drawGradientRect(final float x, final float y, final float x1, final float y1,
                                        final int topColor, final int bottomColor) {
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

    public static void drawGradientRect1(final float x, final float y, final float x1, final float y1,
                                        final int topColor, final int bottomColor) {
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

    public static void drawBorderedRect(final Rectangle rectangle, final float width, final int internalColor,
                                        final int borderColor) {
        final float x = rectangle.x;
        final float y = rectangle.y;
        final float x2 = rectangle.x + rectangle.width;
        final float y2 = rectangle.y + rectangle.height;
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x2 - width, y2 - width);
        glColor(borderColor);
        drawRect(x + 1.0f, y, x2 - 1.0f, y + width);
        drawRect(x, y, x + width, y2);
        drawRect(x2 - width, y, x2, y2);
        drawRect(x + 1.0f, y2 - width, x2 - 1.0f, y2);
        disableGL2D();
    }

    public static void drawGradientHRect(final float x, final float y, final float x1, final float y1,
                                         final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }

    public static void drawSpecialGradRect(int glFlag, int left, int top, int right, int bottom, int color1,
                                           int color2) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(color1);
        GL11.glVertex2f(left, top);
        GL11.glVertex2f(left, bottom);
        glColor(color2);
        GL11.glVertex2f(right, bottom);
        GL11.glVertex2f(right, top);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
        float f3 = (float) (new Color(0, 0, 0, 0).getRGB() >> 24 & 255) / 255.0F;
        float f = (float) (new Color(0, 0, 0, 0).getRGB() >> 16 & 255) / 255.0F;
        float f1 = (float) (new Color(0, 0, 0, 0).getRGB() >> 8 & 255) / 255.0F;
        float f2 = (float) (new Color(0, 0, 0, 0).getRGB() & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(glFlag, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRect(final double x, final double y, final double x2, final double y2,
                                        final int col1, final int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        glColor(col2);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawGradientBorderedRect(final double x, final double y, final double x2, final double y2,
                                                final float l1, final int col1, final int col2, final int col3) {
        enableGL2D();
        GL11.glPushMatrix();
        glColor(col1);
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
        disableGL2D();
    }

    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f);
    }

    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawStrip(final int x, final int y, final float width, final double angle, final float points,
                                 final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double) x, (double) y, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i < angle; ++i) {
                final float a = (float) (i * (angle * 3.141592653589793 / points));
                final float xc = (float) (Math.cos(a) * radius);
                final float yc = (float) (Math.sin(a) * radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i > angle; --i) {
                final float a = (float) (i * (angle * 3.141592653589793 / points));
                final float xc = (float) (Math.cos(a) * -radius);
                final float yc = (float) (Math.sin(a) * -radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float x, float y, final float x1, final int y1) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawVLine(final float x, float y, float x1, final int y1) {
        if (x1 < y) {
            final float var5 = y;
            y = x1;
            x1 = var5;
        }
        drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }

    public static void drawHLine(float x, float y, final float x1, final int y1, final int y2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }

    public static void drawRect(final float x, final float y, final float x1, final float y1, final float r,
                                final float g, final float b, final float a) {
        enableGL2D();
        GL11.glColor4f(r, g, b, a);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }

    public static void drawRect(final float x, final float y, final float x1, final float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }

    static int fade = 0;
    static boolean fadeIn = true;
    static boolean fadeOut = false;

    public static void drawAnimatedUnfilledCircle(float x, float y, float radius, float lineWidth, int color) {

        float alpha = (color >> 24 & 0xFF) / 255.0F;

        float red = (color >> 16 & 0xFF) / 255.0F;

        float green = (color >> 8 & 0xFF) / 255.0F;

        float blue = (color & 0xFF) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);

        GL11.glLineWidth(lineWidth);

        GL11.glEnable(2848);

        GL11.glBegin(2);

        fade = 360 * 3;
        // if (fade > 360) {
        // fadeIn = false;
        // fadeOut = true;
        // }
        // if (fade < 0) {
        // fadeIn = true;
        // fadeOut = false;
        // }

        // if (fadeIn) {
        // fade++;
        // }
        //
        // if (fadeOut) {
        // fade--;
        // }

        for (int i = 0; i <= fade; i++) {

            GL11.glVertex2d(x + Math.sin(i * Math.PI / 270.0D) * radius, y + Math.cos(i * Math.PI / 180.0D) * radius);

        }

        GL11.glEnd();

        GL11.glDisable(2848);

    }

    public static void drawCircle(float cx, float cy, float r, final int num_segments, final int c) {
        r *= 2.0f;
        cx *= 2.0f;
        cy *= 2.0f;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        final float theta = (float) (6.2831852 / num_segments);
        final float p = (float) Math.cos(theta);
        final float s = (float) Math.sin(theta);
        float x = r;
        float y = 0.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, y + cy);
            final float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy, double r, final int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawSmallString(final String s, final int x, final int y, final int color) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(s, x * 2, y * 2, color);
        GL11.glPopMatrix();
    }

    public static void drawLargeString(final String text, int x, final int y, final int color) {
        x *= 2;
        GL11.glPushMatrix();
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
        GL11.glPopMatrix();
    }

    public static ScaledResolution getScaledResolution() {
        return GuiUtils.scaledResolution;
    }
}
