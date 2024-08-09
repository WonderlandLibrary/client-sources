/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class WallBannerBlock
extends AbstractBannerBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final Map<Direction, VoxelShape> BANNER_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(0.0, 0.0, 14.0, 16.0, 12.5, 16.0), Direction.SOUTH, Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.5, 2.0), Direction.WEST, Block.makeCuboidShape(14.0, 0.0, 0.0, 16.0, 12.5, 16.0), Direction.EAST, Block.makeCuboidShape(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)));

    public WallBannerBlock(DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(dyeColor, properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.getBlockState(blockPos.offset(blockState.get(HORIZONTAL_FACING).getOpposite())).getMaterial().isSolid();
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == blockState.get(HORIZONTAL_FACING).getOpposite() && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return BANNER_SHAPES.get(blockState.get(HORIZONTAL_FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction[] directionArray;
        BlockState blockState = this.getDefaultState();
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        for (Direction direction : directionArray = blockItemUseContext.getNearestLookingDirections()) {
            Direction direction2;
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(HORIZONTAL_FACING, direction2 = direction.getOpposite())).isValidPosition(world, blockPos)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(HORIZONTAL_FACING, rotation.rotate(blockState.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(HORIZONTAL_FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }
}

