/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ErodedBadlandsSurfaceBuilder
extends BadlandsSurfaceBuilder {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

    public ErodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        double d2 = 0.0;
        double d3 = Math.min(Math.abs(d), this.field_215435_c.noiseAt((double)n * 0.25, (double)n2 * 0.25, true) * 15.0);
        if (d3 > 0.0) {
            double d4 = 0.001953125;
            d2 = d3 * d3 * 2.5;
            double d5 = Math.abs(this.field_215437_d.noiseAt((double)n * 0.001953125, (double)n2 * 0.001953125, true));
            double d6 = Math.ceil(d5 * 50.0) + 14.0;
            if (d2 > d6) {
                d2 = d6;
            }
            d2 += 64.0;
        }
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
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = Math.max(n3, (int)d2 + 1); i >= 0; --i) {
            BlockState blockState7;
            mutable.setPos(n5, i, n6);
            if (iChunk.getBlockState(mutable).isAir() && i < (int)d2) {
                iChunk.setBlockState(mutable, blockState, false);
            }
            if ((blockState7 = iChunk.getBlockState(mutable)).isAir()) {
                n8 = -1;
                continue;
            }
            if (!blockState7.isIn(blockState.getBlock())) continue;
            if (n8 == -1) {
                Object object;
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
                    if (i <= n4 + 3 + n7) {
                        iChunk.setBlockState(mutable, blockState5, false);
                        bl2 = true;
                        continue;
                    }
                    object = i >= 64 && i <= 127 ? (bl ? TERRACOTTA : this.func_215431_a(n, i, n2)) : ORANGE_TERRACOTTA;
                    iChunk.setBlockState(mutable, (BlockState)object, false);
                    continue;
                }
                iChunk.setBlockState(mutable, blockState6, false);
                object = blockState6.getBlock();
                if (object != Blocks.WHITE_TERRACOTTA && object != Blocks.ORANGE_TERRACOTTA && object != Blocks.MAGENTA_TERRACOTTA && object != Blocks.LIGHT_BLUE_TERRACOTTA && object != Blocks.YELLOW_TERRACOTTA && object != Blocks.LIME_TERRACOTTA && object != Blocks.PINK_TERRACOTTA && object != Blocks.GRAY_TERRACOTTA && object != Blocks.LIGHT_GRAY_TERRACOTTA && object != Blocks.CYAN_TERRACOTTA && object != Blocks.PURPLE_TERRACOTTA && object != Blocks.BLUE_TERRACOTTA && object != Blocks.BROWN_TERRACOTTA && object != Blocks.GREEN_TERRACOTTA && object != Blocks.RED_TERRACOTTA && object != Blocks.BLACK_TERRACOTTA) continue;
                iChunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                continue;
            }
            if (n8 <= 0) continue;
            --n8;
            if (bl2) {
                iChunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                continue;
            }
            iChunk.setBlockState(mutable, this.func_215431_a(n, i, n2), false);
        }
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

