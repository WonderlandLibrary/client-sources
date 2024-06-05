package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;

public class EventChat implements Event
{
    public String message;
    public boolean cancelled;
    
    public EventChat(final String chat) {
        this.message = chat;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }
}
