/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import de.Hero.clickgui.util.ColorUtil;
import java.awt.Color;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class RenderHelper {
    private static final Vec3 field_82884_b = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
    private static final Vec3 field_82885_c = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
    private static final int GL_BLEND = 0;
    public static final int GL_DEPTH_TEST = 0;

    public static void color(int color, float alpha) {
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void blockESPBoxRGB(BlockPos blockPos) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getRenderManager();
        double x2 = (double)blockPos.getX() - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double y2 = (double)blockPos.getY() - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double z2 = (double)blockPos.getZ() - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.3f);
        GL11.glColor4d(0.0, 1.0, 0.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        Color c2 = ColorUtil.getClickGUIColor();
        GL11.glColor4d((float)c2.getRed() / 255.0f, (float)c2.getGreen() / 255.0f, (float)c2.getBlue() / 255.0f, 1.0);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0), -1);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glPopMatrix();
    }

    private static Color rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).brighter();
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

    public static void drawFilledCircle(int x2, int y2, double r2, int c2) {
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
            GL11.glVertex2d((double)x2 + x22, (double)y2 + y22);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb) {
        Tessellator ts2 = Tessellator.getInstance();
        WorldRenderer wr2 = ts2.getWorldRenderer();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        ts2.draw();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        ts2.draw();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        ts2.draw();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        ts2.draw();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        ts2.draw();
        wr2.startDrawingQuads();
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        wr2.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        ts2.draw();
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
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glColor4f(red, green, blue, 0.2f);
        RenderHelper.drawFilledBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawCoolLines(AxisAlignedBB mask) {
        GL11.glPushMatrix();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
        RenderHelper.drawRect(x2, y2, x22, y22, col2);
        float f2 = (float)(col1 >> 24 & 255) / 255.0f;
        float f1 = (float)(col1 >> 16 & 255) / 255.0f;
        float f22 = (float)(col1 >> 8 & 255) / 255.0f;
        float f3 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
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

    public static void drawFilledBox(AxisAlignedBB mask) {
        WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
        Tessellator tessellator = Tessellator.instance;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        tessellator.draw();
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawGradientRect(int x2, int y2, int x1, int y1, int topColor, int bottomColor) {
        GL11.glPushMatrix();
    }

    public static void drawLines(AxisAlignedBB mask) {
        GL11.glPushMatrix();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
        GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
        GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB mask) {
        WorldRenderer var2 = Tessellator.instance.getWorldRenderer();
        Tessellator var1 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(mask.minX, mask.minY, mask.minZ);
        var2.addVertex(mask.maxX, mask.minY, mask.minZ);
        var2.addVertex(mask.maxX, mask.minY, mask.maxZ);
        var2.addVertex(mask.minX, mask.minY, mask.maxZ);
        var2.addVertex(mask.minX, mask.minY, mask.minZ);
        var1.draw();
        var2.startDrawing(3);
        var2.addVertex(mask.minX, mask.maxY, mask.minZ);
        var2.addVertex(mask.maxX, mask.maxY, mask.minZ);
        var2.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        var2.addVertex(mask.minX, mask.maxY, mask.maxZ);
        var2.addVertex(mask.minX, mask.maxY, mask.minZ);
        var1.draw();
        var2.startDrawing(1);
        var2.addVertex(mask.minX, mask.minY, mask.minZ);
        var2.addVertex(mask.minX, mask.maxY, mask.minZ);
        var2.addVertex(mask.maxX, mask.minY, mask.minZ);
        var2.addVertex(mask.maxX, mask.maxY, mask.minZ);
        var2.addVertex(mask.maxX, mask.minY, mask.maxZ);
        var2.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        var2.addVertex(mask.minX, mask.minY, mask.maxZ);
        var2.addVertex(mask.minX, mask.maxY, mask.maxZ);
        var1.draw();
    }

    public static void drawRect(float g2, float h2, float i2, float j2, int col1) {
        float f2 = (float)(col1 >> 24 & 255) / 255.0f;
        float f1 = (float)(col1 >> 16 & 255) / 255.0f;
        float f22 = (float)(col1 >> 8 & 255) / 255.0f;
        float f3 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(i2, h2);
        GL11.glVertex2d(g2, h2);
        GL11.glVertex2d(g2, j2);
        GL11.glVertex2d(i2, j2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void enableGUIStandardItemLighting() {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableBooleanStateAt(0);
        GlStateManager.enableBooleanStateAt(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        float var0 = 0.4f;
        float var1 = 0.6f;
        float var2 = 0.0f;
        GL11.glLight(16384, 4611, RenderHelper.setColorBuffer(RenderHelper.field_82884_b.xCoord, RenderHelper.field_82884_b.yCoord, RenderHelper.field_82884_b.zCoord, 0.0));
        GL11.glLight(16384, 4609, RenderHelper.setColorBuffer(var1, var1, var1, 1.0));
        GL11.glLight(16384, 4608, RenderHelper.setColorBuffer(0.0, 0.0, 0.0, 1.0));
        GL11.glLight(16384, 4610, RenderHelper.setColorBuffer(var2, var2, var2, 1.0));
        GL11.glLight(16385, 4611, RenderHelper.setColorBuffer(RenderHelper.field_82885_c.xCoord, RenderHelper.field_82885_c.yCoord, RenderHelper.field_82885_c.zCoord, 0.0));
        GL11.glLight(16385, 4609, RenderHelper.setColorBuffer(var1, var1, var1, 1.0));
        GL11.glLight(16385, 4608, RenderHelper.setColorBuffer(0.0, 0.0, 0.0, 1.0));
        GL11.glLight(16385, 4610, RenderHelper.setColorBuffer(var2, var2, var2, 1.0));
        GlStateManager.shadeModel(7424);
        GL11.glLightModel(2899, RenderHelper.setColorBuffer(var0, var0, var0, 1.0));
    }

    private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
        return RenderHelper.setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }

    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableBooleanStateAt(0);
        GlStateManager.disableBooleanStateAt(1);
        GlStateManager.disableColorMaterial();
    }

    public static void drawESP(double a1, double a2, double a3, double a4, double a5, double a6, float a7, float a8, float a9) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().entityRenderer.func_175072_h();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.5f);
        GL11.glColor4f(a7, a8, a9, 0.5f);
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(a1, a2, a3, a4, a5, a6));
        GL11.glColor4f(a7, a8, a9, 0.2f);
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(a1, a2, a3, a4, a5, a6));
        Minecraft.getMinecraft().entityRenderer.func_180436_i();
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB a1) {
        WorldRenderer v1 = Tessellator.instance.getWorldRenderer();
        Tessellator v2 = Tessellator.instance;
        v1.startDrawingQuads();
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v2.draw();
        v1.startDrawingQuads();
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v2.draw();
        v1.startDrawingQuads();
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v2.draw();
        v1.startDrawingQuads();
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v2.draw();
        v1.startDrawingQuads();
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v2.draw();
        v1.startDrawingQuads();
        v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.minX, a1.minY, a1.maxZ);
        v1.addVertex(a1.minX, a1.maxY, a1.minZ);
        v1.addVertex(a1.minX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
        v1.addVertex(a1.maxX, a1.minY, a1.minZ);
        v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
        v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
        v2.draw();
    }

    public static void entityESPBox(AxisAlignedBB coords, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderHelper.color(color.getRGB(), color.getAlpha());
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawOutlinedBoundingBox(coords, -1);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
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
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
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
        RenderHelper.drawBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
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
        RenderHelper.drawBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
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
        RenderHelper.drawBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
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
        RenderHelper.drawBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x2 - width, y2, z2 - width, x2 + width, y2 + height, z2 + width));
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

    public static void entityESPBox(Entity entity, int mode) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(0);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(0);
        GL11.glDepthMask(false);
        if (mode == 0) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            GL11.glColor4d(1.0f - Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f, Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f, 0.0, 0.5);
        } else if (mode == 2) {
            GL11.glColor4d(1.0, 1.0, 0.0, 0.5);
        }
        GL11.glColor4d(0.0, 1.0, 0.0, 0.5);
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ)), -1);
        GL11.glEnable(3553);
        GL11.glEnable(0);
        GL11.glDepthMask(true);
        GL11.glDisable(0);
    }

    public static void blockESPBox(BlockPos blockPos) {
        Minecraft.getMinecraft().getRenderManager();
        double x2 = (double)blockPos.getX() - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double y2 = (double)blockPos.getY() - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double z2 = (double)blockPos.getZ() - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(0);
        GL11.glLineWidth(1.0f);
        GL11.glColor4d(0.0, 1.0, 0.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(0);
        GL11.glDepthMask(false);
        RenderHelper.drawColorBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0));
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0), -1);
        GL11.glEnable(3553);
        GL11.glEnable(0);
        GL11.glDepthMask(true);
        GL11.glDisable(0);
    }

    public static void entityESPBox2(Entity entity, int mode) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(0);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(0);
        GL11.glDepthMask(false);
        if (mode == 0) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            GL11.glColor4d(1.0f - Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f, Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f, 0.0, 0.5);
        } else if (mode == 1) {
            GL11.glColor4d(0.0, 0.0, 1.0, 0.5);
        } else if (mode == 2) {
            GL11.glColor4d(1.0, 1.0, 0.0, 0.5);
        } else if (mode == 3) {
            GL11.glColor4d(1.0, 0.0, 0.0, 0.5);
        } else if (mode == 4) {
            GL11.glColor4d(0.0, 1.0, 0.0, 0.5);
        }
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ)), -1);
        GL11.glEnable(3553);
        GL11.glEnable(0);
        GL11.glDepthMask(true);
        GL11.glDisable(0);
    }

    public static void box(double x2, double y2, double z2, double x22, double y22, double z22, Color color) {
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(0);
        GL11.glLineWidth(2.0f);
        RenderHelper.glColor(color);
        GL11.glDisable(3553);
        GL11.glDisable(0);
        GL11.glDepthMask(false);
        RenderHelper.drawColorBox(new AxisAlignedBB(x2 -= RenderManager.renderPosX, y2 -= RenderManager.renderPosY, z2 -= RenderManager.renderPosZ, x22 -= RenderManager.renderPosX, y22 -= RenderManager.renderPosY, z22 -= RenderManager.renderPosZ));
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y2, z2, x22, y22, z22), -1);
        GL11.glEnable(3553);
        GL11.glEnable(0);
        GL11.glDepthMask(true);
        GL11.glDisable(0);
    }

    public static void drawRect(double d2, double e2, double g2, double h2, int col1) {
        float f2 = (float)(col1 >> 24 & 255) / 255.0f;
        float f1 = (float)(col1 >> 16 & 255) / 255.0f;
        float f22 = (float)(col1 >> 8 & 255) / 255.0f;
        float f3 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(g2, e2);
        GL11.glVertex2d(d2, e2);
        GL11.glVertex2d(d2, h2);
        GL11.glVertex2d(g2, h2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}

