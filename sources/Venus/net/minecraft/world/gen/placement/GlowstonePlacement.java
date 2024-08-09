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

public class GlowstonePlacement
extends SimplePlacement<FeatureSpreadConfig> {
    public GlowstonePlacement(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, FeatureSpreadConfig featureSpreadConfig, BlockPos blockPos) {
        return IntStream.range(0, random2.nextInt(random2.nextInt(featureSpreadConfig.func_242799_a().func_242259_a(random2)) + 1)).mapToObj(arg_0 -> GlowstonePlacement.lambda$getPositions$0(random2, blockPos, arg_0));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (FeatureSpreadConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$getPositions$0(Random random2, BlockPos blockPos, int n) {
        int n2 = random2.nextInt(16) + blockPos.getX();
        int n3 = random2.nextInt(16) + blockPos.getZ();
        int n4 = random2.nextInt(120) + 4;
        return new BlockPos(n2, n4, n3);
    }
}

