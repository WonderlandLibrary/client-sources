package net.silentclient.client.mixin.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCape.class)
public abstract class LayerCapeMixin {
    @Inject(method = "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V", at = @At("HEAD"), cancellable = true)
    public void cancelCapeRenderer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale, CallbackInfo ci) {
        if(((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCape() != null) {
            ci.cancel();
        }
    }
}
