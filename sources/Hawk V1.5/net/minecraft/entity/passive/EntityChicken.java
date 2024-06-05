package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends EntityAnimal {
   public float destPos;
   public boolean field_152118_bv;
   public float field_70888_h;
   public int timeUntilNextEgg;
   public float field_70884_g;
   public float field_70889_i = 1.0F;
   private static final String __OBFID = "CL_00001639";
   public float field_70886_e;

   protected void func_180429_a(BlockPos var1, Block var2) {
      this.playSound("mob.chicken.step", 0.15F, 1.0F);
   }

   protected Item getDropItem() {
      return Items.feather;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.field_152118_bv = var1.getBoolean("IsChickenJockey");
      if (var1.hasKey("EggLayTime")) {
         this.timeUntilNextEgg = var1.getInteger("EggLayTime");
      }

   }

   protected String getDeathSound() {
      return "mob.chicken.hurt";
   }

   public float getEyeHeight() {
      return this.height;
   }

   protected String getHurtSound() {
      return "mob.chicken.hurt";
   }

   public EntityChicken(World var1) {
      super(var1);
      this.setSize(0.4F, 0.7F);
      this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
      this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
      this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
      this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
      this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
   }

   public void updateRiderPosition() {
      super.updateRiderPosition();
      float var1 = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
      float var2 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
      float var3 = 0.1F;
      float var4 = 0.0F;
      this.riddenByEntity.setPosition(this.posX + (double)(var3 * var1), this.posY + (double)(this.height * 0.5F) + this.riddenByEntity.getYOffset() + (double)var4, this.posZ - (double)(var3 * var2));
      if (this.riddenByEntity instanceof EntityLivingBase) {
         ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
      }

   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return this.createChild1(var1);
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("IsChickenJockey", this.field_152118_bv);
      var1.setInteger("EggLayTime", this.timeUntilNextEgg);
   }

   protected String getLivingSound() {
      return "mob.chicken.say";
   }

   public EntityChicken createChild1(EntityAgeable var1) {
      return new EntityChicken(this.worldObj);
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      return this.func_152116_bZ() ? 10 : super.getExperiencePoints(var1);
   }

   public void fall(float var1, float var2) {
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
   }

   public boolean isBreedingItem(ItemStack var1) {
      return var1 != null && var1.getItem() == Items.wheat_seeds;
   }

   public boolean func_152116_bZ() {
      return this.field_152118_bv;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.field_70888_h = this.field_70886_e;
      this.field_70884_g = this.destPos;
      this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
      this.destPos = MathHelper.clamp_float(this.destPos, 0.0F, 1.0F);
      if (!this.onGround && this.field_70889_i < 1.0F) {
         this.field_70889_i = 1.0F;
      }

      this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);
      if (!this.onGround && this.motionY < 0.0D) {
         this.motionY *= 0.6D;
      }

      this.field_70886_e += this.field_70889_i * 2.0F;
      if (!this.worldObj.isRemote && !this.isChild() && !this.func_152116_bZ() && --this.timeUntilNextEgg <= 0) {
         this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         this.dropItem(Items.egg, 1);
         this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
      }

   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + var2);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.feather, 1);
      }

      if (this.isBurning()) {
         this.dropItem(Items.cooked_chicken, 1);
      } else {
         this.dropItem(Items.chicken, 1);
      }

   }

   protected boolean canDespawn() {
      return this.func_152116_bZ() && this.riddenByEntity == null;
   }

   public void func_152117_i(boolean var1) {
      this.field_152118_bv = var1;
   }
}
