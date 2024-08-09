/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SnowmanHeadLayer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.ResourceLocation;

public class SnowManRenderer
extends MobRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> {
    private static final ResourceLocation SNOW_MAN_TEXTURES = new ResourceLocation("textures/entity/snow_golem.png");

    public SnowManRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SnowManModel(), 0.5f);
        this.addLayer(new SnowmanHeadLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(SnowGolemEntity snowGolemEntity) {
        return SNOW_MAN_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SnowGolemEntity)entity2);
    }
}

