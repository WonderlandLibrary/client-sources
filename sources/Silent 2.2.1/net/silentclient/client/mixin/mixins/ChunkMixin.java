package net.silentclient.client.mixin.mixins;


import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.silentclient.client.Client;
import net.silentclient.client.hooks.ChunkHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public class ChunkMixin {
    @ModifyArg(
            method = "setBlockState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;relightBlock(III)V", ordinal = 0),
            index = 1
    )
    private int silent$subtractOneFromY(int y) {
        return y - 1;
    }

    /**
     * @author LlamaLad7
     * @reason Optimization
     */
    @Overwrite
    public IBlockState getBlockState(BlockPos pos) {
        return ChunkHook.getBlockState((Chunk) (Object) this, pos);
    }

    @Inject(method = {"getLightFor", "getLightSubtracted"}, at = @At("HEAD"), cancellable = true)
    private void patchFullbright(CallbackInfoReturnable<Integer> cir) {
        if (Client.getInstance().getModInstances().getFullBrightMod().isEnabled()) {
            cir.setReturnValue(15);
        }
    }
}
