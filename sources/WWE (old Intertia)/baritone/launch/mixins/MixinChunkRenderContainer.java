package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import baritone.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bun.class })
public class MixinChunkRenderContainer
{
    public MixinChunkRenderContainer() {
        super();
    }
    
    @Redirect(method = { "preRenderChunk" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/chunk/RenderChunk.getPosition()Lnet/minecraft/util/math/BlockPos;"))
    private et getPosition(final bxr bxr) {
        if (a.a().renderCachedChunks.value && bib.z().F() == null && bib.z().f.f(bxr.k()).f()) {
            bus.e();
            bus.m();
            GL14.glBlendColor(0.0f, 0.0f, 0.0f, (float)a.a().cachedChunksOpacity.value);
            bus.a(32771, 32772, 1, 0);
        }
        return bxr.k();
    }
}
