package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;

public class EventUsingItem extends Event<EventUsingItem>
{
    private boolean isCancelled;
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean canceled) {
        this.isCancelled = canceled;
    }
}
