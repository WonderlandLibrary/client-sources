/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.util.math.vector.Vector3f;

public class HeldBlockLayer
extends LayerRenderer<EndermanEntity, EndermanModel<EndermanEntity>> {
    public HeldBlockLayer(IEntityRenderer<EndermanEntity, EndermanModel<EndermanEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, EndermanEntity endermanEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        BlockState blockState = endermanEntity.getHeldBlockState();
        if (blockState != null) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.6875, -0.75);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(20.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(45.0f));
            matrixStack.translate(0.25, 0.1875, 0.25);
            float f7 = 0.5f;
            matrixStack.scale(-0.5f, -0.5f, 0.5f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (EndermanEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

