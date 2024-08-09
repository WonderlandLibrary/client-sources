/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class PandaHeldItemLayer
extends LayerRenderer<PandaEntity, PandaModel<PandaEntity>> {
    public PandaHeldItemLayer(IEntityRenderer<PandaEntity, PandaModel<PandaEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, PandaEntity pandaEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = pandaEntity.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (pandaEntity.func_213556_dX() && !pandaEntity.func_213566_eo()) {
            float f7 = -0.6f;
            float f8 = 1.4f;
            if (pandaEntity.func_213578_dZ()) {
                f7 -= 0.2f * MathHelper.sin(f4 * 0.6f) + 0.2f;
                f8 -= 0.09f * MathHelper.sin(f4 * 0.6f);
            }
            matrixStack.push();
            matrixStack.translate(0.1f, f8, f7);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(pandaEntity, itemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, iRenderTypeBuffer, n);
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (PandaEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

