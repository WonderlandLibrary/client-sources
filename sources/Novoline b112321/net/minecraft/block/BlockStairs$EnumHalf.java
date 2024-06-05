package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockStairs$EnumHalf implements IStringSerializable {
   TOP("top"),
   BOTTOM("bottom");

   private final String name;
   private static final BlockStairs$EnumHalf[] $VALUES = new BlockStairs$EnumHalf[]{TOP, BOTTOM};

   private BlockStairs$EnumHalf(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
