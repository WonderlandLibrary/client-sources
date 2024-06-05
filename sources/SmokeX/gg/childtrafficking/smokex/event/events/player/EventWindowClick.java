// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.player;

import gg.childtrafficking.smokex.event.Event;

public final class EventWindowClick extends Event
{
    private final int windowId;
    private final int slot;
    private final int mouseButton;
    private final int mode;
    
    public EventWindowClick(final int windowId, final int slot, final int mouseButton, final int mode) {
        this.windowId = windowId;
        this.slot = slot;
        this.mouseButton = mouseButton;
        this.mode = mode;
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public int getMouseButton() {
        return this.mouseButton;
    }
    
    public int getMode() {
        return this.mode;
    }
}
