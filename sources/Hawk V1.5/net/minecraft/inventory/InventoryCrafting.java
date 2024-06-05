package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCrafting implements IInventory {
   private final ItemStack[] stackList;
   private final int inventoryWidth;
   private static final String __OBFID = "CL_00001743";
   private final int field_174924_c;
   private final Container eventHandler;

   public void closeInventory(EntityPlayer var1) {
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.stackList[var1] = var2;
      this.eventHandler.onCraftMatrixChanged(this);
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public void clearInventory() {
      for(int var1 = 0; var1 < this.stackList.length; ++var1) {
         this.stackList[var1] = null;
      }

   }

   public int getFieldCount() {
      return 0;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public void setField(int var1, int var2) {
   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public ItemStack getStackInSlot(int var1) {
      return var1 >= this.getSizeInventory() ? null : this.stackList[var1];
   }

   public void openInventory(EntityPlayer var1) {
   }

   public int getField(int var1) {
      return 0;
   }

   public int func_174923_h() {
      return this.field_174924_c;
   }

   public int func_174922_i() {
      return this.inventoryWidth;
   }

   public String getName() {
      return "container.crafting";
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public ItemStack getStackInRowAndColumn(int var1, int var2) {
      return var1 >= 0 && var1 < this.inventoryWidth && var2 >= 0 && var2 <= this.field_174924_c ? this.getStackInSlot(var1 + var2 * this.inventoryWidth) : null;
   }

   public void markDirty() {
   }

   public int getSizeInventory() {
      return this.stackList.length;
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if (this.stackList[var1] != null) {
         ItemStack var2 = this.stackList[var1];
         this.stackList[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if (this.stackList[var1] != null) {
         ItemStack var3;
         if (this.stackList[var1].stackSize <= var2) {
            var3 = this.stackList[var1];
            this.stackList[var1] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return var3;
         } else {
            var3 = this.stackList[var1].splitStack(var2);
            if (this.stackList[var1].stackSize == 0) {
               this.stackList[var1] = null;
            }

            this.eventHandler.onCraftMatrixChanged(this);
            return var3;
         }
      } else {
         return null;
      }
   }

   public boolean hasCustomName() {
      return false;
   }

   public InventoryCrafting(Container var1, int var2, int var3) {
      int var4 = var2 * var3;
      this.stackList = new ItemStack[var4];
      this.eventHandler = var1;
      this.inventoryWidth = var2;
      this.field_174924_c = var3;
   }
}
