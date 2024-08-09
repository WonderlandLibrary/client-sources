/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BatModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BatRenderer
extends MobRenderer<BatEntity, BatModel> {
    private static final ResourceLocation BAT_TEXTURES = new ResourceLocation("textures/entity/bat.png");

    public BatRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new BatModel(), 0.25f);
    }

    @Override
    public ResourceLocation getEntityTexture(BatEntity batEntity) {
        return BAT_TEXTURES;
    }

    @Override
    protected void preRenderCallback(BatEntity batEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.35f, 0.35f, 0.35f);
    }

    @Override
    protected void applyRotations(BatEntity batEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        if (batEntity.getIsBatHanging()) {
            matrixStack.translate(0.0, -0.1f, 0.0);
        } else {
            matrixStack.translate(0.0, MathHelper.cos(f * 0.3f) * 0.1f, 0.0);
        }
        super.applyRotations(batEntity, matrixStack, f, f2, f3);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((BatEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((BatEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((BatEntity)entity2);
    }
}

