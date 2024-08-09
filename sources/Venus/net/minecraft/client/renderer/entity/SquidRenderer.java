/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SquidRenderer
extends MobRenderer<SquidEntity, SquidModel<SquidEntity>> {
    private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation("textures/entity/squid.png");

    public SquidRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SquidModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(SquidEntity squidEntity) {
        return SQUID_TEXTURES;
    }

    @Override
    protected void applyRotations(SquidEntity squidEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        float f4 = MathHelper.lerp(f3, squidEntity.prevSquidPitch, squidEntity.squidPitch);
        float f5 = MathHelper.lerp(f3, squidEntity.prevSquidYaw, squidEntity.squidYaw);
        matrixStack.translate(0.0, 0.5, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f2));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f4));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f5));
        matrixStack.translate(0.0, -1.2f, 0.0);
    }

    @Override
    protected float handleRotationFloat(SquidEntity squidEntity, float f) {
        return MathHelper.lerp(f, squidEntity.lastTentacleAngle, squidEntity.tentacleAngle);
    }

    @Override
    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.handleRotationFloat((SquidEntity)livingEntity, f);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((SquidEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SquidEntity)entity2);
    }
}

