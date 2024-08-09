/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ObserverBlock
extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ObserverBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.SOUTH)).with(POWERED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(POWERED).booleanValue()) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWERED, false), 1);
        } else {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWERED, true), 1);
            serverWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 2);
        }
        this.updateNeighborsInFront(serverWorld, blockPos, blockState);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(FACING) == direction && !blockState.get(POWERED).booleanValue()) {
            this.startSignal(iWorld, blockPos);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    private void startSignal(IWorld iWorld, BlockPos blockPos) {
        if (!iWorld.isRemote() && !iWorld.getPendingBlockTicks().isTickScheduled(blockPos, this)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 2);
        }
    }

    protected void updateNeighborsInFront(World world, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(FACING);
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite());
        world.neighborChanged(blockPos2, this, blockPos);
        world.notifyNeighborsOfStateExcept(blockPos2, this, direction);
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.getWeakPower(iBlockReader, blockPos, direction);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false && blockState.get(FACING) == direction ? 15 : 0;
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock()) && !world.isRemote() && blockState.get(POWERED).booleanValue() && !world.getPendingBlockTicks().isTickScheduled(blockPos, this)) {
            BlockState blockState3 = (BlockState)blockState.with(POWERED, false);
            world.setBlockState(blockPos, blockState3, 1);
            this.updateNeighborsInFront(world, blockPos, blockState3);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock()) && !world.isRemote && blockState.get(POWERED).booleanValue() && world.getPendingBlockTicks().isTickScheduled(blockPos, this)) {
            this.updateNeighborsInFront(world, blockPos, (BlockState)blockState.with(POWERED, false));
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite().getOpposite());
    }
}

