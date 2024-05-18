// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import net.minecraft.client.Minecraft;
import java.awt.Color;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.Gui;

public class RenderUtils
{
    public static void drawBorderRect(final int left, final int top, final int right, final int bottom, final int bcolor, final int icolor, final int bwidth) {
        Gui.drawRect(left + bwidth, top + bwidth, right - bwidth, bottom - bwidth, icolor);
        Gui.drawRect(left, top, left + bwidth, bottom, bcolor);
        Gui.drawRect(left + bwidth, top, right, top + bwidth, bcolor);
        Gui.drawRect(left + bwidth, bottom - bwidth, right, bottom, bcolor);
        Gui.drawRect(right - bwidth, top + bwidth, right, bottom - bwidth, bcolor);
    }
    
    public static void beginGl() {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.func_179090_x();
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
    }
    
    public static void endGl() {
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    public static void drawLines(final AxisAlignedBB bb) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.draw();
    }
    
    public static int transparency(final int color, final float alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }
    
    public static void drawRect(final int x, final int y, final int x1, final int y1, final int color) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        Gui.drawRect(x, y, x1, y1, color);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
    }
    
    public static void drawOutlinedBlockESP(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawBlockESP(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha, final float lineRed, final float lineGreen, final float lineBlue, final float lineAlpha, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawClickTPESP(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha, final float lineRed, final float lineGreen, final float lineBlue, final float lineAlpha, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y + 1.1, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y + 1.1, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawSolidBlockESP(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawOutlinedEntityESP(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawSolidEntityESP(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawEntityESP(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha, final float lineRed, final float lineGreen, final float lineBlue, final float lineAlpha, final float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawHat(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha, final float lineRed, final float lineGreen, final float lineBlue, final float lineAlpha, final float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawTracerLine(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha, final float lineWdith) {
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
        GL11.glVertex3d(0.0, (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawCircle(final int x, final int y, final double r, final int c) {
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(2);
        for (int i = 0; i <= 360; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawFilledCircle(final int x, final int y, final double r, final int c) {
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void dr(double i, double j, double k, double l, final int i1) {
        if (i < k) {
            final double j2 = i;
            i = k;
            k = j2;
        }
        if (j < l) {
            final double k2 = j;
            j = l;
            l = k2;
        }
        final float f = (i1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (i1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (i1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (i1 & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(i, l, 0.0);
        worldRenderer.addVertex(k, l, 0.0);
        worldRenderer.addVertex(k, j, 0.0);
        worldRenderer.addVertex(i, j, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawGradientRect(final double left, final double top, final double right, final double bottom, final int startColor, final int endColor) {
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
        var16.addVertex(right, top, 0.0);
        var16.addVertex(left, top, 0.0);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, 0.0);
        var16.addVertex(right, bottom, 0.0);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }
    
    public static void drawBorderedRect(final double left, final double top, final double right, final double bottom, final float borderWidth, final int borderColor, final int color) {
        final float alpha = (borderColor >> 24 & 0xFF) / 255.0f;
        final float red = (borderColor >> 16 & 0xFF) / 255.0f;
        final float green = (borderColor >> 8 & 0xFF) / 255.0f;
        final float blue = (borderColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        drawRects(left, top, right, bottom, color);
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        if (borderWidth == 1.0f) {
            GL11.glEnable(2848);
        }
        GL11.glLineWidth(borderWidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0);
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, top, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        tessellator.draw();
        GL11.glLineWidth(2.0f);
        if (borderWidth == 1.0f) {
            GL11.glDisable(2848);
        }
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawRects(final double left, final double top, final double right, final double bottom, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        var10.startDrawingQuads();
        var10.addVertex(left, bottom, 0.0);
        var10.addVertex(right, bottom, 0.0);
        var10.addVertex(right, top, 0.0);
        var10.addVertex(left, top, 0.0);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
    
    public static void drawBorderedGradientRect(final double left, final double top, final double right, final double bottom, final float borderWidth, final int borderColor, final int startColor, final int endColor) {
        final float alpha = (borderColor >> 24 & 0xFF) / 255.0f;
        final float red = (borderColor >> 16 & 0xFF) / 255.0f;
        final float green = (borderColor >> 8 & 0xFF) / 255.0f;
        final float blue = (borderColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        drawGradientRect(left, top, right, bottom, startColor, endColor);
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        if (borderWidth == 1.0f) {
            GL11.glEnable(2848);
        }
        GL11.glLineWidth(borderWidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0);
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, top, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        tessellator.draw();
        GL11.glLineWidth(2.0f);
        if (borderWidth == 1.0f) {
            GL11.glDisable(2848);
        }
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBorderedRect(final double left, final double top, final double right, final double bottom, final int borderColor, final int color) {
        drawBorderedRect(left, top, right, bottom, 1.0f, borderColor, color);
    }
    
    public static void drawBorderedGradientRect(final double left, final double top, final double right, final double bottom, final int borderColor, final int startColor, final int endColor) {
        drawBorderedGradientRect(left, top, right, bottom, 1.0f, borderColor, startColor, endColor);
    }
    
    public static int withAlpha(final int c, final float a) {
        final float r = (c >> 16 & 0xFF) / 255.0f;
        final float g = (c >> 8 & 0xFF) / 255.0f;
        final float b = (c & 0xFF) / 255.0f;
        return new Color(r, g, b, a).getRGB();
    }
}
