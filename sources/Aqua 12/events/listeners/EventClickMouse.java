// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import events.Event;

public class EventClickMouse extends Event<EventClickMouse>
{
    private int slot;
    
    public EventClickMouse(final int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public void setSlot(final int slot) {
        this.slot = slot;
    }
}
