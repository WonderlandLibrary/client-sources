package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoublePlant$EnumBlockHalf implements IStringSerializable {
   UPPER,
   LOWER;

   private static final BlockDoublePlant$EnumBlockHalf[] $VALUES = new BlockDoublePlant$EnumBlockHalf[]{UPPER, LOWER};

   public String toString() {
      return this.getName();
   }

   public String getName() {
      return this == UPPER?"upper":"lower";
   }
}
