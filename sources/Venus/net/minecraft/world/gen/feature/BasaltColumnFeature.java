/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ColumnConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BasaltColumnFeature
extends Feature<ColumnConfig> {
    private static final ImmutableList<Block> field_236245_a_ = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);

    public BasaltColumnFeature(Codec<ColumnConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, ColumnConfig columnConfig) {
        int n = chunkGenerator.func_230356_f_();
        if (!BasaltColumnFeature.func_242762_a(iSeedReader, n, blockPos.toMutable())) {
            return true;
        }
        int n2 = columnConfig.func_242795_b().func_242259_a(random2);
        boolean bl = random2.nextFloat() < 0.9f;
        int n3 = Math.min(n2, bl ? 5 : 8);
        int n4 = bl ? 50 : 15;
        boolean bl2 = false;
        for (BlockPos blockPos2 : BlockPos.getRandomPositions(random2, n4, blockPos.getX() - n3, blockPos.getY(), blockPos.getZ() - n3, blockPos.getX() + n3, blockPos.getY(), blockPos.getZ() + n3)) {
            int n5 = n2 - blockPos2.manhattanDistance(blockPos);
            if (n5 < 0) continue;
            bl2 |= this.func_236248_a_(iSeedReader, n, blockPos2, n5, columnConfig.func_242794_am_().func_242259_a(random2));
        }
        return bl2;
    }

    private boolean func_236248_a_(IWorld iWorld, int n, BlockPos blockPos, int n2, int n3) {
        boolean bl = false;
        block0: for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.getX() - n3, blockPos.getY(), blockPos.getZ() - n3, blockPos.getX() + n3, blockPos.getY(), blockPos.getZ() + n3)) {
            int n4 = blockPos2.manhattanDistance(blockPos);
            BlockPos blockPos3 = BasaltColumnFeature.func_236247_a_(iWorld, n, blockPos2) ? BasaltColumnFeature.func_236246_a_(iWorld, n, blockPos2.toMutable(), n4) : BasaltColumnFeature.func_236249_a_(iWorld, blockPos2.toMutable(), n4);
            if (blockPos3 == null) continue;
            BlockPos.Mutable mutable = blockPos3.toMutable();
            for (int i = n2 - n4 / 2; i >= 0; --i) {
                if (BasaltColumnFeature.func_236247_a_(iWorld, n, mutable)) {
                    this.setBlockState(iWorld, mutable, Blocks.BASALT.getDefaultState());
                    mutable.move(Direction.UP);
                    bl = true;
                    continue;
                }
                if (!iWorld.getBlockState(mutable).isIn(Blocks.BASALT)) continue block0;
                mutable.move(Direction.UP);
            }
        }
        return bl;
    }

    @Nullable
    private static BlockPos func_236246_a_(IWorld iWorld, int n, BlockPos.Mutable mutable, int n2) {
        while (mutable.getY() > 1 && n2 > 0) {
            --n2;
            if (BasaltColumnFeature.func_242762_a(iWorld, n, mutable)) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }
        return null;
    }

    private static boolean func_242762_a(IWorld iWorld, int n, BlockPos.Mutable mutable) {
        if (!BasaltColumnFeature.func_236247_a_(iWorld, n, mutable)) {
            return true;
        }
        BlockState blockState = iWorld.getBlockState(mutable.move(Direction.DOWN));
        mutable.move(Direction.UP);
        return !blockState.isAir() && !field_236245_a_.contains(blockState.getBlock());
    }

    @Nullable
    private static BlockPos func_236249_a_(IWorld iWorld, BlockPos.Mutable mutable, int n) {
        while (mutable.getY() < iWorld.getHeight() && n > 0) {
            --n;
            BlockState blockState = iWorld.getBlockState(mutable);
            if (field_236245_a_.contains(blockState.getBlock())) {
                return null;
            }
            if (blockState.isAir()) {
                return mutable;
            }
            mutable.move(Direction.UP);
        }
        return null;
    }

    private static boolean func_236247_a_(IWorld iWorld, int n, BlockPos blockPos) {
        BlockState blockState = iWorld.getBlockState(blockPos);
        return blockState.isAir() || blockState.isIn(Blocks.LAVA) && blockPos.getY() <= n;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (ColumnConfig)iFeatureConfig);
    }
}

