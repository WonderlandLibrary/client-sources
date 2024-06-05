package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIVillagerMate extends EntityAIBase {
   private EntityVillager villagerObj;
   private EntityVillager c;
   private World worldObj;
   private int matingTimeout;
   Village villageObj;

   public EntityAIVillagerMate(EntityVillager var1) {
      this.villagerObj = var1;
      this.worldObj = var1.worldObj;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      if(this.villagerObj.getGrowingAge() != 0) {
         return false;
      } else if(this.villagerObj.getRNG().nextInt(500) != 0) {
         return false;
      } else {
         this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.villagerObj), 0);
         if(this.villageObj == null) {
            return false;
         } else if(this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getIsWillingToMate(true)) {
            Entity var1 = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), this.villagerObj);
            return false;
         } else {
            return false;
         }
      }
   }

   public void startExecuting() {
      this.matingTimeout = 300;
      this.villagerObj.setMating(true);
   }

   public void resetTask() {
      this.villageObj = null;
      this.c = null;
      this.villagerObj.setMating(false);
   }

   public boolean continueExecuting() {
      return this.matingTimeout >= 0 && this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate(false);
   }

   public void updateTask() {
      --this.matingTimeout;
      this.villagerObj.getLookHelper().setLookPositionWithEntity(this.c, 10.0F, 30.0F);
      if(this.villagerObj.getDistanceSqToEntity(this.c) > 2.25D) {
         this.villagerObj.getNavigator().tryMoveToEntityLiving(this.c, 0.25D);
      } else if(this.matingTimeout == 0 && this.c.isMating()) {
         this.giveBirth();
      }

      if(this.villagerObj.getRNG().nextInt(35) == 0) {
         this.worldObj.setEntityState(this.villagerObj, (byte)12);
      }

   }

   private boolean checkSufficientDoorsPresentForNewVillager() {
      if(!this.villageObj.isMatingSeason()) {
         return false;
      } else {
         int var1 = (int)((double)((float)this.villageObj.getNumVillageDoors()) * 0.35D);
         return this.villageObj.getNumVillagers() < var1;
      }
   }

   private void giveBirth() {
      EntityVillager var1 = this.villagerObj.createChild(this.c);
      this.c.setGrowingAge(6000);
      this.villagerObj.setGrowingAge(6000);
      this.c.setIsWillingToMate(false);
      this.villagerObj.setIsWillingToMate(false);
      var1.setGrowingAge(-24000);
      var1.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
      this.worldObj.spawnEntityInWorld(var1);
      this.worldObj.setEntityState(var1, (byte)12);
   }
}
