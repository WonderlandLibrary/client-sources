/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.vector.Vector3f;

public class IronGolenFlowerLayer
extends LayerRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
    public IronGolenFlowerLayer(IEntityRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, IronGolemEntity ironGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (ironGolemEntity.getHoldRoseTick() != 0) {
            matrixStack.push();
            ModelRenderer modelRenderer = ((IronGolemModel)this.getEntityModel()).getArmHoldingRose();
            modelRenderer.translateRotate(matrixStack);
            matrixStack.translate(-1.1875, 1.0625, -0.9375);
            matrixStack.translate(0.5, 0.5, 0.5);
            float f7 = 0.5f;
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f));
            matrixStack.translate(-0.5, -0.5, -0.5);
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(Blocks.POPPY.getDefaultState(), matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (IronGolemEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

