/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EndPortalBlock
extends ContainerBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    protected EndPortalBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new EndPortalTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (world instanceof ServerWorld && !entity2.isPassenger() && !entity2.isBeingRidden() && entity2.isNonBoss() && VoxelShapes.compare(VoxelShapes.create(entity2.getBoundingBox().offset(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())), blockState.getShape(world, blockPos), IBooleanFunction.AND)) {
            RegistryKey<World> registryKey = world.getDimensionKey() == World.THE_END ? World.OVERWORLD : World.THE_END;
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) {
                return;
            }
            entity2.changeDimension(serverWorld);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        double d = (double)blockPos.getX() + random2.nextDouble();
        double d2 = (double)blockPos.getY() + 0.8;
        double d3 = (double)blockPos.getZ() + random2.nextDouble();
        world.addParticle(ParticleTypes.SMOKE, d, d2, d3, 0.0, 0.0, 0.0);
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isReplaceable(BlockState blockState, Fluid fluid) {
        return true;
    }
}

