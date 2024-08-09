/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class FoxHeldItemLayer
extends LayerRenderer<FoxEntity, FoxModel<FoxEntity>> {
    public FoxHeldItemLayer(IEntityRenderer<FoxEntity, FoxModel<FoxEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, FoxEntity foxEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7;
        boolean bl = foxEntity.isSleeping();
        boolean bl2 = foxEntity.isChild();
        matrixStack.push();
        if (bl2) {
            f7 = 0.75f;
            matrixStack.scale(0.75f, 0.75f, 0.75f);
            matrixStack.translate(0.0, 0.5, 0.209375f);
        }
        matrixStack.translate(((FoxModel)this.getEntityModel()).head.rotationPointX / 16.0f, ((FoxModel)this.getEntityModel()).head.rotationPointY / 16.0f, ((FoxModel)this.getEntityModel()).head.rotationPointZ / 16.0f);
        f7 = foxEntity.func_213475_v(f3);
        matrixStack.rotate(Vector3f.ZP.rotation(f7));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f5));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f6));
        if (foxEntity.isChild()) {
            if (bl) {
                matrixStack.translate(0.4f, 0.26f, 0.15f);
            } else {
                matrixStack.translate(0.06f, 0.26f, -0.5);
            }
        } else if (bl) {
            matrixStack.translate(0.46f, 0.26f, 0.22f);
        } else {
            matrixStack.translate(0.06f, 0.27f, -0.5);
        }
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
        if (bl) {
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
        }
        ItemStack itemStack = foxEntity.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(foxEntity, itemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, iRenderTypeBuffer, n);
        matrixStack.pop();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (FoxEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

