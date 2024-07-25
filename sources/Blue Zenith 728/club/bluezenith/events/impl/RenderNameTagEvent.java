package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.entity.EntityLivingBase;

public class RenderNameTagEvent extends Event {

    private final EntityLivingBase entity;

    public RenderNameTagEvent(EntityLivingBase entity) {
        this.entity = entity;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

}
