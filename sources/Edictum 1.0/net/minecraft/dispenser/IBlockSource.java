package net.minecraft.dispenser;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public interface IBlockSource extends ILocatableSource
{
    double getX();

    double getY();

    double getZ();

    BlockPos getBlockPos();

    IBlockState func_189992_e();

    <T extends TileEntity> T getBlockTileEntity();
}
