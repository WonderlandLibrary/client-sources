package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.callables.MemeMeable;

public class EventChatSend extends MemeMeable {

	public String message;

	public EventChatSend(String message) {
		this.message = message;
	}

}
