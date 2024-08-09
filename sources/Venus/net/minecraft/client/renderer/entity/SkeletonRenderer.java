/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class SkeletonRenderer
extends BipedRenderer<AbstractSkeletonEntity, SkeletonModel<AbstractSkeletonEntity>> {
    private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public SkeletonRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SkeletonModel(), 0.5f);
        this.addLayer(new BipedArmorLayer(this, new SkeletonModel(0.5f, true), new SkeletonModel(1.0f, true)));
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return SKELETON_TEXTURES;
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

