package net.shoreline.client.mixin.chunk.light;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.chunk.light.RenderSkylightEvent;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ChunkSkyLightProvider
 */
@Mixin(ChunkSkyLightProvider.class)
public class MixinChunkSkylightProvider
{
    /**
     *
     * @param id
     * @param excludedId
     * @param maxLevel
     * @param cir
     */
    @Inject(method = "recalculateLevel", at = @At(value = "HEAD"), cancellable = true)
    private void hookRecalculateLevel(long id, long excludedId, int maxLevel,
                                      CallbackInfoReturnable<Integer> cir)
    {
        RenderSkylightEvent renderSkylightEvent = new RenderSkylightEvent();
        Shoreline.EVENT_HANDLER.dispatch(renderSkylightEvent);
        if (renderSkylightEvent.isCanceled())
        {
            cir.setReturnValue(0);
            cir.cancel();
        }
    }
}
