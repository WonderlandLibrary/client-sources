/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomColors;

public class CatCollarLayer
extends LayerRenderer<CatEntity, CatModel<CatEntity>> {
    private static final ResourceLocation CAT_COLLAR = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final CatModel<CatEntity> model = new CatModel(0.01f);

    public CatCollarLayer(IEntityRenderer<CatEntity, CatModel<CatEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, CatEntity catEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (catEntity.isTamed()) {
            float[] fArray = catEntity.getCollarColor().getColorComponentValues();
            if (Config.isCustomColors()) {
                fArray = CustomColors.getWolfCollarColors(catEntity.getCollarColor(), fArray);
            }
            CatCollarLayer.renderCopyCutoutModel(this.getEntityModel(), this.model, CAT_COLLAR, matrixStack, iRenderTypeBuffer, n, catEntity, f, f2, f4, f5, f6, f3, fArray[0], fArray[1], fArray[2]);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (CatEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

