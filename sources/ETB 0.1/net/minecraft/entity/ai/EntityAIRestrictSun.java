package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase
{
  private EntityCreature theEntity;
  private static final String __OBFID = "CL_00001611";
  
  public EntityAIRestrictSun(EntityCreature p_i1652_1_)
  {
    theEntity = p_i1652_1_;
  }
  



  public boolean shouldExecute()
  {
    return theEntity.worldObj.isDaytime();
  }
  



  public void startExecuting()
  {
    ((PathNavigateGround)theEntity.getNavigator()).func_179685_e(true);
  }
  



  public void resetTask()
  {
    ((PathNavigateGround)theEntity.getNavigator()).func_179685_e(false);
  }
}
