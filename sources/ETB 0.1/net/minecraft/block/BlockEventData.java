package net.minecraft.block;

import net.minecraft.util.BlockPos;



public class BlockEventData
{
  private BlockPos field_180329_a;
  private Block field_151344_d;
  private int eventID;
  private int eventParameter;
  private static final String __OBFID = "CL_00000131";
  
  public BlockEventData(BlockPos p_i45756_1_, Block p_i45756_2_, int p_i45756_3_, int p_i45756_4_)
  {
    field_180329_a = p_i45756_1_;
    eventID = p_i45756_3_;
    eventParameter = p_i45756_4_;
    field_151344_d = p_i45756_2_;
  }
  
  public BlockPos func_180328_a()
  {
    return field_180329_a;
  }
  



  public int getEventID()
  {
    return eventID;
  }
  
  public int getEventParameter()
  {
    return eventParameter;
  }
  
  public Block getBlock()
  {
    return field_151344_d;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (!(p_equals_1_ instanceof BlockEventData))
    {
      return false;
    }
    

    BlockEventData var2 = (BlockEventData)p_equals_1_;
    return (field_180329_a.equals(field_180329_a)) && (eventID == eventID) && (eventParameter == eventParameter) && (field_151344_d == field_151344_d);
  }
  

  public String toString()
  {
    return "TE(" + field_180329_a + ")," + eventID + "," + eventParameter + "," + field_151344_d;
  }
}
