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
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class CountNoisePlacement
extends Placement<NoiseDependant> {
    public CountNoisePlacement(Codec<NoiseDependant> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, NoiseDependant noiseDependant, BlockPos blockPos) {
        double d = Biome.INFO_NOISE.noiseAt((double)blockPos.getX() / 200.0, (double)blockPos.getZ() / 200.0, true);
        int n = d < noiseDependant.noiseLevel ? noiseDependant.belowNoise : noiseDependant.aboveNoise;
        return IntStream.range(0, n).mapToObj(arg_0 -> CountNoisePlacement.lambda$func_241857_a$0(blockPos, arg_0));
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (NoiseDependant)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$func_241857_a$0(BlockPos blockPos, int n) {
        return blockPos;
    }
}

