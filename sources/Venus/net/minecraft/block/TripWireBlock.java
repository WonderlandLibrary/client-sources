/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TripWireBlock
extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = FourWayBlock.FACING_TO_PROPERTY_MAP;
    protected static final VoxelShape AABB = Block.makeCuboidShape(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
    protected static final VoxelShape TRIP_WRITE_ATTACHED_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private final TripWireHookBlock hook;

    public TripWireBlock(TripWireHookBlock tripWireHookBlock, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWERED, false)).with(ATTACHED, false)).with(DISARMED, false)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false));
        this.hook = tripWireHookBlock;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return blockState.get(ATTACHED) != false ? AABB : TRIP_WRITE_ATTACHED_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(NORTH, this.shouldConnectTo(world.getBlockState(blockPos.north()), Direction.NORTH))).with(EAST, this.shouldConnectTo(world.getBlockState(blockPos.east()), Direction.EAST))).with(SOUTH, this.shouldConnectTo(world.getBlockState(blockPos.south()), Direction.SOUTH))).with(WEST, this.shouldConnectTo(world.getBlockState(blockPos.west()), Direction.WEST));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction.getAxis().isHorizontal() ? (BlockState)blockState.with(FACING_TO_PROPERTY_MAP.get(direction), this.shouldConnectTo(blockState2, direction)) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.notifyHook(world, blockPos, blockState);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            this.notifyHook(world, blockPos, (BlockState)blockState.with(POWERED, true));
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote && !playerEntity.getHeldItemMainhand().isEmpty() && playerEntity.getHeldItemMainhand().getItem() == Items.SHEARS) {
            world.setBlockState(blockPos, (BlockState)blockState.with(DISARMED, true), 1);
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    private void notifyHook(World world, BlockPos blockPos, BlockState blockState) {
        block0: for (Direction direction : new Direction[]{Direction.SOUTH, Direction.WEST}) {
            for (int i = 1; i < 42; ++i) {
                BlockPos blockPos2 = blockPos.offset(direction, i);
                BlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.isIn(this.hook)) {
                    if (blockState2.get(TripWireHookBlock.FACING) != direction.getOpposite()) continue block0;
                    this.hook.calculateState(world, blockPos2, blockState2, false, true, i, blockState);
                    continue block0;
                }
                if (!blockState2.isIn(this)) continue block0;
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!world.isRemote && !blockState.get(POWERED).booleanValue()) {
            this.updateState(world, blockPos);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.getBlockState(blockPos).get(POWERED).booleanValue()) {
            this.updateState(serverWorld, blockPos);
        }
    }

    private void updateState(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        boolean bl = blockState.get(POWERED);
        boolean bl2 = false;
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, blockState.getShape(world, blockPos).getBoundingBox().offset(blockPos));
        if (!list.isEmpty()) {
            for (Entity entity2 : list) {
                if (entity2.doesEntityNotTriggerPressurePlate()) continue;
                bl2 = true;
                break;
            }
        }
        if (bl2 != bl) {
            blockState = (BlockState)blockState.with(POWERED, bl2);
            world.setBlockState(blockPos, blockState, 0);
            this.notifyHook(world, blockPos, blockState);
        }
        if (bl2) {
            world.getPendingBlockTicks().scheduleTick(new BlockPos(blockPos), this, 10);
        }
    }

    public boolean shouldConnectTo(BlockState blockState, Direction direction) {
        Block block = blockState.getBlock();
        if (block == this.hook) {
            return blockState.get(TripWireHookBlock.FACING) == direction.getOpposite();
        }
        return block == this;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(SOUTH))).with(EAST, blockState.get(WEST))).with(SOUTH, blockState.get(NORTH))).with(WEST, blockState.get(EAST));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(EAST))).with(EAST, blockState.get(SOUTH))).with(SOUTH, blockState.get(WEST))).with(WEST, blockState.get(NORTH));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(WEST))).with(EAST, blockState.get(NORTH))).with(SOUTH, blockState.get(EAST))).with(WEST, blockState.get(SOUTH));
            }
        }
        return blockState;
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)blockState.with(NORTH, blockState.get(SOUTH))).with(SOUTH, blockState.get(NORTH));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)blockState.with(EAST, blockState.get(WEST))).with(WEST, blockState.get(EAST));
            }
        }
        return super.mirror(blockState, mirror);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH);
    }
}

