package me.satisfactory.base.events.event;

public interface Cancellable
{
    boolean isCancelled();
    
    void setCancelled(final boolean p0);
}
