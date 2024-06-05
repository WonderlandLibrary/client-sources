package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockRailBase$EnumRailDirection implements IStringSerializable {
   NORTH_SOUTH(0, "north_south"),
   EAST_WEST(1, "east_west"),
   ASCENDING_EAST(2, "ascending_east"),
   ASCENDING_WEST(3, "ascending_west"),
   ASCENDING_NORTH(4, "ascending_north"),
   ASCENDING_SOUTH(5, "ascending_south"),
   SOUTH_EAST(6, "south_east"),
   SOUTH_WEST(7, "south_west"),
   NORTH_WEST(8, "north_west"),
   NORTH_EAST(9, "north_east");

   private static final BlockRailBase$EnumRailDirection[] META_LOOKUP = new BlockRailBase$EnumRailDirection[values().length];
   private final int meta;
   private final String name;
   private static final BlockRailBase$EnumRailDirection[] $VALUES = new BlockRailBase$EnumRailDirection[]{NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST};

   private BlockRailBase$EnumRailDirection(int var3, String var4) {
      this.meta = var3;
      this.name = var4;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public boolean isAscending() {
      return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
   }

   public static BlockRailBase$EnumRailDirection byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   static {
      for(BlockRailBase$EnumRailDirection var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
