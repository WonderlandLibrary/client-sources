package net.minecraft.block;

import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockStoneSlabNew$EnumType implements IStringSerializable {
   RED_SANDSTONE(0, "red_sandstone", BlockSand$EnumType.RED_SAND.getMapColor());

   private static final BlockStoneSlabNew$EnumType[] META_LOOKUP = new BlockStoneSlabNew$EnumType[values().length];
   private final int meta;
   private final String name;
   private final MapColor field_181069_e;
   private static final BlockStoneSlabNew$EnumType[] $VALUES = new BlockStoneSlabNew$EnumType[]{RED_SANDSTONE};

   private BlockStoneSlabNew$EnumType(int var3, String var4, MapColor var5) {
      this.meta = var3;
      this.name = var4;
      this.field_181069_e = var5;
   }

   public int getMetadata() {
      return this.meta;
   }

   public MapColor func_181068_c() {
      return this.field_181069_e;
   }

   public String toString() {
      return this.name;
   }

   public static BlockStoneSlabNew$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   public String getUnlocalizedName() {
      return this.name;
   }

   static {
      for(BlockStoneSlabNew$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
