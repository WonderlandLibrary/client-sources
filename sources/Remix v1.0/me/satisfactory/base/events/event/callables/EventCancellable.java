package me.satisfactory.base.events.event.callables;

import me.satisfactory.base.events.event.*;

public abstract class EventCancellable implements Event, Cancellable
{
    private boolean cancelled;
    
    protected EventCancellable() {
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
