package io.github.nevalackin.radium.event.impl.player;

import io.github.nevalackin.radium.event.Event;
import net.minecraft.entity.Entity;

public final class DamageEntityEvent implements Event {

    private final Entity entity;
    private final double damage;

    public DamageEntityEvent(Entity entity, double damage) {
        this.entity = entity;
        this.damage = damage;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getDamage() {
        return damage;
    }

}
