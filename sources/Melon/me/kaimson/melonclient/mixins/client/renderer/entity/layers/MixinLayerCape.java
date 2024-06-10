package me.kaimson.melonclient.mixins.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.cosmetics.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bkp.class })
public class MixinLayerCape
{
    @Inject(method = { "doRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    private void doRenderLayer(final bet entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale, final CallbackInfo ci) {
        if (CosmeticManager.hasCape(entitylivingbaseIn.aK().toString())) {
            ci.cancel();
        }
    }
}
