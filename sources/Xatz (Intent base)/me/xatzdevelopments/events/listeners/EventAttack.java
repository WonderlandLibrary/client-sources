package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;
import net.minecraft.entity.Entity;

public class EventAttack extends Event<EventAttack>
{
    private boolean isCancelled;
    public Entity entity;
    
    public EventAttack(final Entity e) {
        this.entity = e;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
