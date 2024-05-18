package me.valk.event.events.screen;

import me.valk.event.Event;

public class EventRenderChatMessage extends Event {

	private String message;

	public EventRenderChatMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}
