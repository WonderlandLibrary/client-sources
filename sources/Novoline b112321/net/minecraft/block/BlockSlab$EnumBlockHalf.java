package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockSlab$EnumBlockHalf implements IStringSerializable {
   TOP("top"),
   BOTTOM("bottom");

   private final String name;
   private static final BlockSlab$EnumBlockHalf[] $VALUES = new BlockSlab$EnumBlockHalf[]{TOP, BOTTOM};

   private BlockSlab$EnumBlockHalf(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
