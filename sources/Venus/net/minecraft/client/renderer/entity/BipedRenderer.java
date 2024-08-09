/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class BipedRenderer<T extends MobEntity, M extends BipedModel<T>>
extends MobRenderer<T, M> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");

    public BipedRenderer(EntityRendererManager entityRendererManager, M m, float f) {
        this(entityRendererManager, m, f, 1.0f, 1.0f, 1.0f);
    }

    public BipedRenderer(EntityRendererManager entityRendererManager, M m, float f, float f2, float f3, float f4) {
        super(entityRendererManager, m, f);
        this.addLayer(new HeadLayer(this, f2, f3, f4));
        this.addLayer(new ElytraLayer(this));
        this.addLayer(new HeldItemLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(T t) {
        return DEFAULT_RES_LOC;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((T)((MobEntity)entity2));
    }
}

