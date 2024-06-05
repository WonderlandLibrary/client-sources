package me.rich.notifications;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;

import me.rich.Main;
import me.rich.font.CFontRenderer;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.helpers.render.Shifting;

public final class NotificationPublisher {
	private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<Notification>();

	public static void publish(ScaledResolution sr) {
		int srScaledHeight = sr.getScaledHeight();
		int scaledWidth = sr.getScaledWidth();
		int y = srScaledHeight - 35;
		for (Notification notification : NOTIFICATIONS) {
			Shifting translate = notification.getTranslate();
			int width = notification.getWidth();
			if (!notification.getTimer().elapsed(notification.getTime())) {
				notification.scissorBoxWidth = AnimationHelper.animate(width, notification.scissorBoxWidth,
						0.1 * Minecraft.getSystemTime());
				translate.interpolate(scaledWidth - width, y, 0.045);
			} else {
				notification.scissorBoxWidth = AnimationHelper.animate(0.0, notification.scissorBoxWidth, 0.1 * Minecraft.getSystemTime());
				if (notification.scissorBoxWidth < 1.0) {
					NOTIFICATIONS.remove(notification);
				}
				y += 30;
			}
			float translateX = (float) translate.getX();
			float translateY = (float) translate.getY();
			GL11.glPushMatrix();
			GL11.glEnable(3089);
			int count = 0;
			int yTotal = 0;
			NotificationPublisher.prepareScissorBox((float) ((double) scaledWidth - notification.scissorBoxWidth - 55),
					translateY, scaledWidth, translateY + 30.0f);
			// RenderUtil2.drawBorderedRect(translateX, translateY, scaledWidth, translateY
			// + 30.0f, 0.5D, (new Color(40, 40, 40, 255)).getRGB(), (new Color(60, 60, 60,
			// 255)).getRGB(), true);
			// RenderUtil2.drawBorderedRect(translateX, translateY + 30.0f - 2.0f,
			// scaledWidth, translateY + 30.0f, 0.5D, (new Color(10, 10, 10, 255)).getRGB(),
			// (new Color(60, 60, 60, 255)).getRGB(), true);
			// RenderUtil2.drawBorderedRect(translateX, translateY + 29.0f + 1.0f,
			// translateX + (float)((long)width * ((long)notification.getTime() -
			// notification.getTimer().getElapsedTime()) / (long)notification.getTime()),
			// translateY + 29.0f, 0.5D, ColorUtils.astolfoColors((int)count, yTotal), (new
			// Color(60, 60, 60, 255)).getRGB(), true);

			// RenderUtil2.drawNewRect(translateX, translateY, scaledWidth, translateY +
			// 30.0f, new Color(10, 10, 10, 210).getRGB());
			// RenderUtil2.drawNewRect(translateX, translateY + 30.0f - 2.0f, scaledWidth,
			// translateY + 30.0f, new Color(10, 10, 10, 255).getRGB());
			RenderHelper.drawRoundedRect1(translateX - 20, translateY + 17, scaledWidth - 5, translateY + 28.0f, (new Color(24, 24, 24, 255)).getRGB(), (new Color(24, 24, 24, 255)).getRGB());
			RenderHelper.drawRect(translateX - 22, translateY + 17, scaledWidth - 120f, translateY + 28.0f, Main.getClientColor().getRGB());
			// RenderUtil2.drawNewRect(translateX, translateY + 29.0f + 1.0f, translateX +
			// (float)((long)width * ((long)notification.getTime() -
			// notification.getTimer().getElapsedTime()) / (long)notification.getTime()),
			// translateY + 28.0f, ColorUtils.astolfoColors((int)count, yTotal));
			Fonts.roboto_14.drawStringWithShadow(notification.getTitle() + " " + notification.getContent(), translateX - 8, translateY + 21.5f, -1);
			Fonts.elegant_16.drawStringWithShadow("N", translateX - 18.0f, translateY + 21.5f, -1);
			Fonts.stylesicons_16.drawStringWithShadow("M", translateX + 85.0f, translateY + 21.5f, -1);
			GL11.glDisable(3089);
			GL11.glPopMatrix();
			y -= 5;
			y -= 10;
		}
	}

	public static void prepareScissorBox(float x, float y, float x2, float y2) {
		ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
		int factor = scale.getScaleFactor();
		GL11.glScissor((int) (x * (float) factor), (int) (((float) scale.getScaledHeight() - y2) * (float) factor),
				(int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
	}

	public static void queue(String title, String content, NotificationType type) {
		Minecraft mc = Minecraft.getMinecraft();
		CFontRenderer fr = Fonts.roboto_16;
		NOTIFICATIONS.add(new Notification(title, content, type, Fonts.roboto_16));
	}
}
