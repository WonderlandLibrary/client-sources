package com.client.glowclient.sponge.mixin;

import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public abstract class MixinKeyBinding implements Comparable<KeyBinding>
{
    @Shadow
    private boolean field_74513_e;
    
    public MixinKeyBinding() {
        super();
    }
    
    @Inject(method = { "isKeyDown" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsKeyDown(final CallbackInfoReturnable callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(this.pressed);
    }
}
