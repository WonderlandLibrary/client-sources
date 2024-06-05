package net;

import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.material.MapColor;

public class aJ5 {
   public static int b(BlockSand$EnumType var0) {
      return var0.getMetadata();
   }

   public static MapColor a(BlockSand$EnumType var0) {
      return var0.getMapColor();
   }

   public static BlockSand$EnumType a(int var0) {
      return BlockSand$EnumType.byMetadata(var0);
   }
}
