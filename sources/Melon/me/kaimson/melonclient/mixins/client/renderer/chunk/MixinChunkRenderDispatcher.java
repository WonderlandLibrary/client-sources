package me.kaimson.melonclient.mixins.client.renderer.chunk;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bho.class })
public class MixinChunkRenderDispatcher
{
    @Inject(method = { "getNextChunkUpdate" }, at = { @At("HEAD") })
    private void limitChunkUpdates(final CallbackInfoReturnable<bhn> cir) throws InterruptedException {
        final int mode = SettingsManager.INSTANCE.chunkUpdates.getInt();
        if (mode > 0) {
            Thread.sleep((mode == 1) ? 15L : ((mode == 2) ? 50L : ((mode == 3) ? 110L : ((mode == 4) ? 150L : ((mode == 5) ? 200L : -1L)))));
        }
    }
}
