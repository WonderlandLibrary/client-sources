package net.minecraft.client.main.neptune.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
	public static void sendMessageToPlayer(String msg) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§7Occult§8]:§r " + msg));
	}

	public static void sendMessageFromPlayer(String msg) {
		Minecraft.getMinecraft().thePlayer.sendChatMessage(msg);
	}
}