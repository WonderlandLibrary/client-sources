package maxstats.weave.mixin;

import me.sleepyfish.smok.Smok;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Class from SMok Client by SleepyFish
@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Shadow public abstract ResourceLocation getLocationSkin();

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> ci) {
        if (Smok.inst.injected) {
            if (this.getLocationSkin() == Smok.inst.mc.thePlayer.getLocationSkin()) {
                if (Smok.inst.capeManager.getCurrentCape() != null) {
                    ci.setReturnValue(Smok.inst.capeManager.getCurrentCape().getFile());
                }
            }
        }
    }

}