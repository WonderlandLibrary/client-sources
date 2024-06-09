// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.andrewsnetwork.icarus.event.Event;

public class ReachDistance extends Event
{
    private float reach;
    
    public ReachDistance(final float reach) {
        this.reach = reach;
    }
    
    public float getReach() {
        return this.reach;
    }
    
    public void setReach(final float reach) {
        this.reach = reach;
    }
}
