package dev.waveycapes;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.vertex.IVertexBuilder;

public class VanillaCapeRenderer implements CapeRenderer {

    public IVertexBuilder vertexConsumer = null;

    public void render(final AbstractClientPlayerEntity player, final int part, final ModelRenderer model, final MatrixStack poseStack, final IRenderTypeBuffer multiBufferSource, final int light, final int overlay) {
        model.render(poseStack, this.vertexConsumer, light, OverlayTexture.NO_OVERLAY);
    }

    public IVertexBuilder getVertexConsumer(final IRenderTypeBuffer multiBufferSource, final AbstractClientPlayerEntity player) {
        return multiBufferSource.getBuffer(RenderType.getEntityCutout(player.getLocationCape()));
    }

    @Override
    public boolean vanillaUvValues() {
        return true;
    }

}
