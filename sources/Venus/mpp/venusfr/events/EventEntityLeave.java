/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class EventEntityLeave
extends Event {
    private Entity entity;

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity2) {
        this.entity = entity2;
    }

    public String toString() {
        return "EventEntityLeave(entity=" + this.getEntity() + ")";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventEntityLeave)) {
            return true;
        }
        EventEntityLeave eventEntityLeave = (EventEntityLeave)object;
        if (!eventEntityLeave.canEqual(this)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        Entity entity2 = this.getEntity();
        Entity entity3 = eventEntityLeave.getEntity();
        return entity2 == null ? entity3 != null : !((Object)entity2).equals(entity3);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventEntityLeave;
    }

    public int hashCode() {
        int n = 59;
        int n2 = super.hashCode();
        Entity entity2 = this.getEntity();
        n2 = n2 * 59 + (entity2 == null ? 43 : ((Object)entity2).hashCode());
        return n2;
    }

    public EventEntityLeave(Entity entity2) {
        this.entity = entity2;
    }
}

