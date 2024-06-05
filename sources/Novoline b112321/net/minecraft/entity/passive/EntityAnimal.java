package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
   protected Block spawnableBlock = Blocks.grass;
   private int inLove;
   private EntityPlayer playerInLove;

   public EntityAnimal(World var1) {
      super(var1);
   }

   protected void updateAITasks() {
      if(this.getGrowingAge() != 0) {
         this.inLove = 0;
      }

      super.updateAITasks();
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(this.getGrowingAge() != 0) {
         this.inLove = 0;
      }

      if(this.inLove > 0) {
         --this.inLove;
         if(this.inLove % 10 == 0) {
            double var1 = this.rand.nextGaussian() * 0.02D;
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var1, var3, var5, new int[0]);
         }
      }

   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else {
         this.inLove = 0;
         return super.attackEntityFrom(var1, var2);
      }
   }

   public float getBlockPathWeight(BlockPos var1) {
      return this.worldObj.getBlockState(var1.down()).getBlock() == Blocks.grass?10.0F:this.worldObj.getLightBrightness(var1) - 0.5F;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("InLove", this.inLove);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.inLove = var1.getInteger("InLove");
   }

   public boolean getCanSpawnHere() {
      int var1 = MathHelper.floor_double(this.posX);
      int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
      int var3 = MathHelper.floor_double(this.posZ);
      BlockPos var4 = new BlockPos(var1, var2, var3);
      return this.worldObj.getBlockState(var4.down()).getBlock() == this.spawnableBlock && this.worldObj.getLight(var4) > 8 && super.getCanSpawnHere();
   }

   public int getTalkInterval() {
      return 120;
   }

   protected boolean canDespawn() {
      return false;
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      return 1 + this.worldObj.rand.nextInt(3);
   }

   public boolean isBreedingItem(ItemStack var1) {
      return var1.getItem() == Items.wheat;
   }

   public boolean interact(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      if(this.isBreedingItem(var2) && this.getGrowingAge() == 0 && this.inLove <= 0) {
         this.consumeItemFromStack(var1, var2);
         this.setInLove(var1);
         return true;
      } else if(this.isChild() && this.isBreedingItem(var2)) {
         this.consumeItemFromStack(var1, var2);
         this.func_175501_a((int)((float)(-this.getGrowingAge() / 20) * 0.1F), true);
         return true;
      } else {
         return super.interact(var1);
      }
   }

   protected void consumeItemFromStack(EntityPlayer var1, ItemStack var2) {
      if(!var1.abilities.isCreative()) {
         --var2.stackSize;
         if(var2.stackSize <= 0) {
            var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
         }
      }

   }

   public void setInLove(EntityPlayer var1) {
      this.inLove = 600;
      this.playerInLove = var1;
      this.worldObj.setEntityState(this, (byte)18);
   }

   public EntityPlayer getPlayerInLove() {
      return this.playerInLove;
   }

   public boolean isInLove() {
      return this.inLove > 0;
   }

   public void resetInLove() {
      this.inLove = 0;
   }

   public boolean canMateWith(EntityAnimal var1) {
      return var1 != this && var1.getClass() == this.getClass() && this.isInLove() && var1.isInLove();
   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 18) {
         for(int var2 = 0; var2 < 7; ++var2) {
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7, new int[0]);
         }
      } else {
         super.handleStatusUpdate(var1);
      }

   }
}
