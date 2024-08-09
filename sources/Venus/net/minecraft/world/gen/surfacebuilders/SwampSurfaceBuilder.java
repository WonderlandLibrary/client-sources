/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class SwampSurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    public SwampSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        double d2 = Biome.INFO_NOISE.noiseAt((double)n * 0.25, (double)n2 * 0.25, true);
        if (d2 > 0.0) {
            int n5 = n & 0xF;
            int n6 = n2 & 0xF;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = n3; i >= 0; --i) {
                mutable.setPos(n5, i, n6);
                if (iChunk.getBlockState(mutable).isAir()) continue;
                if (i != 62 || iChunk.getBlockState(mutable).isIn(blockState2.getBlock())) break;
                iChunk.setBlockState(mutable, blockState2, false);
                break;
            }
        }
        SurfaceBuilder.DEFAULT.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, surfaceBuilderConfig);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }
}

