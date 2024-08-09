/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AnvilBlock
extends FallingBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape PART_LOWER_X = Block.makeCuboidShape(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
    private static final VoxelShape PART_MID_X = Block.makeCuboidShape(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
    private static final VoxelShape PART_UPPER_X = Block.makeCuboidShape(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape PART_LOWER_Z = Block.makeCuboidShape(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
    private static final VoxelShape PART_MID_Z = Block.makeCuboidShape(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
    private static final VoxelShape PART_UPPER_Z = Block.makeCuboidShape(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
    private static final VoxelShape X_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_X, PART_MID_X, PART_UPPER_X);
    private static final VoxelShape Z_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_Z, PART_MID_Z, PART_UPPER_Z);
    private static final ITextComponent containerName = new TranslationTextComponent("container.repair");

    public AnvilBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing().rotateY());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        playerEntity.openContainer(blockState.getContainer(world, blockPos));
        playerEntity.addStat(Stats.INTERACT_WITH_ANVIL);
        return ActionResultType.CONSUME;
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        return new SimpleNamedContainerProvider((arg_0, arg_1, arg_2) -> AnvilBlock.lambda$getContainer$0(world, blockPos, arg_0, arg_1, arg_2), containerName);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Direction direction = blockState.get(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    protected void onStartFalling(FallingBlockEntity fallingBlockEntity) {
        fallingBlockEntity.setHurtEntities(false);
    }

    @Override
    public void onEndFalling(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.playEvent(1031, blockPos, 0);
        }
    }

    @Override
    public void onBroken(World world, BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.playEvent(1029, blockPos, 0);
        }
    }

    @Nullable
    public static BlockState damage(BlockState blockState) {
        if (blockState.isIn(Blocks.ANVIL)) {
            return (BlockState)Blocks.CHIPPED_ANVIL.getDefaultState().with(FACING, blockState.get(FACING));
        }
        return blockState.isIn(Blocks.CHIPPED_ANVIL) ? (BlockState)Blocks.DAMAGED_ANVIL.getDefaultState().with(FACING, blockState.get(FACING)) : null;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    @Override
    public int getDustColor(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.getMaterialColor((IBlockReader)iBlockReader, (BlockPos)blockPos).colorValue;
    }

    private static Container lambda$getContainer$0(World world, BlockPos blockPos, int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RepairContainer(n, playerInventory, IWorldPosCallable.of(world, blockPos));
    }
}

