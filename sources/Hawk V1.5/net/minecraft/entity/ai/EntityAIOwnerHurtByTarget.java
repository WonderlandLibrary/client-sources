package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   EntityLivingBase theOwnerAttacker;
   private static final String __OBFID = "CL_00001624";
   EntityTameable theDefendingTameable;
   private int field_142051_e;

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityLivingBase var1 = this.theDefendingTameable.func_180492_cm();
      if (var1 != null) {
         this.field_142051_e = var1.getRevengeTimer();
      }

      super.startExecuting();
   }

   public boolean shouldExecute() {
      if (!this.theDefendingTameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase var1 = this.theDefendingTameable.func_180492_cm();
         if (var1 == null) {
            return false;
         } else {
            this.theOwnerAttacker = var1.getAITarget();
            int var2 = var1.getRevengeTimer();
            return var2 != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.func_142018_a(this.theOwnerAttacker, var1);
         }
      }
   }

   public EntityAIOwnerHurtByTarget(EntityTameable var1) {
      super(var1, false);
      this.theDefendingTameable = var1;
      this.setMutexBits(1);
   }
}
