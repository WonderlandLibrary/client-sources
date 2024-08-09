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
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DefaultSurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    public DefaultSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, surfaceBuilderConfig.getTop(), surfaceBuilderConfig.getUnder(), surfaceBuilderConfig.getUnderWaterMaterial(), n4);
    }

    protected void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, BlockState blockState3, BlockState blockState4, BlockState blockState5, int n4) {
        BlockState blockState6 = blockState3;
        BlockState blockState7 = blockState4;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n5 = -1;
        int n6 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        int n7 = n & 0xF;
        int n8 = n2 & 0xF;
        for (int i = n3; i >= 0; --i) {
            mutable.setPos(n7, i, n8);
            BlockState blockState8 = iChunk.getBlockState(mutable);
            if (blockState8.isAir()) {
                n5 = -1;
                continue;
            }
            if (!blockState8.isIn(blockState.getBlock())) continue;
            if (n5 == -1) {
                if (n6 <= 0) {
                    blockState6 = Blocks.AIR.getDefaultState();
                    blockState7 = blockState;
                } else if (i >= n4 - 4 && i <= n4 + 1) {
                    blockState6 = blockState3;
                    blockState7 = blockState4;
                }
                if (i < n4 && (blockState6 == null || blockState6.isAir())) {
                    blockState6 = biome.getTemperature(mutable.setPos(n, i, n2)) < 0.15f ? Blocks.ICE.getDefaultState() : blockState2;
                    mutable.setPos(n7, i, n8);
                }
                n5 = n6;
                if (i >= n4 - 1) {
                    iChunk.setBlockState(mutable, blockState6, false);
                    continue;
                }
                if (i < n4 - 7 - n6) {
                    blockState6 = Blocks.AIR.getDefaultState();
                    blockState7 = blockState;
                    iChunk.setBlockState(mutable, blockState5, false);
                    continue;
                }
                iChunk.setBlockState(mutable, blockState7, false);
                continue;
            }
            if (n5 <= 0) continue;
            iChunk.setBlockState(mutable, blockState7, false);
            if (--n5 != 0 || !blockState7.isIn(Blocks.SAND) || n6 <= 1) continue;
            n5 = random2.nextInt(4) + Math.max(0, i - 63);
            blockState7 = blockState7.isIn(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
        }
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

