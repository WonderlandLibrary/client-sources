/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomColors;

public class WolfCollarLayer
extends LayerRenderer<WolfEntity, WolfModel<WolfEntity>> {
    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public WolfCollarLayer(IEntityRenderer<WolfEntity, WolfModel<WolfEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, WolfEntity wolfEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (wolfEntity.isTamed() && !wolfEntity.isInvisible()) {
            float[] fArray = wolfEntity.getCollarColor().getColorComponentValues();
            if (Config.isCustomColors()) {
                fArray = CustomColors.getWolfCollarColors(wolfEntity.getCollarColor(), fArray);
            }
            WolfCollarLayer.renderCutoutModel(this.getEntityModel(), WOLF_COLLAR, matrixStack, iRenderTypeBuffer, n, wolfEntity, fArray[0], fArray[1], fArray[2]);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (WolfEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

