// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.Event;

public class Render3DEvent implements Event
{
    private final float partialTicks;
    
    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
