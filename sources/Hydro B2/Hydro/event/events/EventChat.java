package Hydro.event.events;

import Hydro.event.Event;
import net.minecraft.util.IChatComponent;

public class EventChat extends Event<EventChat> {

    private IChatComponent chat;
	private boolean incoming;
    
	public EventChat(IChatComponent chat) {
		this.chat = chat;
	}
	
	public IChatComponent getChatComponent() {
		return chat;
	}
	
	public void setChat(IChatComponent chat) {
		this.chat = chat;
	}
	
}
