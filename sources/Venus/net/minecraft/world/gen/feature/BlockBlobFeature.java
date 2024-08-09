/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockBlobFeature
extends Feature<BlockStateFeatureConfig> {
    public BlockBlobFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockStateFeatureConfig blockStateFeatureConfig) {
        while (true) {
            Block block;
            if (blockPos.getY() <= 3 || !iSeedReader.isAirBlock(blockPos.down()) && (BlockBlobFeature.isDirt(block = iSeedReader.getBlockState(blockPos.down()).getBlock()) || BlockBlobFeature.isStone(block))) {
                if (blockPos.getY() <= 3) {
                    return true;
                }
                for (int i = 0; i < 3; ++i) {
                    int n = random2.nextInt(2);
                    int n2 = random2.nextInt(2);
                    int n3 = random2.nextInt(2);
                    float f = (float)(n + n2 + n3) * 0.333f + 0.5f;
                    for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-n, -n2, -n3), blockPos.add(n, n2, n3))) {
                        if (!(blockPos2.distanceSq(blockPos) <= (double)(f * f))) continue;
                        iSeedReader.setBlockState(blockPos2, blockStateFeatureConfig.state, 4);
                    }
                    blockPos = blockPos.add(-1 + random2.nextInt(2), -random2.nextInt(2), -1 + random2.nextInt(2));
                }
                return false;
            }
            blockPos = blockPos.down();
        }
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockStateFeatureConfig)iFeatureConfig);
    }
}

