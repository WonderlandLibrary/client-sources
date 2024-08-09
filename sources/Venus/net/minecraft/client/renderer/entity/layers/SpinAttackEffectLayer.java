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
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SpinAttackEffectLayer<T extends LivingEntity>
extends LayerRenderer<T, PlayerModel<T>> {
    public static final ResourceLocation field_204836_a = new ResourceLocation("textures/entity/trident_riptide.png");
    private final ModelRenderer field_229143_b_ = new ModelRenderer(64, 64, 0, 0);

    public SpinAttackEffectLayer(IEntityRenderer<T, PlayerModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
        this.field_229143_b_.addBox(-8.0f, -16.0f, -8.0f, 16.0f, 32.0f, 16.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((LivingEntity)t).isSpinAttacking()) {
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(field_204836_a));
            for (int i = 0; i < 3; ++i) {
                matrixStack.push();
                float f7 = f4 * (float)(-(45 + i * 5));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(f7));
                float f8 = 0.75f * (float)i;
                matrixStack.scale(f8, f8, f8);
                matrixStack.translate(0.0, -0.2f + 0.6f * (float)i, 0.0);
                this.field_229143_b_.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY);
                matrixStack.pop();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

