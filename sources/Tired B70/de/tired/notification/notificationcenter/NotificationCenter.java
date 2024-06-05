package de.tired.notification.notificationcenter;

import de.tired.interfaces.FHook;
import de.tired.tired.Tired;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class NotificationCenter extends AbstractNotificationCenter {

	private double width = 120, height = 30;

	private double x, y;

	private static boolean over;

	@Override
	public void render(int x, int y) {
		this.x = x;
		this.y = y;
		Gui.drawRect(x, y - 4, x + width, y - height, over ? new Color(20, 20, 20).getRGB() : Integer.MIN_VALUE);

		if (over) {
			//Gui.drawRect(x, y - 4, x + width, y - height, Integer.MIN_VALUE);
			Gui.drawRect(x, y - 4, x + width, y - height + 120, Integer.MIN_VALUE);
			FHook.fontRenderer2.drawString(Tired.INSTANCE.CLIENT_NAME, x + 5, y - 19, -1);
			FHook.fontRenderer3.drawString("current build: " + Tired.INSTANCE.VERSION, x + 5, y + 3, -1);

			String ip = "";

			if (Minecraft.getMinecraft().getCurrentServerData().serverIP != null) {
				ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
			} else {
				ip = "Null";
			}
			FHook.fontRenderer3.drawString("server: " + ip, x + 5, y + 12, -1);

		}

	}
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseKey) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int x = sr.getScaledWidth() - 120;
		int y = 34;

		over = isOver(10, 0, 140, (int) height, mouseX, mouseY);

	}

	public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}

}
