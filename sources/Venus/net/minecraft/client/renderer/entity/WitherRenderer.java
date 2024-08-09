/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitherAuraLayer;
import net.minecraft.client.renderer.entity.model.WitherModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class WitherRenderer
extends MobRenderer<WitherEntity, WitherModel<WitherEntity>> {
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");

    public WitherRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new WitherModel(0.0f), 1.0f);
        this.addLayer(new WitherAuraLayer(this));
    }

    @Override
    protected int getBlockLight(WitherEntity witherEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(WitherEntity witherEntity) {
        int n = witherEntity.getInvulTime();
        return n > 0 && (n > 80 || n / 5 % 2 != 1) ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
    }

    @Override
    protected void preRenderCallback(WitherEntity witherEntity, MatrixStack matrixStack, float f) {
        float f2 = 2.0f;
        int n = witherEntity.getInvulTime();
        if (n > 0) {
            f2 -= ((float)n - f) / 220.0f * 0.5f;
        }
        matrixStack.scale(f2, f2, f2);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((WitherEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((WitherEntity)entity2);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((WitherEntity)entity2, blockPos);
    }
}

