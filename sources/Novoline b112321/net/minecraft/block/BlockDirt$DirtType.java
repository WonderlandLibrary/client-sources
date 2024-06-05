package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockDirt$DirtType implements IStringSerializable {
   DIRT(0, "dirt", "default", MapColor.dirtColor),
   COARSE_DIRT(1, "coarse_dirt", "coarse", MapColor.dirtColor),
   PODZOL(2, "podzol", MapColor.obsidianColor);

   private static final BlockDirt$DirtType[] METADATA_LOOKUP = new BlockDirt$DirtType[values().length];
   private final int metadata;
   private final String name;
   private final String unlocalizedName;
   private final MapColor field_181067_h;
   private static final BlockDirt$DirtType[] $VALUES = new BlockDirt$DirtType[]{DIRT, COARSE_DIRT, PODZOL};

   private BlockDirt$DirtType(int var3, String var4, MapColor var5) {
      this(var3, var4, var4, var5);
   }

   private BlockDirt$DirtType(int var3, String var4, String var5, MapColor var6) {
      this.metadata = var3;
      this.name = var4;
      this.unlocalizedName = var5;
      this.field_181067_h = var6;
   }

   public int getMetadata() {
      return this.metadata;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   public MapColor func_181066_d() {
      return this.field_181067_h;
   }

   public String toString() {
      return this.name;
   }

   public static BlockDirt$DirtType byMetadata(int var0) {
      if(var0 >= METADATA_LOOKUP.length) {
         var0 = 0;
      }

      return METADATA_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   static {
      for(BlockDirt$DirtType var11 : values()) {
         METADATA_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
