package maxstats.weave.mixin.fpsboost;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {

    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    public void renderParticles(Entity entity, float partialTicks, CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(FPS_Boost.class).isEnabled()) {
            if (FPS_Boost.removeParticles.isEnabled()) {
                ci.cancel();
            }
        }
    }

}