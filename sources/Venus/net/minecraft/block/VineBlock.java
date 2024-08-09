/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class VineBlock
extends Block {
    public static final BooleanProperty UP = SixWayBlock.UP;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter(VineBlock::lambda$static$0).collect(Util.toMapCollector());
    private static final VoxelShape UP_AABB = Block.makeCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_AABB = Block.makeCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> stateToShapeMap;

    public VineBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(UP, false)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().collect(Collectors.toMap(Function.identity(), VineBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState blockState) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (blockState.get(UP).booleanValue()) {
            voxelShape = UP_AABB;
        }
        if (blockState.get(NORTH).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, SOUTH_AABB);
        }
        if (blockState.get(SOUTH).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, NORTH_AABB);
        }
        if (blockState.get(EAST).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, WEST_AABB);
        }
        if (blockState.get(WEST).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, EAST_AABB);
        }
        return voxelShape;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.stateToShapeMap.get(blockState);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return this.getBlocksAttachedTo(this.func_196545_h(blockState, iWorldReader, blockPos));
    }

    private boolean getBlocksAttachedTo(BlockState blockState) {
        return this.countBlocksVineIsAttachedTo(blockState) > 0;
    }

    private int countBlocksVineIsAttachedTo(BlockState blockState) {
        int n = 0;
        for (BooleanProperty booleanProperty : FACING_TO_PROPERTY_MAP.values()) {
            if (!blockState.get(booleanProperty).booleanValue()) continue;
            ++n;
        }
        return n;
    }

    private boolean hasAttachment(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        if (direction == Direction.DOWN) {
            return true;
        }
        BlockPos blockPos2 = blockPos.offset(direction);
        if (VineBlock.canAttachTo(iBlockReader, blockPos2, direction)) {
            return false;
        }
        if (direction.getAxis() == Direction.Axis.Y) {
            return true;
        }
        BooleanProperty booleanProperty = FACING_TO_PROPERTY_MAP.get(direction);
        BlockState blockState = iBlockReader.getBlockState(blockPos.up());
        return blockState.isIn(this) && blockState.get(booleanProperty) != false;
    }

    public static boolean canAttachTo(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        return Block.doesSideFillSquare(blockState.getCollisionShape(iBlockReader, blockPos), direction.getOpposite());
    }

    private BlockState func_196545_h(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        if (blockState.get(UP).booleanValue()) {
            blockState = (BlockState)blockState.with(UP, VineBlock.canAttachTo(iBlockReader, blockPos2, Direction.DOWN));
        }
        AbstractBlock.AbstractBlockState abstractBlockState = null;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanProperty = VineBlock.getPropertyFor(direction);
            if (!blockState.get(booleanProperty).booleanValue()) continue;
            boolean bl = this.hasAttachment(iBlockReader, blockPos, direction);
            if (!bl) {
                if (abstractBlockState == null) {
                    abstractBlockState = iBlockReader.getBlockState(blockPos2);
                }
                bl = abstractBlockState.isIn(this) && abstractBlockState.get(booleanProperty) != false;
            }
            blockState = (BlockState)blockState.with(booleanProperty, bl);
        }
        return blockState;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.DOWN) {
            return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        BlockState blockState3 = this.func_196545_h(blockState, iWorld, blockPos);
        return !this.getBlocksAttachedTo(blockState3) ? Blocks.AIR.getDefaultState() : blockState3;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.rand.nextInt(4) == 0) {
            Direction direction = Direction.getRandomDirection(random2);
            BlockPos blockPos2 = blockPos.up();
            if (direction.getAxis().isHorizontal() && !blockState.get(VineBlock.getPropertyFor(direction)).booleanValue()) {
                if (this.hasVineBelow(serverWorld, blockPos)) {
                    BlockPos blockPos3 = blockPos.offset(direction);
                    BlockState blockState2 = serverWorld.getBlockState(blockPos3);
                    if (blockState2.isAir()) {
                        Direction direction2 = direction.rotateY();
                        Direction direction3 = direction.rotateYCCW();
                        boolean bl = blockState.get(VineBlock.getPropertyFor(direction2));
                        boolean bl2 = blockState.get(VineBlock.getPropertyFor(direction3));
                        BlockPos blockPos4 = blockPos3.offset(direction2);
                        BlockPos blockPos5 = blockPos3.offset(direction3);
                        if (bl && VineBlock.canAttachTo(serverWorld, blockPos4, direction2)) {
                            serverWorld.setBlockState(blockPos3, (BlockState)this.getDefaultState().with(VineBlock.getPropertyFor(direction2), true), 1);
                        } else if (bl2 && VineBlock.canAttachTo(serverWorld, blockPos5, direction3)) {
                            serverWorld.setBlockState(blockPos3, (BlockState)this.getDefaultState().with(VineBlock.getPropertyFor(direction3), true), 1);
                        } else {
                            Direction direction4 = direction.getOpposite();
                            if (bl && serverWorld.isAirBlock(blockPos4) && VineBlock.canAttachTo(serverWorld, blockPos.offset(direction2), direction4)) {
                                serverWorld.setBlockState(blockPos4, (BlockState)this.getDefaultState().with(VineBlock.getPropertyFor(direction4), true), 1);
                            } else if (bl2 && serverWorld.isAirBlock(blockPos5) && VineBlock.canAttachTo(serverWorld, blockPos.offset(direction3), direction4)) {
                                serverWorld.setBlockState(blockPos5, (BlockState)this.getDefaultState().with(VineBlock.getPropertyFor(direction4), true), 1);
                            } else if ((double)serverWorld.rand.nextFloat() < 0.05 && VineBlock.canAttachTo(serverWorld, blockPos3.up(), Direction.UP)) {
                                serverWorld.setBlockState(blockPos3, (BlockState)this.getDefaultState().with(UP, true), 1);
                            }
                        }
                    } else if (VineBlock.canAttachTo(serverWorld, blockPos3, direction)) {
                        serverWorld.setBlockState(blockPos, (BlockState)blockState.with(VineBlock.getPropertyFor(direction), true), 1);
                    }
                }
            } else {
                BlockState blockState3;
                BlockState blockState4;
                BlockPos blockPos6;
                BlockState blockState5;
                if (direction == Direction.UP && blockPos.getY() < 255) {
                    if (this.hasAttachment(serverWorld, blockPos, direction)) {
                        serverWorld.setBlockState(blockPos, (BlockState)blockState.with(UP, true), 1);
                        return;
                    }
                    if (serverWorld.isAirBlock(blockPos2)) {
                        if (!this.hasVineBelow(serverWorld, blockPos)) {
                            return;
                        }
                        BlockState blockState6 = blockState;
                        for (Direction direction5 : Direction.Plane.HORIZONTAL) {
                            if (!random2.nextBoolean() && VineBlock.canAttachTo(serverWorld, blockPos2.offset(direction5), Direction.UP)) continue;
                            blockState6 = (BlockState)blockState6.with(VineBlock.getPropertyFor(direction5), false);
                        }
                        if (this.isFacingCardinal(blockState6)) {
                            serverWorld.setBlockState(blockPos2, blockState6, 1);
                        }
                        return;
                    }
                }
                if (blockPos.getY() > 0 && ((blockState5 = serverWorld.getBlockState(blockPos6 = blockPos.down())).isAir() || blockState5.isIn(this)) && (blockState4 = blockState5.isAir() ? this.getDefaultState() : blockState5) != (blockState3 = this.func_196544_a(blockState, blockState4, random2)) && this.isFacingCardinal(blockState3)) {
                    serverWorld.setBlockState(blockPos6, blockState3, 1);
                }
            }
        }
    }

    private BlockState func_196544_a(BlockState blockState, BlockState blockState2, Random random2) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanProperty;
            if (!random2.nextBoolean() || !blockState.get(booleanProperty = VineBlock.getPropertyFor(direction)).booleanValue()) continue;
            blockState2 = (BlockState)blockState2.with(booleanProperty, true);
        }
        return blockState2;
    }

    private boolean isFacingCardinal(BlockState blockState) {
        return blockState.get(NORTH) != false || blockState.get(EAST) != false || blockState.get(SOUTH) != false || blockState.get(WEST) != false;
    }

    private boolean hasVineBelow(IBlockReader iBlockReader, BlockPos blockPos) {
        int n = 4;
        Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(blockPos.getX() - 4, blockPos.getY() - 1, blockPos.getZ() - 4, blockPos.getX() + 4, blockPos.getY() + 1, blockPos.getZ() + 4);
        int n2 = 5;
        for (BlockPos blockPos2 : iterable) {
            if (!iBlockReader.getBlockState(blockPos2).isIn(this) || --n2 > 0) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        BlockState blockState2 = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos());
        if (blockState2.isIn(this)) {
            return this.countBlocksVineIsAttachedTo(blockState2) < FACING_TO_PROPERTY_MAP.size();
        }
        return super.isReplaceable(blockState, blockItemUseContext);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos());
        boolean bl = blockState.isIn(this);
        BlockState blockState2 = bl ? blockState : this.getDefaultState();
        for (Direction direction : blockItemUseContext.getNearestLookingDirections()) {
            boolean bl2;
            if (direction == Direction.DOWN) continue;
            BooleanProperty booleanProperty = VineBlock.getPropertyFor(direction);
            boolean bl3 = bl2 = bl && blockState.get(booleanProperty) != false;
            if (bl2 || !this.hasAttachment(blockItemUseContext.getWorld(), blockItemUseContext.getPos(), direction)) continue;
            return (BlockState)blockState2.with(booleanProperty, true);
        }
        return bl ? blockState2 : null;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST);
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

    public static BooleanProperty getPropertyFor(Direction direction) {
        return FACING_TO_PROPERTY_MAP.get(direction);
    }

    private static boolean lambda$static$0(Map.Entry entry) {
        return entry.getKey() != Direction.DOWN;
    }
}

