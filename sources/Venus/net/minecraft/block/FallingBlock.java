/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FallingBlock
extends Block {
    public FallingBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        world.getPendingBlockTicks().scheduleTick(blockPos, this, this.getFallDelay());
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, this.getFallDelay());
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (FallingBlock.canFallThrough(serverWorld.getBlockState(blockPos.down())) && blockPos.getY() >= 0) {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(serverWorld, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, serverWorld.getBlockState(blockPos));
            this.onStartFalling(fallingBlockEntity);
            serverWorld.addEntity(fallingBlockEntity);
        }
    }

    protected void onStartFalling(FallingBlockEntity fallingBlockEntity) {
    }

    protected int getFallDelay() {
        return 1;
    }

    public static boolean canFallThrough(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.isAir() || blockState.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void onEndFalling(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, FallingBlockEntity fallingBlockEntity) {
    }

    public void onBroken(World world, BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        BlockPos blockPos2;
        if (random2.nextInt(16) == 0 && FallingBlock.canFallThrough(world.getBlockState(blockPos2 = blockPos.down()))) {
            double d = (double)blockPos.getX() + random2.nextDouble();
            double d2 = (double)blockPos.getY() - 0.05;
            double d3 = (double)blockPos.getZ() + random2.nextDouble();
            world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, blockState), d, d2, d3, 0.0, 0.0, 0.0);
        }
    }

    public int getDustColor(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return 1;
    }
}

