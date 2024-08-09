/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.util.ResourceLocation;

public class PillagerRenderer
extends IllagerRenderer<PillagerEntity> {
    private static final ResourceLocation field_217772_a = new ResourceLocation("textures/entity/illager/pillager.png");

    public PillagerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new IllagerModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addLayer(new HeldItemLayer<PillagerEntity, IllagerModel<PillagerEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(PillagerEntity pillagerEntity) {
        return field_217772_a;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PillagerEntity)entity2);
    }
}

