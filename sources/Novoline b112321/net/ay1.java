package net;

import net.minecraft.block.BlockStoneBrick$EnumType;

public class ay1 {
   public static int a(BlockStoneBrick$EnumType var0) {
      return var0.getMetadata();
   }

   public static BlockStoneBrick$EnumType a(int var0) {
      return BlockStoneBrick$EnumType.byMetadata(var0);
   }
}
