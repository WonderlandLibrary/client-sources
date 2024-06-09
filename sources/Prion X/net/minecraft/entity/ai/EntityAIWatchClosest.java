package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAIWatchClosest
  extends EntityAIBase
{
  protected EntityLiving theWatcher;
  protected Entity closestEntity;
  protected float maxDistanceForPlayer;
  private int lookTime;
  private float field_75331_e;
  protected Class watchedClass;
  private static final String __OBFID = "CL_00001592";
  
  public EntityAIWatchClosest(EntityLiving p_i1631_1_, Class p_i1631_2_, float p_i1631_3_)
  {
    theWatcher = p_i1631_1_;
    watchedClass = p_i1631_2_;
    maxDistanceForPlayer = p_i1631_3_;
    field_75331_e = 0.02F;
    setMutexBits(2);
  }
  
  public EntityAIWatchClosest(EntityLiving p_i1632_1_, Class p_i1632_2_, float p_i1632_3_, float p_i1632_4_)
  {
    theWatcher = p_i1632_1_;
    watchedClass = p_i1632_2_;
    maxDistanceForPlayer = p_i1632_3_;
    field_75331_e = p_i1632_4_;
    setMutexBits(2);
  }
  



  public boolean shouldExecute()
  {
    if (theWatcher.getRNG().nextFloat() >= field_75331_e)
    {
      return false;
    }
    

    if (theWatcher.getAttackTarget() != null)
    {
      closestEntity = theWatcher.getAttackTarget();
    }
    
    if (watchedClass == EntityPlayer.class)
    {
      closestEntity = theWatcher.worldObj.getClosestPlayerToEntity(theWatcher, maxDistanceForPlayer);
    }
    else
    {
      closestEntity = theWatcher.worldObj.findNearestEntityWithinAABB(watchedClass, theWatcher.getEntityBoundingBox().expand(maxDistanceForPlayer, 3.0D, maxDistanceForPlayer), theWatcher);
    }
    
    return closestEntity != null;
  }
  




  public boolean continueExecuting()
  {
    return closestEntity.isEntityAlive();
  }
  



  public void startExecuting()
  {
    lookTime = (40 + theWatcher.getRNG().nextInt(40));
  }
  



  public void resetTask()
  {
    closestEntity = null;
  }
  



  public void updateTask()
  {
    theWatcher.getLookHelper().setLookPosition(closestEntity.posX, closestEntity.posY + closestEntity.getEyeHeight(), closestEntity.posZ, 10.0F, theWatcher.getVerticalFaceSpeed());
    lookTime -= 1;
  }
}
