/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.AbstractTropicalFishModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;

public class TropicalFishPatternLayer
extends LayerRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
    private final TropicalFishAModel<TropicalFishEntity> modelA = new TropicalFishAModel(0.008f);
    private final TropicalFishBModel<TropicalFishEntity> modelB = new TropicalFishBModel(0.008f);

    public TropicalFishPatternLayer(IEntityRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, TropicalFishEntity tropicalFishEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        AbstractTropicalFishModel abstractTropicalFishModel = tropicalFishEntity.getSize() == 0 ? this.modelA : this.modelB;
        float[] fArray = tropicalFishEntity.func_204222_dD();
        TropicalFishPatternLayer.renderCopyCutoutModel(this.getEntityModel(), abstractTropicalFishModel, tropicalFishEntity.getPatternTexture(), matrixStack, iRenderTypeBuffer, n, tropicalFishEntity, f, f2, f4, f5, f6, f3, fArray[0], fArray[1], fArray[2]);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (TropicalFishEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

