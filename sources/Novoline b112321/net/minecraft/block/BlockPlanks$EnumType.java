package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockPlanks$EnumType implements IStringSerializable {
   OAK(0, "oak", MapColor.woodColor),
   SPRUCE(1, "spruce", MapColor.obsidianColor),
   BIRCH(2, "birch", MapColor.sandColor),
   JUNGLE(3, "jungle", MapColor.dirtColor),
   ACACIA(4, "acacia", MapColor.adobeColor),
   DARK_OAK(5, "dark_oak", "big_oak", MapColor.brownColor);

   private static final BlockPlanks$EnumType[] META_LOOKUP = new BlockPlanks$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private final MapColor field_181071_k;
   private static final BlockPlanks$EnumType[] $VALUES = new BlockPlanks$EnumType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};

   private BlockPlanks$EnumType(int var3, String var4, MapColor var5) {
      this(var3, var4, var4, var5);
   }

   private BlockPlanks$EnumType(int var3, String var4, String var5, MapColor var6) {
      this.meta = var3;
      this.name = var4;
      this.unlocalizedName = var5;
      this.field_181071_k = var6;
   }

   public int getMetadata() {
      return this.meta;
   }

   public MapColor func_181070_c() {
      return this.field_181071_k;
   }

   public String toString() {
      return this.name;
   }

   public static BlockPlanks$EnumType byMetadata(int var0) {
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
      for(BlockPlanks$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
