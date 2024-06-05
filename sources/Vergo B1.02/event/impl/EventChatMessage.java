package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;

public class EventChatMessage extends Event {
	
	public String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
