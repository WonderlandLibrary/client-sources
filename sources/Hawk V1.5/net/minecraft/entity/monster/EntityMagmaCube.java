package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {
   private static final String __OBFID = "CL_00001691";

   protected EnumParticleTypes func_180487_n() {
      return EnumParticleTypes.FLAME;
   }

   protected void alterSquishAmount() {
      this.squishAmount *= 0.9F;
   }

   protected int getAttackStrength() {
      return super.getAttackStrength() + 2;
   }

   protected void dropFewItems(boolean var1, int var2) {
      Item var3 = this.getDropItem();
      if (var3 != null && this.getSlimeSize() > 1) {
         int var4 = this.rand.nextInt(4) - 2;
         if (var2 > 0) {
            var4 += this.rand.nextInt(var2 + 1);
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            this.dropItem(var3, 1);
         }
      }

   }

   public void fall(float var1, float var2) {
   }

   public boolean getCanSpawnHere() {
      return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
   }

   protected boolean makesSoundOnLand() {
      return true;
   }

   protected EntitySlime createInstance() {
      return new EntityMagmaCube(this.worldObj);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
   }

   public int getBrightnessForRender(float var1) {
      return 15728880;
   }

   public boolean isBurning() {
      return false;
   }

   public float getBrightness(float var1) {
      return 1.0F;
   }

   protected String getJumpSound() {
      return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
   }

   protected void func_180466_bG() {
      this.motionY = (double)(0.22F + (float)this.getSlimeSize() * 0.05F);
      this.isAirBorne = true;
   }

   protected void jump() {
      this.motionY = (double)(0.42F + (float)this.getSlimeSize() * 0.1F);
      this.isAirBorne = true;
   }

   public boolean handleLavaMovement() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   public int getTotalArmorValue() {
      return this.getSlimeSize() * 3;
   }

   protected boolean canDamagePlayer() {
      return true;
   }

   public EntityMagmaCube(World var1) {
      super(var1);
      this.isImmuneToFire = true;
   }

   protected int getJumpDelay() {
      return super.getJumpDelay() * 4;
   }

   protected Item getDropItem() {
      return Items.magma_cream;
   }
}
