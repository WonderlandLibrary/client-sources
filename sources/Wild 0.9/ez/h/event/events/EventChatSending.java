package ez.h.event.events;

import ez.h.event.*;

public class EventChatSending extends Event
{
    public String msg;
    
    public EventChatSending(final String msg) {
        this.msg = msg;
    }
}
