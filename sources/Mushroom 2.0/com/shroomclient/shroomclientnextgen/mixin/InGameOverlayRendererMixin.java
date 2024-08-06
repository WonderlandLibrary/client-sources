package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.impl.render.AntiBlind;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Inject(
        method = "renderFireOverlay",
        at = @At(
            value = "HEAD",
            target = "Lnet/minecraft/client/render/VertexConsumer;color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private static void injectFireOpacity(
        MinecraftClient client,
        MatrixStack matrices,
        CallbackInfo ci
    ) {
        matrices.translate(0, -AntiBlind.fireOffset(), 0);
    }

    @Redirect(
        method = "renderFireOverlay",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private static VertexConsumer injectFireOpacity(
        VertexConsumer vertexConsumer,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        return vertexConsumer.color(
            red,
            green,
            blue,
            alpha * AntiBlind.fireOpacity()
        );
    }
}
