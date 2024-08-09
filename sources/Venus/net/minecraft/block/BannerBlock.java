/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BannerBlock
extends AbstractBannerBlock {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
    private static final Map<DyeColor, Block> BANNERS_BY_COLOR = Maps.newHashMap();
    private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    public BannerBlock(DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(dyeColor, properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(ROTATION, 0));
        BANNERS_BY_COLOR.put(dyeColor, this);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.getBlockState(blockPos.down()).getMaterial().isSolid();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(ROTATION, MathHelper.floor((double)((180.0f + blockItemUseContext.getPlacementYaw()) * 16.0f / 360.0f) + 0.5) & 0xF);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(ROTATION, rotation.rotate(blockState.get(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return (BlockState)blockState.with(ROTATION, mirror.mirrorRotation(blockState.get(ROTATION), 16));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    public static Block forColor(DyeColor dyeColor) {
        return BANNERS_BY_COLOR.getOrDefault(dyeColor, Blocks.WHITE_BANNER);
    }
}

