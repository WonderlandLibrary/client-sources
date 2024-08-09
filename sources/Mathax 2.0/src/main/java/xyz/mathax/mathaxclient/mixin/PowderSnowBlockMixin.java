package xyz.mathax.mathaxclient.mixin;

import net.minecraft.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    /*@Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void onCanWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (entity == mc.player && Modules.get().get(Jesus.class).canWalkOnPowderSnow()) {
            infoReturnable.setReturnValue(true);
        }
    }*/
}
