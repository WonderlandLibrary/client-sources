package net;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class on {
   public static int a(InventoryCrafting var0) {
      return var0.getSizeInventory();
   }

   public static ItemStack b(InventoryCrafting var0, int var1) {
      return var0.getStackInSlot(var1);
   }

   public static ItemStack a(InventoryCrafting var0, int var1) {
      return var0.removeStackFromSlot(var1);
   }

   public static int b(InventoryCrafting var0) {
      return var0.getHeight();
   }

   public static int c(InventoryCrafting var0) {
      return var0.getWidth();
   }

   public static ItemStack a(InventoryCrafting var0, int var1, int var2) {
      return var0.getStackInRowAndColumn(var1, var2);
   }

   public static void a(InventoryCrafting var0, int var1, ItemStack var2) {
      var0.setInventorySlotContents(var1, var2);
   }

   public static ItemStack b(InventoryCrafting var0, int var1, int var2) {
      return var0.decrStackSize(var1, var2);
   }
}
