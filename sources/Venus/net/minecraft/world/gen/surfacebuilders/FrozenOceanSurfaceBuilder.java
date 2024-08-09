/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class FrozenOceanSurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
    protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState ICE = Blocks.ICE.getDefaultState();
    private PerlinNoiseGenerator field_205199_h;
    private PerlinNoiseGenerator field_205200_i;
    private long seed;

    public FrozenOceanSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        double d2 = 0.0;
        double d3 = 0.0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        float f = biome.getTemperature(mutable.setPos(n, 63, n2));
        double d4 = Math.min(Math.abs(d), this.field_205199_h.noiseAt((double)n * 0.1, (double)n2 * 0.1, true) * 15.0);
        if (d4 > 1.8) {
            double d5 = 0.09765625;
            d2 = d4 * d4 * 1.2;
            double d6 = Math.abs(this.field_205200_i.noiseAt((double)n * 0.09765625, (double)n2 * 0.09765625, true));
            double d7 = Math.ceil(d6 * 40.0) + 14.0;
            if (d2 > d7) {
                d2 = d7;
            }
            if (f > 0.1f) {
                d2 -= 2.0;
            }
            if (d2 > 2.0) {
                d3 = (double)n4 - d2 - 7.0;
                d2 += (double)n4;
            } else {
                d2 = 0.0;
            }
        }
        int n5 = n & 0xF;
        int n6 = n2 & 0xF;
        ISurfaceBuilderConfig iSurfaceBuilderConfig = biome.getGenerationSettings().getSurfaceBuilderConfig();
        BlockState blockState3 = iSurfaceBuilderConfig.getUnder();
        BlockState blockState4 = iSurfaceBuilderConfig.getTop();
        BlockState blockState5 = blockState3;
        BlockState blockState6 = blockState4;
        int n7 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        int n8 = -1;
        int n9 = 0;
        int n10 = 2 + random2.nextInt(4);
        int n11 = n4 + 18 + random2.nextInt(10);
        for (int i = Math.max(n3, (int)d2 + 1); i >= 0; --i) {
            mutable.setPos(n5, i, n6);
            if (iChunk.getBlockState(mutable).isAir() && i < (int)d2 && random2.nextDouble() > 0.01) {
                iChunk.setBlockState(mutable, PACKED_ICE, false);
            } else if (iChunk.getBlockState(mutable).getMaterial() == Material.WATER && i > (int)d3 && i < n4 && d3 != 0.0 && random2.nextDouble() > 0.15) {
                iChunk.setBlockState(mutable, PACKED_ICE, false);
            }
            BlockState blockState7 = iChunk.getBlockState(mutable);
            if (blockState7.isAir()) {
                n8 = -1;
                continue;
            }
            if (!blockState7.isIn(blockState.getBlock())) {
                if (!blockState7.isIn(Blocks.PACKED_ICE) || n9 > n10 || i <= n11) continue;
                iChunk.setBlockState(mutable, SNOW_BLOCK, false);
                ++n9;
                continue;
            }
            if (n8 == -1) {
                if (n7 <= 0) {
                    blockState6 = AIR;
                    blockState5 = blockState;
                } else if (i >= n4 - 4 && i <= n4 + 1) {
                    blockState6 = blockState4;
                    blockState5 = blockState3;
                }
                if (i < n4 && (blockState6 == null || blockState6.isAir())) {
                    blockState6 = biome.getTemperature(mutable.setPos(n, i, n2)) < 0.15f ? ICE : blockState2;
                }
                n8 = n7;
                if (i >= n4 - 1) {
                    iChunk.setBlockState(mutable, blockState6, false);
                    continue;
                }
                if (i < n4 - 7 - n7) {
                    blockState6 = AIR;
                    blockState5 = blockState;
                    iChunk.setBlockState(mutable, GRAVEL, false);
                    continue;
                }
                iChunk.setBlockState(mutable, blockState5, false);
                continue;
            }
            if (n8 <= 0) continue;
            iChunk.setBlockState(mutable, blockState5, false);
            if (--n8 != 0 || !blockState5.isIn(Blocks.SAND) || n7 <= 1) continue;
            n8 = random2.nextInt(4) + Math.max(0, i - 63);
            blockState5 = blockState5.isIn(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
        }
    }

    @Override
    public void setSeed(long l) {
        if (this.seed != l || this.field_205199_h == null || this.field_205200_i == null) {
            SharedSeedRandom sharedSeedRandom = new SharedSeedRandom(l);
            this.field_205199_h = new PerlinNoiseGenerator(sharedSeedRandom, IntStream.rangeClosed(-3, 0));
            this.field_205200_i = new PerlinNoiseGenerator(sharedSeedRandom, ImmutableList.of(0));
        }
        this.seed = l;
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

