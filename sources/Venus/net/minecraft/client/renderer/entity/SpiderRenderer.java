/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class SpiderRenderer<T extends SpiderEntity>
extends MobRenderer<T, SpiderModel<T>> {
    private static final ResourceLocation SPIDER_TEXTURES = new ResourceLocation("textures/entity/spider/spider.png");

    public SpiderRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SpiderModel(), 0.8f);
        this.addLayer(new SpiderEyesLayer(this));
    }

    @Override
    protected float getDeathMaxRotation(T t) {
        return 180.0f;
    }

    @Override
    public ResourceLocation getEntityTexture(T t) {
        return SPIDER_TEXTURES;
    }

    @Override
    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.getDeathMaxRotation((T)((SpiderEntity)livingEntity));
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((T)((SpiderEntity)entity2));
    }
}

