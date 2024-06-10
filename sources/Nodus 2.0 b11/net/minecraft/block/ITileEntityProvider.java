package net.minecraft.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract interface ITileEntityProvider
{
  public abstract TileEntity createNewTileEntity(World paramWorld, int paramInt);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.ITileEntityProvider
 * JD-Core Version:    0.7.0.1
 */