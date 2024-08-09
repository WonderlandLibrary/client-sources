/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DragonEggBlock
extends FallingBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public DragonEggBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        this.teleport(blockState, world, blockPos);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public void onBlockClicked(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.teleport(blockState, world, blockPos);
    }

    private void teleport(BlockState blockState, World world, BlockPos blockPos) {
        for (int i = 0; i < 1000; ++i) {
            BlockPos blockPos2 = blockPos.add(world.rand.nextInt(16) - world.rand.nextInt(16), world.rand.nextInt(8) - world.rand.nextInt(8), world.rand.nextInt(16) - world.rand.nextInt(16));
            if (!world.getBlockState(blockPos2).isAir()) continue;
            if (world.isRemote) {
                for (int j = 0; j < 128; ++j) {
                    double d = world.rand.nextDouble();
                    float f = (world.rand.nextFloat() - 0.5f) * 0.2f;
                    float f2 = (world.rand.nextFloat() - 0.5f) * 0.2f;
                    float f3 = (world.rand.nextFloat() - 0.5f) * 0.2f;
                    double d2 = MathHelper.lerp(d, (double)blockPos2.getX(), (double)blockPos.getX()) + (world.rand.nextDouble() - 0.5) + 0.5;
                    double d3 = MathHelper.lerp(d, (double)blockPos2.getY(), (double)blockPos.getY()) + world.rand.nextDouble() - 0.5;
                    double d4 = MathHelper.lerp(d, (double)blockPos2.getZ(), (double)blockPos.getZ()) + (world.rand.nextDouble() - 0.5) + 0.5;
                    world.addParticle(ParticleTypes.PORTAL, d2, d3, d4, f, f2, f3);
                }
            } else {
                world.setBlockState(blockPos2, blockState, 1);
                world.removeBlock(blockPos, true);
            }
            return;
        }
    }

    @Override
    protected int getFallDelay() {
        return 0;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

