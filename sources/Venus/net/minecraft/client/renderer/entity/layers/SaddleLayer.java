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
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEquipable;
import net.minecraft.util.ResourceLocation;

public class SaddleLayer<T extends Entity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    private final ResourceLocation field_239408_a_;
    private final M field_239409_b_;

    public SaddleLayer(IEntityRenderer<T, M> iEntityRenderer, M m, ResourceLocation resourceLocation) {
        super(iEntityRenderer);
        this.field_239409_b_ = m;
        this.field_239408_a_ = resourceLocation;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((IEquipable)t).isHorseSaddled()) {
            ((EntityModel)this.getEntityModel()).copyModelAttributesTo(this.field_239409_b_);
            ((EntityModel)this.field_239409_b_).setLivingAnimations(t, f, f2, f3);
            ((EntityModel)this.field_239409_b_).setRotationAngles(t, f, f2, f4, f5, f6);
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(this.field_239408_a_));
            ((Model)this.field_239409_b_).render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

