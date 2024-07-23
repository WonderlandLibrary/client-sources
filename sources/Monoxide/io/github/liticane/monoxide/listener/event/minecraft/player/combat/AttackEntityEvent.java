package io.github.liticane.monoxide.listener.event.minecraft.player.combat;

import net.minecraft.entity.Entity;
import io.github.liticane.monoxide.listener.event.Event;

public class AttackEntityEvent extends Event {
    private final Entity entity;

    public AttackEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
