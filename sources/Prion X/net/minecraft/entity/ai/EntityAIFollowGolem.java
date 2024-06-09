package net.minecraft.entity.ai;

import java.util.Iterator;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAIFollowGolem extends EntityAIBase
{
  private EntityVillager theVillager;
  private EntityIronGolem theGolem;
  private int takeGolemRoseTick;
  private boolean tookGolemRose;
  private static final String __OBFID = "CL_00001615";
  
  public EntityAIFollowGolem(EntityVillager p_i1656_1_)
  {
    theVillager = p_i1656_1_;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    if (theVillager.getGrowingAge() >= 0)
    {
      return false;
    }
    if (!theVillager.worldObj.isDaytime())
    {
      return false;
    }
    

    java.util.List var1 = theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));
    
    if (var1.isEmpty())
    {
      return false;
    }
    

    Iterator var2 = var1.iterator();
    
    while (var2.hasNext())
    {
      EntityIronGolem var3 = (EntityIronGolem)var2.next();
      
      if (var3.getHoldRoseTick() > 0)
      {
        theGolem = var3;
        break;
      }
    }
    
    return theGolem != null;
  }
  





  public boolean continueExecuting()
  {
    return theGolem.getHoldRoseTick() > 0;
  }
  



  public void startExecuting()
  {
    takeGolemRoseTick = theVillager.getRNG().nextInt(320);
    tookGolemRose = false;
    theGolem.getNavigator().clearPathEntity();
  }
  



  public void resetTask()
  {
    theGolem = null;
    theVillager.getNavigator().clearPathEntity();
  }
  



  public void updateTask()
  {
    theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30.0F, 30.0F);
    
    if (theGolem.getHoldRoseTick() == takeGolemRoseTick)
    {
      theVillager.getNavigator().tryMoveToEntityLiving(theGolem, 0.5D);
      tookGolemRose = true;
    }
    
    if ((tookGolemRose) && (theVillager.getDistanceSqToEntity(theGolem) < 4.0D))
    {
      theGolem.setHoldingRose(false);
      theVillager.getNavigator().clearPathEntity();
    }
  }
}
