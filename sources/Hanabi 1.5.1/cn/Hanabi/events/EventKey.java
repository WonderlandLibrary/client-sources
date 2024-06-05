package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;

public class EventKey implements Event
{
    private int key;
    
    public EventKey(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
