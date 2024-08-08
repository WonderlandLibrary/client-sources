package me.xatzdevelopments.xatz.client.events;


public abstract class levent {
    protected boolean cancelled;

    public void fire() {
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
