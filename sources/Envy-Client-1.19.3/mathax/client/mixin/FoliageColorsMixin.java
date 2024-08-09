package mathax.client.mixin;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Ambience;
import net.minecraft.client.color.world.FoliageColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoliageColors.class)
public class FoliageColorsMixin {
    @Inject(method = "getBirchColor", at = @At("HEAD"), cancellable = true)
    private static void onGetBirchColor(CallbackInfoReturnable<Integer> info) {
        Ambience ambience = Modules.get().get(Ambience.class);

        if (ambience.isActive() && ambience.customFoliageColor.get()) {
            info.setReturnValue(ambience.foliageColor.get().getPacked());
            info.cancel();
        }
    }

    @Inject(method = "getSpruceColor", at = @At("HEAD"), cancellable = true)
    private static void onGetSpruceColor(CallbackInfoReturnable<Integer> info) {
        Ambience ambience = Modules.get().get(Ambience.class);

        if (ambience.isActive() && ambience.customFoliageColor.get()) {
            info.setReturnValue(ambience.foliageColor.get().getPacked());
            info.cancel();
        }
    }
}
