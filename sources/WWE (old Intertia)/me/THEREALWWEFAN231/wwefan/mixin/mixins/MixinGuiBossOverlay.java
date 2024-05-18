package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiBossOverlay.class }, priority = 9999)
public class MixinGuiBossOverlay
{
    public MixinGuiBossOverlay() {
        super();
    }
    
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    private void getPlayerCooldownPeriod(final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).wither.OnGi()) {
            callbackInfo.cancel();
        }
    }
}
