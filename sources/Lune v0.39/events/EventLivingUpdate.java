package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.entity.Entity;

public class EventLivingUpdate extends Event {
    private Entity entity;
    public EventLivingUpdate(Entity entity) {
        super();
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
