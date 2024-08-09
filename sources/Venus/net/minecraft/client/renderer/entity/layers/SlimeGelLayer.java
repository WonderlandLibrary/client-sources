/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class SlimeGelLayer<T extends LivingEntity>
extends LayerRenderer<T, SlimeModel<T>> {
    private final EntityModel<T> slimeModel = new SlimeModel(0);

    public SlimeGelLayer(IEntityRenderer<T, SlimeModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!((Entity)t).isInvisible()) {
            ((SlimeModel)this.getEntityModel()).copyModelAttributesTo(this.slimeModel);
            this.slimeModel.setLivingAnimations(t, f, f2, f3);
            this.slimeModel.setRotationAngles(t, f, f2, f4, f5, f6);
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(t)));
            this.slimeModel.render(matrixStack, iVertexBuilder, n, LivingRenderer.getPackedOverlay(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

