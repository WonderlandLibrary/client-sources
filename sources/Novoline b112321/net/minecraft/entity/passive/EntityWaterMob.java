package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals {
   public EntityWaterMob(World var1) {
      super(var1);
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this);
   }

   public int getTalkInterval() {
      return 120;
   }

   protected boolean canDespawn() {
      return true;
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      return 1 + this.worldObj.rand.nextInt(3);
   }

   public void onEntityUpdate() {
      int var1 = this.getAir();
      super.onEntityUpdate();
      if(this.isEntityAlive() && !this.isInWater()) {
         --var1;
         this.setAir(var1);
         if(this.getAir() == -20) {
            this.setAir(0);
            this.attackEntityFrom(DamageSource.drown, 2.0F);
         }
      } else {
         this.setAir(300);
      }

   }

   public boolean isPushedByWater() {
      return false;
   }
}
