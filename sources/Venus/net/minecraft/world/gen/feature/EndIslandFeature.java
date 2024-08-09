/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EndIslandFeature
extends Feature<NoFeatureConfig> {
    public EndIslandFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        float f = random2.nextInt(3) + 4;
        int n = 0;
        while (f > 0.5f) {
            for (int i = MathHelper.floor(-f); i <= MathHelper.ceil(f); ++i) {
                for (int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j) {
                    if (!((float)(i * i + j * j) <= (f + 1.0f) * (f + 1.0f))) continue;
                    this.setBlockState(iSeedReader, blockPos.add(i, n, j), Blocks.END_STONE.getDefaultState());
                }
            }
            f = (float)((double)f - ((double)random2.nextInt(2) + 0.5));
            --n;
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

