package com.alan.clients.event.impl.other;

import com.alan.clients.event.CancellableEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public final class UpdatePlayerAnglesEvent extends CancellableEvent {
    private final EntityPlayer entityPlayer;
    private final ModelBiped modelBiped;

    public UpdatePlayerAnglesEvent(EntityPlayer entityPlayer, ModelBiped modelBiped) {
        this.entityPlayer = entityPlayer;
        this.modelBiped = modelBiped;
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public ModelBiped getModelBiped() {
        return modelBiped;
    }
}
