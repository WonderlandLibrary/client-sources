package rip.helium;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ClientBase {;
	
	
	
	public static void chat(String msg) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText("§7[§aHelium§7]§f " + msg));
	}

}
