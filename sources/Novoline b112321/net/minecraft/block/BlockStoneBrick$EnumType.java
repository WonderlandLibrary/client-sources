package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockStoneBrick$EnumType implements IStringSerializable {
   DEFAULT(0, "stonebrick", "default"),
   MOSSY(1, "mossy_stonebrick", "mossy"),
   CRACKED(2, "cracked_stonebrick", "cracked"),
   CHISELED(3, "chiseled_stonebrick", "chiseled");

   private static final BlockStoneBrick$EnumType[] META_LOOKUP = new BlockStoneBrick$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockStoneBrick$EnumType[] $VALUES = new BlockStoneBrick$EnumType[]{DEFAULT, MOSSY, CRACKED, CHISELED};

   private BlockStoneBrick$EnumType(int var3, String var4, String var5) {
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

   public static BlockStoneBrick$EnumType byMetadata(int var0) {
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
      for(BlockStoneBrick$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
