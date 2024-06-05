package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIHurtByTarget extends EntityAITarget {
   private boolean entityCallsForHelp;
   private int revengeTimerOld;
   private final Class[] targetClasses;

   public EntityAIHurtByTarget(EntityCreature var1, boolean var2, Class... var3) {
      super(var1, false);
      this.entityCallsForHelp = var2;
      this.targetClasses = var3;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      int var1 = this.taskOwner.getRevengeTimer();
      return var1 != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
      this.revengeTimerOld = this.taskOwner.getRevengeTimer();
      if(this.entityCallsForHelp) {
         double var1 = this.getTargetDistance();

         for(EntityCreature var4 : this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).expand(var1, 10.0D, var1))) {
            if(this.taskOwner != var4 && var4.getAttackTarget() == null && !var4.isOnSameTeam(this.taskOwner.getAITarget())) {
               boolean var5 = false;

               for(Class var9 : this.targetClasses) {
                  if(var4.getClass() == var9) {
                     var5 = true;
                     break;
                  }
               }

               this.setEntityAttackTarget(var4, this.taskOwner.getAITarget());
            }
         }
      }

      super.startExecuting();
   }

   protected void setEntityAttackTarget(EntityCreature var1, EntityLivingBase var2) {
      var1.setAttackTarget(var2);
   }
}
