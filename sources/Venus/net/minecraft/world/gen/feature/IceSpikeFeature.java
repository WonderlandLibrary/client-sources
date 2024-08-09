/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class IceSpikeFeature
extends Feature<NoFeatureConfig> {
    public IceSpikeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        int n;
        int n2;
        while (iSeedReader.isAirBlock(blockPos) && blockPos.getY() > 2) {
            blockPos = blockPos.down();
        }
        if (!iSeedReader.getBlockState(blockPos).isIn(Blocks.SNOW_BLOCK)) {
            return true;
        }
        blockPos = blockPos.up(random2.nextInt(4));
        int n3 = random2.nextInt(4) + 7;
        int n4 = n3 / 4 + random2.nextInt(2);
        if (n4 > 1 && random2.nextInt(60) == 0) {
            blockPos = blockPos.up(10 + random2.nextInt(30));
        }
        for (n2 = 0; n2 < n3; ++n2) {
            float f = (1.0f - (float)n2 / (float)n3) * (float)n4;
            n = MathHelper.ceil(f);
            for (int i = -n; i <= n; ++i) {
                float f2 = (float)MathHelper.abs(i) - 0.25f;
                for (int j = -n; j <= n; ++j) {
                    float f3 = (float)MathHelper.abs(j) - 0.25f;
                    if ((i != 0 || j != 0) && f2 * f2 + f3 * f3 > f * f || (i == -n || i == n || j == -n || j == n) && random2.nextFloat() > 0.75f) continue;
                    BlockState blockState = iSeedReader.getBlockState(blockPos.add(i, n2, j));
                    Block block = blockState.getBlock();
                    if (blockState.isAir() || IceSpikeFeature.isDirt(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                        this.setBlockState(iSeedReader, blockPos.add(i, n2, j), Blocks.PACKED_ICE.getDefaultState());
                    }
                    if (n2 == 0 || n <= 1) continue;
                    blockState = iSeedReader.getBlockState(blockPos.add(i, -n2, j));
                    block = blockState.getBlock();
                    if (!blockState.isAir() && !IceSpikeFeature.isDirt(block) && block != Blocks.SNOW_BLOCK && block != Blocks.ICE) continue;
                    this.setBlockState(iSeedReader, blockPos.add(i, -n2, j), Blocks.PACKED_ICE.getDefaultState());
                }
            }
        }
        n2 = n4 - 1;
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > 1) {
            n2 = 1;
        }
        for (int i = -n2; i <= n2; ++i) {
            block5: for (n = -n2; n <= n2; ++n) {
                BlockPos blockPos2 = blockPos.add(i, -1, n);
                int n5 = 50;
                if (Math.abs(i) == 1 && Math.abs(n) == 1) {
                    n5 = random2.nextInt(5);
                }
                while (blockPos2.getY() > 50) {
                    BlockState blockState = iSeedReader.getBlockState(blockPos2);
                    Block block = blockState.getBlock();
                    if (!blockState.isAir() && !IceSpikeFeature.isDirt(block) && block != Blocks.SNOW_BLOCK && block != Blocks.ICE && block != Blocks.PACKED_ICE) continue block5;
                    this.setBlockState(iSeedReader, blockPos2, Blocks.PACKED_ICE.getDefaultState());
                    blockPos2 = blockPos2.down();
                    if (--n5 > 0) continue;
                    blockPos2 = blockPos2.down(random2.nextInt(5) + 1);
                    n5 = random2.nextInt(5);
                }
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

