package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class a_c {
   private static String b;

   public static boolean c(Slot var0) {
      return var0.getHasStack();
   }

   public static ItemStack b(Slot var0) {
      return var0.getStack();
   }

   public static ItemStack a(Slot var0, int var1) {
      return var0.decrStackSize(var1);
   }

   public static void a(Slot var0, ItemStack var1, ItemStack var2) {
      var0.onSlotChange(var1, var2);
   }

   public static void b(Slot var0, ItemStack var1) {
      var0.putStack(var1);
   }

   public static void a(Slot var0) {
      var0.onSlotChanged();
   }

   public static void a(Slot var0, EntityPlayer var1, ItemStack var2) {
      var0.onPickupFromSlot(var1, var2);
   }

   public static boolean a(Slot var0, IInventory var1, int var2) {
      return var0.isHere(var1, var2);
   }

   public static boolean c(Slot var0, ItemStack var1) {
      return var0.isItemValid(var1);
   }

   public static int a(Slot var0, ItemStack var1) {
      return var0.getItemStackLimit(var1);
   }

   public static boolean a(Slot var0, EntityPlayer var1) {
      return var0.canTakeStack(var1);
   }

   public static boolean e(Slot var0) {
      return var0.canBeHovered();
   }

   public static String d(Slot var0) {
      return var0.getSlotTexture();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("wC0XDb");
      }

   }
}
