/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ParrotRenderer
extends MobRenderer<ParrotEntity, ParrotModel> {
    public static final ResourceLocation[] PARROT_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_green.png"), new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_grey.png")};

    public ParrotRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new ParrotModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(ParrotEntity parrotEntity) {
        return PARROT_TEXTURES[parrotEntity.getVariant()];
    }

    @Override
    public float handleRotationFloat(ParrotEntity parrotEntity, float f) {
        float f2 = MathHelper.lerp(f, parrotEntity.oFlap, parrotEntity.flap);
        float f3 = MathHelper.lerp(f, parrotEntity.oFlapSpeed, parrotEntity.flapSpeed);
        return (MathHelper.sin(f2) + 1.0f) * f3;
    }

    @Override
    public float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.handleRotationFloat((ParrotEntity)livingEntity, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ParrotEntity)entity2);
    }
}

