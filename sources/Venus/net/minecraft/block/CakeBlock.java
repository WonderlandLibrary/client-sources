/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CakeBlock
extends Block {
    public static final IntegerProperty BITES = BlockStateProperties.BITES_0_6;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(3.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(5.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(7.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(9.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(11.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.makeCuboidShape(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)};

    protected CakeBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(BITES)];
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            if (this.eatSlice(world, blockPos, blockState, playerEntity).isSuccessOrConsume()) {
                return ActionResultType.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }
        return this.eatSlice(world, blockPos, blockState, playerEntity);
    }

    private ActionResultType eatSlice(IWorld iWorld, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!playerEntity.canEat(true)) {
            return ActionResultType.PASS;
        }
        playerEntity.addStat(Stats.EAT_CAKE_SLICE);
        playerEntity.getFoodStats().addStats(2, 0.1f);
        int n = blockState.get(BITES);
        if (n < 6) {
            iWorld.setBlockState(blockPos, (BlockState)blockState.with(BITES, n + 1), 3);
        } else {
            iWorld.removeBlock(blockPos, false);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.getBlockState(blockPos.down()).getMaterial().isSolid();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return (7 - blockState.get(BITES)) * 2;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

