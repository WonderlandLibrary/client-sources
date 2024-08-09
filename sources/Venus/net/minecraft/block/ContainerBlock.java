/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerBlock
extends Block
implements ITileEntityProvider {
    protected ContainerBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean eventReceived(BlockState blockState, World world, BlockPos blockPos, int n, int n2) {
        super.eventReceived(blockState, world, blockPos, n, n2);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity == null ? false : tileEntity.receiveClientEvent(n, n2);
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider)((Object)tileEntity) : null;
    }
}

