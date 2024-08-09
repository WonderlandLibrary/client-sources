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
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class UnderwaterCaveWorldCarver
extends CaveWorldCarver {
    public UnderwaterCaveWorldCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 256);
        this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW, Blocks.SAND, Blocks.GRAVEL, Blocks.WATER, Blocks.LAVA, Blocks.OBSIDIAN, Blocks.AIR, Blocks.CAVE_AIR, Blocks.PACKED_ICE);
    }

    @Override
    protected boolean func_222700_a(IChunk iChunk, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        return true;
    }

    @Override
    protected boolean func_230358_a_(IChunk iChunk, Function<BlockPos, Biome> function, BitSet bitSet, Random random2, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, MutableBoolean mutableBoolean) {
        return UnderwaterCaveWorldCarver.func_222728_a(this, iChunk, bitSet, random2, mutable, n, n2, n3, n4, n5, n6, n7, n8);
    }

    protected static boolean func_222728_a(WorldCarver<?> worldCarver, IChunk iChunk, BitSet bitSet, Random random2, BlockPos.Mutable mutable, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (n7 >= n) {
            return true;
        }
        int n9 = n6 | n8 << 4 | n7 << 8;
        if (bitSet.get(n9)) {
            return true;
        }
        bitSet.set(n9);
        mutable.setPos(n4, n7, n5);
        BlockState blockState = iChunk.getBlockState(mutable);
        if (!worldCarver.isCarvable(blockState)) {
            return true;
        }
        if (n7 == 10) {
            float f = random2.nextFloat();
            if ((double)f < 0.25) {
                iChunk.setBlockState(mutable, Blocks.MAGMA_BLOCK.getDefaultState(), false);
                iChunk.getBlocksToBeTicked().scheduleTick(mutable, Blocks.MAGMA_BLOCK, 0);
            } else {
                iChunk.setBlockState(mutable, Blocks.OBSIDIAN.getDefaultState(), false);
            }
            return false;
        }
        if (n7 < 10) {
            iChunk.setBlockState(mutable, Blocks.LAVA.getDefaultState(), false);
            return true;
        }
        boolean bl = false;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int n10 = n4 + direction.getXOffset();
            int n11 = n5 + direction.getZOffset();
            if (n10 >> 4 == n2 && n11 >> 4 == n3 && !iChunk.getBlockState(mutable.setPos(n10, n7, n11)).isAir()) continue;
            iChunk.setBlockState(mutable, WATER.getBlockState(), false);
            iChunk.getFluidsToBeTicked().scheduleTick(mutable, WATER.getFluid(), 0);
            bl = true;
            break;
        }
        mutable.setPos(n4, n7, n5);
        if (!bl) {
            iChunk.setBlockState(mutable, WATER.getBlockState(), false);
            return false;
        }
        return false;
    }
}

