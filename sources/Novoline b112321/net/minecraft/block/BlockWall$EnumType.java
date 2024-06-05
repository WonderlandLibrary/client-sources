package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockWall$EnumType implements IStringSerializable {
   NORMAL(0, "cobblestone", "normal"),
   MOSSY(1, "mossy_cobblestone", "mossy");

   private static final BlockWall$EnumType[] META_LOOKUP = new BlockWall$EnumType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockWall$EnumType[] $VALUES = new BlockWall$EnumType[]{NORMAL, MOSSY};

   private BlockWall$EnumType(int var3, String var4, String var5) {
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

   public static BlockWall$EnumType byMetadata(int var0) {
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
      for(BlockWall$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
