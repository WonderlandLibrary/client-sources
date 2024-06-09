package net.minecraft.dispenser;

import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
  double getX();
  
  double getY();
  
  double getZ();
  
  BlockPos getBlockPos();
  
  int getBlockMetadata();
  
  <T extends net.minecraft.tileentity.TileEntity> T getBlockTileEntity();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\dispenser\IBlockSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */