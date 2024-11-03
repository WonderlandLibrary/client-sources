package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.silentclient.client.utils.HUDCaching;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Framebuffer.class)
public class FramebufferMixin {
    @Inject(method = "bindFramebuffer", at = @At("HEAD"), cancellable = true)
    public void bindHUDCachingBuffer(boolean viewport, CallbackInfo ci) {
        final Framebuffer framebuffer = (Framebuffer) (Object) this;
        if (HUDCaching.renderingCacheOverride && framebuffer == Minecraft.getMinecraft().getFramebuffer()) {
            HUDCaching.framebuffer.bindFramebuffer(viewport);
            ci.cancel();
        }
    }
}
