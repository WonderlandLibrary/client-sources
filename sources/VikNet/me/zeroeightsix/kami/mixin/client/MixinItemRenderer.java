package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.module.modules.render.NoRender;

import net.minecraft.client.renderer.ItemRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void renderFireInFirstPerson(CallbackInfo info) {
		if (NoRender.enabled() && NoRender.fire.getValue()) {
			info.cancel();
		}
    }
	
}