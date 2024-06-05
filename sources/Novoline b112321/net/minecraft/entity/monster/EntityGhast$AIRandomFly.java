package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGhast;

class EntityGhast$AIRandomFly extends EntityAIBase {
   private EntityGhast parentEntity;

   public EntityGhast$AIRandomFly(EntityGhast var1) {
      this.parentEntity = var1;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      EntityMoveHelper var1 = this.parentEntity.getMoveHelper();
      if(!var1.isUpdating()) {
         return true;
      } else {
         double var2 = var1.getX() - this.parentEntity.posX;
         double var4 = var1.getY() - this.parentEntity.posY;
         double var6 = var1.getZ() - this.parentEntity.posZ;
         double var8 = var2 * var2 + var4 * var4 + var6 * var6;
         return var8 < 1.0D || var8 > 3600.0D;
      }
   }

   public boolean continueExecuting() {
      return false;
   }

   public void startExecuting() {
      Random var1 = this.parentEntity.getRNG();
      double var2 = this.parentEntity.posX + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
      double var4 = this.parentEntity.posY + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
      double var6 = this.parentEntity.posZ + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
      this.parentEntity.getMoveHelper().setMoveTo(var2, var4, var6, 1.0D);
   }
}
