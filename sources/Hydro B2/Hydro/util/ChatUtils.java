package Hydro.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils {
	
	public static void sendMessageToPlayer(String message) {
		   Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Hydro" + EnumChatFormatting.GRAY + ": " + EnumChatFormatting.RESET + message));
	}
	
	public static void sendMessage(String message) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
	}

}
