package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
  private EntityCreature entityObj;
  private net.minecraft.village.VillageDoorInfo frontDoor;
  private static final String __OBFID = "CL_00001610";
  
  public EntityAIRestrictOpenDoor(EntityCreature p_i1651_1_)
  {
    entityObj = p_i1651_1_;
    
    if (!(p_i1651_1_.getNavigator() instanceof PathNavigateGround))
    {
      throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
    }
  }
  



  public boolean shouldExecute()
  {
    if (entityObj.worldObj.isDaytime())
    {
      return false;
    }
    

    BlockPos var1 = new BlockPos(entityObj);
    Village var2 = entityObj.worldObj.getVillageCollection().func_176056_a(var1, 16);
    
    if (var2 == null)
    {
      return false;
    }
    

    frontDoor = var2.func_179865_b(var1);
    return frontDoor != null;
  }
  





  public boolean continueExecuting()
  {
    return !entityObj.worldObj.isDaytime();
  }
  



  public void startExecuting()
  {
    ((PathNavigateGround)entityObj.getNavigator()).func_179688_b(false);
    ((PathNavigateGround)entityObj.getNavigator()).func_179691_c(false);
  }
  



  public void resetTask()
  {
    ((PathNavigateGround)entityObj.getNavigator()).func_179688_b(true);
    ((PathNavigateGround)entityObj.getNavigator()).func_179691_c(true);
    frontDoor = null;
  }
  



  public void updateTask()
  {
    frontDoor.incrementDoorOpeningRestrictionCounter();
  }
}
