// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.event.type;

public class Cancellable implements ICancellable
{
    private boolean cancelled;
    
    @Override
    public void cancel() {
        this.cancelled = true;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
