package net;

import net.minecraft.block.BlockRedSandstone$EnumType;

public class r_ {
   public static int a(BlockRedSandstone$EnumType var0) {
      return var0.getMetadata();
   }

   public static BlockRedSandstone$EnumType a(int var0) {
      return BlockRedSandstone$EnumType.byMetadata(var0);
   }
}
