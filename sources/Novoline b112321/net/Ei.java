package net;

import viaversion.viaversion.api.minecraft.BlockFace;
import viaversion.viaversion.api.minecraft.BlockFace$EnumAxis;

public class Ei {
   public static BlockFace a(String var0) {
      return BlockFace.valueOf(var0);
   }

   public static byte c(BlockFace var0) {
      return var0.getModX();
   }

   public static byte b(BlockFace var0) {
      return var0.getModY();
   }

   public static byte a(BlockFace var0) {
      return var0.getModZ();
   }

   public static BlockFace d(BlockFace var0) {
      return var0.opposite();
   }

   public static BlockFace$EnumAxis e(BlockFace var0) {
      return var0.getAxis();
   }
}
