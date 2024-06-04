package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIPanic extends EntityAIBase
{
  private EntityCreature theEntityCreature;
  protected double speed;
  private double randPosX;
  private double randPosY;
  private double randPosZ;
  private static final String __OBFID = "CL_00001604";
  
  public EntityAIPanic(EntityCreature p_i1645_1_, double p_i1645_2_)
  {
    theEntityCreature = p_i1645_1_;
    speed = p_i1645_2_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if ((theEntityCreature.getAITarget() == null) && (!theEntityCreature.isBurning()))
    {
      return false;
    }
    

    Vec3 var1 = RandomPositionGenerator.findRandomTarget(theEntityCreature, 5, 4);
    
    if (var1 == null)
    {
      return false;
    }
    

    randPosX = xCoord;
    randPosY = yCoord;
    randPosZ = zCoord;
    return true;
  }
  





  public void startExecuting()
  {
    theEntityCreature.getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
  }
  



  public boolean continueExecuting()
  {
    return !theEntityCreature.getNavigator().noPath();
  }
}
