/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.util.ResourceLocation;

public class SilverfishRenderer
extends MobRenderer<SilverfishEntity, SilverfishModel<SilverfishEntity>> {
    private static final ResourceLocation SILVERFISH_TEXTURES = new ResourceLocation("textures/entity/silverfish.png");

    public SilverfishRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SilverfishModel(), 0.3f);
    }

    @Override
    protected float getDeathMaxRotation(SilverfishEntity silverfishEntity) {
        return 180.0f;
    }

    @Override
    public ResourceLocation getEntityTexture(SilverfishEntity silverfishEntity) {
        return SILVERFISH_TEXTURES;
    }

    @Override
    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.getDeathMaxRotation((SilverfishEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SilverfishEntity)entity2);
    }
}

