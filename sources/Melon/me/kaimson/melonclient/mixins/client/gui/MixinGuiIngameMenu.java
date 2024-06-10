package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.utils.*;

@Mixin({ axp.class })
public class MixinGuiIngameMenu extends axu
{
    @Inject(method = { "initGui" }, at = { @At("TAIL") })
    private void initGui(final CallbackInfo ci) {
        this.n.add(new avs(-1, this.l / 2 - 100, this.m / 4 + 56, 200, 20, "Serverlist"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    private void actionPerformed(final avs button, final CallbackInfo ci) {
        if (button.k == -1) {
            this.j.a((axu)new azh((axu)this));
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("TAIL") })
    private void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        WatermarkRenderer.render(this.l, this.m);
    }
}
