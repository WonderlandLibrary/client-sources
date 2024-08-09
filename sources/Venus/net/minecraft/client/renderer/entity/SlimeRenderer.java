/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeGelLayer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SlimeRenderer
extends MobRenderer<SlimeEntity, SlimeModel<SlimeEntity>> {
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");

    public SlimeRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SlimeModel(16), 0.25f);
        this.addLayer(new SlimeGelLayer<SlimeEntity>(this));
    }

    @Override
    public void render(SlimeEntity slimeEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.shadowSize = 0.25f * (float)slimeEntity.getSlimeSize();
        super.render(slimeEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void preRenderCallback(SlimeEntity slimeEntity, MatrixStack matrixStack, float f) {
        float f2 = 0.999f;
        matrixStack.scale(0.999f, 0.999f, 0.999f);
        matrixStack.translate(0.0, 0.001f, 0.0);
        float f3 = slimeEntity.getSlimeSize();
        float f4 = MathHelper.lerp(f, slimeEntity.prevSquishFactor, slimeEntity.squishFactor) / (f3 * 0.5f + 1.0f);
        float f5 = 1.0f / (f4 + 1.0f);
        matrixStack.scale(f5 * f3, 1.0f / f5 * f3, f5 * f3);
    }

    @Override
    public ResourceLocation getEntityTexture(SlimeEntity slimeEntity) {
        return SLIME_TEXTURES;
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((SlimeEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((SlimeEntity)livingEntity, matrixStack, f);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((SlimeEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SlimeEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((SlimeEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

