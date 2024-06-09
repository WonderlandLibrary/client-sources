package me.swezedcode.client.utils.console;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ConsoleUtils {

	public static void writeLine(String msg) {
		System.out.println(msg);
	}

	public static void write(String msg) {
		System.out.print(msg);
	}

	public static void logChat(final String message) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§cTea§7] " + message));
		} else {
			writeLine(message);
		}
	}
	
	public static void logIRC(final String message) {
        if (Minecraft.thePlayer == null) {
        	writeLine(message);
        }
        else {
        	writeLine(message);
            Minecraft.thePlayer.addChatMessage(new ChatComponentText("§7[§cIRC§7] " + message));
        }
    }

}
