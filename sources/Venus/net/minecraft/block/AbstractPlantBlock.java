/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractPlantBlock
extends Block {
    protected final Direction growthDirection;
    protected final boolean breaksInWater;
    protected final VoxelShape shape;

    protected AbstractPlantBlock(AbstractBlock.Properties properties, Direction direction, VoxelShape voxelShape, boolean bl) {
        super(properties);
        this.growthDirection = direction;
        this.shape = voxelShape;
        this.breaksInWater = bl;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().offset(this.growthDirection));
        return !blockState.isIn(this.getTopPlantBlock()) && !blockState.isIn(this.getBodyPlantBlock()) ? this.grow(blockItemUseContext.getWorld()) : this.getBodyPlantBlock().getDefaultState();
    }

    public BlockState grow(IWorld iWorld) {
        return this.getDefaultState();
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.offset(this.growthDirection.getOpposite());
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        Block block = blockState2.getBlock();
        if (!this.canGrowOn(block)) {
            return true;
        }
        return block == this.getTopPlantBlock() || block == this.getBodyPlantBlock() || blockState2.isSolidSide(iWorldReader, blockPos2, this.growthDirection);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    protected boolean canGrowOn(Block block) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.shape;
    }

    protected abstract AbstractTopPlantBlock getTopPlantBlock();

    protected abstract Block getBodyPlantBlock();
}

