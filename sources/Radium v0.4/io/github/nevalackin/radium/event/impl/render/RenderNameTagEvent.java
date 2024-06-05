package io.github.nevalackin.radium.event.impl.render;

import io.github.nevalackin.radium.event.CancellableEvent;
import net.minecraft.entity.EntityLivingBase;

public final class RenderNameTagEvent extends CancellableEvent {

    private final EntityLivingBase entityLivingBase;

    public RenderNameTagEvent(EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
    }

    public EntityLivingBase getEntityLivingBase() {
        return entityLivingBase;
    }

}
