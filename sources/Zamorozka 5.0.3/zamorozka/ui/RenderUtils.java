package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import zamorozka.main.Zamorozka;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderUtils implements MCUtil {
	public static boolean click;
	private float cumSize;
	private float spin;
	private static final Frustum frustrum = new Frustum();
	private static final int GL_DEPTH_TEST = 0;
	private static final int GL_BLEND = 0;

	public static int getDistanceFromMouse(Entity entity) {
		float[] neededRotations = getRotationsNeeded(entity);

		if (neededRotations != null) {
			float neededYaw = Zamorozka.player().rotationYaw - neededRotations[0];
			float neededPitch = Zamorozka.player().rotationPitch - neededRotations[1];
			float distanceFromMouse = MathHelper.sqrt(neededYaw * neededYaw + neededPitch * neededPitch * 2.0F);
			return (int) distanceFromMouse;
		} else {
			return -1;
		}
	}

	public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos((double) right, (double) top, (double) Gui.zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) top, (double) Gui.zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) bottom, (double) Gui.zLevel).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, (double) Gui.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawCircle(float x, float y, double radius, int colour) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		radius *= 2;
		x *= 2;
		y *= 2;

		float red = (float) (colour >> 16 & 0xff) / 255F;
		float green = (float) (colour >> 8 & 0xff) / 255F;
		float blue = (float) (colour & 0xff) / 255F;
		float alpha = (float) (colour >> 24 & 0xff) / 255F;

		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i <= 360; i++) {
			double x1 = Math.sin((i * Math.PI / 180)) * radius;
			double y1 = Math.cos((i * Math.PI / 180)) * radius;
			GL11.glVertex2d(x + x1, y + y1);
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glScalef(2F, 2F, 2F);
	}

	public void penisESP(final EntityPlayer player, final double x, final double y, final double z) {
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glEnable(2848);
		GL11.glDepthMask(true);
		GL11.glLineWidth(1.0f);
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
		GL11.glTranslated(-x, -y, -z);
		GL11.glTranslated(x, y + player.height / 2.0f - 0.22499999403953552, z);
		GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
		GL11.glRotatef((player.isSneaking() ? 35 : 0) + this.spin, 1.0f + this.spin, 0.0f, this.cumSize);
		GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
		final Cylinder shaft = new Cylinder();
		shaft.setDrawStyle(100013);
		shaft.draw(0.1f, 0.11f, 0.4f, 25, 20);
		GL11.glColor4f(1.38f, 0.85f, 1.38f, 1.0f);
		GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
		GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
		final Sphere right = new Sphere();
		right.setDrawStyle(100013);
		right.draw(0.14f, 10, 20);
		GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
		final Sphere left = new Sphere();
		left.setDrawStyle(100013);
		left.draw(0.14f, 10, 20);
		GL11.glColor4f(1.35f, 0.0f, 0.0f, 1.0f);
		GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
		final Sphere tip = new Sphere();
		tip.setDrawStyle(100013);
		tip.draw(0.13f, 15, 20);
		GL11.glDepthMask(true);
		GL11.glDisable(2848);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(3553);
	}

	public static void drawArrow(float x, float y, final int hexColor) {
		GL11.glPushMatrix();
		GL11.glScaled(1.3, 1.3, 1.3);
		x /= (float) 1.3;
		y /= (float) 1.3;
		GL11.glEnable(2848);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		hexColor(hexColor);
		GL11.glLineWidth(2.0f);
		GL11.glBegin(1);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) (x + 3.0f), (double) (y + 4.0f));
		GL11.glEnd();
		GL11.glBegin(1);
		GL11.glVertex2d((double) (x + 3.0f), (double) (y + 4.0f));
		GL11.glVertex2d((double) (x + 6.0f), (double) y);
		GL11.glEnd();
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

	public static void drawTracerPointer(final float x, final float y, final float size, final float widthDiv,
			final float heightDiv, final int color) {
		final boolean blend = GL11.glIsEnabled(3042);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		hexColor(color);
		GL11.glBegin(7);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) (x - size / widthDiv), (double) (y + size));
		GL11.glVertex2d((double) x, (double) (y + size / heightDiv));
		GL11.glVertex2d((double) (x + size / widthDiv), (double) (y + size));
		GL11.glVertex2d((double) x, (double) y);
		GL11.glEnd();
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
		GL11.glBegin(2);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) (x - size / widthDiv), (double) (y + size));
		GL11.glVertex2d((double) x, (double) (y + size / heightDiv));
		GL11.glVertex2d((double) (x + size / widthDiv), (double) (y + size));
		GL11.glVertex2d((double) x, (double) y);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		if (!blend) {
			GL11.glDisable(3042);
		}
		GL11.glDisable(2848);
	}

	public static void hexColor(final int hexColor) {
		final float red = (hexColor >> 16 & 0xFF) / 255.0f;
		final float green = (hexColor >> 8 & 0xFF) / 255.0f;
		final float blue = (hexColor & 0xFF) / 255.0f;
		final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static void keystrokes() {
		ScaledResolution s1 = new ScaledResolution(mc);
		int keyW = mc.gameSettings.keyBindForward.getKeyCode();
		String keynameW = Keyboard.getKeyName(keyW);
		if (!Keyboard.isKeyDown(keyW)) {
			drawUnfilledCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 92, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 92, 12.0F, -15921907);
			mc.fontRendererObj.drawStringWithShadow(keynameW, (float) (s1.getScaledWidth() - 65f),
					(float) (s1.getScaledHeight() - 99f), -255);
		} else {
			drawUnfilledCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 92, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 92, 12.0F, Color.CYAN.hashCode());
			mc.fontRendererObj.drawStringWithShadow(keynameW, (float) (s1.getScaledWidth() - 65f),
					(float) (s1.getScaledHeight() - 99f), -255);
		}

		int keyS = mc.gameSettings.keyBindBack.getKeyCode();
		String keynameS = Keyboard.getKeyName(keyS);
		if (!Keyboard.isKeyDown(keyS)) {
			drawUnfilledCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 53, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 53, 12.0F, -15921907);
			mc.fontRendererObj.drawStringWithShadow(keynameS, (float) (s1.getScaledWidth() - 65f),
					(float) (s1.getScaledHeight() - 60f), -255);
		} else {
			drawUnfilledCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 53, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 60, s1.getScaledHeight() - 53, 12.0F, Color.CYAN.hashCode());
			mc.fontRendererObj.drawStringWithShadow(keynameS, (float) (s1.getScaledWidth() - 65f),
					(float) (s1.getScaledHeight() - 60f), -255);
		}

		int keyD = mc.gameSettings.keyBindRight.getKeyCode();
		String keynameD = Keyboard.getKeyName(keyD);
		if (!Keyboard.isKeyDown(keyD)) {
			drawUnfilledCircle(s1.getScaledWidth() - 20, s1.getScaledHeight() - 53, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 20, s1.getScaledHeight() - 53, 12.0F, -15921907);
			mc.fontRendererObj.drawStringWithShadow(keynameD, (float) (s1.getScaledWidth() - 24f),
					(float) (s1.getScaledHeight() - 60f), -255);
		} else {
			drawUnfilledCircle(s1.getScaledWidth() - 20, s1.getScaledHeight() - 53, 12.0F, 8.0F, Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 20, s1.getScaledHeight() - 53, 12.0F, Color.CYAN.hashCode());
			mc.fontRendererObj.drawStringWithShadow(keynameD, (float) (s1.getScaledWidth() - 24f),
					(float) (s1.getScaledHeight() - 60f), -255);
		}

		int keyA = mc.gameSettings.keyBindLeft.getKeyCode();
		String keynameA = Keyboard.getKeyName(keyA);
		if (!Keyboard.isKeyDown(keyA)) {
			drawUnfilledCircle(s1.getScaledWidth() - 100, s1.getScaledHeight() - 53, 12.0F, 8.0F,
					Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 100, s1.getScaledHeight() - 53, 12.0F, -15921907);
			mc.fontRendererObj.drawStringWithShadow(keynameA, (float) (s1.getScaledWidth() - 105f),
					(float) (s1.getScaledHeight() - 60f), -255);
		} else {
			drawUnfilledCircle(s1.getScaledWidth() - 100, s1.getScaledHeight() - 53, 12.0F, 8.0F,
					Color.CYAN.hashCode());
			drawCircle(s1.getScaledWidth() - 100, s1.getScaledHeight() - 53, 12.0F, Color.CYAN.hashCode());
			mc.fontRendererObj.drawStringWithShadow(keynameA, (float) (s1.getScaledWidth() - 105f),
					(float) (s1.getScaledHeight() - 60f), -255);
		}

	}

	public static void drawCircle(int x, int y, float radius, int color) {
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glEnable(2848);
		GL11.glDisable(3553);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(9);

		for (int i = 0; i <= 360; ++i) {
			GL11.glVertex2d((double) x + Math.sin((double) i * 3.141526D / 180.0D) * (double) radius,
					(double) y + Math.cos((double) i * 3.141526D / 180.0D) * (double) radius);
		}

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color) {
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glEnable(2848);
		GL11.glDisable(3553);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(lineWidth);
		GL11.glBegin(2);

		for (int i = 0; i <= 360; ++i) {
			GL11.glVertex2d((double) x + Math.sin((double) i * 3.141526D / 180.0D) * (double) radius,
					(double) y + Math.cos((double) i * 3.141526D / 180.0D) * (double) radius);
		}

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void drawRectStatic(int leftX, int leftY, int rightX, int rightY, Color color) {
		Gui.drawRect(leftX, leftY, rightX, rightY, color.getRGB());
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		} else {
			double diffX = entity.posX - Zamorozka.player().posX;
			double diffY;

			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
				diffY = entityLivingBase.posY + (double) entityLivingBase.getEyeHeight() * 0.9D
						- (Zamorozka.player().posY + (double) Zamorozka.player().getEyeHeight());
			} else {
				diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
						- (Zamorozka.player().posY + (double) Zamorozka.player().getEyeHeight());
			}

			double diffZ = entity.posZ - Zamorozka.player().posZ;
			double dist = (double) MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
			float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
			float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / Math.PI));
			return new float[] {
					Zamorozka.player().rotationYaw + MathHelper.wrapDegrees(yaw - Zamorozka.player().rotationYaw),
					Zamorozka.player().rotationPitch
							+ MathHelper.wrapDegrees(pitch - Zamorozka.player().rotationPitch) };
		}
	}

	public static void drawEntityESP5(Entity entity, Color c) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15F);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawColorBox(
				new AxisAlignedBB(
						entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
						entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
						entity.boundingBox.minZ - 0.07 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
						entity.boundingBox.maxX + 0.07 - entity.posX + (entity.posX - renderManager.renderPosX),
						entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
						entity.boundingBox.maxZ + 0.07 - entity.posZ + (entity.posZ - renderManager.renderPosZ)),
				0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 255);
		/*drawSelectionBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.07 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.07 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.07 - entity.posZ + (entity.posZ - renderManager.renderPosZ)));*/
		GL11.glLineWidth(0.5F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawBorderedRect12(double x, double y, double x2, double y2, float l1, int col1, int col2) {
		drawRect((int) x, (int) y, (int) x2, (int) y2, col2);

		float f = (float) (col1 >> 24 & 0xFF) / 255F;
		float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
		float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
		float f3 = (float) (col1 & 0xFF) / 255F;

		// GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
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

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawEntityESP1(Entity entity, Color c) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15F);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawColorBox(
				new AxisAlignedBB(
						entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
						entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
						entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
						entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
						entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
						entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ)),
				0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ)));
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
		final Sphere s = new Sphere();
		GL11.glPushMatrix();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.2F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		s.setDrawStyle(GLU.GLU_SILHOUETTE);
		GL11.glTranslated(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY,
				z - mc.getRenderManager().renderPosZ);
		s.draw(size, slices, stacks);
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP2(double x, double y, double z, double width, double height, float red, float green,
			float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtils.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		RenderUtils
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
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

	public static void drawBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd,
			int paramColor) {
		float alpha = (float) (paramColor >> 24 & 0xFF) / 255F;
		float red = (float) (paramColor >> 16 & 0xFF) / 255F;
		float green = (float) (paramColor >> 8 & 0xFF) / 255F;
		float blue = (float) (paramColor & 0xFF) / 255F;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}

	public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
		drawRect((float) x, (float) y, (float) x2, (float) y2, col2);

		float f = (float) (col1 >> 24 & 0xFF) / 255F;
		float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
		float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
		float f3 = (float) (col1 & 0xFF) / 255F;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}

	public static ResourceLocation drawPic(double x, double y, double h, double w, ResourceLocation pic) {
		GlStateManager.enableAlpha();
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(pic);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		vertexbuffer.pos(x, y + w, 0.0D).tex(0.0D, 1.0D).color(255, 255, 255, 255).endVertex();
		vertexbuffer.pos(x + h, y + w, 0.0D).tex(1.0D, 1.0D).color(255, 255, 255, 255).endVertex();
		vertexbuffer.pos(x + h, y, 0.0D).tex(1.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		vertexbuffer.pos(x, y, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();
		GlStateManager.disableAlpha();
		return pic;
	}

	public static void drawSolidBox(AxisAlignedBB bb) {
		GL11.glBegin(7);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

		GL11.glEnd();
	}

	public static void drawOutlinedBox(AxisAlignedBB bb) {

		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		}
		GL11.glEnd();
	}

	public static void tracerLineF(Entity entity, int mode) {
		if (mode == 0) {
			tracerLine(entity, 1.0F - Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F,
					Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F, 0.0D, 0.5D);
		} else if (mode == 1) {
			tracerLine(entity, 0.0D, 0.0D, 1.0D, 0.5D);
		} else if (mode == 2) {
			tracerLine(entity, 1.0D, 1.0D, 0.0D, 0.5D);
		} else if (mode == 3) {
			tracerLine(entity, 1.0D, 0.0D, 0.0D, 0.5D);
		} else if (mode == 4) {
			tracerLine(entity, 0.0D, 1.0D, 0.0D, 0.5D);
		}
	}

	public static void disableRender3D(boolean enableDepth) {
		if (enableDepth) {
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void tracerLine(Entity entity, double red, double green, double blue, double alpha) {
		double var10000 = entity.posX;
		Minecraft.getMinecraft().getRenderManager();
		double d0 = var10000 - RenderManager.renderPosX;
		var10000 = entity.posY + (double) (entity.height / 2.0F);
		Minecraft.getMinecraft().getRenderManager();
		double d1 = var10000 - RenderManager.renderPosY;
		var10000 = entity.posZ;
		Minecraft.getMinecraft().getRenderManager();
		double d2 = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(red, green, blue, alpha);
		Vec3d vec3d = (new Vec3d(0.0D, 0.0D, 1.0D))
				.rotatePitch(-((float) Math.toRadians((double) Minecraft.getMinecraft().player.rotationPitch)))
				.rotateYaw(-((float) Math.toRadians((double) Minecraft.getMinecraft().player.rotationYaw)));
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(vec3d.xCoord, (double) Minecraft.getMinecraft().player.getEyeHeight() + vec3d.yCoord,
				vec3d.zCoord);
		GL11.glVertex3d(d0, d1, d2);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void blockESPBox(BlockPos blockPos, AxisAlignedBB bb, int color) {

		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var12 = (color >> 16 & 0xFF) / 255.0F;
		float var13 = (color >> 8 & 0xFF) / 255.0F;
		float var14 = (color & 0xFF) / 255.0F;

		GL11.glLineWidth(2.0F);

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GlStateManager.color(var12, var13, var14, var11);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 0, 0, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawboxESP5(BlockPos blockPos, Color c) {
		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.25F);

		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 0F, 0F, 0F, 0F);

		GL11.glColor4d(0.4f, 0.6f, 1.0f, 1.0f);
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void blockESPBox2(BlockPos blockPos, AxisAlignedBB bb, int color) {

		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var12 = (color >> 16 & 0xFF) / 255.0F;
		float var13 = (color >> 8 & 0xFF) / 255.0F;
		float var14 = (color & 0xFF) / 255.0F;
		GlStateManager.color(var12, var13, var14, var11);
		GL11.glLineWidth(2.0F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4d(0.4, 0.4, 0.9, 0.5);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(0.4, 0.4, 0.9, 0.5);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 0.4f, 0.6f,
				1.0f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void EspBed(BlockPos blockPos) {
		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);

		RenderGlobal.renderFilledBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 0.6F, z + 1.0F), 0.9f, 0.4f, 1.4f, 1.5f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void VHChest(BlockPos blockPos) {
		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
	}

	public static void drawLines(AxisAlignedBB bb, int color) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var12 = (color >> 16 & 0xFF) / 255.0F;
		float var13 = (color >> 8 & 0xFF) / 255.0F;
		float var14 = (color & 0xFF) / 255.0F;
		GlStateManager.color(var12, var13, var14, var11);
		GL11.glLineWidth(2.0F);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

	}

	public static void EspSpawner(BlockPos blockPos) {
		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 34f, 255f, 5f,
				1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void EspMobs(BlockPos blockPos) {
		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(3.0F);
		GL11.glColor3f(0.0f, 0.99f, 0.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor3f(0.0f, 0.99f, 0.0f);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 0.0f, 1.0f,
				1.0f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void EspShalk(BlockPos blockPos) {

	}

	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green,
			float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(1.0D, 0.0D, 0.0D, 0.5D);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 1.0F, 0.0F,
				0.0F, 0.8F);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4d(1.0D, 0.0D, 0.0D, 0.5D);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0F, z + 1.0F), 1.0F, 0.0F,
				0.0F, 0.8F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawPlayerNametag(EntityPlayer p, double scale, boolean health) {

		RenderManager rm = Minecraft.getMinecraft().renderManager;

		String displayName = p.getName();

		float r = (float) Minecraft.getMinecraft().timer.elapsedTicks;

		double x = p.lastTickPosX - (p.lastTickPosX - p.posX) * r - rm.renderPosX;
		double y = p.lastTickPosY - (p.lastTickPosY - p.posY) * r - rm.renderPosY;
		double z = p.lastTickPosZ - (p.lastTickPosZ - p.posZ) * r - rm.renderPosZ;

		float tagScale = (float) (Zamorozka.player().getDistanceToEntity(p) * scale + 0.02f);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + p.height + 0.5F, z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(rm.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-tagScale, -tagScale, tagScale);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

		int halfNameWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(displayName) / 2;

		Minecraft.getMinecraft().fontRendererObj.drawString(displayName, -halfNameWidth, 0,
				p.isSneaking() ? 0xFFFFFF00 : -1);

		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public static void playervh(double x, double y, double z, double width, double height, float red, float green,
			float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor3f(0, 1, 0);

		GL11.glLineWidth(6F);
		GL11.glColor3f(0, 1, 0);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(0.0D, 0.0D + (double) Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		tessellator.draw();
	}

	public void drawOres(float px, float py, float pz) {
		int bx, by, bz;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glLineWidth(1f);
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder vb = tes.getBuffer();

		List<BlockInfo> temp = new ArrayList();

		for (BlockInfo b : temp) {
			bx = b.x;
			by = b.y;
			bz = b.z;
			float f = 0.0f;
			float f1 = 1.0f;

			vb.pos(bx - px + f, by - py + f1, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f1, bz - pz + f);

			vb.pos(bx - px + f1, by - py + f, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f1, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f, bz - pz + f);
			vb.pos(bx - px + f, by - py + f, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f, bz - pz + f);

			vb.pos(bx - px + f1, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f1, by - py + f, bz - pz + f);
			vb.pos(bx - px + f1, by - py + f1, bz - pz + f);
			vb.pos(bx - px + f, by - py + f, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f1, bz - pz + f1);
			vb.pos(bx - px + f, by - py + f, bz - pz + f);
			vb.pos(bx - px + f, by - py + f1, bz - pz + f);
			tes.draw();
		}

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
	}

	public static void entityESPBox(Entity entity, int mode) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		if (mode == 0)// Enemy
			GL11.glColor4d(1 - Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40, 0, 0.5F);
		else if (mode == 1)// Friend
			GL11.glColor4d(0, 0, 1, 0.5F);
		else if (mode == 2)// Other
			GL11.glColor4d(1, 1, 0, 0.5F);
		else if (mode == 3)// Target
			GL11.glColor4d(1, 0, 0, 0.5F);
		else if (mode == 4)// Team
			GL11.glColor4d(0, 1, 0, 0.5F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawSelectionBoundingBox(
				new AxisAlignedBB(
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
				mode, mode, mode, mode);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void nukerBox(BlockPos blockPos, float damage) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glColor4d(damage, 1 - damage, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(
				new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
						x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2),
				damage, damage, damage, damage);
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal
				.drawSelectionBoundingBox(
						new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
								x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2),
						-1, damage, damage, damage);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, int color) {
		drawLine3D(x1, y1, z1, x2, y2, z2, color);
	}

	public static void drawOutlinedBox(AxisAlignedBB boundingBox, int color) {
		drawOutlinedBox(boundingBox, color, true);
	}

	public static void drawOutlinedBox(AxisAlignedBB boundingBox, int color, boolean disableDepth) {
		if (boundingBox == null) {
			return;
		}
		disableRender3D(disableDepth);

		GL11.glBegin(3);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glEnd();
		GL11.glBegin(3);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glEnd();
		GL11.glBegin(1);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glEnd();

		disableRender3D(disableDepth);
	}

	public static void drawStringWithRect(String string, int x, int y, int colorString, int colorRect, int colorRect2) {
		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		RenderUtils.drawBorderedRect(x - 2, y - 2, x + fontRenderer.getStringWidth(string) + 2, y + 10, 1, colorRect,
				colorRect2);
		fontRenderer.drawString(string, x, y, colorString);
	}

	public static void prepareScissorBox(float x, float y, float x2, float y2) {
		ScaledResolution scale = new ScaledResolution(mc);
		int factor = scale.getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor),
				(int) ((y2 - y) * factor));
	}

	public static void drawRect(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(left, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, top, 0.0D).endVertex();
		vertexbuffer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static boolean isInViewFrustrum(Entity entity) {
		return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
	}

	private static boolean isInViewFrustrum(AxisAlignedBB bb) {
		Entity current = mc.getRenderViewEntity();
		frustrum.setPosition(current.posX, current.posY, current.posZ);
		return frustrum.isBoundingBoxInFrustum(bb);
	}

	public static double interpolate(double current, double old, double scale) {
		return old + (current - old) * scale;
	}

	public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor,
			int borderColor) {
		drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
		drawRect(x + width, y, x1 - width, y + width, borderColor);
		drawRect(x, y, x + width, y1, borderColor);
		drawRect(x1 - width, y, x1, y1, borderColor);
		drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
	}

	public static void drawNode(AxisAlignedBB bb) {
		double midX = (bb.minX + bb.maxX) / 2;
		double midY = (bb.minY + bb.maxY) / 2;
		double midZ = (bb.minZ + bb.maxZ) / 2;

		GL11.glVertex3d(midX, midY, bb.maxZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(bb.minX, midY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, midY, bb.minZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(bb.maxX, midY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);
	}

	public static void prepareGL() {
		GL11.glBlendFunc(770, 771);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(1.5F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
	}

	public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
		return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ))
				.add(getInterpolatedAmount(entity, ticks));
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y,
				(entity.posZ - entity.lastTickPosZ) * z);
	}

	public static void drawLine(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();

		minX -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		minY -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		minZ -= Minecraft.getMinecraft().getRenderManager().renderPosZ;

		maxX -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		maxY -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		maxZ -= Minecraft.getMinecraft().getRenderManager().renderPosZ;

		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(3.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		// drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(1, 1, 1, 0.5F);

		worldrenderer.begin(1, DefaultVertexFormats.POSITION);
		worldrenderer.pos(minX, minY, minZ).endVertex();
		worldrenderer.pos(maxX, maxY, maxZ).endVertex();
		tessellator.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);

	}

    public static int getColorFromPercentage(float current, float max) {
        float percentage = (current / max) / 3;
        return Color.HSBtoRGB(percentage, 1.0F, 1.0F);
    }

}