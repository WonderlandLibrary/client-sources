package net;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;

public class in {
   public static int b(BlockStateBase var0) {
      return var0.getBlockId();
   }

   public static int a(BlockStateBase var0) {
      return var0.getMetadata();
   }

   public static Block d(BlockStateBase var0) {
      return var0.getBlock();
   }

   public static Comparable a(BlockStateBase var0, IProperty var1) {
      return var0.getValue(var1);
   }

   public static IBlockState a(BlockStateBase var0, IProperty var1, Comparable var2) {
      return var0.withProperty(var1, var2);
   }

   public static ImmutableMap c(BlockStateBase var0) {
      return var0.getProperties();
   }
}
