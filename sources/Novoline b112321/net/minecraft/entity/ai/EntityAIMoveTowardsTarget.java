package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
   private EntityCreature theEntity;
   private EntityLivingBase targetEntity;
   private double c;
   private double e;
   private double d;
   private double speed;
   private float maxTargetDistance;

   public EntityAIMoveTowardsTarget(EntityCreature var1, double var2, float var4) {
      this.theEntity = var1;
      this.speed = var2;
      this.maxTargetDistance = var4;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      this.targetEntity = this.theEntity.getAttackTarget();
      if(this.targetEntity == null) {
         return false;
      } else if(this.targetEntity.getDistanceSqToEntity(this.theEntity) > (double)(this.maxTargetDistance * this.maxTargetDistance)) {
         return false;
      } else {
         Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.theEntity.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity(this.theEntity) < (double)(this.maxTargetDistance * this.maxTargetDistance);
   }

   public void resetTask() {
      this.targetEntity = null;
   }

   public void startExecuting() {
      this.theEntity.getNavigator().tryMoveToXYZ(this.c, this.e, this.d, this.speed);
   }
}
