package win.sightclient.event.events.chat;

import win.sightclient.Sight;
import win.sightclient.event.Event;

public class EventChatSend extends Event {

    private String chat;
    
    @Override
    public void call() {
    	Sight.instance.cm.onChat(this);
    	super.call();
    }
    
	public EventChatSend(String chat) {
		this.chat = chat;
	}
	
	public String getMessage() {
		return chat;
	}
	
	public void setChat(String chat) {
		this.chat = chat;
	}
	
}
