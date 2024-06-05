package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
   public IInventory craftResult = new InventoryCraftResult();
   private World worldObj;
   private BlockPos pos;

   public ContainerWorkbench(InventoryPlayer var1, World var2, BlockPos var3) {
      this.worldObj = var2;
      this.pos = var3;
      this.addSlotToContainer(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 124, 35));

      for(int var4 = 0; var4 < 3; ++var4) {
         for(int var5 = 0; var5 < 3; ++var5) {
            this.addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 3, 30 + var5 * 18, 17 + var4 * 18));
         }
      }

      for(int var6 = 0; var6 < 3; ++var6) {
         for(int var8 = 0; var8 < 9; ++var8) {
            this.addSlotToContainer(new Slot(var1, var8 + var6 * 9 + 9, 8 + var8 * 18, 84 + var6 * 18));
         }
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         this.addSlotToContainer(new Slot(var1, var7, 8 + var7 * 18, 142));
      }

      this.onCraftMatrixChanged(this.craftMatrix);
   }

   public void onCraftMatrixChanged(IInventory var1) {
      this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      if(!this.worldObj.isRemote) {
         for(int var2 = 0; var2 < 9; ++var2) {
            ItemStack var3 = this.craftMatrix.removeStackFromSlot(var2);
            var1.dropPlayerItemWithRandomChoice(var3, false);
         }
      }

   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.worldObj.getBlockState(this.pos).getBlock() == Blocks.crafting_table && var1.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(!this.mergeItemStack(var5, 10, 46, true)) {
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
