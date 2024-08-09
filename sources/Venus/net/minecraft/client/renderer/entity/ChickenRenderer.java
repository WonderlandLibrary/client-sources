/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ChickenRenderer
extends MobRenderer<ChickenEntity, ChickenModel<ChickenEntity>> {
    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("textures/entity/chicken.png");

    public ChickenRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new ChickenModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(ChickenEntity chickenEntity) {
        return CHICKEN_TEXTURES;
    }

    @Override
    protected float handleRotationFloat(ChickenEntity chickenEntity, float f) {
        float f2 = MathHelper.lerp(f, chickenEntity.oFlap, chickenEntity.wingRotation);
        float f3 = MathHelper.lerp(f, chickenEntity.oFlapSpeed, chickenEntity.destPos);
        return (MathHelper.sin(f2) + 1.0f) * f3;
    }

    @Override
    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.handleRotationFloat((ChickenEntity)livingEntity, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ChickenEntity)entity2);
    }
}

