package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {
   public EntityWitherSkull(World var1) {
      super(var1);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntityWitherSkull(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1, var2, var3, var5, var7);
      this.setSize(0.3125F, 0.3125F);
   }

   protected float getMotionFactor() {
      return this.isInvulnerable()?0.73F:super.getMotionFactor();
   }

   public EntityWitherSkull(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.setSize(0.3125F, 0.3125F);
   }

   public boolean isBurning() {
      return false;
   }

   public float getExplosionResistance(Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      float var5 = super.getExplosionResistance(var1, var2, var3, var4);
      Block var6 = var4.getBlock();
      if(this.isInvulnerable() && EntityWither.func_181033_a(var6)) {
         var5 = Math.min(0.8F, var5);
      }

      return var5;
   }

   protected void onImpact(MovingObjectPosition var1) {
      if(!this.worldObj.isRemote) {
         if(var1.entityHit != null) {
            if(this.shootingEntity != null) {
               if(var1.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F)) {
                  if(!var1.entityHit.isEntityAlive()) {
                     this.shootingEntity.heal(5.0F);
                  } else {
                     this.applyEnchantments(this.shootingEntity, var1.entityHit);
                  }
               }
            } else {
               var1.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
            }

            if(var1.entityHit instanceof EntityLivingBase) {
               byte var2 = 0;
               if(this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
                  var2 = 10;
               } else if(this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var2 = 40;
               }

               ((EntityLivingBase)var1.entityHit).addPotionEffect(new PotionEffect(Potion.wither.getId(), 20 * var2, 1));
            }
         }

         this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
         this.setDead();
      }

   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return false;
   }

   protected void entityInit() {
      this.I.b(10, Byte.valueOf((byte)0));
   }

   public boolean isInvulnerable() {
      return this.I.g(10) == 1;
   }

   public void setInvulnerable(boolean var1) {
      this.I.a(10, Byte.valueOf((byte)1));
   }
}
