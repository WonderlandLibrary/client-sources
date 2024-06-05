package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {
   EntityTameable theEntityTameable;
   EntityLivingBase h;
   private int j;

   public EntityAIOwnerHurtTarget(EntityTameable var1) {
      super(var1, false);
      this.theEntityTameable = var1;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.theEntityTameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase var1 = this.theEntityTameable.a();
         return false;
      }
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.h);
      EntityLivingBase var1 = this.theEntityTameable.a();
      this.j = var1.getLastAttackerTime();
      super.startExecuting();
   }
}
