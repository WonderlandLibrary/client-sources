/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PolarBearModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.ResourceLocation;

public class PolarBearRenderer
extends MobRenderer<PolarBearEntity, PolarBearModel<PolarBearEntity>> {
    private static final ResourceLocation POLAR_BEAR_TEXTURE = new ResourceLocation("textures/entity/bear/polarbear.png");

    public PolarBearRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new PolarBearModel(), 0.9f);
    }

    @Override
    public ResourceLocation getEntityTexture(PolarBearEntity polarBearEntity) {
        return POLAR_BEAR_TEXTURE;
    }

    @Override
    protected void preRenderCallback(PolarBearEntity polarBearEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.2f, 1.2f, 1.2f);
        super.preRenderCallback(polarBearEntity, matrixStack, f);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((PolarBearEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PolarBearEntity)entity2);
    }
}

