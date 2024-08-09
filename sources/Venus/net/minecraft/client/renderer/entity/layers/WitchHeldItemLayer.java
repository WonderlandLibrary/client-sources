/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;

public class WitchHeldItemLayer<T extends LivingEntity>
extends CrossedArmsItemLayer<T, WitchModel<T>> {
    public WitchHeldItemLayer(IEntityRenderer<T, WitchModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = ((LivingEntity)t).getHeldItemMainhand();
        matrixStack.push();
        if (itemStack.getItem() == Items.POTION) {
            ((WitchModel)this.getEntityModel()).getModelHead().translateRotate(matrixStack);
            ((WitchModel)this.getEntityModel()).func_205073_b().translateRotate(matrixStack);
            matrixStack.translate(0.0625, 0.25, 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(140.0f));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(10.0f));
            matrixStack.translate(0.0, -0.4f, 0.4f);
        }
        super.render(matrixStack, iRenderTypeBuffer, n, t, f, f2, f3, f4, f5, f6);
        matrixStack.pop();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

