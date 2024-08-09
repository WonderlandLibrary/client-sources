/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class CaveEdge
extends Placement<CaveEdgeConfig> {
    public CaveEdge(Codec<CaveEdgeConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, CaveEdgeConfig caveEdgeConfig, BlockPos blockPos) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        BitSet bitSet = worldDecoratingHelper.func_242892_a(chunkPos, caveEdgeConfig.step);
        return IntStream.range(0, bitSet.length()).filter(arg_0 -> CaveEdge.lambda$func_241857_a$0(bitSet, random2, caveEdgeConfig, arg_0)).mapToObj(arg_0 -> CaveEdge.lambda$func_241857_a$1(chunkPos, arg_0));
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (CaveEdgeConfig)iPlacementConfig, blockPos);
    }

    private static BlockPos lambda$func_241857_a$1(ChunkPos chunkPos, int n) {
        int n2 = n & 0xF;
        int n3 = n >> 4 & 0xF;
        int n4 = n >> 8;
        return new BlockPos(chunkPos.getXStart() + n2, n4, chunkPos.getZStart() + n3);
    }

    private static boolean lambda$func_241857_a$0(BitSet bitSet, Random random2, CaveEdgeConfig caveEdgeConfig, int n) {
        return bitSet.get(n) && random2.nextFloat() < caveEdgeConfig.probability;
    }
}

