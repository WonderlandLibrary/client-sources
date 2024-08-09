/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.VexModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class VexRenderer
extends BipedRenderer<VexEntity, VexModel> {
    private static final ResourceLocation VEX_TEXTURE = new ResourceLocation("textures/entity/illager/vex.png");
    private static final ResourceLocation VEX_CHARGING_TEXTURE = new ResourceLocation("textures/entity/illager/vex_charging.png");

    public VexRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new VexModel(), 0.3f);
    }

    @Override
    protected int getBlockLight(VexEntity vexEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(VexEntity vexEntity) {
        return vexEntity.isCharging() ? VEX_CHARGING_TEXTURE : VEX_TEXTURE;
    }

    @Override
    protected void preRenderCallback(VexEntity vexEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4f, 0.4f, 0.4f);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        return this.getEntityTexture((VexEntity)mobEntity);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((VexEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((VexEntity)entity2);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((VexEntity)entity2, blockPos);
    }
}

