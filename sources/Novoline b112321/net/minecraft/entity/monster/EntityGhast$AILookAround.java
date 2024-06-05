package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.MathHelper;

class EntityGhast$AILookAround extends EntityAIBase {
   private EntityGhast parentEntity;

   public EntityGhast$AILookAround(EntityGhast var1) {
      this.parentEntity = var1;
      this.setMutexBits(2);
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      if(this.parentEntity.getAttackTarget() == null) {
         this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.func_181159_b(this.parentEntity.motionX, this.parentEntity.motionZ)) * 180.0F / 3.1415927F;
      } else {
         EntityLivingBase var1 = this.parentEntity.getAttackTarget();
         double var2 = 64.0D;
         if(var1.getDistanceSqToEntity(this.parentEntity) < var2 * var2) {
            double var4 = var1.posX - this.parentEntity.posX;
            double var6 = var1.posZ - this.parentEntity.posZ;
            this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.func_181159_b(var4, var6)) * 180.0F / 3.1415927F;
         }
      }

   }
}
