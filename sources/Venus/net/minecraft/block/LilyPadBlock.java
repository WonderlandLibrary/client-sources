/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LilyPadBlock
extends BushBlock {
    protected static final VoxelShape LILY_PAD_AABB = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    protected LilyPadBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        super.onEntityCollision(blockState, world, blockPos, entity2);
        if (world instanceof ServerWorld && entity2 instanceof BoatEntity) {
            world.destroyBlock(new BlockPos(blockPos), true, entity2);
        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return LILY_PAD_AABB;
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        FluidState fluidState = iBlockReader.getFluidState(blockPos);
        FluidState fluidState2 = iBlockReader.getFluidState(blockPos.up());
        return (fluidState.getFluid() == Fluids.WATER || blockState.getMaterial() == Material.ICE) && fluidState2.getFluid() == Fluids.EMPTY;
    }
}

