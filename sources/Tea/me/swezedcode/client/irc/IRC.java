package me.swezedcode.client.irc;

import me.swezedcode.client.Tea;
import net.minecraft.client.Minecraft;

public class IRC {
	public static void onStartup() {
		Tea.setBot(getUsername());
		new Thread(Tea.getBot()).start();
	}

	public static String getUsername() {
		return Minecraft.getMinecraft().session.getUsername();
	}
}
