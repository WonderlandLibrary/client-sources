package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget
  extends EntityAITarget
{
  EntityTameable theEntityTameable;
  EntityLivingBase theTarget;
  private int field_142050_e;
  private static final String __OBFID = "CL_00001625";
  
  public EntityAIOwnerHurtTarget(EntityTameable p_i1668_1_)
  {
    super(p_i1668_1_, false);
    this.theEntityTameable = p_i1668_1_;
    setMutexBits(1);
  }
  
  public boolean shouldExecute()
  {
    if (!this.theEntityTameable.isTamed()) {
      return false;
    }
    EntityLivingBase var1 = this.theEntityTameable.func_180492_cm();
    if (var1 == null) {
      return false;
    }
    this.theTarget = var1.getLastAttacker();
    int var2 = var1.getLastAttackerTime();
    return (var2 != this.field_142050_e) && (isSuitableTarget(this.theTarget, false)) && (this.theEntityTameable.func_142018_a(this.theTarget, var1));
  }
  
  public void startExecuting()
  {
    this.taskOwner.setAttackTarget(this.theTarget);
    EntityLivingBase var1 = this.theEntityTameable.func_180492_cm();
    if (var1 != null) {
      this.field_142050_e = var1.getLastAttackerTime();
    }
    super.startExecuting();
  }
}
