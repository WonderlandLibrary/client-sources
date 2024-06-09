package net.minecraft.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIFollowParent extends EntityAIBase
{
  EntityAnimal childAnimal;
  EntityAnimal parentAnimal;
  double field_75347_c;
  private int field_75345_d;
  private static final String __OBFID = "CL_00001586";
  
  public EntityAIFollowParent(EntityAnimal p_i1626_1_, double p_i1626_2_)
  {
    childAnimal = p_i1626_1_;
    field_75347_c = p_i1626_2_;
  }
  



  public boolean shouldExecute()
  {
    if (childAnimal.getGrowingAge() >= 0)
    {
      return false;
    }
    

    List var1 = childAnimal.worldObj.getEntitiesWithinAABB(childAnimal.getClass(), childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
    EntityAnimal var2 = null;
    double var3 = Double.MAX_VALUE;
    Iterator var5 = var1.iterator();
    
    while (var5.hasNext())
    {
      EntityAnimal var6 = (EntityAnimal)var5.next();
      
      if (var6.getGrowingAge() >= 0)
      {
        double var7 = childAnimal.getDistanceSqToEntity(var6);
        
        if (var7 <= var3)
        {
          var3 = var7;
          var2 = var6;
        }
      }
    }
    
    if (var2 == null)
    {
      return false;
    }
    if (var3 < 9.0D)
    {
      return false;
    }
    

    parentAnimal = var2;
    return true;
  }
  





  public boolean continueExecuting()
  {
    if (childAnimal.getGrowingAge() >= 0)
    {
      return false;
    }
    if (!parentAnimal.isEntityAlive())
    {
      return false;
    }
    

    double var1 = childAnimal.getDistanceSqToEntity(parentAnimal);
    return (var1 >= 9.0D) && (var1 <= 256.0D);
  }
  




  public void startExecuting()
  {
    field_75345_d = 0;
  }
  



  public void resetTask()
  {
    parentAnimal = null;
  }
  



  public void updateTask()
  {
    if (--field_75345_d <= 0)
    {
      field_75345_d = 10;
      childAnimal.getNavigator().tryMoveToEntityLiving(parentAnimal, field_75347_c);
    }
  }
}
