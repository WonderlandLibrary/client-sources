package net;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;

public class r3 {
   private static int b;

   public static ItemStack c(InventoryPlayer var0, int var1) {
      return var0.getStackInSlot(var1);
   }

   public static ItemStack a(InventoryPlayer var0) {
      return var0.getCurrentItem();
   }

   public static int a() {
      return InventoryPlayer.getHotbarSize();
   }

   public static boolean c(InventoryPlayer var0, ItemStack var1) {
      return var0.addItemStackToInventory(var1);
   }

   public static int b(InventoryPlayer var0) {
      return var0.getSizeInventory();
   }

   public static ItemStack c(InventoryPlayer var0) {
      return var0.getItemStack();
   }

   public static void b(InventoryPlayer var0, ItemStack var1) {
      var0.setItemStack(var1);
   }

   public static int e(InventoryPlayer var0) {
      return var0.getFirstEmptyStack();
   }

   public static void a(InventoryPlayer var0, int var1, ItemStack var2) {
      var0.setInventorySlotContents(var1, var2);
   }

   public static void b(InventoryPlayer var0, int var1) {
      var0.changeCurrentItem(var1);
   }

   public static void a(InventoryPlayer var0, Item var1, int var2, boolean var3, boolean var4) {
      var0.setCurrentItem(var1, var2, var3, var4);
   }

   public static IChatComponent f(InventoryPlayer var0) {
      return var0.getDisplayName();
   }

   public static void g(InventoryPlayer var0) {
      var0.decrementAnimations();
   }

   public static void d(InventoryPlayer var0) {
      var0.dropAllItems();
   }

   public static ItemStack a(InventoryPlayer var0, int var1, int var2) {
      return var0.decrStackSize(var1, var2);
   }

   public static float a(InventoryPlayer var0, Block var1) {
      return var0.getStrVsBlock(var1);
   }

   public static boolean b(InventoryPlayer var0, Block var1) {
      return var0.canHeldItemHarvest(var1);
   }

   public static void b(InventoryPlayer var0, NBTTagList var1) {
      var0.readFromNBT(var1);
   }

   public static NBTTagList a(InventoryPlayer var0, NBTTagList var1) {
      return var0.writeToNBT(var1);
   }

   public static ItemStack d(InventoryPlayer var0, int var1) {
      return var0.armorItemInSlot(var1);
   }

   public static void a(InventoryPlayer var0, InventoryPlayer var1) {
      var0.copyInventory(var1);
   }

   public static boolean a(InventoryPlayer var0, ItemStack var1) {
      return var0.hasItemStack(var1);
   }

   public static ItemStack a(InventoryPlayer var0, int var1) {
      return var0.removeStackFromSlot(var1);
   }

   public static int a(InventoryPlayer var0, Item var1, int var2, int var3, NBTTagCompound var4) {
      return var0.clearMatchingItems(var1, var2, var3, var4);
   }

   public static boolean a(InventoryPlayer var0, Item var1) {
      return var0.hasItem(var1);
   }

   public static boolean b(InventoryPlayer var0, Item var1) {
      return var0.c(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 117;
   }

   static {
      if(c() != 0) {
         b(96);
      }

   }
}
