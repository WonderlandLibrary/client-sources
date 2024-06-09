package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.PathNavigate;




public class EntityAICreeperSwell
  extends EntityAIBase
{
  EntityCreeper swellingCreeper;
  EntityLivingBase creeperAttackTarget;
  private static final String __OBFID = "CL_00001614";
  
  public EntityAICreeperSwell(EntityCreeper p_i1655_1_)
  {
    swellingCreeper = p_i1655_1_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    EntityLivingBase var1 = swellingCreeper.getAttackTarget();
    return (swellingCreeper.getCreeperState() > 0) || ((var1 != null) && (swellingCreeper.getDistanceSqToEntity(var1) < 9.0D));
  }
  



  public void startExecuting()
  {
    swellingCreeper.getNavigator().clearPathEntity();
    creeperAttackTarget = swellingCreeper.getAttackTarget();
  }
  



  public void resetTask()
  {
    creeperAttackTarget = null;
  }
  



  public void updateTask()
  {
    if (creeperAttackTarget == null)
    {
      swellingCreeper.setCreeperState(-1);
    }
    else if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49.0D)
    {
      swellingCreeper.setCreeperState(-1);
    }
    else if (!swellingCreeper.getEntitySenses().canSee(creeperAttackTarget))
    {
      swellingCreeper.setCreeperState(-1);
    }
    else
    {
      swellingCreeper.setCreeperState(1);
    }
  }
}
