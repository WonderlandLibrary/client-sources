/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class NetherSurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
    protected long field_205552_a;
    protected OctavesNoiseGenerator field_205553_b;

    public NetherSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        int n5 = n4;
        int n6 = n & 0xF;
        int n7 = n2 & 0xF;
        double d2 = 0.03125;
        boolean bl = this.field_205553_b.func_205563_a((double)n * 0.03125, (double)n2 * 0.03125, 0.0) * 75.0 + random2.nextDouble() > 0.0;
        boolean bl2 = this.field_205553_b.func_205563_a((double)n * 0.03125, 109.0, (double)n2 * 0.03125) * 75.0 + random2.nextDouble() > 0.0;
        int n8 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n9 = -1;
        BlockState blockState3 = surfaceBuilderConfig.getTop();
        BlockState blockState4 = surfaceBuilderConfig.getUnder();
        for (int i = 127; i >= 0; --i) {
            mutable.setPos(n6, i, n7);
            BlockState blockState5 = iChunk.getBlockState(mutable);
            if (blockState5.isAir()) {
                n9 = -1;
                continue;
            }
            if (!blockState5.isIn(blockState.getBlock())) continue;
            if (n9 == -1) {
                boolean bl3 = false;
                if (n8 <= 0) {
                    bl3 = true;
                    blockState4 = surfaceBuilderConfig.getUnder();
                } else if (i >= n5 - 4 && i <= n5 + 1) {
                    blockState3 = surfaceBuilderConfig.getTop();
                    blockState4 = surfaceBuilderConfig.getUnder();
                    if (bl2) {
                        blockState3 = GRAVEL;
                        blockState4 = surfaceBuilderConfig.getUnder();
                    }
                    if (bl) {
                        blockState3 = SOUL_SAND;
                        blockState4 = SOUL_SAND;
                    }
                }
                if (i < n5 && bl3) {
                    blockState3 = blockState2;
                }
                n9 = n8;
                if (i >= n5 - 1) {
                    iChunk.setBlockState(mutable, blockState3, false);
                    continue;
                }
                iChunk.setBlockState(mutable, blockState4, false);
                continue;
            }
            if (n9 <= 0) continue;
            --n9;
            iChunk.setBlockState(mutable, blockState4, false);
        }
    }

    @Override
    public void setSeed(long l) {
        if (this.field_205552_a != l || this.field_205553_b == null) {
            this.field_205553_b = new OctavesNoiseGenerator(new SharedSeedRandom(l), IntStream.rangeClosed(-3, 0));
        }
        this.field_205552_a = l;
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

