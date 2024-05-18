// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.memes.events;

public abstract class EventStop implements Event
{
    private boolean stopped;
    
    public void stop() {
        this.stopped = true;
    }
    
    public boolean isStopped() {
        return this.stopped;
    }
}
