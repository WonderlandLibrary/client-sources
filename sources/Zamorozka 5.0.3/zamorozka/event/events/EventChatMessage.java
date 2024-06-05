package zamorozka.event.events;

import zamorozka.event.Event;

public class EventChatMessage extends Event {
	public String message;
	public boolean cancelled;
	
	public EventChatMessage(String chat) {
		message = chat;
	}

	public String getMessage() {
		return message;
	}
	
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}
}