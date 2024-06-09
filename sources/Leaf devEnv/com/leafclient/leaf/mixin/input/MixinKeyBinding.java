package com.leafclient.leaf.mixin.input;

import com.leafclient.leaf.extension.ExtensionKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding implements ExtensionKeyBinding {

    @Shadow private boolean pressed;

    @Override
    public boolean isKeyPressed() {
        return pressed;
    }

    @Override
    public void setKeyPressed(boolean isKeyPressed) {
        pressed = isKeyPressed;
    }

    @Inject(
            method = "isKeyDown",
            at = @At("INVOKE"),
            cancellable = true
    )
    private void redirect$keyDown(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(pressed);
        info.cancel();
    }

}
