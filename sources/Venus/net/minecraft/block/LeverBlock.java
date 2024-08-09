/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class LeverBlock
extends HorizontalFaceBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape LEVER_NORTH_AABB = Block.makeCuboidShape(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
    protected static final VoxelShape LEVER_SOUTH_AABB = Block.makeCuboidShape(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
    protected static final VoxelShape LEVER_WEST_AABB = Block.makeCuboidShape(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
    protected static final VoxelShape LEVER_EAST_AABB = Block.makeCuboidShape(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
    protected static final VoxelShape FLOOR_Z_SHAPE = Block.makeCuboidShape(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
    protected static final VoxelShape FLOOR_X_SHAPE = Block.makeCuboidShape(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
    protected static final VoxelShape CEILING_Z_SHAPE = Block.makeCuboidShape(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
    protected static final VoxelShape CEILING_X_SHAPE = Block.makeCuboidShape(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

    protected LeverBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(POWERED, false)).with(FACE, AttachFace.WALL));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch ((AttachFace)blockState.get(FACE)) {
            case FLOOR: {
                switch (blockState.get(HORIZONTAL_FACING).getAxis()) {
                    case X: {
                        return FLOOR_X_SHAPE;
                    }
                }
                return FLOOR_Z_SHAPE;
            }
            case WALL: {
                switch (blockState.get(HORIZONTAL_FACING)) {
                    case EAST: {
                        return LEVER_EAST_AABB;
                    }
                    case WEST: {
                        return LEVER_WEST_AABB;
                    }
                    case SOUTH: {
                        return LEVER_SOUTH_AABB;
                    }
                }
                return LEVER_NORTH_AABB;
            }
        }
        switch (blockState.get(HORIZONTAL_FACING).getAxis()) {
            case X: {
                return CEILING_X_SHAPE;
            }
        }
        return CEILING_Z_SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            BlockState blockState2 = (BlockState)blockState.func_235896_a_(POWERED);
            if (blockState2.get(POWERED).booleanValue()) {
                LeverBlock.addParticles(blockState2, world, blockPos, 1.0f);
            }
            return ActionResultType.SUCCESS;
        }
        BlockState blockState3 = this.setPowered(blockState, world, blockPos);
        float f = blockState3.get(POWERED) != false ? 0.6f : 0.5f;
        world.playSound(null, blockPos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        return ActionResultType.CONSUME;
    }

    public BlockState setPowered(BlockState blockState, World world, BlockPos blockPos) {
        blockState = (BlockState)blockState.func_235896_a_(POWERED);
        world.setBlockState(blockPos, blockState, 0);
        this.updateNeighbors(blockState, world, blockPos);
        return blockState;
    }

    private static void addParticles(BlockState blockState, IWorld iWorld, BlockPos blockPos, float f) {
        Direction direction = blockState.get(HORIZONTAL_FACING).getOpposite();
        Direction direction2 = LeverBlock.getFacing(blockState).getOpposite();
        double d = (double)blockPos.getX() + 0.5 + 0.1 * (double)direction.getXOffset() + 0.2 * (double)direction2.getXOffset();
        double d2 = (double)blockPos.getY() + 0.5 + 0.1 * (double)direction.getYOffset() + 0.2 * (double)direction2.getYOffset();
        double d3 = (double)blockPos.getZ() + 0.5 + 0.1 * (double)direction.getZOffset() + 0.2 * (double)direction2.getZOffset();
        iWorld.addParticle(new RedstoneParticleData(1.0f, 0.0f, 0.0f, f), d, d2, d3, 0.0, 0.0, 0.0);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(POWERED).booleanValue() && random2.nextFloat() < 0.25f) {
            LeverBlock.addParticles(blockState, world, blockPos, 0.5f);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            if (blockState.get(POWERED).booleanValue()) {
                this.updateNeighbors(blockState, world, blockPos);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false && LeverBlock.getFacing(blockState) == direction ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    private void updateNeighbors(BlockState blockState, World world, BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(LeverBlock.getFacing(blockState).getOpposite()), this);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACE, HORIZONTAL_FACING, POWERED);
    }
}

