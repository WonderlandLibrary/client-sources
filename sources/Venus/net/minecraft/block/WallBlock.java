/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.WallHeight;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class WallBlock
extends Block
implements IWaterLoggable {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_EAST = BlockStateProperties.WALL_HEIGHT_EAST;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_NORTH = BlockStateProperties.WALL_HEIGHT_NORTH;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_SOUTH = BlockStateProperties.WALL_HEIGHT_SOUTH;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_WEST = BlockStateProperties.WALL_HEIGHT_WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Map<BlockState, VoxelShape> stateToCollisionShapeMap;
    private static final VoxelShape CENTER_POLE_SHAPE = Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape WALL_CONNECTION_NORTH_SIDE_SHAPE = Block.makeCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
    private static final VoxelShape WALL_CONNECTION_SOUTH_SIDE_SHAPE = Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
    private static final VoxelShape WALL_CONNECTION_WEST_SIDE_SHAPE = Block.makeCuboidShape(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape WALL_CONNECTION_EAST_SIDE_SHAPE = Block.makeCuboidShape(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

    public WallBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(UP, true)).with(WALL_HEIGHT_NORTH, WallHeight.NONE)).with(WALL_HEIGHT_EAST, WallHeight.NONE)).with(WALL_HEIGHT_SOUTH, WallHeight.NONE)).with(WALL_HEIGHT_WEST, WallHeight.NONE)).with(WATERLOGGED, false));
        this.stateToShapeMap = this.makeShapes(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
        this.stateToCollisionShapeMap = this.makeShapes(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
    }

    private static VoxelShape getHeightAlteredShape(VoxelShape voxelShape, WallHeight wallHeight, VoxelShape voxelShape2, VoxelShape voxelShape3) {
        if (wallHeight == WallHeight.TALL) {
            return VoxelShapes.or(voxelShape, voxelShape3);
        }
        return wallHeight == WallHeight.LOW ? VoxelShapes.or(voxelShape, voxelShape2) : voxelShape;
    }

    private Map<BlockState, VoxelShape> makeShapes(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = 8.0f - f;
        float f8 = 8.0f + f;
        float f9 = 8.0f - f2;
        float f10 = 8.0f + f2;
        VoxelShape voxelShape = Block.makeCuboidShape(f7, 0.0, f7, f8, f3, f8);
        VoxelShape voxelShape2 = Block.makeCuboidShape(f9, f4, 0.0, f10, f5, f10);
        VoxelShape voxelShape3 = Block.makeCuboidShape(f9, f4, f9, f10, f5, 16.0);
        VoxelShape voxelShape4 = Block.makeCuboidShape(0.0, f4, f9, f10, f5, f10);
        VoxelShape voxelShape5 = Block.makeCuboidShape(f9, f4, f9, 16.0, f5, f10);
        VoxelShape voxelShape6 = Block.makeCuboidShape(f9, f4, 0.0, f10, f6, f10);
        VoxelShape voxelShape7 = Block.makeCuboidShape(f9, f4, f9, f10, f6, 16.0);
        VoxelShape voxelShape8 = Block.makeCuboidShape(0.0, f4, f9, f10, f6, f10);
        VoxelShape voxelShape9 = Block.makeCuboidShape(f9, f4, f9, 16.0, f6, f10);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        for (Boolean bl : UP.getAllowedValues()) {
            for (WallHeight wallHeight : WALL_HEIGHT_EAST.getAllowedValues()) {
                for (WallHeight wallHeight2 : WALL_HEIGHT_NORTH.getAllowedValues()) {
                    for (WallHeight wallHeight3 : WALL_HEIGHT_WEST.getAllowedValues()) {
                        for (WallHeight wallHeight4 : WALL_HEIGHT_SOUTH.getAllowedValues()) {
                            VoxelShape voxelShape10 = VoxelShapes.empty();
                            voxelShape10 = WallBlock.getHeightAlteredShape(voxelShape10, wallHeight, voxelShape5, voxelShape9);
                            voxelShape10 = WallBlock.getHeightAlteredShape(voxelShape10, wallHeight3, voxelShape4, voxelShape8);
                            voxelShape10 = WallBlock.getHeightAlteredShape(voxelShape10, wallHeight2, voxelShape2, voxelShape6);
                            voxelShape10 = WallBlock.getHeightAlteredShape(voxelShape10, wallHeight4, voxelShape3, voxelShape7);
                            if (bl.booleanValue()) {
                                voxelShape10 = VoxelShapes.or(voxelShape10, voxelShape);
                            }
                            BlockState blockState = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(UP, bl)).with(WALL_HEIGHT_EAST, wallHeight)).with(WALL_HEIGHT_WEST, wallHeight3)).with(WALL_HEIGHT_NORTH, wallHeight2)).with(WALL_HEIGHT_SOUTH, wallHeight4);
                            builder.put((BlockState)blockState.with(WATERLOGGED, false), voxelShape10);
                            builder.put((BlockState)blockState.with(WATERLOGGED, true), voxelShape10);
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.stateToShapeMap.get(blockState);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.stateToCollisionShapeMap.get(blockState);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private boolean shouldConnect(BlockState blockState, boolean bl, Direction direction) {
        Block block = blockState.getBlock();
        boolean bl2 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(blockState, direction);
        return blockState.isIn(BlockTags.WALLS) || !WallBlock.cannotAttach(block) && bl || block instanceof PaneBlock || bl2;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.east();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        BlockPos blockPos6 = blockPos.up();
        BlockState blockState = world.getBlockState(blockPos2);
        BlockState blockState2 = world.getBlockState(blockPos3);
        BlockState blockState3 = world.getBlockState(blockPos4);
        BlockState blockState4 = world.getBlockState(blockPos5);
        BlockState blockState5 = world.getBlockState(blockPos6);
        boolean bl = this.shouldConnect(blockState, blockState.isSolidSide(world, blockPos2, Direction.SOUTH), Direction.SOUTH);
        boolean bl2 = this.shouldConnect(blockState2, blockState2.isSolidSide(world, blockPos3, Direction.WEST), Direction.WEST);
        boolean bl3 = this.shouldConnect(blockState3, blockState3.isSolidSide(world, blockPos4, Direction.NORTH), Direction.NORTH);
        boolean bl4 = this.shouldConnect(blockState4, blockState4.isSolidSide(world, blockPos5, Direction.EAST), Direction.EAST);
        BlockState blockState6 = (BlockState)this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return this.func_235626_a_(world, blockState6, blockPos6, blockState5, bl, bl2, bl3, bl4);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        if (direction == Direction.DOWN) {
            return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        return direction == Direction.UP ? this.func_235625_a_(iWorld, blockState, blockPos2, blockState2) : this.func_235627_a_(iWorld, blockPos, blockState, blockPos2, blockState2, direction);
    }

    private static boolean hasHeightForProperty(BlockState blockState, Property<WallHeight> property) {
        return blockState.get(property) != WallHeight.NONE;
    }

    private static boolean compareShapes(VoxelShape voxelShape, VoxelShape voxelShape2) {
        return !VoxelShapes.compare(voxelShape2, voxelShape, IBooleanFunction.ONLY_FIRST);
    }

    private BlockState func_235625_a_(IWorldReader iWorldReader, BlockState blockState, BlockPos blockPos, BlockState blockState2) {
        boolean bl = WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_NORTH);
        boolean bl2 = WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_EAST);
        boolean bl3 = WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_SOUTH);
        boolean bl4 = WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_WEST);
        return this.func_235626_a_(iWorldReader, blockState, blockPos, blockState2, bl, bl2, bl3, bl4);
    }

    private BlockState func_235627_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2, Direction direction) {
        Direction direction2 = direction.getOpposite();
        boolean bl = direction == Direction.NORTH ? this.shouldConnect(blockState2, blockState2.isSolidSide(iWorldReader, blockPos2, direction2), direction2) : WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_NORTH);
        boolean bl2 = direction == Direction.EAST ? this.shouldConnect(blockState2, blockState2.isSolidSide(iWorldReader, blockPos2, direction2), direction2) : WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_EAST);
        boolean bl3 = direction == Direction.SOUTH ? this.shouldConnect(blockState2, blockState2.isSolidSide(iWorldReader, blockPos2, direction2), direction2) : WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_SOUTH);
        boolean bl4 = direction == Direction.WEST ? this.shouldConnect(blockState2, blockState2.isSolidSide(iWorldReader, blockPos2, direction2), direction2) : WallBlock.hasHeightForProperty(blockState, WALL_HEIGHT_WEST);
        BlockPos blockPos3 = blockPos.up();
        BlockState blockState3 = iWorldReader.getBlockState(blockPos3);
        return this.func_235626_a_(iWorldReader, blockState, blockPos3, blockState3, bl, bl2, bl3, bl4);
    }

    private BlockState func_235626_a_(IWorldReader iWorldReader, BlockState blockState, BlockPos blockPos, BlockState blockState2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        VoxelShape voxelShape = blockState2.getCollisionShape(iWorldReader, blockPos).project(Direction.DOWN);
        BlockState blockState3 = this.func_235630_a_(blockState, bl, bl2, bl3, bl4, voxelShape);
        return (BlockState)blockState3.with(UP, this.func_235628_a_(blockState3, blockState2, voxelShape));
    }

    private boolean func_235628_a_(BlockState blockState, BlockState blockState2, VoxelShape voxelShape) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        boolean bl4 = bl3 = blockState2.getBlock() instanceof WallBlock && blockState2.get(UP) != false;
        if (bl3) {
            return false;
        }
        WallHeight wallHeight = blockState.get(WALL_HEIGHT_NORTH);
        WallHeight wallHeight2 = blockState.get(WALL_HEIGHT_SOUTH);
        WallHeight wallHeight3 = blockState.get(WALL_HEIGHT_EAST);
        WallHeight wallHeight4 = blockState.get(WALL_HEIGHT_WEST);
        boolean bl5 = wallHeight2 == WallHeight.NONE;
        boolean bl6 = wallHeight4 == WallHeight.NONE;
        boolean bl7 = wallHeight3 == WallHeight.NONE;
        boolean bl8 = wallHeight == WallHeight.NONE;
        boolean bl9 = bl2 = bl8 && bl5 && bl6 && bl7 || bl8 != bl5 || bl6 != bl7;
        if (bl2) {
            return false;
        }
        boolean bl10 = bl = wallHeight == WallHeight.TALL && wallHeight2 == WallHeight.TALL || wallHeight3 == WallHeight.TALL && wallHeight4 == WallHeight.TALL;
        if (bl) {
            return true;
        }
        return blockState2.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || WallBlock.compareShapes(voxelShape, CENTER_POLE_SHAPE);
    }

    private BlockState func_235630_a_(BlockState blockState, boolean bl, boolean bl2, boolean bl3, boolean bl4, VoxelShape voxelShape) {
        return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(WALL_HEIGHT_NORTH, this.func_235633_a_(bl, voxelShape, WALL_CONNECTION_NORTH_SIDE_SHAPE))).with(WALL_HEIGHT_EAST, this.func_235633_a_(bl2, voxelShape, WALL_CONNECTION_EAST_SIDE_SHAPE))).with(WALL_HEIGHT_SOUTH, this.func_235633_a_(bl3, voxelShape, WALL_CONNECTION_SOUTH_SIDE_SHAPE))).with(WALL_HEIGHT_WEST, this.func_235633_a_(bl4, voxelShape, WALL_CONNECTION_WEST_SIDE_SHAPE));
    }

    private WallHeight func_235633_a_(boolean bl, VoxelShape voxelShape, VoxelShape voxelShape2) {
        if (bl) {
            return WallBlock.compareShapes(voxelShape, voxelShape2) ? WallHeight.TALL : WallHeight.LOW;
        }
        return WallHeight.NONE;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.get(WATERLOGGED) == false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, WALL_HEIGHT_NORTH, WALL_HEIGHT_EAST, WALL_HEIGHT_WEST, WALL_HEIGHT_SOUTH, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(WALL_HEIGHT_NORTH, blockState.get(WALL_HEIGHT_SOUTH))).with(WALL_HEIGHT_EAST, blockState.get(WALL_HEIGHT_WEST))).with(WALL_HEIGHT_SOUTH, blockState.get(WALL_HEIGHT_NORTH))).with(WALL_HEIGHT_WEST, blockState.get(WALL_HEIGHT_EAST));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(WALL_HEIGHT_NORTH, blockState.get(WALL_HEIGHT_EAST))).with(WALL_HEIGHT_EAST, blockState.get(WALL_HEIGHT_SOUTH))).with(WALL_HEIGHT_SOUTH, blockState.get(WALL_HEIGHT_WEST))).with(WALL_HEIGHT_WEST, blockState.get(WALL_HEIGHT_NORTH));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(WALL_HEIGHT_NORTH, blockState.get(WALL_HEIGHT_WEST))).with(WALL_HEIGHT_EAST, blockState.get(WALL_HEIGHT_NORTH))).with(WALL_HEIGHT_SOUTH, blockState.get(WALL_HEIGHT_EAST))).with(WALL_HEIGHT_WEST, blockState.get(WALL_HEIGHT_SOUTH));
            }
        }
        return blockState;
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)blockState.with(WALL_HEIGHT_NORTH, blockState.get(WALL_HEIGHT_SOUTH))).with(WALL_HEIGHT_SOUTH, blockState.get(WALL_HEIGHT_NORTH));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)blockState.with(WALL_HEIGHT_EAST, blockState.get(WALL_HEIGHT_WEST))).with(WALL_HEIGHT_WEST, blockState.get(WALL_HEIGHT_EAST));
            }
        }
        return super.mirror(blockState, mirror);
    }
}

