package maxstats.weave.mixin.fpsboost;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Inject(method = "renderRainSnow", at = @At("HEAD"), cancellable = true)
    public void renderRainSnow(float v, CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(FPS_Boost.class).isEnabled()) {
            if (FPS_Boost.removeRainAndSnow.isEnabled()) {
                ci.cancel();
            }
        }
    }

}