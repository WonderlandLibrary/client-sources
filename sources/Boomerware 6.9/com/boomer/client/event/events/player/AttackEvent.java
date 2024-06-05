package com.boomer.client.event.events.player;

import com.boomer.client.event.Event;
import net.minecraft.entity.Entity;

/**
 * @author Xen for BoomerWare
 * @since 8/4/2019
 **/
public class AttackEvent extends Event {
    private Entity entity;

    public AttackEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
