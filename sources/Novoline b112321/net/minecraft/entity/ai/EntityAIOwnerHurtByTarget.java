package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   EntityTameable theDefendingTameable;
   EntityLivingBase i;
   private int j;

   public EntityAIOwnerHurtByTarget(EntityTameable var1) {
      super(var1, false);
      this.theDefendingTameable = var1;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.theDefendingTameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase var1 = this.theDefendingTameable.a();
         return false;
      }
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.i);
      EntityLivingBase var1 = this.theDefendingTameable.a();
      this.j = var1.getRevengeTimer();
      super.startExecuting();
   }
}
