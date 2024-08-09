/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;

public class ExplosionContext {
    public Optional<Float> getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return blockState.isAir() && fluidState.isEmpty() ? Optional.empty() : Optional.of(Float.valueOf(Math.max(blockState.getBlock().getExplosionResistance(), fluidState.getExplosionResistance())));
    }

    public boolean canExplosionDestroyBlock(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, float f) {
        return false;
    }
}

