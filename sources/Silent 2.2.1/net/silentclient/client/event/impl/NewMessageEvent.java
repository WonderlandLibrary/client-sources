package net.silentclient.client.event.impl;

import net.minecraft.util.IChatComponent;
import net.silentclient.client.event.EventCancelable;

public class NewMessageEvent extends EventCancelable {
	private final IChatComponent message;
	
	public NewMessageEvent(IChatComponent message) {
		this.message = message;
	}
	
	public IChatComponent getMessage() {
		return message;
	}
}
