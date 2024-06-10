package maxstats.weave.mixin;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.blatant.Hitboxes;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Class from SMok Client by SleepyFish
@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "getCollisionBorderSize", at = @At("HEAD"), cancellable = true)
    private void getCollisionBorderSize(final CallbackInfoReturnable<Float> ci) {
        if (Smok.inst.ratManager.getRatByClass(Hitboxes.class).isEnabled()) {
            double expand = Hitboxes.expand.getValue();
            ci.setReturnValue(0.1F + (float) expand);
        } else {
            ci.setReturnValue(0.0F);
        }
    }

}