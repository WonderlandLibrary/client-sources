/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CreeperRenderer
extends MobRenderer<CreeperEntity, CreeperModel<CreeperEntity>> {
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");

    public CreeperRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CreeperModel(), 0.5f);
        this.addLayer(new CreeperChargeLayer(this));
    }

    @Override
    protected void preRenderCallback(CreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
        float f2 = creeperEntity.getCreeperFlashIntensity(f);
        float f3 = 1.0f + MathHelper.sin(f2 * 100.0f) * f2 * 0.01f;
        f2 = MathHelper.clamp(f2, 0.0f, 1.0f);
        f2 *= f2;
        f2 *= f2;
        float f4 = (1.0f + f2 * 0.4f) * f3;
        float f5 = (1.0f + f2 * 0.1f) / f3;
        matrixStack.scale(f4, f5, f4);
    }

    @Override
    protected float getOverlayProgress(CreeperEntity creeperEntity, float f) {
        float f2 = creeperEntity.getCreeperFlashIntensity(f);
        return (int)(f2 * 10.0f) % 2 == 0 ? 0.0f : MathHelper.clamp(f2, 0.5f, 1.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(CreeperEntity creeperEntity) {
        return CREEPER_TEXTURES;
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((CreeperEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected float getOverlayProgress(LivingEntity livingEntity, float f) {
        return this.getOverlayProgress((CreeperEntity)livingEntity, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((CreeperEntity)entity2);
    }
}

