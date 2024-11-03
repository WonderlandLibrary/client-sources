package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSpiderEyes.class)
public class LayerSpiderEyesMixin {
    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySpider;FFFFFFF)V", at = @At("TAIL"))
    private void silent$fixDepth(CallbackInfo ci) {
        GlStateManager.depthMask(true);
    }
}
