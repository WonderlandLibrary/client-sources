// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events;

public class CancellableEvent implements Event
{
    private boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public void setCancelled() {
        this.cancelled = true;
    }
}
