package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockStairs$EnumShape implements IStringSerializable {
   STRAIGHT("straight"),
   INNER_LEFT("inner_left"),
   INNER_RIGHT("inner_right"),
   OUTER_LEFT("outer_left"),
   OUTER_RIGHT("outer_right");

   private final String name;
   private static final BlockStairs$EnumShape[] $VALUES = new BlockStairs$EnumShape[]{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT};

   private BlockStairs$EnumShape(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
