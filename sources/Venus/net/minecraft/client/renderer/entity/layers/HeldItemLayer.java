/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;

public class HeldItemLayer<T extends LivingEntity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    public HeldItemLayer(IEntityRenderer<T, M> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack;
        boolean bl = ((LivingEntity)t).getPrimaryHand() == HandSide.RIGHT;
        ItemStack itemStack2 = bl ? ((LivingEntity)t).getHeldItemOffhand() : ((LivingEntity)t).getHeldItemMainhand();
        ItemStack itemStack3 = itemStack = bl ? ((LivingEntity)t).getHeldItemMainhand() : ((LivingEntity)t).getHeldItemOffhand();
        if (!itemStack2.isEmpty() || !itemStack.isEmpty()) {
            matrixStack.push();
            if (((EntityModel)this.getEntityModel()).isChild) {
                float f7 = 0.5f;
                matrixStack.translate(0.0, 0.75, 0.0);
                matrixStack.scale(0.5f, 0.5f, 0.5f);
            }
            this.func_229135_a_((LivingEntity)t, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, matrixStack, iRenderTypeBuffer, n);
            this.func_229135_a_((LivingEntity)t, itemStack2, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, matrixStack, iRenderTypeBuffer, n);
            matrixStack.pop();
        }
    }

    private void func_229135_a_(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (!itemStack.isEmpty()) {
            matrixStack.push();
            ((IHasArm)this.getEntityModel()).translateHand(handSide, matrixStack);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            boolean bl = handSide == HandSide.LEFT;
            matrixStack.translate((float)(bl ? -1 : 1) / 16.0f, 0.125, -0.625);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(livingEntity, itemStack, transformType, bl, matrixStack, iRenderTypeBuffer, n);
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

