// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.andrewsnetwork.icarus.event.Event;

public class RenderIn3D extends Event
{
    float partialTicks;
    int pass;
    
    public RenderIn3D(final float partialTicks, final int pass) {
        this.partialTicks = partialTicks;
        this.pass = pass;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public int getPass() {
        return this.pass;
    }
}
