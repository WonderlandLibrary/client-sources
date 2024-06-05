// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.player;

import gg.childtrafficking.smokex.event.Event;

public class EventSendMessage extends Event
{
    private String message;
    
    public EventSendMessage(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
