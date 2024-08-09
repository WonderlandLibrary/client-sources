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
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class Deadmau5HeadLayer
extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    public Deadmau5HeadLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if ("deadmau5".equals(abstractClientPlayerEntity.getName().getString()) && abstractClientPlayerEntity.hasSkin() && !abstractClientPlayerEntity.isInvisible()) {
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(abstractClientPlayerEntity.getLocationSkin()));
            int n2 = LivingRenderer.getPackedOverlay(abstractClientPlayerEntity, 0.0f);
            for (int i = 0; i < 2; ++i) {
                float f7 = MathHelper.lerp(f3, abstractClientPlayerEntity.prevRotationYaw, abstractClientPlayerEntity.rotationYaw) - MathHelper.lerp(f3, abstractClientPlayerEntity.prevRenderYawOffset, abstractClientPlayerEntity.renderYawOffset);
                float f8 = MathHelper.lerp(f3, abstractClientPlayerEntity.prevRotationPitch, abstractClientPlayerEntity.rotationPitch);
                matrixStack.push();
                matrixStack.rotate(Vector3f.YP.rotationDegrees(f7));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(f8));
                matrixStack.translate(0.375f * (float)(i * 2 - 1), 0.0, 0.0);
                matrixStack.translate(0.0, -0.375, 0.0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-f8));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(-f7));
                float f9 = 1.3333334f;
                matrixStack.scale(1.3333334f, 1.3333334f, 1.3333334f);
                ((PlayerModel)this.getEntityModel()).renderEars(matrixStack, iVertexBuilder, n, n2);
                matrixStack.pop();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (AbstractClientPlayerEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

