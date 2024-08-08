package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;
import net.minecraft.entity.player.EntityPlayerMP;

public class EventChatmessage extends Event<EventChatmessage> {
	
	public String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
