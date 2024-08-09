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

public class WoodedBadlandsSurfaceBuilder
extends BadlandsSurfaceBuilder {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

    public WoodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
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
                    if (i > 86 + n7 * 2) {
                        if (bl) {
                            iChunk.setBlockState(mutable, Blocks.COARSE_DIRT.getDefaultState(), false);
                        } else {
                            iChunk.setBlockState(mutable, Blocks.GRASS_BLOCK.getDefaultState(), false);
                        }
                    } else if (i > n4 + 3 + n7) {
                        BlockState blockState8 = i >= 64 && i <= 127 ? (bl ? TERRACOTTA : this.func_215431_a(n, i, n2)) : ORANGE_TERRACOTTA;
                        iChunk.setBlockState(mutable, blockState8, false);
                    } else {
                        iChunk.setBlockState(mutable, blockState5, false);
                        bl2 = true;
                    }
                } else {
                    iChunk.setBlockState(mutable, blockState6, false);
                    if (blockState6 == WHITE_TERRACOTTA) {
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
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

