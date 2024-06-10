package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.utils.*;

@Mixin({ ayl.class })
public abstract class MixinGuiContainer extends axu
{
    @Shadow
    protected abstract boolean b(final int p0);
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") })
    private void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo ci) {
        this.b(mouseButton - 100);
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("TAIL") })
    private void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        WatermarkRenderer.render(this.l, this.m);
    }
}
