package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World worldIn) {
      super(worldIn);
      this.isImmuneToFire = true;
   }

   protected EntitySlime createInstance() {
      return new EntityMagmaCube(this.worldObj);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
   }

   public int getTotalArmorValue() {
      return this.getSlimeSize() * 3;
   }

   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
      Item item = this.getDropItem();
      if(item != null && this.getSlimeSize() > 1) {
         int i = this.rand.nextInt(4) - 2;
         if(p_70628_2_ > 0) {
            i += this.rand.nextInt(p_70628_2_ + 1);
         }

         for(int j = 0; j < i; ++j) {
            this.dropItem(item, 1);
         }
      }

   }

   protected void handleJumpLava() {
      this.motionY = (double)(0.22F + (float)this.getSlimeSize() * 0.05F);
      this.isAirBorne = true;
   }

   public float getBrightness(float partialTicks) {
      return 1.0F;
   }

   public int getBrightnessForRender(float partialTicks) {
      return 15728880;
   }

   protected void jump() {
      this.motionY = (double)(0.42F + (float)this.getSlimeSize() * 0.1F);
      this.isAirBorne = true;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public boolean isBurning() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
   }

   protected Item getDropItem() {
      return Items.magma_cream;
   }

   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   protected void alterSquishAmount() {
      this.squishAmount *= 0.9F;
   }

   protected int getAttackStrength() {
      return super.getAttackStrength() + 2;
   }

   protected String getJumpSound() {
      return this.getSlimeSize() > 1?"mob.magmacube.big":"mob.magmacube.small";
   }

   protected int getJumpDelay() {
      return super.getJumpDelay() * 4;
   }

   protected boolean canDamagePlayer() {
      return true;
   }

   protected boolean makesSoundOnLand() {
      return true;
   }

   protected EnumParticleTypes getParticleType() {
      return EnumParticleTypes.FLAME;
   }
}
