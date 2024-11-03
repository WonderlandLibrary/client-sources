package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderDispatcher.class)
public class ChunkRenderDispatcherMixin {
    @Inject(method = "getNextChunkUpdate", at = @At("HEAD"))
    public void lazyChunkLoading(CallbackInfoReturnable<ChunkCompileTaskGenerator> cir) {
        if(Client.getInstance().getModInstances() != null && Client.getInstance().getSettingsManager() != null) {
            int mode = 0;

            switch (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Lazy Chunk Loading").getValString()) {
                case "Quality":
                    mode = 1;
                    break;
                case "Balance":
                    mode = 2;
                    break;
                case "Performance":
                    mode = 4;
                    break;
            }

            if(mode != 0) {
                try {
                    Thread.sleep((mode == 1) ? 15L : ((mode == 2) ? 50L : ((mode == 3) ? 110L : ((mode == 4) ? 150L : ((mode == 5) ? 200L : -1L)))));
                } catch (Exception err) {
                    Client.logger.catching(err);
                }
            }
        }
    }
}
