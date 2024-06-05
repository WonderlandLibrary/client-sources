package net;

import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.material.MapColor;

public class O4 {
   public static int a(BlockStone$EnumType var0) {
      return var0.getMetadata();
   }

   public static MapColor b(BlockStone$EnumType var0) {
      return var0.func_181072_c();
   }

   public static BlockStone$EnumType a(int var0) {
      return BlockStone$EnumType.byMetadata(var0);
   }
}
