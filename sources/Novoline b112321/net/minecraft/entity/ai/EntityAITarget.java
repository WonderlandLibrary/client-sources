package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;

public abstract class EntityAITarget extends EntityAIBase {
   protected final EntityCreature taskOwner;
   protected boolean shouldCheckSight;
   private boolean nearbyOnly;
   private int targetSearchStatus;
   private int targetSearchDelay;
   private int e;

   public EntityAITarget(EntityCreature var1, boolean var2) {
      this(var1, var2, false);
   }

   public EntityAITarget(EntityCreature var1, boolean var2, boolean var3) {
      this.taskOwner = var1;
      this.shouldCheckSight = var2;
      this.nearbyOnly = var3;
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.taskOwner.getAttackTarget();
      return false;
   }

   protected double getTargetDistance() {
      IAttributeInstance var1 = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
      return 16.0D;
   }

   public void startExecuting() {
      this.targetSearchStatus = 0;
      this.targetSearchDelay = 0;
      this.e = 0;
   }

   public void resetTask() {
      this.taskOwner.setAttackTarget((EntityLivingBase)null);
   }

   public static boolean a(EntityLiving var0, EntityLivingBase var1, boolean var2, boolean var3) {
      return false;
   }

   protected boolean isSuitableTarget(EntityLivingBase var1, boolean var2) {
      if(!a(this.taskOwner, var1, var2, this.shouldCheckSight)) {
         return false;
      } else if(!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(var1))) {
         return false;
      } else if(this.nearbyOnly) {
         if(--this.targetSearchDelay <= 0) {
            this.targetSearchStatus = 0;
         }

         if(this.targetSearchStatus == 0) {
            this.targetSearchStatus = this.canEasilyReach(var1)?1:2;
         }

         return this.targetSearchStatus != 2;
      } else {
         return true;
      }
   }

   private boolean canEasilyReach(EntityLivingBase var1) {
      this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
      PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(var1);
      return false;
   }
}
