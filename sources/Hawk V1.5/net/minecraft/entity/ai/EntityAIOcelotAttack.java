package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase {
   int attackCountdown;
   private static final String __OBFID = "CL_00001600";
   World theWorld;
   EntityLivingBase theVictim;
   EntityLiving theEntity;

   public boolean continueExecuting() {
      return !this.theVictim.isEntityAlive() ? false : (this.theEntity.getDistanceSqToEntity(this.theVictim) > 225.0D ? false : !this.theEntity.getNavigator().noPath() || this.shouldExecute());
   }

   public void updateTask() {
      this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0F, 30.0F);
      double var1 = (double)(this.theEntity.width * 2.0F * this.theEntity.width * 2.0F);
      double var3 = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
      double var5 = 0.8D;
      if (var3 > var1 && var3 < 16.0D) {
         var5 = 1.33D;
      } else if (var3 < 225.0D) {
         var5 = 0.6D;
      }

      this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, var5);
      this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
      if (var3 <= var1 && this.attackCountdown <= 0) {
         this.attackCountdown = 20;
         this.theEntity.attackEntityAsMob(this.theVictim);
      }

   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.theEntity.getAttackTarget();
      if (var1 == null) {
         return false;
      } else {
         this.theVictim = var1;
         return true;
      }
   }

   public EntityAIOcelotAttack(EntityLiving var1) {
      this.theEntity = var1;
      this.theWorld = var1.worldObj;
      this.setMutexBits(3);
   }

   public void resetTask() {
      this.theVictim = null;
      this.theEntity.getNavigator().clearPathEntity();
   }
}
