/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class StairsBlock
extends Block
implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape AABB_SLAB_TOP = SlabBlock.TOP_SHAPE;
    protected static final VoxelShape AABB_SLAB_BOTTOM = SlabBlock.BOTTOM_SHAPE;
    protected static final VoxelShape NWD_CORNER = Block.makeCuboidShape(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
    protected static final VoxelShape SWD_CORNER = Block.makeCuboidShape(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
    protected static final VoxelShape NWU_CORNER = Block.makeCuboidShape(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
    protected static final VoxelShape SWU_CORNER = Block.makeCuboidShape(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
    protected static final VoxelShape NED_CORNER = Block.makeCuboidShape(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
    protected static final VoxelShape SED_CORNER = Block.makeCuboidShape(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape NEU_CORNER = Block.makeCuboidShape(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
    protected static final VoxelShape SEU_CORNER = Block.makeCuboidShape(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape[] SLAB_TOP_SHAPES = StairsBlock.makeShapes(AABB_SLAB_TOP, NWD_CORNER, NED_CORNER, SWD_CORNER, SED_CORNER);
    protected static final VoxelShape[] SLAB_BOTTOM_SHAPES = StairsBlock.makeShapes(AABB_SLAB_BOTTOM, NWU_CORNER, NEU_CORNER, SWU_CORNER, SEU_CORNER);
    private static final int[] PALETTE_SHAPE_MAP = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    private final Block modelBlock;
    private final BlockState modelState;

    private static VoxelShape[] makeShapes(VoxelShape voxelShape, VoxelShape voxelShape2, VoxelShape voxelShape3, VoxelShape voxelShape4, VoxelShape voxelShape5) {
        return (VoxelShape[])IntStream.range(0, 16).mapToObj(arg_0 -> StairsBlock.lambda$makeShapes$0(voxelShape, voxelShape2, voxelShape3, voxelShape4, voxelShape5, arg_0)).toArray(StairsBlock::lambda$makeShapes$1);
    }

    private static VoxelShape combineShapes(int n, VoxelShape voxelShape, VoxelShape voxelShape2, VoxelShape voxelShape3, VoxelShape voxelShape4, VoxelShape voxelShape5) {
        VoxelShape voxelShape6 = voxelShape;
        if ((n & 1) != 0) {
            voxelShape6 = VoxelShapes.or(voxelShape, voxelShape2);
        }
        if ((n & 2) != 0) {
            voxelShape6 = VoxelShapes.or(voxelShape6, voxelShape3);
        }
        if ((n & 4) != 0) {
            voxelShape6 = VoxelShapes.or(voxelShape6, voxelShape4);
        }
        if ((n & 8) != 0) {
            voxelShape6 = VoxelShapes.or(voxelShape6, voxelShape5);
        }
        return voxelShape6;
    }

    protected StairsBlock(BlockState blockState, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(HALF, Half.BOTTOM)).with(SHAPE, StairsShape.STRAIGHT)).with(WATERLOGGED, false));
        this.modelBlock = blockState.getBlock();
        this.modelState = blockState;
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return (blockState.get(HALF) == Half.TOP ? SLAB_TOP_SHAPES : SLAB_BOTTOM_SHAPES)[PALETTE_SHAPE_MAP[this.getPaletteId(blockState)]];
    }

    private int getPaletteId(BlockState blockState) {
        return blockState.get(SHAPE).ordinal() * 4 + blockState.get(FACING).getHorizontalIndex();
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        this.modelBlock.animateTick(blockState, world, blockPos, random2);
    }

    @Override
    public void onBlockClicked(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.modelState.onBlockClicked(world, blockPos, playerEntity);
    }

    @Override
    public void onPlayerDestroy(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        this.modelBlock.onPlayerDestroy(iWorld, blockPos, blockState);
    }

    @Override
    public float getExplosionResistance() {
        return this.modelBlock.getExplosionResistance();
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState.getBlock())) {
            this.modelState.neighborChanged(world, blockPos, Blocks.AIR, blockPos, true);
            this.modelBlock.onBlockAdded(this.modelState, world, blockPos, blockState2, true);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            this.modelState.onReplaced(world, blockPos, blockState2, bl);
        }
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
        this.modelBlock.onEntityWalk(world, blockPos, entity2);
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return this.modelBlock.ticksRandomly(blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.modelBlock.randomTick(blockState, serverWorld, blockPos, random2);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.modelBlock.tick(blockState, serverWorld, blockPos, random2);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        return this.modelState.onBlockActivated(world, playerEntity, hand, blockRayTraceResult);
    }

    @Override
    public void onExplosionDestroy(World world, BlockPos blockPos, Explosion explosion) {
        this.modelBlock.onExplosionDestroy(world, blockPos, explosion);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getFace();
        BlockPos blockPos = blockItemUseContext.getPos();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockPos);
        BlockState blockState = (BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing())).with(HALF, !(direction == Direction.DOWN || direction != Direction.UP && blockItemUseContext.getHitVec().y - (double)blockPos.getY() > 0.5) ? Half.BOTTOM : Half.TOP)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return (BlockState)blockState.with(SHAPE, StairsBlock.getShapeProperty(blockState, blockItemUseContext.getWorld(), blockPos));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return direction.getAxis().isHorizontal() ? (BlockState)blockState.with(SHAPE, StairsBlock.getShapeProperty(blockState, iWorld, blockPos)) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    private static StairsShape getShapeProperty(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        Direction direction;
        Object object;
        Direction direction2 = blockState.get(FACING);
        BlockState blockState2 = iBlockReader.getBlockState(blockPos.offset(direction2));
        if (StairsBlock.isBlockStairs(blockState2) && blockState.get(HALF) == blockState2.get(HALF) && ((Direction)(object = blockState2.get(FACING))).getAxis() != blockState.get(FACING).getAxis() && StairsBlock.isDifferentStairs(blockState, iBlockReader, blockPos, ((Direction)object).getOpposite())) {
            if (object == direction2.rotateYCCW()) {
                return StairsShape.OUTER_LEFT;
            }
            return StairsShape.OUTER_RIGHT;
        }
        object = iBlockReader.getBlockState(blockPos.offset(direction2.getOpposite()));
        if (StairsBlock.isBlockStairs((BlockState)object) && blockState.get(HALF) == ((StateHolder)object).get(HALF) && (direction = ((StateHolder)object).get(FACING)).getAxis() != blockState.get(FACING).getAxis() && StairsBlock.isDifferentStairs(blockState, iBlockReader, blockPos, direction)) {
            if (direction == direction2.rotateYCCW()) {
                return StairsShape.INNER_LEFT;
            }
            return StairsShape.INNER_RIGHT;
        }
        return StairsShape.STRAIGHT;
    }

    private static boolean isDifferentStairs(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        BlockState blockState2 = iBlockReader.getBlockState(blockPos.offset(direction));
        return !StairsBlock.isBlockStairs(blockState2) || blockState2.get(FACING) != blockState.get(FACING) || blockState2.get(HALF) != blockState.get(HALF);
    }

    public static boolean isBlockStairs(BlockState blockState) {
        return blockState.getBlock() instanceof StairsBlock;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        Direction direction = blockState.get(FACING);
        StairsShape stairsShape = blockState.get(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT: {
                if (direction.getAxis() != Direction.Axis.Z) break;
                switch (stairsShape) {
                    case INNER_LEFT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
                    }
                    case INNER_RIGHT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
                    }
                    case OUTER_LEFT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
                    }
                    case OUTER_RIGHT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
                    }
                }
                return blockState.rotate(Rotation.CLOCKWISE_180);
            }
            case FRONT_BACK: {
                if (direction.getAxis() != Direction.Axis.X) break;
                switch (stairsShape) {
                    case INNER_LEFT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
                    }
                    case INNER_RIGHT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
                    }
                    case OUTER_LEFT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
                    }
                    case OUTER_RIGHT: {
                        return (BlockState)blockState.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
                    }
                    case STRAIGHT: {
                        return blockState.rotate(Rotation.CLOCKWISE_180);
                    }
                }
            }
        }
        return super.mirror(blockState, mirror);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static VoxelShape[] lambda$makeShapes$1(int n) {
        return new VoxelShape[n];
    }

    private static VoxelShape lambda$makeShapes$0(VoxelShape voxelShape, VoxelShape voxelShape2, VoxelShape voxelShape3, VoxelShape voxelShape4, VoxelShape voxelShape5, int n) {
        return StairsBlock.combineShapes(n, voxelShape, voxelShape2, voxelShape3, voxelShape4, voxelShape5);
    }
}

