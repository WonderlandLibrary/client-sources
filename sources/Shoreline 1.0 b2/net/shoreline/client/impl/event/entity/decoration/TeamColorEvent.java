package net.shoreline.client.impl.event.entity.decoration;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.entity.Entity;

/**
 *
 */
@Cancelable
public class TeamColorEvent extends Event
{
    private final Entity entity;
    private int color;

    public TeamColorEvent(Entity entity)
    {
        this.entity = entity;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public int getColor()
    {
        return color;
    }
}
