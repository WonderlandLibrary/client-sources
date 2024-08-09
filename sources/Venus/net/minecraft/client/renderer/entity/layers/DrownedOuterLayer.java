/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.util.ResourceLocation;

public class DrownedOuterLayer<T extends DrownedEntity>
extends LayerRenderer<T, DrownedModel<T>> {
    private static final ResourceLocation LOCATION_OUTER_LAYER = new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
    private final DrownedModel<T> modelOuterLayer = new DrownedModel(0.25f, 0.0f, 64, 64);

    public DrownedOuterLayer(IEntityRenderer<T, DrownedModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        DrownedOuterLayer.renderCopyCutoutModel(this.getEntityModel(), this.modelOuterLayer, LOCATION_OUTER_LAYER, matrixStack, iRenderTypeBuffer, n, t, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((DrownedEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

