package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;





public class NextTickListEntry
  implements Comparable
{
  private static long nextTickEntryID;
  private final Block field_151352_g;
  public final BlockPos field_180282_a;
  public long scheduledTime;
  public int priority;
  private long tickEntryID;
  private static final String __OBFID = "CL_00000156";
  
  public NextTickListEntry(BlockPos p_i45745_1_, Block p_i45745_2_)
  {
    tickEntryID = (nextTickEntryID++);
    field_180282_a = p_i45745_1_;
    field_151352_g = p_i45745_2_;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (!(p_equals_1_ instanceof NextTickListEntry))
    {
      return false;
    }
    

    NextTickListEntry var2 = (NextTickListEntry)p_equals_1_;
    return (field_180282_a.equals(field_180282_a)) && (Block.isEqualTo(field_151352_g, field_151352_g));
  }
  

  public int hashCode()
  {
    return field_180282_a.hashCode();
  }
  



  public NextTickListEntry setScheduledTime(long p_77176_1_)
  {
    scheduledTime = p_77176_1_;
    return this;
  }
  
  public void setPriority(int p_82753_1_)
  {
    priority = p_82753_1_;
  }
  
  public int compareTo(NextTickListEntry p_compareTo_1_)
  {
    return tickEntryID > tickEntryID ? 1 : tickEntryID < tickEntryID ? -1 : priority != priority ? priority - priority : scheduledTime > scheduledTime ? 1 : scheduledTime < scheduledTime ? -1 : 0;
  }
  
  public String toString()
  {
    return Block.getIdFromBlock(field_151352_g) + ": " + field_180282_a + ", " + scheduledTime + ", " + priority + ", " + tickEntryID;
  }
  
  public Block func_151351_a()
  {
    return field_151352_g;
  }
  
  public int compareTo(Object p_compareTo_1_)
  {
    return compareTo((NextTickListEntry)p_compareTo_1_);
  }
}
