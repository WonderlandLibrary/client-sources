package net.shoreline.client.impl.event.world;

import net.shoreline.client.api.event.Event;
import net.shoreline.client.util.Globals;
import net.minecraft.entity.Entity;

public class RemoveEntityEvent extends Event implements Globals
{
    private final int entityId;
    private final Entity.RemovalReason removalReason;

    public RemoveEntityEvent(int entityId, Entity.RemovalReason removalReason)
    {
        this.entityId = entityId;
        this.removalReason = removalReason;
    }

    public int getEntityId()
    {
        return entityId;
    }

    /**
     *
     * @return
     */
    public Entity getEntity()
    {
        return mc.world.getEntityById(entityId);
    }

    public Entity.RemovalReason getRemovalReason()
    {
        return removalReason;
    }
}
