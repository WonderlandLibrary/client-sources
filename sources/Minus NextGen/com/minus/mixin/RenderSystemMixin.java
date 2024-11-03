package com.minus.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import imgui.ImGuiIO;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    @Inject(at = @At(value = "HEAD"), method = "flipFrame(J)V")
    private static void flipFrame(long p_69496_, CallbackInfo cbi) {
        RenderSystem.recordRenderCall(() -> {
        });
    }
}


