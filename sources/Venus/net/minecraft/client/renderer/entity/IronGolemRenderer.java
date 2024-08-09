/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCracksLayer;
import net.minecraft.client.renderer.entity.layers.IronGolenFlowerLayer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class IronGolemRenderer
extends MobRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
    private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

    public IronGolemRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new IronGolemModel(), 0.7f);
        this.addLayer(new IronGolemCracksLayer(this));
        this.addLayer(new IronGolenFlowerLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(IronGolemEntity ironGolemEntity) {
        return IRON_GOLEM_TEXTURES;
    }

    @Override
    protected void applyRotations(IronGolemEntity ironGolemEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(ironGolemEntity, matrixStack, f, f2, f3);
        if (!((double)ironGolemEntity.limbSwingAmount < 0.01)) {
            float f4 = 13.0f;
            float f5 = ironGolemEntity.limbSwing - ironGolemEntity.limbSwingAmount * (1.0f - f3) + 6.0f;
            float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(6.5f * f6));
        }
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((IronGolemEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((IronGolemEntity)entity2);
    }
}

