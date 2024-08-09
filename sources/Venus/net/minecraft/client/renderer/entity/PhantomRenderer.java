/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class PhantomRenderer
extends MobRenderer<PhantomEntity, PhantomModel<PhantomEntity>> {
    private static final ResourceLocation PHANTOM_LOCATION = new ResourceLocation("textures/entity/phantom.png");

    public PhantomRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new PhantomModel(), 0.75f);
        this.addLayer(new PhantomEyesLayer<PhantomEntity>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(PhantomEntity phantomEntity) {
        return PHANTOM_LOCATION;
    }

    @Override
    protected void preRenderCallback(PhantomEntity phantomEntity, MatrixStack matrixStack, float f) {
        int n = phantomEntity.getPhantomSize();
        float f2 = 1.0f + 0.15f * (float)n;
        matrixStack.scale(f2, f2, f2);
        matrixStack.translate(0.0, 1.3125, 0.1875);
    }

    @Override
    protected void applyRotations(PhantomEntity phantomEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(phantomEntity, matrixStack, f, f2, f3);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(phantomEntity.rotationPitch));
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((PhantomEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((PhantomEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PhantomEntity)entity2);
    }
}

