package me.xatzdevelopments.command.commands;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command {

	public Say() {
		super("Say", "Says shit lol", "say <message>", "s");
	}

	@Override
	public void onCommand(String[] args, String command) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
	}
	
}
