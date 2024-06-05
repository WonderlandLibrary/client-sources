/*
 * Decompiled with CFR 0.136.
 */
package gq.vapu.czfclient.Util;

import gq.vapu.czfclient.Util.Math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static gq.vapu.czfclient.Util.Render.RenderUtil.R3DUtils.drawFilledBox;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {
    private static final Frustum frustum = new Frustum();
    private static final Map<Integer, Boolean> glCapMap = new HashMap<>();

    public static void enableGlCap(final int... caps) {
        for (final int cap : caps)
            setGlCap(cap, true);
    }

    public static void disableGlCap(final int cap) {
        setGlCap(cap, false);
    }

    public static void disableGlCap(final int... caps) {
        for (final int cap : caps)
            setGlCap(cap, false);
    }

    public static void setGlCap(final int cap, final boolean state) {
        glCapMap.put(cap, glGetBoolean(cap));
        setGlState(cap, state);
    }

    public static void setGlState(final int cap, final boolean state) {
        if (state)
            glEnable(cap);
        else
            glDisable(cap);
    }

    public static void resetCaps() {
        glCapMap.forEach(RenderUtils::setGlState);
        glCapMap.clear();
    }

    public static void glColor(final Color color) {
        final float red = color.getRed() / 255F;
        final float green = color.getGreen() / 255F;
        final float blue = color.getBlue() / 255F;
        final float alpha = color.getAlpha() / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(final int red, final int green, final int blue, final int alpha) {
        GlStateManager.color(red / 255F, green / 255F, blue / 255F, alpha / 255F);
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        // Lower Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();

        // Upper Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();

        // Upper Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();

        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();

        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();

        tessellator.draw();
    }

    public static void drawAxisAlignedBB(final AxisAlignedBB axisAlignedBB, final Color color, final boolean outline, final boolean box, final float outlineWidth) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glLineWidth(outlineWidth);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glColor(color);

        if (outline) {
            glLineWidth(outlineWidth);
            enableGlCap(GL_LINE_SMOOTH);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(axisAlignedBB);
        }

        if (box) {
            glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
            drawFilledBox(axisAlignedBB);
        }

        GlStateManager.resetColor();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public static double interpolate(double newPos, double oldPos) {
        return oldPos + (newPos - oldPos) * (double) Wrapper.mc.timer.renderPartialTicks;
    }

    public static int getRandomRGB(double min, double max, float alpha) {
        return new Color((float) MathUtil.randomDouble(min, max), (float) MathUtil.randomDouble(min, max), (float) MathUtil.randomDouble(min, max), alpha).getRGB();
    }

    public static int withTransparency(int rgb, float alpha) {
        float r2 = (float) (rgb >> 16 & 255) / 255.0f;
        float g2 = (float) (rgb >> 8 & 255) / 255.0f;
        float b2 = (float) (rgb >> 0 & 255) / 255.0f;
        return new Color(r2, g2, b2, alpha).getRGB();
    }

    public static void doGlScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
                && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor,
                width * scaleFactor, height * scaleFactor);
    }

    public static int getHexRGB(int hex) {
        return -16777216 | hex;
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }


    public static void drawblock(double a, double a2, double a3, int a4, int a5, float a6) {
        float a7 = (float) (a4 >> 24 & 255) / 255.0f;
        float a8 = (float) (a4 >> 16 & 255) / 255.0f;
        float a9 = (float) (a4 >> 8 & 255) / 255.0f;
        float a10 = (float) (a4 & 255) / 255.0f;
        float a11 = (float) (a5 >> 24 & 255) / 255.0f;
        float a12 = (float) (a5 >> 16 & 255) / 255.0f;
        float a13 = (float) (a5 >> 8 & 255) / 255.0f;
        float a14 = (float) (a5 & 255) / 255.0f;
        org.lwjgl.opengl.GL11.glPushMatrix();
        glEnable(3042);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        glDisable(3553);
        glEnable(2848);
        glDisable(2929);
        org.lwjgl.opengl.GL11.glDepthMask(false);
        org.lwjgl.opengl.GL11.glColor4f(a8, a9, a10, a7);
        drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0, a2 + 1.0, a3 + 1.0));
        org.lwjgl.opengl.GL11.glLineWidth(a6);
        org.lwjgl.opengl.GL11.glColor4f(a12, a13, a14, a11);
        drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0, a2 + 1.0, a3 + 1.0));
        glDisable(2848);
        glEnable(3553);
        glEnable(2929);
        org.lwjgl.opengl.GL11.glDepthMask(true);
        glDisable(3042);
        org.lwjgl.opengl.GL11.glPopMatrix();
    }


    public static void pre() {
        glDisable(2929);
        glDisable(3553);
        glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void post() {
        glDisable(3042);
        glEnable(3553);
        glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float) (round / 2.0f + 0.5);
        y += (float) (round / 2.0f + 0.5);
        x2 -= (float) (round / 2.0f + 0.5);
        y2 -= (float) (round / 2.0f + 0.5);
        Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect((int) (x - round / 2.0f - 0.5f), (int) (y + round / 2.0f), (int) x2, (int) (y2 - round / 2.0f), color);
        Gui.drawRect((int) x, (int) (y + round / 2.0f), (int) (x2 + round / 2.0f + 0.5f), (int) (y2 - round / 2.0f), color);
        Gui.drawRect((int) (x + round / 2.0f), (int) (y - round / 2.0f - 0.5f), (int) (x2 - round / 2.0f), (int) (y2 - round / 2.0f), color);
        Gui.drawRect((int) (x + round / 2.0f), (int) y, (int) (x2 - round / 2.0f), (int) (y2 + round / 2.0f + 0.5f), color);
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
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5f) {
            glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
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
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        if (color.getAlpha() > 0.5f) {
            glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBordered(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
        Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
        Gui.drawRect(x2 - length, y2, x2, y2 + height, outerColor);
        Gui.drawRect(x2 - length, y2 - length, x2 + width, y2, outerColor);
        Gui.drawRect(x2 + width, y2 - length, x2 + width + length, y2 + height + length, outerColor);
        Gui.drawRect(x2 - length, y2 + height, x2 + width, y2 + height + length, outerColor);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        glDisable(2929);
        glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        glDisable(3042);
        glEnable(2929);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        glDisable(2929);
        glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float) color.getRed() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getRed() / 255.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        glDisable(3042);
        glEnable(2929);
    }

    public static void drawBordered1(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
        Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
        Gui.drawRect(x2, y2, x2, y2, outerColor);
    }

    public static boolean isInFrustumView(Entity ent) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        double x2 = RenderUtils.interpolate(current.posX, current.lastTickPosX);
        double y2 = RenderUtils.interpolate(current.posY, current.lastTickPosY);
        double z2 = RenderUtils.interpolate(current.posZ, current.lastTickPosZ);
        frustum.setPosition(x2, y2, z2);
        return frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }

    public static final ScaledResolution getScaledRes() {
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        return scaledRes;
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
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
        float var6 = (float) (color >> 24 & 255) / 255.0f;
        float var7 = (float) (color >> 16 & 255) / 255.0f;
        float var8 = (float) (color >> 8 & 255) / 255.0f;
        float var9 = (float) (color & 255) / 255.0f;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var7, var8, var9, var6);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, top, 0.0);
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void disableLighting() {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        glEnable(3042);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        glDisable(2896);
        glDisable(3553);
    }

    public static void enableGL2D() {
        glDisable(2929);
        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        glEnable(3553);
        glDisable(3042);
        glEnable(2929);
        glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        RenderUtils.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        RenderUtils.glColor(topColor);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        RenderUtils.glColor(bottomColor);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        RenderUtils.disableGL2D();
    }

    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0f;
        float red = (float) (hex >> 16 & 255) / 255.0f;
        float green = (float) (hex >> 8 & 255) / 255.0f;
        float blue = (float) (hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawGradientBordere(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        RenderUtils.enableGL2D();
        RenderUtils.drawGradientRect(x2, y2, x1, y1, top, bottom);
        RenderUtils.glColor(border);
        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        glEnable(3553);
        glDisable(3042);
        RenderUtils.disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
        RenderUtils.drawRect(x2, y2, x22, y22, col2);
        float f2 = (float) (col1 >> 24 & 255) / 255.0f;
        float f22 = (float) (col1 >> 16 & 255) / 255.0f;
        float f3 = (float) (col1 >> 8 & 255) / 255.0f;
        float f4 = (float) (col1 & 255) / 255.0f;
        GL11.glPushMatrix();
        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        glEnable(3553);
        glDisable(3042);
        glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawRect(float g2, float h2, float i2, float j2, int col1) {
        float f2 = (float) (col1 >> 24 & 255) / 255.0f;
        float f22 = (float) (col1 >> 16 & 255) / 255.0f;
        float f3 = (float) (col1 >> 8 & 255) / 255.0f;
        float f4 = (float) (col1 & 255) / 255.0f;
        GL11.glPushMatrix();
        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(i2, h2);
        GL11.glVertex2d(g2, h2);
        GL11.glVertex2d(g2, j2);
        GL11.glVertex2d(i2, j2);
        GL11.glEnd();
        glEnable(3553);
        glDisable(3042);
        glDisable(2848);
        GL11.glPopMatrix();
    }

    public static class R3DUtils {
        public static void startDrawing() {
            glEnable(3042);
            glEnable(3042);
            GL11.glBlendFunc(770, 771);
            glEnable(2848);
            glDisable(3553);
            glDisable(2929);
            Wrapper.mc.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
        }

        public static void stopDrawing() {
            glDisable(3042);
            glEnable(3553);
            glDisable(2848);
            glDisable(3042);
            glEnable(2929);
        }

        public static void drawOutlinedBox(AxisAlignedBB box2) {
            if (box2 == null) {
                return;
            }
            Wrapper.mc.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
            GL11.glBegin(3);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.maxZ);
            GL11.glEnd();
        }

        public static void drawBoundingBox(AxisAlignedBB aabb) {
            WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            Tessellator tessellator = Tessellator.getInstance();
            Wrapper.mc.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tessellator.draw();
        }
    }

}

