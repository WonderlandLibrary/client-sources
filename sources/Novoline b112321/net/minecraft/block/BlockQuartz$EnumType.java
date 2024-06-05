package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockQuartz$EnumType implements IStringSerializable {
   DEFAULT(0, "default", "default"),
   CHISELED(1, "chiseled", "chiseled"),
   LINES_Y(2, "lines_y", "lines"),
   LINES_X(3, "lines_x", "lines"),
   LINES_Z(4, "lines_z", "lines");

   private static final BlockQuartz$EnumType[] META_LOOKUP = new BlockQuartz$EnumType[values().length];
   private final int meta;
   private final String field_176805_h;
   private final String unlocalizedName;
   private static final BlockQuartz$EnumType[] $VALUES = new BlockQuartz$EnumType[]{DEFAULT, CHISELED, LINES_Y, LINES_X, LINES_Z};

   private BlockQuartz$EnumType(int var3, String var4, String var5) {
      this.meta = var3;
      this.field_176805_h = var4;
      this.unlocalizedName = var5;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String toString() {
      return this.unlocalizedName;
   }

   public static BlockQuartz$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.field_176805_h;
   }

   static {
      for(BlockQuartz$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
