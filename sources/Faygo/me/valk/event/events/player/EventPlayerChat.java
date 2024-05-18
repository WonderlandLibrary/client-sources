package me.valk.event.events.player;

import me.valk.event.Event;

public class EventPlayerChat extends Event {

	private String message;

	public EventPlayerChat(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}
