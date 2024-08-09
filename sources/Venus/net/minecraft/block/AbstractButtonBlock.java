/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractButtonBlock
extends HorizontalFaceBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape AABB_CEILING_X_OFF = Block.makeCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    protected static final VoxelShape AABB_CEILING_Z_OFF = Block.makeCuboidShape(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
    protected static final VoxelShape AABB_FLOOR_X_OFF = Block.makeCuboidShape(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
    protected static final VoxelShape AABB_FLOOR_Z_OFF = Block.makeCuboidShape(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
    protected static final VoxelShape AABB_NORTH_OFF = Block.makeCuboidShape(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape AABB_SOUTH_OFF = Block.makeCuboidShape(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
    protected static final VoxelShape AABB_WEST_OFF = Block.makeCuboidShape(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape AABB_EAST_OFF = Block.makeCuboidShape(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
    protected static final VoxelShape AABB_CEILING_X_ON = Block.makeCuboidShape(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
    protected static final VoxelShape AABB_CEILING_Z_ON = Block.makeCuboidShape(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
    protected static final VoxelShape AABB_FLOOR_X_ON = Block.makeCuboidShape(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
    protected static final VoxelShape AABB_FLOOR_Z_ON = Block.makeCuboidShape(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
    protected static final VoxelShape AABB_NORTH_ON = Block.makeCuboidShape(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape AABB_SOUTH_ON = Block.makeCuboidShape(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
    protected static final VoxelShape AABB_WEST_ON = Block.makeCuboidShape(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape AABB_EAST_ON = Block.makeCuboidShape(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
    private final boolean wooden;

    protected AbstractButtonBlock(boolean bl, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(POWERED, false)).with(FACE, AttachFace.WALL));
        this.wooden = bl;
    }

    private int getActiveDuration() {
        return this.wooden ? 30 : 20;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        boolean bl = blockState.get(POWERED);
        switch ((AttachFace)blockState.get(FACE)) {
            case FLOOR: {
                if (direction.getAxis() == Direction.Axis.X) {
                    return bl ? AABB_FLOOR_X_ON : AABB_FLOOR_X_OFF;
                }
                return bl ? AABB_FLOOR_Z_ON : AABB_FLOOR_Z_OFF;
            }
            case WALL: {
                switch (direction) {
                    case EAST: {
                        return bl ? AABB_EAST_ON : AABB_EAST_OFF;
                    }
                    case WEST: {
                        return bl ? AABB_WEST_ON : AABB_WEST_OFF;
                    }
                    case SOUTH: {
                        return bl ? AABB_SOUTH_ON : AABB_SOUTH_OFF;
                    }
                }
                return bl ? AABB_NORTH_ON : AABB_NORTH_OFF;
            }
        }
        if (direction.getAxis() == Direction.Axis.X) {
            return bl ? AABB_CEILING_X_ON : AABB_CEILING_X_OFF;
        }
        return bl ? AABB_CEILING_Z_ON : AABB_CEILING_Z_OFF;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (blockState.get(POWERED).booleanValue()) {
            return ActionResultType.CONSUME;
        }
        this.powerBlock(blockState, world, blockPos);
        this.playSound(playerEntity, world, blockPos, false);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    public void powerBlock(BlockState blockState, World world, BlockPos blockPos) {
        world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, true), 0);
        this.updateNeighbors(blockState, world, blockPos);
        world.getPendingBlockTicks().scheduleTick(blockPos, this, this.getActiveDuration());
    }

    protected void playSound(@Nullable PlayerEntity playerEntity, IWorld iWorld, BlockPos blockPos, boolean bl) {
        iWorld.playSound(bl ? playerEntity : null, blockPos, this.getSoundEvent(bl), SoundCategory.BLOCKS, 0.3f, bl ? 0.6f : 0.5f);
    }

    protected abstract SoundEvent getSoundEvent(boolean var1);

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
        return blockState.get(POWERED) != false && AbstractButtonBlock.getFacing(blockState) == direction ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(POWERED).booleanValue()) {
            if (this.wooden) {
                this.checkPressed(blockState, serverWorld, blockPos);
            } else {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWERED, false), 0);
                this.updateNeighbors(blockState, serverWorld, blockPos);
                this.playSound(null, serverWorld, blockPos, true);
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!world.isRemote && this.wooden && !blockState.get(POWERED).booleanValue()) {
            this.checkPressed(blockState, world, blockPos);
        }
    }

    private void checkPressed(BlockState blockState, World world, BlockPos blockPos) {
        boolean bl;
        List<AbstractArrowEntity> list = world.getEntitiesWithinAABB(AbstractArrowEntity.class, blockState.getShape(world, blockPos).getBoundingBox().offset(blockPos));
        boolean bl2 = !list.isEmpty();
        if (bl2 != (bl = blockState.get(POWERED).booleanValue())) {
            world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl2), 0);
            this.updateNeighbors(blockState, world, blockPos);
            this.playSound(null, world, blockPos, bl2);
        }
        if (bl2) {
            world.getPendingBlockTicks().scheduleTick(new BlockPos(blockPos), this, this.getActiveDuration());
        }
    }

    private void updateNeighbors(BlockState blockState, World world, BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(AbstractButtonBlock.getFacing(blockState).getOpposite()), this);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, POWERED, FACE);
    }
}

