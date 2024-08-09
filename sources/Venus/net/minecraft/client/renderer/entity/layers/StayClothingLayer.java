/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class StayClothingLayer<T extends MobEntity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    private static final ResourceLocation STRAY_CLOTHES_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
    private final SkeletonModel<T> layerModel = new SkeletonModel(0.25f, true);

    public StayClothingLayer(IEntityRenderer<T, M> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        StayClothingLayer.renderCopyCutoutModel(this.getEntityModel(), this.layerModel, STRAY_CLOTHES_TEXTURES, matrixStack, iRenderTypeBuffer, n, t, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((MobEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

