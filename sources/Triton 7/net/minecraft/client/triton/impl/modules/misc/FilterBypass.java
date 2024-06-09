package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketSendEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mod
public class FilterBypass extends Module {

	public FilterBypass() {
		this.setProperties("FilterBypass", "FilterBypass", Module.Category.Misc, 0, "", true);
	}

	@EventTarget
	public void onPacketSend(PacketSendEvent event) {
		if ((event.getPacket() instanceof C01PacketChatMessage)) {
			C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
			String message = packet.getMessage();
			if (message.contains("fuck")) {
				packet.setMessage(message.replace("fuck", "\uFF46\uFF55\uFF43\uFF4B"));
			} else if (message.contains("faggot")) {
				packet.setMessage(message.replace("faggot", "\uFF46\uFF41\uFF47\uFF47\uFF4F\uFF54"));
			} else if (message.contains("nigger")) {
				packet.setMessage(message.replace("nigger", "\uFF4E\uFF49\uFF47\uFF47\uFF45\uFF52"));
			}
		}
	}
}
