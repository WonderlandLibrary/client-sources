/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class RangePlacement
extends SimplePlacement<TopSolidRangeConfig> {
    public RangePlacement(Codec<TopSolidRangeConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, TopSolidRangeConfig topSolidRangeConfig, BlockPos blockPos) {
        int n = blockPos.getX();
        int n2 = blockPos.getZ();
        int n3 = random2.nextInt(topSolidRangeConfig.field_242815_e - topSolidRangeConfig.field_242814_d) + topSolidRangeConfig.field_242813_c;
        return Stream.of(new BlockPos(n, n3, n2));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (TopSolidRangeConfig)iPlacementConfig, blockPos);
    }
}

