package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.exploits.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityPig.class }, priority = 9999)
public class MixinEntityPig
{
    public MixinEntityPig() {
        super();
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    private void isHorseSaddled(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<EntityRide>)ASkY.class).IuiN()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
