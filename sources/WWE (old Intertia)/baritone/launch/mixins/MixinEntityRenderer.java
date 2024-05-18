package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import baritone.api.*;
import baritone.api.event.events.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ buq.class })
public class MixinEntityRenderer
{
    public MixinEntityRenderer() {
        super();
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = { "ldc=hand" }) })
    private void renderWorldPass(final int n, final float n2, final long n3, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            iterator.next().getGameEventHandler().onRenderPass(new RenderEvent(n2));
        }
    }
}
