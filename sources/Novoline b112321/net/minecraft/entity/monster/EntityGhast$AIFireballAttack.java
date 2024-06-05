package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class EntityGhast$AIFireballAttack extends EntityAIBase {
   private EntityGhast parentEntity;
   public int attackTimer;

   public EntityGhast$AIFireballAttack(EntityGhast var1) {
      this.parentEntity = var1;
   }

   public boolean shouldExecute() {
      return this.parentEntity.getAttackTarget() != null;
   }

   public void startExecuting() {
      this.attackTimer = 0;
   }

   public void resetTask() {
      this.parentEntity.setAttacking(false);
   }

   public void updateTask() {
      EntityLivingBase var1 = this.parentEntity.getAttackTarget();
      double var2 = 64.0D;
      if(var1.getDistanceSqToEntity(this.parentEntity) < var2 * var2 && this.parentEntity.canEntityBeSeen(var1)) {
         World var4 = this.parentEntity.worldObj;
         ++this.attackTimer;
         if(this.attackTimer == 10) {
            var4.playAuxSFXAtEntity((EntityPlayer)null, 1007, new BlockPos(this.parentEntity), 0);
         }

         if(this.attackTimer == 20) {
            double var5 = 4.0D;
            Vec3 var7 = this.parentEntity.getLook(1.0F);
            double var8 = var1.posX - (this.parentEntity.posX + var7.xCoord * var5);
            double var10 = var1.getEntityBoundingBox().minY + (double)(var1.height / 2.0F) - (0.5D + this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F));
            double var12 = var1.posZ - (this.parentEntity.posZ + var7.zCoord * var5);
            var4.playAuxSFXAtEntity((EntityPlayer)null, 1008, new BlockPos(this.parentEntity), 0);
            EntityLargeFireball var14 = new EntityLargeFireball(var4, this.parentEntity, var8, var10, var12);
            var14.explosionPower = this.parentEntity.getFireballStrength();
            var14.posX = this.parentEntity.posX + var7.xCoord * var5;
            var14.posY = this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F) + 0.5D;
            var14.posZ = this.parentEntity.posZ + var7.zCoord * var5;
            var4.spawnEntityInWorld(var14);
            this.attackTimer = -40;
         }
      } else if(this.attackTimer > 0) {
         --this.attackTimer;
      }

      this.parentEntity.setAttacking(this.attackTimer > 10);
   }
}
