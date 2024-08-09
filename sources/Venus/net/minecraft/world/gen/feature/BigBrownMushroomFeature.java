/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigBrownMushroomFeature
extends AbstractBigMushroomFeature {
    public BigBrownMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void func_225564_a_(IWorld iWorld, Random random2, BlockPos blockPos, int n, BlockPos.Mutable mutable, BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        int n2 = bigMushroomFeatureConfig.field_227274_c_;
        for (int i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                boolean bl;
                boolean bl2 = i == -n2;
                boolean bl3 = i == n2;
                boolean bl4 = j == -n2;
                boolean bl5 = j == n2;
                boolean bl6 = bl2 || bl3;
                boolean bl7 = bl = bl4 || bl5;
                if (bl6 && bl) continue;
                mutable.setAndOffset(blockPos, i, n, j);
                if (iWorld.getBlockState(mutable).isOpaqueCube(iWorld, mutable)) continue;
                boolean bl8 = bl2 || bl && i == 1 - n2;
                boolean bl9 = bl3 || bl && i == n2 - 1;
                boolean bl10 = bl4 || bl6 && j == 1 - n2;
                boolean bl11 = bl5 || bl6 && j == n2 - 1;
                this.setBlockState(iWorld, mutable, (BlockState)((BlockState)((BlockState)((BlockState)bigMushroomFeatureConfig.field_227272_a_.getBlockState(random2, blockPos).with(HugeMushroomBlock.WEST, bl8)).with(HugeMushroomBlock.EAST, bl9)).with(HugeMushroomBlock.NORTH, bl10)).with(HugeMushroomBlock.SOUTH, bl11));
            }
        }
    }

    @Override
    protected int func_225563_a_(int n, int n2, int n3, int n4) {
        return n4 <= 3 ? 0 : n3;
    }
}

