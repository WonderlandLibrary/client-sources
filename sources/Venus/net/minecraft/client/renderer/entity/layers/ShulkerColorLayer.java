/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class ShulkerColorLayer
extends LayerRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> {
    public ShulkerColorLayer(IEntityRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, ShulkerEntity shulkerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        matrixStack.push();
        matrixStack.translate(0.0, 1.0, 0.0);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        Quaternion quaternion = shulkerEntity.getAttachmentFacing().getOpposite().getRotation();
        quaternion.conjugate();
        matrixStack.rotate(quaternion);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.0, 0.0);
        DyeColor dyeColor = shulkerEntity.getColor();
        ResourceLocation resourceLocation = dyeColor == null ? ShulkerRenderer.field_204402_a : ShulkerRenderer.SHULKER_ENDERGOLEM_TEXTURE[dyeColor.getId()];
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(resourceLocation));
        ((ShulkerModel)this.getEntityModel()).getHead().render(matrixStack, iVertexBuilder, n, LivingRenderer.getPackedOverlay(shulkerEntity, 0.0f));
        matrixStack.pop();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (ShulkerEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

