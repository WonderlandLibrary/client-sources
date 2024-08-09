/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class ParrotVariantLayer<T extends PlayerEntity>
extends LayerRenderer<T, PlayerModel<T>> {
    private final ParrotModel parrotModel = new ParrotModel();

    public ParrotVariantLayer(IEntityRenderer<T, PlayerModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        this.renderParrot(matrixStack, iRenderTypeBuffer, n, t, f, f2, f5, f6, false);
        this.renderParrot(matrixStack, iRenderTypeBuffer, n, t, f, f2, f5, f6, true);
    }

    private void renderParrot(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, boolean bl) {
        CompoundNBT compoundNBT = bl ? ((PlayerEntity)t).getLeftShoulderEntity() : ((PlayerEntity)t).getRightShoulderEntity();
        EntityType.byKey(compoundNBT.getString("id")).filter(ParrotVariantLayer::lambda$renderParrot$0).ifPresent(arg_0 -> this.lambda$renderParrot$1(t, bl, matrixStack, iRenderTypeBuffer, compoundNBT, n, f, f2, f3, f4, arg_0));
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((PlayerEntity)entity2), f, f2, f3, f4, f5, f6);
    }

    private void lambda$renderParrot$1(PlayerEntity playerEntity, boolean bl, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, CompoundNBT compoundNBT, int n, float f, float f2, float f3, float f4, EntityType entityType) {
        Object object;
        Entity entity2 = Config.getRenderGlobal().renderedEntity;
        if (playerEntity instanceof AbstractClientPlayerEntity) {
            ShoulderRidingEntity shoulderRidingEntity;
            object = (AbstractClientPlayerEntity)playerEntity;
            ShoulderRidingEntity shoulderRidingEntity2 = shoulderRidingEntity = bl ? ((AbstractClientPlayerEntity)object).entityShoulderLeft : ((AbstractClientPlayerEntity)object).entityShoulderRight;
            if (shoulderRidingEntity != null) {
                Config.getRenderGlobal().renderedEntity = shoulderRidingEntity;
                if (Config.isShaders()) {
                    Shaders.nextEntity(shoulderRidingEntity);
                }
            }
        }
        matrixStack.push();
        matrixStack.translate(bl ? (double)0.4f : (double)-0.4f, playerEntity.isCrouching() ? (double)-1.3f : -1.5, 0.0);
        object = iRenderTypeBuffer.getBuffer(this.parrotModel.getRenderType(ParrotRenderer.PARROT_TEXTURES[compoundNBT.getInt("Variant")]));
        this.parrotModel.renderOnShoulder(matrixStack, (IVertexBuilder)object, n, OverlayTexture.NO_OVERLAY, f, f2, f3, f4, playerEntity.ticksExisted);
        matrixStack.pop();
        Config.getRenderGlobal().renderedEntity = entity2;
        if (Config.isShaders()) {
            Shaders.nextEntity(entity2);
        }
    }

    private static boolean lambda$renderParrot$0(EntityType entityType) {
        return entityType == EntityType.PARROT;
    }
}

