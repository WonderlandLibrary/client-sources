// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.andrewsnetwork.icarus.event.Event;

public class MouseClicked extends Event
{
    private int button;
    
    public MouseClicked(final int button) {
        this.button = button;
    }
    
    public int getButton() {
        return this.button;
    }
}
