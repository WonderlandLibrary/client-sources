package net.minecraft.entity.item;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
   private ItemStack[] minecartContainerItems = new ItemStack[36];
   private boolean dropContentsWhenDead = true;

   public EntityMinecartContainer(World var1) {
      super(var1);
   }

   public EntityMinecartContainer(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   public void killMinecart(DamageSource var1) {
      super.killMinecart(var1);
      if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         InventoryHelper.func_180176_a(this.worldObj, this, this);
      }

   }

   public ItemStack getStackInSlot(int var1) {
      return this.minecartContainerItems[var1];
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if(this.minecartContainerItems[var1] != null) {
         if(this.minecartContainerItems[var1].stackSize <= var2) {
            ItemStack var4 = this.minecartContainerItems[var1];
            this.minecartContainerItems[var1] = null;
            return var4;
         } else {
            ItemStack var3 = this.minecartContainerItems[var1].splitStack(var2);
            if(this.minecartContainerItems[var1].stackSize == 0) {
               this.minecartContainerItems[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack removeStackFromSlot(int var1) {
      if(this.minecartContainerItems[var1] != null) {
         ItemStack var2 = this.minecartContainerItems[var1];
         this.minecartContainerItems[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.minecartContainerItems[var1] = var2;
      if(var2.stackSize > this.getInventoryStackLimit()) {
         var2.stackSize = this.getInventoryStackLimit();
      }

   }

   public void markDirty() {
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return !this.isDead && var1.getDistanceSqToEntity(this) <= 64.0D;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public void closeInventory(EntityPlayer var1) {
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public String getName() {
      return this.hasCustomName()?this.getCustomNameTag():"container.minecart";
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void travelToDimension(int var1) {
      this.dropContentsWhenDead = false;
      super.travelToDimension(var1);
   }

   public void setDead() {
      if(this.dropContentsWhenDead) {
         InventoryHelper.func_180176_a(this.worldObj, this, this);
      }

      super.setDead();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.minecartContainerItems.length; ++var3) {
         if(this.minecartContainerItems[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.minecartContainerItems[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      var1.setTag("Items", var2);
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      NBTTagList var2 = var1.getTagList("Items", 10);
      this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         int var5 = var4.getByte("Slot") & 255;
         if(var5 < this.minecartContainerItems.length) {
            this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }

   }

   public boolean interactFirst(EntityPlayer var1) {
      if(!this.worldObj.isRemote) {
         var1.displayGUIChest(this);
      }

      return true;
   }

   protected void applyDrag() {
      int var1 = 15 - Container.a((IInventory)this);
      float var2 = 0.98F + (float)var1 * 0.001F;
      this.motionX *= (double)var2;
      this.motionY *= 0.0D;
      this.motionZ *= (double)var2;
   }

   public int getField(int var1) {
      return 0;
   }

   public void setField(int var1, int var2) {
   }

   public int getFieldCount() {
      return 0;
   }

   public boolean isLocked() {
      return false;
   }

   public void setLockCode(LockCode var1) {
   }

   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   public void clear() {
      for(int var1 = 0; var1 < this.minecartContainerItems.length; ++var1) {
         this.minecartContainerItems[var1] = null;
      }

   }
}
