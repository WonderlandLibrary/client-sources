/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class AbstractBigMushroomFeature
extends Feature<BigMushroomFeatureConfig> {
    public AbstractBigMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected void func_227210_a_(IWorld iWorld, Random random2, BlockPos blockPos, BigMushroomFeatureConfig bigMushroomFeatureConfig, int n, BlockPos.Mutable mutable) {
        for (int i = 0; i < n; ++i) {
            mutable.setPos(blockPos).move(Direction.UP, i);
            if (iWorld.getBlockState(mutable).isOpaqueCube(iWorld, mutable)) continue;
            this.setBlockState(iWorld, mutable, bigMushroomFeatureConfig.field_227273_b_.getBlockState(random2, blockPos));
        }
    }

    protected int func_227211_a_(Random random2) {
        int n = random2.nextInt(3) + 4;
        if (random2.nextInt(12) == 0) {
            n *= 2;
        }
        return n;
    }

    protected boolean func_227209_a_(IWorld iWorld, BlockPos blockPos, int n, BlockPos.Mutable mutable, BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        int n2 = blockPos.getY();
        if (n2 >= 1 && n2 + n + 1 < 256) {
            Block block = iWorld.getBlockState(blockPos.down()).getBlock();
            if (!AbstractBigMushroomFeature.isDirt(block) && !block.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
                return true;
            }
            for (int i = 0; i <= n; ++i) {
                int n3 = this.func_225563_a_(-1, -1, bigMushroomFeatureConfig.field_227274_c_, i);
                for (int j = -n3; j <= n3; ++j) {
                    for (int k = -n3; k <= n3; ++k) {
                        BlockState blockState = iWorld.getBlockState(mutable.setAndOffset(blockPos, j, i, k));
                        if (blockState.isAir() || blockState.isIn(BlockTags.LEAVES)) continue;
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        BlockPos.Mutable mutable;
        int n = this.func_227211_a_(random2);
        if (!this.func_227209_a_(iSeedReader, blockPos, n, mutable = new BlockPos.Mutable(), bigMushroomFeatureConfig)) {
            return true;
        }
        this.func_225564_a_(iSeedReader, random2, blockPos, n, mutable, bigMushroomFeatureConfig);
        this.func_227210_a_(iSeedReader, random2, blockPos, bigMushroomFeatureConfig, n, mutable);
        return false;
    }

    protected abstract int func_225563_a_(int var1, int var2, int var3, int var4);

    protected abstract void func_225564_a_(IWorld var1, Random var2, BlockPos var3, int var4, BlockPos.Mutable var5, BigMushroomFeatureConfig var6);

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BigMushroomFeatureConfig)iFeatureConfig);
    }
}

