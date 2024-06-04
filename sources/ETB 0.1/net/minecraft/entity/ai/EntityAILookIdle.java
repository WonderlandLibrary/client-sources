package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;








public class EntityAILookIdle
  extends EntityAIBase
{
  private EntityLiving idleEntity;
  private double lookX;
  private double lookZ;
  private int idleTime;
  private static final String __OBFID = "CL_00001607";
  
  public EntityAILookIdle(EntityLiving p_i1647_1_)
  {
    idleEntity = p_i1647_1_;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    return idleEntity.getRNG().nextFloat() < 0.02F;
  }
  



  public boolean continueExecuting()
  {
    return idleTime >= 0;
  }
  



  public void startExecuting()
  {
    double var1 = 6.283185307179586D * idleEntity.getRNG().nextDouble();
    lookX = Math.cos(var1);
    lookZ = Math.sin(var1);
    idleTime = (20 + idleEntity.getRNG().nextInt(20));
  }
  



  public void updateTask()
  {
    idleTime -= 1;
    idleEntity.getLookHelper().setLookPosition(idleEntity.posX + lookX, idleEntity.posY + idleEntity.getEyeHeight(), idleEntity.posZ + lookZ, 10.0F, idleEntity.getVerticalFaceSpeed());
  }
}
