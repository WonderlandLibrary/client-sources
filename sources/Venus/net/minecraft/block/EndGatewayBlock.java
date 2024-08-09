/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EndGatewayBlock
extends ContainerBlock {
    protected EndGatewayBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new EndGatewayTileEntity();
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof EndGatewayTileEntity) {
            int n = ((EndGatewayTileEntity)tileEntity).getParticleAmount();
            for (int i = 0; i < n; ++i) {
                double d = (double)blockPos.getX() + random2.nextDouble();
                double d2 = (double)blockPos.getY() + random2.nextDouble();
                double d3 = (double)blockPos.getZ() + random2.nextDouble();
                double d4 = (random2.nextDouble() - 0.5) * 0.5;
                double d5 = (random2.nextDouble() - 0.5) * 0.5;
                double d6 = (random2.nextDouble() - 0.5) * 0.5;
                int n2 = random2.nextInt(2) * 2 - 1;
                if (random2.nextBoolean()) {
                    d3 = (double)blockPos.getZ() + 0.5 + 0.25 * (double)n2;
                    d6 = random2.nextFloat() * 2.0f * (float)n2;
                } else {
                    d = (double)blockPos.getX() + 0.5 + 0.25 * (double)n2;
                    d4 = random2.nextFloat() * 2.0f * (float)n2;
                }
                world.addParticle(ParticleTypes.PORTAL, d, d2, d3, d4, d5, d6);
            }
        }
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

