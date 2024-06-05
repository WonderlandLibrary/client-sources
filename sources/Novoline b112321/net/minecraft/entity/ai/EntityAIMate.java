package net.minecraft.entity.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAIMate extends EntityAIBase {
   private EntityAnimal theAnimal;
   World theWorld;
   private EntityAnimal targetMate;
   int spawnBabyDelay;
   double moveSpeed;

   public EntityAIMate(EntityAnimal var1, double var2) {
      this.theAnimal = var1;
      this.theWorld = var1.worldObj;
      this.moveSpeed = var2;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      if(!this.theAnimal.isInLove()) {
         return false;
      } else {
         this.targetMate = this.getNearbyMate();
         return this.targetMate != null;
      }
   }

   public boolean continueExecuting() {
      return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
   }

   public void resetTask() {
      this.targetMate = null;
      this.spawnBabyDelay = 0;
   }

   public void updateTask() {
      this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, (float)this.theAnimal.getVerticalFaceSpeed());
      this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
      ++this.spawnBabyDelay;
      if(this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0D) {
         this.spawnBaby();
      }

   }

   private EntityAnimal getNearbyMate() {
      float var1 = 8.0F;
      List var2 = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand((double)var1, (double)var1, (double)var1));
      double var3 = Double.MAX_VALUE;
      EntityAnimal var5 = null;

      for(EntityAnimal var7 : var2) {
         if(this.theAnimal.canMateWith(var7) && this.theAnimal.getDistanceSqToEntity(var7) < var3) {
            var5 = var7;
            var3 = this.theAnimal.getDistanceSqToEntity(var7);
         }
      }

      return var5;
   }

   private void spawnBaby() {
      EntityAgeable var1 = this.theAnimal.createChild(this.targetMate);
      EntityPlayer var2 = this.theAnimal.getPlayerInLove();
      if(this.targetMate.getPlayerInLove() != null) {
         var2 = this.targetMate.getPlayerInLove();
      }

      var2.triggerAchievement(StatList.animalsBredStat);
      if(this.theAnimal instanceof EntityCow) {
         var2.triggerAchievement(AchievementList.breedCow);
      }

      this.theAnimal.setGrowingAge(6000);
      this.targetMate.setGrowingAge(6000);
      this.theAnimal.resetInLove();
      this.targetMate.resetInLove();
      var1.setGrowingAge(-24000);
      var1.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
      this.theWorld.spawnEntityInWorld(var1);
      Random var3 = this.theAnimal.getRNG();

      for(int var4 = 0; var4 < 7; ++var4) {
         double var5 = var3.nextGaussian() * 0.02D;
         double var7 = var3.nextGaussian() * 0.02D;
         double var9 = var3.nextGaussian() * 0.02D;
         double var11 = var3.nextDouble() * (double)this.theAnimal.width * 2.0D - (double)this.theAnimal.width;
         double var13 = 0.5D + var3.nextDouble() * (double)this.theAnimal.height;
         double var15 = var3.nextDouble() * (double)this.theAnimal.width * 2.0D - (double)this.theAnimal.width;
         this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + var11, this.theAnimal.posY + var13, this.theAnimal.posZ + var15, var5, var7, var9, new int[0]);
      }

      if(this.theWorld.getGameRules().getBoolean("doMobLoot")) {
         this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, var3.nextInt(7) + 1));
      }

   }
}
