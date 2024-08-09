/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
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
import net.minecraft.world.LightType;
import net.minecraft.world.server.ServerWorld;

public class SnowBlock
extends Block {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS_1_8;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{VoxelShapes.empty(), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    protected SnowBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LAYERS, 1));
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (1.$SwitchMap$net$minecraft$pathfinding$PathType[pathType.ordinal()]) {
            case 1: {
                return blockState.get(LAYERS) < 5;
            }
            case 2: {
                return true;
            }
            case 3: {
                return true;
            }
        }
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(LAYERS) - 1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return SHAPES[blockState.get(LAYERS)];
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(LAYERS)];
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        if (!(blockState2.isIn(Blocks.ICE) || blockState2.isIn(Blocks.PACKED_ICE) || blockState2.isIn(Blocks.BARRIER))) {
            if (!blockState2.isIn(Blocks.HONEY_BLOCK) && !blockState2.isIn(Blocks.SOUL_SAND)) {
                return Block.doesSideFillSquare(blockState2.getCollisionShape(iWorldReader, blockPos.down()), Direction.UP) || blockState2.getBlock() == this && blockState2.get(LAYERS) == 8;
            }
            return false;
        }
        return true;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.getLightFor(LightType.BLOCK, blockPos) > 11) {
            SnowBlock.spawnDrops(blockState, serverWorld, blockPos);
            serverWorld.removeBlock(blockPos, true);
        }
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        int n = blockState.get(LAYERS);
        if (blockItemUseContext.getItem().getItem() == this.asItem() && n < 8) {
            if (blockItemUseContext.replacingClickedOnBlock()) {
                return blockItemUseContext.getFace() == Direction.UP;
            }
            return false;
        }
        return n == 1;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos());
        if (blockState.isIn(this)) {
            int n = blockState.get(LAYERS);
            return (BlockState)blockState.with(LAYERS, Math.min(8, n + 1));
        }
        return super.getStateForPlacement(blockItemUseContext);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }
}

