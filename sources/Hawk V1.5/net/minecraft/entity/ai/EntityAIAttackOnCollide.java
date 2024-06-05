package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
   private int field_75445_i;
   PathEntity entityPathEntity;
   protected EntityCreature attacker;
   private double field_151495_j;
   double speedTowardsTarget;
   private static final String __OBFID = "CL_00001595";
   private double field_151497_i;
   int attackTick;
   World worldObj;
   Class classTarget;
   private double field_151496_k;
   boolean longMemory;

   public void updateTask() {
      EntityLivingBase var1 = this.attacker.getAttackTarget();
      this.attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
      double var2 = this.attacker.getDistanceSq(var1.posX, var1.getEntityBoundingBox().minY, var1.posZ);
      double var4 = this.func_179512_a(var1);
      --this.field_75445_i;
      if ((this.longMemory || this.attacker.getEntitySenses().canSee(var1)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || var1.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
         this.field_151497_i = var1.posX;
         this.field_151495_j = var1.getEntityBoundingBox().minY;
         this.field_151496_k = var1.posZ;
         this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
         if (var2 > 1024.0D) {
            this.field_75445_i += 10;
         } else if (var2 > 256.0D) {
            this.field_75445_i += 5;
         }

         if (!this.attacker.getNavigator().tryMoveToEntityLiving(var1, this.speedTowardsTarget)) {
            this.field_75445_i += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (var2 <= var4 && this.attackTick <= 0) {
         this.attackTick = 20;
         if (this.attacker.getHeldItem() != null) {
            this.attacker.swingItem();
         }

         this.attacker.attackEntityAsMob(var1);
      }

   }

   public void resetTask() {
      this.attacker.getNavigator().clearPathEntity();
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.attacker.getAttackTarget();
      return var1 == null ? false : (!var1.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.func_180485_d(new BlockPos(var1))));
   }

   public EntityAIAttackOnCollide(EntityCreature var1, double var2, boolean var4) {
      this.attacker = var1;
      this.worldObj = var1.worldObj;
      this.speedTowardsTarget = var2;
      this.longMemory = var4;
      this.setMutexBits(3);
   }

   public void startExecuting() {
      this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
      this.field_75445_i = 0;
   }

   public EntityAIAttackOnCollide(EntityCreature var1, Class var2, double var3, boolean var5) {
      this(var1, var3, var5);
      this.classTarget = var2;
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.attacker.getAttackTarget();
      if (var1 == null) {
         return false;
      } else if (!var1.isEntityAlive()) {
         return false;
      } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(var1);
         return this.entityPathEntity != null;
      }
   }

   protected double func_179512_a(EntityLivingBase var1) {
      return (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + var1.width);
   }
}
