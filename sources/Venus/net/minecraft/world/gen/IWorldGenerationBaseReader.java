/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;

public interface IWorldGenerationBaseReader {
    public boolean hasBlockState(BlockPos var1, Predicate<BlockState> var2);

    public BlockPos getHeight(Heightmap.Type var1, BlockPos var2);
}

