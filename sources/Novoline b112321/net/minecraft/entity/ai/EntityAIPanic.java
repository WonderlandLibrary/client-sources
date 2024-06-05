package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIPanic extends EntityAIBase {
   private EntityCreature theEntityCreature;
   protected double speed;
   private double f;
   private double c;
   private double b;

   public EntityAIPanic(EntityCreature var1, double var2) {
      this.theEntityCreature = var1;
      this.speed = var2;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
         return false;
      } else {
         Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
         return false;
      }
   }

   public void startExecuting() {
      this.theEntityCreature.getNavigator().tryMoveToXYZ(this.f, this.c, this.b, this.speed);
   }

   public boolean continueExecuting() {
      return !this.theEntityCreature.getNavigator().noPath();
   }
}
