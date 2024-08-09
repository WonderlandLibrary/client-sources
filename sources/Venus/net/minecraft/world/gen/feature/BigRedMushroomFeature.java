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

public class BigRedMushroomFeature
extends AbstractBigMushroomFeature {
    public BigRedMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void func_225564_a_(IWorld iWorld, Random random2, BlockPos blockPos, int n, BlockPos.Mutable mutable, BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        for (int i = n - 3; i <= n; ++i) {
            int n2 = i < n ? bigMushroomFeatureConfig.field_227274_c_ : bigMushroomFeatureConfig.field_227274_c_ - 1;
            int n3 = bigMushroomFeatureConfig.field_227274_c_ - 2;
            for (int j = -n2; j <= n2; ++j) {
                for (int k = -n2; k <= n2; ++k) {
                    boolean bl;
                    boolean bl2 = j == -n2;
                    boolean bl3 = j == n2;
                    boolean bl4 = k == -n2;
                    boolean bl5 = k == n2;
                    boolean bl6 = bl2 || bl3;
                    boolean bl7 = bl = bl4 || bl5;
                    if (i < n && bl6 == bl) continue;
                    mutable.setAndOffset(blockPos, j, i, k);
                    if (iWorld.getBlockState(mutable).isOpaqueCube(iWorld, mutable)) continue;
                    this.setBlockState(iWorld, mutable, (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)bigMushroomFeatureConfig.field_227272_a_.getBlockState(random2, blockPos).with(HugeMushroomBlock.UP, i >= n - 1)).with(HugeMushroomBlock.WEST, j < -n3)).with(HugeMushroomBlock.EAST, j > n3)).with(HugeMushroomBlock.NORTH, k < -n3)).with(HugeMushroomBlock.SOUTH, k > n3));
                }
            }
        }
    }

    @Override
    protected int func_225563_a_(int n, int n2, int n3, int n4) {
        int n5 = 0;
        if (n4 < n2 && n4 >= n2 - 3) {
            n5 = n3;
        } else if (n4 == n2) {
            n5 = n3;
        }
        return n5;
    }
}

