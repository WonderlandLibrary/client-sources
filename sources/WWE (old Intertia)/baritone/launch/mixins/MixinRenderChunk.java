package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import baritone.*;
import baritone.api.*;
import baritone.api.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bxr.class })
public class MixinRenderChunk
{
    public MixinRenderChunk() {
        super();
    }
    
    @Redirect(method = { "rebuildChunk" }, at = @At(value = "INVOKE", target = "net/minecraft/world/ChunkCache.isEmpty()Z"))
    private boolean isEmpty(final and and) {
        if (!and.ac()) {
            return false;
        }
        final a a;
        final IPlayerContext playerContext;
        if (baritone.a.a().renderCachedChunks.value && bib.z().F() == null && (playerContext = (a = (a)BaritoneAPI.getProvider().getPrimaryBaritone()).getPlayerContext()).player() != null && playerContext.world() != null && a.a != null) {
            final et k = ((bxr)this).k();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    if (a.a.b((i << 4) + k.p(), (j << 4) + k.r())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Redirect(method = { "rebuildChunk" }, at = @At(value = "INVOKE", target = "net/minecraft/world/ChunkCache.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"))
    private awt getBlockState(final and and, final et et) {
        final a a;
        final IPlayerContext playerContext;
        if (baritone.a.a().renderCachedChunks.value && bib.z().F() == null && (playerContext = (a = (a)BaritoneAPI.getProvider().getPrimaryBaritone()).getPlayerContext()).player() != null && playerContext.world() != null && a.a != null) {
            return a.a.a(et);
        }
        return and.o(et);
    }
}
