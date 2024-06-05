package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIWander extends EntityAIBase {
   private EntityCreature entity;
   private double c;
   private double d;
   private double h;
   private double speed;
   private int executionChance;
   private boolean mustUpdate;

   public EntityAIWander(EntityCreature var1, double var2) {
      this(var1, var2, 120);
   }

   public EntityAIWander(EntityCreature var1, double var2, int var4) {
      this.entity = var1;
      this.speed = var2;
      this.executionChance = var4;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.mustUpdate) {
         if(this.entity.getAge() >= 100) {
            return false;
         }

         if(this.entity.getRNG().nextInt(this.executionChance) != 0) {
            return false;
         }
      }

      Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
      return false;
   }

   public boolean continueExecuting() {
      return !this.entity.getNavigator().noPath();
   }

   public void startExecuting() {
      this.entity.getNavigator().tryMoveToXYZ(this.c, this.d, this.h, this.speed);
   }

   public void makeUpdate() {
      this.mustUpdate = true;
   }

   public void setExecutionChance(int var1) {
      this.executionChance = var1;
   }
}
