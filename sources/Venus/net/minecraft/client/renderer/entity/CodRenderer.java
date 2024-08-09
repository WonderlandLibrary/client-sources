/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class CodRenderer
extends MobRenderer<CodEntity, CodModel<CodEntity>> {
    private static final ResourceLocation COD_LOCATION = new ResourceLocation("textures/entity/fish/cod.png");

    public CodRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CodModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(CodEntity codEntity) {
        return COD_LOCATION;
    }

    @Override
    protected void applyRotations(CodEntity codEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(codEntity, matrixStack, f, f2, f3);
        float f4 = 4.3f * MathHelper.sin(0.6f * f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        if (!codEntity.isInWater()) {
            matrixStack.translate(0.1f, 0.1f, -0.1f);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((CodEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((CodEntity)entity2);
    }
}

