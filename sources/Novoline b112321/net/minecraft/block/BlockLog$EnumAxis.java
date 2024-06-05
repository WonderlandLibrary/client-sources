package net.minecraft.block;

import net.minecraft.block.BlockLog$1;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.IStringSerializable;

public enum BlockLog$EnumAxis implements IStringSerializable {
   X("x"),
   Y("y"),
   Z("z"),
   NONE("none");

   private final String name;
   private static final BlockLog$EnumAxis[] $VALUES = new BlockLog$EnumAxis[]{X, Y, Z, NONE};

   private BlockLog$EnumAxis(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public static BlockLog$EnumAxis fromFacingAxis(EnumFacing$Axis var0) {
      switch(BlockLog$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[var0.ordinal()]) {
      case 1:
         return X;
      case 2:
         return Y;
      case 3:
         return Z;
      default:
         return NONE;
      }
   }

   public String getName() {
      return this.name;
   }
}
