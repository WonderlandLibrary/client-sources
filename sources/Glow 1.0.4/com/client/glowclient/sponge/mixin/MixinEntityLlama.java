package com.client.glowclient.sponge.mixin;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityLlama.class })
public abstract class MixinEntityLlama extends AbstractChestHorse implements IRangedAttackMob
{
    public MixinEntityLlama() {
        super((World)null);
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void preCanBeSteered(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v5) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
