package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.world.chunk.NibbleArray;
















public class ExtendedBlockStorage
{
  private int yBase;
  private int blockRefCount;
  private int tickRefCount;
  private char[] data;
  private NibbleArray blocklightArray;
  private NibbleArray skylightArray;
  private static final String __OBFID = "CL_00000375";
  
  public ExtendedBlockStorage(int y, boolean storeSkylight)
  {
    yBase = y;
    data = new char['က'];
    blocklightArray = new NibbleArray();
    
    if (storeSkylight)
    {
      skylightArray = new NibbleArray();
    }
  }
  
  public IBlockState get(int x, int y, int z)
  {
    IBlockState var4 = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(data[(y << 8 | z << 4 | x)]);
    return var4 != null ? var4 : Blocks.air.getDefaultState();
  }
  
  public void set(int x, int y, int z, IBlockState state)
  {
    IBlockState var5 = get(x, y, z);
    Block var6 = var5.getBlock();
    Block var7 = state.getBlock();
    
    if (var6 != Blocks.air)
    {
      blockRefCount -= 1;
      
      if (var6.getTickRandomly())
      {
        tickRefCount -= 1;
      }
    }
    
    if (var7 != Blocks.air)
    {
      blockRefCount += 1;
      
      if (var7.getTickRandomly())
      {
        tickRefCount += 1;
      }
    }
    
    data[(y << 8 | z << 4 | x)] = ((char)Block.BLOCK_STATE_IDS.get(state));
  }
  




  public Block getBlockByExtId(int x, int y, int z)
  {
    return get(x, y, z).getBlock();
  }
  



  public int getExtBlockMetadata(int x, int y, int z)
  {
    IBlockState var4 = get(x, y, z);
    return var4.getBlock().getMetaFromState(var4);
  }
  



  public boolean isEmpty()
  {
    return blockRefCount == 0;
  }
  




  public boolean getNeedsRandomTick()
  {
    return tickRefCount > 0;
  }
  



  public int getYLocation()
  {
    return yBase;
  }
  



  public void setExtSkylightValue(int x, int y, int z, int value)
  {
    skylightArray.set(x, y, z, value);
  }
  



  public int getExtSkylightValue(int x, int y, int z)
  {
    return skylightArray.get(x, y, z);
  }
  



  public void setExtBlocklightValue(int x, int y, int z, int value)
  {
    blocklightArray.set(x, y, z, value);
  }
  



  public int getExtBlocklightValue(int x, int y, int z)
  {
    return blocklightArray.get(x, y, z);
  }
  
  public void removeInvalidBlocks()
  {
    blockRefCount = 0;
    tickRefCount = 0;
    
    for (int var1 = 0; var1 < 16; var1++)
    {
      for (int var2 = 0; var2 < 16; var2++)
      {
        for (int var3 = 0; var3 < 16; var3++)
        {
          Block var4 = getBlockByExtId(var1, var2, var3);
          
          if (var4 != Blocks.air)
          {
            blockRefCount += 1;
            
            if (var4.getTickRandomly())
            {
              tickRefCount += 1;
            }
          }
        }
      }
    }
  }
  
  public char[] getData()
  {
    return data;
  }
  
  public void setData(char[] dataArray)
  {
    data = dataArray;
  }
  



  public NibbleArray getBlocklightArray()
  {
    return blocklightArray;
  }
  



  public NibbleArray getSkylightArray()
  {
    return skylightArray;
  }
  



  public void setBlocklightArray(NibbleArray newBlocklightArray)
  {
    blocklightArray = newBlocklightArray;
  }
  



  public void setSkylightArray(NibbleArray newSkylightArray)
  {
    skylightArray = newSkylightArray;
  }
}
