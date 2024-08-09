package ru.FecuritySQ.capes;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexConsumer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class VanillaCapeRenderer implements CapeRenderer {

    public IVertexBuilder vertexConsumer = null;

    @Override
    public void render(AbstractClientPlayerEntity player, int part, ModelRenderer model, MatrixStack poseStack,
                       IRenderTypeBuffer multiBufferSource, int light, int overlay) {
        model.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public IVertexBuilder getVertexConsumer(IRenderTypeBuffer multiBufferSource, AbstractClientPlayerEntity player) {
        return multiBufferSource.getBuffer(RenderType.getEntityCutout(player.getLocationCape()));
    }

    @Override
    public boolean vanillaUvValues() {
        return true;
    }

}
