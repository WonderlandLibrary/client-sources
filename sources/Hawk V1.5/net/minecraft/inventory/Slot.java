package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Slot {
   private final int slotIndex;
   private static final String __OBFID = "CL_00001762";
   public int slotNumber;
   public int xDisplayPosition;
   public final IInventory inventory;
   public int yDisplayPosition;

   public boolean canTakeStack(EntityPlayer var1) {
      return true;
   }

   public boolean canBeHovered() {
      return true;
   }

   public int func_178170_b(ItemStack var1) {
      return this.getSlotStackLimit();
   }

   public boolean isItemValid(ItemStack var1) {
      return true;
   }

   protected void onCrafting(ItemStack var1, int var2) {
   }

   public boolean getHasStack() {
      return this.getStack() != null;
   }

   public void onSlotChange(ItemStack var1, ItemStack var2) {
      if (var1 != null && var2 != null && var1.getItem() == var2.getItem()) {
         int var3 = var2.stackSize - var1.stackSize;
         if (var3 > 0) {
            this.onCrafting(var1, var3);
         }
      }

   }

   public boolean isHere(IInventory var1, int var2) {
      return var1 == this.inventory && var2 == this.slotIndex;
   }

   public String func_178171_c() {
      return null;
   }

   public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
      this.onSlotChanged();
   }

   public ItemStack getStack() {
      return this.inventory.getStackInSlot(this.slotIndex);
   }

   public void onSlotChanged() {
      this.inventory.markDirty();
   }

   protected void onCrafting(ItemStack var1) {
   }

   public void putStack(ItemStack var1) {
      this.inventory.setInventorySlotContents(this.slotIndex, var1);
      this.onSlotChanged();
   }

   public int getSlotStackLimit() {
      return this.inventory.getInventoryStackLimit();
   }

   public ItemStack decrStackSize(int var1) {
      return this.inventory.decrStackSize(this.slotIndex, var1);
   }

   public Slot(IInventory var1, int var2, int var3, int var4) {
      this.inventory = var1;
      this.slotIndex = var2;
      this.xDisplayPosition = var3;
      this.yDisplayPosition = var4;
   }
}
