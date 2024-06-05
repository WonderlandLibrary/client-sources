package win.sightclient.event.events.chat;

import net.minecraft.util.IChatComponent;
import win.sightclient.event.Event;

public class EventChatReceive extends Event {

    private IChatComponent chat;
    
	public EventChatReceive(IChatComponent chat) {
		this.chat = chat;
	}
	
	public IChatComponent getMessage() {
		return chat;
	}
	
	public void setChat(IChatComponent chat) {
		this.chat = chat;
	}
	
}
