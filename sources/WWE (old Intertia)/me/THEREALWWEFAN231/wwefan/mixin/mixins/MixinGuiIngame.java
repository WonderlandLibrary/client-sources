package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import net.minecraft.scoreboard.*;

@Mixin(value = { GuiIngame.class }, priority = 9999)
public class MixinGuiIngame
{
    public MixinGuiIngame() {
        super();
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    private void potions(final CallbackInfo callbackInfo) {
        if ((vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).potions.OnGi()) || (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).potionIcon.OnGi())) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderPortal" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPortal(final float n, final ScaledResolution scaledResolution, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).portalOverLay.OnGi()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderScoreboard(final ScoreObjective scoreObjective, final ScaledResolution scaledResolution, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).scoreBoard.OnGi()) {
            callbackInfo.cancel();
        }
    }
}
