package com.client.glowclient.sponge.mixin;

import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractHorse.class })
public abstract class MixinAbstractHorse extends EntityAnimal implements IInventoryChangedListener, IJumpingMount
{
    @Shadow
    protected abstract boolean getHorseWatchableBoolean(final int p0);
    
    public MixinAbstractHorse() {
        super((World)HookTranslator.mc.world);
    }
    
    @Inject(method = { "isHorseSaddled" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsHorseSaddled(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v5) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
