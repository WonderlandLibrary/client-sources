package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase
{
  private EntityVillager villager;
  private static final String __OBFID = "CL_00001617";
  
  public EntityAITradePlayer(EntityVillager p_i1658_1_)
  {
    villager = p_i1658_1_;
    setMutexBits(5);
  }
  



  public boolean shouldExecute()
  {
    if (!villager.isEntityAlive())
    {
      return false;
    }
    if (villager.isInWater())
    {
      return false;
    }
    if (!villager.onGround)
    {
      return false;
    }
    if (villager.velocityChanged)
    {
      return false;
    }
    

    EntityPlayer var1 = villager.getCustomer();
    return villager.getDistanceSqToEntity(var1) > 16.0D ? false : var1 == null ? false : openContainer instanceof Container;
  }
  




  public void startExecuting()
  {
    villager.getNavigator().clearPathEntity();
  }
  



  public void resetTask()
  {
    villager.setCustomer(null);
  }
}
