/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
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

public class WallTorchBlock
extends TorchBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0), Direction.SOUTH, Block.makeCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0), Direction.WEST, Block.makeCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5), Direction.EAST, Block.makeCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)));

    protected WallTorchBlock(AbstractBlock.Properties properties, IParticleData iParticleData) {
        super(properties, iParticleData);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return WallTorchBlock.getShapeForState(blockState);
    }

    public static VoxelShape getShapeForState(BlockState blockState) {
        return SHAPES.get(blockState.get(HORIZONTAL_FACING));
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite());
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        return blockState2.isSolidSide(iWorldReader, blockPos2, direction);
    }

    @Override
    @Nullable
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
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction.getOpposite() == blockState.get(HORIZONTAL_FACING) && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : blockState;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getY() + 0.7;
        double d3 = (double)blockPos.getZ() + 0.5;
        double d4 = 0.22;
        double d5 = 0.27;
        Direction direction2 = direction.getOpposite();
        world.addParticle(ParticleTypes.SMOKE, d + 0.27 * (double)direction2.getXOffset(), d2 + 0.22, d3 + 0.27 * (double)direction2.getZOffset(), 0.0, 0.0, 0.0);
        world.addParticle(this.particleData, d + 0.27 * (double)direction2.getXOffset(), d2 + 0.22, d3 + 0.27 * (double)direction2.getZOffset(), 0.0, 0.0, 0.0);
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

