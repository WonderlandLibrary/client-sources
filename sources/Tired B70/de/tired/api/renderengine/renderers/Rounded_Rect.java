package de.tired.api.renderengine.renderers;

import de.tired.api.extension.Extension;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Rounded_Rect {

	public static void drawRoundedRect(int x, int y, int x1, int y1, int cr, int color) {

		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((double)(x + cr), (double)(y + cr), (double)cr, color);
		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((double)(x + cr), (double)(y1 - cr), (double)cr, color);
		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((double)(x1 - cr), (double)(y + cr), (double)cr, color);
		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((double)(x1 - cr), (double)(y1 - cr), (double)cr, color);

		Gui.drawRect(x, y + cr, x1, y1 - cr, color);
		Gui.drawRect(x + cr, y, x1 - cr, y1, color);
	}

	public static void drawFullCircle(double cx, double cy, double r, int c) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		r *= 2.0D;
		cx *= 2.0D;
		cy *= 2.0D;
		float f = (float)(c >> 24 & 255) / 255.0F;
		float f1 = (float)(c >> 16 & 255) / 255.0F;
		float f2 = (float)(c >> 8 & 255) / 255.0F;
		float f3 = (float)(c & 255) / 255.0F;
		boolean blend = GL11.glIsEnabled(3042);
		boolean texture2d = GL11.glIsEnabled(3553);
		boolean line = GL11.glIsEnabled(2848);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(6);

		for(int i = 0; i <= 360; ++i) {
			double x = Math.sin((double)i * 3.141592653589793D / 180.0D) * r;
			double y = Math.cos((double)i * 3.141592653589793D / 180.0D) * r;
			GL11.glVertex2d(cx + x, cy + y);
		}

		GL11.glEnd();
		f = (float)(c >> 24 & 255) / 255.0F;
		f1 = (float)(c >> 16 & 255) / 255.0F;
		f2 = (float)(c >> 8 & 255) / 255.0F;
		f3 = (float)(c & 255) / 255.0F;
		GL11.glColor4f(f1, f2, f3, f);
		if (!line) {
			GL11.glDisable(2848);
		}

		if (texture2d) {
			GL11.glEnable(3553);
		}

		if (!blend) {
			GL11.glDisable(3042);
		}

		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glPopMatrix();
	}



}
