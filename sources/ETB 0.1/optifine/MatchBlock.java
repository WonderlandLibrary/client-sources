package optifine;

import net.minecraft.block.state.BlockStateBase;

public class MatchBlock
{
  private int blockId = -1;
  private int[] metadatas = null;
  
  public MatchBlock(int blockId)
  {
    this.blockId = blockId;
  }
  
  public MatchBlock(int blockId, int metadata)
  {
    this.blockId = blockId;
    
    if ((metadata >= 0) && (metadata <= 15))
    {
      metadatas = new int[] { metadata };
    }
  }
  
  public MatchBlock(int blockId, int[] metadatas)
  {
    this.blockId = blockId;
    this.metadatas = metadatas;
  }
  
  public int getBlockId()
  {
    return blockId;
  }
  
  public int[] getMetadatas()
  {
    return metadatas;
  }
  
  public boolean matches(BlockStateBase blockState)
  {
    return blockState.getBlockId() != blockId ? false : Matches.metadata(blockState.getMetadata(), metadatas);
  }
  
  public boolean matches(int id, int metadata)
  {
    return id != blockId ? false : Matches.metadata(metadata, metadatas);
  }
  
  public void addMetadata(int metadata)
  {
    if (metadatas != null)
    {
      if ((metadata >= 0) && (metadata <= 15))
      {
        for (int i = 0; i < metadatas.length; i++)
        {
          if (metadatas[i] == metadata)
          {
            return;
          }
        }
        
        metadatas = Config.addIntToArray(metadatas, metadata);
      }
    }
  }
  
  public String toString()
  {
    return blockId + ":" + Config.arrayToString(metadatas);
  }
}
