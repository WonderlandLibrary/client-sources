/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class FourWayBlock
extends Block
implements IWaterLoggable {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter(FourWayBlock::lambda$static$0).collect(Util.toMapCollector());
    protected final VoxelShape[] collisionShapes;
    protected final VoxelShape[] shapes;
    private final Object2IntMap<BlockState> statePaletteMap = new Object2IntOpenHashMap<BlockState>();

    protected FourWayBlock(float f, float f2, float f3, float f4, float f5, AbstractBlock.Properties properties) {
        super(properties);
        this.collisionShapes = this.makeShapes(f, f2, f5, 0.0f, f5);
        this.shapes = this.makeShapes(f, f2, f3, 0.0f, f4);
        for (BlockState blockState : this.stateContainer.getValidStates()) {
            this.getIndex(blockState);
        }
    }

    protected VoxelShape[] makeShapes(float f, float f2, float f3, float f4, float f5) {
        float f6 = 8.0f - f;
        float f7 = 8.0f + f;
        float f8 = 8.0f - f2;
        float f9 = 8.0f + f2;
        VoxelShape voxelShape = Block.makeCuboidShape(f6, 0.0, f6, f7, f3, f7);
        VoxelShape voxelShape2 = Block.makeCuboidShape(f8, f4, 0.0, f9, f5, f9);
        VoxelShape voxelShape3 = Block.makeCuboidShape(f8, f4, f8, f9, f5, 16.0);
        VoxelShape voxelShape4 = Block.makeCuboidShape(0.0, f4, f8, f9, f5, f9);
        VoxelShape voxelShape5 = Block.makeCuboidShape(f8, f4, f8, 16.0, f5, f9);
        VoxelShape voxelShape6 = VoxelShapes.or(voxelShape2, voxelShape5);
        VoxelShape voxelShape7 = VoxelShapes.or(voxelShape3, voxelShape4);
        VoxelShape[] voxelShapeArray = new VoxelShape[]{VoxelShapes.empty(), voxelShape3, voxelShape4, voxelShape7, voxelShape2, VoxelShapes.or(voxelShape3, voxelShape2), VoxelShapes.or(voxelShape4, voxelShape2), VoxelShapes.or(voxelShape7, voxelShape2), voxelShape5, VoxelShapes.or(voxelShape3, voxelShape5), VoxelShapes.or(voxelShape4, voxelShape5), VoxelShapes.or(voxelShape7, voxelShape5), voxelShape6, VoxelShapes.or(voxelShape3, voxelShape6), VoxelShapes.or(voxelShape4, voxelShape6), VoxelShapes.or(voxelShape7, voxelShape6)};
        for (int i = 0; i < 16; ++i) {
            voxelShapeArray[i] = VoxelShapes.or(voxelShape, voxelShapeArray[i]);
        }
        return voxelShapeArray;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.get(WATERLOGGED) == false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.shapes[this.getIndex(blockState)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.collisionShapes[this.getIndex(blockState)];
    }

    private static int getMask(Direction direction) {
        return 1 << direction.getHorizontalIndex();
    }

    protected int getIndex(BlockState blockState) {
        return this.statePaletteMap.computeIntIfAbsent(blockState, FourWayBlock::lambda$getIndex$1);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
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

    private static int lambda$getIndex$1(BlockState blockState) {
        int n = 0;
        if (blockState.get(NORTH).booleanValue()) {
            n |= FourWayBlock.getMask(Direction.NORTH);
        }
        if (blockState.get(EAST).booleanValue()) {
            n |= FourWayBlock.getMask(Direction.EAST);
        }
        if (blockState.get(SOUTH).booleanValue()) {
            n |= FourWayBlock.getMask(Direction.SOUTH);
        }
        if (blockState.get(WEST).booleanValue()) {
            n |= FourWayBlock.getMask(Direction.WEST);
        }
        return n;
    }

    private static boolean lambda$static$0(Map.Entry entry) {
        return ((Direction)entry.getKey()).getAxis().isHorizontal();
    }
}

