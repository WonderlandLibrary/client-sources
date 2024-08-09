/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class DepthAveragePlacement
extends SimplePlacement<DepthAverageConfig> {
    public DepthAveragePlacement(Codec<DepthAverageConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, DepthAverageConfig depthAverageConfig, BlockPos blockPos) {
        int n = depthAverageConfig.baseline;
        int n2 = depthAverageConfig.spread;
        int n3 = blockPos.getX();
        int n4 = blockPos.getZ();
        int n5 = random2.nextInt(n2) + random2.nextInt(n2) - n2 + n;
        return Stream.of(new BlockPos(n3, n5, n4));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (DepthAverageConfig)iPlacementConfig, blockPos);
    }
}

