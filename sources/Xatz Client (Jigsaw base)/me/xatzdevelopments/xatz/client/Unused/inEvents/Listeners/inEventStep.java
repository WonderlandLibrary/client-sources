// 
// Decompiled by Procyon v0.5.36
// 

package me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners;

import me.xatzdevelopments.xatz.client.Unused.inEvents.inEvent;

public class inEventStep extends inEvent<inEventStep>
{
    private boolean isCancelled;
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.isCancelled = cancelled;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
