package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

class EntityBlaze$AIFireballAttack extends EntityAIBase {
   private EntityBlaze blaze;
   private int field_179467_b;
   private int field_179468_c;

   public EntityBlaze$AIFireballAttack(EntityBlaze var1) {
      this.blaze = var1;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.blaze.getAttackTarget();
      return var1.isEntityAlive();
   }

   public void startExecuting() {
      this.field_179467_b = 0;
   }

   public void resetTask() {
      this.blaze.setOnFire(false);
   }

   public void updateTask() {
      --this.field_179468_c;
      EntityLivingBase var1 = this.blaze.getAttackTarget();
      double var2 = this.blaze.getDistanceSqToEntity(var1);
      if(var2 < 4.0D) {
         if(this.field_179468_c <= 0) {
            this.field_179468_c = 20;
            this.blaze.attackEntityAsMob(var1);
         }

         this.blaze.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0D);
      } else if(var2 < 256.0D) {
         double var4 = var1.posX - this.blaze.posX;
         double var6 = var1.getEntityBoundingBox().minY + (double)(var1.height / 2.0F) - (this.blaze.posY + (double)(this.blaze.height / 2.0F));
         double var8 = var1.posZ - this.blaze.posZ;
         if(this.field_179468_c <= 0) {
            ++this.field_179467_b;
            if(this.field_179467_b == 1) {
               this.field_179468_c = 60;
               this.blaze.setOnFire(true);
            } else if(this.field_179467_b <= 4) {
               this.field_179468_c = 6;
            } else {
               this.field_179468_c = 100;
               this.field_179467_b = 0;
               this.blaze.setOnFire(false);
            }

            if(this.field_179467_b > 1) {
               float var10 = MathHelper.sqrt_float(MathHelper.sqrt_double(var2)) * 0.5F;
               this.blaze.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);

               for(int var11 = 0; var11 < 1; ++var11) {
                  EntitySmallFireball var12 = new EntitySmallFireball(this.blaze.worldObj, this.blaze, var4 + this.blaze.getRNG().nextGaussian() * (double)var10, var6, var8 + this.blaze.getRNG().nextGaussian() * (double)var10);
                  var12.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 0.5D;
                  this.blaze.worldObj.spawnEntityInWorld(var12);
               }
            }
         }

         this.blaze.getLookHelper().setLookPositionWithEntity(var1, 10.0F, 10.0F);
      } else {
         this.blaze.getNavigator().clearPathEntity();
         this.blaze.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0D);
      }

      super.updateTask();
   }
}
