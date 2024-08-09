/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.BlockState;
import net.minecraft.dispenser.IPosition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public interface IBlockSource
extends IPosition {
    @Override
    public double getX();

    @Override
    public double getY();

    @Override
    public double getZ();

    public BlockPos getBlockPos();

    public BlockState getBlockState();

    public <T extends TileEntity> T getBlockTileEntity();

    public ServerWorld getWorld();
}

