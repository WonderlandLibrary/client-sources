package me.swezedcode.client.command;

import java.util.List;

import me.swezedcode.client.Tea;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public abstract class Command {

	public Minecraft mc;

	public Command() {
		mc = Minecraft.getMinecraft();
	}

	public abstract void executeMsg(String[] args);

	public abstract String getName();

	public void msg(String msg) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText("§7[§c" + Tea.getInstance().getName() + "§7]§7 " + msg));
	}
	
	public void error(String msg) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText("§7[§c" + Tea.getInstance().getName() + "§7]§c " + msg));
	}

}
