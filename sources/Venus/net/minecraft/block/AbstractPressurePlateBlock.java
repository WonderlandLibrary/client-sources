/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractPressurePlateBlock
extends Block {
    protected static final VoxelShape PRESSED_AABB = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
    protected static final VoxelShape UNPRESSED_AABB = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
    protected static final AxisAlignedBB PRESSURE_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

    protected AbstractPressurePlateBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.getRedstoneStrength(blockState) > 0 ? PRESSED_AABB : UNPRESSED_AABB;
    }

    protected int getPoweredDuration() {
        return 1;
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return AbstractPressurePlateBlock.hasSolidSideOnTop(iWorldReader, blockPos2) || AbstractPressurePlateBlock.hasEnoughSolidSide(iWorldReader, blockPos2, Direction.UP);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n = this.getRedstoneStrength(blockState);
        if (n > 0) {
            this.updateState(serverWorld, blockPos, blockState, n);
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        int n;
        if (!world.isRemote && (n = this.getRedstoneStrength(blockState)) == 0) {
            this.updateState(world, blockPos, blockState, n);
        }
    }

    protected void updateState(World world, BlockPos blockPos, BlockState blockState, int n) {
        boolean bl;
        int n2 = this.computeRedstoneStrength(world, blockPos);
        boolean bl2 = n > 0;
        boolean bl3 = bl = n2 > 0;
        if (n != n2) {
            BlockState blockState2 = this.setRedstoneStrength(blockState, n2);
            world.setBlockState(blockPos, blockState2, 1);
            this.updateNeighbors(world, blockPos);
            world.markBlockRangeForRenderUpdate(blockPos, blockState, blockState2);
        }
        if (!bl && bl2) {
            this.playClickOffSound(world, blockPos);
        } else if (bl && !bl2) {
            this.playClickOnSound(world, blockPos);
        }
        if (bl) {
            world.getPendingBlockTicks().scheduleTick(new BlockPos(blockPos), this, this.getPoweredDuration());
        }
    }

    protected abstract void playClickOnSound(IWorld var1, BlockPos var2);

    protected abstract void playClickOffSound(IWorld var1, BlockPos var2);

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            if (this.getRedstoneStrength(blockState) > 0) {
                this.updateNeighbors(world, blockPos);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    protected void updateNeighbors(World world, BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.down(), this);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return this.getRedstoneStrength(blockState);
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return direction == Direction.UP ? this.getRedstoneStrength(blockState) : 0;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    protected abstract int computeRedstoneStrength(World var1, BlockPos var2);

    protected abstract int getRedstoneStrength(BlockState var1);

    protected abstract BlockState setRedstoneStrength(BlockState var1, int var2);
}

