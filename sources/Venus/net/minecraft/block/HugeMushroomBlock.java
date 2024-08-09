/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class HugeMushroomBlock
extends Block {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    public static final BooleanProperty DOWN = SixWayBlock.DOWN;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP;

    public HugeMushroomBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(NORTH, true)).with(EAST, true)).with(SOUTH, true)).with(WEST, true)).with(UP, true)).with(DOWN, true));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(DOWN, this != world.getBlockState(blockPos.down()).getBlock())).with(UP, this != world.getBlockState(blockPos.up()).getBlock())).with(NORTH, this != world.getBlockState(blockPos.north()).getBlock())).with(EAST, this != world.getBlockState(blockPos.east()).getBlock())).with(SOUTH, this != world.getBlockState(blockPos.south()).getBlock())).with(WEST, this != world.getBlockState(blockPos.west()).getBlock());
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return blockState2.isIn(this) ? (BlockState)blockState.with(FACING_TO_PROPERTY_MAP.get(direction), false) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.NORTH)), blockState.get(NORTH))).with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.SOUTH)), blockState.get(SOUTH))).with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.EAST)), blockState.get(EAST))).with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.WEST)), blockState.get(WEST))).with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.UP)), blockState.get(UP))).with(FACING_TO_PROPERTY_MAP.get(rotation.rotate(Direction.DOWN)), blockState.get(DOWN));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.NORTH)), blockState.get(NORTH))).with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.SOUTH)), blockState.get(SOUTH))).with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.EAST)), blockState.get(EAST))).with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.WEST)), blockState.get(WEST))).with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.UP)), blockState.get(UP))).with(FACING_TO_PROPERTY_MAP.get(mirror.mirror(Direction.DOWN)), blockState.get(DOWN));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }
}

