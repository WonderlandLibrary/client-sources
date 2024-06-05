package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
   World worldObj;
   protected EntityCreature attacker;
   int attackTick;
   double speedTowardsTarget;
   boolean longMemory;
   PathEntity l;
   Class k;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;

   public EntityAIAttackOnCollide(EntityCreature var1, Class var2, double var3, boolean var5) {
      this(var1, var3, var5);
      this.k = var2;
   }

   public EntityAIAttackOnCollide(EntityCreature var1, double var2, boolean var4) {
      this.attacker = var1;
      this.worldObj = var1.worldObj;
      this.speedTowardsTarget = var2;
      this.longMemory = var4;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.attacker.getAttackTarget();
      return false;
   }

   public boolean continueExecuting() {
      boolean var10000;
      label0: {
         EntityLivingBase var1 = this.attacker.getAttackTarget();
         if(var1.isEntityAlive()) {
            if(!this.longMemory) {
               if(!this.attacker.getNavigator().noPath()) {
                  break label0;
               }
            } else if(this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(var1))) {
               break label0;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   public void startExecuting() {
      this.attacker.getNavigator().a(this.l, this.speedTowardsTarget);
      this.delayCounter = 0;
   }

   public void resetTask() {
      this.attacker.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      EntityLivingBase var1 = this.attacker.getAttackTarget();
      this.attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
      double var2 = this.attacker.getDistanceSq(var1.posX, var1.getEntityBoundingBox().minY, var1.posZ);
      double var4 = this.func_179512_a(var1);
      --this.delayCounter;
      if((this.longMemory || this.attacker.getEntitySenses().canSee(var1)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || var1.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
         this.targetX = var1.posX;
         this.targetY = var1.getEntityBoundingBox().minY;
         this.targetZ = var1.posZ;
         this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
         if(var2 > 1024.0D) {
            this.delayCounter += 10;
         } else if(var2 > 256.0D) {
            this.delayCounter += 5;
         }

         if(!this.attacker.getNavigator().tryMoveToEntityLiving(var1, this.speedTowardsTarget)) {
            this.delayCounter += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      if(var2 <= var4 && this.attackTick <= 0) {
         this.attackTick = 20;
         if(this.attacker.getHeldItem() != null) {
            this.attacker.swingItem();
         }

         this.attacker.attackEntityAsMob(var1);
      }

   }

   protected double func_179512_a(EntityLivingBase var1) {
      return (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + var1.width);
   }
}
