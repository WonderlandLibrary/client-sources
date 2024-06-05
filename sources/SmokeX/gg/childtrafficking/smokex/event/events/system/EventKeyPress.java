// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.system;

import gg.childtrafficking.smokex.event.Event;

public final class EventKeyPress extends Event
{
    private final int key;
    
    public EventKeyPress(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
