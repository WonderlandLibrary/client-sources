package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.event.Cancellable;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author antja03
 */
public class RenderNametagEvent extends Cancellable {

    private EntityLivingBase entity;

    public RenderNametagEvent(EntityLivingBase entity) {
        this.entity = entity;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }
}
