package net.shoreline.client.impl.event.render.entity;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.entity.Entity;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Cancelable
public class RenderLabelEvent extends Event
{
    private final Entity entity;

    public RenderLabelEvent(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
