package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAISit
  extends EntityAIBase
{
  private EntityTameable theEntity;
  private boolean isSitting;
  private static final String __OBFID = "CL_00001613";
  
  public EntityAISit(EntityTameable p_i1654_1_)
  {
    theEntity = p_i1654_1_;
    setMutexBits(5);
  }
  



  public boolean shouldExecute()
  {
    if (!theEntity.isTamed())
    {
      return false;
    }
    if (theEntity.isInWater())
    {
      return false;
    }
    if (!theEntity.onGround)
    {
      return false;
    }
    

    EntityLivingBase var1 = theEntity.func_180492_cm();
    return (theEntity.getDistanceSqToEntity(var1) < 144.0D) && (var1.getAITarget() != null) ? false : var1 == null ? true : isSitting;
  }
  




  public void startExecuting()
  {
    theEntity.getNavigator().clearPathEntity();
    theEntity.setSitting(true);
  }
  



  public void resetTask()
  {
    theEntity.setSitting(false);
  }
  



  public void setSitting(boolean p_75270_1_)
  {
    isSitting = p_75270_1_;
  }
}
