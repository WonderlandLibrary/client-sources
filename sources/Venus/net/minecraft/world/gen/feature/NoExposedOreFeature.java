/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class NoExposedOreFeature
extends Feature<OreFeatureConfig> {
    NoExposedOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        int n = random2.nextInt(oreFeatureConfig.size + 1);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n; ++i) {
            this.func_236327_a_(mutable, random2, blockPos, Math.min(i, 7));
            if (!oreFeatureConfig.target.test(iSeedReader.getBlockState(mutable), random2) || this.func_236326_a_(iSeedReader, mutable)) continue;
            iSeedReader.setBlockState(mutable, oreFeatureConfig.state, 2);
        }
        return false;
    }

    private void func_236327_a_(BlockPos.Mutable mutable, Random random2, BlockPos blockPos, int n) {
        int n2 = this.func_236328_a_(random2, n);
        int n3 = this.func_236328_a_(random2, n);
        int n4 = this.func_236328_a_(random2, n);
        mutable.setAndOffset(blockPos, n2, n3, n4);
    }

    private int func_236328_a_(Random random2, int n) {
        return Math.round((random2.nextFloat() - random2.nextFloat()) * (float)n);
    }

    private boolean func_236326_a_(IWorld iWorld, BlockPos blockPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.setAndMove(blockPos, direction);
            if (!iWorld.getBlockState(mutable).isAir()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (OreFeatureConfig)iFeatureConfig);
    }
}

