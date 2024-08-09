/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractChestBlock<E extends TileEntity>
extends ContainerBlock {
    protected final Supplier<TileEntityType<? extends E>> tileEntityType;

    protected AbstractChestBlock(AbstractBlock.Properties properties, Supplier<TileEntityType<? extends E>> supplier) {
        super(properties);
        this.tileEntityType = supplier;
    }

    public abstract TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState var1, World var2, BlockPos var3, boolean var4);
}

