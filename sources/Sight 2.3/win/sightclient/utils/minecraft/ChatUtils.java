package win.sightclient.utils.minecraft;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils {

	public static void sendMessage(String message) {
		sendMessage(message, null);
	}
	
	public static void sendMessage(String message, ChatStyle cs) {
		StringBuilder sb = new StringBuilder(EnumChatFormatting.GOLD + "[Sight] " + EnumChatFormatting.GRAY);
		String[] args = message.split(" ");
		
		for (String str : args) {
			sb.append(str);
			sb.append(" ");
			if (ThreadLocalRandom.current().nextBoolean()) {
				sb.append(EnumChatFormatting.GRAY);
			}
		}
		
		ChatComponentText cct = new ChatComponentText(sb.toString());
		if (cs != null) {
			cct.setChatStyle(cs);
		}
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(cct);
	}
	
}
