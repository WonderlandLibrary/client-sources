package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;


public class EntityAIDefendVillage
  extends EntityAITarget
{
  EntityIronGolem irongolem;
  EntityLivingBase villageAgressorTarget;
  private static final String __OBFID = "CL_00001618";
  
  public EntityAIDefendVillage(EntityIronGolem p_i1659_1_)
  {
    super(p_i1659_1_, false, true);
    irongolem = p_i1659_1_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    Village var1 = irongolem.getVillage();
    
    if (var1 == null)
    {
      return false;
    }
    

    villageAgressorTarget = var1.findNearestVillageAggressor(irongolem);
    
    if (!isSuitableTarget(villageAgressorTarget, false))
    {
      if (taskOwner.getRNG().nextInt(20) == 0)
      {
        villageAgressorTarget = var1.func_82685_c(irongolem);
        return isSuitableTarget(villageAgressorTarget, false);
      }
      

      return false;
    }
    


    return true;
  }
  





  public void startExecuting()
  {
    irongolem.setAttackTarget(villageAgressorTarget);
    super.startExecuting();
  }
}
