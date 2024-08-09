/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class HorizontalFaceBlock
extends HorizontalBlock {
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.FACE;

    protected HorizontalFaceBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return HorizontalFaceBlock.isSideSolidForDirection(iWorldReader, blockPos, HorizontalFaceBlock.getFacing(blockState).getOpposite());
    }

    public static boolean isSideSolidForDirection(IWorldReader iWorldReader, BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.offset(direction);
        return iWorldReader.getBlockState(blockPos2).isSolidSide(iWorldReader, blockPos2, direction.getOpposite());
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        for (Direction direction : blockItemUseContext.getNearestLookingDirections()) {
            BlockState blockState = direction.getAxis() == Direction.Axis.Y ? (BlockState)((BlockState)this.getDefaultState().with(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)).with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing()) : (BlockState)((BlockState)this.getDefaultState().with(FACE, AttachFace.WALL)).with(HORIZONTAL_FACING, direction.getOpposite());
            if (!blockState.isValidPosition(blockItemUseContext.getWorld(), blockItemUseContext.getPos())) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return HorizontalFaceBlock.getFacing(blockState).getOpposite() == direction && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    protected static Direction getFacing(BlockState blockState) {
        switch (1.$SwitchMap$net$minecraft$state$properties$AttachFace[blockState.get(FACE).ordinal()]) {
            case 1: {
                return Direction.DOWN;
            }
            case 2: {
                return Direction.UP;
            }
        }
        return blockState.get(HORIZONTAL_FACING);
    }
}

