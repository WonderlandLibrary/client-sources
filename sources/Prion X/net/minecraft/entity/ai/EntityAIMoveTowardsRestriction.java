package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
  private EntityCreature theEntity;
  private double movePosX;
  private double movePosY;
  private double movePosZ;
  private double movementSpeed;
  private static final String __OBFID = "CL_00001598";
  
  public EntityAIMoveTowardsRestriction(EntityCreature p_i2347_1_, double p_i2347_2_)
  {
    theEntity = p_i2347_1_;
    movementSpeed = p_i2347_2_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if (theEntity.isWithinHomeDistanceCurrentPosition())
    {
      return false;
    }
    

    BlockPos var1 = theEntity.func_180486_cf();
    Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7, new Vec3(var1.getX(), var1.getY(), var1.getZ()));
    
    if (var2 == null)
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
    return !theEntity.getNavigator().noPath();
  }
  



  public void startExecuting()
  {
    theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, movementSpeed);
  }
}
