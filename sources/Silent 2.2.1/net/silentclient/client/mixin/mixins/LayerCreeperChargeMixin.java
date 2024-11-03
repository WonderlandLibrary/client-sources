package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LayerCreeperCharge.class)
public class LayerCreeperChargeMixin {
    @ModifyArg(
            method = "doRenderLayer(Lnet/minecraft/entity/monster/EntityCreeper;FFFFFFF)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/model/ModelCreeper;render(Lnet/minecraft/entity/Entity;FFFFFF)V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V"
            )
    )
    private boolean silent$fixDepth(boolean original) {
        return true;
    }
}
