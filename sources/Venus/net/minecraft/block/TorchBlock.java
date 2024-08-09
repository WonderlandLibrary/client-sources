/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class TorchBlock
extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
    protected final IParticleData particleData;

    protected TorchBlock(AbstractBlock.Properties properties, IParticleData iParticleData) {
        super(properties);
        this.particleData = iParticleData;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !this.isValidPosition(blockState, iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return TorchBlock.hasEnoughSolidSide(iWorldReader, blockPos.down(), Direction.UP);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getY() + 0.7;
        double d3 = (double)blockPos.getZ() + 0.5;
        world.addParticle(ParticleTypes.SMOKE, d, d2, d3, 0.0, 0.0, 0.0);
        world.addParticle(this.particleData, d, d2, d3, 0.0, 0.0, 0.0);
    }
}

