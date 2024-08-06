package com.shroomclient.shroomclientnextgen.mixin;

import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockOcclusionCache.class)
public class BlockOcclusionCacheMixin {

    @Inject(at = @At("HEAD"), method = "shouldDrawSide", cancellable = true)
    public void shouldDrawSide(
        BlockState selfState,
        BlockView view,
        BlockPos selfPos,
        Direction facing,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (selfState.getBlock() == Blocks.CHEST) {
            cir.setReturnValue(true);
            //BlockRenderHook.onRenderBlock(selfState, selfPos, view, null);
        }
    }
}
