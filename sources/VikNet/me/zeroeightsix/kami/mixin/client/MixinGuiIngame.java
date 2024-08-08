package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.module.modules.render.NoRender;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {
	
	@Inject(method = "renderPortal", at = @At("HEAD"), cancellable = true)
    protected void renderPortal(float n, ScaledResolution scaledResolution, CallbackInfo info) {
		if (NoRender.enabled() && NoRender.portalOverlay.getValue()) {
			info.cancel();
		}
    }

}
