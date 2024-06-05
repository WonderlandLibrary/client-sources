package net;

import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class j_ {
   private static int b;

   public static Slot a(Container var0, IInventory var1, int var2) {
      return var0.getSlotFromInventory(var1, var2);
   }

   public static void b(Container var0) {
      var0.detectAndSendChanges();
   }

   public static boolean a(Container var0, EntityPlayer var1) {
      return var0.getCanCraft(var1);
   }

   public static ItemStack a(Container var0, int var1, int var2, int var3, EntityPlayer var4) {
      return var0.slotClick(var1, var2, var3, var4);
   }

   public static void a(Container var0, EntityPlayer var1, boolean var2) {
      var0.setCanCraft(var1, var2);
   }

   public static boolean a(Container var0, EntityPlayer var1, int var2) {
      return var0.enchantItem(var1, var2);
   }

   public static void a(Container var0, int var1, ItemStack var2) {
      var0.putStackInSlot(var1, var2);
   }

   public static boolean a(Slot var0, ItemStack var1, boolean var2) {
      return Container.canAddItemToSlot(var0, var1, var2);
   }

   public static boolean a(Container var0, Slot var1) {
      return var0.canDragIntoSlot(var1);
   }

   public static void a(Set var0, int var1, ItemStack var2, int var3) {
      Container.computeStackSize(var0, var1, var2, var3);
   }

   public static boolean a(Container var0, ItemStack var1, Slot var2) {
      return var0.canMergeSlot(var1, var2);
   }

   public static int a(int var0, int var1) {
      return Container.func_94534_d(var0, var1);
   }

   public static void b(Container var0, EntityPlayer var1) {
      var0.onContainerClosed(var1);
   }

   public static Slot a(Container var0, int var1) {
      return var0.getSlot(var1);
   }

   public static int a(IInventory var0) {
      return Container.a(var0);
   }

   public static short c(Container var0) {
      return var0.d();
   }

   public static boolean c(Container var0, EntityPlayer var1) {
      return var0.canInteractWith(var1);
   }

   public static List a(Container var0) {
      return var0.getInventory();
   }

   public static int a(int var0) {
      return Container.getDragEvent(var0);
   }

   public static void b(Container var0, ICrafting var1) {
      var0.onCraftGuiOpened(var1);
   }

   public static void a(Container var0, ICrafting var1) {
      var0.removeCraftingFromCrafters(var1);
   }

   public static int a(TileEntity var0) {
      return Container.calcRedstone(var0);
   }

   public static void a(Container var0, ItemStack[] var1) {
      var0.putStacksInSlots(var1);
   }

   public static void a(Container var0, int var1, int var2) {
      var0.updateProgressBar(var1, var2);
   }

   public static void a(Container var0, IInventory var1) {
      var0.onCraftMatrixChanged(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 19;
   }

   static {
      if(c() == 0) {
         b(77);
      }

   }
}
