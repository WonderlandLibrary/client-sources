/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class CountExtraPlacement
extends SimplePlacement<AtSurfaceWithExtraConfig> {
    public CountExtraPlacement(Codec<AtSurfaceWithExtraConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, AtSurfaceWithExtraConfig atSurfaceWithExtraConfig, BlockPos blockPos) {
        int n = atSurfaceWithExtraConfig.count + (random2.nextFloat() < atSurfaceWithExtraConfig.extraChance ? atSurfaceWithExtraConfig.extraCount : 0);
        return IntStream.range(0, n).mapToObj(arg_0 -> CountExtraPlacement.lambda$getPositions$0(blockPos, arg_0));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (AtSurfaceWithExtraConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$getPositions$0(BlockPos blockPos, int n) {
        return blockPos;
    }
}

