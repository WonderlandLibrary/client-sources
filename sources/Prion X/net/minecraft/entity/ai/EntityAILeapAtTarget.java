package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;





public class EntityAILeapAtTarget
  extends EntityAIBase
{
  EntityLiving leaper;
  EntityLivingBase leapTarget;
  float leapMotionY;
  private static final String __OBFID = "CL_00001591";
  
  public EntityAILeapAtTarget(EntityLiving p_i1630_1_, float p_i1630_2_)
  {
    leaper = p_i1630_1_;
    leapMotionY = p_i1630_2_;
    setMutexBits(5);
  }
  



  public boolean shouldExecute()
  {
    leapTarget = leaper.getAttackTarget();
    
    if (leapTarget == null)
    {
      return false;
    }
    

    double var1 = leaper.getDistanceSqToEntity(leapTarget);
    return leaper.onGround;
  }
  




  public boolean continueExecuting()
  {
    return !leaper.onGround;
  }
  



  public void startExecuting()
  {
    double var1 = leapTarget.posX - leaper.posX;
    double var3 = leapTarget.posZ - leaper.posZ;
    float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
    leaper.motionX += var1 / var5 * 0.5D * 0.800000011920929D + leaper.motionX * 0.20000000298023224D;
    leaper.motionZ += var3 / var5 * 0.5D * 0.800000011920929D + leaper.motionZ * 0.20000000298023224D;
    leaper.motionY = leapMotionY;
  }
}
