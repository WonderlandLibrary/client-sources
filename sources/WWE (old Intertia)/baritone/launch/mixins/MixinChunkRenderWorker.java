package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import baritone.*;
import baritone.api.*;
import baritone.api.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bxn.class })
public abstract class MixinChunkRenderWorker
{
    public MixinChunkRenderWorker() {
        super();
    }
    
    @Shadow
    protected abstract boolean a(final et p0, final amu p1);
    
    @Redirect(method = { "processTask" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/chunk/ChunkRenderWorker.isChunkExisting(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z"))
    private boolean isChunkExisting(final bxn bxn, final et et, final amu amu) {
        final a a;
        final IPlayerContext playerContext;
        if (baritone.a.a().renderCachedChunks.value && bib.z().F() == null && (playerContext = (a = (a)BaritoneAPI.getProvider().getPrimaryBaritone()).getPlayerContext()).player() != null && playerContext.world() != null && a.a != null) {
            return a.a.b(et.p(), et.r()) || this.a(et, amu);
        }
        return this.a(et, amu);
    }
}
