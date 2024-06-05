package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockBed$EnumPartType implements IStringSerializable {
   HEAD("head"),
   FOOT("foot");

   private final String name;
   private static final BlockBed$EnumPartType[] $VALUES = new BlockBed$EnumPartType[]{HEAD, FOOT};

   private BlockBed$EnumPartType(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
