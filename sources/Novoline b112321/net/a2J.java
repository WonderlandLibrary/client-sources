package net;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;

public class a2J {
   public static int b(ItemStack var0) {
      return TileEntityBanner.getPatterns(var0);
   }

   public static void a(TileEntityBanner var0, ItemStack var1) {
      var0.setItemValues(var1);
   }

   public static void a(NBTTagCompound var0, int var1, NBTTagList var2) {
      TileEntityBanner.func_181020_a(var0, var1, var2);
   }

   public static int d(TileEntityBanner var0) {
      return var0.getBaseColor();
   }

   public static NBTTagList h(TileEntityBanner var0) {
      return var0.func_181021_d();
   }

   public static Block c(TileEntityBanner var0) {
      return var0.getBlockType();
   }

   public static int g(TileEntityBanner var0) {
      return var0.getBlockMetadata();
   }

   public static BlockPos b(TileEntityBanner var0) {
      return var0.getPos();
   }

   public static String e(TileEntityBanner var0) {
      return var0.func_175116_e();
   }

   public static List f(TileEntityBanner var0) {
      return var0.getPatternList();
   }

   public static List a(TileEntityBanner var0) {
      return var0.getColorList();
   }

   public static void a(ItemStack var0) {
      TileEntityBanner.removeBannerData(var0);
   }

   public static int c(ItemStack var0) {
      return TileEntityBanner.getBaseColor(var0);
   }
}
