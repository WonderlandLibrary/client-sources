package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockRedSandstone$EnumType implements IStringSerializable {
   DEFAULT(0, "red_sandstone", "default"),
   CHISELED(1, "chiseled_red_sandstone", "chiseled"),
   SMOOTH(2, "smooth_red_sandstone", "smooth");

   private static final BlockRedSandstone$EnumType[] META_LOOKUP = new BlockRedSandstone$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockRedSandstone$EnumType[] $VALUES = new BlockRedSandstone$EnumType[]{DEFAULT, CHISELED, SMOOTH};

   private BlockRedSandstone$EnumType(int var3, String var4, String var5) {
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

   public static BlockRedSandstone$EnumType byMetadata(int var0) {
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
      for(BlockRedSandstone$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
