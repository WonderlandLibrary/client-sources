/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitchHeldItemLayer;
import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.util.ResourceLocation;

public class WitchRenderer
extends MobRenderer<WitchEntity, WitchModel<WitchEntity>> {
    private static final ResourceLocation WITCH_TEXTURES = new ResourceLocation("textures/entity/witch.png");

    public WitchRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new WitchModel(0.0f), 0.5f);
        this.addLayer(new WitchHeldItemLayer<WitchEntity>(this));
    }

    @Override
    public void render(WitchEntity witchEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        ((WitchModel)this.entityModel).func_205074_a(!witchEntity.getHeldItemMainhand().isEmpty());
        super.render(witchEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(WitchEntity witchEntity) {
        return WITCH_TEXTURES;
    }

    @Override
    protected void preRenderCallback(WitchEntity witchEntity, MatrixStack matrixStack, float f) {
        float f2 = 0.9375f;
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WitchEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((WitchEntity)livingEntity, matrixStack, f);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WitchEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((WitchEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WitchEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

