package net.minecraft.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public interface ITileEntityProvider
{
    @Nullable
    TileEntity createNewTileEntity(IBlockReader worldIn);
}
