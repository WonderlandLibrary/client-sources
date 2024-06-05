package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.entity.Entity;

public class EventAttack
extends Event {
    private final Entity entity;
    private final boolean preAttack;

    public EventAttack(Entity targetEntity, boolean preAttack) {
        this.entity = targetEntity;
        this.preAttack = preAttack;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean isPreAttack() {
        return this.preAttack;
    }

    public boolean isPostAttack() {
        return !this.preAttack;
    }
}