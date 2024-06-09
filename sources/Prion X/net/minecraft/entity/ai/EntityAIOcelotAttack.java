package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAIOcelotAttack extends EntityAIBase
{
  net.minecraft.world.World theWorld;
  EntityLiving theEntity;
  EntityLivingBase theVictim;
  int attackCountdown;
  private static final String __OBFID = "CL_00001600";
  
  public EntityAIOcelotAttack(EntityLiving p_i1641_1_)
  {
    theEntity = p_i1641_1_;
    theWorld = worldObj;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    EntityLivingBase var1 = theEntity.getAttackTarget();
    
    if (var1 == null)
    {
      return false;
    }
    

    theVictim = var1;
    return true;
  }
  




  public boolean continueExecuting()
  {
    return theVictim.isEntityAlive();
  }
  



  public void resetTask()
  {
    theVictim = null;
    theEntity.getNavigator().clearPathEntity();
  }
  



  public void updateTask()
  {
    theEntity.getLookHelper().setLookPositionWithEntity(theVictim, 30.0F, 30.0F);
    double var1 = theEntity.width * 2.0F * theEntity.width * 2.0F;
    double var3 = theEntity.getDistanceSq(theVictim.posX, theVictim.getEntityBoundingBox().minY, theVictim.posZ);
    double var5 = 0.8D;
    
    if ((var3 > var1) && (var3 < 16.0D))
    {
      var5 = 1.33D;
    }
    else if (var3 < 225.0D)
    {
      var5 = 0.6D;
    }
    
    theEntity.getNavigator().tryMoveToEntityLiving(theVictim, var5);
    attackCountdown = Math.max(attackCountdown - 1, 0);
    
    if (var3 <= var1)
    {
      if (attackCountdown <= 0)
      {
        attackCountdown = 20;
        theEntity.attackEntityAsMob(theVictim);
      }
    }
  }
}
