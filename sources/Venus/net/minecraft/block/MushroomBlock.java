/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;

public class MushroomBlock
extends BushBlock
implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

    public MushroomBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos object, Random random2) {
        if (random2.nextInt(25) == 0) {
            int n = 5;
            int n2 = 4;
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(((BlockPos)object).add(-4, -1, -4), ((BlockPos)object).add(4, 1, 4))) {
                if (!serverWorld.getBlockState(blockPos).isIn(this) || --n > 0) continue;
                return;
            }
            Object object2 = ((BlockPos)object).add(random2.nextInt(3) - 1, random2.nextInt(2) - random2.nextInt(2), random2.nextInt(3) - 1);
            for (int i = 0; i < 4; ++i) {
                if (serverWorld.isAirBlock((BlockPos)object2) && blockState.isValidPosition(serverWorld, (BlockPos)object2)) {
                    object = object2;
                }
                object2 = ((BlockPos)object).add(random2.nextInt(3) - 1, random2.nextInt(2) - random2.nextInt(2), random2.nextInt(3) - 1);
            }
            if (serverWorld.isAirBlock((BlockPos)object2) && blockState.isValidPosition(serverWorld, (BlockPos)object2)) {
                serverWorld.setBlockState((BlockPos)object2, blockState, 1);
            }
        }
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isOpaqueCube(iBlockReader, blockPos);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        if (blockState2.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
        }
        return iWorldReader.getLightSubtracted(blockPos, 0) < 13 && this.isValidGround(blockState2, iWorldReader, blockPos2);
    }

    public boolean grow(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState, Random random2) {
        ConfiguredFeature<?, ?> configuredFeature;
        serverWorld.removeBlock(blockPos, true);
        if (this == Blocks.BROWN_MUSHROOM) {
            configuredFeature = Features.HUGE_BROWN_MUSHROOM;
        } else {
            if (this != Blocks.RED_MUSHROOM) {
                serverWorld.setBlockState(blockPos, blockState, 0);
                return true;
            }
            configuredFeature = Features.HUGE_RED_MUSHROOM;
        }
        if (configuredFeature.func_242765_a(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), random2, blockPos)) {
            return false;
        }
        serverWorld.setBlockState(blockPos, blockState, 0);
        return true;
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return (double)random2.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        this.grow(serverWorld, blockPos, blockState, random2);
    }
}

