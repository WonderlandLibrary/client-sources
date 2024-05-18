package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
  private EntityHorse horseHost;
  private double field_111178_b;
  private double field_111179_c;
  private double field_111176_d;
  private double field_111177_e;
  private static final String __OBFID = "CL_00001612";
  
  public EntityAIRunAroundLikeCrazy(EntityHorse p_i1653_1_, double p_i1653_2_)
  {
    horseHost = p_i1653_1_;
    field_111178_b = p_i1653_2_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if ((!horseHost.isTame()) && (horseHost.riddenByEntity != null))
    {
      Vec3 var1 = RandomPositionGenerator.findRandomTarget(horseHost, 5, 4);
      
      if (var1 == null)
      {
        return false;
      }
      

      field_111179_c = xCoord;
      field_111176_d = yCoord;
      field_111177_e = zCoord;
      return true;
    }
    


    return false;
  }
  




  public void startExecuting()
  {
    horseHost.getNavigator().tryMoveToXYZ(field_111179_c, field_111176_d, field_111177_e, field_111178_b);
  }
  



  public boolean continueExecuting()
  {
    return (!horseHost.getNavigator().noPath()) && (horseHost.riddenByEntity != null);
  }
  



  public void updateTask()
  {
    if (horseHost.getRNG().nextInt(50) == 0)
    {
      if ((horseHost.riddenByEntity instanceof EntityPlayer))
      {
        int var1 = horseHost.getTemper();
        int var2 = horseHost.getMaxTemper();
        
        if ((var2 > 0) && (horseHost.getRNG().nextInt(var2) < var1))
        {
          horseHost.setTamedBy((EntityPlayer)horseHost.riddenByEntity);
          horseHost.worldObj.setEntityState(horseHost, (byte)7);
          return;
        }
        
        horseHost.increaseTemper(5);
      }
      
      horseHost.riddenByEntity.mountEntity(null);
      horseHost.riddenByEntity = null;
      horseHost.makeHorseRearWithSound();
      horseHost.worldObj.setEntityState(horseHost, (byte)6);
    }
  }
}
