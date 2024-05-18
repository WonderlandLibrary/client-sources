// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.network.badlion.memes.events.EventCancelable;

public class EventPushOut implements EventCancelable
{
    private boolean cancelled;
    
    public EventPushOut() {
        this.cancelled = false;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
