// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event;

public class Event
{
    private boolean cancelled;
    
    public void cancel() {
        this.cancelled = true;
    }
    
    public boolean cancelled() {
        return this.cancelled;
    }
}
