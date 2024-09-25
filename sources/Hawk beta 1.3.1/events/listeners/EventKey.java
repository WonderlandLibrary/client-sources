package eze.events.listeners;

import eze.events.*;

public class EventKey extends Event<EventKey>
{
    public int code;
    
    public EventKey(final int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
}
