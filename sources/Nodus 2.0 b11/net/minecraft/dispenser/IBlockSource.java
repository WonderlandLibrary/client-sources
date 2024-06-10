package net.minecraft.dispenser;

import net.minecraft.tileentity.TileEntity;

public abstract interface IBlockSource
  extends ILocatableSource
{
  public abstract double getX();
  
  public abstract double getY();
  
  public abstract double getZ();
  
  public abstract int getXInt();
  
  public abstract int getYInt();
  
  public abstract int getZInt();
  
  public abstract int getBlockMetadata();
  
  public abstract TileEntity getBlockTileEntity();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.dispenser.IBlockSource
 * JD-Core Version:    0.7.0.1
 */