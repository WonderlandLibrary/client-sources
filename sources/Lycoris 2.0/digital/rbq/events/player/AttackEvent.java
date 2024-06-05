/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.player;

import net.minecraft.entity.Entity;
import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class AttackEvent
extends Cancellable
implements Event {
    private final Entity entity;

    public AttackEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

