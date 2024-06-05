package me.vaziak.sensation.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft(); // Useless but used to make my life easier
	
	public static void log(String message) {
		mc.thePlayer.addChatComponentMessage(new ChatComponentText(("&7[&cSensation&7] &7" + message).replaceAll("&", "§")));
	}
	
	public static void debug(String message) {
		log("&c[DEBUG] &7" + message);
	}

}
