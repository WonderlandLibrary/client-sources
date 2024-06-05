package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer$1;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerPlayer extends Container {
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
   public IInventory craftResult = new InventoryCraftResult();
   public boolean isLocalWorld;
   private final EntityPlayer thePlayer;

   public ContainerPlayer(InventoryPlayer var1, boolean var2, EntityPlayer var3) {
      this.isLocalWorld = var2;
      this.thePlayer = var3;
      this.addSlotToContainer(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 144, 36));

      for(int var4 = 0; var4 < 2; ++var4) {
         for(int var5 = 0; var5 < 2; ++var5) {
            this.addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
         }
      }

      for(int var6 = 0; var6 < 4; ++var6) {
         this.addSlotToContainer(new ContainerPlayer$1(this, var1, var1.getSizeInventory() - 1 - var6, 8, 8 + var6 * 18, var6));
      }

      for(int var7 = 0; var7 < 3; ++var7) {
         for(int var9 = 0; var9 < 9; ++var9) {
            this.addSlotToContainer(new Slot(var1, var9 + (var7 + 1) * 9, 8 + var9 * 18, 84 + var7 * 18));
         }
      }

      for(int var8 = 0; var8 < 9; ++var8) {
         this.addSlotToContainer(new Slot(var1, var8, 8 + var8 * 18, 142));
      }

      this.onCraftMatrixChanged(this.craftMatrix);
   }

   public void onCraftMatrixChanged(IInventory var1) {
      this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);

      for(int var2 = 0; var2 < 4; ++var2) {
         ItemStack var3 = this.craftMatrix.removeStackFromSlot(var2);
         var1.dropPlayerItemWithRandomChoice(var3, false);
      }

      this.craftResult.setInventorySlotContents(0, (ItemStack)null);
   }

   public boolean canInteractWith(EntityPlayer var1) {
      return true;
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(!this.mergeItemStack(var5, 9, 45, true)) {
            return null;
         }

         var4.onSlotChange(var5, var3);
         if(var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }

         if(var5.stackSize == var3.stackSize) {
            return null;
         }

         var4.onPickupFromSlot(var1, var5);
      }

      return var3;
   }

   public boolean canMergeSlot(ItemStack var1, Slot var2) {
      return var2.inventory != this.craftResult && super.canMergeSlot(var1, var2);
   }
}
