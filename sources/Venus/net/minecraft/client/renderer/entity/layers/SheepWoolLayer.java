/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomColors;

public class SheepWoolLayer
extends LayerRenderer<SheepEntity, SheepModel<SheepEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    public SheepWoolModel<SheepEntity> sheepModel = new SheepWoolModel();

    public SheepWoolLayer(IEntityRenderer<SheepEntity, SheepModel<SheepEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, SheepEntity sheepEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!sheepEntity.getSheared() && !sheepEntity.isInvisible()) {
            float f7;
            float f8;
            float f9;
            if (sheepEntity.hasCustomName() && "jeb_".equals(sheepEntity.getName().getUnformattedComponentText())) {
                int n2 = 25;
                int n3 = sheepEntity.ticksExisted / 25 + sheepEntity.getEntityId();
                int n4 = DyeColor.values().length;
                int n5 = n3 % n4;
                int n6 = (n3 + 1) % n4;
                float f10 = ((float)(sheepEntity.ticksExisted % 25) + f3) / 25.0f;
                float[] fArray = SheepEntity.getDyeRgb(DyeColor.byId(n5));
                float[] fArray2 = SheepEntity.getDyeRgb(DyeColor.byId(n6));
                if (Config.isCustomColors()) {
                    fArray = CustomColors.getSheepColors(DyeColor.byId(n5), fArray);
                    fArray2 = CustomColors.getSheepColors(DyeColor.byId(n6), fArray2);
                }
                f9 = fArray[0] * (1.0f - f10) + fArray2[0] * f10;
                f8 = fArray[1] * (1.0f - f10) + fArray2[1] * f10;
                f7 = fArray[2] * (1.0f - f10) + fArray2[2] * f10;
            } else {
                float[] fArray = SheepEntity.getDyeRgb(sheepEntity.getFleeceColor());
                if (Config.isCustomColors()) {
                    fArray = CustomColors.getSheepColors(sheepEntity.getFleeceColor(), fArray);
                }
                f9 = fArray[0];
                f8 = fArray[1];
                f7 = fArray[2];
            }
            SheepWoolLayer.renderCopyCutoutModel(this.getEntityModel(), this.sheepModel, TEXTURE, matrixStack, iRenderTypeBuffer, n, sheepEntity, f, f2, f4, f5, f6, f3, f9, f8, f7);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (SheepEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

