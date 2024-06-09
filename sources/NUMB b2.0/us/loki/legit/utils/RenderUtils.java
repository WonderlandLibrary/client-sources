package us.loki.legit.utils;

import java.awt.Color;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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

public final class RenderUtils {

	private static final Vec3 field_82884_b = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
	private static final Vec3 field_82885_c = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);

	public static void color(int color, float alpha) {
		float red = (float) (color >> 16 & 255) / 255.0f;
		float green = (float) (color >> 8 & 255) / 255.0f;
		float blue = (float) (color & 255) / 255.0f;
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb) {
		Tessellator ts = Tessellator.getInstance();
		WorldRenderer wr = ts.getWorldRenderer();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();
	}

	public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red,
			float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtils
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) 0.2f);
		RenderUtils.drawFilledBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawCoolLines(AxisAlignedBB mask) {
		GL11.glPushMatrix();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.maxZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.minZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		RenderUtils.drawRect(x, y, x2, y2, col2);
		float f = (float) (col1 >> 24 & 255) / 255.0f;
		float f1 = (float) (col1 >> 16 & 255) / 255.0f;
		float f2 = (float) (col1 >> 8 & 255) / 255.0f;
		float f3 = (float) (col1 & 255) / 255.0f;
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glPushMatrix();
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glLineWidth((float) l1);
		GL11.glBegin((int) 1);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
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
		GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f),
				(float) ((float) color.getBlue() / 255.0f), (float) ((float) color.getAlpha() / 255.0f));
	}

	public static void glColor(int hex) {
		float alpha = (float) (hex >> 24 & 255) / 255.0f;
		float red = (float) (hex >> 16 & 255) / 255.0f;
		float green = (float) (hex >> 8 & 255) / 255.0f;
		float blue = (float) (hex & 255) / 255.0f;
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
	}

	public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
		GL11.glEnable((int) 1536);
		GL11.glShadeModel((int) 7425);
		GL11.glBegin((int) 7);
		RenderUtils.glColor(topColor);
		GL11.glVertex2f((float) x, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		RenderUtils.glColor(bottomColor);
		GL11.glVertex2f((float) x1, (float) y);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glEnd();
		GL11.glShadeModel((int) 7424);
		GL11.glDisable((int) 1536);
	}

	public static void drawLines(AxisAlignedBB mask) {
		GL11.glPushMatrix();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.maxZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.minZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.minZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.maxY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.maxY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) mask.maxX, (double) mask.minY, (double) mask.minZ);
		GL11.glVertex3d((double) mask.minX, (double) mask.minY, (double) mask.maxZ);
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

	public static void drawRect(float g, float h, float i, float j, int col1) {
		float f = (float) (col1 >> 24 & 255) / 255.0f;
		float f1 = (float) (col1 >> 16 & 255) / 255.0f;
		float f2 = (float) (col1 >> 8 & 255) / 255.0f;
		float f3 = (float) (col1 & 255) / 255.0f;
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glPushMatrix();
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 7);
		GL11.glVertex2d((double) i, (double) h);
		GL11.glVertex2d((double) g, (double) h);
		GL11.glVertex2d((double) g, (double) j);
		GL11.glVertex2d((double) i, (double) j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
	}

	public static void enableGUIStandardItemLighting() {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
		RenderUtils.enableStandardItemLighting();
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
		GL11.glLight((int) 16384, (int) 4611,
				(FloatBuffer) RenderUtils.setColorBuffer(RenderUtils.field_82884_b.xCoord,
						RenderUtils.field_82884_b.yCoord, RenderUtils.field_82884_b.zCoord, 0.0));
		GL11.glLight((int) 16384, (int) 4609, (FloatBuffer) RenderUtils.setColorBuffer(var1, var1, var1, 1.0));
		GL11.glLight((int) 16384, (int) 4608, (FloatBuffer) RenderUtils.setColorBuffer(0.0, 0.0, 0.0, 1.0));
		GL11.glLight((int) 16384, (int) 4610, (FloatBuffer) RenderUtils.setColorBuffer(var2, var2, var2, 1.0));
		GL11.glLight((int) 16385, (int) 4611,
				(FloatBuffer) RenderUtils.setColorBuffer(RenderUtils.field_82885_c.xCoord,
						RenderUtils.field_82885_c.yCoord, RenderUtils.field_82885_c.zCoord, 0.0));
		GL11.glLight((int) 16385, (int) 4609, (FloatBuffer) RenderUtils.setColorBuffer(var1, var1, var1, 1.0));
		GL11.glLight((int) 16385, (int) 4608, (FloatBuffer) RenderUtils.setColorBuffer(0.0, 0.0, 0.0, 1.0));
		GL11.glLight((int) 16385, (int) 4610, (FloatBuffer) RenderUtils.setColorBuffer(var2, var2, var2, 1.0));
		GlStateManager.shadeModel(7424);
		GL11.glLightModel((int) 2899, (FloatBuffer) RenderUtils.setColorBuffer(var0, var0, var0, 1.0));
	}

	private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_,
			double p_74517_6_) {
		return RenderUtils.setColorBuffer((float) p_74517_0_, (float) p_74517_2_, (float) p_74517_4_,
				(float) p_74517_6_);
	}

	public static void disableStandardItemLighting() {
		GlStateManager.disableLighting();
		GlStateManager.disableBooleanStateAt(0);
		GlStateManager.disableBooleanStateAt(1);
		GlStateManager.disableColorMaterial();
	}

	public static void drawESP(double a1, double a2, double a3, double a4, double a5, double a6, float a7, float a8,
			float a9) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().entityRenderer.func_175072_h();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 2896);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glLineWidth((float) 1.5f);
		GL11.glColor4f((float) a7, (float) a8, (float) a9, (float) 0.5f);
		RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(a1, a2, a3, a4, a5, a6));
		GL11.glColor4f((float) a7, (float) a8, (float) a9, (float) 0.2f);
		RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(a1, a2, a3, a4, a5, a6));
		Minecraft.getMinecraft().entityRenderer.func_180436_i();
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2896);
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glLineWidth((float) 1.0f);
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
		GL11.glPopMatrix();
	}

	public static void drawBoundingBox(final AxisAlignedBB a1) {
		final WorldRenderer v1 = Tessellator.instance.getWorldRenderer();
		final Tessellator v2 = Tessellator.instance;
		/* SL:539 */v1.startDrawingQuads();
		/* SL:540 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:541 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:542 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:543 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:544 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:545 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:546 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:547 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:548 */v2.draw();
		/* SL:549 */v1.startDrawingQuads();
		/* SL:550 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:551 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:552 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:553 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:554 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:555 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:556 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:557 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:558 */v2.draw();
		/* SL:559 */v1.startDrawingQuads();
		/* SL:560 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:561 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:562 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:563 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:564 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:565 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:566 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:567 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:568 */v2.draw();
		/* SL:569 */v1.startDrawingQuads();
		/* SL:570 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:571 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:572 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:573 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:574 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:575 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:576 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:577 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:578 */v2.draw();
		/* SL:579 */v1.startDrawingQuads();
		/* SL:580 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:581 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:582 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:583 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:584 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:585 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:586 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:587 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:588 */v2.draw();
		/* SL:589 */v1.startDrawingQuads();
		/* SL:590 */v1.addVertex(a1.minX, a1.maxY, a1.maxZ);
		/* SL:591 */v1.addVertex(a1.minX, a1.minY, a1.maxZ);
		/* SL:592 */v1.addVertex(a1.minX, a1.maxY, a1.minZ);
		/* SL:593 */v1.addVertex(a1.minX, a1.minY, a1.minZ);
		/* SL:594 */v1.addVertex(a1.maxX, a1.maxY, a1.minZ);
		/* SL:595 */v1.addVertex(a1.maxX, a1.minY, a1.minZ);
		/* SL:596 */v1.addVertex(a1.maxX, a1.maxY, a1.maxZ);
		/* SL:597 */v1.addVertex(a1.maxX, a1.minY, a1.maxZ);
		/* SL:598 */v2.draw();
	}

	public static void entityESPBox(AxisAlignedBB coords, Color color) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);

		color(color.getRGB(), color.getAlpha());

		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawOutlinedBoundingBox(coords, -1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private static final int GL_BLEND = 0;
	private static final int GL_DEPTH_TEST = 0;

	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue,
			float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glLineWidth((float) lineWidth);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glLineWidth((float) lineWidth);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue,
			float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red,
			float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green,
			float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 3042);
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) 0.0, (double) (0.0 + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight()),
				(double) 0.0);
		GL11.glVertex3d((double) x, (double) y, (double) z);
		GL11.glEnd();
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDisable((int) 2848);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void entityESPBox(Entity entity, int mode) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		if (mode == 0)// Enemy
			GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40, 0, 0.5F);
		else if (mode == 2)// Other
			GL11.glColor4d(1, 1, 0, 0.5F);
		GL11.glColor4d(0, 1, 0, 0.5F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05 - entity.posX
						+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
				entity.boundingBox.minY - entity.posY
						+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ
						+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX
						+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY
						+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ
						+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ)),
				-1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void blockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), -1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void entityESPBox2(Entity entity, int mode) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		if (mode == 0)// Enemy
			GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40, 0, 0.5F);
		else if (mode == 1)// Friend
			GL11.glColor4d(0, 0, 1, 0.5F);
		else if (mode == 2)// Other
			GL11.glColor4d(1, 1, 0, 0.5F);
		else if (mode == 3)// Target
			GL11.glColor4d(1, 0, 0, 0.5F);
		else if (mode == 4)// Team
			GL11.glColor4d(0, 1, 0, 0.5F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05 - entity.posX
						+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
				entity.boundingBox.minY - entity.posY
						+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ
						+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX
						+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY
						+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ
						+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ)),
				-1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void box(double x, double y, double z, double x2, double y2, double z2, Color color) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		glColor(color);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2), -1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void drawBorderedCorneredRect(final float x, final float y, final float x2, final float y2,
			final float lineWidth, final int lineColor, final int bgColor) {
		drawRect(x, y, x2, y2, bgColor);
		final float f = (lineColor >> 24 & 0xFF) / 255.0f;
		final float f2 = (lineColor >> 16 & 0xFF) / 255.0f;
		final float f3 = (lineColor >> 8 & 0xFF) / 255.0f;
		final float f4 = (lineColor & 0xFF) / 255.0f;
		drawRect(x - 1.0f, y - 1.0f, x2 + 1.0f, y, lineColor);
		drawRect(x - 1.0f, y, x, y2, lineColor);
		drawRect(x - 1.0f, y2, x2 + 1.0f, y2 + 1.0f, lineColor);
		drawRect(x2, y, x2 + 1.0f, y2, lineColor);
	}

	public static void drawTracerLine(double x, double y, double z, Color color, float alpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		glColor(color);
		GL11.glBegin(2);
		GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	/*public static void drawBorderRect(int left, int top, int right, int bottom, int bcolor, int icolor, int bwidth) {
		drawRect(left + bwidth, top + bwidth, right - bwidth, bottom - bwidth, icolor);
		drawRect(left, top + 1, left + bwidth, bottom - 1, bcolor);
		drawRect(left + bwidth, top, right, top + bwidth, bcolor);
		drawRect(left + bwidth, bottom - bwidth, right, bottom, bcolor);
		drawRect(right - bwidth, top + bwidth, right, bottom - bwidth, bcolor);
	}*/

	public static void drawRect(final double d, final double e, final double f2, final double f3, final float red,
			final float green, final float blue, final float alpha) {
		GlStateManager.enableBlend();
		GlStateManager.func_179090_x();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(7);
		GL11.glVertex2d(f2, e);
		GL11.glVertex2d(d, e);
		GL11.glVertex2d(d, f3);
		GL11.glVertex2d(f2, f3);
		GL11.glEnd();
		GlStateManager.func_179098_w();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}

	public static void drawRect(final double d, final double e, final double f2, final double f3,
			final int paramColor) {
		final float alpha = (paramColor >> 24 & 0xFF) / 255.0f;
		final float red = (paramColor >> 16 & 0xFF) / 255.0f;
		final float green = (paramColor >> 8 & 0xFF) / 255.0f;
		final float blue = (paramColor & 0xFF) / 255.0f;
		GlStateManager.enableBlend();
		GlStateManager.func_179090_x();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(7);
		GL11.glVertex2d(f2, e);
		GL11.glVertex2d(d, e);
		GL11.glVertex2d(d, f3);
		GL11.glVertex2d(f2, f3);
		GL11.glEnd();
		GlStateManager.func_179098_w();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}

	public static void drawRectColor(final double d, final double e, final double f2, final double f3,
			final float alpha, final float red, final float green, final float blue) {
		GlStateManager.enableBlend();
		GlStateManager.func_179090_x();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(alpha, red, green, blue);
		GL11.glBegin(7);
		GL11.glVertex2d(f2, e);
		GL11.glVertex2d(d, e);
		GL11.glVertex2d(d, f3);
		GL11.glVertex2d(f2, f3);
		GL11.glEnd();
		GlStateManager.func_179098_w();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}

	public static void DrawArc(double cx, double cy, double r, double start_angle, double arc_angle, int num_segments,
			float lw, int c) {
		float f = (c >> 24 & 0xFF) / 255.0F;
		float f1 = (c >> 16 & 0xFF) / 255.0F;
		float f2 = (c >> 8 & 0xFF) / 255.0F;
		float f3 = (c & 0xFF) / 255.0F;
		GL11.glColor4f(f1, f2, f3, f);
		double theta = arc_angle / (num_segments - 1);

		double tangetial_factor = Math.tan(theta);
		double radial_factor = Math.cos(theta);

		double x = r * Math.cos(start_angle);
		double y = r * Math.sin(start_angle);

		GL11.glLineWidth(lw);
		GL11.glBegin(3);
		for (int ii = 0; ii < num_segments; ii++) {
			GL11.glVertex2d(x + cx, y + cy);

			double tx = -y;
			double ty = x;

			x += tx * tangetial_factor;
			y += ty * tangetial_factor;

			x *= radial_factor;
			y *= radial_factor;
		}
		GL11.glEnd();
	}

	public static void enableGL3D(float lineWidth) {
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glEnable(2884);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glLineWidth(lineWidth);
	}

	public static void disableGL3D() {
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glDepthMask(true);
		GL11.glCullFace(1029);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static void drawBorderRect(float left, float top, float right, float bottom, int bcolor, int icolor,
			float f) {
		Gui.drawRect(left + f, top + f, right - f, bottom - f, icolor);
		Gui.drawRect(left, top, left + f, bottom, bcolor);
		Gui.drawRect(left + f, top, right, top + f, bcolor);
		Gui.drawRect(left + f, bottom - f, right, bottom, bcolor);
		Gui.drawRect(right - f, top + f, right, bottom - f, bcolor);
	}
	public static void drawRect(int x, int y, int x1, int y1, int color)
	  {
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

}
