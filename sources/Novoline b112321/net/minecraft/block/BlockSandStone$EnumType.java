package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockSandStone$EnumType implements IStringSerializable {
   DEFAULT(0, "sandstone", "default"),
   CHISELED(1, "chiseled_sandstone", "chiseled"),
   SMOOTH(2, "smooth_sandstone", "smooth");

   private static final BlockSandStone$EnumType[] META_LOOKUP = new BlockSandStone$EnumType[values().length];
   private final int metadata;
   private final String name;
   private final String unlocalizedName;
   private static final BlockSandStone$EnumType[] $VALUES = new BlockSandStone$EnumType[]{DEFAULT, CHISELED, SMOOTH};

   private BlockSandStone$EnumType(int var3, String var4, String var5) {
      this.metadata = var3;
      this.name = var4;
      this.unlocalizedName = var5;
   }

   public int getMetadata() {
      return this.metadata;
   }

   public String toString() {
      return this.name;
   }

   public static BlockSandStone$EnumType byMetadata(int var0) {
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
      for(BlockSandStone$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
