package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.*;

@Mixin({ avo.class })
public class MixinGuiIngame extends avp
{
    @Shadow
    @Final
    private ave j;
    
    @Inject(method = { "renderGameOverlay" }, at = { @At("RETURN") })
    private void renderGameOverlay(final float partialTicks, final CallbackInfo ci) {
        Client.INSTANCE.onRenderOverlay();
    }
    
    @Redirect(method = { "renderGameOverlay" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;showCrosshair()Z"))
    private boolean showCrosshair(final avo guiIngame) {
        if (this.j.t.aC && !this.j.h.cq() && !this.j.t.w) {
            return false;
        }
        if (!this.j.c.a()) {
            return CrosshairModule.INSTANCE.showInThird.getBoolean() || this.j.t.aB == 0;
        }
        if (this.j.i != null) {
            return true;
        }
        if (this.j.s != null && this.j.s.a == auh.a.b) {
            final cj blockpos = this.j.s.a();
            if (this.j.f.s(blockpos) instanceof og) {
                return true;
            }
        }
        return false;
    }
    
    @Redirect(method = { "renderGameOverlay" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V"))
    private void renderCrosshair(final avo guiIngame, final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        if (ModuleConfig.INSTANCE.isEnabled(CrosshairModule.INSTANCE)) {
            final avr sr = new avr(ave.A());
            CrosshairModule.INSTANCE.render(guiIngame, sr.a() / 2, sr.b() / 2, x, y);
        }
        else {
            guiIngame.b(x, y, textureX, textureY, width, height);
        }
    }
}
