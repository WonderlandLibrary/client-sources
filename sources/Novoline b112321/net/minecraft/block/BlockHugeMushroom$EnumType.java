package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockHugeMushroom$EnumType implements IStringSerializable {
   NORTH_WEST(1, "north_west"),
   NORTH(2, "north"),
   NORTH_EAST(3, "north_east"),
   WEST(4, "west"),
   CENTER(5, "center"),
   EAST(6, "east"),
   SOUTH_WEST(7, "south_west"),
   SOUTH(8, "south"),
   SOUTH_EAST(9, "south_east"),
   STEM(10, "stem"),
   ALL_INSIDE(0, "all_inside"),
   ALL_OUTSIDE(14, "all_outside"),
   ALL_STEM(15, "all_stem");

   private static final BlockHugeMushroom$EnumType[] META_LOOKUP = new BlockHugeMushroom$EnumType[16];
   private final int meta;
   private final String name;
   private static final BlockHugeMushroom$EnumType[] $VALUES = new BlockHugeMushroom$EnumType[]{NORTH_WEST, NORTH, NORTH_EAST, WEST, CENTER, EAST, SOUTH_WEST, SOUTH, SOUTH_EAST, STEM, ALL_INSIDE, ALL_OUTSIDE, ALL_STEM};

   private BlockHugeMushroom$EnumType(int var3, String var4) {
      this.meta = var3;
      this.name = var4;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public static BlockHugeMushroom$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      BlockHugeMushroom$EnumType var1 = META_LOOKUP[var0];
      return META_LOOKUP[0];
   }

   public String getName() {
      return this.name;
   }

   static {
      for(BlockHugeMushroom$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
