package net.minecraft.entity.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;



public class EntityAIMate
  extends EntityAIBase
{
  private EntityAnimal theAnimal;
  World theWorld;
  private EntityAnimal targetMate;
  int spawnBabyDelay;
  double moveSpeed;
  private static final String __OBFID = "CL_00001578";
  
  public EntityAIMate(EntityAnimal p_i1619_1_, double p_i1619_2_)
  {
    theAnimal = p_i1619_1_;
    theWorld = worldObj;
    moveSpeed = p_i1619_2_;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    if (!theAnimal.isInLove())
    {
      return false;
    }
    

    targetMate = getNearbyMate();
    return targetMate != null;
  }
  




  public boolean continueExecuting()
  {
    return (targetMate.isEntityAlive()) && (targetMate.isInLove()) && (spawnBabyDelay < 60);
  }
  



  public void resetTask()
  {
    targetMate = null;
    spawnBabyDelay = 0;
  }
  



  public void updateTask()
  {
    theAnimal.getLookHelper().setLookPositionWithEntity(targetMate, 10.0F, theAnimal.getVerticalFaceSpeed());
    theAnimal.getNavigator().tryMoveToEntityLiving(targetMate, moveSpeed);
    spawnBabyDelay += 1;
    
    if ((spawnBabyDelay >= 60) && (theAnimal.getDistanceSqToEntity(targetMate) < 9.0D))
    {
      spawnBaby();
    }
  }
  




  private EntityAnimal getNearbyMate()
  {
    float var1 = 8.0F;
    List var2 = theWorld.getEntitiesWithinAABB(theAnimal.getClass(), theAnimal.getEntityBoundingBox().expand(var1, var1, var1));
    double var3 = Double.MAX_VALUE;
    EntityAnimal var5 = null;
    Iterator var6 = var2.iterator();
    
    while (var6.hasNext())
    {
      EntityAnimal var7 = (EntityAnimal)var6.next();
      
      if ((theAnimal.canMateWith(var7)) && (theAnimal.getDistanceSqToEntity(var7) < var3))
      {
        var5 = var7;
        var3 = theAnimal.getDistanceSqToEntity(var7);
      }
    }
    
    return var5;
  }
  



  private void spawnBaby()
  {
    EntityAgeable var1 = theAnimal.createChild(targetMate);
    
    if (var1 != null)
    {
      EntityPlayer var2 = theAnimal.func_146083_cb();
      
      if ((var2 == null) && (targetMate.func_146083_cb() != null))
      {
        var2 = targetMate.func_146083_cb();
      }
      
      if (var2 != null)
      {
        var2.triggerAchievement(StatList.animalsBredStat);
        
        if ((theAnimal instanceof EntityCow))
        {
          var2.triggerAchievement(AchievementList.breedCow);
        }
      }
      
      theAnimal.setGrowingAge(6000);
      targetMate.setGrowingAge(6000);
      theAnimal.resetInLove();
      targetMate.resetInLove();
      var1.setGrowingAge(41536);
      var1.setLocationAndAngles(theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0F, 0.0F);
      theWorld.spawnEntityInWorld(var1);
      Random var3 = theAnimal.getRNG();
      
      for (int var4 = 0; var4 < 7; var4++)
      {
        double var5 = var3.nextGaussian() * 0.02D;
        double var7 = var3.nextGaussian() * 0.02D;
        double var9 = var3.nextGaussian() * 0.02D;
        theWorld.spawnParticle(EnumParticleTypes.HEART, theAnimal.posX + var3.nextFloat() * theAnimal.width * 2.0F - theAnimal.width, theAnimal.posY + 0.5D + var3.nextFloat() * theAnimal.height, theAnimal.posZ + var3.nextFloat() * theAnimal.width * 2.0F - theAnimal.width, var5, var7, var9, new int[0]);
      }
      
      if (theWorld.getGameRules().getGameRuleBooleanValue("doMobLoot"))
      {
        theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theAnimal.posX, theAnimal.posY, theAnimal.posZ, var3.nextInt(7) + 1));
      }
    }
  }
}
