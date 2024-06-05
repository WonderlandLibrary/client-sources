package net;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class agF {
   public static IBlockState b(BlockSlab var0) {
      return var0.getDefaultState();
   }

   public static IBlockState b(BlockSlab var0, int var1) {
      return var0.getStateFromMeta(var1);
   }

   public static String a(BlockSlab var0, int var1) {
      return var0.getUnlocalizedName(var1);
   }

   public static Object a(BlockSlab var0, ItemStack var1) {
      return var0.getVariant(var1);
   }

   public static IProperty a(BlockSlab var0) {
      return var0.getVariantProperty();
   }

   public static AxisAlignedBB a(BlockSlab var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.getCollisionBoundingBox(var1, var2, var3);
   }
}
