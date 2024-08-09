/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.WeepingVineFeature;

public class HugeFungusFeature
extends Feature<HugeFungusConfig> {
    public HugeFungusFeature(Codec<HugeFungusConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, HugeFungusConfig hugeFungusConfig) {
        Block block = hugeFungusConfig.field_236303_f_.getBlock();
        BlockPos blockPos2 = null;
        Block block2 = iSeedReader.getBlockState(blockPos.down()).getBlock();
        if (block2 == block) {
            blockPos2 = blockPos;
        }
        if (blockPos2 == null) {
            return true;
        }
        int n2 = MathHelper.nextInt(random2, 4, 13);
        if (random2.nextInt(12) == 0) {
            n2 *= 2;
        }
        if (!hugeFungusConfig.field_236307_j_) {
            int n = chunkGenerator.func_230355_e_();
            if (blockPos2.getY() + n2 + 1 >= n) {
                return true;
            }
        }
        boolean bl = !hugeFungusConfig.field_236307_j_ && random2.nextFloat() < 0.06f;
        iSeedReader.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 4);
        this.func_236317_a_(iSeedReader, random2, hugeFungusConfig, blockPos2, n2, bl);
        this.func_236321_b_(iSeedReader, random2, hugeFungusConfig, blockPos2, n2, bl);
        return false;
    }

    private static boolean func_236315_a_(IWorld iWorld, BlockPos blockPos, boolean bl) {
        return iWorld.hasBlockState(blockPos, arg_0 -> HugeFungusFeature.lambda$func_236315_a_$0(bl, arg_0));
    }

    private void func_236317_a_(IWorld iWorld, Random random2, HugeFungusConfig hugeFungusConfig, BlockPos blockPos, int n, boolean bl) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockState blockState = hugeFungusConfig.field_236304_g_;
        int n2 = bl ? 1 : 0;
        for (int i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                boolean bl2 = bl && MathHelper.abs(i) == n2 && MathHelper.abs(j) == n2;
                for (int k = 0; k < n; ++k) {
                    mutable.setAndOffset(blockPos, i, k, j);
                    if (!HugeFungusFeature.func_236315_a_(iWorld, mutable, true)) continue;
                    if (hugeFungusConfig.field_236307_j_) {
                        if (!iWorld.getBlockState((BlockPos)mutable.down()).isAir()) {
                            iWorld.destroyBlock(mutable, true);
                        }
                        iWorld.setBlockState(mutable, blockState, 3);
                        continue;
                    }
                    if (bl2) {
                        if (!(random2.nextFloat() < 0.1f)) continue;
                        this.setBlockState(iWorld, mutable, blockState);
                        continue;
                    }
                    this.setBlockState(iWorld, mutable, blockState);
                }
            }
        }
    }

    private void func_236321_b_(IWorld iWorld, Random random2, HugeFungusConfig hugeFungusConfig, BlockPos blockPos, int n, boolean bl) {
        int n2;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        boolean bl2 = hugeFungusConfig.field_236305_h_.isIn(Blocks.NETHER_WART_BLOCK);
        int n3 = Math.min(random2.nextInt(1 + n / 3) + 5, n);
        for (int i = n2 = n - n3; i <= n; ++i) {
            int n4;
            int n5 = n4 = i < n - random2.nextInt(3) ? 2 : 1;
            if (n3 > 8 && i < n2 + 4) {
                n4 = 3;
            }
            if (bl) {
                ++n4;
            }
            for (int j = -n4; j <= n4; ++j) {
                for (int k = -n4; k <= n4; ++k) {
                    boolean bl3 = j == -n4 || j == n4;
                    boolean bl4 = k == -n4 || k == n4;
                    boolean bl5 = !bl3 && !bl4 && i != n;
                    boolean bl6 = bl3 && bl4;
                    boolean bl7 = i < n2 + 3;
                    mutable.setAndOffset(blockPos, j, i, k);
                    if (!HugeFungusFeature.func_236315_a_(iWorld, mutable, false)) continue;
                    if (hugeFungusConfig.field_236307_j_ && !iWorld.getBlockState((BlockPos)mutable.down()).isAir()) {
                        iWorld.destroyBlock(mutable, true);
                    }
                    if (bl7) {
                        if (bl5) continue;
                        this.func_236318_a_(iWorld, random2, mutable, hugeFungusConfig.field_236305_h_, bl2);
                        continue;
                    }
                    if (bl5) {
                        this.func_236316_a_(iWorld, random2, hugeFungusConfig, mutable, 0.1f, 0.2f, bl2 ? 0.1f : 0.0f);
                        continue;
                    }
                    if (bl6) {
                        this.func_236316_a_(iWorld, random2, hugeFungusConfig, mutable, 0.01f, 0.7f, bl2 ? 0.083f : 0.0f);
                        continue;
                    }
                    this.func_236316_a_(iWorld, random2, hugeFungusConfig, mutable, 5.0E-4f, 0.98f, bl2 ? 0.07f : 0.0f);
                }
            }
        }
    }

    private void func_236316_a_(IWorld iWorld, Random random2, HugeFungusConfig hugeFungusConfig, BlockPos.Mutable mutable, float f, float f2, float f3) {
        if (random2.nextFloat() < f) {
            this.setBlockState(iWorld, mutable, hugeFungusConfig.field_236306_i_);
        } else if (random2.nextFloat() < f2) {
            this.setBlockState(iWorld, mutable, hugeFungusConfig.field_236305_h_);
            if (random2.nextFloat() < f3) {
                HugeFungusFeature.func_236319_a_(mutable, iWorld, random2);
            }
        }
    }

    private void func_236318_a_(IWorld iWorld, Random random2, BlockPos blockPos, BlockState blockState, boolean bl) {
        if (iWorld.getBlockState(blockPos.down()).isIn(blockState.getBlock())) {
            this.setBlockState(iWorld, blockPos, blockState);
        } else if ((double)random2.nextFloat() < 0.15) {
            this.setBlockState(iWorld, blockPos, blockState);
            if (bl && random2.nextInt(11) == 0) {
                HugeFungusFeature.func_236319_a_(blockPos, iWorld, random2);
            }
        }
    }

    private static void func_236319_a_(BlockPos blockPos, IWorld iWorld, Random random2) {
        BlockPos.Mutable mutable = blockPos.toMutable().move(Direction.DOWN);
        if (iWorld.isAirBlock(mutable)) {
            int n = MathHelper.nextInt(random2, 1, 5);
            if (random2.nextInt(7) == 0) {
                n *= 2;
            }
            int n2 = 23;
            int n3 = 25;
            WeepingVineFeature.func_236427_a_(iWorld, random2, mutable, n, 23, 25);
        }
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (HugeFungusConfig)iFeatureConfig);
    }

    private static boolean lambda$func_236315_a_$0(boolean bl, BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.getMaterial().isReplaceable() || bl && material == Material.PLANTS;
    }
}

