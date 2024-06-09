package com.leafclient.leaf.mixin.render.item;

import com.leafclient.leaf.extension.ExtensionRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase {

    @Inject(
            method = "renderEnchantedGlint",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void inject$enchantGlint(CallbackInfo info) {
        if(((ExtensionRenderManager)Minecraft.getMinecraft().getRenderManager()).isRenderOutline()) {
            info.cancel();
        }
    }

}
