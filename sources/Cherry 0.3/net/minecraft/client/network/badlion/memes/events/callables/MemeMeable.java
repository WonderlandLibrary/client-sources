// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.memes.events.callables;

import net.minecraft.client.network.badlion.memes.events.EventCancelable;
import net.minecraft.client.network.badlion.memes.events.Event;

public abstract class MemeMeable implements Event, EventCancelable
{
    private boolean cancelled;
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
