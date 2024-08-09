/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class FoxRenderer
extends MobRenderer<FoxEntity, FoxModel<FoxEntity>> {
    private static final ResourceLocation FOX = new ResourceLocation("textures/entity/fox/fox.png");
    private static final ResourceLocation SLEEPING_FOX = new ResourceLocation("textures/entity/fox/fox_sleep.png");
    private static final ResourceLocation SNOW_FOX = new ResourceLocation("textures/entity/fox/snow_fox.png");
    private static final ResourceLocation SLEEPING_SNOW_FOX = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");

    public FoxRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new FoxModel(), 0.4f);
        this.addLayer(new FoxHeldItemLayer(this));
    }

    @Override
    protected void applyRotations(FoxEntity foxEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(foxEntity, matrixStack, f, f2, f3);
        if (foxEntity.func_213480_dY() || foxEntity.isStuck()) {
            float f4 = -MathHelper.lerp(f3, foxEntity.prevRotationPitch, foxEntity.rotationPitch);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f4));
        }
    }

    @Override
    public ResourceLocation getEntityTexture(FoxEntity foxEntity) {
        if (foxEntity.getVariantType() == FoxEntity.Type.RED) {
            return foxEntity.isSleeping() ? SLEEPING_FOX : FOX;
        }
        return foxEntity.isSleeping() ? SLEEPING_SNOW_FOX : SNOW_FOX;
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((FoxEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((FoxEntity)entity2);
    }
}

