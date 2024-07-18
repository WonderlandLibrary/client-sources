package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;
import net.minecraft.entity.EntityLivingBase;

public final class RenderModelEvent implements Event {

    private final EntityLivingBase entity;
    private final Runnable modelRenderer, layerRenderer;

    public RenderModelEvent(EntityLivingBase entity, Runnable modelRenderer, Runnable layerRenderer) {
        this.entity = entity;
        this.modelRenderer = modelRenderer;
        this.layerRenderer = layerRenderer;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }

    public Runnable getModelRenderer() {
        return modelRenderer;
    }

    public Runnable getLayerRenderer() {
        return layerRenderer;
    }
}