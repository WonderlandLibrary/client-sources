/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void drawOutlinedBoundingBox(AxisAlignedBB aa2) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aa2) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.maxZ);
        worldRenderer.addVertex(aa2.minX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.minX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.minZ);
        worldRenderer.addVertex(aa2.maxX, aa2.maxY, aa2.maxZ);
        worldRenderer.addVertex(aa2.maxX, aa2.minY, aa2.maxZ);
        tessellator.draw();
    }

    public static void drawOutlinedBlockESP(double x2, double y2, double z2, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawBlockESP(double x2, double y2, double z2, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidBlockESP(double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedEntityESP(double x2, double y2, double z2, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidEntityESP(double x2, double y2, double z2, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawEntityESP(double x2, double y2, double z2, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawTracerLine(double x2, double y2, double z2, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        Minecraft.getMinecraft();
        GL11.glVertex3d(0.0, 0.0 + (double)Minecraft.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawCircle(int x2, int y2, double r2, int c2) {
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f1 = (float)(c2 >> 16 & 255) / 255.0f;
        float f22 = (float)(c2 >> 8 & 255) / 255.0f;
        float f3 = (float)(c2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(2);
        for (int i2 = 0; i2 <= 360; ++i2) {
            double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
            double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d((double)x2 + x22, (double)y2 + y22);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void drawFilledCircle1(double x2, double y2, double r2, int c2) {
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f1 = (float)(c2 >> 16 & 255) / 255.0f;
        float f22 = (float)(c2 >> 8 & 255) / 255.0f;
        float f3 = (float)(c2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(6);
        for (int i2 = 0; i2 <= 360; ++i2) {
            double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
            double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d(x2 + x22, y2 + y22);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void drawFilledCircle(double x2, double y2, double r2, int c2) {
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f1 = (float)(c2 >> 16 & 255) / 255.0f;
        float f22 = (float)(c2 >> 8 & 255) / 255.0f;
        float f3 = (float)(c2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(6);
        for (int i2 = 0; i2 <= 360; ++i2) {
            double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
            double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d(x2 + x22, y2 + y22);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void dr(double i2, double j2, double k2, double l2, int i1) {
        if (i2 < k2) {
            double j1 = i2;
            i2 = k2;
            k2 = j1;
        }
        if (j2 < l2) {
            double k1 = j2;
            j2 = l2;
            l2 = k1;
        }
        float f2 = (float)(i1 >> 24 & 255) / 255.0f;
        float f1 = (float)(i1 >> 16 & 255) / 255.0f;
        float f22 = (float)(i1 >> 8 & 255) / 255.0f;
        float f3 = (float)(i1 & 255) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f22, f3, f2);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(i2, l2, 0.0);
        worldRenderer.addVertex(k2, l2, 0.0);
        worldRenderer.addVertex(k2, j2, 0.0);
        worldRenderer.addVertex(i2, j2, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        float f2 = (float)(color >> 24 & 255) / 255.0f;
        float f1 = (float)(color >> 16 & 255) / 255.0f;
        float f22 = (float)(color >> 8 & 255) / 255.0f;
        float f3 = (float)(color & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawImage(int x2, int y2, int width, int height, ResourceLocation rs2) {
        GlStateManager.enableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(rs2);
        Gui.drawModalRectWithCustomSizedTexture(x2, y2, 0.0f, 0.0f, width, height, width, height);
    }

    public static void drawRoundedRect(float x2, float y2, float x22, float y22, float round, int color) {
        RenderUtils.drawRect(x2 + 0.5f, y2, x22 - 0.5f, y2 + 0.5f, color);
        RenderUtils.drawRect(x2 + 0.5f, y22 - 0.5f, x22 - 0.5f, y22, color);
        RenderUtils.drawRect(x2, y2 + 0.5f, x22, y22 - 0.5f, color);
    }

    public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
        RenderUtils.drawRect(x2, y2, x22, y22, col2);
        float f2 = (float)(col1 >> 24 & 255) / 255.0f;
        float f22 = (float)(col1 >> 16 & 255) / 255.0f;
        float f3 = (float)(col1 >> 8 & 255) / 255.0f;
        float f4 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
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
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}

