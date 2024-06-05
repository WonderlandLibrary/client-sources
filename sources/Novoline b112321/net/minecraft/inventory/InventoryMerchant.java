package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;

public class InventoryMerchant implements IInventory {
   private final IMerchant theMerchant;
   private ItemStack[] theInventory = new ItemStack[3];
   private final EntityPlayer d;
   private MerchantRecipe currentRecipe;
   private int c;

   public InventoryMerchant(EntityPlayer var1, IMerchant var2) {
      this.d = var1;
      this.theMerchant = var2;
   }

   public int getSizeInventory() {
      return this.theInventory.length;
   }

   public ItemStack getStackInSlot(int var1) {
      return this.theInventory[var1];
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if(this.theInventory[var1] != null) {
         if(var1 == 2) {
            ItemStack var5 = this.theInventory[var1];
            this.theInventory[var1] = null;
            return var5;
         } else if(this.theInventory[var1].stackSize <= var2) {
            ItemStack var4 = this.theInventory[var1];
            this.theInventory[var1] = null;
            if(this.inventoryResetNeededOnSlotChange(var1)) {
               this.resetRecipeAndSlots();
            }

            return var4;
         } else {
            ItemStack var3 = this.theInventory[var1].splitStack(var2);
            if(this.theInventory[var1].stackSize == 0) {
               this.theInventory[var1] = null;
            }

            if(this.inventoryResetNeededOnSlotChange(var1)) {
               this.resetRecipeAndSlots();
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   private boolean inventoryResetNeededOnSlotChange(int var1) {
      return var1 == 1;
   }

   public ItemStack removeStackFromSlot(int var1) {
      if(this.theInventory[var1] != null) {
         ItemStack var2 = this.theInventory[var1];
         this.theInventory[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.theInventory[var1] = var2;
      if(var2.stackSize > this.getInventoryStackLimit()) {
         var2.stackSize = this.getInventoryStackLimit();
      }

      if(this.inventoryResetNeededOnSlotChange(var1)) {
         this.resetRecipeAndSlots();
      }

   }

   public String getName() {
      return "mob.villager";
   }

   public boolean hasCustomName() {
      return false;
   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return this.theMerchant.getCustomer() == var1;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public void closeInventory(EntityPlayer var1) {
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public void markDirty() {
      this.resetRecipeAndSlots();
   }

   public void resetRecipeAndSlots() {
      this.currentRecipe = null;
      ItemStack var1 = this.theInventory[0];
      ItemStack var2 = this.theInventory[1];
      var2 = null;
      this.setInventorySlotContents(2, (ItemStack)null);
      this.theMerchant.verifySellingItem(this.getStackInSlot(2));
   }

   public MerchantRecipe getCurrentRecipe() {
      return this.currentRecipe;
   }

   public void setCurrentRecipeIndex(int var1) {
      this.c = var1;
      this.resetRecipeAndSlots();
   }

   public int getField(int var1) {
      return 0;
   }

   public void setField(int var1, int var2) {
   }

   public int getFieldCount() {
      return 0;
   }

   public void clear() {
      for(int var1 = 0; var1 < this.theInventory.length; ++var1) {
         this.theInventory[var1] = null;
      }

   }
}
