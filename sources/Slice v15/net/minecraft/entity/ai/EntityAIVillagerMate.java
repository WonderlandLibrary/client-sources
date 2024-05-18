package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIVillagerMate extends EntityAIBase
{
  private EntityVillager villagerObj;
  private EntityVillager mate;
  private World worldObj;
  private int matingTimeout;
  Village villageObj;
  private static final String __OBFID = "CL_00001594";
  
  public EntityAIVillagerMate(EntityVillager p_i1634_1_)
  {
    villagerObj = p_i1634_1_;
    worldObj = worldObj;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    if (villagerObj.getGrowingAge() != 0)
    {
      return false;
    }
    if (villagerObj.getRNG().nextInt(500) != 0)
    {
      return false;
    }
    

    villageObj = worldObj.getVillageCollection().func_176056_a(new net.minecraft.util.BlockPos(villagerObj), 0);
    
    if (villageObj == null)
    {
      return false;
    }
    if ((checkSufficientDoorsPresentForNewVillager()) && (villagerObj.func_175550_n(true)))
    {
      net.minecraft.entity.Entity var1 = worldObj.findNearestEntityWithinAABB(EntityVillager.class, villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), villagerObj);
      
      if (var1 == null)
      {
        return false;
      }
      

      mate = ((EntityVillager)var1);
      return (mate.getGrowingAge() == 0) && (mate.func_175550_n(true));
    }
    


    return false;
  }
  





  public void startExecuting()
  {
    matingTimeout = 300;
    villagerObj.setMating(true);
  }
  



  public void resetTask()
  {
    villageObj = null;
    mate = null;
    villagerObj.setMating(false);
  }
  



  public boolean continueExecuting()
  {
    return (matingTimeout >= 0) && (checkSufficientDoorsPresentForNewVillager()) && (villagerObj.getGrowingAge() == 0) && (villagerObj.func_175550_n(false));
  }
  



  public void updateTask()
  {
    matingTimeout -= 1;
    villagerObj.getLookHelper().setLookPositionWithEntity(mate, 10.0F, 30.0F);
    
    if (villagerObj.getDistanceSqToEntity(mate) > 2.25D)
    {
      villagerObj.getNavigator().tryMoveToEntityLiving(mate, 0.25D);
    }
    else if ((matingTimeout == 0) && (mate.isMating()))
    {
      giveBirth();
    }
    
    if (villagerObj.getRNG().nextInt(35) == 0)
    {
      worldObj.setEntityState(villagerObj, (byte)12);
    }
  }
  
  private boolean checkSufficientDoorsPresentForNewVillager()
  {
    if (!villageObj.isMatingSeason())
    {
      return false;
    }
    

    int var1 = (int)(villageObj.getNumVillageDoors() * 0.35D);
    return villageObj.getNumVillagers() < var1;
  }
  

  private void giveBirth()
  {
    EntityVillager var1 = villagerObj.func_180488_b(mate);
    mate.setGrowingAge(6000);
    villagerObj.setGrowingAge(6000);
    mate.func_175549_o(false);
    villagerObj.func_175549_o(false);
    var1.setGrowingAge(41536);
    var1.setLocationAndAngles(villagerObj.posX, villagerObj.posY, villagerObj.posZ, 0.0F, 0.0F);
    worldObj.spawnEntityInWorld(var1);
    worldObj.setEntityState(var1, (byte)12);
  }
}
