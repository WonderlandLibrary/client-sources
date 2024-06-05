package rip.helium.event.minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import rip.helium.event.CancellableEvent;

/**
 * @author antja03
 */
public class RenderNametagEvent extends CancellableEvent {

    private EntityLivingBase entity;

    public RenderNametagEvent(EntityLivingBase entity) {
        this.entity = entity;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }
}
