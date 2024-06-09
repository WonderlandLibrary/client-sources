package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
  EntityTameable theDefendingTameable;
  EntityLivingBase theOwnerAttacker;
  private int field_142051_e;
  private static final String __OBFID = "CL_00001624";
  
  public EntityAIOwnerHurtByTarget(EntityTameable p_i1667_1_)
  {
    super(p_i1667_1_, false);
    theDefendingTameable = p_i1667_1_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if (!theDefendingTameable.isTamed())
    {
      return false;
    }
    

    EntityLivingBase var1 = theDefendingTameable.func_180492_cm();
    
    if (var1 == null)
    {
      return false;
    }
    

    theOwnerAttacker = var1.getAITarget();
    int var2 = var1.getRevengeTimer();
    return (var2 != field_142051_e) && (isSuitableTarget(theOwnerAttacker, false)) && (theDefendingTameable.func_142018_a(theOwnerAttacker, var1));
  }
  





  public void startExecuting()
  {
    taskOwner.setAttackTarget(theOwnerAttacker);
    EntityLivingBase var1 = theDefendingTameable.func_180492_cm();
    
    if (var1 != null)
    {
      field_142051_e = var1.getRevengeTimer();
    }
    
    super.startExecuting();
  }
}
