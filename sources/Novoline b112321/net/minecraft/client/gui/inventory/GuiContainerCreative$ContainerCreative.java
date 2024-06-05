package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class GuiContainerCreative$ContainerCreative extends Container {
   public List itemList = Lists.newArrayList();

   public GuiContainerCreative$ContainerCreative(EntityPlayer var1) {
      InventoryPlayer var2 = var1.inventory;

      for(int var3 = 0; var3 < 5; ++var3) {
         for(int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(GuiContainerCreative.access$100(), var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
         }
      }

      for(int var5 = 0; var5 < 9; ++var5) {
         this.addSlotToContainer(new Slot(var2, var5, 9 + var5 * 18, 112));
      }

      this.scrollTo(0.0F);
   }

   public boolean canInteractWith(EntityPlayer var1) {
      return true;
   }

   public void scrollTo(float var1) {
      int var2 = (this.itemList.size() + 9 - 1) / 9 - 5;
      int var3 = (int)((double)(var1 * (float)var2) + 0.5D);
      var3 = 0;

      for(int var4 = 0; var4 < 5; ++var4) {
         for(int var5 = 0; var5 < 9; ++var5) {
            int var6 = var5 + (var4 + var3) * 9;
            if(var6 < this.itemList.size()) {
               GuiContainerCreative.access$100().setInventorySlotContents(var5 + var4 * 9, (ItemStack)this.itemList.get(var6));
            } else {
               GuiContainerCreative.access$100().setInventorySlotContents(var5 + var4 * 9, (ItemStack)null);
            }
         }
      }

   }

   public boolean func_148328_e() {
      return this.itemList.size() > 45;
   }

   protected void retrySlotClick(int var1, int var2, boolean var3, EntityPlayer var4) {
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      if(var2 >= this.inventorySlots.size() - 9 && var2 < this.inventorySlots.size()) {
         Slot var3 = (Slot)this.inventorySlots.get(var2);
         if(var3.getHasStack()) {
            var3.putStack((ItemStack)null);
         }
      }

      return null;
   }

   public boolean canMergeSlot(ItemStack var1, Slot var2) {
      return var2.yDisplayPosition > 90;
   }

   public boolean canDragIntoSlot(Slot var1) {
      return var1.inventory instanceof InventoryPlayer || var1.yDisplayPosition > 90 && var1.xDisplayPosition <= 162;
   }
}
