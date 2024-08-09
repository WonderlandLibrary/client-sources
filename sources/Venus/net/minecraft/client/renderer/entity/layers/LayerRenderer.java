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
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public abstract class LayerRenderer<T extends Entity, M extends EntityModel<T>> {
    private final IEntityRenderer<T, M> entityRenderer;

    public LayerRenderer(IEntityRenderer<T, M> iEntityRenderer) {
        this.entityRenderer = iEntityRenderer;
    }

    protected static <T extends LivingEntity> void renderCopyCutoutModel(EntityModel<T> entityModel, EntityModel<T> entityModel2, ResourceLocation resourceLocation, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        if (!t.isInvisible()) {
            entityModel.copyModelAttributesTo(entityModel2);
            entityModel2.setLivingAnimations(t, f, f2, f6);
            entityModel2.setRotationAngles(t, f, f2, f3, f4, f5);
            LayerRenderer.renderCutoutModel(entityModel2, resourceLocation, matrixStack, iRenderTypeBuffer, n, t, f7, f8, f9);
        }
    }

    protected static <T extends LivingEntity> void renderCutoutModel(EntityModel<T> entityModel, ResourceLocation resourceLocation, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3) {
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(resourceLocation));
        entityModel.render(matrixStack, iVertexBuilder, n, LivingRenderer.getPackedOverlay(t, 0.0f), f, f2, f3, 1.0f);
    }

    public M getEntityModel() {
        return this.entityRenderer.getEntityModel();
    }

    protected ResourceLocation getEntityTexture(T t) {
        return this.entityRenderer.getEntityTexture(t);
    }

    public abstract void render(MatrixStack var1, IRenderTypeBuffer var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10);
}

