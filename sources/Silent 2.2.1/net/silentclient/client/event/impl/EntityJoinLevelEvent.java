package net.silentclient.client.event.impl;

import net.minecraft.entity.Entity;
import net.silentclient.client.event.EventCancelable;

public class EntityJoinLevelEvent extends EventCancelable {
    private final boolean loadedFromDisk;
    private final Entity entity;

    public EntityJoinLevelEvent(Entity entity)
    {
        this(entity, false);
    }

    public EntityJoinLevelEvent(Entity entity, boolean loadedFromDisk)
    {
        this.entity = entity;
        this.loadedFromDisk = loadedFromDisk;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public boolean loadedFromDisk()
    {
        return loadedFromDisk;
    }
}
