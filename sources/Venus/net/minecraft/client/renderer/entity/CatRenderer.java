/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class CatRenderer
extends MobRenderer<CatEntity, CatModel<CatEntity>> {
    public CatRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CatModel(0.0f), 0.4f);
        this.addLayer(new CatCollarLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(CatEntity catEntity) {
        return catEntity.getCatTypeName();
    }

    @Override
    protected void preRenderCallback(CatEntity catEntity, MatrixStack matrixStack, float f) {
        super.preRenderCallback(catEntity, matrixStack, f);
        matrixStack.scale(0.8f, 0.8f, 0.8f);
    }

    @Override
    protected void applyRotations(CatEntity catEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(catEntity, matrixStack, f, f2, f3);
        float f4 = catEntity.func_213408_v(f3);
        if (f4 > 0.0f) {
            matrixStack.translate(0.4f * f4, 0.15f * f4, 0.1f * f4);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.interpolateAngle(f4, 0.0f, 90.0f)));
            BlockPos blockPos = catEntity.getPosition();
            for (PlayerEntity playerEntity : catEntity.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(blockPos).grow(2.0, 2.0, 2.0))) {
                if (!playerEntity.isSleeping()) continue;
                matrixStack.translate(0.15f * f4, 0.0, 0.0);
                break;
            }
        }
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((CatEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((CatEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((CatEntity)entity2);
    }
}

