package xyz.mathax.mathaxclient.mixin;

import net.minecraft.block.SlimeBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {
    /*@Inject(method = "onSteppedOn", at = @At("HEAD"), cancellable = true)
    private void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (Modules.get().get(NoSlow.class).slimeBlock() && entity == mc.player) {
            info.cancel();
        }
    }*/
}
