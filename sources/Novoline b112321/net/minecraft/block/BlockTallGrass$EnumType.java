package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockTallGrass$EnumType implements IStringSerializable {
   DEAD_BUSH(0, "dead_bush"),
   GRASS(1, "tall_grass"),
   FERN(2, "fern");

   private static final BlockTallGrass$EnumType[] META_LOOKUP = new BlockTallGrass$EnumType[values().length];
   private final int meta;
   private final String name;
   private static final BlockTallGrass$EnumType[] $VALUES = new BlockTallGrass$EnumType[]{DEAD_BUSH, GRASS, FERN};

   private BlockTallGrass$EnumType(int var3, String var4) {
      this.meta = var3;
      this.name = var4;
   }

   public int getMeta() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public static BlockTallGrass$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   static {
      for(BlockTallGrass$EnumType var11 : values()) {
         META_LOOKUP[var11.getMeta()] = var11;
      }

   }
}
