package maxstats.weave.mixin;

import me.sleepyfish.smok.Smok;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Class from SMok Client by SleepyFish
@Mixin(MovingObjectPosition.class)
public class MixinMovingObjectPosition {

    @Inject(method = {"getBlockPos"}, at = {@At("HEAD")}, cancellable = true)
    public void getBlockPos(CallbackInfoReturnable<BlockPos> cir) {
        if (Smok.inst.rotManager.isRotating() && Smok.inst.rotManager.rayTracePos != null) {
            cir.setReturnValue(Smok.inst.rotManager.rayTracePos);
        }
    }

}