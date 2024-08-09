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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.util.ResourceLocation;

public abstract class EnergyLayer<T extends Entity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    public EnergyLayer(IEntityRenderer<T, M> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((IChargeableMob)t).isCharged()) {
            float f7 = (float)((Entity)t).ticksExisted + f3;
            EntityModel<T> entityModel = this.func_225635_b_();
            entityModel.setLivingAnimations(t, f, f2, f3);
            ((EntityModel)this.getEntityModel()).copyModelAttributesTo(entityModel);
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEnergySwirl(this.func_225633_a_(), this.func_225634_a_(f7), f7 * 0.01f));
            entityModel.setRotationAngles(t, f, f2, f4, f5, f6);
            entityModel.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1.0f);
        }
    }

    protected abstract float func_225634_a_(float var1);

    protected abstract ResourceLocation func_225633_a_();

    protected abstract EntityModel<T> func_225635_b_();
}

