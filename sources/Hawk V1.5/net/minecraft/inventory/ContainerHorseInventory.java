package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {
   private IInventory field_111243_a;
   private static final String __OBFID = "CL_00001751";
   private EntityHorse theHorse;

   public boolean canInteractWith(EntityPlayer var1) {
      return this.field_111243_a.isUseableByPlayer(var1) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(var1) < 8.0F;
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      this.field_111243_a.closeInventory(var1);
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if (var4 != null && var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if (var2 < this.field_111243_a.getSizeInventory()) {
            if (!this.mergeItemStack(var5, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true)) {
               return null;
            }
         } else if (this.getSlot(1).isItemValid(var5) && !this.getSlot(1).getHasStack()) {
            if (!this.mergeItemStack(var5, 1, 2, false)) {
               return null;
            }
         } else if (this.getSlot(0).isItemValid(var5)) {
            if (!this.mergeItemStack(var5, 0, 1, false)) {
               return null;
            }
         } else if (this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(var5, 2, this.field_111243_a.getSizeInventory(), false)) {
            return null;
         }

         if (var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }
      }

      return var3;
   }

   public ContainerHorseInventory(IInventory var1, IInventory var2, EntityHorse var3, EntityPlayer var4) {
      this.field_111243_a = var2;
      this.theHorse = var3;
      byte var5 = 3;
      var2.openInventory(var4);
      int var6 = (var5 - 4) * 18;
      this.addSlotToContainer(new Slot(this, var2, 0, 8, 18) {
         private static final String __OBFID = "CL_00001752";
         final ContainerHorseInventory this$0;

         public boolean isItemValid(ItemStack var1) {
            return super.isItemValid(var1) && var1.getItem() == Items.saddle && !this.getHasStack();
         }

         {
            this.this$0 = var1;
         }
      });
      this.addSlotToContainer(new Slot(this, var2, 1, 8, 36, var3) {
         private static final String __OBFID = "CL_00001753";
         final ContainerHorseInventory this$0;
         private final EntityHorse val$p_i45791_3_;

         public boolean isItemValid(ItemStack var1) {
            return super.isItemValid(var1) && this.val$p_i45791_3_.canWearArmor() && EntityHorse.func_146085_a(var1.getItem());
         }

         public boolean canBeHovered() {
            return this.val$p_i45791_3_.canWearArmor();
         }

         {
            this.this$0 = var1;
            this.val$p_i45791_3_ = var6;
         }
      });
      int var7;
      int var8;
      if (var3.isChested()) {
         for(var7 = 0; var7 < var5; ++var7) {
            for(var8 = 0; var8 < 5; ++var8) {
               this.addSlotToContainer(new Slot(var2, 2 + var8 + var7 * 5, 80 + var8 * 18, 18 + var7 * 18));
            }
         }
      }

      for(var7 = 0; var7 < 3; ++var7) {
         for(var8 = 0; var8 < 9; ++var8) {
            this.addSlotToContainer(new Slot(var1, var8 + var7 * 9 + 9, 8 + var8 * 18, 102 + var7 * 18 + var6));
         }
      }

      for(var7 = 0; var7 < 9; ++var7) {
         this.addSlotToContainer(new Slot(var1, var7, 8 + var7 * 18, 160 + var6));
      }

   }
}
