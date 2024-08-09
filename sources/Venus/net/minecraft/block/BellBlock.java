/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BellAttachment;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BellBlock
extends ContainerBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<BellAttachment> ATTACHMENT = BlockStateProperties.BELL_ATTACHMENT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final VoxelShape FLOOR_NORTH_SOUTH_SHAPE = Block.makeCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
    private static final VoxelShape FLOOR_EAST_WEST_SHAPE = Block.makeCuboidShape(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
    private static final VoxelShape BELL_CUP_SHAPE = Block.makeCuboidShape(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
    private static final VoxelShape BELL_RIM_SHAPE = Block.makeCuboidShape(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
    private static final VoxelShape BASE_WALL_SHAPE = VoxelShapes.or(BELL_RIM_SHAPE, BELL_CUP_SHAPE);
    private static final VoxelShape DOUBLE_WALL_NORTH_SOUTH_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
    private static final VoxelShape DOUBLE_WALL_EAST_WEST_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
    private static final VoxelShape WEST_FACING_WALL_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
    private static final VoxelShape EAST_FACING_WALL_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
    private static final VoxelShape NORTH_FACING_WALL_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
    private static final VoxelShape SOUTH_FACING_WALL_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
    private static final VoxelShape CEILING_SHAPE = VoxelShapes.or(BASE_WALL_SHAPE, Block.makeCuboidShape(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));

    public BellBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(ATTACHMENT, BellAttachment.FLOOR)).with(POWERED, false));
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2 = world.isBlockPowered(blockPos);
        if (bl2 != blockState.get(POWERED)) {
            if (bl2) {
                this.ring(world, blockPos, null);
            }
            world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl2), 0);
        }
    }

    @Override
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        Entity entity2 = projectileEntity.func_234616_v_();
        PlayerEntity playerEntity = entity2 instanceof PlayerEntity ? (PlayerEntity)entity2 : null;
        this.attemptRing(world, blockState, blockRayTraceResult, playerEntity, false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        return this.attemptRing(world, blockState, blockRayTraceResult, playerEntity, false) ? ActionResultType.func_233537_a_(world.isRemote) : ActionResultType.PASS;
    }

    public boolean attemptRing(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, @Nullable PlayerEntity playerEntity, boolean bl) {
        boolean bl2;
        Direction direction = blockRayTraceResult.getFace();
        BlockPos blockPos = blockRayTraceResult.getPos();
        boolean bl3 = bl2 = !bl || this.canRingFrom(blockState, direction, blockRayTraceResult.getHitVec().y - (double)blockPos.getY());
        if (bl2) {
            boolean bl4 = this.ring(world, blockPos, direction);
            if (bl4 && playerEntity != null) {
                playerEntity.addStat(Stats.BELL_RING);
            }
            return false;
        }
        return true;
    }

    private boolean canRingFrom(BlockState blockState, Direction direction, double d) {
        if (direction.getAxis() != Direction.Axis.Y && !(d > (double)0.8124f)) {
            Direction direction2 = blockState.get(HORIZONTAL_FACING);
            BellAttachment bellAttachment = blockState.get(ATTACHMENT);
            switch (1.$SwitchMap$net$minecraft$state$properties$BellAttachment[bellAttachment.ordinal()]) {
                case 1: {
                    return direction2.getAxis() == direction.getAxis();
                }
                case 2: 
                case 3: {
                    return direction2.getAxis() != direction.getAxis();
                }
                case 4: {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public boolean ring(World world, BlockPos blockPos, @Nullable Direction direction) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!world.isRemote && tileEntity instanceof BellTileEntity) {
            if (direction == null) {
                direction = world.getBlockState(blockPos).get(HORIZONTAL_FACING);
            }
            ((BellTileEntity)tileEntity).ring(direction);
            world.playSound(null, blockPos, SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 2.0f, 1.0f);
            return false;
        }
        return true;
    }

    private VoxelShape getShape(BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BellAttachment bellAttachment = blockState.get(ATTACHMENT);
        if (bellAttachment == BellAttachment.FLOOR) {
            return direction != Direction.NORTH && direction != Direction.SOUTH ? FLOOR_EAST_WEST_SHAPE : FLOOR_NORTH_SOUTH_SHAPE;
        }
        if (bellAttachment == BellAttachment.CEILING) {
            return CEILING_SHAPE;
        }
        if (bellAttachment == BellAttachment.DOUBLE_WALL) {
            return direction != Direction.NORTH && direction != Direction.SOUTH ? DOUBLE_WALL_EAST_WEST_SHAPE : DOUBLE_WALL_NORTH_SOUTH_SHAPE;
        }
        if (direction == Direction.NORTH) {
            return NORTH_FACING_WALL_SHAPE;
        }
        if (direction == Direction.SOUTH) {
            return SOUTH_FACING_WALL_SHAPE;
        }
        return direction == Direction.EAST ? EAST_FACING_WALL_SHAPE : WEST_FACING_WALL_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.getShape(blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.getShape(blockState);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getFace();
        BlockPos blockPos = blockItemUseContext.getPos();
        World world = blockItemUseContext.getWorld();
        Direction.Axis axis = direction.getAxis();
        if (axis == Direction.Axis.Y) {
            BlockState blockState = (BlockState)((BlockState)this.getDefaultState().with(ATTACHMENT, direction == Direction.DOWN ? BellAttachment.CEILING : BellAttachment.FLOOR)).with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing());
            if (blockState.isValidPosition(blockItemUseContext.getWorld(), blockPos)) {
                return blockState;
            }
        } else {
            boolean bl = axis == Direction.Axis.X && world.getBlockState(blockPos.west()).isSolidSide(world, blockPos.west(), Direction.EAST) && world.getBlockState(blockPos.east()).isSolidSide(world, blockPos.east(), Direction.WEST) || axis == Direction.Axis.Z && world.getBlockState(blockPos.north()).isSolidSide(world, blockPos.north(), Direction.SOUTH) && world.getBlockState(blockPos.south()).isSolidSide(world, blockPos.south(), Direction.NORTH);
            BlockState blockState = (BlockState)((BlockState)this.getDefaultState().with(HORIZONTAL_FACING, direction.getOpposite())).with(ATTACHMENT, bl ? BellAttachment.DOUBLE_WALL : BellAttachment.SINGLE_WALL);
            if (blockState.isValidPosition(blockItemUseContext.getWorld(), blockItemUseContext.getPos())) {
                return blockState;
            }
            boolean bl2 = world.getBlockState(blockPos.down()).isSolidSide(world, blockPos.down(), Direction.UP);
            if ((blockState = (BlockState)blockState.with(ATTACHMENT, bl2 ? BellAttachment.FLOOR : BellAttachment.CEILING)).isValidPosition(blockItemUseContext.getWorld(), blockItemUseContext.getPos())) {
                return blockState;
            }
        }
        return null;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        BellAttachment bellAttachment = blockState.get(ATTACHMENT);
        Direction direction2 = BellBlock.getDirectionFromState(blockState).getOpposite();
        if (direction2 == direction && !blockState.isValidPosition(iWorld, blockPos) && bellAttachment != BellAttachment.DOUBLE_WALL) {
            return Blocks.AIR.getDefaultState();
        }
        if (direction.getAxis() == blockState.get(HORIZONTAL_FACING).getAxis()) {
            if (bellAttachment == BellAttachment.DOUBLE_WALL && !blockState2.isSolidSide(iWorld, blockPos2, direction)) {
                return (BlockState)((BlockState)blockState.with(ATTACHMENT, BellAttachment.SINGLE_WALL)).with(HORIZONTAL_FACING, direction.getOpposite());
            }
            if (bellAttachment == BellAttachment.SINGLE_WALL && direction2.getOpposite() == direction && blockState2.isSolidSide(iWorld, blockPos2, blockState.get(HORIZONTAL_FACING))) {
                return (BlockState)blockState.with(ATTACHMENT, BellAttachment.DOUBLE_WALL);
            }
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        Direction direction = BellBlock.getDirectionFromState(blockState).getOpposite();
        return direction == Direction.UP ? Block.hasEnoughSolidSide(iWorldReader, blockPos.up(), Direction.DOWN) : HorizontalFaceBlock.isSideSolidForDirection(iWorldReader, blockPos, direction);
    }

    private static Direction getDirectionFromState(BlockState blockState) {
        switch (1.$SwitchMap$net$minecraft$state$properties$BellAttachment[blockState.get(ATTACHMENT).ordinal()]) {
            case 1: {
                return Direction.UP;
            }
            case 4: {
                return Direction.DOWN;
            }
        }
        return blockState.get(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ATTACHMENT, POWERED);
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BellTileEntity();
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

