package Ascii4UwUWareClient.UI.Notification;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.Color;

import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;

public class Notification {
	private NotificationType type;
	private String title;
	private String messsage;
	private long start;

	private long fadedIn;
	private long fadeOut;
	private long end;

	public Notification(NotificationType type, String title, String messsage, int length) {
		this.type = type;
		this.title = title;
		this.messsage = messsage;

		fadedIn = 200 * length;
		fadeOut = fadedIn + 500 * length;
		end = fadeOut + fadedIn;
	}

	public void show() {
		start = System.currentTimeMillis();
	}

	public boolean isShown() {
		return getTime() <= end;
	}

	private long getTime() {
		return System.currentTimeMillis() - start;
	}

	public void render() {
		CFontRenderer font = FontLoaders.GoogleSans18;
		double offset = 0;
		int width = font.getStringWidth(messsage) < 27 ? 44 : font.getStringWidth(messsage) + 12;
		int height = 24;
		long time = getTime();

		if (time < fadedIn) {
			offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
		} else if (time > fadeOut) {
			offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
		} else {
			offset = width;
		}

		Color color = new Color(0, 0, 0, 217);
		Color color1;

		if (type == NotificationType.INFO)
			color1 = new Color(4, 255, 39);
		else if (type == NotificationType.WARNING)
			color1 = new Color(204, 193, 0);
		else {
			color1 = new Color(204, 0, 18);
		}
		drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5,
				color.getRGB());
		drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width - offset + 4,
				GuiScreen.height - 5, color1.getRGB());

		font.drawString(title, (int) (GuiScreen.width - offset + 8), GuiScreen.height - 26,
				new Color(255, 255, 255).getRGB());
		font.drawString(messsage, (int) (GuiScreen.width - offset + 8), GuiScreen.height - 14,
				new Color(255, 255, 255).getRGB());

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
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos(left, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, top, 0.0D).endVertex();
		worldrenderer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
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
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
		worldrenderer.pos(left, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, top, 0.0D).endVertex();
		worldrenderer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

}
