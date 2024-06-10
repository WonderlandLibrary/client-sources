package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.biome.BiomeGenBase;

public abstract interface IBlockAccess
{
  public abstract Block getBlock(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getLightBrightnessForSkyBlocks(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract int getBlockMetadata(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean isAirBlock(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract BiomeGenBase getBiomeGenForCoords(int paramInt1, int paramInt2);
  
  public abstract int getHeight();
  
  public abstract boolean extendedLevelsInChunkCache();
  
  public abstract Vec3Pool getWorldVec3Pool();
  
  public abstract int isBlockProvidingPowerTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.IBlockAccess
 * JD-Core Version:    0.7.0.1
 */