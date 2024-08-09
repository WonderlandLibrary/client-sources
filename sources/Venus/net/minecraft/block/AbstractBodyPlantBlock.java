/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractBodyPlantBlock
extends AbstractPlantBlock
implements IGrowable {
    protected AbstractBodyPlantBlock(AbstractBlock.Properties properties, Direction direction, VoxelShape voxelShape, boolean bl) {
        super(properties, direction, voxelShape, bl);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        Block block;
        if (direction == this.growthDirection.getOpposite() && !blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        AbstractTopPlantBlock abstractTopPlantBlock = this.getTopPlantBlock();
        if (direction == this.growthDirection && (block = blockState2.getBlock()) != this && block != abstractTopPlantBlock) {
            return abstractTopPlantBlock.grow(iWorld);
        }
        if (this.breaksInWater) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.getTopPlantBlock());
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        Optional<BlockPos> optional = this.nextGrowPosition(iBlockReader, blockPos, blockState);
        return optional.isPresent() && this.getTopPlantBlock().canGrowIn(iBlockReader.getBlockState(optional.get().offset(this.growthDirection)));
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        Optional<BlockPos> optional = this.nextGrowPosition(serverWorld, blockPos, blockState);
        if (optional.isPresent()) {
            BlockState blockState2 = serverWorld.getBlockState(optional.get());
            ((AbstractTopPlantBlock)blockState2.getBlock()).grow(serverWorld, random2, optional.get(), blockState2);
        }
    }

    private Optional<BlockPos> nextGrowPosition(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        Block block;
        BlockPos blockPos2 = blockPos;
        while ((block = iBlockReader.getBlockState(blockPos2 = blockPos2.offset(this.growthDirection)).getBlock()) == blockState.getBlock()) {
        }
        return block == this.getTopPlantBlock() ? Optional.of(blockPos2) : Optional.empty();
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        boolean bl = super.isReplaceable(blockState, blockItemUseContext);
        return bl && blockItemUseContext.getItem().getItem() == this.getTopPlantBlock().asItem() ? false : bl;
    }

    @Override
    protected Block getBodyPlantBlock() {
        return this;
    }
}

