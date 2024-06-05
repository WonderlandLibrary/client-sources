package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCraftResult implements IInventory {
   private ItemStack[] stackResult = new ItemStack[1];
   private static final String __OBFID = "CL_00001760";

   public void markDirty() {
   }

   public int getField(int var1) {
      return 0;
   }

   public String getName() {
      return "Result";
   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.stackResult[0] = var2;
   }

   public void setField(int var1, int var2) {
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if (this.stackResult[0] != null) {
         ItemStack var3 = this.stackResult[0];
         this.stackResult[0] = null;
         return var3;
      } else {
         return null;
      }
   }

   public int getSizeInventory() {
      return 1;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public ItemStack getStackInSlot(int var1) {
      return this.stackResult[0];
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if (this.stackResult[0] != null) {
         ItemStack var2 = this.stackResult[0];
         this.stackResult[0] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void closeInventory(EntityPlayer var1) {
   }

   public void clearInventory() {
      for(int var1 = 0; var1 < this.stackResult.length; ++var1) {
         this.stackResult[var1] = null;
      }

   }

   public boolean hasCustomName() {
      return false;
   }

   public int getFieldCount() {
      return 0;
   }

   public void openInventory(EntityPlayer var1) {
   }
}
