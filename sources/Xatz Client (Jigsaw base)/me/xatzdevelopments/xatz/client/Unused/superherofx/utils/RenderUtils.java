package me.xatzdevelopments.xatz.client.Unused.superherofx.utils;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector2d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtils {
	
private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void drawBorderedRect(float f, int i, int j, int k, float g, int l, int m) {
		Gui.drawRect((int)f, i, j, k, 0x90909090);
	}
	
	public static void drawBorderedRect(float f, float g, int j, int k, float g2, int l, int minValue) {
		
	}
	
	public static int toRGBA(Color c) {
		return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
	}
	
	 public static Vector2d worldToScreen(final double posX, final double posY, final double posZ) {
	        final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
	        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
	        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
	        final FloatBuffer screenCords = GLAllocation.createDirectFloatBuffer(3);
	        modelView.clear();
	        projection.clear();
	        viewport.clear();
	        screenCords.clear();
	        GL11.glGetFloat(2982, modelView);
	        GL11.glGetFloat(2983, projection);
	        GL11.glGetInteger(2978, viewport);
	        if (GLU.gluProject((float)posX, (float)posY, (float)posZ, modelView, projection, viewport, screenCords)) {
	            return new Vector2d((double)(screenCords.get(0) / 2.0f), (double)(screenCords.get(1) / 2.0f));
	        }
	        return null;
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
	     
	     public static void drawEntityESP(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha) {
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
	         GL11.glVertex3d(0.0, 0.0 + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0);
	         GL11.glVertex3d(x, y, z);
	         GL11.glEnd();
	         GL11.glDisable(3042);
	         GL11.glEnable(3553);
	         GL11.glEnable(2929);
	         GL11.glDisable(2848);
	         GL11.glDisable(3042);
	         GL11.glPopMatrix();
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
	     
	     public static void drawCircle(float cx, float cy, float r, final int num_segments, final int c2) {
	         GL11.glPushMatrix();
	         cx *= 2.0f;
	         cy *= 2.0f;
	         final float theta = (float)(6.2831852 / num_segments);
	         final float p2 = (float)Math.cos(theta);
	         final float s = (float)Math.sin(theta);
	         float x = r *= 2.0f;
	         float y2 = 0.0f;
	         enableGL2D();
	         GL11.glScalef(0.5f, 0.5f, 0.5f);
	         GL11.glBegin(2);
	         for (int ii = 0; ii < num_segments; ++ii) {
	             final int c3 = ColorUtil.getRainbow();
	             final float f = (c3 >> 24 & 0xFF) / 255.0f;
	             final float f2 = (c3 >> 16 & 0xFF) / 255.0f;
	             final float f3 = (c3 >> 8 & 0xFF) / 255.0f;
	             final float f4 = (c3 & 0xFF) / 255.0f;
	             GL11.glColor4f(f2, f3, f4, f);
	             GL11.glVertex2f(x + cx, y2 + cy);
	             final float t = x;
	             x = p2 * x - s * y2;
	             y2 = s * t + p2 * y2;
	         }
	         GL11.glEnd();
	         GL11.glScalef(2.0f, 2.0f, 2.0f);
	         disableGL2D();
	         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	         GL11.glPopMatrix();
	     }
	     
	     public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
	         final float red = 0.003921569f * redRGB;
	         final float green = 0.003921569f * greenRGB;
	         final float blue = 0.003921569f * blueRGB;
	         GL11.glColor4f(red, green, blue, alpha);
	     }
	     
	     public static void glColor(final int hex) {
	         final float alpha = (hex >> 24 & 0xFF) / 255.0f;
	         final float red = (hex >> 16 & 0xFF) / 255.0f;
	         final float green = (hex >> 8 & 0xFF) / 255.0f;
	         final float blue = (hex & 0xFF) / 255.0f;
	         GL11.glColor4f(red, green, blue, alpha);
	     }
	     
	     public static void drawFullCircle(float cx, float cy, float r, final int c2) {
	         cx *= 2.0f;
	         cy *= 2.0f;
	         final float p2 = (float)Math.cos(0.19634953141212463);
	         final float s = (float)Math.sin(0.19634953141212463);
	         float x;
	         r = (x = r * 2.0f);
	         float y2 = 0.0f;
	         enableGL2D();
	         GL11.glEnable(2848);
	         GL11.glHint(3154, 4354);
	         GL11.glEnable(3024);
	         GL11.glScalef(0.5f, 0.5f, 0.5f);
	         glColor(c2);
	         GL11.glBegin(9);
	         for (int ii = 0; ii < 32; ++ii) {
	             GL11.glVertex2f(x + cx, y2 + cy);
	             final float t = x;
	             x = p2 * x - s * y2;
	             y2 = s * t + p2 * y2;
	         }
	         GL11.glEnd();
	         GL11.glScalef(2.0f, 2.0f, 2.0f);
	         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	         disableGL2D();
	     }
	 }
