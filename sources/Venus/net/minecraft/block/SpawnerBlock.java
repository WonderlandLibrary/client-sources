/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class SpawnerBlock
extends ContainerBlock {
    protected SpawnerBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new MobSpawnerTileEntity();
    }

    @Override
    public void spawnAdditionalDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        super.spawnAdditionalDrops(blockState, serverWorld, blockPos, itemStack);
        int n = 15 + serverWorld.rand.nextInt(15) + serverWorld.rand.nextInt(15);
        this.dropXpOnBlockBreak(serverWorld, blockPos, n);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }
}

