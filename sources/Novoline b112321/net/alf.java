package net;

import net.minecraft.block.BlockQuartz$EnumType;

public class alf {
   public static int a(BlockQuartz$EnumType var0) {
      return var0.getMetadata();
   }

   public static BlockQuartz$EnumType a(int var0) {
      return BlockQuartz$EnumType.byMetadata(var0);
   }
}
