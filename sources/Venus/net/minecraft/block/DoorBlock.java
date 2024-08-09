/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoorBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    protected DoorBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(OPEN, false)).with(HINGE, DoorHingeSide.LEFT)).with(POWERED, false)).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Direction direction = blockState.get(FACING);
        boolean bl = blockState.get(OPEN) == false;
        boolean bl2 = blockState.get(HINGE) == DoorHingeSide.RIGHT;
        switch (direction) {
            default: {
                return bl ? EAST_AABB : (bl2 ? NORTH_AABB : SOUTH_AABB);
            }
            case SOUTH: {
                return bl ? SOUTH_AABB : (bl2 ? EAST_AABB : WEST_AABB);
            }
            case WEST: {
                return bl ? WEST_AABB : (bl2 ? SOUTH_AABB : NORTH_AABB);
            }
            case NORTH: 
        }
        return bl ? NORTH_AABB : (bl2 ? WEST_AABB : EAST_AABB);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return blockState2.isIn(this) && blockState2.get(HALF) != doubleBlockHalf ? (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(FACING, blockState2.get(FACING))).with(OPEN, blockState2.get(OPEN))).with(HINGE, blockState2.get(HINGE))).with(POWERED, blockState2.get(POWERED)) : Blocks.AIR.getDefaultState();
        }
        return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote && playerEntity.isCreative()) {
            DoublePlantBlock.removeBottomHalf(world, blockPos, blockState, playerEntity);
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (pathType) {
            case LAND: {
                return blockState.get(OPEN);
            }
            case WATER: {
                return true;
            }
            case AIR: {
                return blockState.get(OPEN);
            }
        }
        return true;
    }

    private int getCloseSound() {
        return this.material == Material.IRON ? 1011 : 1012;
    }

    private int getOpenSound() {
        return this.material == Material.IRON ? 1005 : 1006;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockPos blockPos = blockItemUseContext.getPos();
        if (blockPos.getY() < 255 && blockItemUseContext.getWorld().getBlockState(blockPos.up()).isReplaceable(blockItemUseContext)) {
            World world = blockItemUseContext.getWorld();
            boolean bl = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.up());
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing())).with(HINGE, this.getHingeSide(blockItemUseContext))).with(POWERED, bl)).with(OPEN, bl)).with(HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        world.setBlockState(blockPos.up(), (BlockState)blockState.with(HALF, DoubleBlockHalf.UPPER), 0);
    }

    private DoorHingeSide getHingeSide(BlockItemUseContext blockItemUseContext) {
        boolean bl;
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        Direction direction = blockItemUseContext.getPlacementHorizontalFacing();
        BlockPos blockPos2 = blockPos.up();
        Direction direction2 = direction.rotateYCCW();
        BlockPos blockPos3 = blockPos.offset(direction2);
        BlockState blockState = world.getBlockState(blockPos3);
        BlockPos blockPos4 = blockPos2.offset(direction2);
        BlockState blockState2 = world.getBlockState(blockPos4);
        Direction direction3 = direction.rotateY();
        BlockPos blockPos5 = blockPos.offset(direction3);
        BlockState blockState3 = world.getBlockState(blockPos5);
        BlockPos blockPos6 = blockPos2.offset(direction3);
        BlockState blockState4 = world.getBlockState(blockPos6);
        int n = (blockState.hasOpaqueCollisionShape(world, blockPos3) ? -1 : 0) + (blockState2.hasOpaqueCollisionShape(world, blockPos4) ? -1 : 0) + (blockState3.hasOpaqueCollisionShape(world, blockPos5) ? 1 : 0) + (blockState4.hasOpaqueCollisionShape(world, blockPos6) ? 1 : 0);
        boolean bl2 = blockState.isIn(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        boolean bl3 = bl = blockState3.isIn(this) && blockState3.get(HALF) == DoubleBlockHalf.LOWER;
        if ((!bl2 || bl) && n <= 0) {
            if ((!bl || bl2) && n >= 0) {
                int n2 = direction.getXOffset();
                int n3 = direction.getZOffset();
                Vector3d vector3d = blockItemUseContext.getHitVec();
                double d = vector3d.x - (double)blockPos.getX();
                double d2 = vector3d.z - (double)blockPos.getZ();
                return !(n2 < 0 && d2 < 0.5 || n2 > 0 && d2 > 0.5 || n3 < 0 && d > 0.5 || n3 > 0 && d < 0.5) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            }
            return DoorHingeSide.LEFT;
        }
        return DoorHingeSide.RIGHT;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (this.material == Material.IRON) {
            return ActionResultType.PASS;
        }
        blockState = (BlockState)blockState.func_235896_a_(OPEN);
        world.setBlockState(blockPos, blockState, 1);
        world.playEvent(playerEntity, blockState.get(OPEN) != false ? this.getOpenSound() : this.getCloseSound(), blockPos, 0);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    public boolean isOpen(BlockState blockState) {
        return blockState.get(OPEN);
    }

    public void openDoor(World world, BlockState blockState, BlockPos blockPos, boolean bl) {
        if (blockState.isIn(this) && blockState.get(OPEN) != bl) {
            world.setBlockState(blockPos, (BlockState)blockState.with(OPEN, bl), 1);
            this.playSound(world, blockPos, bl);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2;
        boolean bl3 = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.offset(blockState.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN)) ? true : (bl2 = false);
        if (block != this && bl2 != blockState.get(POWERED)) {
            if (bl2 != blockState.get(OPEN)) {
                this.playSound(world, blockPos, bl2);
            }
            world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(POWERED, bl2)).with(OPEN, bl2), 1);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        return blockState.get(HALF) == DoubleBlockHalf.LOWER ? blockState2.isSolidSide(iWorldReader, blockPos2, Direction.UP) : blockState2.isIn(this);
    }

    private void playSound(World world, BlockPos blockPos, boolean bl) {
        world.playEvent(null, bl ? this.getOpenSound() : this.getCloseSound(), blockPos, 0);
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return mirror == Mirror.NONE ? blockState : (BlockState)blockState.rotate(mirror.toRotation(blockState.get(FACING))).func_235896_a_(HINGE);
    }

    @Override
    public long getPositionRandom(BlockState blockState, BlockPos blockPos) {
        return MathHelper.getCoordinateRandom(blockPos.getX(), blockPos.down(blockState.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, HINGE, POWERED);
    }

    public static boolean isWooden(World world, BlockPos blockPos) {
        return DoorBlock.isWooden(world.getBlockState(blockPos));
    }

    public static boolean isWooden(BlockState blockState) {
        return blockState.getBlock() instanceof DoorBlock && (blockState.getMaterial() == Material.WOOD || blockState.getMaterial() == Material.NETHER_WOOD);
    }
}

