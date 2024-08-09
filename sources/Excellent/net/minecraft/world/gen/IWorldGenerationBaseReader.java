package net.minecraft.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public interface IWorldGenerationBaseReader
{
    boolean hasBlockState(BlockPos pos, Predicate<BlockState> state);

    BlockPos getHeight(Heightmap.Type heightmapType, BlockPos pos);
}
