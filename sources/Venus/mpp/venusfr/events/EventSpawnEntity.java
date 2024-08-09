/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.entity.Entity;

public class EventSpawnEntity {
    private Entity entity;

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity2) {
        this.entity = entity2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventSpawnEntity)) {
            return true;
        }
        EventSpawnEntity eventSpawnEntity = (EventSpawnEntity)object;
        if (!eventSpawnEntity.canEqual(this)) {
            return true;
        }
        Entity entity2 = this.getEntity();
        Entity entity3 = eventSpawnEntity.getEntity();
        return entity2 == null ? entity3 != null : !((Object)entity2).equals(entity3);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventSpawnEntity;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        Entity entity2 = this.getEntity();
        n2 = n2 * 59 + (entity2 == null ? 43 : ((Object)entity2).hashCode());
        return n2;
    }

    public String toString() {
        return "EventSpawnEntity(entity=" + this.getEntity() + ")";
    }

    public EventSpawnEntity(Entity entity2) {
        this.entity = entity2;
    }
}

