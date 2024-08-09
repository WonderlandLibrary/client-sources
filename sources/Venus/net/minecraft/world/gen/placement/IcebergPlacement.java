/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class IcebergPlacement
extends SimplePlacement<NoPlacementConfig> {
    public IcebergPlacement(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        int n = random2.nextInt(8) + 4 + blockPos.getX();
        int n2 = random2.nextInt(8) + 4 + blockPos.getZ();
        return Stream.of(new BlockPos(n, blockPos.getY(), n2));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }
}

