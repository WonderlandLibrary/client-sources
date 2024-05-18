package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.entity.Entity;

public class EventNameRender extends EventCancellable
{
    public Entity entity;
    public boolean canceled;
    
    public EventNameRender(final Entity entity) {
        this.canceled = false;
        this.entity = entity;
    }
    
    public void setCanceled(final boolean b) {
        this.canceled = b;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
}
