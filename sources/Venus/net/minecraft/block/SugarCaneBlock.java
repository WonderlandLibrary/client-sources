/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class SugarCaneBlock
extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    protected SugarCaneBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.isAirBlock(blockPos.up())) {
            int n = 1;
            while (serverWorld.getBlockState(blockPos.down(n)).isIn(this)) {
                ++n;
            }
            if (n < 3) {
                int n2 = blockState.get(AGE);
                if (n2 == 15) {
                    serverWorld.setBlockState(blockPos.up(), this.getDefaultState());
                    serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, 0), 1);
                } else {
                    serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, n2 + 1), 1);
                }
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        if (blockState2.getBlock() == this) {
            return false;
        }
        if (blockState2.isIn(Blocks.GRASS_BLOCK) || blockState2.isIn(Blocks.DIRT) || blockState2.isIn(Blocks.COARSE_DIRT) || blockState2.isIn(Blocks.PODZOL) || blockState2.isIn(Blocks.SAND) || blockState2.isIn(Blocks.RED_SAND)) {
            BlockPos blockPos2 = blockPos.down();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockState3 = iWorldReader.getBlockState(blockPos2.offset(direction));
                FluidState fluidState = iWorldReader.getFluidState(blockPos2.offset(direction));
                if (!fluidState.isTagged(FluidTags.WATER) && !blockState3.isIn(Blocks.FROSTED_ICE)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

