/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;

public class CapeLayer
extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    public CapeLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack;
        if (abstractClientPlayerEntity.hasPlayerInfo() && !abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.isWearing(PlayerModelPart.CAPE) && abstractClientPlayerEntity.getLocationCape() != null && (itemStack = abstractClientPlayerEntity.getItemStackFromSlot(EquipmentSlotType.CHEST)).getItem() != Items.ELYTRA) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 0.125);
            double d = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevChasingPosX, abstractClientPlayerEntity.chasingPosX) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevPosX, abstractClientPlayerEntity.getPosX());
            double d2 = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevChasingPosY, abstractClientPlayerEntity.chasingPosY) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevPosY, abstractClientPlayerEntity.getPosY());
            double d3 = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevChasingPosZ, abstractClientPlayerEntity.chasingPosZ) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevPosZ, abstractClientPlayerEntity.getPosZ());
            float f7 = abstractClientPlayerEntity.prevRenderYawOffset + (abstractClientPlayerEntity.renderYawOffset - abstractClientPlayerEntity.prevRenderYawOffset);
            double d4 = MathHelper.sin(f7 * ((float)Math.PI / 180));
            double d5 = -MathHelper.cos(f7 * ((float)Math.PI / 180));
            float f8 = (float)d2 * 10.0f;
            f8 = MathHelper.clamp(f8, -6.0f, 32.0f);
            float f9 = (float)(d * d4 + d3 * d5) * 100.0f;
            f9 = MathHelper.clamp(f9, 0.0f, 150.0f);
            float f10 = (float)(d * d5 - d3 * d4) * 100.0f;
            f10 = MathHelper.clamp(f10, -20.0f, 20.0f);
            if (f9 < 0.0f) {
                f9 = 0.0f;
            }
            if (f9 > 165.0f) {
                f9 = 165.0f;
            }
            if (f8 < -5.0f) {
                f8 = -5.0f;
            }
            float f11 = MathHelper.lerp(f3, abstractClientPlayerEntity.prevCameraYaw, abstractClientPlayerEntity.cameraYaw);
            f8 += MathHelper.sin(MathHelper.lerp(f3, abstractClientPlayerEntity.prevDistanceWalkedModified, abstractClientPlayerEntity.distanceWalkedModified) * 6.0f) * 32.0f * f11;
            if (abstractClientPlayerEntity.isCrouching()) {
                f8 += 25.0f;
            }
            float f12 = Config.getAverageFrameTimeSec() * 20.0f;
            f12 = Config.limit(f12, 0.02f, 1.0f);
            abstractClientPlayerEntity.capeRotateX = MathHelper.lerp(f12, abstractClientPlayerEntity.capeRotateX, 6.0f + f9 / 2.0f + f8);
            abstractClientPlayerEntity.capeRotateZ = MathHelper.lerp(f12, abstractClientPlayerEntity.capeRotateZ, f10 / 2.0f);
            abstractClientPlayerEntity.capeRotateY = MathHelper.lerp(f12, abstractClientPlayerEntity.capeRotateY, 180.0f - f10 / 2.0f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(abstractClientPlayerEntity.capeRotateX));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(abstractClientPlayerEntity.capeRotateZ));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(abstractClientPlayerEntity.capeRotateY));
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(abstractClientPlayerEntity.getLocationCape()));
            ((PlayerModel)this.getEntityModel()).renderCape(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (AbstractClientPlayerEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

