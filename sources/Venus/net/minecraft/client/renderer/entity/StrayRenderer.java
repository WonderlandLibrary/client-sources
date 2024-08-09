/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.StayClothingLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class StrayRenderer
extends SkeletonRenderer {
    private static final ResourceLocation STRAY_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray.png");

    public StrayRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.addLayer(new StayClothingLayer<AbstractSkeletonEntity, SkeletonModel<AbstractSkeletonEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return STRAY_SKELETON_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        return this.getEntityTexture((AbstractSkeletonEntity)mobEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((AbstractSkeletonEntity)entity2);
    }
}

