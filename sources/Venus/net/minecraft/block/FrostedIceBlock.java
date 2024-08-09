/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FrostedIceBlock
extends IceBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;

    public FrostedIceBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.tick(blockState, serverWorld, blockPos, random2);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if ((random2.nextInt(3) == 0 || this.shouldMelt(serverWorld, blockPos, 1)) && serverWorld.getLight(blockPos) > 11 - blockState.get(AGE) - blockState.getOpacity(serverWorld, blockPos) && this.slightlyMelt(blockState, serverWorld, blockPos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutable.setAndMove(blockPos, direction);
                BlockState blockState2 = serverWorld.getBlockState(mutable);
                if (!blockState2.isIn(this) || this.slightlyMelt(blockState2, serverWorld, mutable)) continue;
                serverWorld.getPendingBlockTicks().scheduleTick(mutable, this, MathHelper.nextInt(random2, 20, 40));
            }
        } else {
            serverWorld.getPendingBlockTicks().scheduleTick(blockPos, this, MathHelper.nextInt(random2, 20, 40));
        }
    }

    private boolean slightlyMelt(BlockState blockState, World world, BlockPos blockPos) {
        int n = blockState.get(AGE);
        if (n < 3) {
            world.setBlockState(blockPos, (BlockState)blockState.with(AGE, n + 1), 1);
            return true;
        }
        this.turnIntoWater(blockState, world, blockPos);
        return false;
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (block == this && this.shouldMelt(world, blockPos, 1)) {
            this.turnIntoWater(blockState, world, blockPos);
        }
        super.neighborChanged(blockState, world, blockPos, block, blockPos2, bl);
    }

    private boolean shouldMelt(IBlockReader iBlockReader, BlockPos blockPos, int n) {
        int n2 = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.setAndMove(blockPos, direction);
            if (!iBlockReader.getBlockState(mutable).isIn(this) || ++n2 < n) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }
}

