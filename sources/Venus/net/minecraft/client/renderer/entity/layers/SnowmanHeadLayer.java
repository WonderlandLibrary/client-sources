/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class SnowmanHeadLayer
extends LayerRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> {
    public SnowmanHeadLayer(IEntityRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, SnowGolemEntity snowGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!snowGolemEntity.isInvisible() && snowGolemEntity.isPumpkinEquipped()) {
            matrixStack.push();
            ((SnowManModel)this.getEntityModel()).func_205070_a().translateRotate(matrixStack);
            float f7 = 0.625f;
            matrixStack.translate(0.0, -0.34375, 0.0);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            matrixStack.scale(0.625f, -0.625f, -0.625f);
            ItemStack itemStack = new ItemStack(Blocks.CARVED_PUMPKIN);
            Minecraft.getInstance().getItemRenderer().renderItem(snowGolemEntity, itemStack, ItemCameraTransforms.TransformType.HEAD, false, matrixStack, iRenderTypeBuffer, snowGolemEntity.world, n, LivingRenderer.getPackedOverlay(snowGolemEntity, 0.0f));
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (SnowGolemEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

