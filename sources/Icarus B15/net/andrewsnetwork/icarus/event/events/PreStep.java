// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.andrewsnetwork.icarus.event.Event;

public class PreStep extends Event
{
    public double stepHeight;
    public boolean bypass;
    
    public PreStep(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
}
