/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ScaffoldingBlock
extends Block
implements IWaterLoggable {
    private static final VoxelShape TOP_SLAB_SHAPE;
    private static final VoxelShape FULL_SHAPE;
    private static final VoxelShape BOTTOM_SLAB_SHAPE;
    private static final VoxelShape field_220124_g;
    public static final IntegerProperty DISTANCE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty BOTTOM;

    protected ScaffoldingBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(DISTANCE, 7)).with(WATERLOGGED, false)).with(BOTTOM, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, WATERLOGGED, BOTTOM);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (!iSelectionContext.hasItem(blockState.getBlock().asItem())) {
            return blockState.get(BOTTOM) != false ? FULL_SHAPE : TOP_SLAB_SHAPE;
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        return blockItemUseContext.getItem().getItem() == this.asItem();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockPos blockPos = blockItemUseContext.getPos();
        World world = blockItemUseContext.getWorld();
        int n = ScaffoldingBlock.getDistance(world, blockPos);
        return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(DISTANCE, n)).with(BOTTOM, this.hasScaffoldingBelow(world, blockPos, n));
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!world.isRemote) {
            world.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        if (!iWorld.isRemote()) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return blockState;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n = ScaffoldingBlock.getDistance(serverWorld, blockPos);
        BlockState blockState2 = (BlockState)((BlockState)blockState.with(DISTANCE, n)).with(BOTTOM, this.hasScaffoldingBelow(serverWorld, blockPos, n));
        if (blockState2.get(DISTANCE) == 7) {
            if (blockState.get(DISTANCE) == 7) {
                serverWorld.addEntity(new FallingBlockEntity(serverWorld, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, (BlockState)blockState2.with(WATERLOGGED, false)));
            } else {
                serverWorld.destroyBlock(blockPos, false);
            }
        } else if (blockState != blockState2) {
            serverWorld.setBlockState(blockPos, blockState2, 0);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return ScaffoldingBlock.getDistance(iWorldReader, blockPos) < 7;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (iSelectionContext.func_216378_a(VoxelShapes.fullCube(), blockPos, true) && !iSelectionContext.getPosY()) {
            return TOP_SLAB_SHAPE;
        }
        return blockState.get(DISTANCE) != 0 && blockState.get(BOTTOM) != false && iSelectionContext.func_216378_a(field_220124_g, blockPos, true) ? BOTTOM_SLAB_SHAPE : VoxelShapes.empty();
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    private boolean hasScaffoldingBelow(IBlockReader iBlockReader, BlockPos blockPos, int n) {
        return n > 0 && !iBlockReader.getBlockState(blockPos.down()).isIn(this);
    }

    public static int getDistance(IBlockReader iBlockReader, BlockPos blockPos) {
        Direction direction;
        BlockState blockState;
        BlockPos.Mutable mutable = blockPos.toMutable().move(Direction.DOWN);
        BlockState blockState2 = iBlockReader.getBlockState(mutable);
        int n = 7;
        if (blockState2.isIn(Blocks.SCAFFOLDING)) {
            n = blockState2.get(DISTANCE);
        } else if (blockState2.isSolidSide(iBlockReader, mutable, Direction.UP)) {
            return 1;
        }
        Iterator<Direction> iterator2 = Direction.Plane.HORIZONTAL.iterator();
        while (iterator2.hasNext() && (!(blockState = iBlockReader.getBlockState(mutable.setAndMove(blockPos, direction = iterator2.next()))).isIn(Blocks.SCAFFOLDING) || (n = Math.min(n, blockState.get(DISTANCE) + 1)) != 1)) {
        }
        return n;
    }

    static {
        BOTTOM_SLAB_SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        field_220124_g = VoxelShapes.fullCube().withOffset(0.0, -1.0, 0.0);
        DISTANCE = BlockStateProperties.DISTANCE_0_7;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        BOTTOM = BlockStateProperties.BOTTOM;
        VoxelShape voxelShape = Block.makeCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        VoxelShape voxelShape2 = Block.makeCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
        VoxelShape voxelShape3 = Block.makeCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
        VoxelShape voxelShape4 = Block.makeCuboidShape(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
        VoxelShape voxelShape5 = Block.makeCuboidShape(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
        TOP_SLAB_SHAPE = VoxelShapes.or(voxelShape, voxelShape2, voxelShape3, voxelShape4, voxelShape5);
        VoxelShape voxelShape6 = Block.makeCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
        VoxelShape voxelShape7 = Block.makeCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        VoxelShape voxelShape8 = Block.makeCuboidShape(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
        VoxelShape voxelShape9 = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
        FULL_SHAPE = VoxelShapes.or(BOTTOM_SLAB_SHAPE, TOP_SLAB_SHAPE, voxelShape7, voxelShape6, voxelShape9, voxelShape8);
    }
}

