/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.ILocatableSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public interface IBlockSource
extends ILocatableSource {
    @Override
    public double getX();

    @Override
    public double getY();

    @Override
    public double getZ();

    public BlockPos getBlockPos();

    public IBlockState getBlockState();

    public <T extends TileEntity> T getBlockTileEntity();
}

