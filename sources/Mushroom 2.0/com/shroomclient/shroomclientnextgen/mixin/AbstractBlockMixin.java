package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.impl.player.FastBreak;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    /**
     * @author scoliosis
     * @reason fastbreak
     */
    @Inject(
        at = @At("HEAD"),
        method = "calcBlockBreakingDelta",
        cancellable = true
    )
    public void calcBlockBreakingDelta(
        BlockState state,
        PlayerEntity player,
        BlockView world,
        BlockPos pos,
        CallbackInfoReturnable<Float> cir
    ) {
        float f = state.getHardness(world, pos);
        if (f == -1.0F) {
            cir.setReturnValue(0.0F);
        } else {
            int i = player.canHarvest(state) ? 30 : 100;
            cir.setReturnValue(
                (player.getBlockBreakingSpeed(state) / f / (float) i) *
                FastBreak.breakSpeed()
            );
        }
    }
}
