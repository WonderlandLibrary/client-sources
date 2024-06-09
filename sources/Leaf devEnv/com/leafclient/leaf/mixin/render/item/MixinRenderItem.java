package com.leafclient.leaf.mixin.render.item;

import com.leafclient.leaf.extension.ExtensionRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {

    @Inject(
            method = "renderEffect",
            at = @At("INVOKE"),
            cancellable = true
    )
    private void cancel$enchantRendering(CallbackInfo info) {
        if(((ExtensionRenderManager)Minecraft.getMinecraft().getRenderManager()).isRenderOutline()) {
            info.cancel();
        }
    }

}
