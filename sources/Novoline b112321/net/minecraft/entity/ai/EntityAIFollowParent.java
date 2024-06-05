package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   EntityAnimal childAnimal;
   EntityAnimal b;
   double moveSpeed;
   private int delayCounter;

   public EntityAIFollowParent(EntityAnimal var1, double var2) {
      this.childAnimal = var1;
      this.moveSpeed = var2;
   }

   public boolean shouldExecute() {
      if(this.childAnimal.getGrowingAge() >= 0) {
         return false;
      } else {
         List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
         Object var2 = null;
         double var3 = Double.MAX_VALUE;

         for(EntityAnimal var6 : var1) {
            if(var6.getGrowingAge() >= 0) {
               double var7 = this.childAnimal.getDistanceSqToEntity(var6);
               if(var7 <= var3) {
                  var3 = var7;
               }
            }
         }

         return false;
      }
   }

   public boolean continueExecuting() {
      if(this.childAnimal.getGrowingAge() >= 0) {
         return false;
      } else if(!this.b.isEntityAlive()) {
         return false;
      } else {
         double var1 = this.childAnimal.getDistanceSqToEntity(this.b);
         return var1 >= 9.0D && var1 <= 256.0D;
      }
   }

   public void startExecuting() {
      this.delayCounter = 0;
   }

   public void resetTask() {
      this.b = null;
   }

   public void updateTask() {
      if(--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.b, this.moveSpeed);
      }

   }
}
