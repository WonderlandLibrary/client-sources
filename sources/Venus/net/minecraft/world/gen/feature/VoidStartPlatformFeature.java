/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class VoidStartPlatformFeature
extends Feature<NoFeatureConfig> {
    private static final BlockPos VOID_SPAWN_POS = new BlockPos(8, 3, 8);
    private static final ChunkPos VOID_SPAWN_CHUNK_POS = new ChunkPos(VOID_SPAWN_POS);

    public VoidStartPlatformFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static int distance(int n, int n2, int n3, int n4) {
        return Math.max(Math.abs(n - n3), Math.abs(n2 - n4));
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        if (VoidStartPlatformFeature.distance(chunkPos.x, chunkPos.z, VoidStartPlatformFeature.VOID_SPAWN_CHUNK_POS.x, VoidStartPlatformFeature.VOID_SPAWN_CHUNK_POS.z) > 1) {
            return false;
        }
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = chunkPos.getZStart(); i <= chunkPos.getZEnd(); ++i) {
            for (int j = chunkPos.getXStart(); j <= chunkPos.getXEnd(); ++j) {
                if (VoidStartPlatformFeature.distance(VOID_SPAWN_POS.getX(), VOID_SPAWN_POS.getZ(), j, i) > 16) continue;
                mutable.setPos(j, VOID_SPAWN_POS.getY(), i);
                if (mutable.equals(VOID_SPAWN_POS)) {
                    iSeedReader.setBlockState(mutable, Blocks.COBBLESTONE.getDefaultState(), 2);
                    continue;
                }
                iSeedReader.setBlockState(mutable, Blocks.STONE.getDefaultState(), 2);
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

