package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;

public class EventStep extends Event<EventStep>
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
