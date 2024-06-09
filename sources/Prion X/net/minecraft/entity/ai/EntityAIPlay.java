package net.minecraft.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIPlay extends EntityAIBase
{
  private EntityVillager villagerObj;
  private net.minecraft.entity.EntityLivingBase targetVillager;
  private double field_75261_c;
  private int playTime;
  private static final String __OBFID = "CL_00001605";
  
  public EntityAIPlay(EntityVillager p_i1646_1_, double p_i1646_2_)
  {
    villagerObj = p_i1646_1_;
    field_75261_c = p_i1646_2_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if (villagerObj.getGrowingAge() >= 0)
    {
      return false;
    }
    if (villagerObj.getRNG().nextInt(400) != 0)
    {
      return false;
    }
    

    List var1 = villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, villagerObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
    double var2 = Double.MAX_VALUE;
    Iterator var4 = var1.iterator();
    
    while (var4.hasNext())
    {
      EntityVillager var5 = (EntityVillager)var4.next();
      
      if ((var5 != villagerObj) && (!var5.isPlaying()) && (var5.getGrowingAge() < 0))
      {
        double var6 = var5.getDistanceSqToEntity(villagerObj);
        
        if (var6 <= var2)
        {
          var2 = var6;
          targetVillager = var5;
        }
      }
    }
    
    if (targetVillager == null)
    {
      Vec3 var8 = RandomPositionGenerator.findRandomTarget(villagerObj, 16, 3);
      
      if (var8 == null)
      {
        return false;
      }
    }
    
    return true;
  }
  




  public boolean continueExecuting()
  {
    return playTime > 0;
  }
  



  public void startExecuting()
  {
    if (targetVillager != null)
    {
      villagerObj.setPlaying(true);
    }
    
    playTime = 1000;
  }
  



  public void resetTask()
  {
    villagerObj.setPlaying(false);
    targetVillager = null;
  }
  



  public void updateTask()
  {
    playTime -= 1;
    
    if (targetVillager != null)
    {
      if (villagerObj.getDistanceSqToEntity(targetVillager) > 4.0D)
      {
        villagerObj.getNavigator().tryMoveToEntityLiving(targetVillager, field_75261_c);
      }
    }
    else if (villagerObj.getNavigator().noPath())
    {
      Vec3 var1 = RandomPositionGenerator.findRandomTarget(villagerObj, 16, 3);
      
      if (var1 == null)
      {
        return;
      }
      
      villagerObj.getNavigator().tryMoveToXYZ(xCoord, yCoord, zCoord, field_75261_c);
    }
  }
}
