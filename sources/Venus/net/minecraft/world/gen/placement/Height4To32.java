/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class Height4To32
extends SimplePlacement<NoPlacementConfig> {
    public Height4To32(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        int n = 3 + random2.nextInt(6);
        return IntStream.range(0, n).mapToObj(arg_0 -> Height4To32.lambda$getPositions$0(random2, blockPos, arg_0));
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$getPositions$0(Random random2, BlockPos blockPos, int n) {
        int n2 = random2.nextInt(16) + blockPos.getX();
        int n3 = random2.nextInt(16) + blockPos.getZ();
        int n4 = random2.nextInt(28) + 4;
        return new BlockPos(n2, n4, n3);
    }
}

