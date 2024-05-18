package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.exploits.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityLlama.class }, priority = 9999)
public class MixinEntityLlama
{
    public MixinEntityLlama() {
        super();
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void canBeSteered(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        try {
            if (vlwv.hNxW.qHPF((Class<EntityRide>)ASkY.class).IuiN()) {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
        catch (Exception ex) {}
    }
}
