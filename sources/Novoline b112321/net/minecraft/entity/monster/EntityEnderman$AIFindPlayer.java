package net.minecraft.entity.monster;

import java.util.Collections;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;

class EntityEnderman$AIFindPlayer extends EntityAINearestAttackableTarget {
   private EntityPlayer player;
   private int field_179450_h;
   private int field_179451_i;
   private EntityEnderman enderman;

   public EntityEnderman$AIFindPlayer(EntityEnderman var1) {
      super(var1, EntityPlayer.class, true);
      this.enderman = var1;
   }

   public boolean shouldExecute() {
      double var1 = this.getTargetDistance();
      List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(var1, 4.0D, var1), this.targetEntitySelector);
      Collections.sort(var3, this.theNearestAttackableTargetSorter);
      if(var3.isEmpty()) {
         return false;
      } else {
         this.player = (EntityPlayer)var3.get(0);
         return true;
      }
   }

   public void startExecuting() {
      this.field_179450_h = 5;
      this.field_179451_i = 0;
   }

   public void resetTask() {
      this.player = null;
      this.enderman.setScreaming(false);
      IAttributeInstance var1 = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      var1.removeModifier(EntityEnderman.access$000());
      super.resetTask();
   }

   public boolean continueExecuting() {
      if(this.player != null) {
         if(!EntityEnderman.access$100(this.enderman, this.player)) {
            return false;
         } else {
            EntityEnderman.access$202(this.enderman, true);
            this.enderman.faceEntity(this.player, 10.0F, 10.0F);
            return true;
         }
      } else {
         return super.continueExecuting();
      }
   }

   public void updateTask() {
      if(this.player != null) {
         if(--this.field_179450_h <= 0) {
            this.targetEntity = this.player;
            this.player = null;
            super.startExecuting();
            this.enderman.playSound("mob.endermen.stare", 1.0F, 1.0F);
            this.enderman.setScreaming(true);
            IAttributeInstance var1 = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.applyModifier(EntityEnderman.access$000());
         }
      } else {
         if(this.targetEntity != null) {
            if(this.targetEntity instanceof EntityPlayer && EntityEnderman.access$100(this.enderman, (EntityPlayer)this.targetEntity)) {
               if(this.targetEntity.getDistanceSqToEntity(this.enderman) < 16.0D) {
                  this.enderman.teleportRandomly();
               }

               this.field_179451_i = 0;
            } else if(this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0D && this.field_179451_i++ >= 30 && this.enderman.teleportToEntity(this.targetEntity)) {
               this.field_179451_i = 0;
            }
         }

         super.updateTask();
      }

   }
}
