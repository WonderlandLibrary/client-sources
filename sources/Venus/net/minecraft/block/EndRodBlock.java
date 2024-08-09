/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EndRodBlock
extends DirectionalBlock {
    protected static final VoxelShape END_ROD_VERTICAL_AABB = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape END_ROD_NS_AABB = Block.makeCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape END_ROD_EW_AABB = Block.makeCuboidShape(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

    protected EndRodBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.UP));
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return (BlockState)blockState.with(FACING, mirror.mirror(blockState.get(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (1.$SwitchMap$net$minecraft$util$Direction$Axis[blockState.get(FACING).getAxis().ordinal()]) {
            default: {
                return END_ROD_EW_AABB;
            }
            case 2: {
                return END_ROD_NS_AABB;
            }
            case 3: 
        }
        return END_ROD_VERTICAL_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getFace();
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().offset(direction.getOpposite()));
        return blockState.isIn(this) && blockState.get(FACING) == direction ? (BlockState)this.getDefaultState().with(FACING, direction.getOpposite()) : (BlockState)this.getDefaultState().with(FACING, direction);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        Direction direction = blockState.get(FACING);
        double d = (double)blockPos.getX() + 0.55 - (double)(random2.nextFloat() * 0.1f);
        double d2 = (double)blockPos.getY() + 0.55 - (double)(random2.nextFloat() * 0.1f);
        double d3 = (double)blockPos.getZ() + 0.55 - (double)(random2.nextFloat() * 0.1f);
        double d4 = 0.4f - (random2.nextFloat() + random2.nextFloat()) * 0.4f;
        if (random2.nextInt(5) == 0) {
            world.addParticle(ParticleTypes.END_ROD, d + (double)direction.getXOffset() * d4, d2 + (double)direction.getYOffset() * d4, d3 + (double)direction.getZOffset() * d4, random2.nextGaussian() * 0.005, random2.nextGaussian() * 0.005, random2.nextGaussian() * 0.005);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.NORMAL;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

