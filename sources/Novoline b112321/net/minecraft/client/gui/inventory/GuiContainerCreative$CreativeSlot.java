package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class GuiContainerCreative$CreativeSlot extends Slot {
   private final Slot slot;
   final GuiContainerCreative this$0;

   public GuiContainerCreative$CreativeSlot(GuiContainerCreative var1, Slot var2, int var3) {
      super(var2.inventory, var3, 0, 0);
      this.this$0 = var1;
      this.slot = var2;
   }

   public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
      this.slot.onPickupFromSlot(var1, var2);
   }

   public boolean isItemValid(ItemStack var1) {
      return this.slot.isItemValid(var1);
   }

   public ItemStack getStack() {
      return this.slot.getStack();
   }

   public boolean getHasStack() {
      return this.slot.getHasStack();
   }

   public void putStack(ItemStack var1) {
      this.slot.putStack(var1);
   }

   public void onSlotChanged() {
      this.slot.onSlotChanged();
   }

   public int getSlotStackLimit() {
      return this.slot.getSlotStackLimit();
   }

   public int getItemStackLimit(ItemStack var1) {
      return this.slot.getItemStackLimit(var1);
   }

   public String getSlotTexture() {
      return this.slot.getSlotTexture();
   }

   public ItemStack decrStackSize(int var1) {
      return this.slot.decrStackSize(var1);
   }

   public boolean isHere(IInventory var1, int var2) {
      return this.slot.isHere(var1, var2);
   }

   static Slot access$000(GuiContainerCreative$CreativeSlot var0) {
      return var0.slot;
   }
}
