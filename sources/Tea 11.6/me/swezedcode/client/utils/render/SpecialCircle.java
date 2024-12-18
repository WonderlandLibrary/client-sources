package me.swezedcode.client.utils.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

public class SpecialCircle {

	private static int rotating = 0;
	private static int circling = 0;
	private static int circling2 = 0;
	private static boolean back = false;
	private static int check = 0;

	public static void circleSpecial(double x, double y, double radius, int color, int color2, int color3) {
		int cc = circling / 5;
		int cc2 = circling2 / 5;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glPushMatrix();
		GL11.glRotated(cc, 0, Integer.MAX_VALUE, 0);
		circle(0, 0, radius, 0xFF000000);
		GL11.glPushMatrix();
		if (Rainbow.enabled) {
			Tea.waterMark.drawStringWithShadow("T", -4.5F, -6F, ColorUtils.getRainbow(1L, 1F).getRGB());
		} else {
			Tea.waterMark.drawStringWithShadow("T", -4.5F, -6F, 0xFFFA6475);
		}
		GL11.glPopMatrix();
		if (Rainbow.enabled) {
			circleOutline(0, 0, radius + 0.2, 0xFF000000);
		} else {
			circleOutline(0, 0, radius + 0.2, color2);
		}
		// check = 0;
		// RenderInGame.fr.drawString(name(), -2.5f, -4, 0xFFFFFFFF, true);

		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glRotated(cc2, 0, Integer.MAX_VALUE, 0);
		GL11.glRotated(++rotating, 0, 0, 1);
		// min(0, 0, radius + 2, color3);
		if (Rainbow.enabled) {
			min(0, 0, radius - 0, ColorUtils.getRainbow(1L, 1F).getRGB());
		} else {
			min(0, 0, radius - 0, 0xFFFA6475);
		}

		/*
		 * GL11.glRotated(rotating, 0, 0, 1); min(0, 0, radius - 1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3);
		 */
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public static void circleSpecial2(double x, double y, double radius, int color, int color2, int color3) {
		++circling2;
		++circling;
		++rotating;
		int cc = circling += 1;
		int cc2 = circling2 += 1;
		if (back) {
			circling = circling - 2;
			if (circling == 0 * 5) {
				if (circling2 == 0 * 5) {
					circling2 = 0;
				}
				back = false;
			}
		}
		// if (circling == 90 * 5) {
		// check = check + 2;
		// if (check > 10) {
		// check = 0;
		// }
		// back = true;
		// }
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glPushMatrix();
		GL11.glRotated(cc, 0, Integer.MAX_VALUE, 0);
		circle(0, 0, radius, color);
		circleOutline(0, 0, radius + 0.2, color2);
		// check = 0;
		GL11.glPushMatrix();
		// GL11.glScaled(1.5, 1.5, 1.0);
		ClientOverlay.fontRenderer.drawStringWithShadow("S", -17F, -20F, 0xFF69D9FF);
		GL11.glPopMatrix();
		// RenderInGame.fr.drawString(name(), -2.5f, -4, 0xFFFFFFFF, true);

		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glRotated(cc2, 0, Integer.MAX_VALUE, 0);
		GL11.glRotated(++rotating, 0, 0, 1);
		// min(0, 0, radius + 2, color3);
		min(0, 0, radius - 10, color3);
		min(0, 0, radius - 5, color3);
		min(0, 0, radius - 0, color3);
		min(0, 0, radius + 1, color3);

		/*
		 * GL11.glRotated(rotating, 0, 0, 1); min(0, 0, radius - 1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3);
		 */
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public static void circleSpecial3(double x, double y, double radius, int color, int color2, int color3) {
		++circling2;
		++circling;
		rotating += 5;
		int cc = circling += 1;
		int cc2 = circling2 += 1;
		if (back) {
			circling = circling - 2;
			if (circling == 0 * 5) {
				if (circling2 == 0 * 5) {
					circling2 = 0;
				}
				back = false;
			}
		}
		// if (circling == 90 * 5) {
		// check = check + 2;
		// if (check > 10) {
		// check = 0;
		// }
		// back = true;
		// }
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glPushMatrix();
		// GL11.glRotated(cc, 0, Integer.MAX_VALUE, 0);
		// circle(0, 0, radius, color);
		// circleOutline(0, 0, radius + 0.2, color2);
		// check = 0;
		GL11.glPushMatrix();
		GL11.glRotated(--rotating, 0, 0, 1);
		// min(0, 0, radius + 2, color3);
		min(0, 0, radius - 20, color3);
		GL11.glPopMatrix();
		// RenderInGame.fr.drawString(name(), -2.5f, -4, 0xFFFFFFFF, true);

		GL11.glPopMatrix();
		GL11.glPushMatrix();
		// GL11.glRotated(cc2, 0, Integer.MAX_VALUE, 0);
		GL11.glRotated(++rotating, 0, 0, 1);
		// min(0, 0, radius + 2, color3);
		min(0, 0, radius - 10, color3);
		min(0, 0, radius - 5, color3);
		min(0, 0, radius - 0, color3);
		min(0, 0, radius + 1, color3);

		/*
		 * GL11.glRotated(rotating, 0, 0, 1); min(0, 0, radius - 1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius - 0.1, color3); min(0, 0, radius - 0.1, color3);
		 * min(0, 0, radius, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3); min(0, 0,
		 * radius + 0.1, color3); min(0, 0, radius + 0.1, color3);
		 */
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private static String name;

	private static String name() {
		if (check == 0) {
			name = "T";
		} else if (check == 2) {
			name = "E";
		} else if (check == 4) {
			name = "A";
		}
		return name;
	}

	private static void min(double x, double y, double radius, int color) {
		float red = (color >> 24 & 0xFF) / 255.0f;
		float green = (color >> 16 & 0xFF) / 255.0f;
		float blue = (color >> 8 & 0xFF) / 255.0f;
		float alpha = (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		// VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.color(green, blue, alpha, red);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glBegin(3);
		for (int i = 0; i <= 100; ++i) {
			final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
			final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
			GL11.glVertex2d(x + x2, y + y2);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glDisable(2881);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	private static void circle(double x, double y, double radius, int color) {
		float red = (color >> 24 & 0xFF) / 255.0f;
		float green = (color >> 16 & 0xFF) / 255.0f;
		float blue = (color >> 8 & 0xFF) / 255.0f;
		float alpha = (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		// VertexBuffer vertexbuffer = tessellator.getWorldRenderer()();
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.color(green, blue, alpha, red);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glBegin(6);
		for (int i = 0; i <= 360; ++i) {
			final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
			final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
			GL11.glVertex2d(x + x2, y + y2);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glDisable(2881);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void circleOutline(double x, double y, double radius, int color) {
		float red = (color >> 24 & 0xFF) / 255.0f;
		float green = (color >> 16 & 0xFF) / 255.0f;
		float blue = (color >> 8 & 0xFF) / 255.0f;
		float alpha = (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.color(green, blue, alpha, red);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glBegin(3);
		for (int i = 0; i <= 360; ++i) {
			final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
			final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
			GL11.glVertex2d(x + x2, y + y2);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glDisable(2881);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void circleOutline2(double x, double y, double radius, int color) {
		float red = (color >> 24 & 0xFF) / 255.0f;
		float green = (color >> 16 & 0xFF) / 255.0f;
		float blue = (color >> 8 & 0xFF) / 255.0f;
		float alpha = (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.color(green, blue, alpha, red);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glBegin(3);
		for (int i = 0; i <= 360; ++i) {
			final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
			final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
			GL11.glVertex2d(x + x2 * 5, y + y2 + 10);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glDisable(2881);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	private static int randomDelay() {
		Random randy = new Random();
		int randyInt = randy.nextInt(15);
		return randyInt;
	}

}
