/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class DolphinCarriedItemLayer
extends LayerRenderer<DolphinEntity, DolphinModel<DolphinEntity>> {
    public DolphinCarriedItemLayer(IEntityRenderer<DolphinEntity, DolphinModel<DolphinEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, DolphinEntity dolphinEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl = dolphinEntity.getPrimaryHand() == HandSide.RIGHT;
        matrixStack.push();
        float f7 = 1.0f;
        float f8 = -1.0f;
        float f9 = MathHelper.abs(dolphinEntity.rotationPitch) / 60.0f;
        if (dolphinEntity.rotationPitch < 0.0f) {
            matrixStack.translate(0.0, 1.0f - f9 * 0.5f, -1.0f + f9 * 0.5f);
        } else {
            matrixStack.translate(0.0, 1.0f + f9 * 0.8f, -1.0f + f9 * 0.2f);
        }
        ItemStack itemStack = bl ? dolphinEntity.getHeldItemMainhand() : dolphinEntity.getHeldItemOffhand();
        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(dolphinEntity, itemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, iRenderTypeBuffer, n);
        matrixStack.pop();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (DolphinEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

