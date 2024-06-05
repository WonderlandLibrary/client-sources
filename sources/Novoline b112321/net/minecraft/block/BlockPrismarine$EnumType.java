package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockPrismarine$EnumType implements IStringSerializable {
   ROUGH(0, "prismarine", "rough"),
   BRICKS(1, "prismarine_bricks", "bricks"),
   DARK(2, "dark_prismarine", "dark");

   private static final BlockPrismarine$EnumType[] META_LOOKUP = new BlockPrismarine$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockPrismarine$EnumType[] $VALUES = new BlockPrismarine$EnumType[]{ROUGH, BRICKS, DARK};

   private BlockPrismarine$EnumType(int var3, String var4, String var5) {
      this.meta = var3;
      this.name = var4;
      this.unlocalizedName = var5;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public static BlockPrismarine$EnumType byMetadata(int var0) {
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
      for(BlockPrismarine$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
