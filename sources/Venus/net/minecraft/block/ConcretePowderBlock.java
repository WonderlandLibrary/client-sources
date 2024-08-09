/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ConcretePowderBlock
extends FallingBlock {
    private final BlockState solidifiedState;

    public ConcretePowderBlock(Block block, AbstractBlock.Properties properties) {
        super(properties);
        this.solidifiedState = block.getDefaultState();
    }

    @Override
    public void onEndFalling(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, FallingBlockEntity fallingBlockEntity) {
        if (ConcretePowderBlock.shouldSolidify(world, blockPos, blockState2)) {
            world.setBlockState(blockPos, this.solidifiedState, 0);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState;
        BlockPos blockPos;
        World world = blockItemUseContext.getWorld();
        return ConcretePowderBlock.shouldSolidify(world, blockPos = blockItemUseContext.getPos(), blockState = world.getBlockState(blockPos)) ? this.solidifiedState : super.getStateForPlacement(blockItemUseContext);
    }

    private static boolean shouldSolidify(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ConcretePowderBlock.causesSolidify(blockState) || ConcretePowderBlock.isTouchingLiquid(iBlockReader, blockPos);
    }

    private static boolean isTouchingLiquid(IBlockReader iBlockReader, BlockPos blockPos) {
        boolean bl = false;
        BlockPos.Mutable mutable = blockPos.toMutable();
        for (Direction direction : Direction.values()) {
            BlockState blockState = iBlockReader.getBlockState(mutable);
            if (direction == Direction.DOWN && !ConcretePowderBlock.causesSolidify(blockState)) continue;
            mutable.setAndMove(blockPos, direction);
            blockState = iBlockReader.getBlockState(mutable);
            if (!ConcretePowderBlock.causesSolidify(blockState) || blockState.isSolidSide(iBlockReader, blockPos, direction.getOpposite())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    private static boolean causesSolidify(BlockState blockState) {
        return blockState.getFluidState().isTagged(FluidTags.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return ConcretePowderBlock.isTouchingLiquid(iWorld, blockPos) ? this.solidifiedState : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public int getDustColor(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.getMaterialColor((IBlockReader)iBlockReader, (BlockPos)blockPos).colorValue;
    }
}

