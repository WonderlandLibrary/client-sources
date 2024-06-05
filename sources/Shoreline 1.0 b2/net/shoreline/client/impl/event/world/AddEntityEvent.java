package net.shoreline.client.impl.event.world;

import net.minecraft.entity.Entity;
import net.shoreline.client.api.event.Event;

public class AddEntityEvent extends Event
{
    private final int entityId;
    private final Entity entity;

    public AddEntityEvent(int entityId, Entity entity)
    {
        this.entityId = entityId;
        this.entity = entity;
    }

    public int getEntityId()
    {
        return entityId;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
