/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SalmonRenderer
extends MobRenderer<SalmonEntity, SalmonModel<SalmonEntity>> {
    private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");

    public SalmonRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SalmonModel(), 0.4f);
    }

    @Override
    public ResourceLocation getEntityTexture(SalmonEntity salmonEntity) {
        return SALMON_LOCATION;
    }

    @Override
    protected void applyRotations(SalmonEntity salmonEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(salmonEntity, matrixStack, f, f2, f3);
        float f4 = 1.0f;
        float f5 = 1.0f;
        if (!salmonEntity.isInWater()) {
            f4 = 1.3f;
            f5 = 1.7f;
        }
        float f6 = f4 * 4.3f * MathHelper.sin(f5 * 0.6f * f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f6));
        matrixStack.translate(0.0, 0.0, -0.4f);
        if (!salmonEntity.isInWater()) {
            matrixStack.translate(0.2f, 0.1f, 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((SalmonEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SalmonEntity)entity2);
    }
}

