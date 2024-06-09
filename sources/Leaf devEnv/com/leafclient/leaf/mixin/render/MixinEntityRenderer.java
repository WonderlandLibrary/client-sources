package com.leafclient.leaf.mixin.render;

import com.leafclient.leaf.event.game.render.ScreenRenderEvent;
import com.leafclient.leaf.management.event.EventManager;
import com.leafclient.leaf.extension.ExtensionEntityRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements ExtensionEntityRenderer {

    @Shadow protected abstract void shadow$setupCameraTransform(float partialTicks, int pass);


    @Override
    public void setupCamera(float partialTicks, int pass) {
        shadow$setupCameraTransform(partialTicks, pass);
    }

    @Inject(
            method = "updateCameraAndRender",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V",
                    shift = At.Shift.AFTER
            )
    )
    private void inject$renderShader(float partialTicks, long nanoTime, CallbackInfo info) {
        EventManager.INSTANCE
                .publish(new ScreenRenderEvent(partialTicks));
    }

}
