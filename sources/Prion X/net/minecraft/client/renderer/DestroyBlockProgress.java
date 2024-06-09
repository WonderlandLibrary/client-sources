package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;













public class DestroyBlockProgress
{
  private final int miningPlayerEntId;
  private final BlockPos field_180247_b;
  private int partialBlockProgress;
  private int createdAtCloudUpdateTick;
  private static final String __OBFID = "CL_00001427";
  
  public DestroyBlockProgress(int p_i45925_1_, BlockPos p_i45925_2_)
  {
    miningPlayerEntId = p_i45925_1_;
    field_180247_b = p_i45925_2_;
  }
  
  public BlockPos func_180246_b()
  {
    return field_180247_b;
  }
  




  public void setPartialBlockDamage(int damage)
  {
    if (damage > 10)
    {
      damage = 10;
    }
    
    partialBlockProgress = damage;
  }
  
  public int getPartialBlockDamage()
  {
    return partialBlockProgress;
  }
  



  public void setCloudUpdateTick(int p_82744_1_)
  {
    createdAtCloudUpdateTick = p_82744_1_;
  }
  



  public int getCreationCloudUpdateTick()
  {
    return createdAtCloudUpdateTick;
  }
}
