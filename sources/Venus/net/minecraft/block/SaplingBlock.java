/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SaplingBlock
extends BushBlock
implements IGrowable {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private final Tree tree;

    protected SaplingBlock(Tree tree, AbstractBlock.Properties properties) {
        super(properties);
        this.tree = tree;
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(STAGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.getLight(blockPos.up()) >= 9 && random2.nextInt(7) == 0) {
            this.placeTree(serverWorld, blockPos, blockState, random2);
        }
    }

    public void placeTree(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState, Random random2) {
        if (blockState.get(STAGE) == 0) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(STAGE), 1);
        } else {
            this.tree.attemptGrowTree(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), blockPos, blockState, random2);
        }
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return (double)world.rand.nextFloat() < 0.45;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        this.placeTree(serverWorld, blockPos, blockState, random2);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}

