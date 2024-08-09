/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class ValleySurfaceBuilder
extends SurfaceBuilder<SurfaceBuilderConfig> {
    private long field_237170_a_;
    private ImmutableMap<BlockState, OctavesNoiseGenerator> field_237171_b_ = ImmutableMap.of();
    private ImmutableMap<BlockState, OctavesNoiseGenerator> field_237172_c_ = ImmutableMap.of();
    private OctavesNoiseGenerator field_237173_d_;

    public ValleySurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, SurfaceBuilderConfig surfaceBuilderConfig) {
        int n5 = n4 + 1;
        int n6 = n & 0xF;
        int n7 = n2 & 0xF;
        int n8 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        int n9 = (int)(d / 3.0 + 3.0 + random2.nextDouble() * 0.25);
        double d2 = 0.03125;
        boolean bl = this.field_237173_d_.func_205563_a((double)n * 0.03125, 109.0, (double)n2 * 0.03125) * 75.0 + random2.nextDouble() > 0.0;
        BlockState blockState3 = (BlockState)this.field_237172_c_.entrySet().stream().max(Comparator.comparing(arg_0 -> ValleySurfaceBuilder.lambda$buildSurface$0(n, n4, n2, arg_0))).get().getKey();
        BlockState blockState4 = (BlockState)this.field_237171_b_.entrySet().stream().max(Comparator.comparing(arg_0 -> ValleySurfaceBuilder.lambda$buildSurface$1(n, n4, n2, arg_0))).get().getKey();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockState blockState5 = iChunk.getBlockState(mutable.setPos(n6, 128, n7));
        for (int i = 127; i >= 0; --i) {
            int n10;
            mutable.setPos(n6, i, n7);
            BlockState blockState6 = iChunk.getBlockState(mutable);
            if (blockState5.isIn(blockState.getBlock()) && (blockState6.isAir() || blockState6 == blockState2)) {
                for (n10 = 0; n10 < n8; ++n10) {
                    mutable.move(Direction.UP);
                    if (!iChunk.getBlockState(mutable).isIn(blockState.getBlock())) break;
                    iChunk.setBlockState(mutable, blockState3, false);
                }
                mutable.setPos(n6, i, n7);
            }
            if ((blockState5.isAir() || blockState5 == blockState2) && blockState6.isIn(blockState.getBlock())) {
                for (n10 = 0; n10 < n9 && iChunk.getBlockState(mutable).isIn(blockState.getBlock()); ++n10) {
                    if (bl && i >= n5 - 4 && i <= n5 + 1) {
                        iChunk.setBlockState(mutable, this.func_230389_c_(), false);
                    } else {
                        iChunk.setBlockState(mutable, blockState4, false);
                    }
                    mutable.move(Direction.DOWN);
                }
            }
            blockState5 = blockState6;
        }
    }

    @Override
    public void setSeed(long l) {
        if (this.field_237170_a_ != l || this.field_237173_d_ == null || this.field_237171_b_.isEmpty() || this.field_237172_c_.isEmpty()) {
            this.field_237171_b_ = ValleySurfaceBuilder.func_237175_a_(this.func_230387_a_(), l);
            this.field_237172_c_ = ValleySurfaceBuilder.func_237175_a_(this.func_230388_b_(), l + (long)this.field_237171_b_.size());
            this.field_237173_d_ = new OctavesNoiseGenerator(new SharedSeedRandom(l + (long)this.field_237171_b_.size() + (long)this.field_237172_c_.size()), ImmutableList.of(0));
        }
        this.field_237170_a_ = l;
    }

    private static ImmutableMap<BlockState, OctavesNoiseGenerator> func_237175_a_(ImmutableList<BlockState> immutableList, long l) {
        ImmutableMap.Builder<BlockState, OctavesNoiseGenerator> builder = new ImmutableMap.Builder<BlockState, OctavesNoiseGenerator>();
        for (BlockState blockState : immutableList) {
            builder.put(blockState, new OctavesNoiseGenerator(new SharedSeedRandom(l), ImmutableList.of(-4)));
            ++l;
        }
        return builder.build();
    }

    protected abstract ImmutableList<BlockState> func_230387_a_();

    protected abstract ImmutableList<BlockState> func_230388_b_();

    protected abstract BlockState func_230389_c_();

    @Override
    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, ISurfaceBuilderConfig iSurfaceBuilderConfig) {
        this.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, (SurfaceBuilderConfig)iSurfaceBuilderConfig);
    }

    private static Double lambda$buildSurface$1(int n, int n2, int n3, Map.Entry entry) {
        return ((OctavesNoiseGenerator)entry.getValue()).func_205563_a(n, n2, n3);
    }

    private static Double lambda$buildSurface$0(int n, int n2, int n3, Map.Entry entry) {
        return ((OctavesNoiseGenerator)entry.getValue()).func_205563_a(n, n2, n3);
    }
}

