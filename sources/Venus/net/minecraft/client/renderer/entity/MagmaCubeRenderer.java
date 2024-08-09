/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.MagmaCubeModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeRenderer
extends MobRenderer<MagmaCubeEntity, MagmaCubeModel<MagmaCubeEntity>> {
    private static final ResourceLocation MAGMA_CUBE_TEXTURES = new ResourceLocation("textures/entity/slime/magmacube.png");

    public MagmaCubeRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new MagmaCubeModel(), 0.25f);
    }

    @Override
    protected int getBlockLight(MagmaCubeEntity magmaCubeEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(MagmaCubeEntity magmaCubeEntity) {
        return MAGMA_CUBE_TEXTURES;
    }

    @Override
    protected void preRenderCallback(MagmaCubeEntity magmaCubeEntity, MatrixStack matrixStack, float f) {
        int n = magmaCubeEntity.getSlimeSize();
        float f2 = MathHelper.lerp(f, magmaCubeEntity.prevSquishFactor, magmaCubeEntity.squishFactor) / ((float)n * 0.5f + 1.0f);
        float f3 = 1.0f / (f2 + 1.0f);
        matrixStack.scale(f3 * (float)n, 1.0f / f3 * (float)n, f3 * (float)n);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((MagmaCubeEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((MagmaCubeEntity)entity2);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((MagmaCubeEntity)entity2, blockPos);
    }
}

