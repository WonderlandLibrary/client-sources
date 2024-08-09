package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Ambience;
import net.minecraft.client.color.world.FoliageColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoliageColors.class)
public class FoliageColorsMixin {
    @Inject(method = "getBirchColor", at = @At("HEAD"), cancellable = true)
    private static void onGetBirchColor(CallbackInfoReturnable<Integer> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customFoliageColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.foliageColorSetting.get().getPacked());
            infoReturnable.cancel();
        }
    }

    @Inject(method = "getSpruceColor", at = @At("HEAD"), cancellable = true)
    private static void onGetSpruceColor(CallbackInfoReturnable<Integer> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customFoliageColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.foliageColorSetting.get().getPacked());
            infoReturnable.cancel();
        }
    }
}
