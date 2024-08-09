package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSkyLightProvider.class)
public class ChunkSkyLightProviderMixin {
    @Inject(method = "recalculateLevel", at = @At("HEAD"), cancellable = true)
    private void recalculateLevel(long long_1, long long_2, int int_1, CallbackInfoReturnable<Integer> infoReturnable) {
        if (Modules.get().get(NoRender.class).noSkylightUpdates()) {
            infoReturnable.setReturnValue(15);
            infoReturnable.cancel();
        }
    }
}
