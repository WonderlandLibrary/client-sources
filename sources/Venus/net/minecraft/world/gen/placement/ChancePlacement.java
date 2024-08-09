/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class ChancePlacement
extends SimplePlacement<ChanceConfig> {
    public ChancePlacement(Codec<ChanceConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, ChanceConfig chanceConfig, BlockPos blockPos) {
        return random2.nextFloat() < 1.0f / (float)chanceConfig.chance ? Stream.of(blockPos) : Stream.empty();
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (ChanceConfig)iPlacementConfig, blockPos);
    }
}

