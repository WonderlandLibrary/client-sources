package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockStone$EnumType implements IStringSerializable {
   STONE(0, MapColor.stoneColor, "stone"),
   GRANITE(1, MapColor.dirtColor, "granite"),
   GRANITE_SMOOTH(2, MapColor.dirtColor, "smooth_granite", "graniteSmooth"),
   DIORITE(3, MapColor.quartzColor, "diorite"),
   DIORITE_SMOOTH(4, MapColor.quartzColor, "smooth_diorite", "dioriteSmooth"),
   ANDESITE(5, MapColor.stoneColor, "andesite"),
   ANDESITE_SMOOTH(6, MapColor.stoneColor, "smooth_andesite", "andesiteSmooth");

   private static final BlockStone$EnumType[] META_LOOKUP = new BlockStone$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private final MapColor field_181073_l;
   private static final BlockStone$EnumType[] $VALUES = new BlockStone$EnumType[]{STONE, GRANITE, GRANITE_SMOOTH, DIORITE, DIORITE_SMOOTH, ANDESITE, ANDESITE_SMOOTH};

   private BlockStone$EnumType(int var3, MapColor var4, String var5) {
      this(var3, var4, var5, var5);
   }

   private BlockStone$EnumType(int var3, MapColor var4, String var5, String var6) {
      this.meta = var3;
      this.name = var5;
      this.unlocalizedName = var6;
      this.field_181073_l = var4;
   }

   public int getMetadata() {
      return this.meta;
   }

   public MapColor func_181072_c() {
      return this.field_181073_l;
   }

   public String toString() {
      return this.name;
   }

   public static BlockStone$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   static {
      for(BlockStone$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
