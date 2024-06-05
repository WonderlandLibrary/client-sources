package win.sightclient.notification;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import win.sightclient.Sight;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.render.NotifModule;
import win.sightclient.utils.minecraft.ChatUtils;

public class NotificationRender {

	private NotifModule nm;
	
	public NotificationRender() {
		nm = (NotifModule) Sight.instance.mm.getModuleByName("Notifications");
	}
	
	public void onRender() {
		ArrayList<Notification> notifs = Sight.instance.nm.getNotifications();
		if (notifs == null || notifs.isEmpty()) {
			return;
		}
		float yPos = -40;
		for (int k = 0; k < notifs.size(); k++) {
			Notification n = notifs.get(k);
			if (n.isFinished() || !nm.isToggled()) {
				Sight.instance.nm.delete(notifs.indexOf(n));
				if (!nm.isToggled()) {
					ChatUtils.sendMessage(n.getDescription());
				}
			} else {
				float duration = (float)((float)n.getLifeTime() / (float)n.getDuration());
				float fadeIn = 0.15f;
				float fadeOut = 0.9f;
				
				ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
				float drawX = 0;
				TTFFontRenderer font = Sight.instance.fm.getFont("XD");
				float width = Math.max(Math.max(font.getStringWidth(n.getTitle()), font.getStringWidth(n.getDescription())), 70);
				
				if (duration < fadeIn) {
					drawX = Math.min(1, duration / fadeIn);
				} else if (duration >= fadeIn && duration < fadeOut) {
					drawX = 1;
				} else if (duration >= fadeOut) {
					float percentage = (duration - fadeOut) / (1 - fadeOut);
					drawX = 1 - percentage;
				}
				int height = 25;
				float drawXPosition = sr.getScaledWidth() - (width * drawX) - 3;
				Gui.drawRect(drawXPosition, sr.getScaledHeight() + yPos, drawXPosition + width, sr.getScaledHeight() + yPos + height, new Color(0, 0, 0, 100).getRGB());
				font.drawStringWithShadow(EnumChatFormatting.BOLD + n.getTitle(), drawXPosition + 1, sr.getScaledHeight() + yPos + 2, -1);
				font.drawStringWithShadow(n.getDescription(), drawXPosition + 2, sr.getScaledHeight() + yPos + 4 + font.getHeight(n.getTitle()), -1);
				Gui.drawRect(drawXPosition, sr.getScaledHeight() + yPos + height - 2, drawXPosition + (width * (1 - duration)), sr.getScaledHeight() + yPos + height, new Color(255, 255, 255, 100).getRGB());
				yPos -= 35;
			}
		}
	}
}
