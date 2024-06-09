package net.minecraft.world;

import net.minecraft.util.BlockPos;




public class ChunkCoordIntPair
{
  public final int chunkXPos;
  public final int chunkZPos;
  private static final String __OBFID = "CL_00000133";
  private int cachedHashCode = 0;
  
  public ChunkCoordIntPair(int x, int z)
  {
    chunkXPos = x;
    chunkZPos = z;
  }
  



  public static long chunkXZ2Int(int x, int z)
  {
    return x & 0xFFFFFFFF | (z & 0xFFFFFFFF) << 32;
  }
  
  public int hashCode()
  {
    if (cachedHashCode == 0)
    {
      int var1 = 1664525 * chunkXPos + 1013904223;
      int var2 = 1664525 * (chunkZPos ^ 0xDEADBEEF) + 1013904223;
      cachedHashCode = (var1 ^ var2);
    }
    
    return cachedHashCode;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if (!(p_equals_1_ instanceof ChunkCoordIntPair))
    {
      return false;
    }
    

    ChunkCoordIntPair var2 = (ChunkCoordIntPair)p_equals_1_;
    return (chunkXPos == chunkXPos) && (chunkZPos == chunkZPos);
  }
  

  public int getCenterXPos()
  {
    return (chunkXPos << 4) + 8;
  }
  
  public int getCenterZPosition()
  {
    return (chunkZPos << 4) + 8;
  }
  



  public int getXStart()
  {
    return chunkXPos << 4;
  }
  



  public int getZStart()
  {
    return chunkZPos << 4;
  }
  



  public int getXEnd()
  {
    return (chunkXPos << 4) + 15;
  }
  



  public int getZEnd()
  {
    return (chunkZPos << 4) + 15;
  }
  







  public BlockPos getBlock(int x, int y, int z)
  {
    return new BlockPos((chunkXPos << 4) + x, y, (chunkZPos << 4) + z);
  }
  





  public BlockPos getCenterBlock(int y)
  {
    return new BlockPos(getCenterXPos(), y, getCenterZPosition());
  }
  
  public String toString()
  {
    return "[" + chunkXPos + ", " + chunkZPos + "]";
  }
}
