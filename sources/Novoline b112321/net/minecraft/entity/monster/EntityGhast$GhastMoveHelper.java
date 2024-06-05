package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

class EntityGhast$GhastMoveHelper extends EntityMoveHelper {
   private EntityGhast parentEntity;
   private int courseChangeCooldown;

   public EntityGhast$GhastMoveHelper(EntityGhast var1) {
      super(var1);
      this.parentEntity = var1;
   }

   public void onUpdateMoveHelper() {
      if(this.update) {
         double var1 = this.posX - this.parentEntity.posX;
         double var3 = this.posY - this.parentEntity.posY;
         double var5 = this.posZ - this.parentEntity.posZ;
         double var7 = var1 * var1 + var3 * var3 + var5 * var5;
         if(this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
            var7 = (double)MathHelper.sqrt_double(var7);
            if(this.isNotColliding(this.posX, this.posY, this.posZ, var7)) {
               this.parentEntity.motionX += var1 / var7 * 0.1D;
               this.parentEntity.motionY += var3 / var7 * 0.1D;
               this.parentEntity.motionZ += var5 / var7 * 0.1D;
            } else {
               this.update = false;
            }
         }
      }

   }

   private boolean isNotColliding(double var1, double var3, double var5, double var7) {
      double var9 = (var1 - this.parentEntity.posX) / var7;
      double var11 = (var3 - this.parentEntity.posY) / var7;
      double var13 = (var5 - this.parentEntity.posZ) / var7;
      AxisAlignedBB var15 = this.parentEntity.getEntityBoundingBox();

      for(int var16 = 1; (double)var16 < var7; ++var16) {
         var15 = var15.offset(var9, var11, var13);
         if(!this.parentEntity.worldObj.getCollidingBoundingBoxes(this.parentEntity, var15).isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
