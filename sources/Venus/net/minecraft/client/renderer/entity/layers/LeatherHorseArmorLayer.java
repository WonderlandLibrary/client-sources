/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

public class LeatherHorseArmorLayer
extends LayerRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private final HorseModel<HorseEntity> field_215341_a = new HorseModel(0.1f);

    public LeatherHorseArmorLayer(IEntityRenderer<HorseEntity, HorseModel<HorseEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, HorseEntity horseEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = horseEntity.func_213803_dV();
        if (itemStack.getItem() instanceof HorseArmorItem) {
            float f7;
            float f8;
            float f9;
            HorseArmorItem horseArmorItem = (HorseArmorItem)itemStack.getItem();
            ((HorseModel)this.getEntityModel()).copyModelAttributesTo(this.field_215341_a);
            this.field_215341_a.setLivingAnimations(horseEntity, f, f2, f3);
            this.field_215341_a.setRotationAngles(horseEntity, f, f2, f4, f5, f6);
            if (horseArmorItem instanceof DyeableHorseArmorItem) {
                int n2 = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
                f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                f8 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                f7 = (float)(n2 & 0xFF) / 255.0f;
            } else {
                f9 = 1.0f;
                f8 = 1.0f;
                f7 = 1.0f;
            }
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(horseArmorItem.getArmorTexture()));
            this.field_215341_a.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, f9, f8, f7, 1.0f);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (HorseEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

