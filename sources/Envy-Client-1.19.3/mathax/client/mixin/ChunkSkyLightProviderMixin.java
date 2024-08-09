package mathax.client.mixin;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.render.NoRender;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSkyLightProvider.class)
public class ChunkSkyLightProviderMixin {
    @Inject(at = @At("HEAD"), method = "recalculateLevel", cancellable = true)
    private void recalculateLevel(long long_1, long long_2, int int_1, CallbackInfoReturnable<Integer> info) {

        if (Modules.get().get(NoRender.class).noSkylightUpdates()) {
            //return;
            info.setReturnValue(15);
            info.cancel();
        }
    }
}
