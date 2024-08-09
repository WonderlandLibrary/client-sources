package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventInteractWithEntity implements Event {
    private Entity entity;
    private boolean canceled;

    public EventInteractWithEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setCanceled() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return this.canceled;
    }
}
