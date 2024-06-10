package me.kaimson.melonclient.mixins.client.settings;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ avb.class })
public class MixinKeyBinding
{
    @Inject(method = { "onTick" }, at = { @At("HEAD") })
    private static void onTick(final int keyCode, final CallbackInfo ci) {
        if (keyCode != 0) {
            KeyBinding.keyBindings.forEach(KeyBinding::onTick);
        }
    }
    
    @Inject(method = { "setKeyBindState" }, at = { @At("HEAD") })
    private static void setKeyBindState(final int keyCode, final boolean pressed, final CallbackInfo ci) {
        if (keyCode != 0) {
            KeyBinding.keyBindings.stream().filter(bind -> bind.getKeyCode() == keyCode).forEach(bind -> bind.setKeyBindState(keyCode, pressed));
        }
    }
}
