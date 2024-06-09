package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.command.CommandManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketSendEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mod(enabled = true, shown = false)
public class Commands extends Module {
	public Commands() {
		this.setProperties("Commands", "Commands", Module.Category.Misc, 0, "", false);
	}

	@EventTarget
	private void onPacketSend(PacketSendEvent event) {
		if ((event.getPacket() instanceof C01PacketChatMessage)) {
			C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
			String message = packet.getMessage();
			if (message.startsWith(".")) {
				event.setCancelled(true);
				String[] args = message.split(" ");
				Command commandFromMessage = CommandManager.parseMessage(message);
				commandFromMessage.runCommand(args);
			}
		}
	}
}
