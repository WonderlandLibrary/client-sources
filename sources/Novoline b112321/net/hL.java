package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

public class hL {
   private static boolean b;

   public static void b(IInventory var0, int var1, int var2) {
      var0.setField(var1, var2);
   }

   public static void b(IInventory var0) {
      var0.markDirty();
   }

   public static void a(IInventory var0, int var1, ItemStack var2) {
      var0.setInventorySlotContents(var1, var2);
   }

   public static int c(IInventory var0) {
      return var0.getSizeInventory();
   }

   public static ItemStack a(IInventory var0, int var1) {
      return var0.getStackInSlot(var1);
   }

   public static int e(IInventory var0) {
      return var0.getInventoryStackLimit();
   }

   public static ItemStack b(IInventory var0, int var1) {
      return var0.removeStackFromSlot(var1);
   }

   public static boolean a(IInventory var0, EntityPlayer var1) {
      return var0.isUseableByPlayer(var1);
   }

   public static IChatComponent d(IInventory var0) {
      return var0.getDisplayName();
   }

   public static ItemStack a(IInventory var0, int var1, int var2) {
      return var0.decrStackSize(var1, var2);
   }

   public static int c(IInventory var0, int var1) {
      return var0.getField(var1);
   }

   public static void f(IInventory var0) {
      var0.clear();
   }

   public static void c(IInventory var0, EntityPlayer var1) {
      var0.openInventory(var1);
   }

   public static void b(IInventory var0, EntityPlayer var1) {
      var0.closeInventory(var1);
   }

   public static int a(IInventory var0) {
      return var0.getFieldCount();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
