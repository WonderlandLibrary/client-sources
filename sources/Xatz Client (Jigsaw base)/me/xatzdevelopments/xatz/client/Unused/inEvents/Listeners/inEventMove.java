// 
// Decompiled by Procyon v0.5.36
// 

package me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners;

import me.xatzdevelopments.xatz.client.Unused.inEvents.inEvent;

public class inEventMove extends inEvent<inEventMove>
{
    public boolean isCancelled;
    
    public inEventMove() {
        this.isCancelled = false;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
