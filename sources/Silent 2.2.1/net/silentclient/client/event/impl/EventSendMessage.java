package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class EventSendMessage extends EventCancelable {
	private final String message;
	
	public EventSendMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
