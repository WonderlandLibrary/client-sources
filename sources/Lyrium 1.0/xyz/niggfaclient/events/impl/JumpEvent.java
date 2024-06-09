// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.CancellableEvent;

public class JumpEvent extends CancellableEvent
{
    private double height;
    
    public JumpEvent(final double height) {
        this.height = height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    public double getHeight() {
        return this.height;
    }
}
