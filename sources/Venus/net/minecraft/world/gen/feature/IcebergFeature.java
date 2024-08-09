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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class IcebergFeature
extends Feature<BlockStateFeatureConfig> {
    public IcebergFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockStateFeatureConfig blockStateFeatureConfig) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        blockPos = new BlockPos(blockPos.getX(), chunkGenerator.func_230356_f_(), blockPos.getZ());
        boolean bl = random2.nextDouble() > 0.7;
        BlockState blockState = blockStateFeatureConfig.state;
        double d = random2.nextDouble() * 2.0 * Math.PI;
        int n6 = 11 - random2.nextInt(5);
        int n7 = 3 + random2.nextInt(3);
        boolean bl2 = random2.nextDouble() > 0.7;
        int n8 = 11;
        int n9 = n5 = bl2 ? random2.nextInt(6) + 6 : random2.nextInt(15) + 3;
        if (!bl2 && random2.nextDouble() > 0.9) {
            n5 += random2.nextInt(19) + 7;
        }
        int n10 = Math.min(n5 + random2.nextInt(11), 18);
        int n11 = Math.min(n5 + random2.nextInt(7) - random2.nextInt(5), 11);
        int n12 = bl2 ? n6 : 11;
        for (n4 = -n12; n4 < n12; ++n4) {
            for (n3 = -n12; n3 < n12; ++n3) {
                for (n2 = 0; n2 < n5; ++n2) {
                    int n13 = n = bl2 ? this.func_205178_b(n2, n5, n11) : this.func_205183_a(random2, n2, n5, n11);
                    if (!bl2 && n4 >= n) continue;
                    this.func_205181_a(iSeedReader, random2, blockPos, n5, n4, n2, n3, n, n12, bl2, n7, d, bl, blockState);
                }
            }
        }
        this.func_205186_a(iSeedReader, blockPos, n11, n5, bl2, n6);
        for (n4 = -n12; n4 < n12; ++n4) {
            for (n3 = -n12; n3 < n12; ++n3) {
                for (n2 = -1; n2 > -n10; --n2) {
                    n = bl2 ? MathHelper.ceil((float)n12 * (1.0f - (float)Math.pow(n2, 2.0) / ((float)n10 * 8.0f))) : n12;
                    int n14 = this.func_205187_b(random2, -n2, n10, n11);
                    if (n4 >= n14) continue;
                    this.func_205181_a(iSeedReader, random2, blockPos, n10, n4, n2, n3, n14, n, bl2, n7, d, bl, blockState);
                }
            }
        }
        int n15 = bl2 ? (random2.nextDouble() > 0.1 ? 1 : 0) : (n4 = random2.nextDouble() > 0.7 ? 1 : 0);
        if (n4 != 0) {
            this.func_205184_a(random2, iSeedReader, n11, n5, blockPos, bl2, n6, d, n7);
        }
        return false;
    }

    private void func_205184_a(Random random2, IWorld iWorld, int n, int n2, BlockPos blockPos, boolean bl, int n3, double d, int n4) {
        int n5;
        int n6;
        int n7 = random2.nextBoolean() ? -1 : 1;
        int n8 = random2.nextBoolean() ? -1 : 1;
        int n9 = random2.nextInt(Math.max(n / 2 - 2, 1));
        if (random2.nextBoolean()) {
            n9 = n / 2 + 1 - random2.nextInt(Math.max(n - n / 2 - 1, 1));
        }
        int n10 = random2.nextInt(Math.max(n / 2 - 2, 1));
        if (random2.nextBoolean()) {
            n10 = n / 2 + 1 - random2.nextInt(Math.max(n - n / 2 - 1, 1));
        }
        if (bl) {
            n9 = n10 = random2.nextInt(Math.max(n3 - 5, 1));
        }
        BlockPos blockPos2 = new BlockPos(n7 * n9, 0, n8 * n10);
        double d2 = bl ? d + 1.5707963267948966 : random2.nextDouble() * 2.0 * Math.PI;
        for (n6 = 0; n6 < n2 - 3; ++n6) {
            n5 = this.func_205183_a(random2, n6, n2, n);
            this.func_205174_a(n5, n6, blockPos, iWorld, false, d2, blockPos2, n3, n4);
        }
        for (n6 = -1; n6 > -n2 + random2.nextInt(5); --n6) {
            n5 = this.func_205187_b(random2, -n6, n2, n);
            this.func_205174_a(n5, n6, blockPos, iWorld, true, d2, blockPos2, n3, n4);
        }
    }

    private void func_205174_a(int n, int n2, BlockPos blockPos, IWorld iWorld, boolean bl, double d, BlockPos blockPos2, int n3, int n4) {
        int n5 = n + 1 + n3 / 3;
        int n6 = Math.min(n - 3, 3) + n4 / 2 - 1;
        for (int i = -n5; i < n5; ++i) {
            for (int j = -n5; j < n5; ++j) {
                BlockPos blockPos3;
                Block block;
                double d2 = this.func_205180_a(i, j, blockPos2, n5, n6, d);
                if (!(d2 < 0.0) || !this.isIce(block = iWorld.getBlockState(blockPos3 = blockPos.add(i, n2, j)).getBlock()) && block != Blocks.SNOW_BLOCK) continue;
                if (bl) {
                    this.setBlockState(iWorld, blockPos3, Blocks.WATER.getDefaultState());
                    continue;
                }
                this.setBlockState(iWorld, blockPos3, Blocks.AIR.getDefaultState());
                this.removeSnowLayer(iWorld, blockPos3);
            }
        }
    }

    private void removeSnowLayer(IWorld iWorld, BlockPos blockPos) {
        if (iWorld.getBlockState(blockPos.up()).isIn(Blocks.SNOW)) {
            this.setBlockState(iWorld, blockPos.up(), Blocks.AIR.getDefaultState());
        }
    }

    private void func_205181_a(IWorld iWorld, Random random2, BlockPos blockPos, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7, double d, boolean bl2, BlockState blockState) {
        double d2;
        double d3 = d2 = bl ? this.func_205180_a(n2, n4, BlockPos.ZERO, n6, this.func_205176_a(n3, n, n7), d) : this.func_205177_a(n2, n4, BlockPos.ZERO, n5, random2);
        if (d2 < 0.0) {
            double d4;
            BlockPos blockPos2 = blockPos.add(n2, n3, n4);
            double d5 = d4 = bl ? -0.5 : (double)(-6 - random2.nextInt(3));
            if (d2 > d4 && random2.nextDouble() > 0.9) {
                return;
            }
            this.func_205175_a(blockPos2, iWorld, random2, n - n3, n, bl, bl2, blockState);
        }
    }

    private void func_205175_a(BlockPos blockPos, IWorld iWorld, Random random2, int n, int n2, boolean bl, boolean bl2, BlockState blockState) {
        BlockState blockState2 = iWorld.getBlockState(blockPos);
        if (blockState2.getMaterial() == Material.AIR || blockState2.isIn(Blocks.SNOW_BLOCK) || blockState2.isIn(Blocks.ICE) || blockState2.isIn(Blocks.WATER)) {
            int n3;
            boolean bl3 = !bl || random2.nextDouble() > 0.05;
            int n4 = n3 = bl ? 3 : 2;
            if (bl2 && !blockState2.isIn(Blocks.WATER) && (double)n <= (double)random2.nextInt(Math.max(1, n2 / n3)) + (double)n2 * 0.6 && bl3) {
                this.setBlockState(iWorld, blockPos, Blocks.SNOW_BLOCK.getDefaultState());
            } else {
                this.setBlockState(iWorld, blockPos, blockState);
            }
        }
    }

    private int func_205176_a(int n, int n2, int n3) {
        int n4 = n3;
        if (n > 0 && n2 - n <= 3) {
            n4 = n3 - (4 - (n2 - n));
        }
        return n4;
    }

    private double func_205177_a(int n, int n2, BlockPos blockPos, int n3, Random random2) {
        float f = 10.0f * MathHelper.clamp(random2.nextFloat(), 0.2f, 0.8f) / (float)n3;
        return (double)f + Math.pow(n - blockPos.getX(), 2.0) + Math.pow(n2 - blockPos.getZ(), 2.0) - Math.pow(n3, 2.0);
    }

    private double func_205180_a(int n, int n2, BlockPos blockPos, int n3, int n4, double d) {
        return Math.pow(((double)(n - blockPos.getX()) * Math.cos(d) - (double)(n2 - blockPos.getZ()) * Math.sin(d)) / (double)n3, 2.0) + Math.pow(((double)(n - blockPos.getX()) * Math.sin(d) + (double)(n2 - blockPos.getZ()) * Math.cos(d)) / (double)n4, 2.0) - 1.0;
    }

    private int func_205183_a(Random random2, int n, int n2, int n3) {
        float f = 3.5f - random2.nextFloat();
        float f2 = (1.0f - (float)Math.pow(n, 2.0) / ((float)n2 * f)) * (float)n3;
        if (n2 > 15 + random2.nextInt(5)) {
            int n4 = n < 3 + random2.nextInt(6) ? n / 2 : n;
            f2 = (1.0f - (float)n4 / ((float)n2 * f * 0.4f)) * (float)n3;
        }
        return MathHelper.ceil(f2 / 2.0f);
    }

    private int func_205178_b(int n, int n2, int n3) {
        float f = 1.0f;
        float f2 = (1.0f - (float)Math.pow(n, 2.0) / ((float)n2 * 1.0f)) * (float)n3;
        return MathHelper.ceil(f2 / 2.0f);
    }

    private int func_205187_b(Random random2, int n, int n2, int n3) {
        float f = 1.0f + random2.nextFloat() / 2.0f;
        float f2 = (1.0f - (float)n / ((float)n2 * f)) * (float)n3;
        return MathHelper.ceil(f2 / 2.0f);
    }

    private boolean isIce(Block block) {
        return block == Blocks.PACKED_ICE || block == Blocks.SNOW_BLOCK || block == Blocks.BLUE_ICE;
    }

    private boolean isAirBellow(IBlockReader iBlockReader, BlockPos blockPos) {
        return iBlockReader.getBlockState(blockPos.down()).getMaterial() == Material.AIR;
    }

    private void func_205186_a(IWorld iWorld, BlockPos blockPos, int n, int n2, boolean bl, int n3) {
        int n4 = bl ? n3 : n / 2;
        for (int i = -n4; i <= n4; ++i) {
            for (int j = -n4; j <= n4; ++j) {
                for (int k = 0; k <= n2; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, k, j);
                    Block block = iWorld.getBlockState(blockPos2).getBlock();
                    if (!this.isIce(block) && block != Blocks.SNOW) continue;
                    if (this.isAirBellow(iWorld, blockPos2)) {
                        this.setBlockState(iWorld, blockPos2, Blocks.AIR.getDefaultState());
                        this.setBlockState(iWorld, blockPos2.up(), Blocks.AIR.getDefaultState());
                        continue;
                    }
                    if (!this.isIce(block)) continue;
                    Block[] blockArray = new Block[]{iWorld.getBlockState(blockPos2.west()).getBlock(), iWorld.getBlockState(blockPos2.east()).getBlock(), iWorld.getBlockState(blockPos2.north()).getBlock(), iWorld.getBlockState(blockPos2.south()).getBlock()};
                    int n5 = 0;
                    for (Block block2 : blockArray) {
                        if (this.isIce(block2)) continue;
                        ++n5;
                    }
                    if (n5 < 3) continue;
                    this.setBlockState(iWorld, blockPos2, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockStateFeatureConfig)iFeatureConfig);
    }
}

