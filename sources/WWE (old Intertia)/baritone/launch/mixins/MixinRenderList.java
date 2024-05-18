package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import baritone.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bva.class })
public class MixinRenderList
{
    public MixinRenderList() {
        super();
    }
    
    @Redirect(method = { "renderChunkLayer" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.popMatrix()V"))
    private void popMatrix() {
        if (a.a().renderCachedChunks.value && bib.z().F() == null) {
            bus.a(770, 771, 1, 0);
        }
        bus.H();
    }
}
