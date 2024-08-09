/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherCaveCarver
extends CaveWorldCarver {
    public NetherCaveCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 128);
        this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.BASALT, Blocks.BLACKSTONE);
        this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
    }

    @Override
    protected int func_230357_a_() {
        return 1;
    }

    @Override
    protected float func_230359_a_(Random random2) {
        return (random2.nextFloat() * 2.0f + random2.nextFloat()) * 2.0f;
    }

    @Override
    protected double func_230360_b_() {
        return 5.0;
    }

    @Override
    protected int func_230361_b_(Random random2) {
        return random2.nextInt(this.maxHeight);
    }

    @Override
    protected boolean func_230358_a_(IChunk iChunk, Function<BlockPos, Biome> function, BitSet bitSet, Random random2, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, MutableBoolean mutableBoolean) {
        int n9 = n6 | n8 << 4 | n7 << 8;
        if (bitSet.get(n9)) {
            return true;
        }
        bitSet.set(n9);
        mutable.setPos(n4, n7, n5);
        if (this.isCarvable(iChunk.getBlockState(mutable))) {
            BlockState blockState = n7 <= 31 ? LAVA.getBlockState() : CAVE_AIR;
            iChunk.setBlockState(mutable, blockState, false);
            return false;
        }
        return true;
    }
}

