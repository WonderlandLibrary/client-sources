package xyz.mathax.mathaxclient.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.movement.EntityControl;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin {
    @Inject(method = "isSaddled", at = @At("HEAD"), cancellable = true)
    private void isSaddled(CallbackInfoReturnable<Boolean> infoReturnable) {
        if (Modules.get().isEnabled(EntityControl.class)) {
            infoReturnable.setReturnValue(true);
        }
    }
}