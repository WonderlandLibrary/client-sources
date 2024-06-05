package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase {
   World theWorld;
   EntityLiving theEntity;
   EntityLivingBase b;
   int attackCountdown;

   public EntityAIOcelotAttack(EntityLiving var1) {
      this.theEntity = var1;
      this.theWorld = var1.worldObj;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.theEntity.getAttackTarget();
      return false;
   }

   public boolean continueExecuting() {
      return this.b.isEntityAlive() && this.theEntity.getDistanceSqToEntity(this.b) <= 225.0D && (!this.theEntity.getNavigator().noPath() || this.shouldExecute());
   }

   public void resetTask() {
      this.b = null;
      this.theEntity.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      this.theEntity.getLookHelper().setLookPositionWithEntity(this.b, 30.0F, 30.0F);
      double var1 = (double)(this.theEntity.width * 2.0F * this.theEntity.width * 2.0F);
      double var3 = this.theEntity.getDistanceSq(this.b.posX, this.b.getEntityBoundingBox().minY, this.b.posZ);
      double var5 = 0.8D;
      if(var3 > var1 && var3 < 16.0D) {
         var5 = 1.33D;
      } else if(var3 < 225.0D) {
         var5 = 0.6D;
      }

      this.theEntity.getNavigator().tryMoveToEntityLiving(this.b, var5);
      this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
      if(var3 <= var1 && this.attackCountdown <= 0) {
         this.attackCountdown = 20;
         this.theEntity.attackEntityAsMob(this.b);
      }

   }
}
