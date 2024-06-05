package me.satisfactory.base.events;

import me.satisfactory.base.events.event.*;

public class EventMouse implements Event
{
    private int key;
    
    public EventMouse(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
}
