package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;



public class EntityAIMoveTowardsTarget
  extends EntityAIBase
{
  private EntityCreature theEntity;
  private EntityLivingBase targetEntity;
  private double movePosX;
  private double movePosY;
  private double movePosZ;
  private double speed;
  private float maxTargetDistance;
  private static final String __OBFID = "CL_00001599";
  
  public EntityAIMoveTowardsTarget(EntityCreature p_i1640_1_, double p_i1640_2_, float p_i1640_4_)
  {
    theEntity = p_i1640_1_;
    speed = p_i1640_2_;
    maxTargetDistance = p_i1640_4_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    targetEntity = theEntity.getAttackTarget();
    
    if (targetEntity == null)
    {
      return false;
    }
    if (targetEntity.getDistanceSqToEntity(theEntity) > maxTargetDistance * maxTargetDistance)
    {
      return false;
    }
    

    Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7, new Vec3(targetEntity.posX, targetEntity.posY, targetEntity.posZ));
    
    if (var1 == null)
    {
      return false;
    }
    

    movePosX = xCoord;
    movePosY = yCoord;
    movePosZ = zCoord;
    return true;
  }
  





  public boolean continueExecuting()
  {
    return (!theEntity.getNavigator().noPath()) && (targetEntity.isEntityAlive()) && (targetEntity.getDistanceSqToEntity(theEntity) < maxTargetDistance * maxTargetDistance);
  }
  



  public void resetTask()
  {
    targetEntity = null;
  }
  



  public void startExecuting()
  {
    theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, speed);
  }
}
