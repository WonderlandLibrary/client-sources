package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;

public class EntityAIPlay extends EntityAIBase {
   private EntityVillager villagerObj;
   private EntityLivingBase targetVillager;
   private double speed;
   private int playTime;

   public EntityAIPlay(EntityVillager var1, double var2) {
      this.villagerObj = var1;
      this.speed = var2;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(this.villagerObj.getGrowingAge() >= 0) {
         return false;
      } else if(this.villagerObj.getRNG().nextInt(400) != 0) {
         return false;
      } else {
         List var1 = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
         double var2 = Double.MAX_VALUE;

         for(EntityVillager var5 : var1) {
            if(var5 != this.villagerObj && !var5.isPlaying() && var5.getGrowingAge() < 0) {
               double var6 = var5.getDistanceSqToEntity(this.villagerObj);
               if(var6 <= var2) {
                  var2 = var6;
                  this.targetVillager = var5;
               }
            }
         }

         if(this.targetVillager == null) {
            Vec3 var8 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            return true;
         } else {
            return true;
         }
      }
   }

   public boolean continueExecuting() {
      return this.playTime > 0;
   }

   public void startExecuting() {
      if(this.targetVillager != null) {
         this.villagerObj.setPlaying(true);
      }

      this.playTime = 1000;
   }

   public void resetTask() {
      this.villagerObj.setPlaying(false);
      this.targetVillager = null;
   }

   public void updateTask() {
      --this.playTime;
      if(this.targetVillager != null) {
         if(this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0D) {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
         }
      } else if(this.villagerObj.getNavigator().noPath()) {
         Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
         return;
      }

   }
}
