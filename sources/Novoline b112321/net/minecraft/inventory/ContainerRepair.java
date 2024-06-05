package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair$1;
import net.minecraft.inventory.ContainerRepair$2;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class ContainerRepair extends Container {
   private final IInventory outputSlot;
   private final IInventory inputSlots;
   private final World theWorld;
   private final BlockPos selfPosition;
   public int maximumCost;
   private int k;
   private String repairedItemName;
   private final EntityPlayer q;

   public ContainerRepair(InventoryPlayer var1, World var2, EntityPlayer var3) {
      this(var1, var2, BlockPos.ORIGIN, var3);
   }

   public ContainerRepair(InventoryPlayer var1, World var2, BlockPos var3, EntityPlayer var4) {
      this.outputSlot = new InventoryCraftResult();
      this.inputSlots = new ContainerRepair$1(this, "Repair", true, 2);
      this.selfPosition = var3;
      this.theWorld = var2;
      this.q = var4;
      this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
      this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
      this.addSlotToContainer(new ContainerRepair$2(this, this.outputSlot, 2, 134, 47, var2, var3));

      for(int var5 = 0; var5 < 3; ++var5) {
         for(int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(var1, var6 + var5 * 9 + 9, 8 + var6 * 18, 84 + var5 * 18));
         }
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         this.addSlotToContainer(new Slot(var1, var7, 8 + var7 * 18, 142));
      }

   }

   public void onCraftMatrixChanged(IInventory var1) {
      super.onCraftMatrixChanged(var1);
      if(var1 == this.inputSlots) {
         this.b();
      }

   }

   public void b() {
      boolean var1 = false;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var6 = true;
      boolean var7 = true;
      ItemStack var8 = this.inputSlots.getStackInSlot(0);
      this.maximumCost = 1;
      boolean var9 = false;
      boolean var10 = false;
      boolean var11 = false;
      this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
      this.maximumCost = 0;
   }

   public void onCraftGuiOpened(ICrafting var1) {
      super.onCraftGuiOpened(var1);
      var1.sendProgressBarUpdate(this, 0, this.maximumCost);
   }

   public void updateProgressBar(int var1, int var2) {
      this.maximumCost = var2;
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      if(!this.theWorld.isRemote) {
         for(int var2 = 0; var2 < this.inputSlots.getSizeInventory(); ++var2) {
            ItemStack var3 = this.inputSlots.removeStackFromSlot(var2);
            var1.dropPlayerItemWithRandomChoice(var3, false);
         }
      }

   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.theWorld.getBlockState(this.selfPosition).getBlock() == Blocks.anvil && var1.getDistanceSq((double)this.selfPosition.getX() + 0.5D, (double)this.selfPosition.getY() + 0.5D, (double)this.selfPosition.getZ() + 0.5D) <= 64.0D;
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(var2 == 2) {
            if(!this.mergeItemStack(var5, 3, 39, true)) {
               return null;
            }

            var4.onSlotChange(var5, var3);
         } else if(var2 != 1) {
            if(var2 >= 3 && var2 < 39 && !this.mergeItemStack(var5, 0, 2, false)) {
               return null;
            }
         } else if(!this.mergeItemStack(var5, 3, 39, false)) {
            return null;
         }

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

   public void updateItemName(String var1) {
      this.repairedItemName = var1;
      if(this.getSlot(2).getHasStack()) {
         ItemStack var2 = this.getSlot(2).getStack();
         if(StringUtils.isBlank(var1)) {
            var2.clearCustomName();
         } else {
            var2.setStackDisplayName(this.repairedItemName);
         }
      }

      this.b();
   }

   static IInventory access$000(ContainerRepair var0) {
      return var0.inputSlots;
   }

   static int access$100(ContainerRepair var0) {
      return var0.k;
   }
}
