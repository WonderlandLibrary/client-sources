package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.event.Cancellable;

public class PlayerChatEvent extends Cancellable {
	private String chatMessage;
	
	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}
	
	public String getChatMessage() {
		return chatMessage;
	}
}
