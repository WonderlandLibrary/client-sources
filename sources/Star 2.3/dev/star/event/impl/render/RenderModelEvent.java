package dev.star.event.impl.render;

import dev.star.event.Event;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.EntityLivingBase;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

public class RenderModelEvent extends Event.StateEvent {

    private final EntityLivingBase entity;
    private final Runnable modelRenderer;
    private final Runnable layerRenderer;
    private ModelPlayer modelPlayer;

    public RenderModelEvent(EntityLivingBase entity, Runnable modelRenderer, Runnable layerRenderer) {
        this.entity = entity;
        this.modelRenderer = modelRenderer;
        this.layerRenderer = layerRenderer;
    }


    public ModelPlayer getModelPlayer() {
        return modelPlayer;
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public EntityLivingBase getEntity() {
        return entity;
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public void drawModel() {
        this.modelRenderer.run();
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public void drawLayers() {
        this.layerRenderer.run();
    }

}
