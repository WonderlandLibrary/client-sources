package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase {
   private EntityHorse horseHost;
   private double speed;
   private double c;
   private double e;
   private double f;

   public EntityAIRunAroundLikeCrazy(EntityHorse var1, double var2) {
      this.horseHost = var1;
      this.speed = var2;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.horseHost.isTame() && this.horseHost.riddenByEntity != null) {
         Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
         return false;
      } else {
         return false;
      }
   }

   public void startExecuting() {
      this.horseHost.getNavigator().tryMoveToXYZ(this.c, this.e, this.f, this.speed);
   }

   public boolean continueExecuting() {
      return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
   }

   public void updateTask() {
      if(this.horseHost.getRNG().nextInt(50) == 0) {
         if(this.horseHost.riddenByEntity instanceof EntityPlayer) {
            int var1 = this.horseHost.getTemper();
            int var2 = this.horseHost.getMaxTemper();
            if(this.horseHost.getRNG().nextInt(var2) < var1) {
               this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
               this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
               return;
            }

            this.horseHost.increaseTemper(5);
         }

         this.horseHost.riddenByEntity.mountEntity((Entity)null);
         this.horseHost.riddenByEntity = null;
         this.horseHost.makeHorseRearWithSound();
         this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
      }

   }
}
