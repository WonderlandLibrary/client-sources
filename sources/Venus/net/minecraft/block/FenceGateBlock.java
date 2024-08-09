/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FenceGateBlock
extends HorizontalBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty IN_WALL = BlockStateProperties.IN_WALL;
    protected static final VoxelShape AABB_HITBOX_ZAXIS = Block.makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape AABB_HITBOX_XAXIS = Block.makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    protected static final VoxelShape AABB_HITBOX_ZAXIS_INWALL = Block.makeCuboidShape(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
    protected static final VoxelShape AABB_HITBOX_XAXIS_INWALL = Block.makeCuboidShape(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
    protected static final VoxelShape TRUE_AABB_COLLISION_BOX_ZAXIS = Block.makeCuboidShape(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
    protected static final VoxelShape AABB_COLLISION_BOX_XAXIS = Block.makeCuboidShape(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
    protected static final VoxelShape AABB_RENDER_BOX_ZAXIS = VoxelShapes.or(Block.makeCuboidShape(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.makeCuboidShape(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
    protected static final VoxelShape AABB_COLLISION_BOX_ZAXIS = VoxelShapes.or(Block.makeCuboidShape(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.makeCuboidShape(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
    protected static final VoxelShape AABB_RENDER_BOX_ZAXIS_INWALL = VoxelShapes.or(Block.makeCuboidShape(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.makeCuboidShape(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
    protected static final VoxelShape AABB_RENDER_BOX_XAXIS_INWALL = VoxelShapes.or(Block.makeCuboidShape(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.makeCuboidShape(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));

    public FenceGateBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(OPEN, false)).with(POWERED, false)).with(IN_WALL, false));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (blockState.get(IN_WALL).booleanValue()) {
            return blockState.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? AABB_HITBOX_XAXIS_INWALL : AABB_HITBOX_ZAXIS_INWALL;
        }
        return blockState.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? AABB_HITBOX_XAXIS : AABB_HITBOX_ZAXIS;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        Direction.Axis axis = direction.getAxis();
        if (blockState.get(HORIZONTAL_FACING).rotateY().getAxis() != axis) {
            return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        boolean bl = this.isWall(blockState2) || this.isWall(iWorld.getBlockState(blockPos.offset(direction.getOpposite())));
        return (BlockState)blockState.with(IN_WALL, bl);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (blockState.get(OPEN).booleanValue()) {
            return VoxelShapes.empty();
        }
        return blockState.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? TRUE_AABB_COLLISION_BOX_ZAXIS : AABB_COLLISION_BOX_XAXIS;
    }

    @Override
    public VoxelShape getRenderShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        if (blockState.get(IN_WALL).booleanValue()) {
            return blockState.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? AABB_RENDER_BOX_XAXIS_INWALL : AABB_RENDER_BOX_ZAXIS_INWALL;
        }
        return blockState.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? AABB_COLLISION_BOX_ZAXIS : AABB_RENDER_BOX_ZAXIS;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (1.$SwitchMap$net$minecraft$pathfinding$PathType[pathType.ordinal()]) {
            case 1: {
                return blockState.get(OPEN);
            }
            case 2: {
                return true;
            }
            case 3: {
                return blockState.get(OPEN);
            }
        }
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        boolean bl = world.isBlockPowered(blockPos);
        Direction direction = blockItemUseContext.getPlacementHorizontalFacing();
        Direction.Axis axis = direction.getAxis();
        boolean bl2 = axis == Direction.Axis.Z && (this.isWall(world.getBlockState(blockPos.west())) || this.isWall(world.getBlockState(blockPos.east()))) || axis == Direction.Axis.X && (this.isWall(world.getBlockState(blockPos.north())) || this.isWall(world.getBlockState(blockPos.south())));
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(HORIZONTAL_FACING, direction)).with(OPEN, bl)).with(POWERED, bl)).with(IN_WALL, bl2);
    }

    private boolean isWall(BlockState blockState) {
        return blockState.getBlock().isIn(BlockTags.WALLS);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (blockState.get(OPEN).booleanValue()) {
            blockState = (BlockState)blockState.with(OPEN, false);
            world.setBlockState(blockPos, blockState, 1);
        } else {
            Direction direction = playerEntity.getHorizontalFacing();
            if (blockState.get(HORIZONTAL_FACING) == direction.getOpposite()) {
                blockState = (BlockState)blockState.with(HORIZONTAL_FACING, direction);
            }
            blockState = (BlockState)blockState.with(OPEN, true);
            world.setBlockState(blockPos, blockState, 1);
        }
        world.playEvent(playerEntity, blockState.get(OPEN) != false ? 1008 : 1014, blockPos, 0);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!world.isRemote) {
            boolean bl2 = world.isBlockPowered(blockPos);
            if (blockState.get(POWERED) != bl2) {
                world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(POWERED, bl2)).with(OPEN, bl2), 1);
                if (blockState.get(OPEN) != bl2) {
                    world.playEvent(null, bl2 ? 1008 : 1014, blockPos, 0);
                }
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN, POWERED, IN_WALL);
    }

    public static boolean isParallel(BlockState blockState, Direction direction) {
        return blockState.get(HORIZONTAL_FACING).getAxis() == direction.rotateY().getAxis();
    }
}

