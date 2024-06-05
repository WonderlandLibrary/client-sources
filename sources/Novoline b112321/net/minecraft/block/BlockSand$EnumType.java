package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockSand$EnumType implements IStringSerializable {
   SAND(0, "sand", "default", MapColor.sandColor),
   RED_SAND(1, "red_sand", "red", MapColor.adobeColor);

   private static final BlockSand$EnumType[] META_LOOKUP = new BlockSand$EnumType[values().length];
   private final int meta;
   private final String name;
   private final MapColor mapColor;
   private final String unlocalizedName;
   private static final BlockSand$EnumType[] $VALUES = new BlockSand$EnumType[]{SAND, RED_SAND};

   private BlockSand$EnumType(int var3, String var4, String var5, MapColor var6) {
      this.meta = var3;
      this.name = var4;
      this.mapColor = var6;
      this.unlocalizedName = var5;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public MapColor getMapColor() {
      return this.mapColor;
   }

   public static BlockSand$EnumType byMetadata(int var0) {
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
      for(BlockSand$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
