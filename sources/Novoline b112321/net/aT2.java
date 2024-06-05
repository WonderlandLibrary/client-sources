package net;

import net.minecraft.block.BlockPrismarine$EnumType;

public class aT2 {
   public static int a(BlockPrismarine$EnumType var0) {
      return var0.getMetadata();
   }

   public static BlockPrismarine$EnumType a(int var0) {
      return BlockPrismarine$EnumType.byMetadata(var0);
   }
}
