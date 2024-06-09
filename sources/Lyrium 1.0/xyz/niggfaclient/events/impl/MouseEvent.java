// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.Event;

public class MouseEvent implements Event
{
    private int button;
    
    public MouseEvent(final int button) {
        this.button = button;
    }
    
    public int getButton() {
        return this.button;
    }
    
    public void setButton(final int button) {
        this.button = button;
    }
}
