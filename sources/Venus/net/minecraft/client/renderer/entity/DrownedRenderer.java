/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class DrownedRenderer
extends AbstractZombieRenderer<DrownedEntity, DrownedModel<DrownedEntity>> {
    private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");

    public DrownedRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new DrownedModel(0.0f, 0.0f, 64, 64), new DrownedModel(0.5f, true), new DrownedModel(1.0f, true));
        this.addLayer(new DrownedOuterLayer<DrownedEntity>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieEntity zombieEntity) {
        return DROWNED_LOCATION;
    }

    @Override
    protected void applyRotations(DrownedEntity drownedEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(drownedEntity, matrixStack, f, f2, f3);
        float f4 = drownedEntity.getSwimAnimation(f3);
        if (f4 > 0.0f) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(f4, drownedEntity.rotationPitch, -10.0f - drownedEntity.rotationPitch)));
        }
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((DrownedEntity)livingEntity, matrixStack, f, f2, f3);
    }
}

