package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;

public class xq {
   public static void a(FoodStats var0, EntityPlayer var1) {
      var0.onUpdate(var1);
   }

   public static boolean b(FoodStats var0) {
      return var0.needFood();
   }

   public static int a(FoodStats var0) {
      return var0.getFoodLevel();
   }

   public static void a(FoodStats var0, int var1) {
      var0.setFoodLevel(var1);
   }

   public static void b(FoodStats var0, NBTTagCompound var1) {
      var0.readNBT(var1);
   }

   public static void a(FoodStats var0, NBTTagCompound var1) {
      var0.writeNBT(var1);
   }

   public static void a(FoodStats var0, int var1, float var2) {
      var0.addStats(var1, var2);
   }

   public static void a(FoodStats var0, float var1) {
      var0.setFoodSaturationLevel(var1);
   }

   public static void a(FoodStats var0, ItemFood var1, ItemStack var2) {
      var0.addStats(var1, var2);
   }

   public static float d(FoodStats var0) {
      return var0.getSaturationLevel();
   }

   public static int c(FoodStats var0) {
      return var0.getPrevFoodLevel();
   }
}
