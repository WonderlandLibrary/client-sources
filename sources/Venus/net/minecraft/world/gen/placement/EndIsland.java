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

public class EndIsland
extends SimplePlacement<NoPlacementConfig> {
    public EndIsland(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        Stream<BlockPos> stream = Stream.empty();
        if (random2.nextInt(14) == 0) {
            stream = Stream.concat(stream, Stream.of(blockPos.add(random2.nextInt(16), 55 + random2.nextInt(16), random2.nextInt(16))));
            if (random2.nextInt(4) == 0) {
                stream = Stream.concat(stream, Stream.of(blockPos.add(random2.nextInt(16), 55 + random2.nextInt(16), random2.nextInt(16))));
            }
            return stream;
        }
        return Stream.empty();
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }
}

