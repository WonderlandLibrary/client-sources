/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BadlandsSurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();
    private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.getDefaultState();
    private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.getDefaultState();
    private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
    private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
    protected BlockState[] field_215432_a;
    protected long field_215433_b;
    protected PerlinNoiseGenerator field_215435_c;
    protected PerlinNoiseGenerator field_215437_d;
    protected PerlinNoiseGenerator field_215439_e;

    public BadlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        int n5 = n & 0xF;
        int n6 = n2 & 0xF;
        BlockState blockState3 = WHITE_TERRACOTTA;
        ISurfaceBuilderConfig iSurfaceBuilderConfig = biome.getGenerationSettings().getSurfaceBuilderConfig();
        BlockState blockState4 = iSurfaceBuilderConfig.getUnder();
        BlockState blockState5 = iSurfaceBuilderConfig.getTop();
        BlockState blockState6 = blockState4;
        int n7 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        boolean bl = Math.cos(d / 3.0 * Math.PI) > 0.0;
        int n8 = -1;
        boolean bl2 = false;
        int n9 = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n3; i >= 0; --i) {
            if (n9 >= 15) continue;
            mutable.setPos(n5, i, n6);
            BlockState blockState7 = iChunk.getBlockState(mutable);
            if (blockState7.isAir()) {
                n8 = -1;
                continue;
            }
            if (!blockState7.isIn(blockState.getBlock())) continue;
            if (n8 == -1) {
                bl2 = false;
                if (n7 <= 0) {
                    blockState3 = Blocks.AIR.getDefaultState();
                    blockState6 = blockState;
                } else if (i >= n4 - 4 && i <= n4 + 1) {
                    blockState3 = WHITE_TERRACOTTA;
                    blockState6 = blockState4;
                }
                if (i < n4 && (blockState3 == null || blockState3.isAir())) {
                    blockState3 = blockState2;
                }
                n8 = n7 + Math.max(0, i - n4);
                if (i >= n4 - 1) {
                    if (i > n4 + 3 + n7) {
                        var30_28 = i >= 64 && i <= 127 ? (bl ? TERRACOTTA : this.func_215431_a(n, i, n2)) : ORANGE_TERRACOTTA;
                        iChunk.setBlockState(mutable, (BlockState)var30_28, false);
                    } else {
                        iChunk.setBlockState(mutable, blockState5, false);
                        bl2 = true;
                    }
                } else {
                    iChunk.setBlockState(mutable, blockState6, false);
                    var30_28 = blockState6.getBlock();
                    if (var30_28 == Blocks.WHITE_TERRACOTTA || var30_28 == Blocks.ORANGE_TERRACOTTA || var30_28 == Blocks.MAGENTA_TERRACOTTA || var30_28 == Blocks.LIGHT_BLUE_TERRACOTTA || var30_28 == Blocks.YELLOW_TERRACOTTA || var30_28 == Blocks.LIME_TERRACOTTA || var30_28 == Blocks.PINK_TERRACOTTA || var30_28 == Blocks.GRAY_TERRACOTTA || var30_28 == Blocks.LIGHT_GRAY_TERRACOTTA || var30_28 == Blocks.CYAN_TERRACOTTA || var30_28 == Blocks.PURPLE_TERRACOTTA || var30_28 == Blocks.BLUE_TERRACOTTA || var30_28 == Blocks.BROWN_TERRACOTTA || var30_28 == Blocks.GREEN_TERRACOTTA || var30_28 == Blocks.RED_TERRACOTTA || var30_28 == Blocks.BLACK_TERRACOTTA) {
                        iChunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                    }
                }
            } else if (n8 > 0) {
                --n8;
                if (bl2) {
                    iChunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                } else {
                    iChunk.setBlockState(mutable, this.func_215431_a(n, i, n2), false);
                }
            }
            ++n9;
        }
    }

    @Override
    public void setSeed(long l) {
        if (this.field_215433_b != l || this.field_215432_a == null) {
            this.func_215430_b(l);
        }
        if (this.field_215433_b != l || this.field_215435_c == null || this.field_215437_d == null) {
            SharedSeedRandom sharedSeedRandom = new SharedSeedRandom(l);
            this.field_215435_c = new PerlinNoiseGenerator(sharedSeedRandom, IntStream.rangeClosed(-3, 0));
            this.field_215437_d = new PerlinNoiseGenerator(sharedSeedRandom, ImmutableList.of(0));
        }
        this.field_215433_b = l;
    }

    protected void func_215430_b(long l) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        this.field_215432_a = new BlockState[64];
        Arrays.fill(this.field_215432_a, TERRACOTTA);
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom(l);
        this.field_215439_e = new PerlinNoiseGenerator(sharedSeedRandom, ImmutableList.of(0));
        for (n7 = 0; n7 < 64; ++n7) {
            if ((n7 += sharedSeedRandom.nextInt(5) + 1) >= 64) continue;
            this.field_215432_a[n7] = ORANGE_TERRACOTTA;
        }
        n7 = sharedSeedRandom.nextInt(4) + 2;
        for (n6 = 0; n6 < n7; ++n6) {
            n5 = sharedSeedRandom.nextInt(3) + 1;
            n4 = sharedSeedRandom.nextInt(64);
            for (n3 = 0; n4 + n3 < 64 && n3 < n5; ++n3) {
                this.field_215432_a[n4 + n3] = YELLOW_TERRACOTTA;
            }
        }
        n6 = sharedSeedRandom.nextInt(4) + 2;
        for (n5 = 0; n5 < n6; ++n5) {
            n4 = sharedSeedRandom.nextInt(3) + 2;
            n3 = sharedSeedRandom.nextInt(64);
            for (n2 = 0; n3 + n2 < 64 && n2 < n4; ++n2) {
                this.field_215432_a[n3 + n2] = BROWN_TERRACOTTA;
            }
        }
        n5 = sharedSeedRandom.nextInt(4) + 2;
        for (n4 = 0; n4 < n5; ++n4) {
            n3 = sharedSeedRandom.nextInt(3) + 1;
            n2 = sharedSeedRandom.nextInt(64);
            for (n = 0; n2 + n < 64 && n < n3; ++n) {
                this.field_215432_a[n2 + n] = RED_TERRACOTTA;
            }
        }
        n4 = sharedSeedRandom.nextInt(3) + 3;
        n3 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            n = 1;
            n3 += sharedSeedRandom.nextInt(16) + 4;
            for (int i = 0; n3 + i < 64 && i < 1; ++i) {
                this.field_215432_a[n3 + i] = WHITE_TERRACOTTA;
                if (n3 + i > 1 && sharedSeedRandom.nextBoolean()) {
                    this.field_215432_a[n3 + i - 1] = LIGHT_GRAY_TERRACOTTA;
                }
                if (n3 + i >= 63 || !sharedSeedRandom.nextBoolean()) continue;
                this.field_215432_a[n3 + i + 1] = LIGHT_GRAY_TERRACOTTA;
            }
        }
    }

    protected BlockState func_215431_a(int n, int n2, int n3) {
        int n4 = (int)Math.round(this.field_215439_e.noiseAt((double)n / 512.0, (double)n3 / 512.0, true) * 2.0);
        return this.field_215432_a[(n2 + n4 + 64) % 64];
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

