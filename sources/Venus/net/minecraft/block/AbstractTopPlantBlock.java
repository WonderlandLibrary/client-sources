/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractTopPlantBlock
extends AbstractPlantBlock
implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_25;
    private final double growthChance;

    protected AbstractTopPlantBlock(AbstractBlock.Properties properties, Direction direction, VoxelShape voxelShape, boolean bl, double d) {
        super(properties, direction, voxelShape, bl);
        this.growthChance = d;
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public BlockState grow(IWorld iWorld) {
        return (BlockState)this.getDefaultState().with(AGE, iWorld.getRandom().nextInt(25));
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(AGE) < 25;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        BlockPos blockPos2;
        if (blockState.get(AGE) < 25 && random2.nextDouble() < this.growthChance && this.canGrowIn(serverWorld.getBlockState(blockPos2 = blockPos.offset(this.growthDirection)))) {
            serverWorld.setBlockState(blockPos2, (BlockState)blockState.func_235896_a_(AGE));
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == this.growthDirection.getOpposite() && !blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        if (direction != this.growthDirection || !blockState2.isIn(this) && !blockState2.isIn(this.getBodyPlantBlock())) {
            if (this.breaksInWater) {
                iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
            }
            return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        return this.getBodyPlantBlock().getDefaultState();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return this.canGrowIn(iBlockReader.getBlockState(blockPos.offset(this.growthDirection)));
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos.offset(this.growthDirection);
        int n = Math.min(blockState.get(AGE) + 1, 25);
        int n2 = this.getGrowthAmount(random2);
        for (int i = 0; i < n2 && this.canGrowIn(serverWorld.getBlockState(blockPos2)); ++i) {
            serverWorld.setBlockState(blockPos2, (BlockState)blockState.with(AGE, n));
            blockPos2 = blockPos2.offset(this.growthDirection);
            n = Math.min(n + 1, 25);
        }
    }

    protected abstract int getGrowthAmount(Random var1);

    protected abstract boolean canGrowIn(BlockState var1);

    @Override
    protected AbstractTopPlantBlock getTopPlantBlock() {
        return this;
    }
}

