package xyz.mathax.mathaxclient.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.player.PotionSaver;
import xyz.mathax.mathaxclient.utils.Utils;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin {
    @Shadow
    int duration;

    @Inject(method = "updateDuration", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfoReturnable<Integer> infoReturnable) {
        if (!Utils.canUpdate()) {
            return;
        }

        if (Modules.get().get(PotionSaver.class).shouldFreeze(((StatusEffectInstance) (Object) this).getEffectType())) {
            infoReturnable.setReturnValue(duration);
        }
    }
}
