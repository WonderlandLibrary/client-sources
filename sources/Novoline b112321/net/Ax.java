package net;

import net.minecraft.block.BlockStoneSlabNew$EnumType;
import net.minecraft.block.material.MapColor;

public class Ax {
   public static int b(BlockStoneSlabNew$EnumType var0) {
      return var0.getMetadata();
   }

   public static BlockStoneSlabNew$EnumType a(int var0) {
      return BlockStoneSlabNew$EnumType.byMetadata(var0);
   }

   public static MapColor a(BlockStoneSlabNew$EnumType var0) {
      return var0.func_181068_c();
   }
}
