/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class CountNoiseBiasedPlacement
extends SimplePlacement<TopSolidWithNoiseConfig> {
    public CountNoiseBiasedPlacement(Codec<TopSolidWithNoiseConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, TopSolidWithNoiseConfig topSolidWithNoiseConfig, BlockPos blockPos) {
        double d = Biome.INFO_NOISE.noiseAt((double)blockPos.getX() / topSolidWithNoiseConfig.noiseFactor, (double)blockPos.getZ() / topSolidWithNoiseConfig.noiseFactor, true);
        int n = (int)Math.ceil((d + topSolidWithNoiseConfig.noiseOffset) * (double)topSolidWithNoiseConfig.noiseToCountRatio);
        return IntStream.range(0, n).mapToObj(arg_0 -> CountNoiseBiasedPlacement.lambda$getPositions$0(blockPos, arg_0));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (TopSolidWithNoiseConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$getPositions$0(BlockPos blockPos, int n) {
        return blockPos;
    }
}

