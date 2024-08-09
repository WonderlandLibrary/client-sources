/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class NetherWartBlock
extends BushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 11.0, 16.0), Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)};

    protected NetherWartBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES[blockState.get(AGE)];
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(Blocks.SOUL_SAND);
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n = blockState.get(AGE);
        if (n < 3 && random2.nextInt(10) == 0) {
            blockState = (BlockState)blockState.with(AGE, n + 1);
            serverWorld.setBlockState(blockPos, blockState, 1);
        }
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(Items.NETHER_WART);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

