/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class CountPlacement
extends SimplePlacement<FeatureSpreadConfig> {
    public CountPlacement(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, FeatureSpreadConfig featureSpreadConfig, BlockPos blockPos) {
        return IntStream.range(0, featureSpreadConfig.func_242799_a().func_242259_a(random2)).mapToObj(arg_0 -> CountPlacement.lambda$getPositions$0(blockPos, arg_0));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (FeatureSpreadConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$getPositions$0(BlockPos blockPos, int n) {
        return blockPos;
    }
}

